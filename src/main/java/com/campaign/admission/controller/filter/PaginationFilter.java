package com.campaign.admission.controller.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;

@Component
@Order(2)
public class PaginationFilter implements Filter {

    private static final String PAGE_STRING = "page=";
    private static final String QUERY_STRING = "%s?%s";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        if (httpRequest.getParameter("page") != null) {
            int page = parseInt(httpRequest.getParameter("page"));
            if (page < 1) {
                String redirectURI = format(QUERY_STRING, httpRequest.getRequestURI(),
                        httpRequest.getQueryString().replace(PAGE_STRING + page, PAGE_STRING + 1));
                ((HttpServletResponse) servletResponse).sendRedirect(redirectURI);

                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
