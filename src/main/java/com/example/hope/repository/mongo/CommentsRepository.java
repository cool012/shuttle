package com.example.hope.repository.mongo;

import com.example.hope.model.entity.Comments;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentsRepository extends MongoRepository<Comments, String> {

    List<Comments> findByContent(String content);

    List<Comments> findByStoreId(long storeId);

    List<Comments> findByName(String name);
}
