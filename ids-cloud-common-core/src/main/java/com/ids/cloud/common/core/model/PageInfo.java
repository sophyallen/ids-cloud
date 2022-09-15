package com.ids.cloud.common.core.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @param <D> 传输对象Dto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo<D> implements Serializable {
    @ApiModelProperty("当前页数，从1开始")
    private Long page;
    @ApiModelProperty("每页大小， (0,500]")
    private Long size;
    @ApiModelProperty("当前页返回数据集合")
    private List<D> records;
    @ApiModelProperty("总页数")
    private Long pages;
    @ApiModelProperty("总条数")
    private long total;
}
