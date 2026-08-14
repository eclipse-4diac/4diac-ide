/*******************************************************************************
 * Copyright (c) 2025 Felix Schmid
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.marker.resolution;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.stream.Stream;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;
import org.eclipse.fordiac.ide.util.FordiacLogHelper;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.views.markers.WorkbenchMarkerResolution;

public abstract class ChangeNameMarkerResolution extends WorkbenchMarkerResolution {

	private final IMarker marker;

	protected ChangeNameMarkerResolution(final IMarker marker) {
		this.marker = marker;
	}

	@Override
	public void run(final IMarker marker) {
		try {
			new WorkspaceModifyOperation() {
				@Override
				protected void execute(final IProgressMonitor monitor)
						throws CoreException, InvocationTargetException, InterruptedException {
					runInWorkspace(marker);
				}
			}.run(new NullProgressMonitor());
		} catch (final InvocationTargetException e) {
			if (e.getCause() instanceof final CoreException ce) {
				ErrorDialog.openError(null, null, null, ce.getStatus());
			} else {
				FordiacLogHelper.logError(e.getMessage(), e);
			}
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	protected abstract void runInWorkspace(IMarker marker) throws CoreException;

	@Override
	public Image getImage() {
		return null;
	}

	@Override
	public IMarker[] findOtherMarkers(final IMarker[] markers) {
		return Stream.of(markers)
				.filter(other -> LibraryElementValidator.DIAGNOSTIC_SOURCE.equals(FordiacErrorMarker.getSource(other))
						&& isApplicable(other))
				.toArray(IMarker[]::new);
	}

	protected boolean isApplicable(final IMarker other) {
		return FordiacErrorMarker.getCode(marker) == FordiacErrorMarker.getCode(other)
				&& Arrays.equals(FordiacErrorMarker.getData(other), FordiacErrorMarker.getData(marker));
	}

	protected static CoreException createExceptionForMarker(final String pattern, final IMarker marker) {
		return new CoreException(Status.error(MessageFormat.format(pattern, marker.getResource().getFullPath())));
	}
}
