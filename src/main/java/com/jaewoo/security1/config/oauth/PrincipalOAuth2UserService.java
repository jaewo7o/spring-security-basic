package com.jaewoo.security1.config.oauth;

import com.jaewoo.security1.config.auth.PrincipalDetails;
import com.jaewoo.security1.model.User;
import com.jaewoo.security1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println(userRequest.getAccessToken());
        System.out.println(userRequest.getAdditionalParameters());
        System.out.println(userRequest.getClientRegistration().getClientId());
        System.out.println(userRequest.getClientRegistration().getClientName());

        // 구글로그인 버튼 클릭 -> 구글로그인창 -> 로그인완료 -> Code 리턴(OAuth-Client 라이브러리) -> AccessToken 요청
        // userRequest정보 -> 회원프로필 받아야함(loadUser함수) -> 회원프로필 확인
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getClientId();
        String providerId = oAuth2User.getAttribute("sub");
        String username = provider + "_" + providerId;
        String email = oAuth2User.getAttribute("email");
        String password = passwordEncoder.encode("1234");
        String role = "ROLE_USER";

        User user = userRepository.findByUsername(username)
                .orElse(User.builder()
                        .username(username)
                        .email(email)
                        .password(password)
                        .provider(provider)
                        .providerId(providerId)
                        .role(role)
                        .build());

        userRepository.save(user);

        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }
}
