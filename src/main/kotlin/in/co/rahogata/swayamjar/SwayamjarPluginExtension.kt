package `in`.co.rahogata.swayamjar

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.SetProperty
import org.gradle.api.provider.Property
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.file.DirectoryProperty
import javax.inject.Inject

abstract class SwayamjarPluginExtension @Inject constructor(
		objects: ObjectFactory
) {

	/** Input JAR */
	val source: RegularFileProperty =
			objects.fileProperty()

	/** Environment variable for JVM flags */
	val jvmFlagEnv: Property<String> =
			objects.property(String::class.java)
					.convention("SWAYAMJAR_JVM_FLAGS")

	/** Output directory */
	val destinationDir: DirectoryProperty =
			objects.directoryProperty()

	/** Target OS platforms */
	val osPlatforms: SetProperty<String> =
			objects.setProperty(String::class.java)
					.convention(emptySet())

	/** Task names this plugin task depends on */
	val dependencyTaskNames: SetProperty<String> =
			objects.setProperty(String::class.java)
					.convention(emptySet())

	fun dependencies(vararg taskNames: String) {
		dependencyTaskNames.set(taskNames.toSet())
	}
}
