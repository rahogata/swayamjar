package `in`.co.rahogata.swayamjar

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.SetProperty
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import kotlin.text.Charsets.US_ASCII

@CacheableTask
abstract class SwayamjarTask : DefaultTask() {

    @get:InputFile
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val source: RegularFileProperty

    @get:Input
    abstract val jvmFlagEnv: Property<String>

    @get:Input
    abstract val osPlatforms: SetProperty<String>

    @get:OutputDirectory
    abstract val destinationDir: DirectoryProperty

    @TaskAction
    fun generate() {
        val sourceFile = source.get().asFile
        val destName = sourceFile.name.substringBeforeLast(".")

        val platforms =
            if (osPlatforms.get().isEmpty())
                setOf(
                    if (System.getProperty("os.name").lowercase().contains("windows"))
                        "windows" else "nix"
                )
            else osPlatforms.get().map { it.lowercase() }.toSet()

        if ("nix" in platforms) {
            val out = destinationDir.file("$destName.sh").get().asFile
            out.writeText("#!/usr/bin/env sh\n\n", US_ASCII)
            out.appendText(
                "exec java \${${jvmFlagEnv.get()}} -jar \"\$0\" \"\$@\" \n\n",
                US_ASCII
            )
            out.appendBytes(sourceFile.readBytes())
            out.setExecutable(true)
        }

        if ("windows" in platforms) {
            val out = destinationDir.file("$destName.bat").get().asFile
            out.writeText("@echo off\r\n\r\n", US_ASCII)
            out.appendText(
                "java %${jvmFlagEnv.get()}% -jar %0 %*\r\n",
                US_ASCII
            )
            out.appendText("exit /b %ERRORLEVEL%\r\n\r\n", US_ASCII)
            out.appendBytes(sourceFile.readBytes())
            out.setExecutable(true)
        }
    }
}
