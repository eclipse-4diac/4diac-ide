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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.debug.ui.actions.IWatchExpressionFactoryAdapterExtension;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugVariable;

public class EvaluatorWatchExpressionFactoryAdapter implements IWatchExpressionFactoryAdapterExtension {

	public static final EvaluatorWatchExpressionFactoryAdapter INSTANCE = new EvaluatorWatchExpressionFactoryAdapter();

	private EvaluatorWatchExpressionFactoryAdapter() {
	}

	@Override
	public String createWatchExpression(final IVariable variable) throws CoreException {
		if (variable instanceof final EvaluatorDebugVariable evaluatorDebugVariable
				&& evaluatorDebugVariable.getExpression() != null) {
			return evaluatorDebugVariable.getExpression();
		}
		throw new CoreException(Status.error(Messages.EvaluatorWatchExpressionFactoryAdapter_NoExpressionForVariable));
	}

	@Override
	public boolean canCreateWatchExpression(final IVariable variable) {
		return variable instanceof final EvaluatorDebugVariable evaluatorDebugVariable
				&& evaluatorDebugVariable.getExpression() != null;
	}
}
