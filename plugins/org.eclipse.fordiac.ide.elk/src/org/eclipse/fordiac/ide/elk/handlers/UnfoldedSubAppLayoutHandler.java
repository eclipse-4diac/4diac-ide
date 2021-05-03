/*******************************************************************************
 * Copyright (c) 2020 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.elk.handlers;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.application.editparts.AbstractFBNElementEditPart;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.application.editparts.UnfoldedSubappContentEditPart;
import org.eclipse.fordiac.ide.application.figures.FBNetworkElementFigure;
import org.eclipse.fordiac.ide.elk.commands.LayoutCommand;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.gef.editparts.ValueEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
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

public class UnfoldedSubAppLayoutHandler extends AbstractHandler {

	private final Map<FBNetworkElement, FBNetworkElementFigure> fbNetworkElements = new HashMap<>();
	private final Map<org.eclipse.fordiac.ide.model.libraryElement.Connection, org.eclipse.draw2d.Connection> connections = new HashMap<>();
	private final Map<Value, Label> values = new HashMap<>();
	private final Map<Value, Point> valueLocations = new HashMap<>();

	private UnfoldedSubappContentEditPart subapp;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		clear();
		final Object selection = HandlerUtil.getCurrentStructuredSelection(event).getFirstElement();
		if (selection instanceof SubAppForFBNetworkEditPart) {
			for (final Object ep : ((SubAppForFBNetworkEditPart) selection).getChildren()) {
				if (ep instanceof UnfoldedSubappContentEditPart) {
					subapp = (UnfoldedSubappContentEditPart) ep;
				}
			}
		} else {
			return null;
		}
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
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
	}

	private void collectEditParts(GraphicalViewer viewer) {
		final Map<Object, Object> editPartSet = viewer.getEditPartRegistry();
		editPartSet.values().forEach(entry -> {
			if ((entry instanceof AbstractFBNElementEditPart)
					&& (((AbstractFBNElementEditPart) entry).getParent() == subapp)) {
				fbNetworkElements.put(((AbstractFBNElementEditPart) entry).getModel(),
						((AbstractFBNElementEditPart) entry).getFigure());
			}
			if (entry instanceof ConnectionEditPart) {
				handleConnectionCollection((ConnectionEditPart) entry);
			}
			if ((entry instanceof ValueEditPart)
					&& (((ValueEditPart) entry).getParent() == subapp)) {
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
		final Object sourceModel = entry.getSource().getParent().getModel();
		final boolean isVisible = entry.getFigure().isVisible();
		if ((sourceModel instanceof SubApp) && isVisible && ((SubApp) sourceModel == subapp.getModel().eContainer())
				&& (entry.getSource().getModel() instanceof IInterfaceElement)
				&& ((IInterfaceElement) entry.getSource().getModel()).isIsInput()) {
			// source is interface element of selected subapp
			connections.put(entry.getModel(), (Connection) entry.getFigure());
		}
		if ((sourceModel instanceof SubApp) && isVisible
				&& (((SubApp) sourceModel).eContainer().eContainer() == subapp.getModel().eContainer())
				&& (entry.getSource().getModel() instanceof IInterfaceElement)
				&& !((IInterfaceElement) entry.getSource().getModel()).isIsInput()) {
			// source is interface element of subapp inside selected subapp
			connections.put(entry.getModel(), (Connection) entry.getFigure());
		}
		if ((sourceModel instanceof FB) && isVisible
				&& (((FB) sourceModel).eContainer().eContainer() == subapp.getModel().eContainer())) {
			// source is FB inside selected subapp
			connections.put(entry.getModel(), (Connection) entry.getFigure());
		}
	}

}
