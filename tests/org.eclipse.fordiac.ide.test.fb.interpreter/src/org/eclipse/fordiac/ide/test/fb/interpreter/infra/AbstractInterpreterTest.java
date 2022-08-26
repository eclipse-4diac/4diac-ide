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
 *   Antonio Garmenda, Bianca Wiesmayr
 *       - initial implementation and/or documentation
 *   Paul Pavlicek
 *   	 -cleanup and us factory methods
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.fb.interpreter.infra;

import java.io.IOException;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.fb.interpreter.api.EventManagerFactory;
import org.eclipse.fordiac.ide.fb.interpreter.api.RuntimeFactory;
import org.eclipse.fordiac.ide.fb.interpreter.api.TransactionFactory;
import org.eclipse.fordiac.ide.fb.interpreter.mm.utils.EventManagerUtils;
import org.eclipse.fordiac.ide.fb.interpreter.mm.utils.SequenceMatcher;
import org.eclipse.fordiac.ide.fb.interpreter.mm.utils.ServiceSequenceUtils;
import org.eclipse.fordiac.ide.fb.interpreter.mm.utils.VariableUtils;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.systemmanagement.FordiacProjectLoader;
import org.junit.Test;
import org.osgi.framework.Bundle;

public abstract class AbstractInterpreterTest {
	public static final String START_STATE = "START"; //$NON-NLS-1$
	private static final Bundle bundle = Platform.getBundle("org.eclipse.fordiac.ide.test.fb.interpreter"); //$NON-NLS-1$

	@Test
	public abstract void test() throws IllegalArgumentException;

	public static FBType loadFBType(final String name) {
		return loadFBType(name, true);
	}

	protected static FBNetwork loadFbNetwork(final String projectName, final String systemName) {
		return loadFbNetwork(projectName, systemName, null);
	}

	protected static FBNetwork loadFbNetwork(final String projectName, final String systemName, final String appName) {
		final Path projectPath = new Path("data/" + projectName); //$NON-NLS-1$
		FordiacProjectLoader loader;
		try {
			loader = new FordiacProjectLoader(bundle, projectPath);
		} catch (CoreException | IOException e) {
			return null;
		}

		final AutomationSystem system = loader.getAutomationSystem(systemName);
		if (appName == null) {
			return system.getApplication().get(0).getFBNetwork();
		}
		return system.getApplication().stream().filter(app -> app.getName().equals(appName)).findAny().orElseThrow()
				.getFBNetwork();
	}

	protected static FBType loadFBType(final String name, final boolean emptyService) {
		final Path projectPath = new Path("data/TestFBs"); //$NON-NLS-1$
		final Bundle bundle = Platform.getBundle("org.eclipse.fordiac.ide.test.fb.interpreter"); //$NON-NLS-1$
		FordiacProjectLoader loader;
		try {
			loader = new FordiacProjectLoader(bundle, projectPath);
		} catch (CoreException | IOException e) {
			return null;
		}

		final TypeLibrary typeLib = TypeLibraryManager.INSTANCE.getTypeLibrary(loader.getEclipseProject());
		final FBTypeEntry typeEntry = typeLib.getFBTypeEntry(name);
		final FBType fbt = typeEntry.getType();

		if (emptyService) {
			fbt.setService(ServiceSequenceUtils.createEmptyServiceModel());
		}
		return fbt;
	}

	protected static ServiceTransaction addTransaction(final ServiceSequence seq,
			final org.eclipse.fordiac.ide.test.fb.interpreter.infra.FBTransaction fbtrans) {
		final ServiceTransaction transaction = LibraryElementFactory.eINSTANCE.createServiceTransaction();
		seq.getServiceTransaction().add(transaction);
		if (fbtrans.getInputEvent() != null) {
			final InputPrimitive inputPrimitive = LibraryElementFactory.eINSTANCE.createInputPrimitive();
			inputPrimitive.setEvent(fbtrans.getInputEvent());
			transaction.setInputPrimitive(inputPrimitive);
		}

		if (!fbtrans.getOutputEvent().isEmpty()) {
			for (final String event : fbtrans.getOutputEvent()) {
				final OutputPrimitive outputPrimitive = LibraryElementFactory.eINSTANCE.createOutputPrimitive();
				outputPrimitive.setEvent(event);
				outputPrimitive.setInterface(((Service) seq.eContainer()).getLeftInterface());
				outputPrimitive.setParameters(""); //$NON-NLS-1$
				for (final String parameter : fbtrans.getOutputParameter()) {
					outputPrimitive.setParameters(outputPrimitive.getParameters() + parameter + ";"); //$NON-NLS-1$
				}
				transaction.getOutputPrimitive().add(outputPrimitive);
			}
		}
		return transaction;
	}

