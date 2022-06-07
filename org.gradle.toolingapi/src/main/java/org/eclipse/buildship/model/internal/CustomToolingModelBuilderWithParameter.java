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

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.plugins.ide.internal.tooling.eclipse.DefaultEclipseProject;
import org.gradle.tooling.BuildController;
import org.gradle.tooling.model.eclipse.EclipseProject;
import org.gradle.tooling.model.eclipse.EclipseRuntime;
import org.gradle.tooling.provider.model.ParameterizedToolingModelBuilder;

import org.eclipse.buildship.model.ExtendedEclipseModel;

public class CustomToolingModelBuilderWithParameter extends ExtendedEclipseModelBuilder implements ParameterizedToolingModelBuilder<EclipseRuntime> {

    public CustomToolingModelBuilderWithParameter(BuildController buildController) {
        super(buildController);
    }

    @Override
    public boolean canBuild(String modelName) {
        return modelName.equals(ExtendedEclipseModel.class.getName());
    }

    @Override
    public Object buildAll(String modelName, EclipseRuntime eclipseRuntime, Project modelRoot) {
        DefaultEclipseProject eclipseProject = (DefaultEclipseProject) this.buildController.getModel(EclipseProject.class, EclipseRuntime.class, new Action<EclipseRuntime>() {
            @Override
            public void execute(EclipseRuntime modelBuilderParam) {
                modelBuilderParam.setWorkspace(eclipseRuntime.getWorkspace());
            }
        });
        return build(eclipseProject, modelRoot);
    }

    @Override
    public Class<EclipseRuntime> getParameterType() {
        return EclipseRuntime.class;
    }
}
