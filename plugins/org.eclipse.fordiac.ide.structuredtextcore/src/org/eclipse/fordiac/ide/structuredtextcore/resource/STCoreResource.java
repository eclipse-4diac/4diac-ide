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
package org.eclipse.fordiac.ide.structuredtextcore.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.resource.FordiacTypeResource;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCorePartitioner;
import org.eclipse.xtext.resource.FileExtensionProvider;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.util.LazyStringInputStream;

import com.google.inject.Inject;

public class STCoreResource extends LibraryElementXtextResource {
	public static final String OPTION_PLAIN_ST = STCoreResource.class.getName() + ".PLAIN_ST"; //$NON-NLS-1$

	@Inject
	private FileExtensionProvider fileExtensionProvider;

	@Inject
	private STCorePartitioner partitioner;

	@Override
	protected void doLoad(final InputStream inputStream, final Map<?, ?> options) throws IOException {
		final Map<?, ?> actualOptions = Objects.requireNonNullElse(options, getDefaultLoadOptions());
		if (isLoadPlainST(actualOptions) || isLoadLiveScope(actualOptions, inputStream)) {
			super.doLoad(inputStream, actualOptions);
			updateInternalLibraryElement();
		} else { // inputStream contains full XML for library element
			// chain load library element from inputStream
			final LibraryElement libraryElement;
			try {
				final FordiacTypeResource typeResource = new FordiacTypeResource(uri);
				typeResource.load(inputStream, Collections.emptyMap());
				libraryElement = (LibraryElement) typeResource.getContents().get(0);
			} catch (final Exception e) {
				throw new IOException("Couldn't load library element", e); //$NON-NLS-1$
			}
			// then load from combined source
			final String text = partitioner.combine(libraryElement);
			super.doLoad(new LazyStringInputStream(text, getEncoding()), actualOptions);
			// and directly add loaded library element to contents
			// (move from typeResource instead of copying)
			if (contents != null && !contents.isEmpty()) {
				contents.add(libraryElement);
				contents.addAll(EcoreUtil.copyAll(getAdditionalContent()));
				relink();
			}
		}
	}

	protected boolean isLoadPlainST(final Map<?, ?> options) {
		return fileExtensionProvider.getPrimaryFileExtension().equalsIgnoreCase(uri.fileExtension())
				|| Boolean.TRUE.equals(options.get(OPTION_PLAIN_ST));
	}

	protected static boolean isLoadLiveScope(final Map<?, ?> options, final InputStream inputStream) {
		return options.containsKey(ResourceDescriptionsProvider.LIVE_SCOPE)
				&& inputStream instanceof LazyStringInputStream;
	}

	public Map<Object, Object> getDefaultLoadOptions() {
		if (defaultLoadOptions == null) {
			defaultLoadOptions = new HashMap<>();
		}
		return defaultLoadOptions;
	}
}
