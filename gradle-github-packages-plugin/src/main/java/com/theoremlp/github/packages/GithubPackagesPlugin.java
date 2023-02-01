/*
 * (c) Copyright 2023 Theorem Lp. All rights reserved.
 */

package com.theoremlp.github.packages;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public final class GithubPackagesPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        GithubRepositoryConfiguration config = GithubRepositoryConfiguration.fromConfig();
        project.allprojects(subProj -> {
            MavenRepository.registerRepository(subProj.getRepositories(), config);
        });
    }
}
