/*******************************************************************************
 * Copyright (c) 2022 Paul Pavlicek
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Paul Pavlicek
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fb.interpreter.api;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.fb.interpreter.mm.utils.ServiceSequenceUtils;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.Primitive;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterface;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public final class ServiceFactory {

	public static final String EXTERNAL_INTERFACE = "external"; //$NON-NLS-1$
	public static final String INTERNAL_INTERFACE = "internal"; //$NON-NLS-1$

	public static Primitive createPrimitiveFrom(final ServiceInterface si, final Event event, final String parameters) {
		if (event.isIsInput()) {
			return createInputPrimitiveFrom(si, event, parameters);
		}
		return createOutputPrimitiveFrom(si, event, parameters);
	}

	public static Primitive createPrimitiveFrom(final ServiceInterface si, final Event event,
			final EList<VarDeclaration> dataSource) {
		if (event.isIsInput()) {
			return createInputPrimitiveFrom(si, event, dataSource);
		}
		return createOutputPrimitiveFrom(si, event, dataSource);
	}

	public static InputPrimitive createInputPrimitiveFrom(final ServiceInterface si,
			final Event event, final EList<VarDeclaration> dataSource) {
		return createInputPrimitiveFrom(si, event, ServiceSequenceUtils.summarizeParameters(dataSource));
	}

	private static InputPrimitive createInputPrimitiveFrom(final ServiceInterface si, final Event event,
			final String parameters) {
		final InputPrimitive inputPrimitive = LibraryElementFactory.eINSTANCE.createInputPrimitive();
		inputPrimitive.setEvent(event.getName());
		inputPrimitive.setInterface(si);
		inputPrimitive.setParameters(parameters);
		return inputPrimitive;
	}

	public static OutputPrimitive createOutputPrimitiveFrom(final ServiceInterface si,
			final Event event, final EList<VarDeclaration> dataSources) {
		return createOutputPrimitiveFrom(si, event, ServiceSequenceUtils.summarizeParameters(dataSources));
	}

	private static OutputPrimitive createOutputPrimitiveFrom(final ServiceInterface si,
			final Event event, final String parameters) {
		final OutputPrimitive outputPrimitive = LibraryElementFactory.eINSTANCE.createOutputPrimitive();
		outputPrimitive.setEvent(event.getName());
		outputPrimitive.setInterface(si);
		outputPrimitive.setParameters(parameters);
		return outputPrimitive;
	}

	public static ServiceTransaction createServiceTransactionFrom(final InputPrimitive input) {
		final ServiceTransaction st = LibraryElementFactory.eINSTANCE.createServiceTransaction();
		st.setInputPrimitive(input);
		return st;

	}

	public static ServiceTransaction createServiceTransactionFrom(final InputPrimitive input,
			final List<OutputPrimitive> outputs) {
		final ServiceTransaction st = createServiceTransactionFrom(input);
		st.getOutputPrimitive().addAll(outputs);
		return st;

	}

	public static ServiceSequence createServiceSequenceFrom(final String name,
			final List<ServiceTransaction> transactions) {
		final ServiceSequence ss = LibraryElementFactory.eINSTANCE.createServiceSequence();
		ss.setName(name);
		ss.getServiceTransaction().addAll(transactions);
		return ss;
	}

	public static ServiceSequence addServiceSequenceTo(final Service service, final String name,
			final List<ServiceTransaction> transactions) {
		final ServiceSequence ss = createServiceSequenceFrom(name, transactions);
		service.getServiceSequence().add(ss);
		return ss;
	}

	public static ServiceInterface createServiceInterfaceFrom(final String name) {
		final ServiceInterface si = LibraryElementFactory.eINSTANCE.createServiceInterface();
		si.setName(name);
		return si;
	}

	public static void createServiceInterface(final Service service, final String name,
			final boolean isLeftInterface) {
		final ServiceInterface si = createServiceInterfaceFrom(name);
		if (isLeftInterface) {
			service.setLeftInterface(si);
		} else {
			service.setRightInterface(si);
		}
	}


	public static Service createDefaultServiceModel() {
		final Service s = LibraryElementFactory.eINSTANCE.createService();
		createServiceInterface(s, EXTERNAL_INTERFACE, true);
		createServiceInterface(s, INTERNAL_INTERFACE, false);
		ServiceSequenceUtils.addServiceSequence(s);
		return s;
	}

	private ServiceFactory() {
		throw new UnsupportedOperationException();
	}
}
