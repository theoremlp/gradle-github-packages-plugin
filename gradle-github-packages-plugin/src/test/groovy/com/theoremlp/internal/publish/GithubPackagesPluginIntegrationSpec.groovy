/*
 * (c) Copyright 2023 Theorem Lp. All rights reserved.
 */

package com.theoremlp.internal.publish

import com.theoremlp.github.packages.GithubPackagesPlugin
import nebula.test.IntegrationSpec

class GithubPackagesPluginIntegrationSpec extends IntegrationSpec{

    def setup() {
        buildFile << """
        ${applyPlugin(GithubPackagesPlugin)}
        """.stripIndent()
    }

    def 'missing configuration file fails'() {
        when:
        def missingFile = new File(projectDir, "missing.json")
        def result = runTasks("-DGITHUB_REPOSITORY_CONFIG=" + missingFile.getAbsolutePath())

        then:
        result.failure
        result.standardError.contains("Missing configuration file")
    }

    def 'present configuration file applies successfully'() {
        when:
        def configFile = file("config.json")
        configFile << """{
        "organization": "theoremlp",
        "repo": "gradle-github-packages-plugin",
        "username": "theoremlp",
        "accessToken": "fake-access-token"
        }
        """.stripIndent()

        def result = runTasks("-DGITHUB_REPOSITORY_CONFIG=" + configFile.getAbsolutePath())

        then:
        result.success
    }
}
