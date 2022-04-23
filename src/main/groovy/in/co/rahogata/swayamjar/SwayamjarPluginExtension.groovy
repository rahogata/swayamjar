/**
 * 
 */
package in.co.rahogata.swayamjar

import org.gradle.api.Task

/**
 * @author Rahogatha
 *
 */
class SwayamjarPluginExtension {
	File source;
	String jvmFlagEnv = "SWAYAMJAR_JVM_FLAGS";
	File destinationDir;
	Set<String> osPlatforms = [];
	Set<Task> dependencies = [];
	
	void dependencies(Task... tasks) {
		this.dependencies = tasks;
	}
}
