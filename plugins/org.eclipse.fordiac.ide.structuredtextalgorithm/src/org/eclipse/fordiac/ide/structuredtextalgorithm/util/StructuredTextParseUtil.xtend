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
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm
import org.eclipse.fordiac.ide.structuredtextalgorithm.parser.antlr.STAlgorithmParser
import org.eclipse.fordiac.ide.structuredtextalgorithm.resource.STAlgorithmResource
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmBody
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSource
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression
import org.eclipse.xtext.ParserRule
import org.eclipse.xtext.parser.IParseResult
import org.eclipse.xtext.parser.IParser
import org.eclipse.xtext.resource.IResourceServiceProvider
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.util.CancelIndicator
import org.eclipse.xtext.util.LazyStringInputStream
import org.eclipse.xtext.validation.CheckMode

import static extension org.eclipse.emf.ecore.util.EcoreUtil.copy
import static extension org.eclipse.emf.ecore.util.EcoreUtil.getRootContainer
import org.eclipse.fordiac.ide.model.libraryElement.STMethod

class StructuredTextParseUtil {
	static final String SYNTHETIC_URI = "__synthetic.stalg"
	static final IResourceServiceProvider SERVICE_PROVIDER = IResourceServiceProvider.Registry.INSTANCE.
		getResourceServiceProvider(URI.createURI(SYNTHETIC_URI))
	static final String EXPRESSION_DEFAULT_NAME = "anonymous"

	private new() {
	}

	def static STAlgorithmBody parse(STAlgorithm algorithm, List<String> errors) {
		val parser = SERVICE_PROVIDER.get(IParser) as STAlgorithmParser
		extension val partitioner = SERVICE_PROVIDER.get(STAlgorithmPartitioner)
		switch (root : algorithm.rootContainer) {
			BaseFBType:
				(root.combine.parse(parser.grammarAccess.STAlgorithmSourceRule, root.name, root, errors)?.
					rootASTElement as STAlgorithmSource)?.elements?.filter(
					org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm)?.findFirst [
					name == algorithm.name
				]?.body
			default:
				(algorithm.toSTText.parse(parser.grammarAccess.STAlgorithmRule, algorithm.name, null, errors)?.
					rootASTElement as org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm)?.body
		}
	}

	def static org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod parse(STMethod method,
		List<String> errors) {
		val parser = SERVICE_PROVIDER.get(IParser) as STAlgorithmParser
		extension val partitioner = SERVICE_PROVIDER.get(STAlgorithmPartitioner)
		switch (root : method.rootContainer) {
			BaseFBType:
				(root.combine.parse(parser.grammarAccess.STAlgorithmSourceRule, root.name, root, errors)?.
					rootASTElement as STAlgorithmSource)?.elements?.filter(
					org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod)?.findFirst [
					name == method.name
				]
			default:
				(method.toSTText.parse(parser.grammarAccess.STAlgorithmRule, method.name, null, errors)?.
					rootASTElement as org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod)
		}
	}

	def static STAlgorithmSource parse(BaseFBType fbType, List<String> errors) {
		val parser = SERVICE_PROVIDER.get(IParser) as STAlgorithmParser
		extension val partitioner = SERVICE_PROVIDER.get(STAlgorithmPartitioner)
		fbType.combine.parse(parser.grammarAccess.STAlgorithmSourceRule, fbType.name, fbType, errors)?.
			rootASTElement as STAlgorithmSource
	}

	def static STExpression parse(String expression, FBType fbType, List<String> errors) {
		val parser = SERVICE_PROVIDER.get(IParser) as STAlgorithmParser
		expression.parse(parser.grammarAccess.STExpressionRule, EXPRESSION_DEFAULT_NAME, fbType, errors)?.
			rootASTElement as STExpression
	}

	def private static IParseResult parse(String text, ParserRule entryPoint, String name, FBType fbType,
		List<String> errors) {
		val resourceSet = SERVICE_PROVIDER.get(ResourceSet) as XtextResourceSet
		val resource = SERVICE_PROVIDER.get(XtextResource) as STAlgorithmResource
		resource.URI = URI.createPlatformResourceURI(fbType?.paletteEntry?.file?.fullPath?.toString ?: SYNTHETIC_URI,
			true)
		resourceSet.resources.add(resource)
		resource.entryPoint = entryPoint
		resource.fbType = fbType?.copy
		resource.load(new LazyStringInputStream(text), #{XtextResource.OPTION_RESOLVE_ALL -> Boolean.TRUE})
		val validator = resource.resourceServiceProvider.resourceValidator
		val issues = validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl)
		if (!issues.empty) {
			errors?.addAll(issues.map['''«name» at «lineNumber»: «message» '''])
			return null
		}
		return resource.parseResult
	}
}
