package com.kaikeba.idscloud.common.core.constants;


/**
 * @author: zhangminchao
 * @date: 2022/03/31
 * @description:
 */
public interface AppConstants {
    String LOGIN_PLATFORM_KEY = "loginPlatform";
    String VERSION_KEY = "version";
    String LOGIN_USER_NAME = "loginUserName";
    String PASSPORT_USER_ID_KEY = "passportUserId";
    String CORGI_USER_ID_KEY = "corgiUserId";
    String SERVER_TYPE_KEY = "serverType";
    String GRAY_NAMESPACE_KEY = "grayNameSpace";
    String IS_GRAY_KEY = "isGray";
    enum LoginPlatformEnum {
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
