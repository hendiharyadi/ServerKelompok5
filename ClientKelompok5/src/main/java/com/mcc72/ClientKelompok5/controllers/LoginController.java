package com.mcc72.ClientKelompok5.controllers;

import com.mcc72.ClientKelompok5.models.dto.LoginRequest;
import com.mcc72.ClientKelompok5.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class LoginController {

    private AuthService authService;

    @GetMapping("/login")
    public String loginView(LoginRequest loginRequest, Authentication auth) {
        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            return "auth/login";
        }
        return "redirect:/dashboard";
    }

    @PostMapping("/login")
    public String login(LoginRequest loginRequest) throws Exception {
        Boolean isLogin = authService.userSignIn(loginRequest);
        if (!isLogin) {
            return "redirect:/login?error=true";
        }
        return "redirect:/dashboard";
    }


}
