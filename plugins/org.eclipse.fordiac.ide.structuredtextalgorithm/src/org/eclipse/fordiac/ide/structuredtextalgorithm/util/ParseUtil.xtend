/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextalgorithm.util

import java.util.Collection
import java.util.List
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.fordiac.ide.structuredtextalgorithm.resource.STAlgorithmResource
import org.eclipse.fordiac.ide.structuredtextcore.FBTypeXtextResource
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

class ParseUtil {
	
	def protected static postProcess(IResourceServiceProvider serviceProvider, XtextResourceSet resourceSet, String text, ParserRule entryPoint, FBType fbType,
		Collection<? extends EObject> additionalContent, List<Issue> issues,URI uri){
		resourceSet.loadOptions.putAll(#{
			XtextResource.OPTION_RESOLVE_ALL -> Boolean.TRUE,
			ResourceDescriptionsProvider.PERSISTED_DESCRIPTIONS -> Boolean.TRUE
		})
		val resource = serviceProvider.get(XtextResource) as FBTypeXtextResource
		resource.URI = fbType?.typeEntry?.file?.fullPath?.toString?.createPlatformResourceURI(true) ?: uri
		resourceSet.resources.add(resource)
		resource.entryPoint = entryPoint
		resource.fbType = fbType
		if(!additionalContent.nullOrEmpty) (resource as STAlgorithmResource).additionalContent.addAll(additionalContent)
		resource.load(new LazyStringInputStream(text), resourceSet.loadOptions)
		val validator = resource.resourceServiceProvider.resourceValidator
		issues.addAll(validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl))
		return resource.parseResult
	}
	
	def protected static IParseResult postProcess(String name, List<String> errors, List<String> warnings,
		List<String> infos, List<Issue> issues, IParseResult parseResult) {

		errors?.addAll(issues.filter[severity == Severity.ERROR].map['''«name» at «lineNumber»: «message» '''])
		warnings?.addAll(issues.filter[severity == Severity.WARNING].map['''«name» at «lineNumber»: «message» '''])
		infos?.addAll(issues.filter[severity == Severity.INFO].map['''«name» at «lineNumber»: «message» '''])
		if (issues.exists[severity == Severity.ERROR]) {
			return null
		}
		return parseResult
	}
}