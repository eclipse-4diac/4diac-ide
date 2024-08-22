/**
 * Copyright (c) 2022, 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.test.model.eval.fb;

import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.Predicate;

import org.eclipse.fordiac.ide.globalconstantseditor.GlobalConstantsStandaloneSetup;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.eval.fb.FBEvaluatorExternalEventQueue;
import org.eclipse.fordiac.ide.model.eval.fb.FBEvaluatorFactory;
import org.eclipse.fordiac.ide.model.eval.st.StructuredTextEvaluatorFactory;
import org.eclipse.fordiac.ide.model.eval.value.AnyElementaryValue;
import org.eclipse.fordiac.ide.model.eval.variable.ElementaryVariable;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.STMethod;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.structuredtextalgorithm.STAlgorithmStandaloneSetup;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.STFunctionStandaloneSetup;
import org.junit.jupiter.api.BeforeAll;

public abstract class AbstractFBEvaluatorTest {

	protected static TypeLibrary typeLib;

	@BeforeAll
	public static void setupXtext() {
		typeLib = TypeLibraryManager.INSTANCE.getTypeLibrary(null);
		GlobalConstantsStandaloneSetup.doSetup();
		STFunctionStandaloneSetup.doSetup();
		STAlgorithmStandaloneSetup.doSetup();
		StructuredTextEvaluatorFactory.register();
		FBEvaluatorFactory.register();
	}

	public static FB newFB(final String instanceName, final FBType instanceType) {
		final FB fb = LibraryElementFactory.eINSTANCE.createFB();
		fb.setName(instanceName);
		fb.setTypeEntry(instanceType.getTypeEntry());
		fb.setInterface(instanceType.getInterfaceList().copy());
		return fb;
	}

	public static InterfaceList newInterfaceList(final Collection<Event> events,
			final Collection<VarDeclaration> vars) {
		final InterfaceList iface = LibraryElementFactory.eINSTANCE.createInterfaceList();
		events.stream().filter(IInterfaceElement::isIsInput).forEachOrdered(iface.getEventInputs()::add);
		events.stream().filter(Predicate.not(IInterfaceElement::isIsInput))
				.forEachOrdered(iface.getEventOutputs()::add);
		vars.stream().filter(IInterfaceElement::isIsInput).forEachOrdered(iface.getInputVars()::add);
		vars.stream().filter(Predicate.not(IInterfaceElement::isIsInput)).forEachOrdered(iface.getOutputVars()::add);
		return iface;
	}

	public static Event newEvent(final String name, final boolean input) {
		final Event event = LibraryElementFactory.eINSTANCE.createEvent();
		event.setName(name);
		event.setIsInput(input);
		return event;
	}

	public static VarDeclaration newVarDeclaration(final String name, final DataType type, final boolean input) {
		final VarDeclaration decl = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		decl.setName(name);
		decl.setType(type);
		decl.setIsInput(input);
		return decl;
	}

	public static VarDeclaration newVarDeclaration(final String name, final DataType type, final boolean input,
			final String defaultValue) {
		final VarDeclaration decl = newVarDeclaration(name, type, input);
		final Value value = LibraryElementFactory.eINSTANCE.createValue();
		value.setValue(defaultValue);
		decl.setValue(value);
		return decl;
	}

	public static Attribute newAttribute(final AttributeDeclaration attributeDeclaration) {
		final Attribute attr = LibraryElementFactory.eINSTANCE.createAttribute();
		attr.setName(attributeDeclaration.getName());
		attr.setType(attributeDeclaration.getType());
		attr.setAttributeDeclaration(attributeDeclaration);
		return attr;
	}

	public static Attribute newAttribute(final AttributeDeclaration attributeDeclaration, final String value) {
		final Attribute attr = newAttribute(attributeDeclaration);
		attr.setValue(value);
		return attr;
	}

	public static STAlgorithm newSTAlgorithm(final CharSequence text, final String name) {
		final STAlgorithm alg = LibraryElementFactory.eINSTANCE.createSTAlgorithm();
		alg.setName(name);
		alg.setText(text.toString());
		return alg;
	}

	public static STMethod newSTMethod(final CharSequence text, final String name) {
		final STMethod method = LibraryElementFactory.eINSTANCE.createSTMethod();
		method.setName(name);
		method.setText(text.toString());
		return method;
	}

	public static ElementaryVariable<AnyElementaryValue> newVariable(final AnyElementaryValue value,
			final String name) {
		return new ElementaryVariable<>(name, value.getType(), value);
	}

	public static class TracingFBEvaluatorEventQueue implements FBEvaluatorExternalEventQueue {
		private final Queue<Event> inputEvents;
		private final Queue<Event> outputEvents = new ArrayBlockingQueue<>(1000);

		public TracingFBEvaluatorEventQueue(final Collection<Event> inputEvents) {
			this.inputEvents = new ArrayBlockingQueue<>(inputEvents.size(), false, inputEvents);
		}

		@Override
		public Event receiveInputEvent() throws InterruptedException {
			return inputEvents.poll();
		}

		@Override
		public boolean triggerInputEvent(final Event event) {
			return inputEvents.offer(event);
		}

		@Override
		public boolean sendOutputEvent(final Event event) {
			return outputEvents.offer(event);
		}

		public Queue<Event> getInputEvents() {
			return inputEvents;
		}

		public Queue<Event> getOutputEvents() {
			return outputEvents;
		}
	}
}
