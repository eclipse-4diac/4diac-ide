/********************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Austria,
 *                    Primetals Technologies Austria GmbH, Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Antonio Garmendia,Bianca Wiesmayr
 *                 - initial API and implementation and/or initial documentation
 *  Fabio Gandolfi - adapted for emf compare
 *  Martin Jobst   - gracefully handle exceptions during load or save
 *                 - add function FB type
 *                 - add global constants
 *  Fabio Gandolfi - load types via inputstream
 *  Alois Zoitl    - extracted and adjusted the main loading and saving
 *                   infrastructure from the old FordiacTypeResource
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.resource.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.dataexport.AbstractTypeExporter;
import org.eclipse.fordiac.ide.model.dataimport.CommonElementImporter;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorLibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.resource.LibraryElementResource;
import org.eclipse.fordiac.ide.model.resource.TypeImportDiagnostic;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;

public abstract class AbstractLibraryElementResource<T extends LibraryElement> extends ResourceImpl
		implements LibraryElementResource {

	private final Class<T> typeClass;

	protected AbstractLibraryElementResource(final URI uri, final Class<T> typeClass) {
		super(uri);
		this.typeClass = typeClass;
	}

	@Override
	protected void doLoad(final InputStream inputStream, final Map<?, ?> options) throws IOException {
		final TypeLibrary typeLibrary = getTypeLibrary();
		if (typeLibrary == null) {
			throw new IOException(
					MessageFormat.format(uri != null && uri.isFile() ? Messages.FordiacTypeResource_NotInWorkspace
							: Messages.FordiacTypeResource_LoadFromUnsupportedURI, uri));
		}

		try {
			final CommonElementImporter importer = getTypeImporter(inputStream, typeLibrary);
			importer.loadElement();
			getErrors().addAll(importer.getErrors());
			getWarnings().addAll(importer.getWarnings());
			addLibraryElement(importer.getElement());
		} catch (final TypeImportException e) {
			getErrors().add(new TypeImportDiagnostic(e.getMessage(), Messages.FordiacTypeResource_TypeImportError));
		} catch (final XMLStreamException e) {
			if (e.getLocation() != null) {
				getErrors().add(new TypeImportDiagnostic(e.getMessage(), Messages.FordiacTypeResource_XMLError,
						e.getLocation().getLineNumber()));
			} else {
				getErrors().add(new TypeImportDiagnostic(e.getMessage(), Messages.FordiacTypeResource_XMLError));
			}
		} catch (final IOException e) {
			throw e;
		} catch (final Exception e) {
			throw new IOWrappedException(e);
		}

		if (getContents().isEmpty()) {
			addLibraryElement(ErrorLibraryElementFactory.INSTANCE.create(getFullTypeName(), getLibraryElementEClass()));
		}
	}

	private void addLibraryElement(final LibraryElement element) {
		if (element != null) {
			final TypeEntry typeEntry = getTypeEntry();
			if (typeEntry != null) {
				element.setTypeEntry(typeEntry);
			}
			getContents().add(element);
		}
	}

	private String getFullTypeName() {
		final TypeEntry entry = getTypeEntry();
		if (entry != null) {
			return entry.getFullTypeName();
		}

		if (uri != null) {
			return uri.trimFileExtension().lastSegment();
		}
		return ""; //$NON-NLS-1$
	}

	@Override
	protected void doSave(final OutputStream outputStream, final Map<?, ?> options) throws IOException {
		final T content = getLibraryElement();
		if (content == null) {
			throw new IOException(
					MessageFormat.format(Messages.FordiacTypeResource_UnsupportedContent, !getContents().isEmpty() ?

							contents.get(0).toString() : Messages.FordiacTypeResource_NoContentToSave));
		}

		final AbstractTypeExporter exporter = getTypeExporter(content);

		try (InputStream inputStream = exporter.getFileContent()) {
			inputStream.transferTo(outputStream);
		}
	}

	@Override
	public T getLibraryElement() {
		final LibraryElement content = LibraryElementResource.super.getLibraryElement();
		return (typeClass.isInstance(content)) ? typeClass.cast(content) : null;
	}

	protected TypeEntry getTypeEntry() {
		return TypeLibraryManager.INSTANCE.getTypeEntryForURI(uri);
	}

	protected TypeLibrary getTypeLibrary() {
		return TypeLibraryManager.INSTANCE.getTypeLibraryFromURI(uri);
	}

	protected abstract EClass getLibraryElementEClass();

	protected abstract CommonElementImporter getTypeImporter(InputStream inputStream, TypeLibrary typeLib);

	protected abstract AbstractTypeExporter getTypeExporter(T contentToSave);
}
