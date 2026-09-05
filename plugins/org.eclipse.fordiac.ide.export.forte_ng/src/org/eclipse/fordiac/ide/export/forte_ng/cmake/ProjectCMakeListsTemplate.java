/*******************************************************************************
 * Copyright (c) 2025 Martin Erich Jobst
 *               2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *   Michael Oberlehner - support additional CMake source directories
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng.cmake;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.core.resources.IProject;
import org.eclipse.fordiac.ide.export.ExportException;
import org.eclipse.fordiac.ide.export.utils.AdditionalSourceDirectories;
import org.eclipse.fordiac.ide.library.model.library.Required;

public class ProjectCMakeListsTemplate extends CMakeListsTemplate {

	private static final List<String> SUBDIRS = AdditionalSourceDirectories.getGeneratedDirectories();
	private static final String CMAKE_LISTS_FILE = "CMakeLists.txt"; //$NON-NLS-1$

	private final List<Path> additionalSubdirectories;

	public ProjectCMakeListsTemplate(final IProject project, final Path output,
			final List<Path> additionalSubdirectories) {
		super(project, output, Path.of("")); //$NON-NLS-1$
		this.additionalSubdirectories = List.copyOf(additionalSubdirectories);
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
		builder.append(generateAddSubdirectories(getProjectSubdirectories()));
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

	/**
	 * Returns the subdirectories to be added to the project build file.
	 *
	 * Additional source directories inside a generated directory are rejected by
	 * {@link AdditionalSourceDirectories#validatePaths} because their build files
	 * would be overwritten by the export. They are skipped here as well to keep the
	 * generated build file consistent with a stale configuration.
	 */
	private List<String> getProjectSubdirectories() {
		final List<Path> candidates = additionalSubdirectories.stream().map(Path::normalize)
				.filter(AdditionalSourceDirectories::isValidRelativeDirectory)
				.filter(Predicate.not(AdditionalSourceDirectories::isGeneratedDirectory))
				.filter(directory -> Files.isRegularFile(getOutput().resolve(directory).resolve(CMAKE_LISTS_FILE)))
				.distinct().sorted().toList();
		return Stream.concat(SUBDIRS.stream(),
				candidates.stream().map(ProjectCMakeListsTemplate::toCMakePath)).toList();
	}

	/**
	 * CMake requires forward slashes. Quoting is not necessary because
	 * {@link AdditionalSourceDirectories#isValidRelativeDirectory(Path)} rejects
	 * names that are not safe in an unquoted argument.
	 */
	private static String toCMakePath(final Path directory) {
		return directory.toString().replace('\\', '/');
	}
}
