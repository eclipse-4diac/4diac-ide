/**
 * Copyright (c) 2022, 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.model.eval;

import java.util.Collection;

import org.eclipse.fordiac.ide.model.eval.variable.Variable;

public interface EvaluatorMonitor {
	void info(final String message);

	void warn(final String message);

	void error(final String message);

	void error(final String message, final Throwable t);

	void update(final Collection<? extends Variable<?>> variables, final Evaluator evaluator);
}
