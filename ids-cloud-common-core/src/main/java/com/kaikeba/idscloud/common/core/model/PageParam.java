package com.kaikeba.idscloud.common.core.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
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
@ApiModel(description = "分页请求对象")
public class PageParam<P> implements Serializable {
    @NotNull
    @Min(1)
    @ApiModelProperty("当前页码")
    private Long page;
    @NotNull
    @Range(min = 1, max = 500)
    @ApiModelProperty("每页条数")
    private Long size;
    @Valid
    @ApiModelProperty("查询条件封装类")
    private P condition;
}
