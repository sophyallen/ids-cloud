package com.kaikeba.idscloud.gateway.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: zmc
 * @date: 2021-10-19 20:33
 * @description:
 */
@Data
public class CorgiResultDto<T> implements Serializable {
    private Integer code;
    private String msg;
    private T data;
}
