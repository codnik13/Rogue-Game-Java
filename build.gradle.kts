plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.2"
    id("application")
}

group = "org.rogue"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.baulsupp.kolja:jcurses:0.9.5.3")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.google.code.gson:gson:2.12.1")
}
application {
    mainClass.set("org.rogue.Main")
}
tasks.test {
    useJUnitPlatform()
}