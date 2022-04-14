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
package org.eclipse.fordiac.ide.debug.ui.basic;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.debug.ui.fb.FBLaunchConfigurationTab;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;

public class BasicFBLaunchConfigurationTab extends FBLaunchConfigurationTab {

	@Override
	protected boolean filterTargetFBType(final FBType fbType) throws CoreException {
		return fbType instanceof BasicFBType;
	}
}
