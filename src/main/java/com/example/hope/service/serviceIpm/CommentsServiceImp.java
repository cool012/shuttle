package com.example.hope.service.serviceIpm;

import com.example.hope.common.utils.JwtUtils;
import com.example.hope.common.utils.Utils;
import com.example.hope.config.exception.BusinessException;
import com.example.hope.model.entity.Comments;
import com.example.hope.model.entity.Orders;
import com.example.hope.model.entity.Page;
import com.example.hope.model.vo.OrdersVO;
import com.example.hope.repository.mongo.CommentsRepository;
import com.example.hope.service.business.CommentsService;
import com.example.hope.service.business.OrderService;
import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Resource
    private OrderService orderService;

    @Resource
    private CommentsRepository commentsRepository;

    @Resource
    private MongoTemplate mongoTemplate;


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
        List<OrdersVO> orders = orderService.findByCid(userId);
        for (OrdersVO order : orders) {
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
     * 根据userId改昵称
     * @param userId 用户id
     * @param newName 昵称
     */
    @Override
    public void updateByUserId(long userId, String newName) {
        mongoTemplate.updateMulti(new Query(Criteria.where("userId").is(userId)), new Update().set("name", newName), Comments.class);
    }

    /**
     * 根据商店id查询评论
     *
     * @param storeId 商店id
     * @return 分页包装类
     */
    @Override
    public Page findByStoreId(long storeId, Map<String, String> option) {
        Utils.checkOption(option, null);

        Page page = new Page<Comments>();
        String _id = option.get("_id");
        page.setPageSize(Integer.parseInt(option.get("pageSize")));
        page.setPageNo(Integer.parseInt(option.get("pageNo")));

        Criteria criteria = Criteria.where("storeId").is(storeId);

        // 分页查询优化，不跳页，附带前一页最后一条记录的_id
        if (_id != null) criteria = criteria.and("_id").gt(new ObjectId(_id));
        Query query = new Query(criteria);

        if (_id != null) query.limit(page.getPageSize());
        else query.skip((page.getPageNo() - 1) * page.getPageSize()).limit(page.getPageSize());

        int count = (int) mongoTemplate.count(new Query(Criteria.where("storeId").is(storeId)), Comments.class);
        page.setCount(count);
        page.setTotal((count + page.getPageSize() - 1) / page.getPageSize());
        page.setList(mongoTemplate.find(query, Comments.class));
        return page;
    }

    /**
     * 查询全部评论
     *
     * @return 分页包装类
     */
    @Override
    public Page<Comments> findAll(Map<String, String> option) {
        Utils.checkOption(option, null);

        Page page = new Page<Comments>();
        String _id = option.get("_id");
        page.setPageSize(Integer.parseInt(option.get("pageSize")));
        page.setPageNo(Integer.parseInt(option.get("pageNo")));
        Criteria criteria = new Criteria();

        // 分页查询优化，不跳页，附带前一页最后一条记录的_id
        if (_id != null) criteria = Criteria.where("_id").gt(new ObjectId(_id));
        Query query = new Query(criteria);

        if (_id != null) query.limit(page.getPageSize());
        else query.skip((page.getPageNo() - 1) * page.getPageSize()).limit(page.getPageSize());

        int count = (int) mongoTemplate.count(new Query(), Comments.class);
        page.setCount(count);
        page.setTotal((count + page.getPageSize() - 1) / page.getPageSize());
        page.setList(mongoTemplate.find(query, Comments.class));
        return page;
    }
}
