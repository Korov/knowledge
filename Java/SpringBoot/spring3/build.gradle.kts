plugins {
	id("org.springframework.boot") version("3.0.0-M4")
	id("io.spring.dependency-management") version("1.0.13.RELEASE")
	id("java")
	id("idea")
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
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	runtimeOnly("org.postgresql:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.getByName<Test>("test") {
	useJUnitPlatform()
}
