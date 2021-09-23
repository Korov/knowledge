package org.korov.springboot_security;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.converts.PostgreSqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.keywords.PostgreSqlKeyWordsHandler;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;

/**
 * @author zhu.lei
 * @date 2021-09-06 13:34
 */
class Generator extends SpringbootSecurityApplicationTests {
    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Test
    void test() {
        AutoGenerator generator = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + File.separator + Joiner.on(File.separator).join(ImmutableList.of("src", "main", "java")));
        gc.setFileOverride(true);
        gc.setAuthor("korov");
        gc.setOpen(false);
        generator.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(url);
        dsc.setUsername(username);
        dsc.setPassword(password);
        dsc.setKeyWordsHandler(new PostgreSqlKeyWordsHandler());
        dsc.setTypeConvert(new PostgreSqlTypeConvert());
        generator.setDataSource(dsc);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setService(null);
        templateConfig.setServiceImpl(null);
        templateConfig.setController(null);
        templateConfig.setXml(null);
        generator.setTemplate(templateConfig);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("springboot_security");
        pc.setParent("org.korov");
        generator.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setInclude("sys_user");
        strategy.setEntityColumnConstant(true);
        generator.setStrategy(strategy);
        generator.execute();
    }
}
