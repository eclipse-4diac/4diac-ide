/*******************************************************************************
 * Copyright (c) 2015 fortiss GmbH
 *               2020 Johannes Kepler University Linz   
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Changed to a per project Type and Data TypeLibrary
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.structuredtext.scoping

import java.util.ArrayList
import java.util.Collections
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.data.StructuredType
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.AdapterVariable
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.StructuredTextAlgorithm
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.Variable
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.Scopes
import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider
import org.eclipse.xtext.scoping.impl.SimpleScope
import org.eclipse.xtext.util.SimpleAttributeResolver

/**
 * This class contains custom scoping description.
 * 
 * see : http://www.eclipse.org/Xtext/documentation.html#scoping
 * on how and when to use it 
 *
 */
class StructuredTextScopeProvider extends AbstractDeclarativeScopeProvider {

	def scope_DataType(EObject context, EReference ref) {
	    val rootElement = EcoreUtil.getRootContainer(context) as StructuredTextAlgorithm;
	    val res = rootElement.eResource as XtextResource;
        val candidates = (res.resourceSet.resources.get(0)?.contents?.get(0) as FBType).typeLibrary.dataTypeLibrary.dataTypes
		// create scope explicitly since Scopes.scopedElementsFor passes ignoreCase as false
        return new SimpleScope(Scopes.scopedElementsFor(candidates, QualifiedName.wrapper(SimpleAttributeResolver.NAME_RESOLVER)), true)
	}

	def scope_AdapterVariable_var(AdapterVariable context, EReference ref) {
		val type = context.adapter?.type 
		if(type === null) {
			return IScope.NULLSCOPE
		}
		val candidates = type.getScopeCandidates
		
		if(candidates.isEmpty){
			return IScope.NULLSCOPE
		}
		
		// create scope explicitly since Scopes.scopedElementsFor passes ignoreCase as false
		new SimpleScope(Scopes.scopedElementsFor(candidates, QualifiedName.wrapper(SimpleAttributeResolver.NAME_RESOLVER)), true)		
	}
	
	def dispatch List<VarDeclaration> getScopeCandidates(AdapterType context){
		val candidates = new ArrayList<VarDeclaration>
		candidates.addAll(context.interfaceList.inputVars)
		candidates.addAll(context.interfaceList.outputVars)
		return candidates
	}
	
	def dispatch List<VarDeclaration> getScopeCandidates(StructuredType context){
		new ArrayList<VarDeclaration>(context.memberVariables)
	}

	def dispatch List<VarDeclaration> getScopeCandidates(DataType context){
		Collections.emptyList()
	}
	
	def scope_VarDeclaration(Variable context, EReference ref){
		val type = context as DataType
		if(type === null) {
			return IScope.NULLSCOPE
		}
	}

}
