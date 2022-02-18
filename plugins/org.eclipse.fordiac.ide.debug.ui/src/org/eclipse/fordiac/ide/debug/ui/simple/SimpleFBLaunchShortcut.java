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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.fordiac.ide.debug.simple.SimpleFBLaunchConfigurationAttributes;
import org.eclipse.fordiac.ide.debug.ui.fb.FBLaunchShortcut;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;

public class SimpleFBLaunchShortcut extends FBLaunchShortcut {

	@Override
	public void launch(FBType type, ILaunchConfiguration configuration, String mode) {
		try {
			configuration.launch(mode, new NullProgressMonitor());
		} catch (CoreException e) {
		}
	}

	@Override
	public String getLaunchConfigurationId() {
		return SimpleFBLaunchConfigurationAttributes.ID;
	}
}
