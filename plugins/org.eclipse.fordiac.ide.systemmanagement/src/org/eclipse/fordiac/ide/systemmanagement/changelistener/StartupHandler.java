/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.changelistener;

import java.util.stream.Stream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.ui.IStartup;

public class StartupHandler implements IStartup {

	@Override
	public void earlyStartup() {
		// isAccessible -> project exists & isOpen
		Stream.of(ResourcesPlugin.getWorkspace().getRoot().getProjects()).filter(IProject::isAccessible)
				.forEach(SystemManager::validateProjectNature);
	}
}
