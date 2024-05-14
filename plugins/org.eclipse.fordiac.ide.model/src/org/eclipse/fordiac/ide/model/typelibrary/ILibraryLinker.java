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
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;

public interface ILibraryLinker {
	void extractLibrary(final File file, final IProject project, boolean autoimport) throws IOException;

	File newFile(final File destinationDir, final ZipEntry zipEntry) throws IOException;

	void setSelectedProject(final IProject project);

	void setSelectedProjectWithTypeLib(final IProject project, TypeLibrary typeLib);

	File[] listLibDirectories(final String directory);

	void importLibrary(final java.net.URI uri);

	void importLibraries(final Collection<java.net.URI> uris);

	Map<String, TypeEntry> findOldTypes(final IFolder oldFolder);

	List<String> findLinkedLibs();

	File[] listDirectoriesContainingArchives();

	File[] listExtractedFiles();

	File[] listStandardLibraries();

	void updateLibrary(String symbolicName, String version);

	void checkManifestFile(final IProject project, final TypeLibrary typeLibrary);

}
