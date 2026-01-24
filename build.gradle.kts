plugins {
    alias(libs.plugins.gradle.publish)
    alias(libs.plugins.kotlinJvm)
    `kotlin-dsl`
}

version = providers.gradleProperty("artifactVersion").get()
group = "in.co.rahogata"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform(libs.junitBom))
    testImplementation(kotlin("test-junit5"))
    testImplementation(gradleTestKit())
}

gradlePlugin {
    website.set("https://github.com/rahogata/swayamjar")
    vcsUrl.set("https://github.com/rahogata/swayamjar")

    plugins {
        create("swayamjar") {
            id = "in.co.rahogata.swayamjar"
            displayName = "SwayamJar Plugin"
            description =
                "A Gradle plugin to create an executable shell/batch script and embedding JAR in it."
            tags.set(listOf("executable jar", "shell", "batch"))
            implementationClass = "in.co.rahogata.swayamjar.SwayamjarPlugin"
        }
    }
}

val functionalTest: SourceSet = sourceSets.create("functionalTest")
configurations[functionalTest.implementationConfigurationName]
    .extendsFrom(configurations["testImplementation"])
val functionalTestTask = tasks.register<Test>("functionalTest") {
    dependsOn(copyTestResources)

    testClassesDirs = functionalTest.output.classesDirs
    classpath = functionalTest.runtimeClasspath
    useJUnitPlatform()
}

gradlePlugin {
    testSourceSets(functionalTest)
}

val copyTestResources = tasks.register<Copy>("copyTestResources") {
    from(sourceSets.test.get().resources)
    into(layout.buildDirectory.dir("functionalTest"))
}

tasks.named("test") {
    dependsOn(functionalTestTask)
}

tasks.withType<Javadoc>().configureEach {
    enabled = false
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
