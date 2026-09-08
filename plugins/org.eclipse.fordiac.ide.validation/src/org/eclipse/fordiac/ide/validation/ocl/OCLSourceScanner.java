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
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.model.buildpath.Buildpath;
import org.eclipse.fordiac.ide.model.buildpath.BuildpathAttributes;
import org.eclipse.fordiac.ide.model.buildpath.SourceFolder;
import org.eclipse.fordiac.ide.model.buildpath.util.BuildpathUtil;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.util.FordiacLogHelper;

public final class OCLSourceScanner {

	public static final String OCL_FILE_EXTENSION = "ocl"; //$NON-NLS-1$

	private static final Set<String> VALIDATION_TARGET_EXTENSIONS = Set.of(TypeLibraryTags.ADAPTER_TYPE_FILE_ENDING,
			TypeLibraryTags.ATTRIBUTE_TYPE_FILE_ENDING, TypeLibraryTags.DATA_TYPE_FILE_ENDING,
			TypeLibraryTags.DEVICE_TYPE_FILE_ENDING, TypeLibraryTags.FB_TYPE_FILE_ENDING,
			TypeLibraryTags.FC_TYPE_FILE_ENDING, TypeLibraryTags.GLOBAL_CONST_FILE_ENDING,
			TypeLibraryTags.RESOURCE_TYPE_FILE_ENDING, TypeLibraryTags.SEGMENT_TYPE_FILE_ENDING,
			TypeLibraryTags.SUBAPP_TYPE_FILE_ENDING, TypeLibraryTags.SYSTEM_TYPE_FILE_ENDING);

	public static List<IFile> findOclFiles(final IProject project) {
		return findBuildpathFiles(project, BuildpathAttributes.OCL_SOURCE, OCLSourceScanner::isOclFile);
	}

	public static List<IFile> findValidationTargets(final IProject project) {
		return findBuildpathFiles(project, BuildpathAttributes.OCL_TARGET,
				OCLSourceScanner::isValidationTargetFile);
	}

	public static List<IProject> findReferencedProjects(final IProject project) {
		final Set<IProject> result = collectProjects(project);
		result.remove(project);
		return new ArrayList<>(result);
	}

	private static List<IFile> findBuildpathFiles(final IProject project, final String attribute,
			final Predicate<IFile> matcher) {
		final Set<IFile> result = new LinkedHashSet<>();
		for (final IProject sourceProject : collectProjects(project)) {
			if (sourceProject.isAccessible()) {
				collectBuildpathFiles(sourceProject, attribute, file -> {
					if (matcher.test(file)) {
						result.add(file);
					}
				});
			}
		}
		return new ArrayList<>(result);
	}

	private static Set<IProject> collectProjects(final IProject project) {
		final Set<IProject> result = new LinkedHashSet<>();
		collectProjects(project, result);
		return result;
	}

	private static void collectProjects(final IProject project, final Set<IProject> result) {
		if (project == null || !result.add(project) || !project.isAccessible()) {
			return;
		}
		try {
			for (final IProject referencedProject : project.getReferencedProjects()) {
				collectProjects(referencedProject, result);
			}
		} catch (final CoreException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
	}

	private static void collectBuildpathFiles(final IProject project, final String attribute,
			final Consumer<IFile> consumer) {
		try {
			final Buildpath buildpath = TypeLibraryManager.INSTANCE.getTypeLibrary(project).getBuildpath();
			for (final SourceFolder folder : buildpath.getSourceFolders()) {
				if (Boolean.parseBoolean(BuildpathAttributes.getAttributeValue(folder.getAttributes(), attribute))) {
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

	private OCLSourceScanner() {
		throw new UnsupportedOperationException();
	}
}
