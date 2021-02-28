package com.example.hope.elasticsearch.serviceImp;

import com.example.hope.elasticsearch.repository.UserRepository;
import com.example.hope.model.entity.User;
import com.example.hope.elasticsearch.service.EsUserService;
import com.example.hope.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @description: 用户搜索实现类
 * @author: DHY
 * @created: 2021/02/28 13:54
 */
@Service
public class EsUserServiceImp implements EsUserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserService userService;

    /**
     * 导出数据库全部数据
     */
    @Override
    public void importAll() {
        List<User> products = userService.findAll(new HashMap<>()).getList();
        userRepository.saveAll(products);
    }

    /**
     * 保存用户到es
     *
     * @param user 用户
     */
    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    /**
     * 从es删除用户
     *
     * @param userId 用户id
     */
    @Override
    public void delete(long userId) {
        userRepository.deleteById(userId);
    }

    /**
     * 根据用户名或电话搜索
     *
     * @param keyword 关键词
     * @return 用户列表
     */
    @Override
    public List<User> search(String keyword) {
        return userRepository.queryUserByNameOrPhone(keyword);
    }
}
