package com.jaewoo.security1.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println(userRequest.getAccessToken());
        System.out.println(userRequest.getAdditionalParameters());
        System.out.println(userRequest.getClientRegistration().getClientId());
        System.out.println(userRequest.getClientRegistration().getClientName());
        return super.loadUser(userRequest);
    }
}
