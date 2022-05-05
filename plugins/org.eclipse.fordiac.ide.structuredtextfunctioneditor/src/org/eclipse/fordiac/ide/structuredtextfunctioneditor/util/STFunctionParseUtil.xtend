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
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.util

import java.util.List
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource
import org.eclipse.xtext.ParserRule
import org.eclipse.xtext.diagnostics.Severity
import org.eclipse.xtext.parser.IParseResult
import org.eclipse.xtext.resource.IResourceServiceProvider
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.util.CancelIndicator
import org.eclipse.xtext.util.LazyStringInputStream
import org.eclipse.xtext.validation.CheckMode

final class STFunctionParseUtil {
	static final String SYNTHETIC_URI = "__synthetic.stfunc"
	static final IResourceServiceProvider SERVICE_PROVIDER = IResourceServiceProvider.Registry.INSTANCE.
		getResourceServiceProvider(URI.createURI(SYNTHETIC_URI))

	private new() {
	}

	def static STFunctionSource parse(String source, String name, List<String> errors, List<String> warnings, List<String> infos) {
		source.parseInternal(name, null, errors, warnings, infos)?.rootASTElement as STFunctionSource
	}

	def static STFunctionSource parse(URI uri, List<String> errors, List<String> warnings, List<String> infos) {
		uri.parseInternal(null, errors, warnings, infos)?.rootASTElement as STFunctionSource
	}

	def private static IParseResult parseInternal(String text, String name, ParserRule entryPoint,
		List<String> errors, List<String> warnings, List<String> infos) {
		val resourceSet = SERVICE_PROVIDER.get(ResourceSet) as XtextResourceSet
		val resource = SERVICE_PROVIDER.get(XtextResource)
		resource.URI = URI.createPlatformResourceURI(SYNTHETIC_URI, true)
		resourceSet.resources.add(resource)
		resource.entryPoint = entryPoint
		resource.load(new LazyStringInputStream(text), #{XtextResource.OPTION_RESOLVE_ALL -> Boolean.TRUE})
		val validator = resource.resourceServiceProvider.resourceValidator
		val issues = validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl)
		errors?.addAll(issues.filter[severity == Severity.ERROR].map['''«name» at «lineNumber»: «message» '''])
		warnings?.addAll(issues.filter[severity == Severity.WARNING].map['''«name» at «lineNumber»: «message» '''])
		infos?.addAll(issues.filter[severity == Severity.INFO].map['''«name» at «lineNumber»: «message» '''])
		if (issues.exists[severity == Severity.ERROR]) {
			return null
		}
		return resource.parseResult
	}

	def private static IParseResult parseInternal(URI uri, ParserRule entryPoint, List<String> errors, List<String> warnings, List<String> infos) {
		val resourceSet = SERVICE_PROVIDER.get(ResourceSet) as XtextResourceSet
		val resource = resourceSet.createResource(uri) as XtextResource
		resource.entryPoint = entryPoint
		resource.load(#{XtextResource.OPTION_RESOLVE_ALL -> Boolean.TRUE})
		val validator = resource.resourceServiceProvider.resourceValidator
		val issues = validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl)
		errors?.addAll(issues.filter[severity == Severity.ERROR].map['''«uri.toString» at «lineNumber»: «message» '''])
		warnings?.addAll(issues.filter[severity == Severity.WARNING].map['''«uri.toString» at «lineNumber»: «message» '''])
		infos?.addAll(issues.filter[severity == Severity.INFO].map['''«uri.toString» at «lineNumber»: «message» '''])
		if (issues.exists[severity == Severity.ERROR]) {
			return null
		}
		return resource.parseResult
	}
}
