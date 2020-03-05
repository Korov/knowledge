package com.security.springsecurity.init;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * @author Administrator
 * @version 1.0
 **/
public class SpringSecurityApplicationInitializer
        extends AbstractSecurityWebApplicationInitializer {
    public SpringSecurityApplicationInitializer() {
        /**
         * 已经使用Spring或Spring MVC 则需要注释掉，否则需要解除注释
         */
        //super(WebSecurityConfig.class);
    }
}
