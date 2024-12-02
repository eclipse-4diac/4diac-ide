/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.systemmanagement.ui.marker.resolution;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.views.markers.WorkbenchMarkerResolution;

public abstract class WorkspaceMarkerResolution extends WorkbenchMarkerResolution {

	@Override
	public final void run(final IMarker marker) {
		if (!marker.exists()) {
			return;
		}
		run(new IMarker[] { marker }, new NullProgressMonitor());
	}

	@Override
	public final void run(final IMarker[] markers, final IProgressMonitor monitor) {
		final WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {

			@Override
			protected void execute(final IProgressMonitor monitor)
					throws CoreException, InvocationTargetException, InterruptedException {
				runInWorkspace(markers, monitor);
			}

		};
		try {
			operation.run(monitor);
		} catch (final InvocationTargetException e) {
			FordiacLogHelper.logError(e.getMessage(), e.getCause());
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			FordiacLogHelper.logError(e.getMessage(), e.getCause());
		}
	}

	protected abstract void runInWorkspace(final IMarker[] markers, final IProgressMonitor monitor)
			throws CoreException;
}