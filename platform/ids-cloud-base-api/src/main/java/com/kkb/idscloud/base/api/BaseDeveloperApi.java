package com.kkb.idscloud.base.api;

import com.kkb.idscloud.base.constants.BaseConstants;
import com.kkb.idscloud.base.model.UserAccount;
import com.kkb.idscloud.common.model.ResultBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zmc
 */
@FeignClient(value = BaseConstants.BASE_SERVER)
public interface BaseDeveloperApi {

    /**
     * 开发者登录
     *
     * @param username
     * @return
     */
    @PostMapping("/developer/login")
    ResultBody<UserAccount> developerLogin(@RequestParam(value = "username") String username);


    /**
     * 注册第三方系统登录账号
     *
     * @param account
     * @param password
     * @param accountType
     * @return
     */
    @PostMapping("/developer/register/thirdParty")
    ResultBody addDeveloperThirdParty(
            @RequestParam(value = "account") String account,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "accountType") String accountType,
            @RequestParam(value = "nickName") String nickName,
            @RequestParam(value = "avatar") String avatar
    );


}
