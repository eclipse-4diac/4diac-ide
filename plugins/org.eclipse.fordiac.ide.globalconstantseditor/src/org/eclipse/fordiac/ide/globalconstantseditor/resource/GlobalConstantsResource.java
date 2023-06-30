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
package org.eclipse.fordiac.ide.globalconstantseditor.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.globalconstantseditor.util.GlobalConstantsPartitioner;
import org.eclipse.fordiac.ide.model.libraryElement.GlobalConstants;
import org.eclipse.fordiac.ide.model.typelibrary.GlobalConstantsEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.structuredtextcore.resource.STCoreResource;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;
import org.eclipse.xtext.resource.FileExtensionProvider;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.util.LazyStringInputStream;

import com.google.inject.Inject;

public class GlobalConstantsResource extends LazyLinkingResource {
	public static final String OPTION_PLAIN_ST = STCoreResource.OPTION_PLAIN_ST;

	@Inject
	private FileExtensionProvider fileExtensionProvider;

	@Inject
	private GlobalConstantsPartitioner partitioner;

	private GlobalConstants globalConstants;

	@Override
	protected void doLoad(final InputStream inputStream, final Map<?, ?> options) throws IOException {
		final var actualOptions = options != null ? options : defaultLoadOptions;
		clearInternalType();
		if (isLoadPlainST(actualOptions) || isLoadLiveScope(actualOptions, inputStream)) {
			super.doLoad(inputStream, actualOptions);
		} else {
			try {
				final IFile typeFile = ResourcesPlugin.getWorkspace().getRoot()
						.getFile(new Path(uri.toPlatformString(true)));
				final TypeEntry typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(typeFile);
				if (typeEntry instanceof final GlobalConstantsEntry globalConstantsEntry) {
					globalConstants = globalConstantsEntry.getType();
				}
			} catch (final Throwable t) {
				throw new IOException("Couldn't load type", t); //$NON-NLS-1$
			}
			final String text = partitioner.combine(globalConstants);
			super.doLoad(new LazyStringInputStream(text, getEncoding()), actualOptions);
		}
		updateInternalType();
	}

	protected boolean isLoadPlainST(final Map<?, ?> options) {
		return fileExtensionProvider.getPrimaryFileExtension().equalsIgnoreCase(getURI().fileExtension())
				|| (options != null && Boolean.TRUE.equals(options.get(OPTION_PLAIN_ST)));
	}

	protected static boolean isLoadLiveScope(final Map<?, ?> options, final InputStream inputStream) {
		return options != null && Boolean.TRUE.equals(options.get(ResourceDescriptionsProvider.LIVE_SCOPE))
				&& inputStream instanceof LazyStringInputStream;
	}

	protected void updateInternalType() {
		clearInternalType();
		if (contents != null && !contents.isEmpty()) {
			if (globalConstants != null) {
				contents.add(EcoreUtil.copy(globalConstants));
			}
			relink();
		}
	}

	protected void clearInternalType() {
		if (contents != null) {
			contents.removeIf(GlobalConstants.class::isInstance);
		}
	}

	@Override
	public synchronized EObject getEObject(final String uriFragment) {
		try {
			return super.getEObject(uriFragment);
		} catch (final IllegalArgumentException e) {
			return null;
		}
	}

	public GlobalConstants getGlobalConstants() {
		return globalConstants;
	}

	public void setGlobalConstants(final GlobalConstants globalConstants) {
		this.globalConstants = globalConstants;
		updateInternalType();
	}

	public Map<Object, Object> getDefaultLoadOptions() {
		if (defaultLoadOptions == null) {
			defaultLoadOptions = new HashMap<>();
		}
		return defaultLoadOptions;
	}
}
