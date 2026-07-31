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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.validation.handlers.OCLParser;
import org.eclipse.ocl.ecore.OCL;

final class OCLTestFile implements AutoCloseable {

	private final IProject project;
	private final IFile file;
	private final OCL ocl;

	static OCLTestFile load(final String projectName, final String resourceName)
			throws CoreException, IOException {
		final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		if (project.exists()) {
			project.delete(true, true, null);
		}
		project.create(null);
		project.open(null);
		TypeLibraryManager.INSTANCE.getTypeLibrary(project);

		final IFile file = project.getFile(resourceName);
		try (InputStream input = OCLTestFile.class.getResourceAsStream("/ocl-files/" + resourceName)) { //$NON-NLS-1$
			assertNotNull(input);
			file.create(input, true, null);
		}
		return new OCLTestFile(project, file, OCLValidationSession.createOCL());
	}

	private OCLTestFile(final IProject project, final IFile file, final OCL ocl) {
		this.project = project;
		this.file = file;
		this.ocl = ocl;
	}

	OCL ocl() {
		return ocl;
	}

	List<OCLConstraintDefinition> parseDefinitions() {
		final OCLParser.ParseResult result = OCLParser.loadOCLConstraints(project, ocl);
		assertTrue(result.problems().isEmpty(), () -> result.problems().toString());
		return result.constraints().stream().filter(constraint -> file.equals(constraint.source()))
				.map(constraint -> OCLConstraintDefinition.from(constraint.constraint(), file)).toList();
	}

	@Override
	public void close() throws CoreException {
		ocl.dispose();
		TypeLibraryManager.INSTANCE.removeProject(project);
		if (project.exists()) {
			project.delete(true, true, null);
		}
	}
}
