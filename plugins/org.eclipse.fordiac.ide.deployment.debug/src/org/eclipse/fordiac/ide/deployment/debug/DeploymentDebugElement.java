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
package org.eclipse.fordiac.ide.deployment.debug;

import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.DebugElement;

public abstract class DeploymentDebugElement extends DebugElement implements IDeploymentDebugElement {

	public static final String MODEL_IDENTIFIER = "org.eclipse.fordiac.ide.deployment.debug.model"; //$NON-NLS-1$

	protected DeploymentDebugElement(final IDeploymentDebugTarget target) {
		super(target);
	}

	@Override
	public String getModelIdentifier() {
		return MODEL_IDENTIFIER;
	}

	@Override
	public IDeploymentDebugTarget getDebugTarget() {
		return (IDeploymentDebugTarget) super.getDebugTarget();
	}

	public static DebugException createUnsupportedOperationException() {
		return new DebugException(Status.error(Messages.DeploymentDebugTarget_UnsupportedOperation));
	}
}
