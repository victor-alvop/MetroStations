plugins {
    id("java")
    application
}

group = "com.miempresa"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass =  "com.miempresa.Main"
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}