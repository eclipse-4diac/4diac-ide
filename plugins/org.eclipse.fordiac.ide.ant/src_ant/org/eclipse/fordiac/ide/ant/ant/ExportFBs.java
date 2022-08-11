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
import org.eclipse.fordiac.ide.export.ExportException;
import org.eclipse.fordiac.ide.export.forte_ng.ForteNgExportFilter;
import org.eclipse.fordiac.ide.model.typelibrary.CMakeListsMarker;

public class ExportFBs extends AbstractExportFBs {

	private final String ANT_EXPORT_TASK_DIRECTORY_NAME = "exported_FBs"; //$NON-NLS-1$

	protected boolean exportCMakeList = false;

	@Override
	protected String getExportDirectoryDefault() {
		return ANT_EXPORT_TASK_DIRECTORY_NAME;
	}

	public void setExportCMakeList(final boolean value) {
		exportCMakeList = value;
	}

	@Override
	protected void exportFiles(final List<File> files) {
		if (!files.isEmpty()) {

			final ForteNgExportFilter filter = new ForteNgExportFilter();

			final File folder = new File(exportDirectory);
			if (!folder.exists()) {
				folder.mkdir();
			}
			files.forEach(file -> exportFile(filter, folder, file));
		}
	}

	private void exportFile(final ForteNgExportFilter filter, final File folder, final File file) {
		final IPath location = Path.fromOSString(file.getAbsolutePath());
		final IFile ifile = workspace.getRoot().getFileForLocation(location);

		try {
			filter.export(ifile, folder.getPath(), true);
			if (exportCMakeList) {
				filter.export(null, folder.getPath(), true, new CMakeListsMarker());
			}

			if (!filter.getErrors().isEmpty()) {
				for (final String error : filter.getErrors()) {
					log(error);
				}
				throw new BuildException("Could not export without errors"); //$NON-NLS-1$
			}

		} catch (final ExportException e) {
			throw new BuildException("Could not export: " + e.getMessage(), e);//$NON-NLS-1$
		}
	}
}
