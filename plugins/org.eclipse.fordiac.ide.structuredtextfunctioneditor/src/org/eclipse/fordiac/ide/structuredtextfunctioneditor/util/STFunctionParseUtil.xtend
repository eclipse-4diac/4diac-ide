/*******************************************************************************
 * Copyright (c) 2022, 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.util

import java.util.List
import java.util.Set
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement
import org.eclipse.fordiac.ide.model.libraryElement.STFunctionBody
import org.eclipse.fordiac.ide.structuredtextcore.resource.STCoreResource
import org.eclipse.fordiac.ide.structuredtextcore.validation.STCoreTypeUsageCollector
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.parser.IParseResult
import org.eclipse.xtext.resource.IResourceFactory
import org.eclipse.xtext.resource.IResourceServiceProvider
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider
import org.eclipse.xtext.util.CancelIndicator
import org.eclipse.xtext.validation.CheckMode
import org.eclipse.xtext.validation.Issue

import static extension org.eclipse.fordiac.ide.structuredtextcore.util.STCoreParseUtil.*

final class STFunctionParseUtil {
	static final URI SYNTHETIC_URI_FCT = URI.createURI("__synthetic.stfunc")
	static final IResourceServiceProvider SERVICE_PROVIDER_FCT = IResourceServiceProvider.Registry.INSTANCE.
		getResourceServiceProvider(SYNTHETIC_URI_FCT)

	static final URI SYNTHETIC_URI_GCF = URI.createURI("__synthetic.globalconsts")
	static final IResourceServiceProvider SERVICE_PROVIDER_GCF = IResourceServiceProvider.Registry.INSTANCE.
		getResourceServiceProvider(SYNTHETIC_URI_GCF)

	private new() {
	}

	def static STFunctionSource parse(STFunctionBody body, List<String> errors, List<String> warnings,
		List<String> infos) {
		switch (fbType : body.eContainer) {
			FunctionFBType:
				fbType.parse(errors, warnings, infos)
			default:
				body.text.parseInternal(body.eResource?.URI, null, null, errors, warnings, infos)?.
					rootASTElement as STFunctionSource
		}
	}

	def static STFunctionSource parse(FunctionFBType fbType, List<String> errors, List<String> warnings,
		List<String> infos) {
		extension val partitioner = SERVICE_PROVIDER_FCT.get(STFunctionPartitioner)
		fbType.combine.parseInternal(fbType.eResource?.URI, fbType.name, fbType, errors, warnings, infos)?.
			rootASTElement as STFunctionSource
	}

	def static STFunctionSource parse(String source, String name, List<String> errors, List<String> warnings,
		List<String> infos) {
		source.parseInternal(null, name, null, errors, warnings, infos)?.rootASTElement as STFunctionSource
	}

	def static STFunctionSource parse(URI uri, List<String> errors, List<String> warnings, List<String> infos) {
		val resourceSet = SERVICE_PROVIDER_FCT.get(ResourceSet) as XtextResourceSet
		resourceSet.loadOptions.putAll(#{
			ResourceDescriptionsProvider.PERSISTED_DESCRIPTIONS -> Boolean.TRUE
		})
		val resource = resourceSet.createResource(uri) as XtextResource
		resource.load(resourceSet.loadOptions)
		EcoreUtil.resolveAll(resource)
		val validator = resource.resourceServiceProvider.resourceValidator
		val issues = validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl)
		return resource.parseResult.postProcess(errors, warnings, infos, issues)?.rootASTElement as STFunctionSource
	}

	def private static IParseResult parseInternal(String text, URI uri, String name, LibraryElement type,
		List<String> errors, List<String> warnings, List<String> infos) {
		val issues = newArrayList
		val parseResult = text.parseInternal(uri, type, issues)
		name.postProcess(errors, warnings, infos, issues, parseResult)
	}

	def private static IParseResult parseInternal(String text, URI uri, LibraryElement type, List<Issue> issues) {
		val resourceSet = SERVICE_PROVIDER_FCT.get(ResourceSet) as XtextResourceSet
		resourceSet.resourceFactoryRegistry.extensionToFactoryMap.put("fct", SERVICE_PROVIDER_FCT.get(IResourceFactory))
		resourceSet.resourceFactoryRegistry.extensionToFactoryMap.put("FCT", SERVICE_PROVIDER_FCT.get(IResourceFactory))
		resourceSet.resourceFactoryRegistry.extensionToFactoryMap.put("gcf", SERVICE_PROVIDER_GCF.get(IResourceFactory))
		resourceSet.resourceFactoryRegistry.extensionToFactoryMap.put("GCF", SERVICE_PROVIDER_GCF.get(IResourceFactory))
		SERVICE_PROVIDER_FCT.parse(resourceSet, text, null, type, null, issues, uri ?: SYNTHETIC_URI_FCT, #{
			ResourceDescriptionsProvider.PERSISTED_DESCRIPTIONS -> Boolean.TRUE,
			STCoreResource.OPTION_PLAIN_ST -> Boolean.TRUE
		})
	}
	
	def static Set<String> collectUsedTypes(EObject object) {
		val qualifiedNameConverter = SERVICE_PROVIDER_FCT.get(IQualifiedNameConverter)
		SERVICE_PROVIDER_FCT.get(STCoreTypeUsageCollector).collectUsedTypes(object).map[qualifiedNameConverter.toString(it)].toSet
	}
}
