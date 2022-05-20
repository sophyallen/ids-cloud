package com.kaikeba.idscloud.flux.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author：zhangminchao
 * @date： 4/28/22
 * @description： corgi 用户基础信息
 * @since: 1.0
 */
@Data
public class CorgiUserDTO implements Serializable {

    private String uid;
    private String name;
    private String phone;
    private String email;
    private String jobNumber;
    private String avatar;
    private String passportUid;
}
