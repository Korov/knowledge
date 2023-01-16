plugins {
	java
	idea
	id("org.springframework.boot") version("3.0.2-SNAPSHOT")
	id("io.spring.dependency-management") version("1.1.0")
	id("org.graalvm.buildtools.native") version ("0.9.18")
}

group = "org.korov"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
	targetCompatibility = JavaVersion.VERSION_17
}

idea {
	module {
		isDownloadSources = true
		isDownloadJavadoc = true
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
	maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	// implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	// runtimeOnly("org.postgresql:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
}

tasks.getByName<Test>("test") {
	useJUnitPlatform()
}
