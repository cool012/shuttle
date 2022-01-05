package com.example.hope.base.service.imp;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hope.base.service.BaseService;
import com.example.hope.config.exception.BusinessException;

/**
 * @Description 基础服务实现类
 * @Author duhongyu
 * @Data 2022/1/4 17:50
 **/
public class BaseServiceImp<T, K extends BaseMapper<T>> extends ServiceImpl<K, T> implements BaseService<T> {

    @Override
    public T getById(long id, String message) {
        T obj = this.getById(id);
        BusinessException.check(obj == null, message);
        return obj;
    }

    @Override
    public Wrapper<T> getQueryWrapper(SFunction<T, ?> function, Object value) {
        return new LambdaUpdateWrapper<T>().eq(function, value);
    }

    @Override
    public Wrapper<T> getUpdateWrapper(SFunction<T, ?> function, Object value) {
        return new LambdaUpdateWrapper<T>().set(function, value);
    }
}
