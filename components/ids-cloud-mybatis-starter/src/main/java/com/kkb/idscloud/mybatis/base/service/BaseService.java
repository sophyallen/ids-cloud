package com.kkb.idscloud.mybatis.base.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kkb.idscloud.common.converter.BaseConverter;
import com.kkb.idscloud.common.core.model.PageInfo;
import com.kkb.idscloud.common.core.model.PageParam;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface BaseService<E> extends IService<E> {
    /**
     * @param pageParam
     * @param <D>       返回的 Dto
     * @param <P>       controller 接收封装查询条件的对象
     * @return
     */
    <D, P> PageInfo<D> page(PageParam<P> pageParam, @NotNull BaseConverter<D, E> converter);

    <D, P> PageInfo<D> page(PageParam<P> pageParam, @Nullable QueryWrapper<E> queryWrapper,
                            @NotNull BaseConverter<D, E> converter);

    /**
     * @param param
     * @param <D>   Dto
     * @return 返回 Dto list
     */
    <D> List<D> listByConditionParam(Object param, @NotNull BaseConverter<D, E> converter);

}
