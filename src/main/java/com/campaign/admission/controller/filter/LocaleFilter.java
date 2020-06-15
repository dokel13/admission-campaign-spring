package com.campaign.admission.controller.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static java.lang.String.format;

//@WebFilter(dispatcherTypes = {DispatcherType.REQUEST})
//@Component
//@Order(1)
//public class LocaleFilter implements Filter {
//
//    private static final String DEFAULT_LOCALE_PARAMETER = "locale=en";
//    private static final String QUERY_STRING = "%s&%s";
//    private static final Map<String, Locale> LOCALES = new HashMap<>();
//
//    @Override
//    public void init(FilterConfig filterConfig) {
//        LOCALES.put("en", new Locale("en"));
//        LOCALES.put("ua", new Locale("ua"));
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        Locale locale = LOCALES.get(request.getParameter("locale"));
//        if (locale == null) {
//            String redirectURI = request.getRequestURI() + "?";
//            if (request.getParameter("locale") != null) {
//                redirectURI += request.getQueryString()
//                        .replace("locale=" + request.getParameter("locale"), DEFAULT_LOCALE_PARAMETER);
//            } else if (request.getParameterMap().size() > 0) {
//                redirectURI = format(QUERY_STRING, redirectURI + request.getQueryString(), DEFAULT_LOCALE_PARAMETER);
//            } else {
//                redirectURI += DEFAULT_LOCALE_PARAMETER;
//            }
//            ((HttpServletResponse) servletResponse).sendRedirect(redirectURI);
//
//            return;
//        } else {
//            ((HttpServletRequest) servletRequest).getSession().setAttribute("locale", locale);
//        }
//        filterChain.doFilter(servletRequest, servletResponse);
//    }
//}
