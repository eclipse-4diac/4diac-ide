/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner - initial implementation and/or documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.application.handlers;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.editparts.AbstractFBNElementEditPart;
import org.eclipse.fordiac.ide.application.editparts.FBNetworkRootEditPart;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationStyles;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.menus.UIElement;

public class MarkPredecessorHandler extends AbstractHandler implements IElementUpdater {

	private static final String MARKER_COLOR = "org.eclipse.fordiac.ide.ui.PredecessorMarkerColor"; //$NON-NLS-1$
	private static Map<URI, AbstractFBNElementEditPart> predecessorMap = new HashMap<>();

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = HandlerUtil.getCurrentStructuredSelection(event);

		if ((selection.getFirstElement() instanceof final AbstractFBNElementEditPart ep)
				&& (ep.getRoot() instanceof final FBNetworkRootEditPart root)) {
			if (isActivePredecessor(ep)) {
				removePredecessor(root);
			} else {
				setPredecessor(root, ep);
			}
		}
		return null;
	}

	@Override
	public void updateElement(final UIElement element, final Map parameters) {
		final IEditorPart currentActiveEditor = EditorUtils.getCurrentActiveEditor();
		if (currentActiveEditor != null) {
			final GraphicalViewer viewer = currentActiveEditor.getAdapter(GraphicalViewer.class);
			final EditPart editPart = viewer.getSelectedEditParts().getFirst();

			if ((editPart.getRoot() instanceof final FBNetworkRootEditPart root && getPredecessor(root) != null)
					&& getPredecessor(root).equals(editPart)) {
				element.setText(Messages.FBMarker_RemovePredecessorMarker);
			}
		}
	}

	public static void setPredecessor(final FBNetworkRootEditPart root,
			final AbstractFBNElementEditPart predecessorEP) {
		final AbstractFBNElementEditPart previousEntry = predecessorMap
				.put(EcoreUtil.getURI(root.getAdapter(FBNetwork.class)), predecessorEP);
		if (previousEntry != null) {
			GraphicalAnnotationStyles.removeAnnotationBorders(previousEntry.getFigure());
		}
		if (predecessorEP != null) {
			GraphicalAnnotationStyles.setAnnotationFeedbackBorder(predecessorEP.getFigure(),
					JFaceResources.getColorRegistry().get(MARKER_COLOR));
			setStatusLineMessage(MessageFormat.format(Messages.FBMarker_ActivePredecessor,
					predecessorEP.getModel().getQualifiedName()));
		}
	}

	public static void removePredecessor(final FBNetworkRootEditPart root) {
		setPredecessor(root, null);
		setStatusLineMessage(""); //$NON-NLS-1$
	}

	public static AbstractFBNElementEditPart getPredecessor(final FBNetworkRootEditPart root) {
		return predecessorMap.get(EcoreUtil.getURI(root.getAdapter(FBNetwork.class)));
	}

	private static boolean isActivePredecessor(final AbstractFBNElementEditPart ep) {
		if (ep.getRoot() instanceof final FBNetworkRootEditPart root) {
			return getPredecessor(root) != null && getPredecessor(root).equals(ep);
		}
		return false;
	}

	private static void setStatusLineMessage(final String message) {
		final IStatusLineManager manager = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActiveEditor().getEditorSite().getActionBars().getStatusLineManager();
		manager.setMessage(message);
		manager.update(false);
	}

}
