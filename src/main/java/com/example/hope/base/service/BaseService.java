package com.example.hope.base.service;

import com.baomidou.mybatisplus.extension.service.IService;

public interface BaseService<T> extends IService<T> {

    /**
     * 按 id 获取对象
     *
     * @param id      id
     * @param message 异常信息
     * @return
     */
    T getById(long id, String message);
}
