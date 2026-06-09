/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
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
import java.util.List;

import org.eclipse.fordiac.ide.model.data.ArrayType;

public class EvaluatorArrayIndexOutOfBoundsException extends EvaluatorException {

	private static final long serialVersionUID = 1L;

	public EvaluatorArrayIndexOutOfBoundsException(final String message, final Evaluator evaluator) {
		super(message, evaluator);
	}

	public EvaluatorArrayIndexOutOfBoundsException(final String message, final Throwable cause,
			final Evaluator evaluator) {
		super(message, cause, evaluator);
	}

	public EvaluatorArrayIndexOutOfBoundsException(final List<Integer> indices, final ArrayType type,
			final Evaluator evaluator) {
		super(MessageFormat.format(Messages.EvaluatorArrayIndexOutOfBoundsException_DefaultMessage, indices,
				type.getName()), evaluator);
	}
}
