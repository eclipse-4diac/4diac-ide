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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.IProject;
import org.eclipse.fordiac.ide.export.forte_ng.ForteNgExportTemplate;
import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.library.Required;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;

public abstract class CMakeListsTemplate extends ForteNgExportTemplate {

	public enum Access {
		PUBLIC, PRIVATE, INTERFACE
	}

	protected static final Set<String> SOURCE_EXTENSIONS = Set.of(".c", ".cpp", ".h"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	protected static final List<String> IMPLICIT_DEPENDENCIES = List.of("core"); //$NON-NLS-1$
	protected static final String FILE_SET_HEADERS = "HEADERS"; //$NON-NLS-1$

	protected static final int INDENT = 8;

	protected static final String HEADER = "# This file was generated using the 4DIAC FORTE Export Filter V1.0.x NG!\n\n"; //$NON-NLS-1$

	protected static final String FORTE = "forte"; //$NON-NLS-1$
	protected static final String FORTE_VERSION = "3.0"; //$NON-NLS-1$
	protected static final String CMAKE_MINIMUM_VERSION = "3.30"; //$NON-NLS-1$

	private final IProject project;
	private final Path output;
	private final Manifest manifest;

	protected CMakeListsTemplate(final IProject project, final Path output, final Path prefix) {
		super("CMakeLists.txt", prefix); //$NON-NLS-1$
		this.project = project;
		this.output = output;
		manifest = ManifestHelper.getContainerManifest(getProject());
	}

	protected static CharSequence generateCMakeMimumumRequired() {
		return "cmake_minimum_required(VERSION " + CMAKE_MINIMUM_VERSION + ")" + System.lineSeparator(); //$NON-NLS-1$ //$NON-NLS-2$
	}

	protected CharSequence generateModuleNamePlain() {
		return getProjectName();
	}

	protected CharSequence generateTargetName() {
		return generateTargetName(generateModuleNamePlain());
	}

	protected static CharSequence generateTargetName(final CharSequence name) {
		return "forte-" + name; //$NON-NLS-1$
	}

	protected CharSequence generateExportName() {
		return generateExportName(generateTargetName());
	}

	protected static CharSequence generateExportName(final CharSequence name) {
		return name + "-export"; //$NON-NLS-1$
	}

	protected static CharSequence generateHeader() {
		return HEADER;
	}

	protected static CharSequence generateProject(final CharSequence name, final CharSequence comment,
			final CharSequence version) {
		return "project(" + name + System.lineSeparator() //$NON-NLS-1$
				+ ((comment != null ? "DESCRIPTION \"" + comment + "\"" + System.lineSeparator() : "") //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						+ (version != null ? "VERSION \"" + version + "\"" + System.lineSeparator() : "") //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						+ "LANGUAGES C CXX" + System.lineSeparator() //$NON-NLS-1$
				).indent(INDENT) //
				+ ")" + System.lineSeparator(); //$NON-NLS-1$
	}

	protected static CharSequence generateFindPackage(final CharSequence name, final CharSequence version,
			final boolean required) {
		return "find_package(" + name + " " + version + (required ? " REQUIRED" : "") + ")" + System.lineSeparator(); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	}

	protected static CharSequence generateAddSubdirectories(final List<? extends CharSequence> subdirs) {
		return subdirs.stream().map(CMakeListsTemplate::generateAddSubdirectory).collect(Collectors.joining());
	}

	protected static CharSequence generateAddSubdirectory(final CharSequence subdir) {
		return "add_subdirectory(" + subdir + ")" + System.lineSeparator(); //$NON-NLS-1$ //$NON-NLS-2$
	}

	protected static CharSequence generateAddLibrary(final CharSequence name) {
		return "add_library(" + name + ")" + System.lineSeparator(); //$NON-NLS-1$ //$NON-NLS-2$
	}

	protected static CharSequence generateTargetSources(final CharSequence name, final Access access,
			final CharSequence fileSet, final CharSequence baseDirs, final List<? extends CharSequence> sources) {
		if (sources.isEmpty() && baseDirs == null) {
			return ""; //$NON-NLS-1$
		}
		return "target_sources(" + name + " " + access.name() + System.lineSeparator() //$NON-NLS-1$ //$NON-NLS-2$
				+ ((fileSet != null ? "FILE_SET " + fileSet + System.lineSeparator() : "") //$NON-NLS-1$ //$NON-NLS-2$
						+ (baseDirs != null ? "BASE_DIRS " + baseDirs + System.lineSeparator() : "") //$NON-NLS-1$ //$NON-NLS-2$
						+ ((fileSet != null && !sources.isEmpty()) ? "FILES" + System.lineSeparator() : "") //$NON-NLS-1$ //$NON-NLS-2$
						+ sources.stream().map(source -> source + System.lineSeparator()).collect(Collectors.joining()))
						.indent(INDENT)
				+ ")" + System.lineSeparator(); //$NON-NLS-1$
	}

	protected static CharSequence generateTargetLinkLibraries(final CharSequence name, final Access access,
			final List<? extends CharSequence> libs) {
		if (libs.isEmpty()) {
			return ""; //$NON-NLS-1$
		}
		return "target_link_libraries(" + name + " " + access.name() + " " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				+ libs.stream().map(CMakeListsTemplate::generateTargetName).collect(Collectors.joining(" ")) + ")" //$NON-NLS-1$//$NON-NLS-2$
				+ System.lineSeparator();
	}

	protected static CharSequence generateTargetLinkLibrariesWholeArchive(final CharSequence name, final Access access,
			final List<? extends CharSequence> libs) {
		return "get_target_property(" + name + "_IMPORTED " + name + " IMPORTED)" + System.lineSeparator() //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				+ "if (NOT " + name + "_IMPORTED)" + System.lineSeparator() //$NON-NLS-1$ //$NON-NLS-2$
				+ ("target_link_libraries(" + name + " " + access.name() //$NON-NLS-1$//$NON-NLS-2$
						+ " $<LINK_LIBRARY:WHOLE_ARCHIVE," //$NON-NLS-1$
						+ libs.stream().map(CMakeListsTemplate::generateTargetName).collect(Collectors.joining(",")) //$NON-NLS-1$
						+ ">)" //$NON-NLS-1$
						+ System.lineSeparator()).indent(INDENT) //
				+ "endif ()" + System.lineSeparator(); //$NON-NLS-1$
	}

	protected static CharSequence generateInstallPreamble(final CharSequence name) {
		return "include(GNUInstallDirs)" + System.lineSeparator() //$NON-NLS-1$
				+ "include(CMakePackageConfigHelpers)" + System.lineSeparator() //$NON-NLS-1$
				+ System.lineSeparator() //
				+ "set(ConfigPackageLocation lib/cmake/" + name.toString().toLowerCase() + "-${PROJECT_VERSION})" //$NON-NLS-1$ //$NON-NLS-2$
				+ System.lineSeparator();
	}

	protected static CharSequence generateConfigurePackageConfigFile(final CharSequence name) {
		return "configure_package_config_file(" + System.lineSeparator() //$NON-NLS-1$
				+ (name.toString().toLowerCase() + "-config.cmake.in" + System.lineSeparator() //$NON-NLS-1$
						+ "${CMAKE_CURRENT_BINARY_DIR}/" + name.toString().toLowerCase() + "-config.cmake" //$NON-NLS-1$ //$NON-NLS-2$
						+ System.lineSeparator() + "INSTALL_DESTINATION ${ConfigPackageLocation}" + System.lineSeparator() //$NON-NLS-1$
				).indent(INDENT) //
				+ ")" + System.lineSeparator(); //$NON-NLS-1$
	}

	protected static CharSequence generateWriteBasicPackageVersionFile(final CharSequence name) {
		return "write_basic_package_version_file(" + System.lineSeparator() //$NON-NLS-1$
				+ ("${CMAKE_CURRENT_BINARY_DIR}/" + name.toString().toLowerCase() + "-config-version.cmake" //$NON-NLS-1$ //$NON-NLS-2$
						+ System.lineSeparator() + "COMPATIBILITY SameMajorVersion" + System.lineSeparator() //$NON-NLS-1$
				).indent(INDENT) //
				+ ")" + System.lineSeparator(); //$NON-NLS-1$
	}

	protected static CharSequence generateInstallTargets(final CharSequence name, final CharSequence exportName) {
		return "install(TARGETS " + name + " EXPORT " + exportName + " FILE_SET HEADERS)" + System.lineSeparator(); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	protected static CharSequence generateInstallFiles(final CharSequence name) {
		return "install(FILES" + System.lineSeparator() //$NON-NLS-1$
				+ ("${CMAKE_CURRENT_BINARY_DIR}/" + name.toString().toLowerCase() + "-config.cmake" //$NON-NLS-1$ //$NON-NLS-2$
						+ System.lineSeparator() + "${CMAKE_CURRENT_BINARY_DIR}/" + name.toString().toLowerCase() //$NON-NLS-1$
						+ "-config-version.cmake" //$NON-NLS-1$
						+ System.lineSeparator() + "DESTINATION ${ConfigPackageLocation}" + System.lineSeparator() //$NON-NLS-1$
				).indent(INDENT) //
				+ ")" + System.lineSeparator(); //$NON-NLS-1$
	}

	protected static CharSequence generateInstallExport(final CharSequence exportName) {
		return "install(EXPORT " + exportName + System.lineSeparator() //$NON-NLS-1$
				+ ("DESTINATION ${ConfigPackageLocation}" + System.lineSeparator() //$NON-NLS-1$
						+ "EXPORT_LINK_INTERFACE_LIBRARIES" + System.lineSeparator() //$NON-NLS-1$
				).indent(INDENT) //
				+ ")" + System.lineSeparator(); //$NON-NLS-1$
	}

	protected String getProjectName() {
		if (manifest == null || manifest.getProduct() == null || manifest.getProduct().getSymbolicName() == null) {
			return project.getName();
		}
		return manifest.getProduct().getSymbolicName();
	}

	protected String getProjectComment() {
		if (manifest == null || manifest.getProduct() == null) {
			return null;
		}
		return manifest.getProduct().getComment();
	}

	protected String getProjectVersion() {
		if (manifest == null || manifest.getProduct() == null || manifest.getProduct().getVersionInfo() == null) {
			return null;
		}
		return manifest.getProduct().getVersionInfo().getVersion();
	}

	protected List<String> getDependencies() {
		if (manifest == null) {
			return IMPLICIT_DEPENDENCIES;
		}
		return Stream
				.concat(IMPLICIT_DEPENDENCIES.stream(),
						manifest.getDependencies().getRequired().stream().map(Required::getSymbolicName).sorted())
				.distinct().toList();
	}

	protected List<Required> getExternalDependencies() {
		if (manifest == null) {
			return List.of();
		}
		return manifest.getDependencies().getRequired().stream()
				.filter(Predicate.not(CMakeListsTemplate::isStandardLibrary))
				.sorted(Comparator.comparing(Required::getSymbolicName)).toList();
	}

	protected static boolean isStandardLibrary(final Required required) {
		return LibraryManager.INSTANCE.getStandardLibraries().containsKey(required.getSymbolicName());
	}

	protected List<String> getSubdirectories() {
		try (Stream<Path> list = Files.list(getCurrentSourceDir())) {
			return list.filter(Files::isDirectory).map(Path::getFileName).map(Path::toString).toList();
		} catch (final IOException e) {
			errors.add(e.getMessage());
			return List.of();
		}
	}

	protected List<String> getSourceFiles() {
		try (Stream<Path> list = Files.list(getCurrentSourceDir())) {
			return list.filter(CMakeListsUtil::isSourceFile).map(Path::getFileName).map(Path::toString).toList();
		} catch (final IOException e) {
			errors.add(e.getMessage());
			return List.of();
		}
	}

	protected Path getCurrentSourceDir() {
		return output.resolve(getPath().subpath(0, getPath().getNameCount() - 1));
	}

	protected IProject getProject() {
		return project;
	}

	protected Path getOutput() {
		return output;
	}
}
