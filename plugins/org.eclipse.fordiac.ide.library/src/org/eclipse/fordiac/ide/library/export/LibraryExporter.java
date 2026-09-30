/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.export;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.MessageFormat;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.library.Messages;
import org.eclipse.fordiac.ide.library.model.library.Library;
import org.eclipse.fordiac.ide.library.model.library.LibraryElement;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;

public class LibraryExporter {

	public enum TypeSelection {
		ALL_TYPES, INCLUDE_EXCLUDE_PATTERNS
	}

	private final String outputDirectory;
	private final TypeSelection exportType;
	private final Library library;
	private final String version;
	private final IProject project;

	private static final String WILDCARD_EXACTLY_ONE = "*"; //$NON-NLS-1$
	private static final String WILDCARD_AT_LEAST_ONE = "**"; //$NON-NLS-1$

	public LibraryExporter(final String outputDirectory, final TypeSelection exportType, final Library library,
			final String version, final IProject project) {
		this.outputDirectory = outputDirectory;
		this.exportType = exportType;
		this.library = library;
		this.version = version;
		this.project = project;
	}

	public void export(final IProgressMonitor monitor) throws IOException {
		final SubMonitor progress = SubMonitor.convert(monitor,
				MessageFormat.format(Messages.LibraryExporter_ExportingLibrary, library.getSymbolicName()), 2);

		final Path rootDir = createLibraryRoot();
		createLibraryManifest(rootDir, progress.split(1));
		exportTypes(rootDir, progress.split(1));
	}

	private Path createLibraryRoot() throws IOException {
		return Files.createDirectories(Path.of(outputDirectory, getLibraryName()));
	}

	private void createLibraryManifest(final Path rootDir, final SubMonitor progress) throws IOException {
		ManifestHelper.createLibraryManifest(rootDir, library, version);
		progress.worked(1);
	}

	private void exportTypes(final Path rootDir, final SubMonitor monitor) throws IOException {
		final List<TypeEntry> types = getTypes(project).toList();

		final SubMonitor progress = SubMonitor.convert(monitor, Messages.LibraryExporter_ExportingTypes, types.size());

		final Path targetFolder = rootDir.resolve("typelib"); //$NON-NLS-1$

		// re-export has to clear directory because -> export all types ->
		// export with pattern -> old files remain
		deleteDirectory(targetFolder);

		Files.createDirectories(targetFolder);

		for (final TypeEntry typeEntry : types) {
			if (progress.isCanceled()) {
				throw new OperationCanceledException();
			}

			progress.subTask(typeEntry.getFullTypeName());
			exportType(typeEntry, targetFolder);
			progress.worked(1);
		}
	}

	private static void exportType(final TypeEntry typeEntry, final Path targetFolder) throws IOException {
		final IFile sourceFile = typeEntry.getFile();
		final Path targetFile = targetFolder.resolve(getTargetPath(typeEntry));

		Files.createDirectories(targetFile.getParent());

		try (InputStream input = sourceFile.getContents()) {
			Files.copy(input, targetFile, StandardCopyOption.REPLACE_EXISTING);
		} catch (final CoreException e) {
			throw new IOException("Error while exporting file " + sourceFile.getFullPath(), e); //$NON-NLS-1$
		}
	}

	private static Path getTargetPath(final TypeEntry typeEntry) {
		final String packageName = typeEntry.getPackageName();

		if (packageName == null || packageName.isEmpty()) {
			return Path.of(typeEntry.getFile().getName());
		}

		return Path.of(packageName.replace(PackageNameHelper.PACKAGE_NAME_DELIMITER, "/")) //$NON-NLS-1$
				.resolve(typeEntry.getFile().getName());
	}

	private String getLibraryName() {
		return MessageFormat.format("{0}-{1}", library.getSymbolicName(), version); //$NON-NLS-1$
	}

	private Stream<TypeEntry> getTypes(final IProject project) {
		return switch (exportType) {
		case ALL_TYPES: {
			yield getLocalTypes(project);
		}
		case INCLUDE_EXCLUDE_PATTERNS: {
			yield getLocalTypes(project).filter(createPackageFilter(library));
		}
		default: {
			yield Stream.empty();
		}
		};
	}

	private static Predicate<TypeEntry> createPackageFilter(final Library library) {
		final Predicate<TypeEntry> included = library.getIncludes() == null
				|| library.getIncludes().getLibraryElement().isEmpty() ? _ -> true
						: matchesAny(library.getIncludes().getLibraryElement());

		final Predicate<TypeEntry> excluded = library.getExcludes() == null ? _ -> false
				: matchesAny(library.getExcludes().getLibraryElement());

		return included.and(Predicate.not(excluded));
	}

	private static Stream<TypeEntry> getLocalTypes(final IProject project) {
		return TypeLibraryManager.INSTANCE.getTypeLibrary(project).getAllTypes().filter(te -> isLocalType(te, project));
	}

	private static boolean isLocalType(final TypeEntry entry, final IProject project) {
		final IFile file = entry.getFile();
		final IPath typeLibraryFolder = project.getFolder(TypeLibraryTags.TYPE_LIB_FOLDER_NAME).getFullPath();
		return file != null && typeLibraryFolder.isPrefixOf(file.getFullPath());
	}

	private static Predicate<TypeEntry> matchesAny(final EList<LibraryElement> patterns) {
		return typeEntry -> patterns.stream()
				.anyMatch(pattern -> matchesPackage(typeEntry.getFullTypeName(), pattern.getValue()));
	}

	// TODO Move to package name helper? and add unit tests
	private static boolean matchesPackage(final String packageName, final String pattern) {
		final String[] packageSegments = packageName.split(PackageNameHelper.PACKAGE_NAME_DELIMITER);
		final String[] patternSegments = pattern.split(PackageNameHelper.PACKAGE_NAME_DELIMITER);

		int packageIndex = 0;
		int patternIndex = 0;

		int wildcardPatternIndex = -1;
		int wildcardPackageIndex = -1;

		while (packageIndex < packageSegments.length) {
			if (patternIndex < patternSegments.length && (WILDCARD_EXACTLY_ONE.equals(patternSegments[patternIndex]) // $NON-NLS-1$
					|| packageSegments[packageIndex].equals(patternSegments[patternIndex]))) {
				packageIndex++;
				patternIndex++;
			} else if (patternIndex < patternSegments.length
					&& WILDCARD_AT_LEAST_ONE.equals(patternSegments[patternIndex])) { // $NON-NLS-1$
				// whildcard match at least one segment
				wildcardPatternIndex = patternIndex;
				patternIndex++;
				packageIndex++;
				wildcardPackageIndex = packageIndex;
			} else if (wildcardPatternIndex >= 0 && wildcardPackageIndex < packageSegments.length) {

				// Previous wildcard consumes additional segment
				patternIndex = wildcardPatternIndex + 1;
				wildcardPackageIndex++;
				packageIndex = wildcardPackageIndex;
			} else {
				return false;
			}
		}

		return patternIndex == patternSegments.length;
	}

	private static void deleteDirectory(final Path directory) throws IOException {
		if (!Files.exists(directory)) {
			return;
		}

		Files.walkFileTree(directory, new SimpleFileVisitor<>() {
			@Override
			public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
				Files.delete(file);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
				if (exc != null) {
					throw exc;
				}

				Files.delete(dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}

}
