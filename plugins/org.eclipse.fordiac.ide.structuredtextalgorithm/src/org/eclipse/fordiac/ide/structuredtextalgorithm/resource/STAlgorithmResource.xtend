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
package org.eclipse.fordiac.ide.structuredtextalgorithm.resource

import com.google.inject.Inject
import java.io.IOException
import java.io.InputStream
import java.util.List
import java.util.Map
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.Path
import org.eclipse.emf.ecore.EObject
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.STAlgorithmPartitioner
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.linking.lazy.LazyLinkingResource
import org.eclipse.xtext.util.LazyStringInputStream

import static extension org.eclipse.emf.ecore.util.EcoreUtil.*

class STAlgorithmResource extends LazyLinkingResource {
	public static final String OPTION_PLAIN_ST = STAlgorithmResource.name + ".PLAIN_ST";

	@Accessors
	FBType fbType

	@Accessors
	final List<EObject> additionalContent = newArrayList

	@Inject
	extension STAlgorithmPartitioner partitioner

	override doLoad(InputStream inputStream, Map<?, ?> options) throws IOException {
		val actualOptions = options ?: defaultLoadOptions
		clearInternalFBType
		if (actualOptions.loadPlainST) {
			super.doLoad(inputStream, actualOptions)
		} else {
			try {
				val typeFile = ResourcesPlugin.workspace.root.getFile(new Path(uri.toPlatformString(true)))
				val typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(typeFile)
				if (typeEntry !== null) {
					val libraryElement = typeEntry.type
					if (libraryElement instanceof FBType) {
						fbType = libraryElement
					}
				}
			} catch (Throwable t) {
				throw new IOException("Couldn't load FB type", t)
			}
			val text = fbType.combine
			super.doLoad(new LazyStringInputStream(text, encoding), actualOptions)
		}
		updateInternalFBType
	}

	def boolean isLoadPlainST(Map<?, ?> options) {
		"stalg".equalsIgnoreCase(uri.fileExtension) || Boolean.TRUE.equals(options?.get(OPTION_PLAIN_ST) ?: "")
	}

	def setFbType(FBType fbType) {
		this.fbType = fbType
		updateInternalFBType
	}

	def protected void updateInternalFBType() {
		clearInternalFBType
		if (!contents.nullOrEmpty) {
			if(fbType !== null) contents.add(fbType.copy)
			contents.addAll(additionalContent.copyAll)
			relink
		}
	}

	def protected void clearInternalFBType() {
		contents?.removeIf[it instanceof FBType]
		contents?.removeAll(additionalContent)
	}

	override synchronized getEObject(String uriFragment) {
		try {
			super.getEObject(uriFragment)
		} catch (IllegalArgumentException e) {
			null
		}
	}

	def Map<Object, Object> getDefaultLoadOptions() {
		if (defaultLoadOptions === null) {
			defaultLoadOptions = newHashMap
		}
		return defaultLoadOptions
	}
}
