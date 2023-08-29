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

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.errormarker.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;
import org.eclipse.xtext.ui.validation.DefaultResourceUIValidatorExtension;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.Issue;

public class STCoreResourceUIValidatorExtension extends DefaultResourceUIValidatorExtension {

	@Override
	protected void createMarkers(final IFile file, final List<Issue> list, final IProgressMonitor monitor)
			throws CoreException {
		final boolean ignoreWarnings = ValidationUtil.isIgnoreWarnings(file);
		super.createMarkers(file,
				list.stream().filter(
						issue -> ValidationUtil.shouldProcess(issue, ignoreWarnings) && !isModelValidationIssue(issue))
						.toList(),
				monitor);
		for (final Issue issue : list) {
			if (ValidationUtil.shouldProcess(issue, ignoreWarnings) && isModelValidationIssue(issue)) {
				createMarker(file, FordiacErrorMarker.VALIDATION_MARKER, issue);
			}
		}
	}

	@Override
	public void deleteMarkers(final IFile file, final CheckMode checkMode, final IProgressMonitor monitor)
			throws CoreException {
		super.deleteMarkers(file, checkMode, monitor);
		file.deleteMarkers(FordiacErrorMarker.VALIDATION_MARKER, true, IResource.DEPTH_ZERO);
	}

	protected static void createMarker(final IFile file, final String type, final Issue issue) throws CoreException {
		ErrorMarkerBuilder.createErrorMarkerBuilder(issue.getMessage()).setType(type)
				.setSeverity(getMarkerSeverity(issue)).addAdditionalAttributes(getMarkerAttributes(issue))
				.createMarker(file);
	}

	protected static int getMarkerSeverity(final Issue issue) {
		return switch (issue.getSeverity()) {
		case ERROR -> IMarker.SEVERITY_ERROR;
		case WARNING -> IMarker.SEVERITY_WARNING;
		case INFO -> IMarker.SEVERITY_INFO;
		default -> throw new IllegalArgumentException(String.valueOf(issue.getSeverity()));
		};
	}

	protected static Map<String, Object> getMarkerAttributes(final Issue issue) {
		final URI canonicalURI = getCanonicalURI(issue.getUriToProblem());
		final String[] data = issue.getData();
		if (canonicalURI == null || data == null || data.length < 3) {
			return Collections.emptyMap();
		}
		return Map.of(IMarker.LOCATION, data[0], FordiacErrorMarker.TARGET_URI, canonicalURI.toString(),
				FordiacErrorMarker.TARGET_TYPE, data[2]);
	}

	protected static URI getCanonicalURI(final URI uri) {
		if (uri != null && uri.hasFragment() && uri.fragment().startsWith("/1")) { //$NON-NLS-1$
			return uri.trimFragment().appendFragment(uri.fragment().substring(2));
		}
		return uri;
	}

	protected static boolean isModelValidationIssue(final Issue issue) {
		return issue.getCode() != null && issue.getCode().startsWith(LibraryElementValidator.DIAGNOSTIC_SOURCE);
	}
}
