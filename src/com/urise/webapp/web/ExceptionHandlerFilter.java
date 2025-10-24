package com.urise.webapp.web;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionHandlerFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (RuntimeException e) {
            handleException(e, (HttpServletRequest) request, (HttpServletResponse) response);
        }
    }

    @Override
    public void destroy() {

    }

    private void handleException(RuntimeException e, HttpServletRequest request,
                                 HttpServletResponse response) throws IOException {
        if (e.getMessage().contains("Зарезервированные резюме нельзя менять")) {
            response.sendRedirect(request.getContextPath() + "/error?message=immutable");
        } else {
            response.sendRedirect(request.getContextPath() + "/error?message=general");
        }
    }
}
