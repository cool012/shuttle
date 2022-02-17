package com.example.hope.service.serviceIpm;

import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.hope.base.service.imp.BaseServiceImp;
import com.example.hope.common.utils.*;
import com.example.hope.config.exception.BusinessException;
import com.example.hope.config.redis.RedisService;
import com.example.hope.model.bo.Query;
import com.example.hope.model.entity.User;
import com.example.hope.model.mapper.UserMapper;
import com.example.hope.repository.elasticsearch.EsPageHelper;
import com.example.hope.repository.elasticsearch.UserRepository;
import com.example.hope.service.business.CommentsService;
import com.example.hope.service.other.MailService;
import com.example.hope.service.business.UserService;
import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @description: 用户相关服务
 * @author: DHY
 * @created: 2020/10/23 19:56
 */

@Log4j2
@Service
public class UserServiceIpm extends BaseServiceImp<User, UserMapper> implements UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private EsPageHelper<User> esPageHelper;

    @Resource
    private MailService mailService;

    private CommentsService commentsService;

    private RedisService redisService;

    public UserServiceIpm(@Lazy RedisService redisService, @Lazy CommentsService commentsService) {
        this.redisService = redisService;
        this.commentsService = commentsService;
    }

    /**
     * 用户注册
     *
     * @param user 用户
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "user", allEntries = true)
    public boolean register(User user) {
        // 检查输入合法
        boolean isLegal = Validator.isEmail(user.getEmail());
        BusinessException.check(!isLegal, "邮件格式不正确");
        // 判断用户已存在
        Wrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .eq(User::getName, user.getName())
                .or()
                .eq(User::getEmail, user.getEmail());
        if (this.getOne(wrapper, false) != null) {
            throw new BusinessException(0, "用户已存在");
        }
        // 用户密码加密
        user.setPassword(Utils.encode(user.getPassword()));
        userRepository.save(user);
        return this.save(user);
    }

    /**
     * 用户登录
     *
     * @param account  账户
     * @param password 密码
     * @param expired  token 过期时间，单位：分钟
     * @return 用户信息和 token
     */
    @Override
    public Map<String, Object> login(String account, String password, int expired) {
        String enPassword = Utils.encode(password);
        Wrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .eq(User::getEmail, account)
                .or()
                .eq(User::getName, account)
                .eq(User::getPassword, enPassword);
        User user = this.getOne(wrapper, false);
        BusinessException.check(user == null, "登录失败，用户名或密码错误");
        user.setPassword(null);
        Map<String, Object> map = new HashMap<>();
        String key = String.format("%s_%d", "user", user.getId());
        String token = redisService.get(key);
        if (token == null) {
            token = JwtUtils.createToken(user, expired);
            redisService.expire(key, token, expired, TimeUnit.MINUTES);
        }
        map.put("token", token);
        map.put("user", user);
        return map;
    }

    /**
     * 删除用户
     *
     * @param id 用户id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "user", allEntries = true)
    public boolean delete(long id) {
        userRepository.deleteById(id);
        return this.removeById(id);
    }

    /**
     * 修改用户信息
     *
     * @param user 用户
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "user", allEntries = true)
    public boolean update(User user, String token) {
        BusinessException.check(user.getId() != JwtUtils.getUserId(token), "只能修改当前用户的信息");
        // 修改对应评论的用户信息
        if (user.getName() != null) commentsService.updateByUserId(user.getId(), user.getName());
        userRepository.save(user);
        this.tokenHandler(token);
        return this.updateById(user);
    }

    /**
     * 用户修改密码
     *
     * @param id       用户id
     * @param password 密码
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "user", allEntries = true)
    public boolean updatePassword(long id, String password, String token) {
        BusinessException.check(JwtUtils.getUserId(token) != id, "只能修改当前用户的密码");
        User user = this.getById(id, "用户不存在");
        user.setPassword(Utils.encode(password));
        return this.updateById(user);
    }

    /**
     * 重置密码
     *
     * @param id       用户id
     * @param password 密码
     */
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "user", allEntries = true)
    public boolean resetPassword(long id, String password) {
        Wrapper<User> wrapper = new LambdaUpdateWrapper<User>()
                .set(User::getPassword, Utils.encode(password))
                .eq(User::getId, id);
        return this.update(wrapper);
    }

    /**
     * 增加点数
     *
     * @param id       用户id
     * @param quantity 数量
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "user", allEntries = true)
    public boolean addScore(long id, int quantity) {
        User user = this.getById(id, "用户不存在");
        user.setScore(user.getScore() + quantity);
        return this.updateById(user);
    }

    /**
     * 点数减1
     *
     * @param id 用户id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "user", allEntries = true)
    public boolean reduceScore(long id) {
        if (findByScore(id) == 0) {
            throw new BusinessException(-1, "用户点数为0");
        }
        return this.addScore(id, -1);
    }

    /**
     * 根据手机号查询用户
     *
     * @param phone 电话
     */
    @Override
    @Cacheable(value = "user", key = "methodName + #phone")
    public User findByPhone(String phone) {
        return this.getOne(this.getQueryWrapper(User::getPhone, phone), false);
    }

    /**
     * 根据id查询用户
     *
     * @param id 用户id
     * @return 用户
     */
    @Override
    @Cacheable(value = "user", key = "methodName + #id")
    public User findById(long id) {
        return this.getById(id);
    }

    /**
     * 查询全部用户
     *
     * @return 用户列表
     */
    @Override
    @Cacheable(value = "user", key = "methodName + #query.toString()")
    public IPage<User> findAll(Query query) {
        IPage<User> userPage = PageUtils.getQuery(query);
        return this.page(userPage);
    }


    /**
     * 查询用户点数
     *
     * @param id 用户id
     * @return 点数
     */
    @Override
    @Cacheable(value = "user", key = "methodName + #id")
    public int findByScore(long id) {
        User user = this.getById(id, "用户不存在");
        return user.getScore();
    }

    /**
     * 搜索
     *
     * @param keyword 关键词
     * @return 用户列表
     */
    @Override
    public SearchHits<User> search(String keyword, Map<String, String> option) {
        // todo 字母模糊搜索
        QueryBuilder queryBuilder = QueryBuilders
                .boolQuery()
                .should(QueryBuilders.wildcardQuery("name", String.format("*%s*", keyword)))
                .should(QueryBuilders.wildcardQuery("phone", String.format("*%s*", keyword)));
        return esPageHelper.build(queryBuilder, option, User.class);
    }

    /**
     * 设置管理员
     *
     * @param id 用户id
     */
    @Override
    @CacheEvict(value = "user", allEntries = true)
    public boolean admin(long id) {
        Wrapper<User> wrapper = new LambdaUpdateWrapper<User>()
                .set(User::isAdmin, 1)
                .eq(User::getId, id);
        return this.update(wrapper);
    }

    /**
     * 是否存在用户
     *
     * @param userId 用户Id
     * @return boolean
     */
    @Override
    public boolean exist(long userId) {
        return findById(userId) == null;
    }

    /**
     * 检查token
     *
     * @param token token
     * @return user
     */
    @Cacheable(value = "user", key = "#token")
    @Override
    public User check(String token) {
        long userId = JwtUtils.getUserId(token);
        return findById(userId);
    }

    /**
     * 重置密码 -> 输入邮箱，点击发送邮件 -> 根据user加密生成token -> 邮箱发送成功，跳转到（前端）重置密码界面 ->
     * 用户获取邮箱中的token，提交新密码 -> 重置密码（输入新密码） -> /user/restPassword
     *
     * @param token       token
     * @param newPassword 新密码
     * @param boolean     是否修改成功
     */
    @Override
    public boolean forget(String token, String newPassword) {
        long id = JwtUtils.getUserId(token);
        User User = findById(id);
        BusinessException.check(User == null, "用户不存在");
        this.tokenHandler(token);
        return resetPassword(id, newPassword);
    }

    /**
     * 发送邮箱
     *
     * @param email 邮箱
     */
    @Override
    public void sendEmail(String email) {
        User user = this.getOne(getQueryWrapper(User::getEmail, email), false);
        // 检查邮箱存不存在
        BusinessException.check(user == null, "用户不存在");
        // 加密生成邮箱token
        String token = JwtUtils.createToken(user, 60);
        // 发送邮箱
        mailService.sendTokenMail(email, token, "shuttle重置密码链接");
    }

    /**
     * token 处理
     *
     * @param token token
     */
    private void tokenHandler(String token) {
        // 重置密码时把 token 加入黑名单
        Claims claims = JwtUtils.parseJWT(token);
        Date expiration = claims.getExpiration();
        Date now = new Date();
        if (now.before(expiration)) {
            int diff = (int) (expiration.getTime() - now.getTime()) / 1000 % 60;
            redisService.expire("user_black_".concat(claims.get("userId").toString()), token, diff, TimeUnit.SECONDS);
        }
    }
}