package com.campaign.admission.configuration.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;

@Component
public class PaginationInterceptor extends HandlerInterceptorAdapter {

    private static final String PAGE_STRING = "page=";
    private static final String QUERY_STRING = "%s?%s";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getParameter("page") != null) {
            int page = parseInt(request.getParameter("page"));
            if (page < 1) {
                String redirectURI = format(QUERY_STRING, request.getRequestURI(),
                        request.getQueryString().replace(PAGE_STRING + page, PAGE_STRING + 1));
                response.sendRedirect(redirectURI);

                return false;
            }
        }

        return true;
    }
}
