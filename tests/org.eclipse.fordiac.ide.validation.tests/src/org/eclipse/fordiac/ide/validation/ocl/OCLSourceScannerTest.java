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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.model.buildpath.Attribute;
import org.eclipse.fordiac.ide.model.buildpath.BuildpathAttributes;
import org.eclipse.fordiac.ide.model.buildpath.SourceFolder;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OCLSourceScannerTest {

	private IProject project;
	private IProject referencedProject;

	@BeforeEach
	void setUp() throws CoreException {
		project = createProject("OCLSourceScanner"); //$NON-NLS-1$
	}

	@AfterEach
	void tearDown() throws CoreException {
		deleteProject(project);
		deleteProject(referencedProject);
	}

	@Test
	void honorsOclSourceAndTargetAttributes() throws CoreException {
		final IFile oclFile = createFile(project.getFile("project.ocl")); //$NON-NLS-1$
		final IFile targetFile = createFile(project.getFile("project.fbt")); //$NON-NLS-1$

		assertTrue(OCLSourceScanner.findOclFiles(project).contains(oclFile));
		assertTrue(OCLSourceScanner.findValidationTargets(project).contains(targetFile));

		setRootAttribute(project, BuildpathAttributes.OCL_SOURCE, false);
		setRootAttribute(project, BuildpathAttributes.OCL_TARGET, false);

		assertFalse(OCLSourceScanner.findOclFiles(project).contains(oclFile));
		assertFalse(OCLSourceScanner.findValidationTargets(project).contains(targetFile));
	}

	@Test
	void loadsOclFromExternalLibrariesWithoutAddingValidationTargets() throws CoreException {
		final IFolder externalLibraries = project.getFolder(TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
		externalLibraries.create(true, true, null);
		final IFile oclFile = createFile(externalLibraries.getFile("library.ocl")); //$NON-NLS-1$
		final IFile targetFile = createFile(externalLibraries.getFile("library.fbt")); //$NON-NLS-1$

		assertTrue(OCLSourceScanner.findOclFiles(project).contains(oclFile));
		assertFalse(OCLSourceScanner.findValidationTargets(project).contains(targetFile));
	}

	@Test
	void includesSourcesAndTargetsFromReferencedProjects() throws CoreException {
		referencedProject = createProject("OCLSourceScannerReference"); //$NON-NLS-1$
		final IFile oclFile = createFile(referencedProject.getFile("library.ocl")); //$NON-NLS-1$
		final IFile targetFile = createFile(referencedProject.getFile("library.fbt")); //$NON-NLS-1$
		final IProjectDescription description = project.getDescription();
		description.setReferencedProjects(new IProject[] { referencedProject });
		project.setDescription(description, null);

		assertTrue(OCLSourceScanner.findOclFiles(project).contains(oclFile));
		assertTrue(OCLSourceScanner.findValidationTargets(project).contains(targetFile));
		assertTrue(OCLSourceScanner.findReferencedProjects(project).contains(referencedProject));
	}

	private static void setRootAttribute(final IProject project, final String name, final boolean value) {
		final SourceFolder root = TypeLibraryManager.INSTANCE.getTypeLibrary(project).getBuildpath().getSourceFolders()
				.stream().filter(folder -> folder.getName().isEmpty()).findFirst().orElseThrow();
		final Attribute attribute = BuildpathAttributes.createAttribute(name);
		attribute.setValue(Boolean.toString(value));
		root.getAttributes().add(attribute);
	}

	private static IProject createProject(final String name) throws CoreException {
		final IProject result = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
		if (result.exists()) {
			result.delete(true, true, null);
		}
		result.create(null);
		result.open(null);
		TypeLibraryManager.INSTANCE.getTypeLibrary(result);
		return result;
	}

	private static void deleteProject(final IProject project) throws CoreException {
		if (project != null) {
			TypeLibraryManager.INSTANCE.removeProject(project);
			if (project.exists()) {
				project.delete(true, true, null);
			}
		}
	}

	private static IFile createFile(final IFile file) throws CoreException {
		file.create(new ByteArrayInputStream("test".getBytes(StandardCharsets.UTF_8)), true, null); //$NON-NLS-1$
		return file;
	}
}
