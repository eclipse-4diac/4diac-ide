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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterface;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
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

	public static void setVariable(final FBType fb, final String name, final String value) {
		final IInterfaceElement el = fb.getInterfaceList().getInterfaceElement(name);
		if (!(el instanceof VarDeclaration)) {
			throw new IllegalArgumentException("variable does not exist in FB"); //$NON-NLS-1$
		}
		VariableUtils.setVariable((VarDeclaration) el, value);
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
				.map(ServiceSequenceUtils::removeSemicolon)
				.collect(Collectors.toList());
	}

	private static String removeSemicolon(final String s) {
		return s.replace(";", " ");  //$NON-NLS-1$//$NON-NLS-2$
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

	private static InputPrimitive createInputPrimitive(final FBType dataSource, final FBType destType,
			final FBTransaction transaction) {
		final InputPrimitive inputPrimitive = LibraryElementFactory.eINSTANCE.createInputPrimitive();
		inputPrimitive.setEvent(transaction.getInputEventOccurrence().getEvent().getName());
		inputPrimitive.setInterface(destType.getService().getLeftInterface());
		inputPrimitive.setParameters(summarizeParameters(dataSource.getInterfaceList().getInputVars()));
		return inputPrimitive;
	}

	private static String summarizeParameters(final EList<VarDeclaration> inputVars) {
		final StringBuilder builder = new StringBuilder();
		inputVars.forEach(variable ->
		summarize(builder, variable)
				);
		return builder.toString();
	}

	private static void summarize(final StringBuilder builder, final VarDeclaration variable) {
		builder.append(variable.getName());
		builder.append(":="); //$NON-NLS-1$
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

	private static OutputPrimitive createOutputPrimitive(final EventOccurrence outputEvent, final FBType fbType) {
		final OutputPrimitive outputPrimitive = LibraryElementFactory.eINSTANCE.createOutputPrimitive();
		outputPrimitive.setEvent(outputEvent.getEvent().getName());
		outputPrimitive.setInterface(fbType.getService().getLeftInterface());
		outputPrimitive.setParameters(summarizeParameters(fbType.getInterfaceList().getOutputVars()));
		return outputPrimitive;
	}

	public static void convertTransactionToServiceModel(final ServiceSequence seq, final FBType destType,
			final FBTransaction transaction) {
		final ServiceTransaction serviceTransaction = LibraryElementFactory.eINSTANCE.createServiceTransaction();
		seq.getServiceTransaction().add(serviceTransaction);
		serviceTransaction.setInputPrimitive(createInputPrimitive(
				getFbTypeFromRuntime(transaction.getInputEventOccurrence()), destType, transaction));
		for (final EventOccurrence outputEvent : transaction.getOutputEventOccurrences()) {
			serviceTransaction.getOutputPrimitive()
			.add(createOutputPrimitive(outputEvent, getFbTypeFromRuntime(outputEvent)));
		}
	}

	private static FBType getFbTypeFromRuntime(final EventOccurrence eo) {
		final EObject type = eo.getFbRuntime().getModel();
		if (type instanceof FBType) {
			return (FBType) type;
		}
		return null;
	}

	private ServiceSequenceUtils() {
		throw new UnsupportedOperationException("utility class should not be instantiated"); //$NON-NLS-1$
	}

}