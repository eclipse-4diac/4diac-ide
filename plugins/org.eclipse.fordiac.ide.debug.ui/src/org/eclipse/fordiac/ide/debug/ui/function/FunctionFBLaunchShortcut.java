/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.debug.ui.function;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.fordiac.ide.debug.function.FunctionFBLaunchConfigurationAttributes;
import org.eclipse.fordiac.ide.debug.ui.fb.FBLaunchShortcut;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class FunctionFBLaunchShortcut extends FBLaunchShortcut {

	@Override
	public void launch(final FBType type, final ILaunchConfiguration configuration, final String mode) {
		try {
			configuration.launch(mode, new NullProgressMonitor());
		} catch (final CoreException e) {
			FordiacLogHelper.logError(String.format("Could not launch type %s with mode %s", type.getName(), mode), e); //$NON-NLS-1$
		}
	}

	@Override
	public String getLaunchConfigurationId() {
		return FunctionFBLaunchConfigurationAttributes.ID;
	}
}
