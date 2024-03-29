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

dependencies {
    implementation("io.etcd:jetcd-core:0.5.11")
    implementation("org.redisson:redisson:3.16.4")
    implementation("org.apache.curator:curator-recipes:5.2.0")
    implementation("org.apache.curator:curator-framework:5.2.0")
    implementation("org.apache.curator:curator-test:5.2.0")
    implementation("org.apache.logging.log4j:log4j-api:${rootProject.ext.get("log4jVersion")}")
    implementation("org.apache.logging.log4j:log4j-core:${rootProject.ext.get("log4jVersion")}")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:${rootProject.ext.get("log4jVersion")}")
    compileOnly("org.projectlombok:lombok:${rootProject.ext.get("lombokVersion")}")
    annotationProcessor("org.projectlombok:lombok:${rootProject.ext.get("lombokVersion")}")

    testCompileOnly("org.projectlombok:lombok:${rootProject.ext.get("lombokVersion")}")
    testAnnotationProcessor("org.projectlombok:lombok:${rootProject.ext.get("lombokVersion")}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${rootProject.ext.get("junitVersion")}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}