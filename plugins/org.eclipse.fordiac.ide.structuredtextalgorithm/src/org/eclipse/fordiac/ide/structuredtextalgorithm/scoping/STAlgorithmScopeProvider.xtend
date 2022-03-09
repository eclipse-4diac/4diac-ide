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
package org.eclipse.fordiac.ide.structuredtextalgorithm.scoping

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmPackage
import org.eclipse.xtext.scoping.impl.SimpleScope
import org.eclipse.xtext.scoping.Scopes
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary

/**
 * This class contains custom scoping description.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping
 * on how and when to use it.
 */
class STAlgorithmScopeProvider extends AbstractSTAlgorithmScopeProvider {
	override getScope(EObject context, EReference reference) {
		if (reference == STAlgorithmPackage.Literals.ST_METHOD__RETURN_TYPE) {
			val globalScope = super.getScope(context, reference);
			return new SimpleScope(globalScope, Scopes.scopedElementsFor(DataTypeLibrary.getNonUserDefinedDataTypes()),
				true);
		}
		return super.getScope(context, reference);
	}
}
