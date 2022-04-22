/*
 * This Groovy source file was generated by the Gradle 'init' task.
 */
package in.co.rahogata.swayamjar

import spock.lang.Specification
import org.gradle.testkit.runner.GradleRunner

/**
 * A simple functional test for the 'in.co.rahogata.swayamjar.greeting' plugin.
 */
public class SwayamjarPluginFunctionalTest extends Specification {
    def "can run task"() {
        given:
        def projectDir = new File("build/functionalTest")
		def destFile = new File(projectDir, "Main.jar")
        projectDir.mkdirs()
        new File(projectDir, "settings.gradle").text = ""
        new File(projectDir, "build.gradle").text = """
            plugins {
                id('in.co.rahogata.swayamjar')
            }
			
			swayamJar {
				source = file('Main.jar')
				destinationDir = file('.')
				osPlatforms = ['nix', 'WINDOWS']
				jvmFlagEnv = "SWAYAYMJAR_TEST_JVM_FLAGS"
			}
        """

        when:
        def runner = GradleRunner.create()
        runner.forwardOutput()
        runner.withPluginClasspath()
		runner.withArguments("swayamJar")
        runner.withProjectDir(projectDir)
        def result = runner.build()

        then:
        result.output.contains("BUILD SUCCESSFUL") &&
		new File(destFile.absolutePath.take(destFile.absolutePath.lastIndexOf('.')) + ".bat").exists() &&
		new File(destFile.absolutePath.take(destFile.absolutePath.lastIndexOf('.')) + ".sh").exists() &&
		new File(destFile.absolutePath.take(destFile.absolutePath.lastIndexOf('.')) + ".sh").text.contains("SWAYAYMJAR_TEST_JVM_FLAGS");
    }
}