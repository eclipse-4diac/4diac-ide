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
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.impl

import org.eclipse.emf.common.util.EList
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInOutDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInputDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarOutputDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction

import static extension org.eclipse.emf.common.util.ECollections.*

final class STFunctionAnnotations {
	private new() {
	}
	
	def package static EList<INamedElement> getInputParameters(STFunction function) {
		function.varDeclarations.filter[it instanceof STVarInputDeclarationBlock].flatMap[varDeclarations].toEList.unmodifiableEList
	}
	
	def package static EList<INamedElement> getOutputParameters(STFunction function) {
		function.varDeclarations.filter[it instanceof STVarOutputDeclarationBlock].flatMap[varDeclarations].toEList.unmodifiableEList
	}
	
	def package static EList<INamedElement> getInOutParameters(STFunction function) {
		function.varDeclarations.filter[it instanceof STVarInOutDeclarationBlock].flatMap[varDeclarations].toEList.unmodifiableEList
	}
}