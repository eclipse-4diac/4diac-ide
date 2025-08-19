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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.editparts.AbstractBlockFBNElementEditPart;
import org.eclipse.fordiac.ide.application.editparts.FBNetworkRootEditPart;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationStyles;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.fordiac.util.marker.MarkerDescriptor;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

public class MarkPredecessorHandler extends AbstractMarkerHandler {

	public static void setPredecessor(final FBNetworkElement elem) {
		getStore().ifPresent(s -> {
			if (getEP(elem) instanceof final GraphicalEditPart ep) {
				s.storeEditPart(MarkerDescriptor.PREDECESSOR.ID(), ep);
				GraphicalAnnotationStyles.setAnnotationFeedbackBorder(ep.getFigure(),
						MarkerDescriptor.PREDECESSOR.color());
				setStatusLineMessage(
						MessageFormat.format(Messages.FBMarker_ActivePredecessor, elem.getQualifiedName()));
			}
		});

	}

	public static void removePredecessor() {
		getStore().ifPresent(s -> {
			if (s.removeElementByID(MarkerDescriptor.PREDECESSOR.ID()) instanceof final GraphicalEditPart ep) {
				GraphicalAnnotationStyles.removeAnnotationBorders(ep.getFigure());
				setStatusLineMessage(""); //$NON-NLS-1$
			}
		});
	}

	@Override
	protected void markElement(final GraphicalEditPart ep) {
		super.markElement(ep);
		if (ep instanceof final AbstractBlockFBNElementEditPart fbnep) {
			setStatusLineMessage(
					MessageFormat.format(Messages.FBMarker_ActivePredecessor, fbnep.getModel().getQualifiedName()));
		}
	}

	@Override
	protected void removeElementMarker(final GraphicalEditPart ep) {
		super.removeElementMarker(ep);
		setStatusLineMessage(""); //$NON-NLS-1$
	}

	public static void setStatusLineMessage(final String message) {
		final IStatusLineManager manager = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActiveEditor().getEditorSite().getActionBars().getStatusLineManager();
		manager.setMessage(message);
		manager.update(false);
	}

	@Override
	protected MarkerDescriptor getDescriptor() {
		return MarkerDescriptor.PREDECESSOR;
	}

	@Override
	protected GraphicalEditPart getValidSelectedElement(final ISelection selection) {
		if (selection instanceof final IStructuredSelection structSel
				&& structSel.getFirstElement() instanceof final AbstractBlockFBNElementEditPart ep
				&& ep.getRoot() instanceof FBNetworkRootEditPart) {
			return ep;
		}
		return null;
	}

	private static GraphicalEditPart getEP(final EObject elem) {
		final IEditorPart currentActiveEditor = EditorUtils.getCurrentActiveEditor();
		if (currentActiveEditor != null) {
			final GraphicalViewer viewer = currentActiveEditor.getAdapter(GraphicalViewer.class);
			if (viewer.getEditPartRegistry().get(elem) instanceof final GraphicalEditPart ep) {
				return ep;
			}
		}
		return null;
	}

}
