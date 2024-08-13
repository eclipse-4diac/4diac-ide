/*******************************************************************************
 * Copyright (c) 2024 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr
  *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.fb.interpreter.api;

import java.util.Collections;
import java.util.List;

import org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Trace;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;

public class ExecutionTrace {

	protected final List<Transaction> transactions;

	protected ExecutionTrace(final List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public List<Trace> getTraces() {
		return transactions.stream().map(tr -> ((FBTransaction) tr).getTrace()).toList();
	}

	public static ExecutionTrace of(final EventManager manager) {
		return of(manager.getTransactions());
	}

	public static ExecutionTrace of(final List<Transaction> transactionsList) {
		if (transactionsList.isEmpty()) {
			return new ExecutionTrace(Collections.emptyList());
		}
		final EventOccurrence eo = transactionsList.iterator().next().getInputEventOccurrence();
		if (eo != null) {
			if (eo.getFbRuntime() instanceof BasicFBTypeRuntime) {
				return new BasicFbExecutionTrace(transactionsList);
			}
			return new ExecutionTrace(transactionsList);
		}
		return null;
	}
}
