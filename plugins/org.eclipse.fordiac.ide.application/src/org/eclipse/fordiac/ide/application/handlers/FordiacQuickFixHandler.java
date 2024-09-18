/*******************************************************************************
 * Copyright (c) 2022, 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dario Romano
 *    - initial API and implementation and/or initial documentation
 *   Sebastian Hollersbacher
 *    - Changed to use Markers and QuickFixWizard
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.application.wizards.QuickFixWizardDialog;
import org.eclipse.fordiac.ide.gef.annotation.FordiacAnnotationUtil;
import org.eclipse.fordiac.ide.gef.annotation.FordiacMarkerGraphicalAnnotationModel;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalMarkerAnnotation;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.views.markers.WorkbenchMarkerResolution;

public class FordiacQuickFixHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) {
		final IStructuredSelection selection = HandlerUtil.getCurrentStructuredSelection(event);
		final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();

		final IMarker[] selectedMarkers = getMarkersFromEditorSelection(selection);
		final IMarkerResolution[] resolutions = IDE.getMarkerHelpRegistry().getResolutions(selectedMarkers[0]);
		final IMarker[] allMarkers = getAllMarker(selectedMarkers[0].getResource().getProject());
		final Map<IMarkerResolution, Collection<IMarker>> resolutionsMap = new HashMap<>();

		for (final IMarkerResolution res : resolutions) {
			if (res instanceof final WorkbenchMarkerResolution workbenchResolution) {
				final IMarker[] other = workbenchResolution.findOtherMarkers(allMarkers);
				final Set<IMarker> markersSet = new LinkedHashSet<>();
				markersSet.addAll(Arrays.asList(selectedMarkers));
				markersSet.addAll(Arrays.asList(other));
				if (markersSet.size() == other.length) {
					resolutionsMap.put(res, markersSet);
				}
			} else if (selectedMarkers.length == 1) {
				resolutionsMap.put(res, Arrays.asList(selectedMarkers));
			}
		}

		if (resolutionsMap.isEmpty()) {
			MessageDialog.openInformation(shell, "Quick Fix", //$NON-NLS-1$
					"The selected problems do not have a common applicable quick fix."); //$NON-NLS-1$
		} else {
			QuickFixWizardDialog.openDialog(shell, selectedMarkers, resolutions, resolutionsMap);
		}

		return null;
	}

	public static IMarker[] getMarkersFromEditorSelection(final IStructuredSelection selection) {
		return selection.stream().filter(EditPart.class::isInstance).map(EditPart.class::cast)
				.map(FordiacQuickFixHandler::getMarker).toArray(IMarker[]::new);
	}

	public static IMarker getMarker(final EditPart editPart) {
		if (FordiacAnnotationUtil
				.getAnnotationModel(editPart) instanceof final FordiacMarkerGraphicalAnnotationModel annotationModel) {
			final var annotation = annotationModel.getAnnotations(editPart.getModel());
			if (!annotation.isEmpty()
					&& annotation.iterator().next() instanceof final GraphicalMarkerAnnotation markerAnnotation) {
				return markerAnnotation.getMarker();
			}
		}
		return null;
	}

	private static IMarker[] getAllMarker(final IResource resource) {
		try {
			return resource.findMarkers(IMarker.MARKER, true, IResource.DEPTH_INFINITE);
		} catch (final CoreException e) {
			return new IMarker[0];
		}
	}
}
