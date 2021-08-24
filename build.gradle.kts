plugins {
    java
}

val javacordVersion : String by project

group = "net.netherald"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    implementation("org.javacord:javacord:$javacordVersion")
    
    testRuntimeOnly("mysql:mysql-connector-java:8.0.26")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}