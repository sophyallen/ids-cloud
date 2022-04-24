package com.kaikeba.idscloud.common.core.constants;


/**
 * @author: zhangminchao
 * @date: 2022/03/31
 * @description:
 */
public interface AppConstants {
    String VERSION_KEY = "version";
    String AUTH_INFO_KEY = "authInfo";
    String VERSION_INFO_KEY = "versionInfo";
    String SERVER_TYPE_KEY = "serverType";
    String DEFAULT_SERVER_VERSION = "v0";
    String SERVER_NAME_PROPERTITY_KEY = "spring.application.name";
    String DEFAULT_SERVER_NAME = "UNKNOWN_SERVER";
    enum AuthPlatformEnum {
        // C端登录平台
        PASSPORT,
        // B端登录平台
        CORGI;
    }

    enum ServerTypeEnum {
        GATEWAY_TOB(true),
        GATEWAY_TOC(false),
        SERVER_TOC(false),
        SERVER_TOB(true);
        final boolean isConsole;
        ServerTypeEnum(boolean isConsole){
            this.isConsole = isConsole;
        }

    }
}
