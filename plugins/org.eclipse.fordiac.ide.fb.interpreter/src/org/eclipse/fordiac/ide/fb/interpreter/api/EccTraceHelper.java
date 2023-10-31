/*******************************************************************************
 * Copyright (c) 2023 Fabio Gandolfi, Martin Melik Merkumians
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
 *   Martin Melik Merkumians
 *     - changed to a Stream based implementation
 *******************************************************************************/

package org.eclipse.fordiac.ide.fb.interpreter.api;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

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
		return getECTransitionsStream().map(ECTransition::getDestination).toList();
	}

	public List<ECState> getAllStatesOfSequenceUnique() {
		return getAllStatesOfSequence().stream().distinct().toList();
	}

	private Stream<ECTransition> getECTransitionsStream() {
		return transactions.stream().filter(FBTransaction.class::isInstance).map(FBTransaction.class::cast)
				.map(EccTraceHelper::getEccTrace).filter(Objects::nonNull).map(eccTrace -> eccTrace.getTransitions(ecc))
				.flatMap(List::stream);
	}

	public List<List<String>> getAllPathsOfSequence() {
		return getECTransitionsStream()
				.map(transi -> (Stream.of(transi.getSource().getName(), transi.getDestination().getName()).toList()))
				.toList();
	}

	public ECState getStartState() {
		if (!transactions.isEmpty() && transactions.get(0) instanceof final FBTransaction fbTransaction) {
			final var trace = getEccTrace(fbTransaction);
			if (trace != null && !trace.getTransitions(ecc).isEmpty()) {
				return trace.getTransitions(ecc).get(0).getSource();
			}
		}
		return null;
	}

	public List<ECAction> getAllOutputEvents() {
		return getECTransitionsStream().map(transi -> ((List<ECAction>) transi.getDestination().getECAction()))
				.flatMap(List::stream).toList();
	}

	public List<Algorithm> getAllAlgorithms() {
		return getECTransitionsStream().map(transi -> ((List<ECAction>) transi.getDestination().getECAction()))
				.flatMap(List::stream).map(ECAction::getAlgorithm).toList();
	}

	private static EccTrace getEccTrace(final FBTransaction transaction) {
		if (transaction.getTrace() instanceof final EccTrace eccTrace) {
			return eccTrace;
		}
		return null;
	}

	public List<ECState> getAllPossibleStates() {
		final var transition = transactions.stream().map(FBTransaction.class::cast).map(EccTraceHelper::getEccTrace)
				.filter(Objects::nonNull).map(transaction -> transaction.getTransitions(ecc)).flatMap(List::stream)
				.filter(trans -> trans.getECC() != null && trans.getECC().getECState() != null).findAny();

		return transition.map(trans -> (List<ECState>) trans.getECC().getECState()).orElse(List.of());
		// List.of() most efficient but secretly immutable
	}

	public List<ECState> getAllPossibleEndStates() {
		final var endStates = getAllPossibleStates();
		endStates.removeIf(state -> state.getOutTransitions().stream()
				.anyMatch(trans -> trans.getConditionEvent() == null && "1".equals(trans.getConditionExpression()))); //$NON-NLS-1$
		return endStates;
	}

	public List<List<String>> getAllPossiblePaths() {
		return getAllPossibleStates().stream().map((final ECState state) -> {
			final var stateName = state.getName();
			if (!state.getOutTransitions().isEmpty()) {
				return state.getOutTransitions().stream()
						.map(outTrans -> Stream.of(stateName, outTrans.getDestination().getName()).toList()).toList();
			}
			// path of length 0 -> single node
			return Stream.of(Stream.of(stateName).toList()).toList();
		}).flatMap(List::stream).toList();
	}
}
