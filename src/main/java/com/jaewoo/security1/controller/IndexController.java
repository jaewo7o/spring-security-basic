package com.jaewoo.security1.controller;

import com.jaewoo.security1.config.auth.PrincipalDetails;
import com.jaewoo.security1.model.User;
import com.jaewoo.security1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/test/login")
    public @ResponseBody
    String testLogin(
            Authentication authentication,
            @AuthenticationPrincipal PrincipalDetails userDetails
    ) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("userName : " + principalDetails.getUsername());
        System.out.println("userName2 : " + userDetails.getUser().getUsername());

        return "세션정보 확인";
    }

    @GetMapping("/test/oauth/login")
    public @ResponseBody
    String testOAuthLogin(
            Authentication authentication,
            @AuthenticationPrincipal OAuth2User oAuth2User2
    ) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("oAuth2User Attribute : " + oAuth2User.getAttributes());
        System.out.println("oAuth2User2 Attribute : " + oAuth2User2.getAttributes());

        return "OAuth2User 세션정보 확인";
    }

    @GetMapping({"", "/"})
    public String index() {
        System.out.println("test!!!123");

        return "index";
    }

    @GetMapping("/user")
    public @ResponseBody
    String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println("user : " + principalDetails.getUser());
        return "user";
    }

    @GetMapping("/manager")
    public @ResponseBody
    String manager() {
        return "manager";
    }

    @GetMapping("/admin")
    public @ResponseBody
    String admin() {
        return "admin";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user) {
        user.setRole("ROLE_USER");
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);

        userRepository.save(user);
        return "redirect:/loginForm";
    }
}
