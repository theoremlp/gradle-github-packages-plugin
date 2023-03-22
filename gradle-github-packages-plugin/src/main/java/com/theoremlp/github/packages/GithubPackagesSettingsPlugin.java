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

import org.gradle.api.Plugin;
import org.gradle.api.initialization.Settings;
import org.gradle.api.internal.plugins.DslObject;

/**
 * We use a settings plugin to ensure that the right repositories are configured for the buildscript
 * classpath in case of custom gradle plugins.
 */
public class GithubPackagesSettingsPlugin implements Plugin<Settings> {

    @Override
    public final void apply(Settings settings) {
        GithubCredentialsConfiguration config = GithubCredentialsConfiguration.fromConfig();
        GithubPackagesPlugin extension =
                settings.getExtensions().create("githubPackages", GithubPackagesPlugin.class, config);
        new DslObject(settings.getBuildscript().getRepositories())
                .getConvention()
                .getPlugins()
                .put("githubPackages", extension);
    }
}
