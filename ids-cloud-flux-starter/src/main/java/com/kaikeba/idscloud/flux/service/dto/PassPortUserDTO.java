package com.kaikeba.idscloud.flux.service.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
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
    @JsonAlias("realname")
    private String realName;
    private Integer gender;
    private String mobile;
}
