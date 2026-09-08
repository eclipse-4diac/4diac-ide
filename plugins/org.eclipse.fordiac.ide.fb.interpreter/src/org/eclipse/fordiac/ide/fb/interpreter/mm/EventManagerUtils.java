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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fb.interpreter.DefaultRunFBType;
import org.eclipse.fordiac.ide.fb.interpreter.ServiceInterfaceFBTypeDefaultInterpreter;
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
		processInternal(eventManager, false);
	}

	public static void processNetwork(final EventManager eventManager) {
		processInternal(eventManager, true);
	}

	private static void processInternal(final EventManager eventManager, final boolean network) {
		DefaultRunFBType.clearCaches();
		ServiceInterfaceFBTypeDefaultInterpreter.setOutputInitO(true);
		final var transactions = eventManager.getTransactions();
		long time = eventManager.getStartTime();

		for (var i = 0; i < transactions.size(); i++) {
			final var transaction = transactions.get(i);
			if (transaction instanceof final FBTransaction fbtransaction) {
				processFbTransaction(fbtransaction, time);
				// use fb runtime in the resulting transactions
				final FBRuntimeAbstract newfbRuntime = getLatestFbRuntime(fbtransaction);

				if (network) {
					for (final EventOccurrence eo : fbtransaction.getOutputEventOccurrences()) {
						for (final Transaction t : eo.getCreatedTransactions()) {
							if (t.getInputEventOccurrence().getFbRuntime() == null) {
								t.getInputEventOccurrence().setFbRuntime(EcoreUtil.copy(newfbRuntime));
							}
							eventManager.getTransactions().add(t);
						}
					}
				} else if ((i + 1) < transactions.size()) {
					transactions.get(i + 1).getInputEventOccurrence().setFbRuntime(newfbRuntime);
				}
			}
			time += transaction.getDuration();
		}
	}

	public static FBRuntimeAbstract getLatestFbRuntime(final FBTransaction transaction) {
		return transaction.getInputEventOccurrence().getResultFBRuntime();
	}

	public static void processFbTransaction(final FBTransaction transaction) {
		processFbTransaction(transaction, 0);
	}

	public static void processFbTransaction(final FBTransaction transaction, final long startTime) {
		// set the input vars
		for (final var inputVar : transaction.getInputVariables()) {
			final var fbtype = transaction.getInputEventOccurrence().getFbRuntime().getModel();
			setInputVariable(inputVar, fbtype);
		}
		transaction.getInputEventOccurrence().setStartTime(startTime);
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
			final var pin = type.getInterfaceList().getInterfaceElement(List.of(inputVar.getName()));
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
