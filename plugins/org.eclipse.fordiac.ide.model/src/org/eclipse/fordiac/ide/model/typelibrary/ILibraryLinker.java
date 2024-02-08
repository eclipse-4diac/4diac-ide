/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.StructuredSelection;

public interface ILibraryLinker {
	void extractLibrary(final File file, final StructuredSelection selection) throws IOException;

	File newFile(final File destinationDir, final ZipEntry zipEntry) throws IOException;

	void setSelectedProject(final StructuredSelection selection);

	void setSelectedProjectWithTypeLib(final IProject project, TypeLibrary typeLib);

	File[] listLibDirectories(final String directory);

	void importLibrary(final String directory);

	void removeOldLibVersion(final List<String> importedLibs, final String newDirectoryName);

	void createTypeEntriesManually(final IContainer container);

	void updateFBInstancesWithNewTypeVersion();

	Map<String, TypeEntry> cashOldTypes(final String oldVersion);

	List<String> findLinkedLibs();

	File[] listDirectoriesContainingArchives();

	File[] listExtractedFiles();

}
