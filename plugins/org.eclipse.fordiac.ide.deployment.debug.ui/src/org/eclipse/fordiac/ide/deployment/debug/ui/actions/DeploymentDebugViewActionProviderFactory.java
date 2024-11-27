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
package org.eclipse.fordiac.ide.deployment.debug.ui.actions;

import org.eclipse.core.runtime.AdapterTypes;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IViewActionProvider;
import org.eclipse.fordiac.ide.debug.ui.actions.EvaluatorDebugViewActionProvider;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentDebugStackFrame;

@SuppressWarnings("restriction")
@AdapterTypes(adaptableClass = { DeploymentDebugStackFrame.class }, adapterNames = { IViewActionProvider.class })
public class DeploymentDebugViewActionProviderFactory implements IAdapterFactory {

	@Override
	public <T> T getAdapter(final Object adaptableObject, final Class<T> adapterType) {
		if (adaptableObject instanceof final DeploymentDebugStackFrame stackFrame
				&& adapterType == IViewActionProvider.class) {
			return adapterType.cast(new EvaluatorDebugViewActionProvider(stackFrame));
		}
		return null;
	}
}
