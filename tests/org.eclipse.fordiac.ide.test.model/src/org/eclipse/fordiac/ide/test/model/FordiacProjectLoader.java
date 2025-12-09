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

package org.eclipse.fordiac.ide.test.model;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.osgi.framework.Bundle;

public class FordiacProjectLoader extends EclipseProjectLoader {
	public FordiacProjectLoader(final Bundle bundle, final Path projectPath) throws CoreException, IOException {
		super(bundle, projectPath);
	}

	public AutomationSystem getAutomationSystem(final String name) {
		return (AutomationSystem) TypeLibraryManager.INSTANCE.getTypeEntryForFile(getSysFile(name)).getType();
	}

	private IFile getSysFile(final String systemName) {
		return getEclipseProject().getFile(systemName + ".sys"); //$NON-NLS-1$
	}

}
