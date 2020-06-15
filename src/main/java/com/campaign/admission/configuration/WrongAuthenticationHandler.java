package com.campaign.admission.configuration;

import com.campaign.admission.exception.ServiceRuntimeException;
import com.campaign.admission.exception.UserValidatorRuntimeException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class WrongAuthenticationHandler implements AuthenticationFailureHandler {

    private final String REDIRECT_STRING = "/api/home";

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        AuthenticationException e) throws IOException, ServletException {
        if (e.getStackTrace().length == 43) {
            httpServletRequest.getSession().setAttribute("exception",
                    new ServiceRuntimeException(e, "Login exception! User doesn`t exist!"));
        } else {
            httpServletRequest.getSession().setAttribute("exception",
                    new UserValidatorRuntimeException("Wrong password!"));
        }
        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + REDIRECT_STRING);
    }
}
