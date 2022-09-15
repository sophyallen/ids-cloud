package com.ids.cloud.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.ids.cloud.common.core.constants.AppConstants;
import com.ids.cloud.common.core.model.AuthInfo;
import com.ids.cloud.common.core.model.VersionInfo;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;

import java.util.Optional;

/**
 * SkySwalking 追踪工具
 * 注意默认限制：
 * 1、存在自定义键值对最多为3对
 * 2、value 长度不能超过128
 * 3、键值都不可为空
 *
 * @author: zhangminchao
 * @date: 2022/03/31
 */
public class IdsTraceContext extends TraceContext {

    public static void putAuthInfo(AuthInfo authInfo) {
        String json = JSONObject.toJSONString(authInfo);
        TraceContext.putCorrelation(AppConstants.AUTH_INFO_KEY, json);
    }

    public static Optional<AuthInfo> getAuthInfo() {
        Optional<String> jsonStr = getCorrelation(AppConstants.AUTH_INFO_KEY);
        return jsonStr.map(json -> JSONObject.parseObject(json, AuthInfo.class));
    }

    public static void putVersionInfo(VersionInfo versionInfo) {
        String json = JSONObject.toJSONString(versionInfo);
        putCorrelation(AppConstants.VERSION_INFO_KEY, json);
    }

    public static Optional<VersionInfo> getVersionInfo() {
        Optional<String> correlation = getCorrelation(AppConstants.VERSION_INFO_KEY);
        return correlation.map(json -> JSONObject.parseObject(json, VersionInfo.class));
    }

}
