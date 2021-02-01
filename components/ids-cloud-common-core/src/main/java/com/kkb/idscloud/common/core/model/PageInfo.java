package com.kkb.idscloud.common.core.model;

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
    private Long pageNum;
    private Long pageSize;
    private List<D> result;
    private Long pages;
    private long total;
}
