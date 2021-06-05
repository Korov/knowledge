plugins {

}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

spotbugs {
    showProgress.set(true)
    ignoreFailures.set(true)
    tasks.spotbugsMain {
        reports.create("html") {
            isEnabled = true
            destination = file("$buildDir/reports/spotbugs/main/spotbugs.html")
            setStylesheet("fancy-hist.xsl")
        }
    }

    tasks.spotbugsTest {
        reports.create("html") {
            isEnabled = true
            destination = file("$buildDir/reports/spotbugs/test/spotbugs.html")
            setStylesheet("fancy-hist.xsl")
        }
    }
}

tasks.test {
    useJUnitPlatform {
        includeEngines("junit-jupiter")
    }
    finalizedBy(tasks.jacocoTestReport)  // report is always generated after tests run
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)  // tests are required to run before generating the report
}

dependencies {
    compileOnly("org.springframework.boot:spring-boot-starter-logging")
    compileOnly("com.squareup.okhttp3:okhttp")
    compileOnly("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}