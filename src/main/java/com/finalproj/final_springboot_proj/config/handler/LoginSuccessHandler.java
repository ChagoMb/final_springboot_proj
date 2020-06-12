package com.finalproj.final_springboot_proj.config.handler;

import com.finalproj.final_springboot_proj.model.Role;
import com.finalproj.final_springboot_proj.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.w3c.dom.UserDataHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {

        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("username", authentication.getPrincipal());

        UserDetails user = (UserDetails) authentication.getPrincipal();
        ArrayList<String> roles = new ArrayList<>();

        for (GrantedAuthority role : user.getAuthorities()) {
            roles.add(role.getAuthority());
        }

        if (roles.contains("ADMIN")) {
            httpServletResponse.sendRedirect("/admin");
        } else if (roles.contains("USER")) {
            httpServletResponse.sendRedirect("/user");
        }
    }
}
