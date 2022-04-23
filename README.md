# swayamjar

A Gradle plugin to create a self executable file with fat jar. This is my first Gradle plugin written with less experience.

## Usage

```gradle
plugins {
    id 'in.co.rahogata.swayamjar'
}

swayamJar {
    source = file('${sourceFile}') // Mandatory
    destinationDir = file('${destDir}') // Optional, default is source file's parent directory.
    osPlatforms = ['nix', 'WINDOWS'] // Optional, default is build environment OS platform
    jvmFlagEnv = 'EXAMPLE_JVM_FLAGS' // Optional, default is SWAYAMJAR_JVM_FLAGS
}
```

## Credits

I am thankful for the below repositories in inspiring me to write this plugin.

1. https://github.com/brianm/really-executable-jars-maven-plugin
2. https://github.com/cinchapi/jarsh
