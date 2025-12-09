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
 *   Fabio Gandolfi - initial implementation and/or documentation
 *   Ernst Blecha - refactoring of base classes for ant tasks
 *                - preserve directory structure on export
 *******************************************************************************/
package org.eclipse.fordiac.ide.ant.ant;

import java.io.File;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.export.ExportFilter;
import org.eclipse.fordiac.ide.export.xmi.XMIExportFilter;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;

public abstract class AbstractExportFBsAsXMI extends AbstractFBTask {
	protected static final String ANT_EXPORT_TASK_DIRECTORY_NAME = "exported_FBs_as_XMI"; //$NON-NLS-1$

	@Override
	protected String getExportDirectoryDefault() {
		return ANT_EXPORT_TASK_DIRECTORY_NAME;
	}

	@Override
	protected ExportFilter getExportFilter() {
		return new XMIExportFilter();
	}

	@Override
	protected boolean isFilteredFiletype(final File file) {
		final var additionalFileTypes = Stream.of(//
				TypeLibraryTags.SUBAPP_TYPE_FILE_ENDING_WITH_DOT, //
				TypeLibraryTags.SYSTEM_TYPE_FILE_ENDING_WITH_DOT);
		return super.isFilteredFiletype(file) || matchesFileExtension(file, additionalFileTypes);
	}
}
