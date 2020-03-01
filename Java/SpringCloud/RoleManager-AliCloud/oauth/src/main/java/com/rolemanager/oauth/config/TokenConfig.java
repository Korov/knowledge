package com.rolemanager.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
public class TokenConfig {
    // 设置令牌的存储策略
    @Bean
    public TokenStore tokenStore() {
        // 使用内存存储
        return new InMemoryTokenStore();
    }
}
