package in.co.rahogata.swayamjar

import org.gradle.testfixtures.ProjectBuilder
import org.gradle.api.Project
import spock.lang.Specification

/**
 * A simple unit test for the 'in.co.rahogata.swayamjar.greeting' plugin.
 */
public class SwayamjarPluginTest extends Specification {
    def "plugin registers task"() {
        given:
        def project = ProjectBuilder.builder().build()

        when:
        project.pluginManager.apply "in.co.rahogata.swayamjar"

        then:
        project.tasks.findByName("swayamJar") != null
    }
}
