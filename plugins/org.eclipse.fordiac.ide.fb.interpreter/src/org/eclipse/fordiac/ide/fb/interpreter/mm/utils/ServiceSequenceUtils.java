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
 *   Paul Pavlicek - cleanup and factory methods
 *******************************************************************************/
package org.eclipse.fordiac.ide.fb.interpreter.mm.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsFactory;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.fb.interpreter.api.EventOccFactory;
import org.eclipse.fordiac.ide.fb.interpreter.api.RuntimeFactory;
import org.eclipse.fordiac.ide.fb.interpreter.api.TransactionFactory;
import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterface;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public final class ServiceSequenceUtils {

	public static final String EXTERNAL_INTERFACE = "external"; //$NON-NLS-1$
	public static final String INTERNAL_INTERFACE = "internal"; //$NON-NLS-1$
	public static final String START_STATE = "START"; //$NON-NLS-1$

	public static Service createEmptyServiceModel() {
		final Service s = LibraryElementFactory.eINSTANCE.createService();
		final ServiceInterface left = LibraryElementFactory.eINSTANCE.createServiceInterface();
		left.setName(EXTERNAL_INTERFACE);
		final ServiceInterface right = LibraryElementFactory.eINSTANCE.createServiceInterface();
		right.setName(INTERNAL_INTERFACE);
		s.setLeftInterface(left);
		s.setRightInterface(right);
		addServiceSequence(s);
		return s;
	}

	public static ServiceSequence addServiceSequence(final org.eclipse.fordiac.ide.model.libraryElement.Service s) {
		final ServiceSequence seq = LibraryElementFactory.eINSTANCE.createServiceSequence();
		seq.setName("Test" + s.getServiceSequence().size()); //$NON-NLS-1$
		s.getServiceSequence().add(seq);
		return seq;
	}

	public static BasicFBType runTest(final BasicFBType fb, final ServiceSequence seq) throws IllegalArgumentException {
		return runTest(fb, seq, START_STATE);
	}

	public static BasicFBType runTest(final BasicFBType fb, final ServiceSequence seq, final String startStateName)
			throws IllegalArgumentException {
		final ResourceSet reset = new ResourceSetImpl();
		final Resource resource = reset.createResource(URI.createURI("platform:/resource/" + fb.getName() + ".xmi")); //$NON-NLS-1$ //$NON-NLS-2$
		final EventManager eventManager = OperationalSemanticsFactory.eINSTANCE.createEventManager();
		resource.getContents().add(eventManager);

		final FBRuntimeAbstract fbRT = RuntimeFactory.createFrom(fb);
		if (startStateName != null) {
			RuntimeFactory.setStartState(fbRT, startStateName);
		}
		eventManager.getTransactions().addAll(createTransactions(fb, seq, fbRT));

		EventManagerUtils.process(eventManager);

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
			final Transaction result = results.get(i);
			final ServiceTransaction expectedResult = expectedResults.get(i);
			checkTransaction(result, expectedResult);
		}
	}

	private static Collection<Transaction> createTransactions(final FBType fb, final ServiceSequence seq,
			final FBRuntimeAbstract runtime) {
		final List<Transaction> transactions = new ArrayList<>();
		for (final ServiceTransaction st : seq.getServiceTransaction()) {
			final String inputEvent = st.getInputPrimitive().getEvent();
			if (inputEvent != null) {
				final Event eventPin = (Event) fb.getInterfaceList().getInterfaceElement(inputEvent);
				if (eventPin == null) {
					throw new IllegalArgumentException("input primitive: event " + inputEvent + " does not exist"); //$NON-NLS-1$//$NON-NLS-2$
				}
				final EventOccurrence eventOccurrence = EventOccFactory.createFrom(eventPin, EcoreUtil.copy(runtime));
				final Transaction transaction = TransactionFactory.createFrom(eventOccurrence);
				// process parameter and set variables
				final String inputParameters = st.getInputPrimitive().getParameters();
				final var paramList = getParametersFromString(inputParameters);
				for (final List<String> parameter : paramList) {
					setVariable(fb, parameter.get(0), parameter.get(1));
				}
				transactions.add(transaction);
			}
		}
		return transactions;
	}

	public static void setVariable(final FBType fb, final String name, final String value) {
		final IInterfaceElement el = fb.getInterfaceList().getInterfaceElement(name);
		if (!(el instanceof VarDeclaration)) {
			throw new IllegalArgumentException("variable does not exist in FB"); //$NON-NLS-1$
		}

	}

	private static void checkTransaction(final Transaction result, final ServiceTransaction expectedResult) {
		// input event was correctly generated
		if (!result.getInputEventOccurrence().getEvent().getName()
				.equals(expectedResult.getInputPrimitive().getEvent())) {
			throw new IllegalArgumentException("Input event was not generated correctly"); //$NON-NLS-1$
		}

		// no unwanted output event occurrences
		final long outputEvents = expectedResult.getOutputPrimitive().stream()
				.filter(p -> !p.getInterface().getName().toLowerCase().contains(INTERNAL_INTERFACE)).count();
		if (outputEvents != ((FBTransaction) result).getOutputEventOccurrences().size()) {
			throw new IllegalArgumentException("Unwanted output event occurrence"); //$NON-NLS-1$
		}

		// check all output primitives
		for (int j = 0; j < outputEvents; j++) {
			final OutputPrimitive p = expectedResult.getOutputPrimitive().get(j);
			checkOutputPrimitive(result, j, p);
		}
	}

	private static void checkOutputPrimitive(final Transaction result, final int j, final OutputPrimitive p) {
		if (!p.getInterface().getName().toLowerCase().contains(INTERNAL_INTERFACE)) {
			// generated output event is correct
			if (!p.getEvent()
					.equals(((FBTransaction) result).getOutputEventOccurrences().get(j).getEvent().getName())) {
				throw new IllegalArgumentException("Generated output event is incorrect"); //$NON-NLS-1$
			}
			// the associated data is correct
			if (!processParameters(p.getParameters(), result)) {
				throw new IllegalArgumentException("Parameter values do not match the data"); //$NON-NLS-1$
			}
		}
	}

	private static boolean processParameters(final String parameters, final Transaction result) {
		if ((parameters == null) || parameters.isBlank()) {
			return true;
		}
		final int length = ((FBTransaction) result).getOutputEventOccurrences().size();
		final BasicFBTypeRuntime captured = (BasicFBTypeRuntime) ((FBTransaction) result).getOutputEventOccurrences()
				.get(length - 1).getFbRuntime();
		final var parameterList = getParametersFromString(parameters);
		for (final List<String> assumption : parameterList) {
			if (!processParameter(assumption.get(0), assumption.get(1), captured.getBasicfbtype())) {
				return false;
			}
		}
		return true;
	}

	public static List<List<String>> getParametersFromString(final String parameters) {
		final List<String> statementList = splitList(parameters);
		final var parameterList = new ArrayList<List<String>>();
		for (final String element : statementList) {
			final List<String> statement = splitParameter(element);
			parameterList.add(statement);
		}
		return parameterList;
	}

	public static List<String> splitAndCleanList(final String text, final String separator) {
		if (text == null) {
			return Collections.emptyList();
		}
		return Arrays.asList(text.split(separator, 0)).stream().filter(s -> !s.isBlank())
				.map(String::strip).collect(Collectors.toList());
	}

	public static List<String> splitList(final String parameters) {
		return splitAndCleanList(parameters, ";"); //$NON-NLS-1$
	}

	public static String removeKeyword(final String parameters) {
		final List<String> para = splitAndCleanList(parameters, "#"); //$NON-NLS-1$
		if (para.size() == 2) {
			return para.get(1);
		}
		return parameters;
	}

	public static List<String> splitParameter(final String parameter) {
		return splitAndCleanList(parameter, ":="); //$NON-NLS-1$
	}

	private static boolean processParameter(final String varName, String expectedValue, final BasicFBType basicfbtype) {
		if ((expectedValue == null) || expectedValue.isBlank()) {
			return true;
		}
		final IInterfaceElement el = basicfbtype.getInterfaceList().getInterfaceElement(varName);
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

	private static InputPrimitive createInputPrimitive(final FBType fbType, final FBTransaction transaction) {
		final InputPrimitive inputPrimitive = LibraryElementFactory.eINSTANCE.createInputPrimitive();
		inputPrimitive.setEvent(transaction.getInputEventOccurrence().getEvent().getName());
		inputPrimitive.setInterface(fbType.getService().getLeftInterface());
		inputPrimitive.setParameters(summarizeParameters(fbType.getInterfaceList().getInputVars()));
		return inputPrimitive;
	}

	private static String summarizeParameters(final EList<VarDeclaration> inputVars) {
		final StringBuilder builder = new StringBuilder();
		inputVars.forEach(variable -> {
			builder.append(variable.getName());
			builder.append(":="); //$NON-NLS-1$
			if (variable.getValue() != null) {
				builder.append(variable.getValue().getValue());
			}
			builder.append(";\n"); //$NON-NLS-1$
		});
		return builder.toString();
	}

	private static OutputPrimitive createOutputPrimitive(final EventOccurrence outputEvent, final FBType fbType) {
		final OutputPrimitive outputPrimitive = LibraryElementFactory.eINSTANCE.createOutputPrimitive();
		outputPrimitive.setEvent(outputEvent.getEvent().getName());
		outputPrimitive.setInterface(fbType.getService().getLeftInterface());
		outputPrimitive.setParameters(summarizeParameters(fbType.getInterfaceList().getOutputVars()));
		return outputPrimitive;
	}

	public static void convertTransactionToServiceModel(final ServiceSequence seq, final FBType fbType,
			final FBTransaction transaction) {
		final ServiceTransaction serviceTransaction = LibraryElementFactory.eINSTANCE.createServiceTransaction();
		seq.getServiceTransaction().add(serviceTransaction);
		serviceTransaction.setInputPrimitive(
				createInputPrimitive(getFbTypeFromRuntime(transaction.getInputEventOccurrence()), transaction));
		for (final EventOccurrence outputEvent : transaction.getOutputEventOccurrences()) {
			serviceTransaction.getOutputPrimitive()
			.add(createOutputPrimitive(outputEvent, getFbTypeFromRuntime(outputEvent)));
		}
	}

	private static BasicFBType getFbTypeFromRuntime(final EventOccurrence eo) {
		final BasicFBTypeRuntime runtime = (BasicFBTypeRuntime) eo.getFbRuntime();
		return runtime.getBasicfbtype(); // TODO this only works for basic fbs for now!
	}

	private ServiceSequenceUtils() {
		throw new UnsupportedOperationException("utility class should not be instantiated"); //$NON-NLS-1$
	}

}