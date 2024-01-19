package com.mq.jobManagement.back_end.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 @author EddieZhang
 @create 2023-11-13 10:15 AM
 */
@SuppressWarnings("all")
public class CommonServiceImpl<E, K extends Serializable,M extends CommonMapper<E,K>> extends ServiceImpl<M,E> implements CommonService<E, K> {
    @Autowired
    protected M baseMapper;

    @Override
    public E get(K id) {
        return baseMapper.get(id);
    }

    @Override
    public List<E> list(Map<String, Object> params) {
        QueryWrapper<E> qw = new QueryWrapper<>();
        Optional.ofNullable(params).ifPresent(p -> p.forEach((key, value) -> {
            switch (key){
                case "orderBy": buildOrders(qw, value); break;
                default: buildQuery(qw, key, value);
            }
        }));
        return list(qw);
    }

    @Override
    public IPage<E> page(Map<String, Object> params) {
        IPage<E> page = new Page<>(1, 10);
        QueryWrapper<E> qw = new QueryWrapper<>();

        Optional.ofNullable(params).ifPresent(p -> p.forEach((key, value) -> {
            switch(key){
                case "current": page.setCurrent(Long.parseLong(value.toString())); break;
                case "size": page.setSize(Long.parseLong(value.toString())); break;
                case "orderBy": buildOrders(qw, value); break;
                default: buildQuery(qw, key, value);
            }
        }));

        return  page(page, qw);
    }

    private QueryWrapper<E> buildQuery(QueryWrapper<E> qw, String key, Object value){
        if(qw == null){
            qw = new QueryWrapper<>();
        }
        if(StringUtils.isBlank(key) || value == null || StringUtils.isBlank(value.toString())){
            return qw;
        }
        /**
         * name$ne: "eddie" -> name != 'eddie'
         * age$gt: 18 -> age > 18
         * age$ge: 18 -> age >= 18
         * age$lt: 18 -> age < 18
         * age$le: 18 -> age <= 18
         * name$like: "eddie,irving" ->  name like 'eddie' or name like 'irving'
         * name$likeLeft: "eddie,irving" ->  name like '%eddie' or name like '%irving'
         * name$likeRight: "eddie,irving" ->  name like 'eddie%' or name like 'irving%'
         * name$notLike: "eddie,irving" ->  name not like 'eddie' or name not like 'irving'
         * name$in: "eddie,irving" ->  name in ('eddie', 'irving')
         * name$notIn: "eddie,irving" ->  name not in ('eddie', 'irving')
         * name$isNull -> name is null
         * name$notNull -> name is not null
         */
        String[] params = key.trim().split(",");
        qw.nested(q->{
            for(int i=0; i<params.length; i++){
                if(i != 0){
                    q.or();
                }
                String[] ps = params[i].trim().split("\\$");
                if(ps.length == 1){
                    q.eq(ps[0], value);
                    continue;
                }
                switch(ps[1]){
                    case "ne": q.ne(ps[0], value); break;
                    case "gt": q.gt(ps[0], value); break;
                    case "ge": q.ge(ps[0], value); break;
                    case "lt": q.lt(ps[0], value); break;
                    case "le": q.le(ps[0], value); break;
                    case "like": q.like(ps[0], value); break;
                    case "notLike": q.notLike(ps[0], value); break;
                    case "likeLeft": q.likeLeft(ps[0], value); break;
                    case "likeRight": q.likeRight(ps[0], value); break;
                    case "isNull": q.isNull(ps[0]); break;
                    case "isNotNull": q.isNotNull(ps[0]); break;
                    case "null":
                        if("true".equalsIgnoreCase(value.toString())){
                            q.isNull(ps[0]);
                        }else{
                            q.isNotNull(ps[0]);
                        }
                        break;
                    case "notNull":
                        if("true".equalsIgnoreCase(value.toString())){
                            q.isNotNull(ps[0]);
                        }else{
                            q.isNull(ps[0]);
                        }
                        break;
                    case "in": q.in(ps[0], (Collection<?>) value); break;
                    case "notIn": q.notIn(ps[0], (Collection<?>)value); break;
                    default: q.eq(ps[0], value); break;
                }
            }
        });
        return qw;
    }

    private QueryWrapper<E> buildOrders(QueryWrapper<E> qw, Object orders){
        if(qw == null){
            qw = new QueryWrapper<>();
        }
        if(orders == null || StringUtils.isBlank(orders.toString())){
            return qw;
        }
        /**
         * orderBy: "name$desc,age$asc" -> order by name desc, age asc
         */
        for(String order: orders.toString().split(",")){
            String[] arr = order.split("\\$");
            if(arr.length>1 && "desc".equalsIgnoreCase(arr[1])){
                qw.orderByDesc(arr[0]);
            }else{
                qw.orderByAsc(arr[0]);
            }
        }
        return qw;
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
