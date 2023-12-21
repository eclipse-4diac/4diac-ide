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
package org.eclipse.fordiac.ide.gef.validation;

import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.util.Diagnostician;

public class CancelableDiagnostician extends Diagnostician {

	private final IProgressMonitor progressMonitor;

	public CancelableDiagnostician(final IProgressMonitor progressMonitor) {
		this.progressMonitor = progressMonitor;
	}

	@Override
	protected boolean doValidate(final EValidator eValidator, final EClass eClass, final EObject eObject,
			final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		if (progressMonitor.isCanceled()) {
			throw new OperationCanceledException();
		}
		return super.doValidate(eValidator, eClass, eObject, diagnostics, context);
	}

	@Override
	protected boolean doValidate(final EValidator eValidator, final EDataType eDataType, final Object value,
			final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		if (progressMonitor.isCanceled()) {
			throw new OperationCanceledException();
		}
		return super.doValidate(eValidator, eDataType, value, diagnostics, context);
	}

	@Override
	protected boolean handleThrowable(final EClass eClass, final EObject eObject, final DiagnosticChain diagnostics,
			final Map<Object, Object> context, final Throwable throwable) {
		if (throwable instanceof OperationCanceledException) {
			return false;
		}
		return super.handleThrowable(eClass, eObject, diagnostics, context, throwable);
	}
}
