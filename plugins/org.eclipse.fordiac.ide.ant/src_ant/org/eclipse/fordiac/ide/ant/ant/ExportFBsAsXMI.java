/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ant.ant;

import java.io.File;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.typemanagement.ExportAsXMI;

public class ExportFBsAsXMI extends AbstractExportFBs {

	protected static final String ANT_EXPORT_TASK_DIRECTORY_NAME = "exported_FBs_as_XMI"; //$NON-NLS-1$

	@Override
	protected String getExportDirectoryDefault() {
		return ANT_EXPORT_TASK_DIRECTORY_NAME;
	}

	@Override
	protected void exportFiles(final List<File> files) {
		if (!files.isEmpty()) {

			final File folder = new File(exportDirectory);
			if (!folder.exists()) {
				folder.mkdir();
			}
			files.forEach(file -> exportFile(folder, file));

		}
	}

	private void exportFile(final File folder, final File file) {
		final ExportAsXMI exporter = new ExportAsXMI();

		final IPath location = Path.fromOSString(file.getAbsolutePath());
		final IFile ifile = workspace.getRoot().getFileForLocation(location);

		// Export only SimpleFBs or STFunc, remove this line to export all FB Types
		if (isSimpleFBType(ifile)
				|| file.getName().toUpperCase().endsWith(TypeLibraryTags.FC_TYPE_FILE_ENDING_WITH_DOT)) {
			final URI folderURI = URI.createFileURI(folder.getAbsolutePath());
			try {
				exporter.export(ifile, folderURI);
				System.out.println(ifile);// print it in console for ANT Tasks
			} catch (final Exception e) {
				throw new BuildException("Could not export: " + e.getMessage(), e);//$NON-NLS-1$
			}
		}
	}

	private static boolean isSimpleFBType(final IFile ifile) {
		if (ifile.getName().toUpperCase().endsWith(TypeLibraryTags.FB_TYPE_FILE_ENDING_WITH_DOT)) {
			final TypeEntry entry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(ifile);
			return null != entry && entry.getType() instanceof SimpleFBType;
		}
		return false;
	}

}
