package com.kkb.idscloud.portal.uaa.service.impl;

import com.kkb.idscloud.base.api.BaseAppApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: zmc
 * @date: 2018/11/12 16:26
 * @description:
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ClientDetailsServiceImpl implements ClientDetailsService {

    @Autowired
    private BaseAppApi baseAppApi;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        ClientDetails details = baseAppApi.getAppClientInfo(clientId).getData();
        if (details != null && details.getClientId()!=null && details.getAdditionalInformation() != null) {
            String status = details.getAdditionalInformation().getOrDefault("status", "0").toString();
            if(!"1".equals(status)){
                throw new ClientRegistrationException("客户端已被禁用");
            }
        }
        return details;
    }
}