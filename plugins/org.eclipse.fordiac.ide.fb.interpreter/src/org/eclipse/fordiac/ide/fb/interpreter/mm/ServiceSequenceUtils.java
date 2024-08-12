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
package org.eclipse.fordiac.ide.fb.interpreter.mm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.api.ServiceFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public final class ServiceSequenceUtils {

	public static final String PARAMETER_SEPARATOR = ";"; //$NON-NLS-1$
	public static final String ASSIGNMENT_OPERATOR = ":="; //$NON-NLS-1$
	public static final String EXTERNAL_INTERFACE = "external"; //$NON-NLS-1$
	public static final String INTERNAL_INTERFACE = "internal"; //$NON-NLS-1$
	public static final String START_STATE = "START"; //$NON-NLS-1$

	public static ServiceSequence addServiceSequence(final org.eclipse.fordiac.ide.model.libraryElement.Service s) {
		return ServiceFactory.addServiceSequenceTo(s, "Test" + s.getServiceSequence().size(), //$NON-NLS-1$
				Collections.emptyList());
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
		return Arrays.asList(text.split(separator, 0)).stream().filter(s -> !s.isBlank()).map(String::strip)
				.map(ServiceSequenceUtils::removeSemicolon).toList();
	}

	private static String removeSemicolon(final String s) {
		return s.replace(PARAMETER_SEPARATOR, " "); //$NON-NLS-1$
	}

	public static List<String> splitList(final String parameters) {
		return splitAndCleanList(parameters, PARAMETER_SEPARATOR);
	}

	public static String removeKeyword(final String parameters) {
		final List<String> para = splitAndCleanList(parameters, "#"); //$NON-NLS-1$
		if (para.size() == 2) {
			return para.get(1);
		}
		return parameters;
	}

	public static List<String> splitParameter(final String parameter) {
		return splitAndCleanList(parameter, ASSIGNMENT_OPERATOR);
	}

	public static String summarizeParameters(final Iterable<VarDeclaration> inputVars) {
		final StringBuilder builder = new StringBuilder();
		inputVars.forEach(variable -> summarizeParameter(builder, variable));
		return builder.toString();
	}

	private static void summarizeParameter(final StringBuilder builder, final VarDeclaration variable) {
		builder.append(variable.getName());
		builder.append(ASSIGNMENT_OPERATOR);
		if (variable.getValue() != null) {
			final String value = variable.getValue().getValue();
			if (value.contains("#")) { //$NON-NLS-1$
				builder.append(value);
			} else {
				builder.append(variable.getTypeName() + "#" + value); //$NON-NLS-1$
			}
		}
		builder.append(";\n"); //$NON-NLS-1$
	}

	public static String summarizeParameter(final VarDeclaration variable) {
		final StringBuilder builder = new StringBuilder();
		summarizeParameter(builder, variable);
		return builder.toString();
	}

	public static void convertTransactionToServiceModel(final ServiceSequence seq, final FBType destType,
			final FBTransaction transaction) {
		final InputPrimitive ip = ServiceFactory.createInputPrimitiveFrom(destType.getService().getLeftInterface(),
				transaction.getInputEventOccurrence().getEvent(), transaction.getInputVariables());
		final List<OutputPrimitive> ops = new ArrayList<>();
		for (final EventOccurrence outputEvent : transaction.getOutputEventOccurrences()) {
			final FBType typeInterface = seq.getService().getFBType();
			final FBType typeRuntime = getFbTypeFromRuntime(outputEvent);
			if (typeInterface != null && typeRuntime != null) {
				ops.add(ServiceFactory.createOutputPrimitiveFrom(typeInterface.getService().getLeftInterface(),
						outputEvent.getEvent(), typeRuntime.getInterfaceList().getOutputVars()));
			}
		}
		final ServiceTransaction serviceTransaction = ServiceFactory.createServiceTransactionFrom(ip, ops);
		seq.getServiceTransaction().add(serviceTransaction);
	}

	public static void convertEventManagerToServiceModel(final ServiceSequence seq, final FBType destType,
			final EventManager manager, final List<String> parameter) {
		manager.getTransactions().forEach(tr -> convertTransactionToServiceModel(seq, destType, (FBTransaction) tr));
		for (int i = 0; i < parameter.size(); i++) {
			seq.getServiceTransaction().get(i).getInputPrimitive().setParameters(parameter.get(i));
		}
	}

	private static FBType getFbTypeFromRuntime(final EventOccurrence eo) {
		final EObject type = eo.getFbRuntime().getModel();
		if (type instanceof final FBType fbtype) {
			return fbtype;
		}
		return null;
	}

	public static List<Event> getEvents(final FBType type, final List<String> eventNames) {
		return eventNames.stream().map(name -> findEvent(type, name)).map(Event.class::cast).filter(Objects::nonNull)
				.toList();
	}

	private static Event findEvent(final FBType fbType, final String eventName) {
		final IInterfaceElement event = fbType.getInterfaceList().getInterfaceElement(eventName);
		if (!(event instanceof final Event ev) || !event.isIsInput()) {
			throw new IllegalArgumentException("input primitive: event " + eventName + " does not exist"); //$NON-NLS-1$//$NON-NLS-2$
		}
		return ev;
	}

	private ServiceSequenceUtils() {
		throw new UnsupportedOperationException("utility class should not be instantiated"); //$NON-NLS-1$
	}

}