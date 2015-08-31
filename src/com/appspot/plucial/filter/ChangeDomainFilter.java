package com.appspot.plucial.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeDomainFilter implements Filter {

    public void init(FilterConfig arg0) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        // リクエスト
        HttpServletRequest req = (HttpServletRequest)request;

        if(req.getServerName().equals("plucial-blog.appspot.com")) {

            // TOPもしくユーザー公開ページもしく管理画面の場合、独自ドメインにリダイレクト
            if(req.getRequestURI().equals("/")
                    || req.getRequestURI().startsWith("/info/")
                    || req.getRequestURI().startsWith("/u/")
                    || req.getRequestURI().startsWith("/user/")) {

                String queryUrl = "http://plucial.com" + req.getRequestURI();

                if(req.getQueryString() != null) {
                    queryUrl = queryUrl + "?" + req.getQueryString();
                }
                ((HttpServletResponse)response).sendRedirect(queryUrl);

            }else {
                chain.doFilter(request, response);
            }

        }else {

            chain.doFilter(request, response);
        }
    }

    public void destroy() {
    }

}
