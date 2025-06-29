plugins {
    kotlin("jvm")
    id("io.johnsonlee.sonatype-publish-plugin")
}

dependencies {
    implementation(project(":io"))
    implementation(kotlin("stdlib"))
    implementation(libs.okhttp)
    implementation(libs.okhttp.urlconnection)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}