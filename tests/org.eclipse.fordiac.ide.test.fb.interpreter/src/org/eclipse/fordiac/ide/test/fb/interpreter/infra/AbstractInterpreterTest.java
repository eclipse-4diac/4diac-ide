/*******************************************************************************
 * Copyright (c) 2021, 2022 Johannes Kepler University Linz
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.fb.interpreter.infra;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsFactory;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.SimpleFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.fb.interpreter.mm.utils.EventManagerUtils;
import org.eclipse.fordiac.ide.fb.interpreter.mm.utils.ServiceSequenceUtils;
import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.value.ValueConverter;
import org.eclipse.fordiac.ide.model.value.ValueConverterFactory;
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
		} else {
			return system.getApplication().stream().filter(app -> app.getName().equals(appName)).findAny().orElseThrow()
					.getFBNetwork();
		}
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

	public static void setVariable(final FBType fb, final String name, final String value) {
		final IInterfaceElement el = fb.getInterfaceList().getInterfaceElement(name);
		if (!(el instanceof VarDeclaration)) {
			throw new IllegalArgumentException("variable does not exist in FB"); //$NON-NLS-1$
		}
		final Value val = ((VarDeclaration) el).getValue();
		if (val == null) {
			((VarDeclaration) el).setValue(LibraryElementFactory.eINSTANCE.createValue());
		}
		final ValueConverter<?> conv = ValueConverterFactory.createValueConverter(el.getType());
		Object convertedValue;
		final int prefixIndex = value.indexOf('#');

		if (prefixIndex == -1) {
			convertedValue = conv.toValue(value);
		} else {
			convertedValue = conv.toValue(value.substring(prefixIndex + 1, value.length() - 1));
		}
		((VarDeclaration) el).getValue().setValue(convertedValue.toString());
	}

	private static Collection<Transaction> createTransactions(final BasicFBType fb, final ServiceSequence seq,
			final BasicFBTypeRuntime runtime) {
		final List<Transaction> transactions = new ArrayList<>();
		for (final ServiceTransaction st : seq.getServiceTransaction()) {
			final FBTransaction t = createTransaction(fb, st);
			if (t != null) {
				transactions.add(t);
			}
		}
		// The first transaction has a copy of the BasicFBTypeRuntime
		final Copier copier = new Copier();
		final BasicFBTypeRuntime copyBasicFBTypeRuntime = (BasicFBTypeRuntime) copier.copy(runtime);
		copier.copyReferences();
		transactions.get(0).getInputEventOccurrence().setFbRuntime(copyBasicFBTypeRuntime);
		return transactions;
	}

	private static FBTransaction createTransaction(final FBType fb, final ServiceTransaction st) {
		final String inputEvent = st.getInputPrimitive().getEvent();
		if (inputEvent != null) {
			final Event eventPin = (Event) fb.getInterfaceList().getInterfaceElement(inputEvent);
			if (eventPin == null) {
				throw new IllegalArgumentException("input primitive: event " + inputEvent + " does not exist"); //$NON-NLS-1$//$NON-NLS-2$
			}
			final EventOccurrence eventOccurrence = OperationalSemanticsFactory.eINSTANCE.createEventOccurrence();
			eventOccurrence.setEvent(eventPin);
			final FBTransaction transaction = OperationalSemanticsFactory.eINSTANCE.createFBTransaction();
			transaction.setInputEventOccurrence(eventOccurrence);
			// process parameter and set variables
			final String inputParameters = st.getInputPrimitive().getParameters();
			final var paramList = getParametersFromString(inputParameters);
			for (final List<String> parameter : paramList) {
				if (parameter.size() == 2) {
					setVariable(fb, parameter.get(0), parameter.get(1));
				}
			}
			return transaction;
		}
		return null;
	}

	public static BaseFBType runFBTest(final BaseFBType fb, final ServiceSequence seq) throws IllegalArgumentException {
		if (fb instanceof BasicFBType) {
			return runTest((BasicFBType) fb, seq, START_STATE);
		}
		return runSimpleFBTest((SimpleFBType) fb, seq);
	}

	public static EList<Transaction> runFBNetworkTest(final FBNetwork network, final Event event) {
		final EventManager eventManager = OperationalSemanticsFactory.eINSTANCE.createEventManager();
		// network.eResource().getContents().add(eventManager);
		// TODO create convenience methods in eventManagerUtils
		final EventOccurrence eventOccurrence = OperationalSemanticsFactory.eINSTANCE.createEventOccurrence();
		eventOccurrence.setEvent(event);
		eventOccurrence.setParentFB(event.getFBNetworkElement());
		eventOccurrence.setActive(true);

		final FBNetworkRuntime runtime = OperationalSemanticsFactory.eINSTANCE.createFBNetworkRuntime();
		runtime.setFbnetwork(EcoreUtil.copy(network));
		eventOccurrence.setFbRuntime(runtime);

		final FBTransaction transaction = OperationalSemanticsFactory.eINSTANCE.createFBTransaction();
		transaction.setInputEventOccurrence(eventOccurrence);
		eventManager.getTransactions().add(transaction);

		EventManagerUtils.processNetwork(eventManager);
		return eventManager.getTransactions();
	}

	private static BaseFBType runSimpleFBTest(final SimpleFBType fb, final ServiceSequence seq) {
		final ResourceSet reset = new ResourceSetImpl();
		final Resource resource = reset.createResource(URI.createURI("platform:/resource/" + fb.getName() + ".xmi")); //$NON-NLS-1$ //$NON-NLS-2$
		final EventManager eventManager = OperationalSemanticsFactory.eINSTANCE.createEventManager();
		resource.getContents().add(eventManager);
		final SimpleFBTypeRuntime simpleFBTypeRT = OperationalSemanticsFactory.eINSTANCE.createSimpleFBTypeRuntime();
		simpleFBTypeRT.setSimpleFBType(fb);
		if (seq.getServiceTransaction().isEmpty()) {
			seq.getServiceTransaction().add(LibraryElementFactory.eINSTANCE.createServiceTransaction());
		}
		final FBTransaction transaction = createTransaction(fb, seq.getServiceTransaction().get(0));
		transaction.getInputEventOccurrence().setFbRuntime(simpleFBTypeRT);
		eventManager.getTransactions().add(transaction);

		EventManagerUtils.process(eventManager);

		checkResults(seq, eventManager);

		return null;
	}

	public static BasicFBType runTest(final BasicFBType fb, final ServiceSequence seq, final String startStateName)
			throws IllegalArgumentException {
		final ResourceSet reset = new ResourceSetImpl();
		final Resource resource = reset.createResource(URI.createURI("platform:/resource/" + fb.getName() + ".xmi")); //$NON-NLS-1$ //$NON-NLS-2$
		final EventManager eventManager = OperationalSemanticsFactory.eINSTANCE.createEventManager();
		resource.getContents().add(eventManager);
		final BasicFBTypeRuntime basicFBTypeRT = OperationalSemanticsFactory.eINSTANCE.createBasicFBTypeRuntime();
		basicFBTypeRT.setBasicfbtype(fb);
		// set the start state
		final EList<ECState> stateList = basicFBTypeRT.getBasicfbtype().getECC().getECState();
		final ECState startState = stateList.stream().filter(s -> s.getName().equals(startStateName))
				.collect(Collectors.toList()).get(0);
		basicFBTypeRT.setActiveState(startState);

		eventManager.getTransactions().addAll(createTransactions(fb, seq, basicFBTypeRT));

		EventManagerUtils.process(eventManager);
		// TODO save the transactions

		checkResults(seq, eventManager);

		final int nT = eventManager.getTransactions().size();
		final FBTransaction t = (FBTransaction) eventManager.getTransactions().get(nT - 1);
		BasicFBType next = null;
		if (!t.getOutputEventOccurrences().isEmpty()) {
			final int nEv = t.getOutputEventOccurrences().size();
			final BasicFBTypeRuntime last = (BasicFBTypeRuntime) (t.getOutputEventOccurrences().get(nEv - 1)
					.getFbRuntime());
			next = last.getBasicfbtype();
		} else {
			next = fb;
		}

		eventManager.getTransactions().clear();
		return next;
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
		final var parameterList = getParametersFromString(parameters);
		for (final List<String> assumption : parameterList) {
			if (!processParameter(assumption.get(0), assumption.get(1), getFBType(captured))) {
				return false;
			}
		}
		return true;
	}

	private static FBType getFBType(final FBRuntimeAbstract captured) {
		if (captured instanceof BasicFBTypeRuntime) {
			return ((BasicFBTypeRuntime) captured).getBasicfbtype();
		}
		if (captured instanceof SimpleFBTypeRuntime) {
			return ((SimpleFBTypeRuntime) captured).getSimpleFBType();
		}
		return null;
	}

	private static List<String> splitParameterList(final String parameters) {
		if (parameters == null) {
			return Collections.emptyList();
		}
		return Arrays.asList(parameters.split(";")); //$NON-NLS-1$
	}

	private static List<List<String>> getParametersFromString(final String parameters) {
		final List<String> statementList = splitParameterList(parameters);
		final var parameterList = new ArrayList<List<String>>();
		for (final String element : statementList) {
			final List<String> statement = Arrays.asList(element.split(":=", 0)); //$NON-NLS-1$
			parameterList.add(statement);
		}
		return parameterList;
	}

	private static boolean processParameter(final String varName, String expectedValue, final FBType fbtype) {
		if ((expectedValue == null) || expectedValue.isBlank()) {
			return true;
		}
		final IInterfaceElement el = fbtype.getInterfaceList().getInterfaceElement(varName);
		if (el instanceof VarDeclaration) {
			final Value val = ((VarDeclaration) el).getValue();
			// special treatment for bools: 1 = TRUE, 0 = FALSE
			if (FordiacKeywords.BOOL.equalsIgnoreCase(((VarDeclaration) el).getTypeName())) {
				final String BOOL_FALSE = "FALSE"; //$NON-NLS-1$
				final String BOOL_TRUE = "TRUE"; //$NON-NLS-1$
				final String BOOL_ZERO = "0"; //$NON-NLS-1$
				final String BOOL_ONE = "1"; //$NON-NLS-1$
				if (BOOL_ONE.equals(val.getValue())) {
					val.setValue(BOOL_TRUE);
				} else if (BOOL_ZERO.equals(val.getValue())) {
					val.setValue(BOOL_FALSE);
				} else if (BOOL_ONE.equals(expectedValue)) {
					expectedValue = BOOL_TRUE;
				} else if (BOOL_ZERO.equals(expectedValue)) {
					expectedValue = BOOL_FALSE;
				}
			}
			// compare the value from the BasicFBType with the primitive
			return (val != null) && expectedValue.equalsIgnoreCase(val.getValue());
		}
		return false;
	}
}
