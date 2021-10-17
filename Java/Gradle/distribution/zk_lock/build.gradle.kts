plugins {
    java
}

group = "org.korov"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.curator:curator-recipes:5.2.0")
    implementation("org.apache.curator:curator-framework:5.2.0")
    implementation("org.apache.curator:curator-test:5.2.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}