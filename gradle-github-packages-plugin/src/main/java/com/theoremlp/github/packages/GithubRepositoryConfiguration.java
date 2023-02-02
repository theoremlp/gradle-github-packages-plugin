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

package com.theoremlp.github.packages;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

record GithubRepositoryConfiguration(String organization, String repo, String username, String accessToken) {
    static final Path GITHUB_REPOSITORY = Path.of("~/.github/repository.json");

    static GithubRepositoryConfiguration fromConfig() {
        Path configFile = Optional.ofNullable(System.getProperty("GITHUB_REPOSITORY_CONFIG"))
                .map(Path::of)
                .orElse(GITHUB_REPOSITORY);

        if (!Files.exists(configFile)) {
            throw new IllegalStateException("Missing configuration file " + configFile);
        }

        try (BufferedReader bufferedReader = Files.newBufferedReader(configFile)) {
            return Mappers.MAPPER.readValue(bufferedReader, GithubRepositoryConfiguration.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load repository config", e);
        }
    }
}
