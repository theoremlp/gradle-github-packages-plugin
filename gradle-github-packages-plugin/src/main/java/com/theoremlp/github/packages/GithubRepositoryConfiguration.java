/*
 * (c) Copyright 2023 Theorem Lp. All rights reserved.
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
