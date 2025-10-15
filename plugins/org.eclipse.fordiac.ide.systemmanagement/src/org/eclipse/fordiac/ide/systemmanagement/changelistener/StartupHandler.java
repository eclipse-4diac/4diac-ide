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

import java.text.MessageFormat;
import java.util.stream.Stream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.systemmanagement.Messages;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.systemmanagement.nature.FordiacNature;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.ui.IStartup;

public class StartupHandler implements IStartup {

	@Override
	public void earlyStartup() {
		Stream.of(ResourcesPlugin.getWorkspace().getRoot().getProjects()).filter(IProject::isOpen)
				.forEach(StartupHandler::validateProjectNature);
	}

	private static void validateProjectNature(final IProject project) {
		try {
			if (project.getNature(SystemManager.FORDIAC_PROJECT_NATURE_ID) instanceof final FordiacNature nature) {
				nature.validate();
			}
		} catch (final CoreException e) {
			FordiacLogHelper.logError(MessageFormat
					.format(Messages.FordiacResourceChangeListener_ErrorLoadingProjectNature, e.getMessage()), e);
		}
	}

}
