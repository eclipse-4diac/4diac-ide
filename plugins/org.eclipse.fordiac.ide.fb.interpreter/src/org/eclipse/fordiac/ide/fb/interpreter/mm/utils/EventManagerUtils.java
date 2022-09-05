/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garmend�a, Bianca Wiesmayr
 *       - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fb.interpreter.mm.utils;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsFactory;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.fb.interpreter.api.RuntimeFactory;
import org.eclipse.fordiac.ide.fb.interpreter.api.TransactionFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class EventManagerUtils {

	private EventManagerUtils() {
		throw new AssertionError("This class cannot be inherited"); //$NON-NLS-1$
	}

	public static final EventManager createFrom(final List<? extends Transaction> trans) {
		final EventManager eventManager = OperationalSemanticsFactory.eINSTANCE.createEventManager();
		eventManager.getTransactions().addAll(trans);
		return eventManager;
	}

	public static final EventManager createFrom(final List<Event> events, final FBType fbtype) {
		final EventManager eventManager = OperationalSemanticsFactory.eINSTANCE.createEventManager();
		eventManager.getTransactions().addAll(TransactionFactory.createFrom(events, RuntimeFactory.createFrom(fbtype)));
		return eventManager;
	}

	public static final void process(final EventManager eventManager) {
		final var transactions = eventManager.getTransactions();
		for (var i = 0; i < transactions.size(); i++) {
			final var transaction = transactions.get(i);
			if (transaction instanceof FBTransaction) {
				processFbTransaction(eventManager, (FBTransaction) transaction);
				if (moreTransactionsLeft(transactions, i)) {
					final FBRuntimeAbstract newfbRuntime = getLatestRuntime((FBTransaction) transaction);
					// use fb runtime in the next transaction
					transactions.get(i + 1).getInputEventOccurrence().setFbRuntime(EcoreUtil.copy(newfbRuntime));
				}
			}
		}
	}

	private static FBRuntimeAbstract getLatestRuntime(final FBTransaction transaction) {
		// choose the latest: input event occurr. or last output event occurr.
		if (transaction.getOutputEventOccurrences().isEmpty()) {
			return transaction.getInputEventOccurrence().getFbRuntime();
		}
		return transaction.getOutputEventOccurrences().get(transaction.getOutputEventOccurrences().size() - 1)
				.getFbRuntime();
	}

	private static boolean moreTransactionsLeft(final EList<Transaction> transactions, final int i) {
		return (i + 1) < transactions.size();
	}

	private static void processFbTransaction(final EventManager eventManager, final FBTransaction transaction) {
		// set the input vars
		for (final var inputVar : transaction.getInputVariables()) {
			final var element = transaction.getInputEventOccurrence().getFbRuntime().getModel();
			if (element instanceof FBType) {
				setInputVariable(inputVar, (FBType) element);
			}
		}
		// run transaction
		final var result = transaction.getInputEventOccurrence().getFbRuntime().run(eventManager);
		transaction.getOutputEventOccurrences().addAll(result);
	}

	private static void setInputVariable(final VarDeclaration inputVar, final FBType type) {
		if (null != inputVar) {
			final var pin = type.getInterfaceList().getInterfaceElement(inputVar.getName());
			if ((pin instanceof VarDeclaration) && pin.isIsInput()) {
				((VarDeclaration) pin).setValue(inputVar.getValue());
			}
		}
	}

	public static final void processNetwork(final EventManager eventManager) {
		final var transactions = eventManager.getTransactions();
		for (var i = 0; i < transactions.size(); i++) {
			final var transaction = transactions.get(i);
			if (transaction instanceof FBTransaction) {
				final FBTransaction fbTransaction = (FBTransaction) transaction;
				final FBNetworkRuntime fbNetwork = ((FBNetworkRuntime) fbTransaction.getInputEventOccurrence()
						.getFbRuntime());
				final var result = transaction.getInputEventOccurrence().getFbRuntime().run(eventManager);
				fbTransaction.getOutputEventOccurrences().addAll(result);
				// if ((i + 1) < transactions.size()) {
				// // TODO use ECoreUtil.copyAll(list of things to copy);
				// final var copyfbRuntime = new Copier();
				// final FBRuntimeAbstract newfbRuntime;
				// // choose the latest: input event occurr. or last output event occurr.
				// if (fbTransaction.getOutputEventOccurrences().isEmpty()) {
				// newfbRuntime = (FBRuntimeAbstract)
				// copyfbRuntime.copy(transaction.getInputEventOccurrence().getFbRuntime());
				// } else {
				// newfbRuntime = (FBRuntimeAbstract) copyfbRuntime.copy(
				// fbTransaction.getOutputEventOccurrences().get(fbTransaction.getOutputEventOccurrences().size()
				// - 1).
				// getFbRuntime());
				// }
				// copyfbRuntime.copyReferences();
				// transactions.get(i + 1).getInputEventOccurrence().setFbRuntime(newfbRuntime);
				// }
			}
		}
	}

}
