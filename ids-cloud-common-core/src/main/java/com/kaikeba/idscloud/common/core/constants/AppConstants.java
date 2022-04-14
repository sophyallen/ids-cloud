package com.kaikeba.idscloud.common.core.constants;


/**
 * @author: zhangminchao
 * @date: 2022/03/31
 * @description:
 */
public interface AppConstants {
    String VERSION_KEY = "version";
    String AUTH_INFO_KEY = "authInfo";
    String VERSION_INFO_KEY = "corgiUserId";
    String SERVER_TYPE_KEY = "serverType";
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
