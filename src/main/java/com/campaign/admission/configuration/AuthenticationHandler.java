package com.campaign.admission.configuration;

import com.campaign.admission.domain.Role;
import com.campaign.admission.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class AuthenticationHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final static Map<String, String> ROLE_TARGET_URL_MAP = new HashMap<>();

    static {
        ROLE_TARGET_URL_MAP.put("STUDENT", "/api/student?");
        ROLE_TARGET_URL_MAP.put("ADMIN", "/api/admin?");
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       Authentication authentication) throws IOException {
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        String targetUrl = determineTargetUrl(authentication) + request.getQueryString();
        request.getSession().setAttribute("role", getRole(authentication));
        if (response.isCommitted()) {
            log.debug("Response already committed!");

            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    private String determineTargetUrl(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if (ROLE_TARGET_URL_MAP.containsKey(authorityName)) {

                return ROLE_TARGET_URL_MAP.get(authorityName);
            }
        }

        return "/api/home";
    }

    private Role getRole(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return user.getRole();
    }
}
