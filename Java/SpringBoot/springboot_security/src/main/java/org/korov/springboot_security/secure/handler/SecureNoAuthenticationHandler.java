package org.korov.springboot_security.secure.handler;

import org.korov.springboot_security.emun.ResultCode;
import org.korov.springboot_security.util.Result;
import org.korov.springboot_security.util.ServletUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Security 用户未登陆处理类
 *
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 * */
@Component
public class SecureNoAuthenticationHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        ServletUtil.writeJson(Result.failure(ResultCode.NOT_LOGIN));
    }
}
