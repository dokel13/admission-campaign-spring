package com.campaign.admission.configuration.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static java.lang.String.format;
import static org.springframework.web.servlet.support.RequestContextUtils.getLocaleResolver;

@Component
public class LocaleInterceptor extends HandlerInterceptorAdapter {

    private static final String DEFAULT_LOCALE_PARAMETER = "locale=en";
    private static final String QUERY_STRING = "%s&%s";
    private static final Map<String, Locale> LOCALES = new HashMap<>();

    static {
        LOCALES.put("en", new Locale("en"));
        LOCALES.put("ua", new Locale("ua"));
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Locale locale = LOCALES.get(request.getParameter("locale"));
        if (locale == null) {
            String redirectURI = request.getRequestURI() + "?";
            if (request.getParameter("locale") != null) {
                redirectURI += request.getQueryString()
                        .replace("locale=" + request.getParameter("locale"), DEFAULT_LOCALE_PARAMETER);
            } else if (request.getParameterMap().size() > 0) {
                redirectURI = format(QUERY_STRING, redirectURI + request.getQueryString(), DEFAULT_LOCALE_PARAMETER);
            } else {
                redirectURI += DEFAULT_LOCALE_PARAMETER;
            }
            try {
                response.sendRedirect(redirectURI);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;
        } else {
            LocaleResolver localeResolver = getLocaleResolver(request);
            if (localeResolver != null) {
                localeResolver.setLocale(request, response, locale);
            }
            request.getSession().setAttribute("locale", locale);
        }

        return true;
    }
}
