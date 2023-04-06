/*******************************************************************************
+ * Copyright (c) 2022, 2023 Primetals Technologies Austria GmbH
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
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.eclipse.fordiac.ide.export.ExportException;
import org.eclipse.fordiac.ide.export.ExportFilter;
import org.eclipse.fordiac.ide.export.forte_ng.ForteNgExportFilter;
import org.eclipse.fordiac.ide.model.typelibrary.CMakeListsMarker;

public abstract class AbstractExportFBs extends AbstractFBTask {
	private static final String ANT_EXPORT_TASK_DIRECTORY_NAME = "exported_FBs"; //$NON-NLS-1$

	private boolean exportCMakeList = false;

	public void setExportCMakeList(final boolean value) {
		exportCMakeList = value;
	}

	@Override
	protected String getExportDirectoryDefault() {
		return ANT_EXPORT_TASK_DIRECTORY_NAME;
	}

	@Override
	protected ExportFilter getExportFilter() {
		return new ForteNgExportFilter();
	}

	@Override
	protected void exportFiles(final List<File> files) {
		super.exportFiles(files);
		final File folder = new File(exportDirectory);
		if (folder.exists() && exportCMakeList) {
			try {
				filter.export(null, folder.getPath(), true, new CMakeListsMarker());
				log("CMakeLists.txt");//$NON-NLS-1$
				if (!filter.getErrors().isEmpty()) {
					filter.getErrors().forEach(this::log);
					throw new BuildException("Could not export without errors"); //$NON-NLS-1$
				}
			} catch (final ExportException e) {
				throw new BuildException("Could not export: " + e.getMessage(), e);//$NON-NLS-1$
			}
		}
	}
}
