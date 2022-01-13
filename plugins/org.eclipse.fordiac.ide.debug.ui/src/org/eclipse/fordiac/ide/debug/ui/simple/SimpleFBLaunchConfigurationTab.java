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
package org.eclipse.fordiac.ide.debug.ui.simple;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.debug.ui.MainLaunchConfigurationTab;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public class SimpleFBLaunchConfigurationTab extends MainLaunchConfigurationTab {

	@Override
	protected boolean filterTargetResource(IResource resource) throws CoreException {
		if (resource instanceof IFile) {
			return resource.getFileExtension().equals("fbt")
					&& TypeLibrary.getPaletteEntryForFile((IFile) resource).getType() instanceof SimpleFBType;
		} else if (resource instanceof IContainer) {
			for (IResource child : ((IContainer) resource).members()) {
				if (filterTargetResource(child)) {
					return true;
				}
			}
			return false;
		}
		return true;
	}
}
