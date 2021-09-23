package org.korov.springboot_security.util;

import org.korov.springboot_security.common.Constant;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet 工具类
 * <p>
 * Author: 就 眠 仪 式
 * CreateTime: 2021/2/3
 */
public class ServletUtil {
    /**
     * Response 对象写出 JSON 数据
     *
     * @param: object 消息数据
     */
    public static void writeJson(Object data) throws IOException {
        write(JSONUtil.objectToJson(data));
    }

    /**
     * Response 对象写出数据
     *
     * @param msg 消息数据
     */
    public static void write(String msg) throws IOException {
        HttpServletResponse response = getResponse();
        response.setHeader("Content-type", "application/json;charset=" + Constant.UTF8);
        response.setCharacterEncoding(Constant.UTF8);
        response.getWriter().write(msg);
    }

    /**
     * 获取 HttpServletResponse 对象
     *
     * @return {@link HttpServletResponse}
     */
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return servletRequestAttributes.getResponse();
    }
}
