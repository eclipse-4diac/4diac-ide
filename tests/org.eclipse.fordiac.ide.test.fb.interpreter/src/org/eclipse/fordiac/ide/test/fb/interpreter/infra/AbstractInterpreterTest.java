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
 *   Antonio Garmendía, Bianca Wiesmayr
 *       - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.fb.interpreter.infra;

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
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsFactory;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.fb.interpreter.mm.utils.EventManagerUtils;
import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
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
import org.eclipse.fordiac.ide.test.fb.interpreter.ModelDeserializer;
import org.eclipse.fordiac.ide.test.fb.interpreter.ModelSerializer;

public class AbstractInterpreterTest {
	private static final String EXTERNAL_INTERFACE = "external"; //$NON-NLS-1$
	private static final String INTERNAL_INTERFACE = "internal"; //$NON-NLS-1$
	private static final String START_STATE = "START"; //$NON-NLS-1$

	static final ModelDeserializer deserializer = new ModelDeserializer();
	static final ModelSerializer serializer = new ModelSerializer();

	protected BasicFBType loadFBType(String name) {
		return loadFBType(name, true);
	}

	protected BasicFBType loadFBType(String name, boolean emptyService) {
		final BasicFBType fbt = (BasicFBType) deserializer
				.loadModel("inputmodels/" + name + ".xmi"); //$NON-NLS-1$ //$NON-NLS-2$
		if (emptyService) {
			fbt.setService(createEmptyServiceModel());
		}
		return fbt;
	}


	protected static org.eclipse.fordiac.ide.model.libraryElement.Service createEmptyServiceModel() {
		final org.eclipse.fordiac.ide.model.libraryElement.Service s = LibraryElementFactory.eINSTANCE.createService();
		final ServiceInterface left = LibraryElementFactory.eINSTANCE.createServiceInterface();
		left.setName(EXTERNAL_INTERFACE);
		final ServiceInterface right = LibraryElementFactory.eINSTANCE.createServiceInterface();
		right.setName(INTERNAL_INTERFACE);
		s.setLeftInterface(left);
		s.setRightInterface(right);
		addServiceSequence(s);
		return s;
	}

	protected static ServiceSequence addServiceSequence(final org.eclipse.fordiac.ide.model.libraryElement.Service s) {
		final ServiceSequence seq = LibraryElementFactory.eINSTANCE.createServiceSequence();
		seq.setName("Test" + s.getServiceSequence().size()); //$NON-NLS-1$
		s.getServiceSequence().add(seq);
		return seq;
	}

