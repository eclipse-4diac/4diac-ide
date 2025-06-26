/*******************************************************************************
 * Copyright (c) 2025 Felix Schmid
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.contracts;

import org.eclipse.xtext.diagnostics.Severity;

public class ContractIssue {

	public enum Code {
		MODEL_LOADING, SYNTAX_OR_SEMANTIC, UNKOWN_RULE, SINGLE_EVENT_MATCH, REPETITION_MATCH, TYPE_MATCH,
		MULTIPLE_FULFILL, MULTIPLE_RESOLVE, UNRESOLVED_REACTION, CONFLICTING_ASSUMPTIONS, CONFLICTING_GUARANTEES, UNKOWN
	}

	private final String message;
	private final Code code;
	private final Severity severity;

	ContractIssue(final String message, final Code code, final Severity severity) {
		this.message = message;
		this.code = code;
		this.severity = severity;
	}

	public String getMessage() {
		return message;
	}

	public Code getCode() {
		return code;
	}

	public Severity getSeverity() {
		return severity;
	}
}
