package `in`.co.rahogata.swayamjar

import org.gradle.testfixtures.ProjectBuilder
import org.gradle.api.Project
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull

/**
 * A simple unit test for the 'in.co.rahogata.swayamjar' plugin.
 */
class SwayamjarPluginTest {

    @Test
    fun `plugin registers swayamJar task`() {
        // given
        val project: Project = ProjectBuilder.builder().build()

        // when
        project.pluginManager.apply("in.co.rahogata.swayamjar")

        // then
        val task = project.tasks.findByName("swayamJar")
        assertNotNull(task, "Expected swayamJar task to be registered")
    }
}
