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
package org.eclipse.fordiac.ide.structuredtextcore.ui.document;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionProvider;
import org.eclipse.xtext.ui.editor.quickfix.XtextResourceMarkerAnnotationModel;
import org.eclipse.xtext.ui.util.IssueUtil;

public class LibraryElementXtextResourceMarkerAnnotationModel extends XtextResourceMarkerAnnotationModel {

	private final String markerType;

	public LibraryElementXtextResourceMarkerAnnotationModel(final IFile file,
			final IssueResolutionProvider issueResolutionProvider, final IssueUtil markerUtil,
			final String markerType) {
		super(file, issueResolutionProvider, markerUtil);
		this.markerType = markerType;
	}

	@Override
	protected boolean isAcceptable(final IMarker marker) {
		try {
			return super.isAcceptable(marker) && (isGlobalMarker(marker) || isLanguageMarker(marker));
		} catch (final CoreException e) {
			return false;
		}
	}

	@SuppressWarnings("static-method") // subclasses may override
	protected boolean isGlobalMarker(final IMarker marker) throws CoreException {
		return IMarker.BOOKMARK.equals(marker.getType()) || IMarker.TASK.equals(marker.getType());
	}

	protected boolean isLanguageMarker(final IMarker marker) throws CoreException {
		return marker.isSubtypeOf(markerType);
	}
}
