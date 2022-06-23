/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.debug.st.breakpoint;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.debug.breakpoint.EvaluatorLineBreakpoint;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;

public class STLineBreakpoint extends EvaluatorLineBreakpoint {
	public static final String BREAKPOINT_MARKER = "org.eclipse.fordiac.ide.debug.st.stLineBreakpointMarker"; //$NON-NLS-1$

	public STLineBreakpoint() {
		super();
	}

	public STLineBreakpoint(final IResource resource, final int lineNumber) throws CoreException {
		super(resource, lineNumber);
	}

	public FBType getFBType() {
		final IMarker m = getMarker();
		if (m != null) {
			final IResource resource = m.getResource();
			if (resource instanceof IFile) {
				final TypeEntry typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile((IFile) resource);
				if (typeEntry instanceof FBTypeEntry) {
					return ((FBTypeEntry) typeEntry).getType();
				}
			}
		}
		return null;
	}

	@Override
	public String getMarkerId() {
		return BREAKPOINT_MARKER;
	}
}
