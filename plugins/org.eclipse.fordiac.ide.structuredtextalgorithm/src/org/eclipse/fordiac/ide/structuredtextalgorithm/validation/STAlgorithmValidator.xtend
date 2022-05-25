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
package org.eclipse.fordiac.ide.structuredtextalgorithm.validation

import java.text.MessageFormat
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage
import org.eclipse.fordiac.ide.structuredtextalgorithm.Messages
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSource
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSourceElement
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod
import org.eclipse.xtext.validation.Check
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm

/**
 * This class contains custom validation rules. 
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
class STAlgorithmValidator extends AbstractSTAlgorithmValidator {

	public static final String ISSUE_CODE_PREFIX = "org.eclipse.fordiac.ide.structuredtextalgorithm."; // $NON-NLS-1$
	public static final String DUPLICATE_METHOD_OR_ALGORITHM_NAME = ISSUE_CODE_PREFIX +
		"duplicateAlgorithmOrMethodName"; // $NON-NLS-1$
	
	@Check
	def checkUniqunessOfSTAlgorithmSourceElementNames(STAlgorithmSourceElement sourceElement) {
		val parent = sourceElement.eContainer as STAlgorithmSource
		if (parent.elements.filter[it.name.equalsIgnoreCase(sourceElement.name)].toList.size != 1) {
			error(MessageFormat.format(Messages.STAlgorithmValidator_Duplicate_Method_Or_Algorithm_Name, sourceElement.name),
				sourceElement, LibraryElementPackage.Literals.INAMED_ELEMENT__NAME, DUPLICATE_METHOD_OR_ALGORITHM_NAME,
				sourceElement.getName())
		}
	}
}
