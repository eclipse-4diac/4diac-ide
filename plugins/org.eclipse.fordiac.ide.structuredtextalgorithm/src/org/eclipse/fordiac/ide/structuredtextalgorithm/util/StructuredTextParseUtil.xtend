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
package org.eclipse.fordiac.ide.structuredtextalgorithm.util

import java.util.List
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm
import org.eclipse.fordiac.ide.structuredtextalgorithm.parser.antlr.STAlgorithmParser
import org.eclipse.fordiac.ide.structuredtextalgorithm.resource.STAlgorithmResource
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmBody
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression
import org.eclipse.xtext.parser.IParseResult
import org.eclipse.xtext.resource.IResourceServiceProvider
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.util.CancelIndicator
import org.eclipse.xtext.util.LazyStringInputStream
import org.eclipse.xtext.validation.CheckMode

import static extension org.eclipse.emf.ecore.util.EcoreUtil.copy
import static extension org.eclipse.emf.ecore.util.EcoreUtil.getRootContainer

class StructuredTextParseUtil {
	static final String SYNTHETIC_URI = "__synthetic.stalg"
	static final IResourceServiceProvider SERVICE_PROVIDER = IResourceServiceProvider.Registry.INSTANCE.
		getResourceServiceProvider(URI.createURI(SYNTHETIC_URI))

	private new() {
	}

	def static STAlgorithmBody parse(STAlgorithm algorithm, List<String> errors) {
		algorithm.text.parse(false, algorithm.name, switch (root : algorithm.rootContainer) { FBType: root }, errors)?.
			rootASTElement as STAlgorithmBody
	}

	def static STExpression parse(String expression, FBType fbType, List<String> errors) {
		expression.parse(true, "anonymous", fbType, errors)?.rootASTElement as STExpression
	}

	def static IParseResult parse(String text, boolean singleExpression, String name, FBType fbType,
		List<String> errors) {
		val resourceSet = SERVICE_PROVIDER.get(ResourceSet) as XtextResourceSet
		val resource = SERVICE_PROVIDER.get(XtextResource) as STAlgorithmResource
		resource.URI = URI.createPlatformResourceURI(fbType?.paletteEntry?.file?.fullPath?.toString ?: SYNTHETIC_URI, true)
		resourceSet.resources.add(resource)
		val parser = resource.parser as STAlgorithmParser
		resource.entryPoint = if (singleExpression)
			parser.grammarAccess.STExpressionRule
		else
			parser.grammarAccess.STAlgorithmBodyRule
		resource.fbType = fbType?.copy
		resource.load(new LazyStringInputStream(text), #{XtextResource.OPTION_RESOLVE_ALL -> Boolean.TRUE})
		val validator = resource.resourceServiceProvider.resourceValidator
		val issues = validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl)
		if (!issues.empty) {
			errors?.addAll(issues.map['''«IF !singleExpression»«name» at «lineNumber»: «ENDIF»«message» '''])
			return null
		}
		return resource.parseResult
	}
}
