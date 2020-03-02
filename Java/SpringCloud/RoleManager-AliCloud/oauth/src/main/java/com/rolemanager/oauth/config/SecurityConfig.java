package com.rolemanager.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 使用BCryptPasswordEncoder作为密码的加密解密
     *
     * @return
     */
    @Bean(name = "bCryptPasswordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //密码模式才需要配置,认证管理器
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Autowired
    @Qualifier(value = "bCryptPasswordEncoder")
    private PasswordEncoder passwordEncoder;

    //AuthenticationManagerBuilder auth
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * 路由策略和访问权限的简单配置
         */
        http.csrf().disable()
                .formLogin() // 允许表单登录
                .and()
                .logout()
                .and()
                .authorizeRequests()
                .anyRequest().permitAll();
        super.configure(http);
    }
}
