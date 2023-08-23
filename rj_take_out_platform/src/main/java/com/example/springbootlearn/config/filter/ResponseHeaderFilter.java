package com.example.springbootlearn.config.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description:
 * @author: 王海虹
 * @create: 2022-11-16 17:39
 */
@Component
@Slf4j
public class ResponseHeaderFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        httpServletResponse.addHeader("App-Version", "2.0");
        // 设置 responseHeader: Content-Disposition, 暴露给浏览器访问
        httpServletResponse.setHeader("Access-Control-Expose-Headers", "Content-Disposition");

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
