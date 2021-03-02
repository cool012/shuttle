package com.example.hope.service;

import com.example.hope.model.entity.Comments;
import com.example.hope.model.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface CommentsService {

    void insert(String token, Comments comments);

    void delete(Comments comments, String token);

    void update(Comments comments, String token);

    List<Comments> findByStoreId(long storeId);

    List<Comments> findAll();
}
