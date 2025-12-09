/*******************************************************************************
 * Copyright (c) 2023, 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.utilities;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.application.editparts.AbstractContainerContentEditPart;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.application.editparts.UnfoldedSubappContentEditPart;
import org.eclipse.fordiac.ide.application.editparts.UntypedSubAppInterfaceElementEditPart.UntypedSubappIEAdapter;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.ui.IEditorPart;

public final class GetEditPartFromGraficalViewerHelper {

	public static AbstractContainerContentEditPart findAbstractContainerContentEditPartAtPosition(
			final IEditorPart editor, final Point pos, final FBNetwork fbNetwork) {
		final GraphicalViewer graphicalViewer = editor.getAdapter(GraphicalViewer.class);
		if (graphicalViewer.getEditPartForModel(fbNetwork) instanceof final GraphicalEditPart graphicalEditPart) {
			final IFigure figure = graphicalEditPart.getFigure().findFigureAt(pos.x, pos.y);
			if (figure != null) {
				final Object targetObject = graphicalViewer.getVisualPartMap().get(figure);
				if (targetObject instanceof final AbstractContainerContentEditPart abstractContainerContentEditPart) {
					return abstractContainerContentEditPart;
				}
			}
		}
		return null;
	}

	public static AbstractContainerContentEditPart findAbstractContainerContentEditFromInterfaceElement(
			final IInterfaceElement ie) {
		final UntypedSubappIEAdapter adapter = ie.eAdapters().stream().filter(UntypedSubappIEAdapter.class::isInstance)
				.map(UntypedSubappIEAdapter.class::cast).findFirst().orElse(null);
		if (adapter != null && adapter.getUntypedSubAppInterfaceElementEditPart()
				.getParent() instanceof final SubAppForFBNetworkEditPart subappEP) {
			return subappEP.getChildren().stream().filter(UnfoldedSubappContentEditPart.class::isInstance)
					.map(UnfoldedSubappContentEditPart.class::cast).findFirst().orElse(null);
		}
		return null;
	}

	private GetEditPartFromGraficalViewerHelper() {
		throw new UnsupportedOperationException("Helper class should not be instantiated!"); //$NON-NLS-1$
	}

}
