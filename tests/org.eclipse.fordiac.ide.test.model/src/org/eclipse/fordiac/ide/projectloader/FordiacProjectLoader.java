/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Lukas Wais - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.projectloader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;

public class FordiacProjectLoader extends EclipseProjectLoader {
	private final AutomationSystem automationSystem;

	public FordiacProjectLoader(final String projectFilename) throws CoreException {
		super(projectFilename);
		automationSystem = getAutomationSystem();
		System.out.println(automationSystem);
	}

	public AutomationSystem getAutomationSystem() {
		return SystemManager.INSTANCE.getSystem(getSysFile());
	}

	private IFile getSysFile() {
		return getEclipseProject().getFile(getProjectname() + ".sys"); //$NON-NLS-1$
	}
}
