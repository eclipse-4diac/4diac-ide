/*******************************************************************************
 * Copyright (c) 2022 - 2023 Martin Erich Jobst
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

import java.util.Collection
import java.util.List
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm
import org.eclipse.fordiac.ide.model.libraryElement.STMethod
import org.eclipse.fordiac.ide.structuredtextalgorithm.parser.antlr.STAlgorithmParser
import org.eclipse.fordiac.ide.structuredtextalgorithm.resource.STAlgorithmResource
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSource
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpressionSource
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpressionSource
import org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil
import org.eclipse.xtext.ParserRule
import org.eclipse.xtext.parser.IParseResult
import org.eclipse.xtext.parser.IParser
import org.eclipse.xtext.resource.IResourceServiceProvider
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.validation.Issue

class StructuredTextParseUtil extends ParseUtil {
	static final URI SYNTHETIC_URI = URI.createURI("__synthetic.stalg")
	static final IResourceServiceProvider SERVICE_PROVIDER = IResourceServiceProvider.Registry.INSTANCE.
		getResourceServiceProvider(SYNTHETIC_URI)

	private new() {
	}

	def static org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm parse(STAlgorithm algorithm,
		List<String> errors, List<String> warnings, List<String> infos) {
		val parser = SERVICE_PROVIDER.get(IParser) as STAlgorithmParser
		extension val partitioner = SERVICE_PROVIDER.get(STAlgorithmPartitioner)
		switch (root : algorithm.eContainer) {
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
		switch (root : method.eContainer) {
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

	def static List<Issue> validate(String expression, URI uri, INamedElement expectedType, FBType fbType,
		Collection<? extends EObject> additionalContent) {
		val issues = newArrayList
		val parser = SERVICE_PROVIDER.get(IParser) as STAlgorithmParser
		expression.parse(parser.grammarAccess.STInitializerExpressionSourceRule, uri, expectedType, fbType,
			additionalContent, issues)
		return issues
	}

	def static STExpressionSource parse(String expression, INamedElement expectedType, FBType fbType,
		List<String> errors, List<String> warnings, List<String> infos) {
		expression.parse(expectedType, fbType, null, errors, warnings, infos)
	}

	def static STExpressionSource parse(String expression, INamedElement expectedType, FBType fbType,
		Collection<? extends EObject> additionalContent, List<String> errors, List<String> warnings,
		List<String> infos) {
		val parser = SERVICE_PROVIDER.get(IParser) as STAlgorithmParser
		expression.parse(parser.grammarAccess.STExpressionSourceRule, fbType?.eResource?.URI, expectedType, fbType,
			additionalContent, errors, warnings, infos)?.rootASTElement as STExpressionSource
	}

	def static STInitializerExpressionSource parse(String expression, URI uri, INamedElement expectedType,
		FBType fbType, Collection<? extends EObject> additionalContent, List<String> errors, List<String> warnings,
		List<String> infos) {
		val parser = SERVICE_PROVIDER.get(IParser) as STAlgorithmParser
		expression.parse(parser.grammarAccess.STInitializerExpressionSourceRule, uri, expectedType, fbType,
			additionalContent, errors, warnings, infos)?.rootASTElement as STInitializerExpressionSource
	}

	def private static IParseResult parse(String text, ParserRule entryPoint, String name, FBType fbType,
		List<String> errors, List<String> warnings, List<String> infos) {
		text.parse(entryPoint, fbType?.eResource?.URI, null, name, fbType, null, errors, warnings, infos)
	}

	def private static IParseResult parse(String text, ParserRule entryPoint, URI uri, INamedElement expectedType,
		FBType fbType, Collection<? extends EObject> additionalContent, List<String> errors, List<String> warnings,
		List<String> infos) {
		val issues = newArrayList
		text.parse(entryPoint, uri, expectedType, fbType, additionalContent, issues).postProcess(errors, warnings,
			infos, issues)
	}

	def private static IParseResult parse(String text, ParserRule entryPoint, URI uri, INamedElement expectedType,
		String name, FBType fbType, Collection<? extends EObject> additionalContent, List<String> errors,
		List<String> warnings, List<String> infos) {
		val issues = newArrayList
		val parseResult = text.parse(entryPoint, uri, expectedType, fbType, additionalContent, issues)
		name.postProcess(errors, warnings, infos, issues, parseResult)
	}

	def private static IParseResult parse(String text, ParserRule entryPoint, FBType fbType, List<Issue> issues) {
		text.parse(entryPoint, fbType?.eResource?.URI, null, fbType, null, issues)
	}

	def private static IParseResult parse(String text, ParserRule entryPoint, URI uri, INamedElement expectedType,
		FBType fbType, Collection<? extends EObject> additionalContent, List<Issue> issues) {
		val resourceSet = SERVICE_PROVIDER.get(ResourceSet) as XtextResourceSet
		resourceSet.loadOptions.putAll(#{
			STAlgorithmResource.OPTION_PLAIN_ST -> Boolean.TRUE,
			STCoreUtil.OPTION_EXPECTED_TYPE -> expectedType
		})
		SERVICE_PROVIDER.postProcess(resourceSet, text, entryPoint, fbType, additionalContent, issues,
			uri ?: SYNTHETIC_URI)
	}
}
