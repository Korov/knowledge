plugins {
    id("org.springframework.boot") version("2.7.3")
    id("io.spring.dependency-management") version("1.0.13.RELEASE")
    id("org.springframework.experimental.aot") version("0.12.1")
    id("java")
}

group = "org.korov"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/release") }
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootBuildImage>("bootBuildImage") {
    builder = "paketobuildpacks/builder:tiny"
    environment = mapOf(
        "BP_NATIVE_IMAGE" to "true"
    )
}

tasks.getByName<org.springframework.boot.gradle.tasks.run.BootRun>("bootRun") {
    systemProperty("springAot", "true")
}

tasks.create("printTaskBefore") {
    println("print task demo before")
}

tasks.create("printTask") {
    dependsOn(":printTaskBefore")
    println("print task demo")
}

tasks.create("printTaskAfter") {
    dependsOn(":printTask")
    println("print task demo after")
}
