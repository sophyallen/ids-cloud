package com.kkb.idscloud.common.core.model;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: zmc
 * @date: 2021-01-29 18:13
 * @description:
 */
@Data
public class PageParam<P> implements Serializable {
    @NotNull
    @Min(1)
    private Long pageNum;
    @NotNull
    @Min(1)
    @Max(500)
    private Long pageSize;
    private P condition;
}
