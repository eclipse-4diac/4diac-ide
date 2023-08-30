/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.resource

import com.google.inject.Inject
import java.io.IOException
import java.io.InputStream
import java.util.Map
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.Path
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager
import org.eclipse.fordiac.ide.structuredtextcore.util.STCorePartitioner
import org.eclipse.xtext.resource.FileExtensionProvider
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider
import org.eclipse.xtext.util.LazyStringInputStream

class STCoreResource extends LibraryElementXtextResource {
	public static final String OPTION_PLAIN_ST = STCoreResource.name + ".PLAIN_ST";

	@Inject
	FileExtensionProvider fileExtensionProvider;

	@Inject
	extension STCorePartitioner partitioner

	override doLoad(InputStream inputStream, Map<?, ?> options) throws IOException {
		val actualOptions = options ?: defaultLoadOptions
		clearInternalLibraryElement
		if (actualOptions.loadPlainST || isLoadLiveScope(actualOptions, inputStream)) {
			super.doLoad(inputStream, actualOptions)
		} else {
			try {
				val typeFile = ResourcesPlugin.workspace.root.getFile(new Path(uri.toPlatformString(true)))
				val typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(typeFile)
				if (typeEntry !== null) {
					libraryElement = typeEntry.type
				}
			} catch (Throwable t) {
				throw new IOException("Couldn't load FB type", t)
			}
			val text = libraryElement.combine
			super.doLoad(new LazyStringInputStream(text, encoding), actualOptions)
		}
		updateInternalLibraryElement
	}

	def protected boolean isLoadPlainST(Map<?, ?> options) {
		fileExtensionProvider.primaryFileExtension.equalsIgnoreCase(uri.fileExtension) || Boolean.TRUE.equals(options?.get(OPTION_PLAIN_ST) ?: "")
	}

	def protected boolean isLoadLiveScope(Map<?, ?> options, InputStream inputStream) {
		options?.get(ResourceDescriptionsProvider.LIVE_SCOPE) == Boolean.TRUE && inputStream instanceof LazyStringInputStream
	}

	def Map<Object, Object> getDefaultLoadOptions() {
		if (defaultLoadOptions === null) {
			defaultLoadOptions = newHashMap
		}
		return defaultLoadOptions
	}
}
