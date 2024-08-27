/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.deployment.debug.ui.fb;

import org.eclipse.fordiac.ide.debug.ui.fb.FBLaunchConfigurationTab;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType;

public class DeploymentFBLaunchConfigurationTab extends FBLaunchConfigurationTab {

	@Override
	protected boolean filterTargetFBType(final FBType fbType) {
		return super.filterTargetFBType(fbType) || fbType instanceof ServiceInterfaceFBType;
	}
}
