/*******************************************************************************
 * Copyright (c) 2013, 2015 fortiss GmbH
 * 				 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   			 - New Project Explorer layout
 *******************************************************************************/
package org.eclipse.fordiac.ide.util;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.ui.IElementFactory;
import org.eclipse.ui.IMemento;

public abstract class AbstractUntypedEditorInputFactory implements IElementFactory {

	/**
	 * Tag for the automation system name.
	 */
	private static final String TAG_AUTOMATION_SYSTEM = "SYSTEM"; //$NON-NLS-1$
	private static final String TAG_PROJECT = "PROJECT"; //$NON-NLS-1$

	protected AbstractUntypedEditorInputFactory() {
		// protected default constructor avoiding to create an instance of this class
	}

	protected static void saveAutomationSystem(final IMemento memento, final AutomationSystem system) {
		if (null != system) {
			memento.putString(TAG_AUTOMATION_SYSTEM,
					system.getTypeEntry().getFile().getProjectRelativePath().toString());
			memento.putString(TAG_PROJECT, system.getTypeEntry().getFile().getProject().getName());
		}
	}

	protected static AutomationSystem loadAutomationSystemName(final IMemento memento) {
		final String projectName = memento.getString(TAG_PROJECT);
		final String systemPath = memento.getString(TAG_AUTOMATION_SYSTEM);

		final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		if (null != project) {
			final IFile file = project.getFile(systemPath);
			return SystemManager.INSTANCE.getSystem(file);
		}
		return null;
	}

}
