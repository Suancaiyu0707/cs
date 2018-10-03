package com.casaba.agent.filter;

import javax.servlet.*;
import java.io.IOException;

/***
 * 访问任何位置都会先走这个过滤器
 */
public class AccessFIlter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

    }

    @Override
    public void destroy() {

    }
}