	protected ServiceTransaction addTransaction(ServiceSequence seq, FBTransaction fbtrans) {
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

	public static void setVariable(FBType fb, String name, String value) {
		final IInterfaceElement el = fb.getInterfaceList().getInterfaceElement(name);
		if (el instanceof VarDeclaration) {
			final Value val = ((VarDeclaration) el).getValue();
			if (val == null) {
				((VarDeclaration) el).setValue(LibraryElementFactory.eINSTANCE.createValue());
			}
			((VarDeclaration) el).getValue().setValue(value);
		} else {
			throw new IllegalArgumentException("variable does not exist in FB"); //$NON-NLS-1$
		}
	}

	private static Collection<Transaction> createTransactions(BasicFBType fb, ServiceSequence seq,
			BasicFBTypeRuntime runtime) {
		final List<Transaction> transactions = new ArrayList<>();
		for (final ServiceTransaction st : seq.getServiceTransaction()) {
			final String inputEvent = st.getInputPrimitive().getEvent();
			if (inputEvent != null) {
				final EventOccurrence eventOccurrence = OperationalSemanticsFactory.eINSTANCE.createEventOccurrence();
				final Event eventPin = (Event) fb.getInterfaceList().getInterfaceElement(inputEvent);
				if (eventPin == null) {
					throw new IllegalArgumentException("input primitive: event " + inputEvent + " does not exist");  //$NON-NLS-1$//$NON-NLS-2$
				}
				eventOccurrence.setEvent(eventPin);
				final Transaction transaction = OperationalSemanticsFactory.eINSTANCE.createTransaction();
				transaction.setInputEventOccurrence(eventOccurrence);
				// process parameter and set variables
				final String inputParameters = st.getInputPrimitive().getParameters();
				final var paramList = getParametersFromString(inputParameters);
				for (final List<String> parameter : paramList) {
					setVariable(fb, parameter.get(0), parameter.get(1));
				}
				transactions.add(transaction);
			}
		}
		// The first transaction has a copy of the BasicFBTypeRuntime
		final Copier copier = new Copier();
		final BasicFBTypeRuntime copyBasicFBTypeRuntime = (BasicFBTypeRuntime) copier.copy(runtime);
		copier.copyReferences();
		transactions.get(0).getInputEventOccurrence().setFbRuntime(copyBasicFBTypeRuntime);
		return transactions;
	}


	protected static BasicFBType runTest(final BasicFBType fb, final ServiceSequence seq) {
		return runTest(fb, seq, START_STATE);
	}

	protected static BasicFBType runTest(BasicFBType fb, final ServiceSequence seq, final String startStateName) {
		final ResourceSet reset = new ResourceSetImpl();
		final Resource resource = reset
				.createResource(URI.createURI("platform:/resource/" + fb.getName() + ".xmi")); //$NON-NLS-1$ //$NON-NLS-2$
		final EventManager eventManager = OperationalSemanticsFactory.eINSTANCE.createEventManager();
		resource.getContents().add(eventManager);
		final BasicFBTypeRuntime basicFBTypeRT = OperationalSemanticsFactory.eINSTANCE.createBasicFBTypeRuntime();
		basicFBTypeRT.setBasicfbtype(fb);
		// set the start state
		final EList<ECState> stateList = basicFBTypeRT.getBasicfbtype().getECC().getECState();
		final ECState startState = stateList.stream()
				.filter(s -> s.getName().equals(startStateName)).collect(Collectors.toList()).get(0);
		basicFBTypeRT.setActiveState(startState);

		eventManager.getTransactions().addAll(createTransactions(fb, seq, basicFBTypeRT));

		EventManagerUtils.process(eventManager);
		//TODO save the transactions

		checkResults(seq, eventManager);

		final int nT = eventManager.getTransactions().size();
		final Transaction t = eventManager.getTransactions().get(nT - 1);
		BasicFBType next = null;
		if (!t.getOutputEventOccurences().isEmpty()) {
			final int nEv = t.getOutputEventOccurences().size();
			final BasicFBTypeRuntime last = (BasicFBTypeRuntime) (t.getOutputEventOccurences().get(nEv - 1)
					.getFbRuntime());
			next = last.getBasicfbtype();
		} else {
			next = fb;
		}

		eventManager.getTransactions().clear();
		return next;
	}


	private static void checkResults(ServiceSequence seq, EventManager eventManager) {
		final EList<ServiceTransaction> expectedResults = seq.getServiceTransaction();
		final EList<Transaction> results = eventManager.getTransactions();

		assert (expectedResults.size() == results.size()); // correct test data

		for (int i = 0; i < expectedResults.size(); i++) {
			final Transaction result = results.get(i);
			final ServiceTransaction expectedResult = expectedResults.get(i);
			// input event was correctly generated
			assert(result.getInputEventOccurrence().getEvent().getName().equals(expectedResult.getInputPrimitive().getEvent()));

			// no unwanted output event occurrences
			final long outputEvents = expectedResult.getOutputPrimitive().stream()
					.filter(p -> !p.getInterface().getName().toLowerCase().contains(INTERNAL_INTERFACE)).count();
			assert (outputEvents == result.getOutputEventOccurences().size());

			// check all output primitives
			for (int j = 0; j < outputEvents; j++) {
				final OutputPrimitive p = expectedResult.getOutputPrimitive().get(j);
				if (!p.getInterface().getName().toLowerCase().contains(INTERNAL_INTERFACE)) {
					// generated output event is correct
					assert (p.getEvent().equals(result.getOutputEventOccurences().get(j).getEvent().getName()));
					// the associated data is correct
					assert (processParameters(p.getParameters(), result));
				}
			}
		}
	}

	private static boolean processParameters(String parameters, Transaction result) {
		if ((parameters == null) || parameters.isBlank()) {
			return true;
		}
		final int length = result.getOutputEventOccurences().size();
		final BasicFBTypeRuntime captured = (BasicFBTypeRuntime) result.getOutputEventOccurences().get(length - 1)
				.getFbRuntime();
		final var parameterList = getParametersFromString(parameters);
		for (final List<String> assumption : parameterList) {
			if (!processParameter(assumption.get(0), assumption.get(1), captured.getBasicfbtype())) {
				return false;
			}
		}
		return true;
	}

	private static List<String> splitParameterList(String parameters) {
		if (parameters == null) {
			return Collections.emptyList();
		}
		return Arrays.asList(parameters.split(";")); //$NON-NLS-1$
	}

	private static List<List<String>> getParametersFromString(String parameters) {
		final List<String> statementList = splitParameterList(parameters);
		final var parameterList = new ArrayList<List<String>>();
		for (final String element : statementList) {
			final List<String> statement = Arrays.asList(element.split(":=")); //$NON-NLS-1$
			parameterList.add(statement);
		}
		return parameterList;
	}

	private static boolean processParameter(String varName, String expectedValue, BasicFBType basicfbtype) {
		if ((expectedValue == null) || expectedValue.isBlank()) {
			return true;
		}
		final IInterfaceElement el = basicfbtype.getInterfaceList().getInterfaceElement(varName);
		if (el instanceof VarDeclaration) {
			final Value val = ((VarDeclaration) el).getValue();
			// special treatment for bools: 1 = TRUE, 0 = FALSE
			if (FordiacKeywords.BOOL.equalsIgnoreCase(((VarDeclaration) el).getTypeName())) {
				if ("1".equals(val.getValue())) { //$NON-NLS-1$
					val.setValue("TRUE"); //$NON-NLS-1$
				} else if ("0".equals(val.getValue())) { //$NON-NLS-1$
					val.setValue("FALSE"); //$NON-NLS-1$
				} else if ("1".equals(expectedValue)) { //$NON-NLS-1$
					expectedValue = "TRUE"; //$NON-NLS-1$
				} else if ("0".equals(expectedValue)) { //$NON-NLS-1$
					expectedValue = "FALSE"; //$NON-NLS-1$
				}
			}
			// compare the value from the BasicFBType with the primitive
			return (val != null) && expectedValue.equalsIgnoreCase(val.getValue());
		}
		return false;
	}
}
