/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
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

import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.eval.st.StructuredTextEvaluatorFactory
import org.eclipse.fordiac.ide.model.eval.value.Value
import org.eclipse.fordiac.ide.model.eval.variable.ElementaryVariable
import org.eclipse.fordiac.ide.model.libraryElement.Event
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.structuredtextalgorithm.STAlgorithmStandaloneSetup
import org.junit.jupiter.api.BeforeAll

class FBEvaluatorTest {

	@BeforeAll
	def static void setupXtext() {
		STAlgorithmStandaloneSetup.doSetup
		StructuredTextEvaluatorFactory.register
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

	def static newSTAlgorithm(CharSequence text) {
		val alg = LibraryElementFactory.eINSTANCE.createSTAlgorithm
		alg.name = "TEST"
		alg.text = text.toString
		return alg
	}

	def static newVariable(Value value, String name) {
		new ElementaryVariable(name, value.type, value)
	}
}
