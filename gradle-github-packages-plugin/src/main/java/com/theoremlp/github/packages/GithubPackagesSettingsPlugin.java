/*
 * (c) Copyright 2023 Theorem Lp. All rights reserved.
 */

package com.theoremlp.github.packages;

import org.gradle.api.Plugin;
import org.gradle.api.initialization.Settings;

/**
 * We use a settings plugin to ensure that the right repositories are configured for the buildscript
 * classpath in case of custom gradle plugins.
 */
public final class GithubPackagesSettingsPlugin implements Plugin<Settings> {

    @Override
    public void apply(Settings settings) {
        GithubRepositoryConfiguration config = GithubRepositoryConfiguration.fromConfig();
        MavenRepository.registerRepository(settings.getBuildscript().getRepositories(), config);
    }
}
