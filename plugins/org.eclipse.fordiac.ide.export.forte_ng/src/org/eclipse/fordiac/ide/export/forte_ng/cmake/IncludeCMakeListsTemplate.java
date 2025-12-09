/*******************************************************************************
 * Copyright (c) 2025 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng.cmake;

import java.nio.file.Path;

import org.eclipse.core.resources.IProject;
import org.eclipse.fordiac.ide.export.ExportException;

public class IncludeCMakeListsTemplate extends CMakeListsTemplate {

	public IncludeCMakeListsTemplate(final IProject project, final Path output, final Path prefix) {
		super(project, output, prefix);
	}

	@Override
	public CharSequence generate() throws ExportException {
		final StringBuilder builder = new StringBuilder();
		builder.append(generateHeader());
		builder.append(generateTargetSources(generateTargetName(), Access.PUBLIC, FILE_SET_HEADERS,
				isTopLevelFileSet() ? "${CMAKE_CURRENT_SOURCE_DIR}" : null, getSourceFiles())); //$NON-NLS-1$
		builder.append(generateAddSubdirectories(getSubdirectories()));
		return builder;
	}

	protected boolean isTopLevelFileSet() {
		return getPath().getNameCount() == 2;
	}
}
