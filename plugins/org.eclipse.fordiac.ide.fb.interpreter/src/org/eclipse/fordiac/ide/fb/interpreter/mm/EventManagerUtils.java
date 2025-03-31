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
package org.eclipse.fordiac.ide.fb.interpreter.mm;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public final class EventManagerUtils {

	private EventManagerUtils() {
		throw new AssertionError("This class cannot be inherited"); //$NON-NLS-1$
	}

	public static void process(final EventManager eventManager) {
		final var transactions = eventManager.getTransactions();
		for (var i = 0; i < transactions.size(); i++) {
			final var transaction = transactions.get(i);
			if (transaction instanceof final FBTransaction fbtransaction) {
				fbtransaction.process();
				if (moreTransactionsLeft(transactions, i)) {
					final FBRuntimeAbstract newfbRuntime = getLatestFbRuntime(fbtransaction);
					// use fb runtime in the next transaction
					transactions.get(i + 1).getInputEventOccurrence().setFbRuntime(EcoreUtil.copy(newfbRuntime));
				}
			}
		}
	}

	public static FBRuntimeAbstract getLatestFbRuntime(final FBTransaction transaction) {
		return transaction.getInputEventOccurrence().getResultFBRuntime();
	}

	private static boolean moreTransactionsLeft(final EList<Transaction> transactions, final int i) {
		return (i + 1) < transactions.size();
	}

	public static void processFbTransaction(final FBTransaction transaction) {
		// set the input vars
		for (final var inputVar : transaction.getInputVariables()) {
			final var element = transaction.getInputEventOccurrence().getFbRuntime().getModel();
			if (element instanceof final FBType fbtype) {
				setInputVariable(inputVar, fbtype);
			}
		}
		final var result = processEventOccurrence(transaction.getInputEventOccurrence());
		transaction.getOutputEventOccurrences().addAll(result);
	}

	private static List<EventOccurrence> processEventOccurrence(final EventOccurrence eo) {
		final FBRuntimeAbstract runtime = eo.getFbRuntime();
		FBRuntimeAbstract resultRuntime = eo.getResultFBRuntime();
		if (resultRuntime == null) {
			resultRuntime = EcoreUtil.copy(runtime);
			eo.setResultFBRuntime(resultRuntime);
		}
		return resultRuntime.run();
	}

	private static void setInputVariable(final VarDeclaration inputVar, final FBType type) {
		if (null != inputVar) {
			final var pin = type.getInterfaceList().getInterfaceElement(inputVar.getName());
			if ((pin instanceof final VarDeclaration datapin) && pin.isIsInput()) {
				final Value sampledValue = LibraryElementFactory.eINSTANCE.createValue();
				datapin.setValue(sampledValue);
				sampledValue.setValue(inputVar.getValue().getValue());
			}
		}
	}

	/**
	 * sets the duration of all transactions of the event manager to the given value
	 */
	public static void setCyclicDuration(final EventManager eventManager, final long duration) {
		for (final Transaction t : eventManager.getTransactions()) {
			t.setDuration(duration);
		}
	}

	public static void processNetwork(final EventManager eventManager) {
		final var transactions = eventManager.getTransactions();
		for (var i = 0; i < transactions.size(); i++) {
			final var transaction = transactions.get(i);
			if (transaction instanceof final FBTransaction fbtransaction) {
				fbtransaction.process();
				fbtransaction.getOutputEventOccurrences()
						.forEach(outputEO -> eventManager.getTransactions().addAll(outputEO.getCreatedTransactions()));
				if (moreTransactionsLeft(transactions, i)) {
					final FBRuntimeAbstract newfbRuntime = getLatestFbRuntime(fbtransaction);
					// use fb network runtime in the next transaction
					transactions.get(i + 1).getInputEventOccurrence().setFbRuntime(EcoreUtil.copy(newfbRuntime));
				}
			}
		}
	}

	public static Resource addResourceToManager(final EventManager eventManager, final URI uri) {
		final ResourceSet reset = new ResourceSetImpl();
		final Resource res = reset.createResource(uri);
		res.getContents().add(eventManager);
		return res;
	}

	public static Resource loadResource(final URI uri) {
		final ResourceSet reset = new ResourceSetImpl();
		return reset.getResource(uri, true);
	}

	public static Resource loadResourceNotOnDemand(final URI uri) {
		final ResourceSet reset = new ResourceSetImpl();
		return reset.getResource(uri, false);
	}

}
