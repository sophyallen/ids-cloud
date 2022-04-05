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
    enum LoginPlatform {
        // C端登录平台
        PASSPORT,
        // B端登录平台
        CORGI;
    }
}
