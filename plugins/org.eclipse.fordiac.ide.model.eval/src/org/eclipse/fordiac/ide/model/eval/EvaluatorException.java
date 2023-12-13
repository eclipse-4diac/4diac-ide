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

public class EvaluatorException extends Exception {
	@java.io.Serial
	private static final long serialVersionUID = -293618872722930949L;

	private final transient Evaluator evaluator;

	public EvaluatorException(final Evaluator evaluator) {
		this(null, null, evaluator);
	}

	public EvaluatorException(final String message, final Evaluator evaluator) {
		this(message, null, evaluator);
	}

	public EvaluatorException(final String message, final Throwable cause, final Evaluator evaluator) {
		super(message, cause);
		this.evaluator = evaluator;
	}

	public Evaluator getEvaluator() {
		return evaluator;
	}
}
