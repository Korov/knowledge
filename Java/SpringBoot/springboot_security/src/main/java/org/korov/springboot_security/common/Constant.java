package org.korov.springboot_security.common;

/**
 * @author zhu.lei
 * @date 2021-09-23 16:35
 */
public class Constant {
    /**
     * 不需要认证的静态资源
     */
    public static final String WEB_ACT_MATCHERS= "/favicon.ico";
    /**
     * 不需要认证的接口资源
     */
    public static final String HTTP_ACT_MATCHERS="/webjars/springfox-swagger-ui/fonts/**,/swagger-resources,/swagger-resources/configuration/**,/webjars**,/swagger-ui.html,/webjars/springfox-swagger-ui/**,/v2/api-docs,/api/login,/api/captcha/create";
    public static final String UTF8 = "UTF-8";
    public static final String LOGIN_URL = "/api/login";
}
