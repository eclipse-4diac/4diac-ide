/*******************************************************************************
 * Copyright (c) 2022, 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *   Ernst Blecha - refactoring of base classes for ant tasks
 *******************************************************************************/
package org.eclipse.fordiac.ide.ant.ant;

import java.io.File;
import java.io.IOException;

import org.apache.tools.ant.BuildException;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fordiac.ide.export.ExportFilter;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.resource.FordiacTypeResourceFactory;
import org.eclipse.fordiac.ide.model.typelibrary.AttributeTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.model.util.LibraryElementHashException;
import org.eclipse.fordiac.ide.model.util.LibraryElementHasher;

public abstract class AbstractExportLibElements extends AbstractFBTask {
	private static final String ANT_CONVERT_TASK_DIRECTORY_NAME = "converted_FBs"; //$NON-NLS-1$

	@Override
	protected String getExportDirectoryDefault() {
		return ANT_CONVERT_TASK_DIRECTORY_NAME;
	}

	@Override
	protected ExportFilter getExportFilter() {
		return null; // This class does not use an ExportFilter
	}

	@Override
	protected void exportFile(final File folder, final IFile file) throws BuildException {
		log(file.toString());

		final TypeEntry entry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(file);
		LibraryElement element = entry.getType();
		String hash;

		try {
			hash = LibraryElementHasher.hash(element);
		} catch (final LibraryElementHashException e) {
			throw new BuildException(e);
		}
		final ExportCopier copier = new ExportCopier();
		element = (LibraryElement) copier.copy(element);
		copier.copyReferences();

		AttributeTypeEntry hashDecl = entry.getTypeLibrary()
				.getAttributeTypeEntry(TypeLibraryTags.TYPE_HASH_ATTRIBUTE_FULL_NAME);
		if (hashDecl == null) {
			hashDecl = entry.getTypeLibrary().getAttributeTypeEntry(TypeLibraryTags.TYPE_HASH_ATTRIBUTE_NAME);
		}
		if (hashDecl == null) {
			throw new BuildException("Type hash attribute is missing from type library"); //$NON-NLS-1$
		}

		element.setAttribute(hashDecl.getType(), hash, null);

		final Resource resource = FordiacTypeResourceFactory.INSTANCE
				.createResource(URI.createFileURI(new File(folder, file.getName()).getAbsolutePath()));
		resource.getContents().add(element);
		try {
			resource.save(null);
		} catch (final IOException e) {
			throw new BuildException(e);
		}

	}
}
