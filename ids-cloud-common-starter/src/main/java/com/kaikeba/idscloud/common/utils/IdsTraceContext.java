package com.kaikeba.idscloud.common.utils;

import com.kaikeba.idscloud.common.core.constants.AppConstants;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;

import java.util.Optional;

/**
 * @author: zhangminchao
 * @date: 2022/03/31
 * @description:
 */
public class IdsTraceContext extends TraceContext {
    public static Optional<Long> getPassportUserId() {
        Optional<String> correlation = getCorrelation(AppConstants.PASSPORT_USER_ID_KEY);
        return correlation.map(Long::new);
    }

    public static Optional<AppConstants.LoginPlatformEnum> getLoginPlatform() {
        Optional<String> correlation = getCorrelation(AppConstants.LOGIN_PLATFORM_KEY);
        return correlation.map(AppConstants.LoginPlatformEnum::valueOf);
    }

    public static Optional<Long> getCorgiUserId() {
        Optional<String> correlation = getCorrelation(AppConstants.CORGI_USER_ID_KEY);
        return correlation.map(Long::new);
    }

    public static Optional<String> getVersion() {
        return getCorrelation(AppConstants.VERSION_KEY);
    }

    public static boolean isGray() {
        return getCorrelation(AppConstants.IS_GRAY_KEY).isPresent()
                && Boolean.parseBoolean(getCorrelation(AppConstants.IS_GRAY_KEY).get());
    }

    public static Optional<String> getGrayNameSpace() {
        return getCorrelation(AppConstants.GRAY_NAMESPACE_KEY);
    }

}
