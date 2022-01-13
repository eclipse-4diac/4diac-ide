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
package org.eclipse.fordiac.ide.model.eval.fb

import org.eclipse.fordiac.ide.model.eval.Evaluator
import org.eclipse.fordiac.ide.model.eval.st.StructuredTextEvaluator
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType

class SimpleFBEvaluator extends FBEvaluator<SimpleFBType> {
	Evaluator algorithmEvaluator
	
	new(SimpleFBType type, Evaluator parent) {
		super(type, parent)
		algorithmEvaluator = switch(alg : type.algorithm) {
			STAlgorithm: new StructuredTextEvaluator(alg, this)
			default: throw new UnsupportedOperationException('''Cannot evaluate algorithm «alg.class»''')
		}
	}
	
	override evaluate() {
		algorithmEvaluator.evaluate
	}
	
	override getVariables() {
		emptyList
	}
}
