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
package org.eclipse.fordiac.ide.deployment.debug.ui;

import org.eclipse.fordiac.ide.debug.ui.EvaluatorDebugModelPresentation;
import org.eclipse.fordiac.ide.deployment.debug.IDeploymentDebugTarget;

public class DeploymentDebugModelPresentation extends EvaluatorDebugModelPresentation {

	@Override
	public String getText(final Object element) {
		if (element instanceof final IDeploymentDebugTarget target) {
			final StringBuilder label = new StringBuilder(target.getName());
			if (target.isTerminated()) {
				label.insert(0, Messages.DeploymentDebugModelPresentation_Terminated);
			} else if (target.isDisconnected()) {
				label.insert(0, Messages.DeploymentDebugModelPresentation_Disconnected);
			}
			return label.toString();
		}
		return super.getText(element);
	}
}
