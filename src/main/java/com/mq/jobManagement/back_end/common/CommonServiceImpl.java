package com.mq.jobManagement.back_end.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 @author EddieZhang
 @create 2023-11-13 10:15 AM
 */
public class CommonServiceImpl<E, K extends Serializable,M extends CommonMapper<E,K>> implements CommonService<E, K> {
    @Autowired
    protected M baseMapper;

    @Override
    public E get(K id) {
        return baseMapper.get(id);
    }

    @Override
    public List<E> list(Map<String, Object> params) {
        return baseMapper.list(params);
    }

    @Override
    public IPage<E> page(Map<String, Object> params) {
        return baseMapper.page(params);
    }

    @Override
    public boolean add(E entity) {
        return baseMapper.add(entity) > 0;
    }

    @Override
    public boolean update(E entity) {
        return baseMapper.update(entity) > 0;
    }

    @Override
    public boolean remove(List<K> ids) {
        return baseMapper.remove(ids) > 0;
    }
}
