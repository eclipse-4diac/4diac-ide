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
package org.eclipse.fordiac.ide.model.eval;

import java.text.MessageFormat;

import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

/**
 * An exception while evaluating an initializer for a particular element
 */
public class EvaluatorInitializerException extends EvaluatorException {

	private static final long serialVersionUID = 324858778456696289L;

	private final transient INamedElement element;

	/**
	 * Create a new evaluator initializer exception
	 *
	 * @param message The message
	 * @param element The element where the exception originated
	 */
	public EvaluatorInitializerException(final String message, final INamedElement element) {
		super(message);
		this.element = element;
	}

	/**
	 * Create a new evaluator initializer exception
	 *
	 * @param message The message
	 * @param element The element where the exception originated
	 * @param cause   The cause
	 */
	public EvaluatorInitializerException(final String message, final INamedElement element, final Throwable cause) {
		super(message, cause);
		this.element = element;
	}

	/**
	 * Create a new evaluator initializer exception with a default message
	 *
	 * @param element The element where the exception originated
	 * @param cause   The cause
	 */
	public EvaluatorInitializerException(final INamedElement element, final Throwable cause) {
		super(MessageFormat.format(Messages.EvaluatorInitializerException_DefaultMessage, element.getName(),
				cause.getMessage()), cause);
		this.element = element;
	}

	/**
	 * Get the element where the exception originated
	 *
	 * @return The element (or null if unknown)
	 */
	public INamedElement getElement() {
		return element;
	}
}
