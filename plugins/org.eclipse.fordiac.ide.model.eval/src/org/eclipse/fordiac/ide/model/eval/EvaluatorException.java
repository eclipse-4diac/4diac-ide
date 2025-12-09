/**
 * Copyright (c) 2022, 2024 Martin Erich Jobst
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

/**
 * An exception while evaluating a model element
 */
public class EvaluatorException extends RuntimeException {
	@java.io.Serial
	private static final long serialVersionUID = -293618872722930949L;

	private final transient Evaluator evaluator;

	/**
	 * Create a new evaluator exception with the given message
	 *
	 * @param message The message
	 */
	public EvaluatorException(final String message) {
		this(message, (Evaluator) null);
	}

	/**
	 * Create a new evaluator exception with the given message and cause
	 *
	 * @param message The message
	 * @param cause   The cause
	 */
	public EvaluatorException(final String message, final Throwable cause) {
		this(message, cause, null);
	}

	/**
	 * Create a new evaluator exception with the given message and evaluator
	 *
	 * @param message   The message
	 * @param evaluator The evaluator
	 */
	public EvaluatorException(final String message, final Evaluator evaluator) {
		super(message);
		this.evaluator = evaluator;
	}

	/**
	 * Create a new evaluator exception with the given message, cause, and evaluator
	 *
	 * @param message   The message
	 * @param cause     The cause
	 * @param evaluator The evaluator
	 */
	public EvaluatorException(final String message, final Throwable cause, final Evaluator evaluator) {
		super(message, cause);
		this.evaluator = evaluator;
	}

	/**
	 * Get the originating evaluator
	 *
	 * @return The evaluator or null if unknown
	 */
	public Evaluator getEvaluator() {
		return evaluator;
	}
}
