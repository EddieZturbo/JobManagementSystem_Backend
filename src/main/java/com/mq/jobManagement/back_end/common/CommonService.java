package com.mq.jobManagement.back_end.common;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 @author EddieZhang
 @create 2023-11-13 10:15 AM
 */
public interface CommonService<E, K extends Serializable> {
    E get(K id);

    List<E> list(Map<String, Object> params);

    IPage<E> page(Map<String, Object> params);

    boolean add(E entity);

    boolean update(E entity);

    boolean remove(List<K> ids);
}
