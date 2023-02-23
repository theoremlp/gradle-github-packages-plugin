# Gradle GitHub Packages plugin

gradle-github-packages-plugin is a toolchain for using 
[GitHub Packages](https://github.com/features/packages) to host maven artifacts. It simplifies:

- Consuming Gradle plugins, JARs and other maven Artifacts
- Publishing JARs


## Usage

In your top level build.gradle add:

```gradle

plugins {
    id 'com.theoremlp.github-packages' version '<latestVersion>'
}

allProjects {
    apply plugin: 'com.theoremlp.github-packages'

    repositories {
        githubPackages { repository '<githubOrganization>/<githubRepository>' }
    }
}
```

Then ensure that in your local environment and in CI there is a file `~/.github/credentials.json`:
```json
{
  "username": "<userName>",
  "accessToken": "<accessToken>"
}
```

where `<accesToken>` is personal access token (classic) with at least `read:packages` scope.

## Contributing

To contribute:

- Install Java 17 - `brew tap homebrew/cask-versions && brew install --cask homebrew/cask-versions/zulu17`
- Setup `JAVA_HOME` - `export JAVA_HOME=$(/usr/libexec/java_home -v 17)`
- Install Intellij via the [Jetbrains Toolbox](https://www.jetbrains.com/toolbox-app/)
- Open the project using Intellij
