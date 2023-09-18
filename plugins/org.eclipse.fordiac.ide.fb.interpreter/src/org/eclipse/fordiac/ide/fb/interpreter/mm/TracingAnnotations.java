/*******************************************************************************
 * Copyright (c) 2023 Fabio Gandolfi
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.fb.interpreter.mm;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EccTrace;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.TransitionTrace;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;

public class TracingAnnotations {

	public static EList<ECTransition> getTransitions(final ECC ecc, final EccTrace traces) {
		final EList<ECTransition> transitions = new BasicEList<>();
		for (final TransitionTrace trace : traces.getTransitionTraces()) {
			transitions.addAll(ecc.getECTransition().stream()
					.filter(s -> s.getSource().getName().equals(trace.getSourceState())
							&& s.getDestination().getName().equals(trace.getDestinationState())
							&& s.getConditionExpression().equals(trace.getCondExpression()))
					.toList());
		}
		return transitions;
	}

	private TracingAnnotations() {
		throw new UnsupportedOperationException("do not instantiate"); //$NON-NLS-1$
	}
}
