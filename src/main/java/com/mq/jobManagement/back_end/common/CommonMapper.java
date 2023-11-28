package com.mq.jobManagement.back_end.common;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 @author EddieZhang
 @create 2023-11-13 10:14 AM
 */
public interface CommonMapper<E, K extends Serializable> extends BaseMapper<E> {
    default E get(K id){return selectById(id);}

    default List<E> list(Map<String, Object> params){return selectByMap(params);}

    default IPage<E> page(Map<String, Object> params){
        IPage<E> page = new Page<E>((Integer)params.get("current"), (Integer)params.get("size"));
        IPage<E> selectPage = selectPage(page, new LambdaQueryWrapper<E>());
        return selectPage;
    }

    default int add(E entity){return insert(entity);}

    default int update(E entity){return updateById(entity);}

    default int remove(List<K> ids){return deleteBatchIds(ids);}
}
