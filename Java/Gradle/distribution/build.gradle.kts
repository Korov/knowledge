plugins {
    java
}

group = "org.korov"
version = "1.0-SNAPSHOT"

ext {
    set("log4jVersion", "2.14.1")
    set("lombokVersion", "1.18.22")
    set("junitVersion", "5.8.1")
    set("springBootVersion", "2.5.6")
}

repositories {
    mavenLocal()
    maven {
        setUrl("https://maven.aliyun.com/repository/central")
    }
    maven {
        setUrl("https://maven.aliyun.com/repository/gradle-plugin")
    }
}



allprojects {
    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    tasks.withType<Test> {
        systemProperty("file.encoding", "UTF-8")
    }

    tasks.withType<Javadoc> {
        options.encoding = "UTF-8"
    }

}
