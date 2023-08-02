/*******************************************************************************
 * Copyright (c) 2022-2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui.editor.embedded

import java.util.Collection
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.eclipse.fordiac.ide.structuredtextalgorithm.parser.antlr.STAlgorithmParser
import org.eclipse.fordiac.ide.structuredtextalgorithm.resource.STAlgorithmResource
import org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtext.parser.IParser
import org.eclipse.xtext.resource.IResourceServiceProvider
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.ui.editor.embedded.IEditedResourceProvider

import static extension org.eclipse.emf.common.util.URI.createPlatformResourceURI

@FinalFieldsConstructor
class STAlgorithmConditionEditedResourceProvider implements IEditedResourceProvider {
	static final URI SYNTHETIC_URI = URI.createURI("__synthetic.stalg")
	static final IResourceServiceProvider SERVICE_PROVIDER = IResourceServiceProvider.Registry.INSTANCE.
		getResourceServiceProvider(SYNTHETIC_URI)

	final FBType fbType
	final Collection<? extends EObject> additionalContent
	final INamedElement expectedType

	override createResource() {
		val resourceSet = SERVICE_PROVIDER.get(ResourceSet) as XtextResourceSet
		resourceSet.loadOptions.putAll(#{
			XtextResource.OPTION_RESOLVE_ALL -> Boolean.TRUE,
			STCoreUtil.OPTION_EXPECTED_TYPE -> expectedType
		})
		val resource = SERVICE_PROVIDER.get(XtextResource) as STAlgorithmResource
		resource.URI = fbType?.typeEntry?.file?.fullPath?.toString?.createPlatformResourceURI(true) ?: SYNTHETIC_URI
		resourceSet.resources.add(resource)
		val parser = SERVICE_PROVIDER.get(IParser) as STAlgorithmParser
		resource.entryPoint = parser.grammarAccess.STExpressionSourceRule
		resource.libraryElement = fbType
		resource.additionalContent.addAll(additionalContent)
		resource.defaultLoadOptions.putAll(#{
			XtextResource.OPTION_RESOLVE_ALL -> Boolean.TRUE,
			STAlgorithmResource.OPTION_PLAIN_ST -> Boolean.TRUE,
			STCoreUtil.OPTION_EXPECTED_TYPE -> expectedType
		})
		return resource
	}
}
