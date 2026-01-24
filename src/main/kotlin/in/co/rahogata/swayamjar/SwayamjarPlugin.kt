package `in`.co.rahogata.swayamjar

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

class SwayamjarPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val ext = project.extensions.create(
            "swayamJar",
            SwayamjarPluginExtension::class.java
        )

        project.tasks.register<SwayamjarTask>("swayamJar") {
            group = "swayamjar"
            description = "Generates an executable script embedding a JAR"

            source.set(ext.source)
            destinationDir.set(
                ext.destinationDir.orElse(
                    project.layout.buildDirectory.dir("swayamjar")
                )
            )
            jvmFlagEnv.set(ext.jvmFlagEnv)
            osPlatforms.set(ext.osPlatforms)

            dependsOn(
                ext.dependencyTaskNames.map { names ->
                    names.map { project.tasks.named(it) }
                }
            )
        }
    }
}
