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
package org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.impl

import org.eclipse.emf.common.util.EList
import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInOutDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInputDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarOutputDeclarationBlock

import static extension org.eclipse.emf.common.util.ECollections.*

final class STAlgorithmAnnotations {
	private new() {
	}

	def package static EList<INamedElement> getInputParameters(STAlgorithm algorithm) { emptyEList }

	def package static EList<INamedElement> getOutputParameters(STAlgorithm algorithm) { emptyEList }

	def package static EList<INamedElement> getInOutParameters(STAlgorithm algorithm) { emptyEList }

	def package static DataType getReturnType(STAlgorithm algorithm) { null }

	def package static EList<INamedElement> getInputParameters(STMethod method) {
		method.body.varDeclarations.filter(STVarInputDeclarationBlock).flatMap[varDeclarations].toEList.
			unmodifiableEList
	}

	def package static EList<INamedElement> getOutputParameters(STMethod method) {
		method.body.varDeclarations.filter(STVarOutputDeclarationBlock).flatMap[varDeclarations].toEList.
			unmodifiableEList
	}

	def package static EList<INamedElement> getInOutParameters(STMethod method) {
		method.body.varDeclarations.filter(STVarInOutDeclarationBlock).flatMap[varDeclarations].toEList.
			unmodifiableEList
	}
}
