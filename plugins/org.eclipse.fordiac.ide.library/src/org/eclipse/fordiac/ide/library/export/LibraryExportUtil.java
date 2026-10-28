/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.export;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fordiac.ide.library.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.resource.FordiacTypeResourceFactory;
import org.eclipse.fordiac.ide.model.typelibrary.AttributeTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.model.util.LibraryElementHashException;
import org.eclipse.fordiac.ide.model.value.StringValueConverter;

public class LibraryExportUtil {

	public static void exportTypeAsSIFB(final TypeEntry entry, final File destination)
			throws LibraryElementHashException, IOException {
		final LibraryElement element = entry.getType();

		final ExportCopier copier = new ExportCopier();
		final var copy = (LibraryElement) copier.copy(element);
		copier.copyReferences();

		final TypeLibrary typeLib = entry.getTypeLibrary();
		final AttributeTypeEntry hashDecl = Optional
				.ofNullable(typeLib.getAttributeTypeEntry(TypeLibraryTags.TYPE_HASH_ATTRIBUTE_FULL_NAME))
				.orElse(typeLib.getAttributeTypeEntry(TypeLibraryTags.TYPE_HASH_ATTRIBUTE_NAME));

		if (hashDecl == null) {
			throw new LibraryElementHashException(Messages.LibraryExporter_MissingTypeHashAttribute);
		}

		copy.setAttribute(hashDecl.getType(), StringValueConverter.INSTANCE.toString(entry.getTypeHash()), null);

		final Resource resource = FordiacTypeResourceFactory.INSTANCE
				.createResource(URI.createFileURI(new File(destination, getExportFileName(entry)).getAbsolutePath()));
		resource.getContents().add(copy);
		resource.save(null);
	}

	private static String getExportFileName(final TypeEntry entry) {
		final IFile file = entry.getFile();

		if (entry.getTypeEClass() == LibraryElementPackage.Literals.FUNCTION_FB_TYPE) {
			return file.getFullPath().removeFileExtension()
					.addFileExtension(TypeLibraryTags.FB_TYPE_FILE_ENDING.toLowerCase()).lastSegment();
		}

		return file.getName();
	}

	private LibraryExportUtil() {
		throw new UnsupportedOperationException();
	}

}
