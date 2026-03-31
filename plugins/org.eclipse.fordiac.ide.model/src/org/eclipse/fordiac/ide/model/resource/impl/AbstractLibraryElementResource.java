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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.dataexport.AbstractTypeExporter;
import org.eclipse.fordiac.ide.model.dataimport.CommonElementImporter;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.resource.LibraryElementResource;
import org.eclipse.fordiac.ide.model.resource.TypeImportDiagnostic;
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
		final IFile typeFile = getTypeFile();

		try {
			final CommonElementImporter importer = getTypeImporter(inputStream,
					TypeLibraryManager.INSTANCE.getTypeLibrary(typeFile.getProject()));
			importer.loadElement();
			getErrors().addAll(importer.getErrors());
			getWarnings().addAll(importer.getWarnings());
			final LibraryElement element = importer.getElement();
			if (element != null) {
				final var typeEntryForFile = TypeLibraryManager.INSTANCE.getTypeEntryForFile(typeFile);
				if (typeEntryForFile != null) {
					element.setTypeEntry(typeEntryForFile);
				}
				getContents().add(element);
			}
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

	private IFile getTypeFile() throws IOException {
		final IFile typeFile;
		if (uri.isPlatformResource()) {
			typeFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(this.uri.toPlatformString(true)));
		} else if (uri.isFile()) {
			typeFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(uri.toFileString()));
			if (!typeFile.exists()) {
				throw new IOException(
						MessageFormat.format(Messages.FordiacTypeResource_NotInWorkspace, uri.toString()));
			}
		} else {
			throw new IOException(
					MessageFormat.format(Messages.FordiacTypeResource_LoadFromUnsupportedURI, uri.toString()));
		}
		return typeFile;
	}

	protected abstract CommonElementImporter getTypeImporter(InputStream inputStream, TypeLibrary typeLib);

	protected abstract AbstractTypeExporter getTypeExporter(T contentToSave);

}
