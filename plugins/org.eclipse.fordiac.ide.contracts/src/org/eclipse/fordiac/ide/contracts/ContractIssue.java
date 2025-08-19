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
		// for static check
		MODEL_LOADING, SYNTAX_OR_SEMANTIC, UNKOWN_RULE, SINGLE_EVENT_MATCH, REPETITION_MATCH, TYPE_MATCH,
		MULTIPLE_FULFILL, MULTIPLE_RESOLVE, UNRESOLVED_REACTION, CONFLICTING_ASSUMPTIONS, CONFLICTING_GUARANTEES,
		UNKOWN,
		// for dynamic check
		SINGLE_EVENT_TOO_EARLY, SINGLE_EVENT_TOO_LATE, SINGLE_EVENT_MULTIPLE, SINGLE_EVENT_MISSED, REPETITION_TOO_EARLY,
		REPETITION_TOO_LATE, REPETITION_MISSED, REACTION_MISSED, REACTION_TOO_OFTEN, AGE_MISSED, AGE_TOO_OFTEN,
		CAUSAL_REACTION_MISSED, CAUSAL_REACTION_TOO_EARLY, CAUSAL_REACTION_TOO_LATE, CAUSAL_AGE_MISSED,
		CAUSAL_AGE_TOO_EARLY, CAUSAL_AGE_TOO_LATE, DUPLICATE_CAUSAL_ID
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
