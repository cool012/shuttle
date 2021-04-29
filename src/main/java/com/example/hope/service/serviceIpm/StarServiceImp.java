package com.example.hope.service.serviceIpm;

import com.example.hope.common.logger.LoggerHelper;
import com.example.hope.common.utils.JwtUtils;
import com.example.hope.common.utils.Utils;
import com.example.hope.config.exception.BusinessException;
import com.example.hope.model.entity.Star;
import com.example.hope.model.mapper.StarMapper;
import com.example.hope.service.ProductService;
import com.example.hope.service.StarService;
import com.example.hope.service.StoreService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @description: 收藏服务实现类
 * @author: DHY
 * @created: 2021/04/27 11:57
 */
@Service
@Log4j2
public class StarServiceImp implements StarService {

    @Resource
    private StarMapper starMapper;

    @Resource
    private StoreService storeService;

    @Resource
    private ProductService productService;

    /**
     * 添加收藏
     *
     * @param star 收藏实体类
     */
    @Override
    @Transactional
    @CacheEvict(value = "star", allEntries = true)
    public void insert(Star star, String token) {
        star.setUid(JwtUtils.getUserId(token));
        // 如果sid和pid都有参数，根据type赋值
        if (star.getSid() != 0 && star.getPid() != 0) {
            if (star.isType()) star.setPid(0);
            else star.setSid(0);
        }
        if (!star.isType() && !storeService.exist(star.getSid())) throw new BusinessException(0, "收藏商店不存在");
        else if (star.isType() && !productService.exist(star.getPid())) throw new BusinessException(0, "收藏产品不存在");
        int res = starMapper.insert(star);
        BusinessException.check(res, "添加失败");
        log.info(LoggerHelper.logger(star, res));
    }

    /**
     * 删除收藏
     *
     * @param star  收藏实体类
     * @param token Token
     */
    @Override
    @Transactional
    @CacheEvict(value = "star", allEntries = true)
    public void delete(Star star, String token) {
        long userId = JwtUtils.getUserId(token);
        if (star.getUid() != userId) throw new BusinessException(0, "只能取消当前用户的收藏");
        int res = starMapper.delete(star.getId());
        BusinessException.check(res, "删除失败");
        log.info(LoggerHelper.logger(star, res));
    }

    /**
     * 查询userId的所有商店收藏
     *
     * @param userId 用户id
     * @return 分页包装类
     */
    @Override
    @Transactional
    @Cacheable(value = "star", key = "methodName + #option.toString()")
    public PageInfo<Star> findByStore(String token, Map<String, String> option) {
        Utils.checkOption(option, Star.class);
        String orderBy = String.format("%s %s", option.get("sort"), option.get("order"));
        PageHelper.startPage(Integer.parseInt(option.get("pageNo")), Integer.parseInt(option.get("pageSize")), orderBy);
        return PageInfo.of(starMapper.findByStore(JwtUtils.getUserId(token)));
    }

    /**
     * 查询userId的所有产品收藏
     *
     * @param userId 用户id
     * @return 分页包装类
     */
    @Override
    @Transactional
    @Cacheable(value = "star", key = "methodName + #option.toString()")
    public PageInfo<Star> findByProduct(String token, Map<String, String> option) {
        Utils.checkOption(option, Star.class);
        String orderBy = String.format("%s %s", option.get("sort"), option.get("order"));
        PageHelper.startPage(Integer.parseInt(option.get("pageNo")), Integer.parseInt(option.get("pageSize")), orderBy);
        return PageInfo.of(starMapper.findByProduct(JwtUtils.getUserId(token)));
    }

    /**
     * 用户的商店是否被收藏
     *
     * @param token   Token
     * @param storeId 商店id
     * @return List 收藏列表
     */
    @Override
    @Transactional
    @Cacheable(value = "star", key = "methodName + #storeId")
    public List<Star> isStarByStoreId(String token, long storeId) {
        return starMapper.isStarByStoreId(JwtUtils.getUserId(token), storeId);
    }

    /**
     * 用户的产品是否被收藏
     *
     * @param token     Token
     * @param productId 产品id
     * @return List 收藏列表
     */
    @Override
    @Transactional
    @Cacheable(value = "star", key = "methodName + #productId")
    public List<Star> isStarByProductId(String token, long productId) {
        return starMapper.isStarByProductId(JwtUtils.getUserId(token), productId);
    }
}