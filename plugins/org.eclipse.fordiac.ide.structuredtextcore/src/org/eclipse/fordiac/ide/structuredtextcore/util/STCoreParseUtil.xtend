/*******************************************************************************
 * Copyright (c) 2022 - 2023 Martin Erich Jobst
 * 				 2022 Primetals Technologies Austria GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 * 	 Christoph Binder - Extracted code from StructuredTextParseUtil, to enable possibility to reuse this class for multiple xtexteditors
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.util

import java.util.Collection
import java.util.List
import java.util.Map
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement
import org.eclipse.fordiac.ide.structuredtextcore.resource.LibraryElementXtextResource
import org.eclipse.xtext.ParserRule
import org.eclipse.xtext.diagnostics.Severity
import org.eclipse.xtext.parser.IParseResult
import org.eclipse.xtext.resource.IResourceServiceProvider
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider
import org.eclipse.xtext.util.CancelIndicator
import org.eclipse.xtext.util.LazyStringInputStream
import org.eclipse.xtext.validation.CheckMode
import org.eclipse.xtext.validation.Issue

import static extension org.eclipse.emf.common.util.URI.createPlatformResourceURI

final class STCoreParseUtil {

	def static parse(IResourceServiceProvider serviceProvider, XtextResourceSet resourceSet, String text,
		ParserRule entryPoint, LibraryElement type, Collection<? extends EObject> additionalContent, List<Issue> issues,
		URI uri) {
		serviceProvider.parse(resourceSet, text, entryPoint, type, additionalContent, issues, uri, null)
	}
	
	def static parse(IResourceServiceProvider serviceProvider, XtextResourceSet resourceSet, String text,
		ParserRule entryPoint, LibraryElement type, Collection<? extends EObject> additionalContent, List<Issue> issues,
		URI uri, boolean includeInternalLibraryElement) {
		serviceProvider.parse(resourceSet, text, entryPoint, type, additionalContent, issues, uri, null, includeInternalLibraryElement)
	}

	def static parse(IResourceServiceProvider serviceProvider, XtextResourceSet resourceSet, String text,
		ParserRule entryPoint, LibraryElement type, Collection<? extends EObject> additionalContent, List<Issue> issues,
		URI uri, Map<?, ?> loadOptions) {
		return parse(serviceProvider, resourceSet, text, entryPoint, type, additionalContent, issues, uri, loadOptions, type instanceof BaseFBType)
	}
	
	def static parse(IResourceServiceProvider serviceProvider, XtextResourceSet resourceSet, String text,
		ParserRule entryPoint, LibraryElement type, Collection<? extends EObject> additionalContent, List<Issue> issues,
		URI uri, Map<?, ?> loadOptions, boolean includeInternalLibraryElement) {
		resourceSet.loadOptions.putAll(#{
			XtextResource.OPTION_RESOLVE_ALL -> Boolean.TRUE,
			ResourceDescriptionsProvider.PERSISTED_DESCRIPTIONS -> Boolean.TRUE
		})
		val resource = serviceProvider.get(XtextResource) as LibraryElementXtextResource
		resource.URI = type?.typeEntry?.file?.fullPath?.toString?.createPlatformResourceURI(true) ?: uri
		resourceSet.resources.add(resource)
		resource.entryPoint = entryPoint
		resource.libraryElement = type
		resource.includeInternalLibraryElement = includeInternalLibraryElement
		if(!additionalContent.nullOrEmpty) resource.additionalContent.addAll(additionalContent)
		resource.load(new LazyStringInputStream(text), loadOptions ?: resourceSet.loadOptions)
		val validator = resource.resourceServiceProvider.resourceValidator
		issues.addAll(validator.validate(resource, CheckMode.FAST_ONLY, CancelIndicator.NullImpl))
		return resource.parseResult
	}
	
	def static IParseResult postProcess(String name, List<String> errors, List<String> warnings, List<String> infos,
		List<Issue> issues, IParseResult parseResult) {

		errors?.addAll(issues.filter[severity == Severity.ERROR].map['''«name» at «lineNumber»: «message»'''])
		warnings?.addAll(issues.filter[severity == Severity.WARNING].map['''«name» at «lineNumber»: «message»'''])
		infos?.addAll(issues.filter[severity == Severity.INFO].map['''«name» at «lineNumber»: «message»'''])
		if (issues.exists[severity == Severity.ERROR]) {
			return null
		}
		return parseResult
	}

	def static IParseResult postProcess(IParseResult parseResult, List<String> errors, List<String> warnings,
		List<String> infos, List<Issue> issues) {

		errors?.addAll(issues.filter[severity == Severity.ERROR].map[message])
		warnings?.addAll(issues.filter[severity == Severity.WARNING].map[message])
		infos?.addAll(issues.filter[severity == Severity.INFO].map[message])
		if (issues.exists[severity == Severity.ERROR]) {
			return null
		}
		return parseResult
	}

	private new() {
		throw new UnsupportedOperationException
	}
}
