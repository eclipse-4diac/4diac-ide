/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.eval

import java.lang.Exception
import org.eclipse.xtend.lib.annotations.Accessors

class EvaluatorException extends Exception {
	@Accessors final Evaluator evaluator

	new(Evaluator evaluator) {
		this(null, null, evaluator)
	}

	new(String message, Evaluator evaluator) {
		this(message, null, evaluator)
	}

	new(String message, Throwable cause, Evaluator evaluator) {
		super(message, cause)
		this.evaluator = evaluator
	}
}
