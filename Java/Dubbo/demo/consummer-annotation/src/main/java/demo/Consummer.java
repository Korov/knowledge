package demo;

import demo.service.ProviderService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * xml方式启动
 */
public class Consummer {
    public static void main(String[] args) throws IOException {
        //加载xml配置文件启动
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"dubbo/config/consumer.xml"});
        context.start();
        ProviderService service = (ProviderService) context.getBean("providerService");
        String value = service.sayHello("Dubbo");
        System.out.println(value);
        System.in.read(); // 按任意键退出
    }
}
