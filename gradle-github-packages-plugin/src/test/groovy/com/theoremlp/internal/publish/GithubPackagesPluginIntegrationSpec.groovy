/*
 * (c) Copyright 2023 Theorem Technology, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
