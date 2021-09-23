package org.korov.springboot_security;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.converts.PostgreSqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import com.baomidou.mybatisplus.generator.keywords.PostgreSqlKeyWordsHandler;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.mysql.cj.jdbc.Driver;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;

/**
 * @author zhu.lei
 * @date 2021-09-06 13:34
 */
class Generator {
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
        dsc.setUrl("jdbc:mysql://localhost:3306/security?allowMultiQueries=true&jdbcCompliantTruncation=false&useAffectedRows=true&zeroDateTimeBehavior=convertToNull");
        dsc.setDriverName(Driver.class.getName());
        dsc.setUsername("security");
        dsc.setPassword("security");
        dsc.setKeyWordsHandler(new MySqlKeyWordsHandler());
        dsc.setTypeConvert(new MySqlTypeConvert());
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
        strategy.setEntityLombokModel(true);
        strategy.setInclude("sys_user");
        strategy.setEntityColumnConstant(true);
        generator.setStrategy(strategy);
        generator.execute();
    }
}
