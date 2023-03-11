package com.yusa.acgnbbs.filter;

import com.yusa.acgnbbs.domain.LoginUser;
import com.yusa.acgnbbs.security.CustomAuthenticationToken;
import com.yusa.acgnbbs.utils.JwtUtils;
import com.yusa.acgnbbs.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    RedisCache redisCache;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取token，header中token
        String token = request.getHeader("token");

        //如果没有token则放行，让后面的filterchain来处理
        if (!StringUtils.hasLength(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        // 解析token
        String id;
        try {
            Claims claims = JwtUtils.parseJWT(token);
            id = claims.getSubject();
            System.out.println(id);
        } catch (Exception e) {
            throw new RuntimeException("解析token失败");
        }

        // 获取id
        LoginUser loginuser = redisCache.getCacheObject("login:"+id);
        if(Objects.isNull(loginuser)){
            throw new RuntimeException("用户未登录");
        }
        //封装Authentication
        CustomAuthenticationToken customAuthenticationToken = new CustomAuthenticationToken(loginuser, null, loginuser.getAuthorities());
        //存入SecurityContentHolder
        SecurityContextHolder.getContext().setAuthentication(customAuthenticationToken);
        //放行
        filterChain.doFilter(request, response);

    }
}
