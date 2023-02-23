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

import java.util.Optional;
import javax.inject.Inject;
import org.gradle.api.Action;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;
import org.gradle.api.internal.artifacts.BaseRepositoryFactory;
import org.gradle.api.model.ObjectFactory;

public class GithubPackagesExtension {
    private static final String GITHUB_MAVEN = "https://maven.pkg.github.com/";
    static final String GITHUB_REPO_DEFAULT_NAME = "githubPackages";

    private final GithubCredentialsConfiguration config;
    private final ObjectFactory objectFactory;
    private final RepositoryHandler repositories;
    private final BaseRepositoryFactory repositoryFactory;

    @Inject
    public GithubPackagesExtension(
            GithubCredentialsConfiguration config,
            ObjectFactory objectFactory,
            RepositoryHandler repositories,
            BaseRepositoryFactory repositoryFactory) {
        this.config = config;
        this.objectFactory = objectFactory;
        this.repositories = repositories;
        this.repositoryFactory = repositoryFactory;
    }

    public final GithubPackagesArtifactRepository githubPackages(
            Action<? super GithubPackagesArtifactRepository> action) {
        MavenArtifactRepository mavenRepository = repositoryFactory.createMavenRepository();

        GithubPackagesArtifactRepository githubRepository =
                objectFactory.newInstance(DefaultGithubPackagesArtifactRepository.class, mavenRepository);
        action.execute(githubRepository);

        String suggestedName = Optional.ofNullable(githubRepository.getName()).orElse(GITHUB_REPO_DEFAULT_NAME);
        mavenRepository.setName(suggestedName);
        mavenRepository.setUrl(GITHUB_MAVEN + githubRepository.getRepository());
        mavenRepository.credentials(creds -> {
            creds.setUsername(config.username());
            creds.setPassword(creds.getPassword());
        });

        repositories.addLast(mavenRepository);
        return githubRepository;
    }
}
