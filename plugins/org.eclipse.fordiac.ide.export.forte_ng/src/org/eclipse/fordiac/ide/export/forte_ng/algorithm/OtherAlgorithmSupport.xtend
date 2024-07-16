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
package org.eclipse.fordiac.ide.export.forte_ng.algorithm

import java.util.Map
import org.eclipse.fordiac.ide.export.ExportException
import org.eclipse.fordiac.ide.export.language.ILanguageSupport
import org.eclipse.fordiac.ide.model.libraryElement.OtherAlgorithm
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

@FinalFieldsConstructor
class OtherAlgorithmSupport implements ILanguageSupport {
	final OtherAlgorithm algorithm
	
	override prepare() { true }
	
	override generate(Map<?, ?> options) throws ExportException '''
		#pragma GCC warning "Algorithm of type: '«algorithm.language»' may lead to unexpected results!"
		#pragma message ("warning Algorithm of type: '«algorithm.language»' may lead to unexpected results!")
		«algorithm.text»
	'''
	
	override getDependencies(Map<?, ?> options) { emptySet }
	
	override getErrors() { emptyList }
	
	override getInfos() { emptyList }
	
	override getWarnings() { emptyList }	
}