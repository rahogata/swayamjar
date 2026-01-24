package `in`.co.rahogata.swayamjar

import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

class SwayamjarPluginFunctionalTest {

	@TempDir
	lateinit var testProjectDir: File

	@Test
	fun `can run task`() {
		// given
		val destFile = File( "${testProjectDir}/build/out", "Main.jar")
		File("build/functionalTest/Main.jar").copyTo(
			target = File(testProjectDir, "Main.jar"),
			overwrite = true
		)

		File(testProjectDir, "settings.gradle.kts").writeText("""
			rootProject.name = "test-project"
		""".trimIndent())
		File(testProjectDir, "build.gradle.kts").writeText(
			"""
            plugins {
                id("in.co.rahogata.swayamjar")
            }
	
            tasks.register("dummy1") {
                doLast {
                    println("Dummy1")
                }
            }

            tasks.register("dummy2") {
                doLast {
                    println("Dummy2")
                }
            }

            swayamJar {
                dependencies("dummy1", "dummy2")
                source.set(layout.projectDirectory.file("Main.jar"))
                destinationDir.set(layout.buildDirectory.dir("out"))
                osPlatforms.set(setOf("nix", "WINDOWS"))
                jvmFlagEnv.set("SWAYAYMJAR_TEST_JVM_FLAGS")
            }
            """.trimIndent()
		)

		// when
		val result = GradleRunner.create()
			.withProjectDir(testProjectDir)
			.withArguments("swayamJar")
			.withPluginClasspath()
			.forwardOutput()
			.build()

		// then
		val basePath = destFile.absolutePath.substringBeforeLast('.')

		assertTrue(result.output.contains("BUILD SUCCESSFUL"))
		assertTrue(result.output.contains("Dummy1"))
		assertTrue(result.output.contains("Dummy2"))
		assertTrue(File("$basePath.bat").exists())
		assertTrue(File("$basePath.sh").exists())
		assertTrue(File("$basePath.sh").readText().contains("SWAYAYMJAR_TEST_JVM_FLAGS"))
	}

	@Test
	fun `null source`() {
		// given

		File(testProjectDir, "settings.gradle.kts").writeText("""
			rootProject.name = "test-project"
		""".trimIndent())
		File(testProjectDir, "build.gradle.kts").writeText(
			"""
            plugins {
                id("in.co.rahogata.swayamjar")
            }

            swayamJar {
                jvmFlagEnv = "SWAYAYMJAR_TEST_JVM_FLAGS"
            }
            """.trimIndent()
		)

		// when
		val result = GradleRunner.create()
			.withProjectDir(testProjectDir)
			.withArguments("swayamJar")
			.withPluginClasspath()
			.forwardOutput()
			.buildAndFail()

		// then
		assertTrue(result.output.contains("property 'source' doesn't have a configured value"), result.output)
	}
}
