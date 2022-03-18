package com.kaikeba.idscloud.mybatis.base.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kaikeba.idscloud.common.converter.BaseConverter;
import com.kaikeba.idscloud.common.core.model.PageInfo;
import com.kaikeba.idscloud.common.core.model.PageParam;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface BaseService<E> extends IService<E> {
    /**
     * @param pageParam
     * @param <D>       返回的 Dto
     * @param <P>       controller 接收封装查询条件的对象
     *                  值拼接添加 com.kaikeba.idscloud.common.core.annotation.Condition
     *                  注解的字段，并且使用 and 拼接
     *
     * @return
     */
    <D, P> PageInfo<D> page(PageParam<P> pageParam, @NotNull BaseConverter<D, E> converter);

    /**
     * @param pageParam  忽略pageParam 里condition 的条件查询
     * @param queryWrapper
     * @param converter
     * @param <D>
     * @param <P>
     * @return
     */
    <D, P> PageInfo<D> page(PageParam<P> pageParam, @Nullable Wrapper<E> queryWrapper,
                            @NotNull BaseConverter<D, E> converter);

    /**
     * @param param
     * @param <D>   Dto
     * @return 返回 Dto list
     */
    <D> List<D> listByConditionParam(Object param, @NotNull BaseConverter<D, E> converter);

}
