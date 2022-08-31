/*******************************************************************************
 * Copyright (c) 2021, 2022 Johannes Kepler University Linz and others
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
 *   Paul Pavlicek - cleanup
 *******************************************************************************/
package org.eclipse.fordiac.ide.fb.interpreter.mm.utils;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public final class EventManagerUtils {

	public static void process(final EventManager eventManager) {
		final var transactions = eventManager.getTransactions();
		for (var i = 0; i < transactions.size(); i++) {
			final var transaction = transactions.get(i);
			if (transaction instanceof FBTransaction) {
				processFbTransaction((FBTransaction) transaction);
				if (moreTransactionsLeft(transactions, i)) {
					final FBRuntimeAbstract newfbRuntime = getLatestFbRuntime((FBTransaction) transaction);
					// use fb runtime in the next transaction
					transactions.get(i + 1).getInputEventOccurrence().setFbRuntime(EcoreUtil.copy(newfbRuntime));
				}
			}
		}
	}

	public static FBRuntimeAbstract getLatestFbRuntime(final FBTransaction transaction) {
		// choose the latest: input event occurr. or last output event occurr.
		if (transaction.getOutputEventOccurrences().isEmpty()) {
			return transaction.getInputEventOccurrence().getFbRuntime();
		}
		return transaction.getOutputEventOccurrences().get(transaction.getOutputEventOccurrences().size() - 1)
				.getFbRuntime();
	}

	public static FBType getLastTypeFromSequence(final FBType startFb, final EventManager eventManager) {
		final int nT = eventManager.getTransactions().size();
		final FBTransaction t = (FBTransaction) eventManager.getTransactions().get(nT - 1);
		FBType next = null;
		if (!t.getOutputEventOccurrences().isEmpty()) {
			final int nEv = t.getOutputEventOccurrences().size();
			final FBRuntimeAbstract last = (t.getOutputEventOccurrences().get(nEv - 1).getFbRuntime());
			next = (FBType) last.getModel();
		} else {
			next = startFb;
		}
		return next;
	}

	private static boolean moreTransactionsLeft(final EList<Transaction> transactions, final int i) {
		return (i + 1) < transactions.size();
	}

	private static void processFbTransaction(final FBTransaction transaction) {
		// set the input vars
		for (final var inputVar : transaction.getInputVariables()) {
			final var element = transaction.getInputEventOccurrence().getFbRuntime().getModel();
			if (element instanceof FBType) {
				setInputVariable(inputVar, (FBType) element);
			}
		}
		// run transaction
		final var result = transaction.getInputEventOccurrence().getFbRuntime().run();
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

	public static void processNetwork(final EventManager eventManager) {
		final var transactions = eventManager.getTransactions();
		for (var i = 0; i < transactions.size(); i++) {
			final var transaction = transactions.get(i);
			if (transaction instanceof FBTransaction) {
				processFbTransaction((FBTransaction) transaction);
				((FBTransaction) transaction).getOutputEventOccurrences()
						.forEach(outputEO -> eventManager.getTransactions().addAll(outputEO.getCreatedTransactions()));
				if (moreTransactionsLeft(transactions, i)) {
					final FBRuntimeAbstract newfbRuntime = EcoreUtil
							.copy(getLatestNetworkRuntime((FBTransaction) transaction));
					// use fb network runtime in the next transaction
					transactions.get(i + 1).getInputEventOccurrence().setFbRuntime(newfbRuntime);
				}

			}
		}
	}

	private static FBRuntimeAbstract getLatestNetworkRuntime(final FBTransaction transaction) {
		return transaction.getInputEventOccurrence().getFbRuntime();
	}

	private EventManagerUtils() {
		throw new AssertionError("This class cannot be inherited"); //$NON-NLS-1$
	}
}
