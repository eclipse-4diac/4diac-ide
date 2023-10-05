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
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.resource.FordiacTypeResource;
import org.eclipse.fordiac.ide.model.resource.TypeImportDiagnostic;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.structuredtextcore.Messages;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCorePartitioner;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCoreReconciler;
import org.eclipse.xtext.resource.FileExtensionProvider;
import org.eclipse.xtext.util.LazyStringInputStream;

import com.google.inject.Inject;

public class STCoreResource extends LibraryElementXtextResource {
	public static final String OPTION_PLAIN_ST = STCoreResource.class.getName() + ".PLAIN_ST"; //$NON-NLS-1$

	@Inject
	private FileExtensionProvider fileExtensionProvider;

	@Inject
	private STCorePartitioner partitioner;

	@Inject
	private STCoreReconciler reconciler;

	@Override
	protected void doLoad(final InputStream inputStream, final Map<?, ?> options) throws IOException {
		final Map<?, ?> actualOptions = Objects.requireNonNullElse(options, getDefaultLoadOptions());
		if (isLoadPlainST(actualOptions, inputStream)) {
			super.doLoad(inputStream, actualOptions);
			if (getLibraryElement() == null) {
				final TypeEntry typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForURI(uri);
				if (typeEntry != null) {
					setLibraryElement(typeEntry.getTypeEditable());
				}
			} else {
				updateInternalLibraryElement();
			}
		} else { // inputStream contains full XML for library element
			// chain load library element from inputStream
			final LibraryElement libraryElement;
			try {
				final FordiacTypeResource typeResource = new FordiacTypeResource(uri);
				typeResource.load(inputStream, Collections.emptyMap());
				getErrors().addAll(typeResource.getErrors());
				getWarnings().addAll(typeResource.getWarnings());
				if (typeResource.getContents().isEmpty()) {
					return;
				}
				libraryElement = (LibraryElement) typeResource.getContents().get(0);
			} catch (final Exception e) {
				handleTypeLoadException(e);
				return;
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

	@Override
	public void doSave(final OutputStream outputStream, final Map<?, ?> options) throws IOException {
		if (isSavePlainST(options)) {
			super.doSave(outputStream, options);
		} else { // outputStream shall contain full XML for library element
			final LibraryElement libraryElement = getInternalLibraryElement();
			// perform checks
			if (getContents().isEmpty() || libraryElement == null) {
				throw new IllegalStateException(
						"The ST core resource must contain at least one element and have an associated library element"); //$NON-NLS-1$
			}
			// partition
			final var partition = partitioner.partition(this);
			if (partition.isEmpty()) {
				throw new IOException("Cannot partition source"); //$NON-NLS-1$
			}
			// reconcile
			reconciler.reconcile(libraryElement, partition);
			// chain save library element to outputStream
			try {
				final FordiacTypeResource typeResource = new FordiacTypeResource(uri);
				typeResource.getContents().add(EcoreUtil.copy(libraryElement));
				typeResource.save(outputStream, Collections.emptyMap());
			} catch (final Exception e) {
				throw new IOException("Error saving to library element: " + e.getMessage(), e); //$NON-NLS-1$
			}
		}
	}

	protected boolean isLoadPlainST(final Map<?, ?> options, final InputStream inputStream) {
		return fileExtensionProvider.getPrimaryFileExtension().equalsIgnoreCase(uri.fileExtension())
				|| Boolean.TRUE.equals(options.get(OPTION_PLAIN_ST)) || inputStream instanceof LazyStringInputStream;
	}

	protected boolean isSavePlainST(final Map<?, ?> options) {
		return fileExtensionProvider.getPrimaryFileExtension().equalsIgnoreCase(uri.fileExtension())
				|| (options != null && Boolean.TRUE.equals(options.get(OPTION_PLAIN_ST)));
	}

	protected void handleTypeLoadException(final Throwable e) {
		getErrors().add(new TypeImportDiagnostic(e.getMessage(), Messages.STCoreResource_TypeLoadError));
	}

	public Map<Object, Object> getDefaultLoadOptions() {
		if (defaultLoadOptions == null) {
			defaultLoadOptions = new HashMap<>();
		}
		return defaultLoadOptions;
	}
}
