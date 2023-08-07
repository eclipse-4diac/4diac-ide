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

package org.eclipse.fordiac.ide.fb.interpreter.api;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EccTrace;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;

public class EccTraceHelper {

	private final List<Transaction> transactions;
	private final ECC ecc;

	public EccTraceHelper(final List<Transaction> transactions, final ECC ecc) {
		this.transactions = transactions;
		this.ecc = ecc;
	}

	public List<ECState> getAllStatesOfSequence() {
		final List<ECState> states = new ArrayList<>();
		for (final Transaction transac : transactions) {
			if (getEccTrace((FBTransaction) transac) != null) {
				for (final ECTransition transi : getEccTrace((FBTransaction) transac).getTransitions(ecc)) {
					states.add(transi.getDestination());
				}
			}
		}
		return states;
	}

	public List<ECState> getAllStatesOfSequenceUnique() {
		final List<ECState> states = new ArrayList<>();
		for (final Transaction transac : transactions) {
			if (getEccTrace((FBTransaction) transac) != null) {
				for (final ECTransition transi : getEccTrace((FBTransaction) transac).getTransitions(ecc)) {
					if (states.stream().filter(s -> s.getName().equals(transi.getDestination().getName())).findAny()
							.isEmpty()) {
						states.add(transi.getDestination());
					}
				}
			}
		}
		return states;
	}

	public List<ArrayList<String>> getAllPathsOfSequence() {
		final List<ArrayList<String>> paths = new ArrayList<>();

		for (final Transaction transac : transactions) {
			if (getEccTrace((FBTransaction) transac) != null) {
				for (final ECTransition transi : getEccTrace((FBTransaction) transac).getTransitions(ecc)) {
					final ArrayList<String> path = new ArrayList<>();
					path.add(transi.getSource().getName());
					path.add(transi.getDestination().getName());
					paths.add(path);
				}
			}
		}

		return paths;
	}

	public ECState getStartState() {
		if (!transactions.isEmpty() && getEccTrace((FBTransaction) transactions.get(0)) != null
				&& !getEccTrace((FBTransaction) transactions.get(0)).getTransitions(ecc).isEmpty()) {
			return getEccTrace((FBTransaction) transactions.get(0)).getTransitions(ecc).get(0).getSource();
		}
		return null;
	}

	public List<ECAction> getAllOutputEvents() {
		final List<ECAction> actions = new ArrayList<>();
		for (final Transaction transac : transactions) {
			if (getEccTrace((FBTransaction) transac) != null) {
				for (final ECTransition transi : getEccTrace((FBTransaction) transac).getTransitions(ecc)) {
					actions.addAll(transi.getDestination().getECAction());
				}
			}
		}
		return actions;
	}

	public List<Algorithm> getAllAlgorithms() {
		final List<Algorithm> algos = new ArrayList<>();
		for (final Transaction transac : transactions) {
			if (getEccTrace((FBTransaction) transac) != null) {
				for (final ECTransition transi : getEccTrace((FBTransaction) transac).getTransitions(ecc)) {
					transi.getDestination().getECAction().forEach(ac -> algos.add(ac.getAlgorithm()));
				}
			}
		}
		return algos;
	}

	private static EccTrace getEccTrace(final FBTransaction transaction) {
		if (transaction.getTrace() instanceof final EccTrace eccTrace) {
			return eccTrace;
		}
		return null;
	}

	public List<ECState> getAllPossibleStates() {
		if (!transactions.isEmpty()) {
			for (final Transaction trans : transactions) {
				if (getEccTrace((FBTransaction) trans) != null
						&& !getEccTrace((FBTransaction) trans).getTransitions(ecc).isEmpty()) {
					for (final ECTransition transi : getEccTrace((FBTransaction) trans).getTransitions(ecc)) {
						if (transi.getECC() != null && transi.getECC().getECState() != null) {
							return transi.getECC().getECState();
						}
					}
				}
			}
		}
		return new ArrayList<>();
	}

	public List<ECState> getAllPossibleEndStates() {
		final List<ECState> allPossibleStates = getAllPossibleStates();
		for (final ECState state : allPossibleStates) {
			for (final ECTransition trans : state.getOutTransitions()) {
				if (trans.getConditionEvent() == null && trans.getConditionExpression().equals("1")) {
					allPossibleStates.remove(state);
					break;
				}
			}
		}
		return allPossibleStates;
	}

	public List<ArrayList<String>> getAllPossiblePaths() {
		final List<ArrayList<String>> allPossiblePaths = new ArrayList<>();

		for (final ECState state : getAllPossibleStates()) {
			if (state.getOutTransitions().size() > 0) {
				for (final ECTransition outTrans : state.getOutTransitions()) {
					final ArrayList<String> path = new ArrayList<>();
					path.add(state.getName());
					path.add(outTrans.getDestination().getName());
					allPossiblePaths.add(path);
				}
			} else {
				// path of length 0 -> single node
				final ArrayList<String> path = new ArrayList<>();
				path.add(state.getName());
			}
		}
		return allPossiblePaths;
	}

}
