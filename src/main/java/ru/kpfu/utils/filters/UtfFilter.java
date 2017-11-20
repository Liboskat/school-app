package ru.kpfu.utils.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

/**
 * Created by Ильшат on 25.09.2017.
 */
public class UtfFilter extends HttpServlet implements Filter {

    public void init(FilterConfig arg0) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf8");

        filterChain.doFilter(request, response);

    }
}
