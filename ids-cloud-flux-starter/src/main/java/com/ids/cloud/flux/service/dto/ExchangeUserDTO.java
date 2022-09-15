package com.ids.cloud.flux.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: zmc
 * @date: 2021-10-19 20:34
 * @description:
 */

/**
 * {
 *   "code": 1,
 *   "msg": "success",
 *   "data": {
 *     "creator": "passport",
 *     "modifier": "passport",
 *     "created": "2021-03-15 20:18:07",
 *     "modified": "2021-03-15 20:18:07",
 *     "status": 1,
 *     "id": 2,
 *     "tenantId": "6XWFVymtaB68REyRBuf", //租户id
 *     "userId": "4574249", //corgi用户id
 *     "openUid": "4574249",//passport用户id
 *     "name": null, //姓名
 *     "mobile": "17696078131", //手机号
 *     "email": null, //邮箱
 *     "avatar": null, //头像
 *     "source": "passport" //来源
 *   }
 * }
 */
@Data
public class ExchangeUserDTO implements Serializable {
    private String userId;
}
