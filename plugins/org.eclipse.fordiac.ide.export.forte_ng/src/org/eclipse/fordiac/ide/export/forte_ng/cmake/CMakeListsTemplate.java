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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.IProject;
import org.eclipse.fordiac.ide.export.forte_ng.ForteNgExportTemplate;
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

	private final IProject project;
	private final Path output;

	protected CMakeListsTemplate(final IProject project, final Path output, final Path prefix) {
		super("CMakeLists.txt", prefix); //$NON-NLS-1$
		this.project = project;
		this.output = output;
	}

	protected CharSequence generateModuleNamePlain() {
		return project.getName();
	}

	protected CharSequence generateProjectName() {
		return generateModuleNamePlain();
	}

	protected CharSequence generateTargetName() {
		return generateTargetName(generateModuleNamePlain());
	}

	protected static CharSequence generateTargetName(final CharSequence name) {
		return "forte-" + name; //$NON-NLS-1$
	}

	protected static CharSequence generateHeader() {
		return HEADER;
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
						+ (fileSet != null ? "FILES" + System.lineSeparator() : "") //$NON-NLS-1$ //$NON-NLS-2$
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
		return "target_link_libraries(" + name + " " + access.name() + " $<LINK_LIBRARY:WHOLE_ARCHIVE," //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				+ libs.stream().map(CMakeListsTemplate::generateTargetName).collect(Collectors.joining(",")) + ">)" //$NON-NLS-1$//$NON-NLS-2$
				+ System.lineSeparator();
	}

	protected List<String> getDependencies() {
		final Manifest manifest = ManifestHelper.getContainerManifest(getProject());
		if (manifest != null) {
			return Stream
					.concat(IMPLICIT_DEPENDENCIES.stream(),
							manifest.getDependencies().getRequired().stream().map(Required::getSymbolicName).sorted())
					.distinct().toList();
		}
		return IMPLICIT_DEPENDENCIES;
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
