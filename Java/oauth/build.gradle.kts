/*
 * This file was generated by the Gradle 'init' task.
 *
 * This is a general purpose Gradle build.
 * Learn more about Gradle by exploring our samples at https://docs.gradle.org/7.0.2/samples
 */
buildscript {
    repositories {
        mavenLocal()
        maven {
            setUrl("https://maven.aliyun.com/repository/central")
        }
        maven {
            setUrl("https://maven.aliyun.com/repository/gradle-plugin")
        }
    }
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:2.4.3")
        classpath("gradle.plugin.com.github.spotbugs.snom:spotbugs-gradle-plugin:4.6.0")
        classpath("io.spring.gradle:dependency-management-plugin:1.0.11.RELEASE")
        classpath("org.springframework.boot:spring-boot-gradle-plugin:2.4.3")
        classpath("gradle.plugin.com.thinkimi.gradle:mybatis-generator-plugin:2.2")
    }
}

//所有项目共用的设置
allprojects {
    group = "org.oauth"
    version = "0.0.1-SNAPSHOT"

    // 此插件来实现类似maven中的dependencyManagement功能
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "jacoco")
    apply(plugin = "idea")
    apply(plugin = "java")
    apply(plugin = "java-library")

    //设置项目字符集为UTF-8
}