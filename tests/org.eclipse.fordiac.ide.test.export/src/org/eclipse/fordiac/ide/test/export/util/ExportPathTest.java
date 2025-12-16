/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: Mario Kastner - initial API and implementation and/or initial
 * documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.export.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.export.utils.ExportFilterUtil;
import org.eclipse.fordiac.ide.test.model.FordiacProjectLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.osgi.framework.Bundle;

class ExportPathTest {

	private static IProject project;

	@BeforeAll
	static void setup() throws CoreException, IOException {
		project = loadProject();
		assertNotNull(project);
	}

	@SuppressWarnings("static-method")
	@ParameterizedTest
	@CsvSource({ "A/B/C, true", "C:/A/B/C, false", "A/../../C, false" })
	void testValidateExportPath(final String path, final String isValid) {
		assertEquals(Boolean.valueOf(ExportFilterUtil.validateExportPath(path, project)), Boolean.valueOf(isValid));
	}

	private static IProject loadProject() throws CoreException, IOException {
		final Bundle bundle = Platform.getBundle("org.eclipse.fordiac.ide.test.export"); //$NON-NLS-1$
		final Path projectPath = new Path("data/EmptyTestProject"); //$NON-NLS-1$
		final FordiacProjectLoader loader = new FordiacProjectLoader(bundle, projectPath);
		return loader.getEclipseProject();
	}

}
