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
import java.util.Set
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm
import org.eclipse.fordiac.ide.model.libraryElement.STMethod
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.structuredtextalgorithm.parser.antlr.STAlgorithmParser
import org.eclipse.fordiac.ide.structuredtextalgorithm.resource.STAlgorithmResource
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSource
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpressionSource
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpressionSource
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STResource
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STTypeDeclaration
import org.eclipse.fordiac.ide.structuredtextcore.validation.STCoreTypeUsageCollector
import org.eclipse.xtext.ParserRule
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.parser.IParseResult
import org.eclipse.xtext.parser.IParser
import org.eclipse.xtext.resource.IResourceFactory
import org.eclipse.xtext.resource.IResourceServiceProvider
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider
import org.eclipse.xtext.validation.Issue

import static extension org.eclipse.fordiac.ide.structuredtextcore.util.STCoreParseUtil.*
import static extension org.eclipse.xtext.EcoreUtil2.*

class StructuredTextParseUtil {
	static final URI SYNTHETIC_URI_FBT = URI.createURI("__synthetic.stalg")
	static final IResourceServiceProvider SERVICE_PROVIDER_FBT = IResourceServiceProvider.Registry.INSTANCE.
		getResourceServiceProvider(SYNTHETIC_URI_FBT)

	static final URI SYNTHETIC_URI_FCT = URI.createURI("__synthetic.stfunc")
	static final IResourceServiceProvider SERVICE_PROVIDER_FCT = IResourceServiceProvider.Registry.INSTANCE.
		getResourceServiceProvider(SYNTHETIC_URI_FCT)

	static final URI SYNTHETIC_URI_GCF = URI.createURI("__synthetic.globalconsts")
	static final IResourceServiceProvider SERVICE_PROVIDER_GCF = IResourceServiceProvider.Registry.INSTANCE.
		getResourceServiceProvider(SYNTHETIC_URI_GCF)

	private new() {
	}

	def static org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm parse(STAlgorithm algorithm,
		List<String> errors, List<String> warnings, List<String> infos) {
		val parser = SERVICE_PROVIDER_FBT.get(IParser) as STAlgorithmParser
		extension val partitioner = SERVICE_PROVIDER_FBT.get(STAlgorithmPartitioner)
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
		val parser = SERVICE_PROVIDER_FBT.get(IParser) as STAlgorithmParser
		extension val partitioner = SERVICE_PROVIDER_FBT.get(STAlgorithmPartitioner)
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
		val parser = SERVICE_PROVIDER_FBT.get(IParser) as STAlgorithmParser
		extension val partitioner = SERVICE_PROVIDER_FBT.get(STAlgorithmPartitioner)
		fbType.combine.parse(parser.grammarAccess.STAlgorithmSourceRule, fbType.name, fbType, errors, warnings, infos)?.
			rootASTElement as STAlgorithmSource
	}

	def static STInitializerExpressionSource validate(String expression, URI uri, INamedElement expectedType, LibraryElement type,
		Collection<? extends EObject> additionalContent, List<Issue> issues) {
		val parser = SERVICE_PROVIDER_FBT.get(IParser) as STAlgorithmParser
		expression.parse(parser.grammarAccess.STInitializerExpressionSourceRule, uri, expectedType, type,
			additionalContent, issues).rootASTElement as STInitializerExpressionSource
	}

	def static STTypeDeclaration validateType(VarDeclaration decl, List<Issue> issues) {
		val parser = SERVICE_PROVIDER_FBT.get(IParser) as STAlgorithmParser
		// use context from FB type since the type declaration is in the context of the FB type (and not an instance)
		val typeVariable = decl.FBNetworkElement?.type?.interfaceList?.getVariable(decl.name) ?: decl
		decl.fullTypeName.parse(parser.grammarAccess.STTypeDeclarationRule, typeVariable?.eResource?.URI, null,
			typeVariable.getContainerOfType(LibraryElement), null, issues).rootASTElement as STTypeDeclaration
	}

	def static STExpressionSource parse(String expression, INamedElement expectedType, LibraryElement type,
		List<String> errors, List<String> warnings, List<String> infos) {
		expression.parse(expectedType, type, null, errors, warnings, infos)
	}

	def static STExpressionSource parse(String expression, INamedElement expectedType, LibraryElement type,
		Collection<? extends EObject> additionalContent, List<String> errors, List<String> warnings,
		List<String> infos) {
		val parser = SERVICE_PROVIDER_FBT.get(IParser) as STAlgorithmParser
		expression.parse(parser.grammarAccess.STExpressionSourceRule, type?.eResource?.URI, expectedType, type,
			additionalContent, errors, warnings, infos)?.rootASTElement as STExpressionSource
	}

