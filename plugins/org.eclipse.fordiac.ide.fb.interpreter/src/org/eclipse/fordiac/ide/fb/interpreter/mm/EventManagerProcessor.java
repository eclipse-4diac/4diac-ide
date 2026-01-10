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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalLong;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fb.interpreter.DefaultRunFBType;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.CompositeFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.api.EventOccFactory;
import org.eclipse.fordiac.ide.fb.interpreter.api.RuntimeFactory;
import org.eclipse.fordiac.ide.fb.interpreter.api.TransactionFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

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

	public EventManagerProcessor(final EventManager eventManager, final FBNetwork network) {
		this.eventManager = eventManager;
		this.networkRuntime = RuntimeFactory.createFrom(network);
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

			var fbRuntime = getFBNetworkRuntime(networkRuntime, fbTransaction.getInputEventOccurrence().getParentFB(),
					false);
			if (fbRuntime == null) {
				fbRuntime = getFBNetworkRuntime(networkRuntime,
						fbTransaction.getInputEventOccurrence().getParentFB().getOuterFBNetworkElement(), true);
				if (fbRuntime == null) {
					fbRuntime = networkRuntime; // createMissingNetworkRuntimes(fbTransaction.getInputEventOccurrence().getParentFB());
				} else if (fbRuntime instanceof final CompositeFBTypeRuntime compositeFBTypeRuntime) {
					fbRuntime = compositeFBTypeRuntime.getNetworkRuntime();
				}
			}
			final var copiedRT = EcoreUtil.copy(fbRuntime);
			fbTransaction.getInputEventOccurrence().setFbRuntime(copiedRT);
			final var transactionProcessor = new FBTransactionProcessor(fbTransaction);

			transactionProcessor.process(startTime.isPresent() ? startTime.getAsLong() : 0);

			((FBNetworkRuntime) fbRuntime).getTypeRuntimes().putAll(((FBNetworkRuntime) copiedRT).getTypeRuntimes());
			((FBNetworkRuntime) fbRuntime).setFbnetwork((((FBNetworkRuntime) copiedRT).getFbnetwork()));

			fbTransaction.getOutputEventOccurrences()
					.forEach(outputEO -> eventManager.getTransactions().addAll(outputEO.getCreatedTransactions()));

			for (final var outputEO : fbTransaction.getOutputEventOccurrences()) {
				lastOutputEvents.add(outputEO.getEvent()); // TODO: this works only because in the default runtime
															// runner, the mapped event from the network is assigned to
															// the returned type event
			}

			eventCounter++;
		}
		time += transaction.getDuration();
		return Optional.of(transaction.getInputEventOccurrence().getEvent());
	}

	private FBRuntimeAbstract getFBNetworkRuntime(final FBNetworkRuntime fbNetworkRuntime, final FBNetworkElement fb,
			final boolean lookingForParent) {
		if (fbNetworkRuntime.getTypeRuntimes().get(fb) != null) {
			if (lookingForParent) {
				return fbNetworkRuntime.getTypeRuntimes().get(fb);
			}
			return fbNetworkRuntime;
		}

		for (final var entry : fbNetworkRuntime.getTypeRuntimes()) {
			if (entry.getValue() instanceof final FBNetworkRuntime fbNetwork) {
				final var possibleRuntime = getFBNetworkRuntime(fbNetwork, fb, lookingForParent);
				if (possibleRuntime != null) {
					return possibleRuntime;
				}
			} else if (entry.getValue() instanceof final CompositeFBTypeRuntime compositeFBTypeRuntime) {
				final var possibleRuntime = getFBNetworkRuntime(compositeFBTypeRuntime.getNetworkRuntime(), fb,
						lookingForParent);
				if (possibleRuntime != null) {
					return possibleRuntime;
				}
			}
		}

		return null;
	}

	public void injectOutputEvent(final FB fb, final Event event, final Map<String, String> outputValues) {
		final String toLog = "\nEvent injected " + event.getQualifiedName();
		FordiacLogHelper.logInfo(toLog);

		// copy output values to the model.
		for (final var output : fb.getInterface().getOutputVars()) {
			final var value = LibraryElementFactory.eINSTANCE.createValue();
			value.setValue(outputValues.get(output.getName()));
			output.setValue(value);
		}

		final List<FBTransaction> generatedT = new ArrayList<>();
		for (final Connection conn : event.getOutputConnections()) {
			final var dest = (Event) conn.getDestination();
			final EventOccurrence destinationEventOccurence = EventOccFactory.createFrom(dest, null);
			destinationEventOccurence.setParentFB(dest.getBlockFBNetworkElement());
			final FBTransaction transaction = TransactionFactory.createFrom(destinationEventOccurence);
			generatedT.add(transaction);
		}
		lastInjectedEvent = event;
		eventManager.getTransactions().addAll(generatedT);
	}

	private class FBTransactionProcessor {
		private final FBTransaction transaction;

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
			final var result = transaction.getInputEventOccurrence().getFbRuntime().run();
			transaction.getOutputEventOccurrences().addAll(result);
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
