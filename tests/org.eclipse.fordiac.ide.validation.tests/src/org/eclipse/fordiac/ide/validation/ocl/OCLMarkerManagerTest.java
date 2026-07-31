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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.model.errormarker.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.validation.handlers.IValidationMarker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OCLMarkerManagerTest {

	private IProject firstProject;
	private IProject secondProject;
	private IFile sharedFile;

	@BeforeEach
	void setUp() throws CoreException {
		firstProject = createProject("OCLMarkerOwnerA"); //$NON-NLS-1$
		secondProject = createProject("OCLMarkerOwnerB"); //$NON-NLS-1$
		sharedFile = firstProject.getFile("shared.fbt"); //$NON-NLS-1$
		sharedFile.create(new ByteArrayInputStream("test".getBytes(StandardCharsets.UTF_8)), true, null); //$NON-NLS-1$
	}

	@AfterEach
	void tearDown() throws CoreException {
		if (secondProject.exists()) {
			secondProject.delete(true, true, null);
		}
		if (firstProject.exists()) {
			firstProject.delete(true, true, null);
		}
	}

	@Test
	void keepsMarkersFromOtherConsumingProjects() throws CoreException {
		OCLMarkerManager.replaceMarkers(firstProject, List.of(createMarker("first"))); //$NON-NLS-1$
		OCLMarkerManager.replaceMarkers(secondProject, List.of(createMarker("second"))); //$NON-NLS-1$

		assertEquals(Set.of(firstProject.getFullPath().toPortableString(),
				secondProject.getFullPath().toPortableString()), getMarkerOwners());

		OCLMarkerManager.replaceMarkers(firstProject, List.of());

		assertEquals(Set.of(secondProject.getFullPath().toPortableString()), getMarkerOwners());
	}

	private OCLMarker createMarker(final String message) {
		return new OCLMarker(sharedFile,
				ErrorMarkerBuilder.createErrorMarkerBuilder(message).setType(IValidationMarker.TYPE));
	}

	private Set<String> getMarkerOwners() throws CoreException {
		return Arrays.stream(sharedFile.findMarkers(IValidationMarker.TYPE, true, IResource.DEPTH_ZERO))
				.map(marker -> marker.getAttribute(OCLMarkerManager.OWNER_PROJECT, null)).collect(Collectors.toSet());
	}

	private static IProject createProject(final String name) throws CoreException {
		final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
		if (project.exists()) {
			project.delete(true, true, null);
		}
		project.create(null);
		project.open(null);
		return project;
	}
}
