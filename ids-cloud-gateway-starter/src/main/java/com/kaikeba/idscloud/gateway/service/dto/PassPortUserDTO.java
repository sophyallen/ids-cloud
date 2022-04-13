package com.kaikeba.idscloud.gateway.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangminchao
 * @date: 2022/04/05
 * @description:
 */
@Data
public class PassPortUserDTO implements Serializable {
    private Long uid;
    private String nickname;
    private String avatar;
    private String realname;
    private Integer gender;
    private String mobile;
}
