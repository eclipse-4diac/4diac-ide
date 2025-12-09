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
package org.eclipse.fordiac.ide.debug.ui;

import org.eclipse.core.runtime.AdapterTypes;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.debug.ui.actions.IWatchExpressionFactoryAdapter;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugVariable;

@AdapterTypes(adaptableClass = EvaluatorDebugVariable.class, adapterNames = { IWatchExpressionFactoryAdapter.class })
public class EvaluatorWatchExpressionFactory implements IAdapterFactory {

	@Override
	public <T> T getAdapter(final Object adaptableObject, final Class<T> adapterType) {
		if (adaptableObject instanceof EvaluatorDebugVariable && adapterType == IWatchExpressionFactoryAdapter.class) {
			return adapterType.cast(EvaluatorWatchExpressionFactoryAdapter.INSTANCE);
		}
		return null;
	}
}
