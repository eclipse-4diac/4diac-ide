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
package org.eclipse.fordiac.ide.validation.handlers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.validation.ocl.OCLValidationSession;
import org.eclipse.ocl.ecore.OCL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OCLParserTest {

	private IProject project;

	@BeforeEach
	void setUp() throws CoreException, IOException {
		project = ResourcesPlugin.getWorkspace().getRoot().getProject("OCLParserTest"); //$NON-NLS-1$
		if (project.exists()) {
			project.delete(true, true, null);
		}
		project.create(null);
		project.open(null);
		TypeLibraryManager.INSTANCE.getTypeLibrary(project);

		final IFile file = project.getFile("MultipleImports.ocl"); //$NON-NLS-1$
		try (InputStream input = getClass().getResourceAsStream("/ocl-files/MultipleImports.ocl")) { //$NON-NLS-1$
			assertNotNull(input);
			file.create(input, true, null);
		}
	}

	@AfterEach
	void tearDown() throws CoreException {
		TypeLibraryManager.INSTANCE.removeProject(project);
		if (project.exists()) {
			project.delete(true, true, null);
		}
	}

	@Test
	void parsesMultipleEditorImports() {
		final OCL ocl = OCLValidationSession.createOCL();
		try {
			final OCLParser.ParseResult result = OCLParser.loadOCLConstraints(project, ocl);

			assertTrue(result.problems().isEmpty(), () -> result.problems().toString());
			assertTrue(result.constraints().stream()
					.anyMatch(constraint -> "MultipleImports".equals(constraint.constraint().getName()))); //$NON-NLS-1$
		} finally {
			ocl.dispose();
		}
	}
}
