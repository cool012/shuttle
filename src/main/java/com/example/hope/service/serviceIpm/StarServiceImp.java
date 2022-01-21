package com.example.hope.service.serviceIpm;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.hope.base.service.imp.BaseServiceImp;
import com.example.hope.common.utils.JwtUtils;
import com.example.hope.common.utils.PageUtils;
import com.example.hope.config.exception.BusinessException;
import com.example.hope.model.bo.Query;
import com.example.hope.model.entity.Star;
import com.example.hope.model.mapper.StarMapper;
import com.example.hope.model.vo.StarVO;
import com.example.hope.service.business.ProductService;
import com.example.hope.service.business.StarService;
import com.example.hope.service.business.StoreService;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @description: 收藏服务实现类
 * @author: DHY
 * @created: 2021/04/27 11:57
 */
@Service
@Log4j2
public class StarServiceImp extends BaseServiceImp<Star, StarMapper> implements StarService {

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
    public boolean insert(Star star, String token) {
        star.setUid(JwtUtils.getUserId(token));
        // 如果sid和pid都有参数，根据type赋值
        if (star.getSid() != 0 && star.getPid() != 0) {
            if (star.isType()) star.setPid(0);
            else star.setSid(0);
        }
        if (!star.isType() && !storeService.exist(star.getSid())) throw new BusinessException(0, "收藏商店不存在");
        else if (star.isType() && !productService.exist(star.getPid())) throw new BusinessException(0, "收藏产品不存在");
        return this.save(star);
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
    public boolean delete(Star star, String token) {
        long userId = JwtUtils.getUserId(token);
        if (star.getUid() != userId) throw new BusinessException(0, "只能取消当前用户的收藏");
        return this.removeById(star.getId());
    }

    /**
     * 查询userId的所有商店收藏
     *
     * @param userId 用户id
     * @return 分页包装类
     */
    @Override
    @Transactional
    @Cacheable(value = "star", key = "methodName + #query.toString()")
    public IPage<StarVO> findByStore(String token, Query query) {
        Page<Star> page = PageUtils.getQuery(query);
        return starMapper.findByStore(page, JwtUtils.getUserId(token));
    }

    /**
     * 查询userId的所有产品收藏
     *
     * @param userId 用户id
     * @return 分页包装类
     */
    @Override
    @Transactional
    @Cacheable(value = "star", key = "methodName + #query.toString()")
    public IPage<StarVO> findByProduct(String token, Query query) {
        Page<Star> page = PageUtils.getQuery(query);
        return starMapper.findByProduct(page, JwtUtils.getUserId(token));
    }

    /**
     * 用户的商店是否被收藏
     *
     * @param token   Token
     * @param storeId 商店id
     * @return boolean
     */
    @Override
    @Transactional
    @Cacheable(value = "star", key = "methodName + #storeId")
    public boolean isStarByStoreId(String token, long storeId) {
        long userId = JwtUtils.getUserId(token);
        Wrapper<Star> wrapper = new LambdaQueryWrapper<Star>()
                .eq(Star::getUid, userId)
                .eq(Star::getSid, storeId);
        return this.list(wrapper).size() != 0;
    }

    /**
     * 用户的产品是否被收藏
     *
     * @param token     Token
     * @param productId 产品id
     * @return boolean
     */
    @Override
    @Transactional
    @Cacheable(value = "star", key = "methodName + #productId")
    public boolean isStarByProductId(String token, long productId) {
        long userId = JwtUtils.getUserId(token);
        Wrapper<Star> wrapper = new LambdaQueryWrapper<Star>()
                .eq(Star::getUid, userId)
                .eq(Star::getPid, productId);
        return this.list(wrapper).size() != 0;
    }
}