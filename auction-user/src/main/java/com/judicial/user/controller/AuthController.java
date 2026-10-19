package com.judicial.user.controller;

import com.judicial.user.entity.SysUser;
import com.judicial.user.mapper.SysUserMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final SysUserMapper sysUserMapper;

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpServletResponse resp) {
        SysUser u = sysUserMapper.login(username, password);
        if (u == null) return "redirect:http://localhost:8080/login.html?err=1";
        Cookie ck = new Cookie("ROLE", u.getRole());
        ck.setPath("/");
        resp.addCookie(ck);
        String target = switch (u.getRole()) {
            case "BIDDER" -> "/c-bidder.html";
            case "ADMIN"  -> "/b-admin.html";
            case "FINANCE"-> "/b-finance.html";
            default       -> "/login.html";
        };
        return "redirect:http://localhost:8080" + target;
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse resp) {
        Cookie ck = new Cookie("ROLE", "");
        ck.setPath("/"); ck.setMaxAge(0);
        resp.addCookie(ck);
        return "redirect:http://localhost:8080/login.html";
    }
}
