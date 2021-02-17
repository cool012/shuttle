package com.example.hope.service;

import com.example.hope.model.entity.Comments;
import com.example.hope.model.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface CommentsService {

    void insert(String token, Comments comments);

    void delete(Comments comments, String token);

    void update(Comments comments, String token);

    PageInfo<Comments> findByStoreId(long storeId, Map<String, String> option);

    PageInfo<Comments> findAll(Map<String, String> option);
}
