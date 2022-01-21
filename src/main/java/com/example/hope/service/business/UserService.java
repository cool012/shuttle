package com.example.hope.service.business;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.hope.base.service.BaseService;
import com.example.hope.model.bo.Query;
import com.example.hope.model.entity.User;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.Map;

public interface UserService extends BaseService<User> {

    boolean register(User user);

    boolean delete(long id);

    boolean update(User user, String token);

    boolean updatePassword(long id, String password, String token);

    boolean addScore(long id, int quantity);

    boolean reduceScore(long id);

    boolean admin(long userId);

    boolean exist(long userId);

    void forget(String token, String newPassword);

    void sendEmail(String email);

    Map<String, Object> login(String account, String password, int expired);

    User findByPhone(String phone);

    User findById(long id);

    User check(String token);

    IPage<User> findAll(Query query);

    int findByScore(long id);

    SearchHits<User> search(String keyword, Map<String, String> option);
}
