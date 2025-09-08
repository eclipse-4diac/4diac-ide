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
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.fordiac.ide.export.ExportException;

public class ProjectCMakeListsTemplate extends CMakeListsTemplate {

	private static final List<String> SUBDIRS = List.of("include", "src"); //$NON-NLS-1$ //$NON-NLS-2$

	public ProjectCMakeListsTemplate(final IProject project, final Path output) {
		super(project, output, Path.of("")); //$NON-NLS-1$
	}

	@Override
	public CharSequence generate() throws ExportException {
		final StringBuilder builder = new StringBuilder();
		builder.append(generateHeader());
		builder.append(generateAddLibrary(generateTargetName()));
		builder.append(generateTargetLinkLibraries(generateTargetName(), Access.PUBLIC, getDependencies()));
		builder.append(
				generateTargetLinkLibrariesWholeArchive("forte", Access.PUBLIC, List.of(generateModuleNamePlain()))); //$NON-NLS-1$
		builder.append(System.lineSeparator());
		builder.append(generateAddSubdirectories(SUBDIRS));
		return builder;
	}
}
