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
import org.eclipse.fordiac.ide.model.libraryElement.STMethod
import org.eclipse.fordiac.ide.structuredtextalgorithm.parser.antlr.STAlgorithmParser
import org.eclipse.fordiac.ide.structuredtextalgorithm.resource.STAlgorithmResource
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSource
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression
import org.eclipse.xtext.ParserRule
import org.eclipse.xtext.diagnostics.Severity
import org.eclipse.xtext.parser.IParseResult
import org.eclipse.xtext.parser.IParser
import org.eclipse.xtext.resource.IResourceServiceProvider
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.util.CancelIndicator
import org.eclipse.xtext.util.LazyStringInputStream
import org.eclipse.xtext.validation.CheckMode
import org.eclipse.xtext.validation.Issue

import static extension org.eclipse.emf.common.util.URI.createPlatformResourceURI
import static extension org.eclipse.emf.ecore.util.EcoreUtil.copy
import static extension org.eclipse.emf.ecore.util.EcoreUtil.getRootContainer

class StructuredTextParseUtil {
	static final URI SYNTHETIC_URI = URI.createURI("__synthetic.stalg")
	static final IResourceServiceProvider SERVICE_PROVIDER = IResourceServiceProvider.Registry.INSTANCE.
		getResourceServiceProvider(SYNTHETIC_URI)
	static final String EXPRESSION_DEFAULT_NAME = "anonymous"

	private new() {
	}

	def static org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm parse(STAlgorithm algorithm,
		List<String> errors, List<String> warnings, List<String> infos) {
		val parser = SERVICE_PROVIDER.get(IParser) as STAlgorithmParser
		extension val partitioner = SERVICE_PROVIDER.get(STAlgorithmPartitioner)
		switch (root : algorithm.rootContainer) {
			BaseFBType:
				(root.combine.parse(parser.grammarAccess.STAlgorithmSourceRule, root.name, root, errors, warnings,
					infos)?.rootASTElement as STAlgorithmSource)?.elements?.filter(
					org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm)?.findFirst [
					name == algorithm.name
				]
			default:
				(algorithm.toSTText.parse(parser.grammarAccess.STAlgorithmRule, algorithm.name, null, errors, warnings,
					infos)?.rootASTElement as org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm)
		}
	}

	def static org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod parse(STMethod method,
		List<String> errors, List<String> warnings, List<String> infos) {
		val parser = SERVICE_PROVIDER.get(IParser) as STAlgorithmParser
		extension val partitioner = SERVICE_PROVIDER.get(STAlgorithmPartitioner)
		switch (root : method.rootContainer) {
			BaseFBType:
				(root.combine.parse(parser.grammarAccess.STAlgorithmSourceRule, root.name, root, errors, warnings,
					infos)?.rootASTElement as STAlgorithmSource)?.elements?.filter(
					org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod)?.findFirst [
					name == method.name
				]
			default:
				(method.toSTText.parse(parser.grammarAccess.STAlgorithmRule, method.name, null, errors, warnings,
					infos)?.rootASTElement as org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod)
		}
	}

	def static STAlgorithmSource parse(BaseFBType fbType, List<String> errors, List<String> warnings,
		List<String> infos) {
		val parser = SERVICE_PROVIDER.get(IParser) as STAlgorithmParser
		extension val partitioner = SERVICE_PROVIDER.get(STAlgorithmPartitioner)
		fbType.combine.parse(parser.grammarAccess.STAlgorithmSourceRule, fbType.name, fbType, errors, warnings, infos)?.
			rootASTElement as STAlgorithmSource
	}

	def static List<Issue> validate(BaseFBType fbType) {
		val issues = newArrayList
		val parser = SERVICE_PROVIDER.get(IParser) as STAlgorithmParser
		extension val partitioner = SERVICE_PROVIDER.get(STAlgorithmPartitioner)
		fbType.combine.parse(parser.grammarAccess.STAlgorithmSourceRule, fbType, issues)
		return issues
	}

	def static STExpression parse(String expression, FBType fbType, List<String> errors, List<String> warnings,
		List<String> infos) {
		val parser = SERVICE_PROVIDER.get(IParser) as STAlgorithmParser
		expression.parse(parser.grammarAccess.STExpressionRule, EXPRESSION_DEFAULT_NAME, fbType, errors, warnings,
			infos)?.rootASTElement as STExpression
	}

	def private static IParseResult parse(String text, ParserRule entryPoint, String name, FBType fbType,
		List<String> errors, List<String> warnings, List<String> infos) {
		val issues = newArrayList
		val parseResult = text.parse(entryPoint, fbType, issues)
		errors?.addAll(issues.filter[severity == Severity.ERROR].map['''«name» at «lineNumber»: «message» '''])
		warnings?.addAll(issues.filter[severity == Severity.WARNING].map['''«name» at «lineNumber»: «message» '''])
		infos?.addAll(issues.filter[severity == Severity.INFO].map['''«name» at «lineNumber»: «message» '''])
		if (issues.exists[severity == Severity.ERROR]) {
			return null
		}
		return parseResult
	}

	def private static IParseResult parse(String text, ParserRule entryPoint, FBType fbType, List<Issue> issues) {
		val resourceSet = SERVICE_PROVIDER.get(ResourceSet) as XtextResourceSet
		val resource = SERVICE_PROVIDER.get(XtextResource) as STAlgorithmResource
		resource.URI = fbType?.paletteEntry?.file?.fullPath?.toString?.createPlatformResourceURI(true) ?: SYNTHETIC_URI
		resourceSet.resources.add(resource)
		resource.entryPoint = entryPoint
		resource.fbType = fbType?.copy
		resource.load(new LazyStringInputStream(text), #{XtextResource.OPTION_RESOLVE_ALL -> Boolean.TRUE})
		val validator = resource.resourceServiceProvider.resourceValidator
		issues.addAll(validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl))
		return resource.parseResult
	}
}
