package com.example.hope.service.serviceIpm;

import com.example.hope.common.utils.JwtUtils;
import com.example.hope.common.utils.Utils;
import com.example.hope.config.exception.BusinessException;
import com.example.hope.model.entity.Comments;
import com.example.hope.model.entity.Orders;
import com.example.hope.model.mapper.CommentsMapper;
import com.example.hope.service.CommentsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @description: 评论服务实现类
 * @author: DHY
 * @created: 2021/02/17 13:20
 */
@Log4j2
@Service
public class CommentsServiceImp implements CommentsService {

    private CommentsMapper commentsMapper;
    private OrderServiceIpm orderServiceIpm;

    @Autowired
    public CommentsServiceImp(CommentsMapper commentsMapper, OrderServiceIpm orderServiceIpm) {
        this.commentsMapper = commentsMapper;
        this.orderServiceIpm = orderServiceIpm;
    }


    /**
     * 添加评论
     *
     * @param token    Token
     * @param comments 评论
     */
    @Override
    @CacheEvict(value = "comments", allEntries = true)
    public void insert(String token, Comments comments) {
        long userId = JwtUtils.getUserId(token);
        comments.setUserId(userId);
        int res = 0;
        boolean status = false;
        // 只允许在当前商店下单完成的用户评论
        List<Orders> orders = orderServiceIpm.findByCid(userId);
        for (Orders order : orders) {
            if (order.getStoreId() == comments.getStoreId() && order.getStatus() == 1) {
                res = commentsMapper.insert(comments);
                status = true;
                break;
            }
        }
        if (!status) throw new BusinessException(0, "只有在此商店完成过订单的用户才能评论");
        log.info("comments insert -> " + comments.toString() + " -> " + res);
        BusinessException.check(res, "评论失败");
    }

    /**
     * 删除评论
     *
     * @param comments 评论
     * @param token    Token
     */
    @Override
    @CacheEvict(value = "comments", allEntries = true)
    public void delete(Comments comments, String token) {
        long userId = JwtUtils.getUserId(token);
        int res;
        if (comments.getUserId() == userId || JwtUtils.is_admin(token)) {
            res = commentsMapper.delete(comments);
        } else throw new BusinessException(0, "只有当前用户才能删除该评论");
        log.info("comment delete ->" + comments.toString() + " res ->" + res);
        BusinessException.check(res, "删除失败");
    }

    /**
     * 更新评论
     *
     * @param comments 评论
     * @param token    Token
     */
    @Override
    @CacheEvict(value = "comments", allEntries = true)
    public void update(Comments comments, String token) {
        comments.setUserId(JwtUtils.getUserId(token));
        int res = commentsMapper.update(comments);
        log.info("update comment ->" + comments.toString() + " res -> " + res);
        BusinessException.check(res, "更新失败");
    }

    /**
     * 根据商店id查询评论
     *
     * @param storeId 商店id
     * @return 分页包装类
     */
    @Override
    @Cacheable(value = "comments", key = "method + #storeId.toString() + #option.toString()")
    public PageInfo<Comments> findByStoreId(long storeId, Map<String, String> option) {
        Utils.check_map(option);
        PageHelper.startPage(Integer.parseInt(option.get("pageNo")), Integer.parseInt(option.get("pageSize")));
        return PageInfo.of(commentsMapper.findByStoreId(storeId));
    }

    /**
     * 查询全部评论
     *
     * @return 分页包装类
     */
    @Override
    @Cacheable(value = "comments", key = "method + #option.toString()")
    public PageInfo<Comments> findAll(Map<String, String> option) {
        Utils.check_map(option);
        PageHelper.startPage(Integer.parseInt(option.get("pageNo")), Integer.parseInt(option.get("pageSize")));
        return PageInfo.of(commentsMapper.findAll());
    }
}
