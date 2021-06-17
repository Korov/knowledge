plugins {
    id("java")
    id("idea")
    id("jacoco")
    id("groovy")
}

group = "org.designpatterns"
version = "1.0-SNAPSHOT"

dependencies {
    compileOnly("org.codehaus.groovy:groovy:3.0.7")
    implementation("org.apache.logging.log4j:log4j-api:2.14.1")
    implementation("org.apache.logging.log4j:log4j-core:2.14.1")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.14.1")
    testImplementation(platform("org.junit:junit-bom:5.7.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testCompileOnly(platform("org.spockframework:spock-bom:2.0-M4-groovy-3.0"))
    testCompileOnly("org.spockframework:spock-core")
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://maven.aliyun.com/repository/central")
    }
    maven {
        url = uri("https://maven.aliyun.com/repository/gradle-plugin")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

tasks.test {
    useJUnitPlatform {
        includeEngines("junit-jupiter")
    }
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
}