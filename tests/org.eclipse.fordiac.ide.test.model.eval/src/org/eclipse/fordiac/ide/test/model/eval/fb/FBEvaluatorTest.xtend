/*******************************************************************************
 * Copyright (c) 2022-2023 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.model.eval.fb

import java.util.Collection
import java.util.Queue
import java.util.concurrent.ArrayBlockingQueue
import org.eclipse.fordiac.ide.globalconstantseditor.GlobalConstantsStandaloneSetup
import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.eval.fb.FBEvaluatorEventQueue
import org.eclipse.fordiac.ide.model.eval.fb.FBEvaluatorFactory
import org.eclipse.fordiac.ide.model.eval.st.StructuredTextEvaluatorFactory
import org.eclipse.fordiac.ide.model.eval.value.AnyElementaryValue
import org.eclipse.fordiac.ide.model.eval.variable.ElementaryVariable
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration
import org.eclipse.fordiac.ide.model.libraryElement.Event
import org.eclipse.fordiac.ide.model.libraryElement.FB
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager
import org.eclipse.fordiac.ide.structuredtextalgorithm.STAlgorithmStandaloneSetup
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.STFunctionStandaloneSetup
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.junit.jupiter.api.BeforeAll

class FBEvaluatorTest {
	protected static TypeLibrary typeLib

	@BeforeAll
	def static void setupXtext() {
		typeLib = TypeLibraryManager.INSTANCE.getTypeLibrary(null)
		GlobalConstantsStandaloneSetup.doSetup
		STFunctionStandaloneSetup.doSetup
		STAlgorithmStandaloneSetup.doSetup
		StructuredTextEvaluatorFactory.register
		FBEvaluatorFactory.register
	}

	def static FB newFB(String instanceName, FBType instanceType) {
		LibraryElementFactory.eINSTANCE.createFB => [
			name = instanceName
			typeEntry = instanceType.typeEntry
			interface = instanceType.interfaceList.copy
		]
	}

	def static newInterfaceList(Iterable<Event> events, Iterable<VarDeclaration> vars) {
		val iface = LibraryElementFactory.eINSTANCE.createInterfaceList
		iface.eventInputs.addAll(events.filter[isIsInput])
		iface.eventOutputs.addAll(events.filter[!isIsInput])
		iface.inputVars.addAll(vars.filter[isIsInput])
		iface.outputVars.addAll(vars.filter[!isIsInput])
		return iface
	}

	def static newEvent(String name, boolean input) {
		val event = LibraryElementFactory.eINSTANCE.createEvent
		event.name = name;
		event.isInput = input
		return event
	}

	def static newVarDeclaration(String name, DataType type, boolean input) {
		val decl = LibraryElementFactory.eINSTANCE.createVarDeclaration
		decl.name = name;
		decl.type = type;
		decl.isInput = input
		return decl
	}

	def static newVarDeclaration(String name, DataType type, boolean input, String defaultValue) {
		val decl = newVarDeclaration(name, type, input)
		decl.value = LibraryElementFactory.eINSTANCE.createValue => [
			value = defaultValue
		]
		return decl
	}

	def static newAttribute(AttributeDeclaration attributeDeclaration) {
		val attr = LibraryElementFactory.eINSTANCE.createAttribute
		attr.name = attributeDeclaration.name;
		attr.type = attributeDeclaration.type;
		attr.attributeDeclaration = attributeDeclaration
		return attr
	}

	def static newAttribute(AttributeDeclaration attributeDeclaration, String value) {
		val attr = newAttribute(attributeDeclaration)
		attr.value = value
		return attr
	}

	def static newSTAlgorithm(CharSequence text, String name) {
		val alg = LibraryElementFactory.eINSTANCE.createSTAlgorithm
		alg.name = name
		alg.text = text.toString
		return alg
	}

	def static newSTMethod(CharSequence text, String name) {
		val method = LibraryElementFactory.eINSTANCE.createSTMethod
		method.name = name
		method.text = text.toString
		return method
	}

	def static newVariable(AnyElementaryValue value, String name) {
		new ElementaryVariable(name, value.type, value)
	}

	@FinalFieldsConstructor
	static class TracingFBEvaluatorEventQueue implements FBEvaluatorEventQueue {
		@Accessors final Queue<Event> inputEvents
		@Accessors final Queue<Event> outputEvents = new ArrayBlockingQueue(1000)

		new(Collection<Event> inputEvents) {
			this(new ArrayBlockingQueue(inputEvents.size, false, inputEvents))
		}

		override receiveInputEvent() throws InterruptedException {
			inputEvents.poll
		}

		override sendOutputEvent(Event event) {
			outputEvents.offer(event)
		}

	}
}
