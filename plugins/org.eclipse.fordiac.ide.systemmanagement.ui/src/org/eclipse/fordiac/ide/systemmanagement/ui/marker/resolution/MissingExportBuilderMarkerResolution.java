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

import java.util.stream.Stream;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.systemmanagement.nature.FordiacNature;
import org.eclipse.fordiac.ide.systemmanagement.ui.Messages;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class MissingExportBuilderMarkerResolution extends WorkspaceMarkerResolution {

	@Override
	protected void runInWorkspace(final IMarker[] markers, final IProgressMonitor monitor) throws CoreException {
		final SubMonitor subMonitor = SubMonitor.convert(monitor, markers.length);
		for (final IMarker marker : markers) {
			subMonitor.subTask(marker.getAttribute(IMarker.MESSAGE, "")); //$NON-NLS-1$
			final IProject project = marker.getResource().getProject();
			final IProjectDescription description = project.getDescription();
			if (FordiacNature.configureExportBuilder(description)) {
				project.setDescription(description, subMonitor.split(1));
			} else {
				subMonitor.split(1);
			}
			if (project
					.getNature(SystemManager.FORDIAC_PROJECT_NATURE_ID) instanceof final FordiacNature fordiacNature) {
				fordiacNature.validate();
			}
		}
	}

	@Override
	public String getLabel() {
		return Messages.MissingExportBuilderMarkerResolution_Label;
	}

	@Override
	public String getDescription() {
		return Messages.MissingExportBuilderMarkerResolution_Description;
	}

	@Override
	public Image getImage() {
		return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ADD);
	}

	@Override
	public IMarker[] findOtherMarkers(final IMarker[] markers) {
		return Stream.of(markers)
				.filter(other -> FordiacNature.class.getName().equals(FordiacErrorMarker.getSource(other))
						&& FordiacNature.MISSING_EXPORT_BUILDER == FordiacErrorMarker.getCode(other))
				.toArray(IMarker[]::new);
	}
}
