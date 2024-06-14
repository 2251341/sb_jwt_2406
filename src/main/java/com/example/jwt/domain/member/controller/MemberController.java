package com.example.jwt.domain.member.controller;

import com.example.jwt.domain.member.service.MemberService;
import com.example.jwt.global.reData.RsData;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @Data
    public static class LoginRequest {
        @NotBlank
        private String username;

        @NotBlank
        private String password;



    }
    @Getter
    @AllArgsConstructor
    public static class LoginResponse {
        private final String accessToken;
    }

    @PostMapping("/login")
    public RsData<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse resp) {

        // 테스트용
        //resp.addHeader("Authentication", "JWT Token");
        String accessToken = memberService.genAccessToken(loginRequest.getUsername(), loginRequest.getPassword());

        resp.addHeader("Authentication", accessToken);;

        return RsData.of(
                "S-1",
                "엑세스 토큰이 생성 되었습니다.",
                new LoginResponse(accessToken)
        );
    }
}
