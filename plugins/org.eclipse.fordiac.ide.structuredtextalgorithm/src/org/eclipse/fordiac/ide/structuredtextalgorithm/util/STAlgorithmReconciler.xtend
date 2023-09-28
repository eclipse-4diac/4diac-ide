/*******************************************************************************
 * Copyright (c) 2022, 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextalgorithm.util

import java.util.List
import java.util.Optional
import org.eclipse.emf.common.util.EList
import org.eclipse.fordiac.ide.model.helpers.ArraySizeHelper
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.model.libraryElement.ICallable
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm
import org.eclipse.fordiac.ide.model.libraryElement.STMethod
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.structuredtextcore.util.STCorePartition
import org.eclipse.fordiac.ide.structuredtextcore.util.STCoreReconciler

import static extension org.eclipse.emf.ecore.util.EcoreUtil.*

class STAlgorithmReconciler implements STCoreReconciler {

	override reconcile(LibraryElement dest, Optional<? extends STCorePartition> source) {
		if (dest instanceof BaseFBType) {
			if (source.isPresent && source.get instanceof STAlgorithmPartition) {
				dest.callables.reconcile(source.get as STAlgorithmPartition)
			}
		}
	}

	def void reconcile(EList<ICallable> dest, STAlgorithmPartition source) {
		// check duplicates in source or dest (very bad)
		if (checkDuplicates(source.callables)) {
			return; // don't even try to attempt this or risk screwing dest up
		}
		if (checkDuplicates(dest)) {
			dest.clear // dest is broken
		}
		// remove from dest if not in source
		dest.removeIf[alg|!source.callables.exists[name == alg.name]]
		// add or merge/move according to source
		source.callables.forEach [ alg, index |
			val candidate = dest.findFirst[name == alg.name]
			if (candidate !== null && merge(candidate, alg)) {
				dest.move(index, candidate) // move to position (dest must contain at least index algs since we insert as we go)
			} else {
				if(candidate !== null) dest.remove(candidate) // remove candidate (if exists)
				dest.add(index, alg) // insert at position
			}
		]
	}

	def protected dispatch boolean merge(ICallable dest, ICallable source) { false }

	def protected dispatch boolean merge(STAlgorithm dest, STAlgorithm source) {
		dest => [
			comment = source.comment
			text = source.text
		]
		true
	}

	def protected dispatch boolean merge(STMethod dest, STMethod source) {
		dest => [
			comment = source.comment
			text = source.text
			mergeParameters(inputParameters, source.inputParameters)
			mergeParameters(outputParameters, source.outputParameters)
			mergeParameters(inOutParameters, source.inOutParameters)
			returnType = source.returnType
		]
		true
	}
	
	def protected void mergeParameters(EList<INamedElement> dest, List<INamedElement> source) {
		// remove from dest if not in source
		dest.removeIf[param|!source.exists[name == param.name]]
		// add or merge/move according to source
		source.forEach [ param, index |
			val candidate = dest.findFirst[name == param.name]
			if (candidate !== null && mergeParameter(candidate, param)) {
				dest.move(index, candidate) // move to position (dest must contain at least index algs since we insert as we go)
			} else {
				if(candidate !== null) dest.remove(candidate) // remove candidate (if exists)
				dest.add(index, param.copy) // insert at position
			}
		]
	}
	
	def protected dispatch boolean mergeParameter(INamedElement dest, INamedElement source) { false }

	def protected dispatch boolean mergeParameter(VarDeclaration dest, VarDeclaration source) {
		dest => [
			name = source.name
			comment = source.comment
			type = source.type
			ArraySizeHelper.setArraySize(it, ArraySizeHelper.getArraySize(source))
			isInput = source.isIsInput
		]
		true
	}

	def protected boolean checkDuplicates(EList<? extends ICallable> list) {
		list.exists[algorithm|list.exists[it !== algorithm && name == algorithm.name]]
	}
}
