package com.kaikeba.idscloud.gateway.service.dto;

import lombok.Data;

import java.util.List;

/**
 * @author: zmc
 * @date: 2021-10-19 20:39
 * @description:
 */

/**
 * {
 *         "appId": "12385",
 *         "name": "班主任数据管理",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 1025495,
 *             "name": "超级管理员"
 *           }
 *         ]
 *       },
 */
@Data
public class CorgiRoleDto {
    private String appId;
    private String name;
    private List<Role> roleList;

    @Data
    public static class Role {
        private Long id;
        private String name;
    }
}
