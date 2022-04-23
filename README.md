# swayamjar

A Gradle plugin to create a self executable file with fat jar. This is my first Gradle plugin written with less experience.

## Usage

```gradle
plugins {
    id 'in.co.rahogata.swayamjar'
}

swayamJar {
    dependencies assemble, someTask  // Optional, define task dependencies
    source = file('${sourceFile}') // Mandatory
    destinationDir = file('${destDir}') // Optional, default is source file's parent directory.
    osPlatforms = ['nix', 'WINDOWS'] // Optional, default is build environment OS platform
    jvmFlagEnv = 'EXAMPLE_JVM_FLAGS' // Optional, default is SWAYAMJAR_JVM_FLAGS
}
```

### Plugin Configuration

Below table gives details about each configuration parameters.

| Config | Data Type | Default Value(s) | Description | Required |
|--------|-----------|------------------|-------------|----------|
| dependencies | Method: dependencies(Set&lt;Task&gt;)| empty array | A set of task dependencies. The parameters should be valid task objects. | No |
| source | File | ```null``` | JAR file that should be used to generate output. | Yes |
| destinationDir | File | Source file parent directory | Destination directory where output should be produced. The output file names has the same name as source files except extension, which would be either .sh or .bat depending on the ```osPlatforms``` configuration.| No |
| osPlatforms | Set<String> | Depend on build environment.<br/> ```windows``` for Windows<br/> ```nix``` for Linux or Mac| A set of pre-defined values to decide the type of scripts to generate. Supported values: ```nix``` and ```windows```. The ```nix``` option generate .sh and ```windows``` option generate .bat script. Multiple values can be provided. | No |
| jvmFlagEnv | String | ```SWAYAMJAR_JVM_FLAGS``` | Name of the environment variable to add in the script which helps to pass JVM flags during running the scripts. | No |


## Credits

I am thankful for the below repositories in inspiring me to write this plugin.

1. https://github.com/brianm/really-executable-jars-maven-plugin
2. https://github.com/cinchapi/jarsh
