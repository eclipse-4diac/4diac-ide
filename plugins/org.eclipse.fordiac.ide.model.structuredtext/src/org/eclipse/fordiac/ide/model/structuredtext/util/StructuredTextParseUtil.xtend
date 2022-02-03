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
package org.eclipse.fordiac.ide.model.structuredtext.util

import java.util.List
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.fordiac.ide.model.data.StructuredType
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.model.structuredtext.parser.antlr.StructuredTextParser
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.Expression
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.StructuredTextAlgorithm
import org.eclipse.xtext.parser.IParseResult
import org.eclipse.xtext.resource.IResourceServiceProvider
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.util.CancelIndicator
import org.eclipse.xtext.util.LazyStringInputStream
import org.eclipse.xtext.validation.CheckMode

import static extension org.eclipse.emf.ecore.util.EcoreUtil.getRootContainer

class StructuredTextParseUtil {
	static final String SYNTHETIC_URI_NAME = "__synthetic" // $NON-NLS-1$
	static final String URI_SEPERATOR = "." // $NON-NLS-1$
	static final String FB_URI_EXTENSION = "xtextfbt" // $NON-NLS-1$
	static final String ST_URI_EXTENSION = "st" // $NON-NLS-1$
	static final IResourceServiceProvider SERVICE_PROVIDER = IResourceServiceProvider.Registry.INSTANCE.
		getResourceServiceProvider(URI.createURI(SYNTHETIC_URI_NAME + URI_SEPERATOR + ST_URI_EXTENSION))

	private new() {
	}

	def static StructuredTextAlgorithm parse(STAlgorithm algorithm, List<String> errors) {
		algorithm.text.parse(false, algorithm.name, algorithm.rootContainer as BaseFBType, errors)?.
			rootASTElement as StructuredTextAlgorithm
	}

	def static Expression parse(String expression, BaseFBType fbType, List<String> errors) {
		expression.parse(true, "anonymous", fbType, errors)?.rootASTElement as Expression
	}

	def static IParseResult parse(String text, boolean singleExpression, String name, BaseFBType fbType,
		List<String> errors) {
		val resourceSet = SERVICE_PROVIDER.get(ResourceSet) as XtextResourceSet
		createFBResource(resourceSet, fbType)
		// create resource for algorithm
		val resource = resourceSet.createResource(resourceSet.computeUnusedUri(ST_URI_EXTENSION)) as XtextResource
		if (singleExpression) {
			val parser = resource.parser as StructuredTextParser
			resource.entryPoint = parser.grammarAccess.expressionRule
		}
		resource.load(new LazyStringInputStream(text), #{XtextResource.OPTION_RESOLVE_ALL -> Boolean.TRUE})
		if (!singleExpression) {
			val stalg = resource.parseResult.rootASTElement as StructuredTextAlgorithm
			stalg.localVariables.forEach[createStructResource(resourceSet)]
		}
		val validator = resource.resourceServiceProvider.resourceValidator
		val issues = validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl)
		if (!issues.empty) {
			errors?.addAll(issues.map['''«IF !singleExpression»«name» at «lineNumber»: «ENDIF»«message» '''])
			return null
		}
		return resource.parseResult
	}

	def private static createFBResource(XtextResourceSet resourceSet, BaseFBType fbType) {
		// create resource for function block and add copy
		val fbResource = resourceSet.createResource(resourceSet.computeUnusedUri(FB_URI_EXTENSION))
		fbResource.contents.add(fbType)
		fbType.interfaceList.sockets.forEach[createAdapterResource(resourceSet)];
		fbType.interfaceList.plugs.forEach[createAdapterResource(resourceSet)];
		fbType.interfaceList.inputVars.forEach[createStructResource(resourceSet)];
		fbType.interfaceList.outputVars.forEach[createStructResource(resourceSet)];
		fbType.internalVars.forEach[createStructResource(resourceSet)];
	}

	def private static void createAdapterResource(AdapterDeclaration adapter, XtextResourceSet resourceSet) {
		val adapterResource = resourceSet.createResource(resourceSet.computeUnusedUri(FB_URI_EXTENSION));
		adapterResource.contents.add(adapter.type.adapterFBType);
	}

	def private static void createStructResource(VarDeclaration variable, XtextResourceSet resourceSet) {
		if (variable.type instanceof StructuredType) {
			val structResource = resourceSet.createResource(resourceSet.computeUnusedUri(FB_URI_EXTENSION));
			val type = variable.type as StructuredType;
			structResource.contents.add(type);
			type.memberVariables.forEach[createStructResource(resourceSet)];
		}
	}

	def private static URI computeUnusedUri(ResourceSet resourceSet, String fileExtension) {
		for (i : 0 ..< Integer.MAX_VALUE) {
			val syntheticUri = URI.createURI(SYNTHETIC_URI_NAME + i + URI_SEPERATOR + fileExtension) // $NON-NLS-1$
			if (resourceSet.getResource(syntheticUri, false) === null) {
				return syntheticUri
			}
		}
		throw new IllegalStateException()
	}
}
