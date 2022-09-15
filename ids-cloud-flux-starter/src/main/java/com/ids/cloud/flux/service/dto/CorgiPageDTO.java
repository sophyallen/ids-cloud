package com.ids.cloud.flux.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: zmc
 * @date: 2021-10-19 20:35
 * @description:
 */

/**
 *
 *{
 *   "code": 1,
 *   "msg": "success",
 *   "data": {
 *     "records": [
 *       {
 *         "appId": "1001",
 *         "name": "老mos",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 1,
 *             "name": "超级管理员"
 *           }
 *         ]
 *       },
 *       {
 *         "appId": "1002",
 *         "name": "小课",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 10021,
 *             "name": "超级管理员"
 *           },
 *           {
 *             "id": 1024232,
 *             "name": "大课test1126"
 *           }
 *         ]
 *       },
 *       {
 *         "appId": "1003",
 *         "name": "大课",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 10031,
 *             "name": "超级管理员"
 *           }
 *         ]
 *       },
 *       {
 *         "appId": "1008",
 *         "name": "cms",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 1025350,
 *             "name": "超级管理员"
 *           }
 *         ]
 *       },
 *       {
 *         "appId": "1009",
 *         "name": "新职课",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 10091,
 *             "name": "超级管理员"
 *           }
 *         ]
 *       },
 *       {
 *         "appId": "1010",
 *         "name": "权限管理",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 10101,
 *             "name": "超级管理员"
 *           },
 *           {
 *             "id": 1024218,
 *             "name": "权杖菜单角色"
 *           },
 *           {
 *             "id": 1025422,
 *             "name": "作业助教"
 *           }
 *         ]
 *       },
 *       {
 *         "appId": "1015",
 *         "name": "萝卜工场",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 10151,
 *             "name": "超级管理员"
 *           }
 *         ]
 *       },
 *       {
 *         "appId": "1020",
 *         "name": "cybertron",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 1025462,
 *             "name": "超级管理员"
 *           }
 *         ]
 *       },
 *       {
 *         "appId": "1024",
 *         "name": "米堆学堂",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 10241,
 *             "name": "超级管理员"
 *           }
 *         ]
 *       },
 *       {
 *         "appId": "12347",
 *         "name": "测试应用",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 1024201,
 *             "name": "超级管理员"
 *           },
 *           {
 *             "id": 1025361,
 *             "name": "111"
 *           }
 *         ]
 *       },
 *       {
 *         "appId": "12348",
 *         "name": "测试应用2",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 1024200,
 *             "name": "超级管理员"
 *           }
 *         ]
 *       },
 *       {
 *         "appId": "12349",
 *         "name": "小抄",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 1024228,
 *             "name": "超级管理员"
 *           }
 *         ]
 *       },
 *       {
 *         "appId": "12350",
 *         "name": "ids",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 123501,
 *             "name": "超级管理员"
 *           }
 *         ]
 *       },
 *       {
 *         "appId": "12351",
 *         "name": "社群工具",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 1025365,
 *             "name": "超级管理员"
 *           }
 *         ]
 *       },
 *       {
 *         "appId": "12353",
 *         "name": "ojms",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 1025379,
 *             "name": "超级管理员"
 *           }
 *         ]
 *       },
 *       {
 *         "appId": "12354",
 *         "name": "学习中心",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 1025381,
 *             "name": "超级管理员"
 *           }
 *         ]
 *       },
 *       {
 *         "appId": "12355",
 *         "name": "KOL",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 1025383,
 *             "name": "超级管理员"
 *           }
 *         ]
 *       },
 *       {
 *         "appId": "12357",
 *         "name": "资产",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 1025388,
 *             "name": "超级管理员"
 *           }
 *         ]
 *       },
 *       {
 *         "appId": "12358",
 *         "name": "misc",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 1025390,
 *             "name": "超级管理员"
 *           }
 *         ]
 *       },
 *       {
 *         "appId": "12359",
 *         "name": "数据中台权限控制",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 1025391,
 *             "name": "超级管理员"
 *           }
 *         ]
 *       },
 *       {
 *         "appId": "12360",
 *         "name": "在线测评系统",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 1025394,
 *             "name": "超级管理员"
 *           }
 *         ]
 *       },
 *       {
 *         "appId": "12361",
 *         "name": "作业系统",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 1025409,
 *             "name": "超级管理员"
 *           }
 *         ]
 *       },
 *       {
 *         "appId": "12364",
 *         "name": "办公指北",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 1025436,
 *             "name": "超级管理员"
 *           }
 *         ]
 *       },
 *       {
 *         "appId": "12365",
 *         "name": "aws",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 1025438,
 *             "name": "超级管理员"
 *           }
 *         ]
 *       },
 *       {
 *         "appId": "12366",
 *         "name": "TES-测评",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 1025445,
 *             "name": "超级管理员"
 *           }
 *         ]
 *       },
 *       {
 *         "appId": "12367",
 *         "name": "真北学院",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 1025448,
 *             "name": "超级管理员"
 *           }
 *         ]
 *       },
 *       {
 *         "appId": "12369",
 *         "name": "人事系统",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 1025450,
 *             "name": "超级管理员"
 *           }
 *         ]
 *       },
 *       {
 *         "appId": "12370",
 *         "name": "记账中心",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 1025451,
 *             "name": "超级管理员"
 *           }
 *         ]
 *       },
 *       {
 *         "appId": "12371",
 *         "name": "鹰眼",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 1025464,
 *             "name": "超级管理员"
 *           }
 *         ]
 *       },
 *       {
 *         "appId": "12372",
 *         "name": "米堆cms",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 1025468,
 *             "name": "超级管理员"
 *           }
 *         ]
 *       },
 *       {
 *         "appId": "12374",
 *         "name": "聊天记录风控",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 1025472,
 *             "name": "超级管理员"
 *           }
 *         ]
 *       },
 *       {
 *         "appId": "12375",
 *         "name": "tes",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 1025476,
 *             "name": "超级管理员"
 *           }
 *         ]
 *       },
 *       {
 *         "appId": "12378",
 *         "name": "课节评价",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 1025484,
 *             "name": "超级管理员"
 *           }
 *         ]
 *       },
 *       {
 *         "appId": "12379",
 *         "name": "在职研究生事业部",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 1025487,
 *             "name": "超级管理员"
 *           }
 *         ]
 *       },
 *       {
 *         "appId": "12381",
 *         "name": "开课吧",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 1025490,
 *             "name": "超级管理员"
 *           }
 *         ]
 *       },
 *       {
 *         "appId": "12384",
 *         "name": "发送短信",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 1025494,
 *             "name": "超级管理员"
 *           }
 *         ]
 *       },
 *       {
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
 *       {
 *         "appId": "12389",
 *         "name": "tool",
 *         "sort": null,
 *         "roleList": [
 *           {
 *             "id": 1025499,
 *             "name": "超级管理员"
 *           }
 *         ]
 *       }
 *     ],
 *     "total": 38,
 *     "size": 100,
 *     "current": 1,
 *     "orders": [],
 *     "optimizeCountSql": true,
 *     "hitCount": false,
 *     "searchCount": true,
 *     "pages": 1
 *   }
 * }
 */
@Data
public class CorgiPageDTO<T> implements Serializable {
    private List<T> records;
    private Integer total;
    private Integer size;
    private Integer pages;
}
