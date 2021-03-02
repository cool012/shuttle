package com.example.hope.service.serviceIpm;

import com.example.hope.common.utils.JwtUtils;
import com.example.hope.config.exception.BusinessException;
import com.example.hope.model.entity.Comments;
import com.example.hope.model.entity.Orders;
import com.example.hope.repository.mongo.CommentsRepository;
import com.example.hope.service.CommentsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 评论服务实现类
 * @author: DHY
 * @created: 2021/02/17 13:20
 */
@Log4j2
@Service
public class CommentsServiceImp implements CommentsService {

    @Resource
    private OrderServiceIpm orderServiceIpm;

    @Resource
    private CommentsRepository commentsRepository;


    /**
     * 添加评论
     *
     * @param token    Token
     * @param comments 评论
     */
    @Override
    public void insert(String token, Comments comments) {
        long userId = JwtUtils.getUserId(token);
        comments.setUserId(userId);
        boolean status = false;
        List<Orders> orders = orderServiceIpm.findByCid(userId);
        for (Orders order : orders) {
            if (order.getStoreId() == comments.getStoreId() && order.getStatus() == 1) {
                commentsRepository.insert(comments);
                status = true;
                break;
            }
        }
        if (!status) throw new BusinessException(0, "只有在此商店完成过订单的用户才能评论");
    }

    /**
     * 删除评论
     *
     * @param comments 评论
     * @param token    Token
     */
    @Override
    public void delete(Comments comments, String token) {
        long userId = JwtUtils.getUserId(token);
        if (comments.getUserId() == userId || JwtUtils.is_admin(token)) {
            commentsRepository.delete(comments);
        } else throw new BusinessException(0, "只有当前用户才能删除该评论");
    }

    /**
     * 更新评论
     *
     * @param comments 评论
     * @param token    Token
     */
    @Override
    public void update(Comments comments, String token) {
        comments.setUserId(JwtUtils.getUserId(token));
        commentsRepository.save(comments);
    }

    /**
     * 根据商店id查询评论
     *
     * @param storeId 商店id
     * @return 分页包装类
     */
    @Override
    public List<Comments> findByStoreId(long storeId) {
        return commentsRepository.findByStoreId(storeId);
    }

    /**
     * 查询全部评论
     *
     * @return 分页包装类
     */
    @Override
    public List<Comments> findAll() {
        return commentsRepository.findAll();
    }
}
