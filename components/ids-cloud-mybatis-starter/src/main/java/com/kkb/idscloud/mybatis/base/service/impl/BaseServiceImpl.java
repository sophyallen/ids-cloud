package com.kkb.idscloud.mybatis.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kkb.idscloud.common.converter.BaseConverter;
import com.kkb.idscloud.common.core.annotation.Condition;
import com.kkb.idscloud.common.core.constants.ErrorCodeEnum;
import com.kkb.idscloud.common.core.model.PageInfo;
import com.kkb.idscloud.common.core.model.PageParam;
import com.kkb.idscloud.common.core.utils.IdsStringUtils;
import com.kkb.idscloud.mybatis.base.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @param <M> Mapper
 * @param <E> Entity
 */
@Getter
@Setter
@AllArgsConstructor
public class BaseServiceImpl<M extends BaseMapper<E>, E> extends ServiceImpl<M, E> implements BaseService<E> {
    @Override
    public <D, P> PageInfo<D> page(PageParam<P> pageParam, BaseConverter<D, E> converter) {
        ErrorCodeEnum.SERVER_ERROR_B0001.assertNotNull(converter, "converter must not be null");
        P condition = pageParam.getCondition();
        List<Field> allFields = getConditionFields(condition.getClass(), new ArrayList<>());
        Wrapper<E> wrapper = generateQueryWrapper(allFields, condition);
        return page(pageParam, wrapper, converter);
    }

    @Override
    public <D, P> PageInfo<D> page(PageParam<P> pageParam, @Nullable Wrapper<E> queryWrapper, @NotNull BaseConverter<D, E> converter) {
        Page<E> page = new Page<>(pageParam.getPage(), pageParam.getSize());
        super.page(page, queryWrapper);
        List<D> result = converter.toDto(page.getRecords());
        PageInfo<D> pageInfo = PageInfo.<D>builder().page(page.getCurrent()).size(page.getSize())
                .pages(page.getPages()).total(page.getTotal()).records(result).build();
        return pageInfo;
    }

    @Override
    public <D> List<D> listByConditionParam(Object param, BaseConverter<D, E> converter) {
        ErrorCodeEnum.SERVER_ERROR_B0001.assertNotNull(converter, "converter must not be null");
        List<Field> allFields = getConditionFields(param.getClass(), new ArrayList<>());
        Wrapper<E> wrapper = generateQueryWrapper(allFields, param);
        List<E> list = super.list(wrapper);
        return converter.toDto(list);
    }

    private Wrapper<E> generateQueryWrapper(List<Field> allFields, Object instance) {
        QueryWrapper<E> wrapper = new QueryWrapper<>();
        for (Field f : allFields) {
            Object val = null;
            try {
                val = f.get(instance);
            } catch (IllegalAccessException e) {
                log.error("generate query conditons occur error", e);
            }
            Condition condition = f.getAnnotation(Condition.class);
            String originName = StringUtils.isBlank(condition.fieldName()) ? f.getName() : condition.fieldName();
            String fieldName = IdsStringUtils.camelToUnderline(originName);
            Condition.CompareEnum compare = condition.compare();
            Condition.SortEnum sort = condition.orderBy();
            jointOrderBy(wrapper, sort, fieldName);
            jointConditon(wrapper, compare, fieldName, val);
        }
        return wrapper;
    }

    private static List<Field> getConditionFields(Class clazz, List<Field> fields) {
        if (clazz != null) {
            Field[] fieldArray = clazz.getDeclaredFields();
            List<Field> conditionFileds = Arrays.stream(fieldArray).parallel()
                    .filter(f -> f.isAnnotationPresent(Condition.class))
                    .collect(Collectors.toList());
            fields.addAll(conditionFileds);
            getConditionFields(clazz.getSuperclass(), fields);
        }
        fields.forEach(f -> {
            f.setAccessible(true);
        });
        return fields;
    }

    protected void jointConditon(QueryWrapper<E> wrapper, Condition.CompareEnum compare,
                                 String fieldName, Object val) {
        // 添加where 条件
        switch (compare) {
            case EQ:
                wrapper.eq(Objects.nonNull(val), fieldName, val);
                break;
            case GT:
                wrapper.gt(Objects.nonNull(val), fieldName, val);
                break;
            case LIKE:
                wrapper.like(Objects.nonNull(val), fieldName, val);
                break;
            case IN:
                if (Objects.isNull(val)) {
                    break;
                }
                if (val.getClass().isArray()) {
                    Object[] objs = (Object[]) val;
                    wrapper.in(objs.length > 0, fieldName, objs);
                }
                ErrorCodeEnum.CLIENT_ERROR_A0400.assertTrue((t) -> {
                    return val instanceof Collection;
                }, val);
                Collection value = (Collection)val;
                wrapper.in(!value.isEmpty(), fieldName, value);
                break;
            case LT:
                wrapper.lt(Objects.nonNull(val), fieldName, val);
                break;
            case GTE:
                wrapper.ge(Objects.nonNull(val), fieldName, val);
                break;
            case LTE:
                wrapper.le(Objects.nonNull(val), fieldName, val);
                break;
            case NEQ:
                wrapper.ne(Objects.nonNull(val), fieldName, val);
                break;
            case BETWEEN: {
                if (Objects.isNull(val)) {
                    break;
                }
                ErrorCodeEnum.SERVER_ERROR_B0001
                        .assertTrue(o -> o instanceof List, val,
                                "query condition 'between' value type must be list");
                List valList = (List) val;
                wrapper.between(valList.size() == 2, fieldName, valList.get(0), valList.get(1));
                break;
            }
            case IS_NULL:
                wrapper.isNull(fieldName);
                break;
            case NOT_NULL:
                wrapper.isNotNull(fieldName);
                break;
            case LEFT_LIKE:
                wrapper.likeLeft(Objects.nonNull(val), fieldName, val);
                break;
            case RIGHT_LIKE:
                wrapper.likeRight(Objects.nonNull(val), fieldName, val);
                break;
            default:
                break;
        }
    }

    protected void jointOrderBy(QueryWrapper wrapper, @NotNull Condition.SortEnum sort, String fieldName) {
        switch (sort) {
            case ASC:
                wrapper.orderByAsc(fieldName);
                break;
            case DESC:
                wrapper.orderByDesc(fieldName);
                break;
            default:
                break;
        }
    }
}
