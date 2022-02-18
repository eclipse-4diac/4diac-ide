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

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunchConfiguration;

public interface LaunchConfigurationAttributes {
	String RESOURCE = "org.eclipse.fordiac.ide.debug.resource";

	static IResource getResource(ILaunchConfiguration configuration) throws CoreException {
		final String resourceAttribute = configuration.getAttribute(RESOURCE, "");
		if (resourceAttribute != null && !resourceAttribute.isEmpty()) {
			return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(resourceAttribute));
		}
		return null;
	}
}
