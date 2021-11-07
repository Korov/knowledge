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
    implementation("io.etcd:jetcd-core:0.5.0")
    implementation("org.redisson:redisson:3.16.3")
    implementation("org.apache.curator:curator-recipes:5.2.0")
    implementation("org.apache.curator:curator-framework:5.2.0")
    implementation("org.apache.curator:curator-test:5.2.0")
    implementation("org.apache.logging.log4j:log4j-api:2.14.1")
    implementation("org.apache.logging.log4j:log4j-core:2.14.1")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.14.1")
    compileOnly("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.22")

    testCompileOnly("org.projectlombok:lombok:${rootProject.ext.get("lombokVersion")}")
    testAnnotationProcessor("org.projectlombok:lombok:${rootProject.ext.get("lombokVersion")}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${rootProject.ext.get("junitVersion")}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}