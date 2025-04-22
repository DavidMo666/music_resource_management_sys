package com.g12.util;

import com.g12.context.BaseContext;
import com.g12.properties.JwtProperty;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    JwtProperty jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //1.获取token
        String token = request.getHeader(jwtProperties.getUserTokenName());
        log.info("jwt校验:{}", token);

        //2.token为空
        if (token == null){
            return false;
        }
        try {
            //3.获取user_id
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
            Long userId = claims.get("UserId", Long.class);
            BaseContext.setCurrentId(userId);
            return true;

        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }
}