	public static EList<Transaction> runFBNetworkTest(final FBNetwork network, final Event event) {
		final EventManager eventManager = EventManagerFactory.createFrom(event, EcoreUtil.copy(network));
		EventManagerUtils.processNetwork(eventManager);
		return eventManager.getTransactions();
	}

	public static FBType runFBTest(final FBType fb, final ServiceSequence seq) {
		return runFBTest(fb, seq, null);
	}

	public static FBType runFBTest(final FBType fb, final ServiceSequence seq, final String startStateName)
			throws IllegalArgumentException {
		if (seq.getServiceTransaction().isEmpty()) {
			return fb;
		}
		final FBRuntimeAbstract rt = RuntimeFactory.createFrom(fb);
		RuntimeFactory.setStartState(rt, startStateName);
		final List<FBTransaction> transaction = TransactionFactory.createFrom(fb, seq, rt);
		final EventManager eventManager = EventManagerFactory.createFrom(transaction);
		EventManagerUtils.process(eventManager);
		checkResults(seq, eventManager);
		return EventManagerUtils.getLastTypeFromSequence(fb, eventManager);
	}



	private static void checkResults(final ServiceSequence seq, final EventManager eventManager)
			throws IllegalArgumentException {
		final EList<ServiceTransaction> expectedResults = seq.getServiceTransaction();
		final EList<Transaction> results = eventManager.getTransactions();

		if (expectedResults.size() != results.size()) { // correct test data
			throw new IllegalArgumentException("test data is incorrect"); //$NON-NLS-1$
		}

		for (int i = 0; i < expectedResults.size(); i++) {
			final FBTransaction result = (FBTransaction) results.get(i);
			final ServiceTransaction expectedResult = expectedResults.get(i);
			checkTransaction(result, expectedResult);
		}
	}

	private static void checkTransaction(final FBTransaction result, final ServiceTransaction expectedResult) {
		// input event was correctly generated
		if (!result.getInputEventOccurrence().getEvent().getName()
				.equals(expectedResult.getInputPrimitive().getEvent())) {
			throw new IllegalArgumentException("Input event was not generated correctly"); //$NON-NLS-1$
		}

		// no unwanted output event occurrences
		final long outputEvents = expectedResult.getOutputPrimitive().stream().filter(
				p -> !p.getInterface().getName().toLowerCase().contains(ServiceSequenceUtils.INTERNAL_INTERFACE))
				.count();
		if (outputEvents != result.getOutputEventOccurrences().size()) {
			throw new IllegalArgumentException("Unwanted output event occurrence"); //$NON-NLS-1$
		}

		// check all output primitives
		for (int j = 0; j < outputEvents; j++) {
			final OutputPrimitive p = expectedResult.getOutputPrimitive().get(j);
			checkOutputPrimitive(result, j, p);
		}
	}

	private static void checkOutputPrimitive(final FBTransaction result, final int j, final OutputPrimitive p) {
		if (!p.getInterface().getName().toLowerCase().contains(ServiceSequenceUtils.INTERNAL_INTERFACE)) {
			// generated output event is correct
			if (!p.getEvent().equals(result.getOutputEventOccurrences().get(j).getEvent().getName())) {
				throw new IllegalArgumentException("Generated output event is incorrect"); //$NON-NLS-1$
			}
			// the associated data is correct
			if (!processParameters(p.getParameters(), result)) {
				throw new IllegalArgumentException("Parameter values do not match the data"); //$NON-NLS-1$
			}
		}
	}

	private static boolean processParameters(final String parameters, final FBTransaction result) {
		if ((parameters == null) || parameters.isBlank()) {
			return true;
		}
		final int length = result.getOutputEventOccurrences().size();
		final FBRuntimeAbstract captured = result.getOutputEventOccurrences().get(length - 1).getFbRuntime();
		final var parameterList = ServiceSequenceUtils.splitAndCleanList(parameters, ";"); //$NON-NLS-1$
		final SequenceMatcher sm = new SequenceMatcher(getFBType(captured));

		for (final String assumption : parameterList) {
			if (!sm.matchVariable(assumption, false)) {
				return false;
			}
		}
		return true;
	}

	private static FBType getFBType(final FBRuntimeAbstract captured) {
		final EObject type = captured.getModel();
		if (type instanceof FBType) {
			return (FBType) type;
		}
		return null;
	}

	protected static void setVariable(final BaseFBType fb, final String name, final String value) {
		VariableUtils.setVariable(fb, name, value);
	}
}
