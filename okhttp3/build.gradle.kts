plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(libs.okhttp)
    implementation(libs.okhttp.urlconnection)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}