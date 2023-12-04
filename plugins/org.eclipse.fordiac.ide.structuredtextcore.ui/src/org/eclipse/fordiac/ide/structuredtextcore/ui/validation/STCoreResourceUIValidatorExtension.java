/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.ui.validation;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.xtext.ui.validation.DefaultResourceUIValidatorExtension;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.Issue;

public class STCoreResourceUIValidatorExtension extends DefaultResourceUIValidatorExtension {

	@Override
	protected void createMarkers(final IFile file, final List<Issue> list, final IProgressMonitor monitor)
			throws CoreException {
		final boolean ignoreWarnings = ValidationUtil.isIgnoreWarnings(file);
		super.createMarkers(file,
				list.stream().filter(issue -> ValidationUtil.shouldProcess(issue, ignoreWarnings)).toList(), monitor);
	}

	@Override
	public void deleteMarkers(final IFile file, final CheckMode checkMode, final IProgressMonitor monitor)
			throws CoreException {
		super.deleteMarkers(file, checkMode, monitor);
		file.deleteMarkers(FordiacErrorMarker.IEC61499_MARKER, true, IResource.DEPTH_ZERO);
		file.deleteMarkers(FordiacErrorMarker.VALIDATION_MARKER, true, IResource.DEPTH_ZERO);
	}
}
