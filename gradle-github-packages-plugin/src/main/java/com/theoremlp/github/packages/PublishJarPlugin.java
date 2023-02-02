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

import java.util.Collections;
import java.util.stream.Stream;
import nebula.plugin.info.scm.ScmInfoPlugin;
import nebula.plugin.publishing.maven.MavenBasePublishPlugin;
import nebula.plugin.publishing.maven.MavenManifestPlugin;
import nebula.plugin.publishing.maven.MavenScmPlugin;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.repositories.ArtifactRepository;
import org.gradle.api.plugins.JavaLibraryPlugin;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.publish.PublishingExtension;
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin;
import org.gradle.api.publish.tasks.GenerateModuleMetadata;
import org.gradle.jvm.tasks.Jar;

public final class PublishJarPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.getPluginManager().apply(JavaLibraryPlugin.class);
        applyPublishingPlugins(project);

        ArtifactRepository githubRepo = project.getRepositories().getByName(MavenRepository.REPOSITORY_NAME);
        PublishingExtension publishing = project.getExtensions().getByType(PublishingExtension.class);
        publishing.getRepositories().add(githubRepo);

        configureJars(project);
        disableModuleMetadata(project);
    }

    private static void configureJars(Project project) {
        project.getTasks().withType(Jar.class).named("jar").configure(jar -> {
            jar.getManifest().attributes(Collections.singletonMap("Implementation-Version", project.getVersion()));
        });

        JavaPluginExtension javaPluginExtension = project.getExtensions().getByType(JavaPluginExtension.class);
        javaPluginExtension.withJavadocJar();
        javaPluginExtension.withSourcesJar();
    }

    private static void disableModuleMetadata(Project project) {
        project.getTasks()
                .withType(GenerateModuleMetadata.class)
                .configureEach(generateModuleMetadata -> generateModuleMetadata.setEnabled(false));
    }

    private static void applyPublishingPlugins(Project project) {
        // Intentionally not applying nebula.maven-publish, but most of its constituent plugins,
        // because we do _not_ want nebula.maven-compile-only
        Stream.of(
                        MavenPublishPlugin.class,
                        MavenBasePublishPlugin.class,
                        MavenManifestPlugin.class,
                        MavenScmPlugin.class,
                        ScmInfoPlugin.class)
                .forEach(plugin -> project.getPluginManager().apply(plugin));
    }
}
