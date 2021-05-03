/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 * 				 2020 Primetals Technologies Germany GmbH
 * 				 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber, Bianca Wiesmayr, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Daniel Lindhuber
 *     - connection collection considering unfolded subapps
 *******************************************************************************/

package org.eclipse.fordiac.ide.elk.handlers;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.editparts.AbstractFBNElementEditPart;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.editparts.UnfoldedSubappContentEditPart;
import org.eclipse.fordiac.ide.application.figures.FBNetworkElementFigure;
import org.eclipse.fordiac.ide.elk.commands.LayoutCommand;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.gef.editparts.ValueEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class LayoutHandler extends AbstractHandler {

	private final Map<FBNetworkElement, FBNetworkElementFigure> fbNetworkElements = new HashMap<>();
	private final Map<Connection, org.eclipse.draw2d.Connection> connections = new HashMap<>();
	private final Map<Value, Label> values = new HashMap<>();
	private final Map<Value, Point> valueLocations = new HashMap<>();

	private IEditorPart editor;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		clear();
		editor = HandlerUtil.getActiveEditor(event);
		final GraphicalViewer viewer = editor.getAdapter(GraphicalViewer.class);
		if (null != viewer) {
			collectEditParts(viewer);
			final Command cmd = new LayoutCommand(fbNetworkElements, connections, values, valueLocations);
			((AbstractGraphicalEditPart) viewer.getRootEditPart()).getFigure().invalidateTree();
			((AbstractGraphicalEditPart) viewer.getRootEditPart()).getFigure().revalidate();
			editor.getAdapter(CommandStack.class).execute(cmd);
		}
		return null;
	}

	private void clear() {
		fbNetworkElements.clear();
		connections.clear();
		values.clear();
		editor = null;
	}

	private void collectEditParts(GraphicalViewer viewer) {
		final Map<Object, Object> editPartSet = viewer.getEditPartRegistry();
		editPartSet.values().forEach(entry -> {
			if ((entry instanceof AbstractFBNElementEditPart)
					&& !(((AbstractFBNElementEditPart) entry).getParent() instanceof UnfoldedSubappContentEditPart)) {
				fbNetworkElements.put(((AbstractFBNElementEditPart) entry).getModel(),
						((AbstractFBNElementEditPart) entry).getFigure());
			}
			if (entry instanceof ConnectionEditPart) {
				handleConnectionCollection((ConnectionEditPart) entry);
			}
			if (entry instanceof ValueEditPart) {
				final ValueEditPart ep = (ValueEditPart) entry;
				values.put(ep.getModel(), ep.getFigure());
				final Object iePart = ep.getViewer().getEditPartRegistry().get(ep.getModel().getVarDeclaration());
				if (iePart instanceof InterfaceEditPart) {
					values.put(ep.getModel(), ep.getFigure());
					final Point point = ((InterfaceEditPart) iePart).getFigure().getBounds().getTopLeft();
					valueLocations.put(((ValueEditPart) entry).getModel(), point);
				}
			}
		});
	}

	private void handleConnectionCollection(ConnectionEditPart entry) {
		if (isIgnorableConnection(entry)) {
			return;
		}
		final IFigure fig = entry.getFigure();
		if (fig.isVisible()) {
			/* use isVisble instead of isHidden -> the hideConnection command handler does not change the "hidden" field
			 * but only sets the visibility of the connection figure */
			connections.put(entry.getModel(), (org.eclipse.draw2d.Connection) fig);
		}
	}

	private boolean isIgnorableConnection(ConnectionEditPart entry) {
		final EObject sourceModel = (EObject) entry.getSource().getParent().getModel();
		final IInterfaceElement sourceIE = (IInterfaceElement) entry.getSource().getModel();
		final Object parent = sourceModel.eContainer().eContainer();
		final Object editorElement = editor.getAdapter(FBNetwork.class).eContainer();

		return (sourceModel instanceof SubApp && sourceIE.isIsInput() && ((SubApp) sourceModel).isUnfolded()
				&& sourceModel != editorElement)
				|| (parent instanceof SubApp && ((SubApp) parent).isUnfolded() && parent != editorElement);
	}

}
