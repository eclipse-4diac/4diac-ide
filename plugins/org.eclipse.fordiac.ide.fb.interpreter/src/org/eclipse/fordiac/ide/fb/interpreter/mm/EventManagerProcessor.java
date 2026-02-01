/*******************************************************************************
 * Copyright (c) 2026 Jose Cabral
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.fb.interpreter.mm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Set;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.fb.interpreter.DefaultRunFBType;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.fb.interpreter.api.EventOccFactory;
import org.eclipse.fordiac.ide.fb.interpreter.api.TransactionFactory;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

/**
 * @brief Process an event manager using different modes
 *
 *        Takes care of the context of given network runtime and process the
 *        transactions in the event manager in different modes: - infinite:
 *        execute all transactions and incoming ones until no one is left -
 *        existing: execute the number of already existing transactions in the
 *        event manager and no new ones. - one: execute one transaction and
 *        return the executed event (if any was executed)
 *
 *        Executed transactions are removed from the internal memory.
 */
public class EventManagerProcessor {

	public enum ProcessMode {
		INFINITE, EXISTING
	}

	private final EventManager eventManager;
	private final FBNetworkRuntime networkRuntime;
	private long time = 0;
	private int eventCounter = 0;
	private final List<Event> lastOutputEvents = new ArrayList<>();
	private Event lastInjectedEvent = null;

	public EventManagerProcessor(final EventManager eventManager, final FBNetworkRuntime networkRuntime) {
		this.eventManager = eventManager;
		this.networkRuntime = networkRuntime;
		DefaultRunFBType.clearCaches();
	}

	public int getEventCounter() {
		return eventCounter;
	}

	public List<Event> getLastOutputEvents() {
		return lastOutputEvents;
	}

	public void process(final ProcessMode processMode) {
		time = eventManager.getStartTime();
		switch (processMode) {
		case INFINITE:
			Optional<Event> executedEvent;
			do {
				executedEvent = processOne(OptionalLong.of(time));
			} while (executedEvent.isPresent());
			break;
		case EXISTING:
			final int existingTransactions = eventManager.getTransactions().size();
			for (int i = 0; i < existingTransactions; i++) {
				processOne(OptionalLong.of(time));
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown process mode: " + processMode); //$NON-NLS-1$
		}
	}

	public Optional<Event> processOne(final OptionalLong startTime) {
		lastOutputEvents.clear();

		final var transactions = eventManager.getTransactions();
		if (transactions.isEmpty()) {
			return Optional.empty();
		}

		if (lastInjectedEvent != null) {
			lastOutputEvents.add(lastInjectedEvent);
			lastInjectedEvent = null;
		}

		final var transaction = transactions.get(0);
		transactions.remove(0);

		if (transaction instanceof final FBTransaction fbTransaction) {

			try (final var contextKeeper = new RuntimeContextHandler(networkRuntime,
					fbTransaction.getInputEventOccurrence())) {
				final var transactionProcessor = new FBTransactionProcessor(fbTransaction);
				transactionProcessor.process(startTime.isPresent() ? startTime.getAsLong() : 0);
			}

			final var isSubApp = fbTransaction.getInputEventOccurrence().getParentFB() instanceof UntypedSubApp;

			fbTransaction.getOutputEventOccurrences()
					.forEach(outputEO -> addTransactions(outputEO.getCreatedTransactions(), isSubApp));

			for (final var outputEO : fbTransaction.getOutputEventOccurrences()) {
				lastOutputEvents.add(outputEO.getEvent());
			}

			if (isSubApp) {
				return processOne(OptionalLong.of(startTime.orElse(0) + transaction.getDuration()));
			}
		}
		time += transaction.getDuration();
		return Optional.of(transaction.getInputEventOccurrence().getEvent());
	}

	private void addTransactions(final EList<Transaction> transactions, final boolean atStart) {
		if (atStart) {
			eventManager.getTransactions().addAll(0, transactions);
		} else {
			eventManager.getTransactions().addAll(transactions);
			eventCounter += transactions.size();
		}
	}

	public void injectOutputEvent(final BlockFBNetworkElement fb, final Event event,
			final Map<String, String> outputValues) {

		for (final var entry : outputValues.entrySet()) {
			final var name = entry.getKey();
			final var value = entry.getValue();
			// copy output values to the model.
			for (final var output : fb.getInterface().getOutputVars()) {
				if (!output.getName().equals(name)) {
					continue;
				}
				final var newValue = LibraryElementFactory.eINSTANCE.createValue();
				newValue.setValue(value);
				output.setValue(newValue);
			}
		}

		final EList<Transaction> generatedT = new BasicEList<>();
		for (final Connection conn : event.getOutputConnections()) {
			final var dest = (Event) conn.getDestination();
			final EventOccurrence destinationEventOccurence = EventOccFactory.createFrom(dest, null);
			destinationEventOccurence.setParentFB(dest.getBlockFBNetworkElement());
			final FBTransaction transaction = TransactionFactory.createFrom(destinationEventOccurence);
			generatedT.add(transaction);
		}
		lastInjectedEvent = event;
		addTransactions(generatedT, false);
	}

	private class FBTransactionProcessor {
		private final FBTransaction transaction;

		private static final Set<String> sifbInForte = new HashSet<>(Arrays.asList("E_CYCLE")); //$NON-NLS-1$

		public FBTransactionProcessor(final FBTransaction transaction) {
			this.transaction = transaction;
		}

		public void process(final long startTime) {
			// set the input vars
			for (final var inputVar : transaction.getInputVariables()) {
				final var element = transaction.getInputEventOccurrence().getFbRuntime().getModel();
				setInputVariable(inputVar, element);
			}
			transaction.getInputEventOccurrence().setStartTime(startTime);
			if (isSifb()) {
				return;
			}
			final var result = transaction.getInputEventOccurrence().getFbRuntime().run();
			transaction.getOutputEventOccurrences().addAll(result);
		}

		private boolean isSifb() {
			return sifbInForte.contains(transaction.getInputEventOccurrence().getParentFB().getTypeName());
		}

		private static void setInputVariable(final VarDeclaration inputVar, final FBType type) {
			if (null != inputVar) {
				final var pin = type.getInterfaceList().getInterfaceElement(Arrays.asList(inputVar.getName()));
				if ((pin instanceof final VarDeclaration datapin) && pin.isIsInput()) {
					final Value sampledValue = LibraryElementFactory.eINSTANCE.createValue();
					datapin.setValue(sampledValue);
					sampledValue.setValue(inputVar.getValue().getValue());
				}
			}
		}

	}

}
