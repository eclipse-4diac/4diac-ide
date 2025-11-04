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
import org.eclipse.fordiac.ide.library.model.library.Required;

public class ProjectCMakeListsTemplate extends CMakeListsTemplate {

	private static final List<String> SUBDIRS = List.of("include", "src"); //$NON-NLS-1$ //$NON-NLS-2$

	public ProjectCMakeListsTemplate(final IProject project, final Path output) {
		super(project, output, Path.of("")); //$NON-NLS-1$
	}

	@Override
	public CharSequence generate() throws ExportException {
		final StringBuilder builder = new StringBuilder();
		builder.append(generateHeader());
		builder.append(generateCMakeMimumumRequired());
		builder.append(generateProject(generateTargetName(), getProjectVersion(), getProjectVersion()));
		builder.append(System.lineSeparator());
		builder.append(generateFindPackage(FORTE, FORTE_VERSION, true));
		for (final Required dep : getExternalDependencies()) {
			builder.append(generateFindPackage(generateTargetName(dep.getSymbolicName()),
					generateVersionRange(dep.getVersion()), true));
		}
		builder.append(System.lineSeparator());
		builder.append(generateAddLibrary(generateTargetName()));
		builder.append(generateTargetLinkLibraries(generateTargetName(), Access.PUBLIC, getDependencies()));
		builder.append(
				generateTargetLinkLibrariesWholeArchive(FORTE, Access.PUBLIC, List.of(generateModuleNamePlain())));
		builder.append(System.lineSeparator());
		builder.append(generateAddSubdirectories(SUBDIRS));
		builder.append(System.lineSeparator());
		builder.append(generateInstallPreamble(generateTargetName()));
		builder.append(System.lineSeparator());
		builder.append(generateConfigurePackageConfigFile(generateTargetName()));
		builder.append(System.lineSeparator());
		builder.append(generateWriteBasicPackageVersionFile(generateTargetName()));
		builder.append(System.lineSeparator());
		builder.append(generateInstallTargets(generateTargetName(), generateExportName()));
		builder.append(System.lineSeparator());
		builder.append(generateInstallFiles(generateTargetName()));
		builder.append(System.lineSeparator());
		builder.append(generateInstallExport(generateExportName()));
		return builder;
	}
}
