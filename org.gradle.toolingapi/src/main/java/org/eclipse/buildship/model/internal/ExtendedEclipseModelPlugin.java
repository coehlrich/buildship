/*******************************************************************************
 * Copyright (c) 2022 Gradle Inc.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.buildship.model.internal;


import javax.inject.Inject;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.tooling.BuildController;
import org.gradle.tooling.provider.model.ToolingModelBuilderRegistry;
import org.gradle.util.GradleVersion;

public class ExtendedEclipseModelPlugin implements Plugin<Project> {
    private final ToolingModelBuilderRegistry registry;
    private final BuildController buildController;

    @Inject
    public ExtendedEclipseModelPlugin(ToolingModelBuilderRegistry registry, BuildController buildController) {
        this.registry = registry;
        this.buildController = buildController;
    }

    @Override
    public void apply(Project project) {
        if (GradleVersion.current().compareTo(GradleVersion.version("4.4")) < 0) {
            this.registry.register(new ExtendedEclipseModelBuilder(this.buildController));
        } else {
            this.registry.register(new CustomToolingModelBuilderWithParameter(this.buildController));
        }
    }
}
