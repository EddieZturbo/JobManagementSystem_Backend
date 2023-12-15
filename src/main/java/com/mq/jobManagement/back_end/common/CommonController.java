package com.mq.jobManagement.back_end.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mq.jobManagement.back_end.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import static com.mq.jobManagement.back_end.utils.ResultCode.*;

/**
 @author EddieZhang
 @create 2023-11-13 10:15 AM
 */
@SuppressWarnings("all")
public class CommonController<S extends CommonService<E,K>, E extends CommonEntity, K extends Serializable> {
    @Autowired
    protected S baseService;

    @GetMapping("get/{id}")
    public Result<E> get(@PathVariable K id) {
        E e = baseService.get(id);
        return e!=null? Result.ok(e): Result.error(DATA_ABSENCE);
    }

    @PostMapping("page")
    public Result page(@RequestBody(required = false) Map<String, Object> params) throws Exception{
        IPage<E> page = baseService.page(params);
        page.getRecords().forEach(e -> e.setNo(page.getRecords().indexOf(e) + 1L));
        return Result.ok(page);
    }

    @PostMapping("list")
    public Result<List<E>> list(@RequestBody(required = false) Map<String, Object> params) throws Exception{
        List<E> list = baseService.list(params);
        return list.size() != 0 ? Result.ok(list) : Result.error(DATA_ABSENCE);
    }

    @PostMapping("add")
    public Result<E> add(@RequestBody E entity) {
        return baseService.add(entity)? Result.ok(entity): Result.error(ADD_DATA_FAILURE);
    }

    @PutMapping("update")
    public Result<E> update(@RequestBody E entity) {
        return baseService.update(entity)? Result.ok(entity): Result.error(UPDATE_DATA_FAILURE);
    }

    @DeleteMapping("remove")
    public Result<List<K>> remove(@RequestBody E entity) {
        return baseService.remove(entity.getIds())? Result.ok(entity.getIds()): Result.error(DELETE_DATA_FAILURE);
    }
}
