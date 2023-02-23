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

import javax.inject.Inject;
import org.gradle.api.Action;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository.MetadataSources;
import org.gradle.api.artifacts.repositories.MavenRepositoryContentDescriptor;
import org.gradle.api.artifacts.repositories.RepositoryContentDescriptor;

public class DefaultGithubPackagesArtifactRepository implements GithubPackagesArtifactRepository {
    private String name;
    private String packageRepository;
    private final MavenArtifactRepository delegate;

    @Inject
    public DefaultGithubPackagesArtifactRepository(MavenArtifactRepository delegate) {
        this.delegate = delegate;
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final void setName(String name) {
        this.name = name;
    }

    @Override
    public final String getRepository() {
        return packageRepository;
    }

    @Override
    public final void setRepository(String fullName) {
        this.packageRepository = fullName;
    }

    @Override
    public final void metadataSources(Action<? super MetadataSources> configureAction) {
        delegate.metadataSources(configureAction);
    }

    @Override
    public final void content(Action<? super RepositoryContentDescriptor> configureAction) {
        delegate.content(configureAction);
    }

    @Override
    public final void mavenContent(Action<? super MavenRepositoryContentDescriptor> configureAction) {
        delegate.mavenContent(configureAction);
    }
}
