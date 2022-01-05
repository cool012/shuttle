package com.example.hope.base.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
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

    /**
     * 获取查询条件
     *
     * @param function function
     * @param value    条件值
     * @return Wrapper<T>
     */
    Wrapper<T> getQueryWrapper(SFunction<T, ?> function, Object value);

    /**
     * 获取更新条件
     *
     * @param function function
     * @param value    更新值
     * @return Wrapper<T>
     */
    Wrapper<T> getUpdateWrapper(SFunction<T, ?> function, Object value);
}