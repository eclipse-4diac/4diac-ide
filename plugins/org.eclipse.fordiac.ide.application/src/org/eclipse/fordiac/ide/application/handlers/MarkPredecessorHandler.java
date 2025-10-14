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
import java.util.Map.Entry;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.editparts.AbstractBlockFBNElementEditPart;
import org.eclipse.fordiac.ide.application.editparts.FBNetworkRootEditPart;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationStyles;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.menus.UIElement;

public class MarkPredecessorHandler extends AbstractHandler implements IElementUpdater {

	private static final String MARKER_COLOR = "org.eclipse.fordiac.ide.ui.PredecessorMarkerColor"; //$NON-NLS-1$
	private static Map<URI, AbstractBlockFBNElementEditPart> predecessorMap = new HashMap<>();

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = HandlerUtil.getCurrentStructuredSelection(event);

		final AbstractBlockFBNElementEditPart ep = getValidSelectedFBNElement(selection);

		if (ep != null) {
			// we don't need to check root here anymore as it is checked in the getter above
			final FBNetworkRootEditPart root = (FBNetworkRootEditPart) ep.getRoot();
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
			if (viewer == null || viewer.getSelectedEditParts().isEmpty()) {
				return;
			}

			final EditPart editPart = viewer.getSelectedEditParts().getFirst();

			if ((editPart.getRoot() instanceof final FBNetworkRootEditPart root && getPredecessor(root) != null)
					&& getPredecessor(root).equals(editPart)) {
				element.setText(Messages.FBMarker_RemovePredecessorMarker);
			}
		}
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final IEditorPart editor = (IEditorPart) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_EDITOR_NAME);
		if (editor != null) {
			final ISelection selection = (ISelection) HandlerUtil.getVariable(evaluationContext,
					ISources.ACTIVE_CURRENT_SELECTION_NAME);
			setBaseEnabled(getValidSelectedFBNElement(selection) != null);
		} else {
			setBaseEnabled(false);
		}
	}

	private static AbstractBlockFBNElementEditPart getValidSelectedFBNElement(final ISelection selection) {
		if (selection instanceof final IStructuredSelection structSel
				&& structSel.getFirstElement() instanceof final AbstractBlockFBNElementEditPart ep
				&& ep.getRoot() instanceof FBNetworkRootEditPart) {
			return ep;
		}
		return null;
	}

	public static void setPredecessor(final FBNetworkRootEditPart root,
			final AbstractBlockFBNElementEditPart predecessorEP) {
		final AbstractBlockFBNElementEditPart previousEntry = predecessorMap
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

	public static AbstractBlockFBNElementEditPart getPredecessor(final FBNetworkRootEditPart root) {
		return predecessorMap.get(EcoreUtil.getURI(root.getAdapter(FBNetwork.class)));
	}

	public static boolean hasPredecessorMarker(final INamedElement element) {
		if (element instanceof final FBNetworkElement fb) {
			for (final Entry<URI, AbstractBlockFBNElementEditPart> entry : predecessorMap.entrySet()) {
				if (entry.getValue() != null && entry.getValue().getModel().equals(fb)) {
					return true;
				}
			}
		}
		return false;
	}

	private static boolean isActivePredecessor(final AbstractBlockFBNElementEditPart ep) {
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
