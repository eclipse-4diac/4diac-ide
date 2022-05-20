/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;

public interface LaunchConfigurationAttributes {
	String RESOURCE = "org.eclipse.fordiac.ide.debug.resource"; //$NON-NLS-1$
	String ARGUMENTS = "org.eclipse.fordiac.ide.debug.arguments"; //$NON-NLS-1$

	static IResource getResource(final ILaunchConfiguration configuration) throws CoreException {
		final String resourceAttribute = configuration.getAttribute(RESOURCE, ""); //$NON-NLS-1$
		if (resourceAttribute != null && !resourceAttribute.isEmpty()) {
			return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(resourceAttribute));
		}
		return null;
	}

	static List<Variable<?>> getArguments(final ILaunchConfiguration configuration, final List<Variable<?>> defaultArguments)
			throws CoreException {
		final var argumentsAttribute = configuration.getAttribute(ARGUMENTS, Collections.emptyMap());
		if (argumentsAttribute != null && defaultArguments != null) {
			defaultArguments.forEach(arg -> {
				final var argumentValue = argumentsAttribute.get(arg.getName());
				try {
					if (argumentValue != null) {
						arg.setValue(argumentValue);
					}
				} catch (final Exception e) {
					// ignore
				}
			});
		}
		return defaultArguments;
	}
}
