package com.rolemanager.user.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public UserDetailsServiceImpl userDetailsService;

    /**
     * 使用BCryptPasswordEncoder作为密码的加密解密
     *
     * @return
     */
    @Bean(name = "bCryptPasswordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    @Qualifier(value = "bCryptPasswordEncoder")
    private PasswordEncoder passwordEncoder;

    //AuthenticationManagerBuilder auth
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * 启用内存用户存储来验证用户信息
         * 添加了两个用户，user和admin，密码均为password，user用户拥有USER角色，admin拥有USER,ADMIN两个角色
         * 使用and()可以将两个用户的配置连接起来
         *
         * roles("USER")实际角色为ROLE_USER,会被添加一个前缀
         */
        /*auth.inMemoryAuthentication().withUser("user").password("password").roles("USER").and()
                .withUser("admin").password("password").authorities("ROLE_USER", "ROLE_ADMIN"); // 这两个方法效果相等*/

        /**
         * 路由策略和访问权限的简单配置
         */
        http.csrf().disable()
                .formLogin() // 允许表单登录
                .and()
                .authorizeRequests()
                .antMatchers("/service/user/login").permitAll()    // 定义哪些URL需要被保护、哪些不需要被保护
                .anyRequest().permitAll(); //permitAll() 登录页面全部权限可访问
        super.configure(http);
    }

    /**
     * 配置内存用户
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        /*auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("user").password(new BCryptPasswordEncoder().encode("123456")).roles("USER")
        .and()
        .passwordEncoder(new BCryptPasswordEncoder()).withUser("admin").password(new BCryptPasswordEncoder().encode("123456")).authorities("ROLE_USER", "ROLE_ADMIN");*/

        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
}
