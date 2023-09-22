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
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui.editor.embedded

import java.util.Collection
import java.util.Map
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement
import org.eclipse.fordiac.ide.structuredtextalgorithm.resource.STAlgorithmResource
import org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil
import org.eclipse.xtext.resource.IResourceServiceProvider
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditor
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditorFactory

import static extension org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil.*
import static extension org.eclipse.xtext.EcoreUtil2.*

final class STAlgorithmEmbeddedEditorUtil {
	static final URI SYNTHETIC_URI = URI.createURI("__synthetic.stalg")
	static final IResourceServiceProvider SERVICE_PROVIDER = IResourceServiceProvider.Registry.INSTANCE.
		getResourceServiceProvider(SYNTHETIC_URI)

	private new() {
	}

	def static EmbeddedEditorFactory getEmbeddedEditorFactory() {
		return SERVICE_PROVIDER.get(EmbeddedEditorFactory)
	}

	def static void updateEditor(EmbeddedEditor editor, INamedElement element) {
		editor.updateEditor(element?.eResource?.URI ?: SYNTHETIC_URI, element.getContainerOfType(LibraryElement), null,
			element.featureType)
	}

	def static void updateEditor(EmbeddedEditor editor, URI uri, LibraryElement type,
		Collection<? extends EObject> additionalContent, INamedElement expectedType) {
		editor.updateEditor(uri, type, additionalContent, #{STCoreUtil.OPTION_EXPECTED_TYPE -> expectedType})
	}

	def static void updateEditor(EmbeddedEditor editor, URI uri, LibraryElement type,
		Collection<? extends EObject> additionalContent, Map<?, ?> loadOptions) {
		editor.document?.internalModify [ resource |
			if (uri !== null) {
				resource.URI = uri
			} else {
				resource.URI = SYNTHETIC_URI
			}
			if (resource instanceof STAlgorithmResource) {
				resource.libraryElement = type
				resource.includeInternalLibraryElement = type instanceof BaseFBType
				resource.additionalContent.clear
				if (additionalContent !== null) {
					resource.additionalContent.addAll(additionalContent)
				}
				if (loadOptions !== null) {
					resource.defaultLoadOptions.putAll(loadOptions)
					resource.resourceSet?.loadOptions?.putAll(loadOptions)
				}
			}
			null
		]
	}
}
