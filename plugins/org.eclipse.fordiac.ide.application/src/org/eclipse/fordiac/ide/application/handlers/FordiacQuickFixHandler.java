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
 *   Paul Stemmer
 *    - Changed to get Markers out of Nat Tables
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.wizards.QuickFixWizardDialog;
import org.eclipse.fordiac.ide.gef.annotation.FordiacAnnotationUtil;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationModel;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalMarkerAnnotation;
import org.eclipse.fordiac.ide.gef.annotation.ResourceMarkerGraphicalAnnotationModel;
import org.eclipse.fordiac.ide.gef.validation.GraphicalValidationAnnotation;
import org.eclipse.fordiac.ide.model.errormarker.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.views.markers.WorkbenchMarkerResolution;

public class FordiacQuickFixHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) {
		final IStructuredSelection selection = HandlerUtil.getCurrentStructuredSelection(event);
		final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();

		IMarker[] selectedMarkers;

		if (selection.getFirstElement() instanceof VarDeclaration || selection.getFirstElement() instanceof FB) {
			selectedMarkers = getSelectedVdMarkersFromNatTable(selection);
		} else {
			selectedMarkers = getMarkersFromEditorSelection(selection);
		}

		final IMarkerResolution[] resolutions = IDE.getMarkerHelpRegistry().getResolutions(selectedMarkers[0]);
		final Map<IMarkerResolution, Collection<IMarker>> resolutionsMap = new HashMap<>();

		for (final IMarkerResolution res : resolutions) {
			if (res instanceof final WorkbenchMarkerResolution workbenchResolution) {
				final IMarker[] other = workbenchResolution.findOtherMarkers(selectedMarkers);
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
			if (selectedMarkers.length == 1) {
				MessageDialog.openInformation(shell, Messages.QuickFixDialog_Title,
						MessageFormat.format(Messages.QuickFixDialog_NoResolutionsFound,
								selectedMarkers[0].getAttribute(IMarker.MESSAGE, ""))); //$NON-NLS-1$

			} else {
				MessageDialog.openInformation(shell, Messages.QuickFixDialog_Title,
						Messages.QuickFixDialog_NoResolutionsFoundForMultiSelection);
			}
		} else {
			QuickFixWizardDialog.openDialog(shell, selectedMarkers, resolutions, resolutionsMap);
		}
		deleteTemporaryMarker(selectedMarkers);
		return null;
	}

	private static IMarker[] getSelectedVdMarkersFromNatTable(final IStructuredSelection selection) {
		if (selection.getFirstElement() instanceof final EObject obj) {
			return retrieveMarkerFromObject(obj);
		}
		return new IMarker[0];
	}

	public static IMarker[] retrieveMarkerFromObject(final Object obj) {
		final IEditorPart editor = EditorUtils.getCurrentActiveEditor();
		if (editor != null) {
			final GraphicalAnnotationModel annotationModel = editor.getAdapter(GraphicalAnnotationModel.class);
			if (annotationModel != null) {
				return annotationModel.getAnnotations(obj).stream().filter(GraphicalMarkerAnnotation.class::isInstance)
						.map(GraphicalMarkerAnnotation.class::cast).map(GraphicalMarkerAnnotation::getMarker)
						.filter(Objects::nonNull).toArray(IMarker[]::new);
			}
		}
		return new IMarker[0];
	}

	public static IMarker[] getMarkersFromEditorSelection(final IStructuredSelection selection) {
		return selection.stream().filter(EditPart.class::isInstance).map(EditPart.class::cast)
				.map(FordiacQuickFixHandler::getMarker).toArray(IMarker[]::new);
	}

	public static boolean hasMarker(final EditPart editPart) {
		final var annotationModel = FordiacAnnotationUtil.getAnnotationModel(editPart);
		if (annotationModel != null) {
			return annotationModel.hasAnnotation(editPart.getModel(),
					annotation -> annotation instanceof GraphicalMarkerAnnotation
							|| annotation instanceof GraphicalValidationAnnotation);
		}
		return false;
	}

	public static IMarker getMarker(final EditPart editPart) {
		if (FordiacAnnotationUtil
				.getAnnotationModel(editPart) instanceof final ResourceMarkerGraphicalAnnotationModel annotationModel) {
			final var annotation = annotationModel.getAnnotations(editPart.getModel());

			final Optional<GraphicalMarkerAnnotation> markerAnnotation = annotation.stream()
					.filter(GraphicalMarkerAnnotation.class::isInstance).map(GraphicalMarkerAnnotation.class::cast)
					.findFirst();
			if (markerAnnotation.isPresent()) {
				return markerAnnotation.get().getMarker();
			}

			if (!annotation.isEmpty() && annotation.iterator()
					.next() instanceof final GraphicalValidationAnnotation validationAnnotation) {
				createTemporaryMarker(validationAnnotation, annotationModel.getResource());
				final Optional<GraphicalMarkerAnnotation> newAnnotation = annotationModel
						.getAnnotations(editPart.getModel()).stream()
						.filter(GraphicalMarkerAnnotation.class::isInstance).map(GraphicalMarkerAnnotation.class::cast)
						.findFirst();

				if (newAnnotation.isPresent()) {
					return newAnnotation.get().getMarker();
				}
			}
		}
		return null;
	}

	private static void createTemporaryMarker(final GraphicalValidationAnnotation validationAnnotation,
			final IResource resource) {
		try {
			new WorkspaceModifyOperation(resource) {
				@Override
				protected void execute(final IProgressMonitor monitor)
						throws CoreException, InvocationTargetException, InterruptedException {
					ErrorMarkerBuilder.createErrorMarkerBuilder(validationAnnotation.getText())
							.addAdditionalAttributes(validationAnnotation.getAttributes())
							.setType(FordiacErrorMarker.TEMPORARY_MARKER).createMarker(resource);
				}
			}.run(new NullProgressMonitor());
		} catch (final InvocationTargetException | InterruptedException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
	}

	private static void deleteTemporaryMarker(final IMarker[] selectedMarkers) {
		final var temporaryMarkers = Arrays.stream(selectedMarkers).filter(marker -> {
			try {
				return marker.getType().equals(FordiacErrorMarker.TEMPORARY_MARKER);
			} catch (final CoreException e) {
				FordiacLogHelper.logError(e.getMessage(), e);
			}
			return false;
		}).toList();

		if (temporaryMarkers.isEmpty()) {
			return;
		}

		try {
			new WorkspaceModifyOperation(temporaryMarkers.getFirst().getResource()) {
				@Override
				protected void execute(final IProgressMonitor monitor)
						throws CoreException, InvocationTargetException, InterruptedException {
					temporaryMarkers.forEach(marker -> {
						try {
							if (marker.getType().equals(FordiacErrorMarker.TEMPORARY_MARKER)) {
								marker.delete();
							}
						} catch (final CoreException e) {
							FordiacLogHelper.logError(e.getMessage(), e);
						}
					});
				}
			}.run(new NullProgressMonitor());
		} catch (final InvocationTargetException | InterruptedException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
	}
}