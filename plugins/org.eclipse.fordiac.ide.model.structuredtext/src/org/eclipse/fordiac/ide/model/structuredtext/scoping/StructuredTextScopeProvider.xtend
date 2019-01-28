/*******************************************************************************
 * Copyright (c) 2015 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Martin Jobst 
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.structuredtext.scoping

import java.util.ArrayList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.Scopes
import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider
import org.eclipse.xtext.scoping.impl.SimpleScope
import org.eclipse.xtext.util.SimpleAttributeResolver
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.AdapterVariable
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary

/**
 * This class contains custom scoping description.
 * 
 * see : http://www.eclipse.org/Xtext/documentation.html#scoping
 * on how and when to use it 
 *
 */
class StructuredTextScopeProvider extends AbstractDeclarativeScopeProvider {

	def scope_DataType(EObject context, EReference ref) {
		val candidates = DataTypeLibrary.getInstance.dataTypes
		// create scope explicitly since Scopes.scopedElementsFor passes ignoreCase as false
		new SimpleScope(Scopes.scopedElementsFor(candidates, QualifiedName.wrapper(SimpleAttributeResolver.NAME_RESOLVER)), true)
	}

	def scope_AdapterVariable_var(AdapterVariable context, EReference ref) {
		val type = context.adapter?.type as AdapterType
		if(type === null) {
			return IScope.NULLSCOPE
		}
		val candidates = new ArrayList<VarDeclaration>
		candidates.addAll(type.interfaceList.inputVars)
		candidates.addAll(type.interfaceList.outputVars)
		// create scope explicitly since Scopes.scopedElementsFor passes ignoreCase as false
		new SimpleScope(Scopes.scopedElementsFor(candidates, QualifiedName.wrapper(SimpleAttributeResolver.NAME_RESOLVER)), true)
	}

}
