/*
 * (c) Copyright 2023 Theorem Lp. All rights reserved.
 */

package com.theoremlp.github.packages;

import org.gradle.api.artifacts.dsl.RepositoryHandler;

final class MavenRepository {
    private static final String GITHUB_MAVEN = "https://maven.pkg.github.com/";
    public static final String REPOSITORY_NAME = "Github";

    static void registerRepository(RepositoryHandler repositories, GithubRepositoryConfiguration config) {
        repositories.maven(repo -> {
            repo.setName(REPOSITORY_NAME);
            repo.setUrl(GITHUB_MAVEN + config.organization() + "/" + config.repo());
            repo.credentials(creds -> {
                creds.setUsername(config.username());
                creds.setPassword(creds.getPassword());
            });
        });
    }

    private MavenRepository() {}
}
