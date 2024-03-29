plugins {
    java
}

group = "org.korov"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    maven {
        setUrl("https://maven.aliyun.com/repository/central")
    }
    maven {
        setUrl("https://maven.aliyun.com/repository/gradle-plugin")
    }
}

configurations {
    implementation.get().exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    // implementation.get().exclude(group = "org.springframework.boot", module = "spring-boot-starter")
}

dependencies {
    /*implementation("com.alibaba.boot:nacos-config-spring-boot-starter:0.2.10") {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter")
    }
    implementation("com.alibaba.boot:nacos-discovery-spring-boot-starter:0.2.10") {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter")
    }*/
    implementation("org.springframework.boot:spring-boot-starter-web:${rootProject.ext.get("springBootVersion")}")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:2.2.0") {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter")
        exclude(group = "org.mybatis.spring.boot", module = "mybatis-spring-boot-autoconfigure")
    }
    implementation("mysql:mysql-connector-java:8.0.27")
    implementation("io.seata:seata-spring-boot-starter:1.4.2")
    implementation("org.springframework.boot:spring-boot-starter-log4j2:${rootProject.ext.get("springBootVersion")}")
    compileOnly("org.projectlombok:lombok:${rootProject.ext.get("lombokVersion")}")
    annotationProcessor("org.projectlombok:lombok:${rootProject.ext.get("lombokVersion")}")

    testImplementation("org.springframework.boot:spring-boot-starter-test:${rootProject.ext.get("springBootVersion")}")
    testCompileOnly("org.projectlombok:lombok:${rootProject.ext.get("lombokVersion")}")
    testAnnotationProcessor("org.projectlombok:lombok:${rootProject.ext.get("lombokVersion")}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${rootProject.ext.get("junitVersion")}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}