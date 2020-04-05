package com.bytescat.netdisk.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;

public class LoginFilter implements Filter {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession();
        if (session.getAttribute("isLoged") != null && (boolean) (session.getAttribute("isLoged"))) {
            chain.doFilter(req, res);
            return;
        }

        StringBuilder callBack = new StringBuilder();
        callBack.append(req.getRequestURI());
        if (req.getQueryString() != null) {
            callBack.append('?');
            callBack.append(req.getQueryString());
        }

        res.sendRedirect("/login?callback=" + URLEncoder.encode(callBack.toString(), "utf-8"));
    }

    @Override
    public void destroy() {

    }
}
