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
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.validation.ocl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.model.buildpath.Buildpath;
import org.eclipse.fordiac.ide.model.buildpath.BuildpathAttributes;
import org.eclipse.fordiac.ide.model.buildpath.SourceFolder;
import org.eclipse.fordiac.ide.model.buildpath.util.BuildpathUtil;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public final class OCLSourceScanner {

	public static final String OCL_FILE_EXTENSION = "ocl"; //$NON-NLS-1$

	private static final Set<String> VALIDATION_TARGET_EXTENSIONS = Set.of(TypeLibraryTags.ADAPTER_TYPE_FILE_ENDING,
			TypeLibraryTags.ATTRIBUTE_TYPE_FILE_ENDING, TypeLibraryTags.DATA_TYPE_FILE_ENDING,
			TypeLibraryTags.DEVICE_TYPE_FILE_ENDING, TypeLibraryTags.FB_TYPE_FILE_ENDING,
			TypeLibraryTags.FC_TYPE_FILE_ENDING, TypeLibraryTags.GLOBAL_CONST_FILE_ENDING,
			TypeLibraryTags.RESOURCE_TYPE_FILE_ENDING, TypeLibraryTags.SEGMENT_TYPE_FILE_ENDING,
			TypeLibraryTags.SUBAPP_TYPE_FILE_ENDING, TypeLibraryTags.SYSTEM_TYPE_FILE_ENDING);

	public static List<IFile> findOclFiles(final IProject project) {
		final Set<IFile> result = new LinkedHashSet<>();
		collectOclFiles(project, result);
		try {
			for (final IProject referencedProject : project.getReferencedProjects()) {
				if (referencedProject.isAccessible()) {
					collectOclFiles(referencedProject, result);
				}
			}
		} catch (final CoreException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
		return new ArrayList<>(result);
	}

	public static List<IFile> findValidationTargets(final IProject project) {
		final Set<IFile> result = new LinkedHashSet<>();
		if (project != null && project.isAccessible()) {
			collectBuildpathFiles(project, BuildpathAttributes.OCL_TARGET, true, file -> {
				if (isValidationTargetFile(file)) {
					result.add(file);
				}
			});
			collectProjectFiles(project, result, OCLSourceScanner::isValidationTargetFile);
		}
		return new ArrayList<>(result);
	}

	private static void collectOclFiles(final IProject project, final Set<IFile> result) {
		if (project != null && project.isAccessible()) {
			collectBuildpathFiles(project, BuildpathAttributes.OCL_SOURCE, true, file -> {
				if (isOclFile(file)) {
					result.add(file);
				}
			});
			collectProjectFiles(project, result, OCLSourceScanner::isOclFile);
		}
	}

	private static void collectBuildpathFiles(final IProject project, final String attribute,
			final boolean defaultValue, final FileConsumer consumer) {
		try {
			final Buildpath buildpath = TypeLibraryManager.INSTANCE.getTypeLibrary(project).getBuildpath();
			for (final SourceFolder folder : buildpath.getSourceFolders()) {
				if (getBooleanAttribute(folder, attribute, defaultValue)) {
					BuildpathUtil.acceptMatches(folder, project, (IResourceVisitor) resource -> {
						if (resource instanceof final IFile file) {
							consumer.accept(file);
							return false;
						}
						return resource instanceof IFolder || resource instanceof IProject;
					});
				}
			}
		} catch (final CoreException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
	}

	private static void collectProjectFiles(final IProject project, final Set<IFile> result,
			final FileMatcher matcher) {
		try {
			project.accept(resource -> {
				if (resource instanceof final IFile file && matcher.matches(file)) {
					result.add(file);
					return false;
				}
				if (resource instanceof final IFolder folder
						&& (folder.isLinked() || TypeLibraryTags.TYPE_LIBRARY.equalsIgnoreCase(folder.getName()))) {
					return false;
				}
				return true;
			}, IResource.NONE);
		} catch (final CoreException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
	}

	private static boolean getBooleanAttribute(final SourceFolder folder, final String attribute,
			final boolean defaultValue) {
		final String value = BuildpathAttributes.getAttributeValue(folder.getAttributes(), attribute);
		return value != null ? Boolean.parseBoolean(value) : defaultValue;
	}

	public static boolean isOclFile(final IFile file) {
		return hasExtension(file, OCL_FILE_EXTENSION);
	}

	public static boolean isValidationTargetFile(final IFile file) {
		final String extension = file.getFileExtension();
		return extension != null && VALIDATION_TARGET_EXTENSIONS.stream().anyMatch(extension::equalsIgnoreCase);
	}

	private static boolean hasExtension(final IFile file, final String extension) {
		return file != null && extension.equalsIgnoreCase(file.getFileExtension());
	}

	@FunctionalInterface
	private interface FileConsumer {
		void accept(IFile file);
	}

	@FunctionalInterface
	private interface FileMatcher {
		boolean matches(IFile file);
	}

	private OCLSourceScanner() {
		throw new UnsupportedOperationException();
	}
}
