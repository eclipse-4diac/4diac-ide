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

import java.nio.file.Path;

import org.eclipse.core.resources.IProject;
import org.eclipse.fordiac.ide.export.ExportException;
import org.eclipse.fordiac.ide.export.forte_ng.ForteNgExportTemplate;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;

public class CMakeConfigTemplate extends ForteNgExportTemplate {

	public CMakeConfigTemplate(final IProject project) {
		super(generateConfigTemplateName(project), Path.of("")); //$NON-NLS-1$
	}

	@Override
	public CharSequence generate() throws ExportException {
		final StringBuilder builder = new StringBuilder();
		builder.append("# ").append(HEADER_TEXT).append(System.lineSeparator()).append(System.lineSeparator()); //$NON-NLS-1$
		builder.append("@PACKAGE_INIT@").append(System.lineSeparator()); //$NON-NLS-1$
		builder.append(System.lineSeparator());
		builder.append("include(${CMAKE_CURRENT_LIST_DIR}/@PROJECT_NAME@-export.cmake)"); //$NON-NLS-1$
		builder.append(System.lineSeparator());
		builder.append("check_required_components(@PROJECT_NAME@)"); //$NON-NLS-1$
		return builder;
	}

	protected static String generateConfigTemplateName(final IProject project) {
		return "forte-" + getProjectName(project).toLowerCase() + "-config.cmake.in"; //$NON-NLS-1$ //$NON-NLS-2$
	}

	protected static String getProjectName(final IProject project) {
		final Manifest manifest = ManifestHelper.getContainerManifest(project);
		if (manifest == null || manifest.getProduct() == null || manifest.getProduct().getSymbolicName() == null) {
			return project.getName();
		}
		return manifest.getProduct().getSymbolicName();
	}
}