	def static STInitializerExpressionSource parse(String expression, URI uri, INamedElement expectedType,
		LibraryElement type, Collection<? extends EObject> additionalContent, List<String> errors,
		List<String> warnings, List<String> infos) {
		val parser = SERVICE_PROVIDER_FBT.get(IParser) as STAlgorithmParser
		expression.parse(parser.grammarAccess.STInitializerExpressionSourceRule, uri, expectedType, type,
			additionalContent, errors, warnings, infos)?.rootASTElement as STInitializerExpressionSource
	}

	def static STTypeDeclaration parseType(VarDeclaration decl, List<String> errors, List<String> warnings,
		List<String> infos) {
		val parser = SERVICE_PROVIDER_FBT.get(IParser) as STAlgorithmParser
		// use context from FB type since the type declaration is in the context of the FB type (and not an instance)
		val typeVariable = decl.FBNetworkElement?.type?.interfaceList?.getVariable(decl.name) ?: decl
		decl.fullTypeName.parse(parser.grammarAccess.STTypeDeclarationRule, typeVariable?.eResource?.URI, null, decl.name,
			typeVariable.getContainerOfType(LibraryElement), null, errors, warnings, infos)?.rootASTElement as STTypeDeclaration
	}
	
	def private static IParseResult parse(String text, ParserRule entryPoint, String name, LibraryElement type,
		List<String> errors, List<String> warnings, List<String> infos) {
		text.parse(entryPoint, type?.eResource?.URI, null, name, type, null, errors, warnings, infos)
	}

	def private static IParseResult parse(String text, ParserRule entryPoint, URI uri, INamedElement expectedType,
		LibraryElement type, Collection<? extends EObject> additionalContent, List<String> errors,
		List<String> warnings, List<String> infos) {
		val issues = newArrayList
		text.parse(entryPoint, uri, expectedType, type, additionalContent, issues).postProcess(errors, warnings, infos,
			issues)
	}

	def private static IParseResult parse(String text, ParserRule entryPoint, URI uri, INamedElement expectedType,
		String name, LibraryElement type, Collection<? extends EObject> additionalContent, List<String> errors,
		List<String> warnings, List<String> infos) {
		val issues = newArrayList
		val parseResult = text.parse(entryPoint, uri, expectedType, type, additionalContent, issues)
		name.postProcess(errors, warnings, infos, issues, parseResult)
	}

	def private static IParseResult parse(String text, ParserRule entryPoint, URI uri, INamedElement expectedType,
		LibraryElement type, Collection<? extends EObject> additionalContent, List<Issue> issues) {
		val resourceSet = SERVICE_PROVIDER_FBT.get(ResourceSet) as XtextResourceSet
		resourceSet.resourceFactoryRegistry.extensionToFactoryMap.put("fbt", SERVICE_PROVIDER_FBT.get(IResourceFactory))
		resourceSet.resourceFactoryRegistry.extensionToFactoryMap.put("FBT", SERVICE_PROVIDER_FBT.get(IResourceFactory))
		resourceSet.resourceFactoryRegistry.extensionToFactoryMap.put("fct", SERVICE_PROVIDER_FCT.get(IResourceFactory))
		resourceSet.resourceFactoryRegistry.extensionToFactoryMap.put("FCT", SERVICE_PROVIDER_FCT.get(IResourceFactory))
		resourceSet.resourceFactoryRegistry.extensionToFactoryMap.put("gcf", SERVICE_PROVIDER_GCF.get(IResourceFactory))
		resourceSet.resourceFactoryRegistry.extensionToFactoryMap.put("GCF", SERVICE_PROVIDER_GCF.get(IResourceFactory))
		SERVICE_PROVIDER_FBT.parse(resourceSet, text, entryPoint, type, additionalContent, issues,
			uri ?: SYNTHETIC_URI_FBT, #{
				XtextResource.OPTION_RESOLVE_ALL -> Boolean.TRUE,
				ResourceDescriptionsProvider.PERSISTED_DESCRIPTIONS -> Boolean.TRUE,
				STAlgorithmResource.OPTION_PLAIN_ST -> Boolean.TRUE,
				STResource.OPTION_EXPECTED_TYPE -> expectedType
			})
	}
	
	def static Set<String> collectUsedTypes(EObject object) {
		val qualifiedNameConverter = SERVICE_PROVIDER_FBT.get(IQualifiedNameConverter)
		SERVICE_PROVIDER_FBT.get(STCoreTypeUsageCollector).collectUsedTypes(object).map[qualifiedNameConverter.toString(it)].toSet
	}
}
