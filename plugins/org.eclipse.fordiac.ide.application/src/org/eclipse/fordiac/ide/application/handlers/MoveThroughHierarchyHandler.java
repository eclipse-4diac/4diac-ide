/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import static org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper.getCommandStack;
import static org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper.getFBNetwork;
import static org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper.openEditor;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.commands.MoveAndReconnectCommand;
import org.eclipse.fordiac.ide.application.utilities.SubAppHierarchyDialog;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class MoveThroughHierarchyHandler extends AbstractHandler {
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);

		if (null != editor) {
			final ISelection selection = HandlerUtil.getCurrentSelection(event);
			final List<FBNetworkElement> fbelements = HandlerHelper.getSelectedFBNElements(selection);

			if (!fbelements.isEmpty() && allElementsFromSameNetwork(fbelements)) {
				// get selected FBNetwork
				final SubAppHierarchyDialog dialog = new SubAppHierarchyDialog(fbelements.get(0), fbelements);
				final FBNetwork destinationNetwork = dialog.open();
				if (destinationNetwork == null) {
					return Status.CANCEL_STATUS;
				}

				final Point destination = getDestination(editor, fbelements, destinationNetwork);
				final MoveAndReconnectCommand cmd = new MoveAndReconnectCommand(fbelements, destination,
						destinationNetwork);
				getCommandStack(editor).execute(cmd);

				// Update editor view
				EObject obj = destinationNetwork.eContainer();
				while (obj instanceof final SubApp subapp && subapp.isUnfolded()) {
					obj = subapp.eContainer().eContainer();
				}

				final GraphicalViewer viewer = HandlerHelper.openEditor(obj).getAdapter(GraphicalViewer.class);
				HandlerHelper.selectElement(fbelements.get(0), viewer);

			}
		}
		return Status.OK_STATUS;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final ISelection selection = (ISelection) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME);

		final List<FBNetworkElement> fbElements = HandlerHelper.getSelectedFBNElements(selection);
		if (!fbElements.isEmpty()) {
			// we are inside of a subapp
			final FBNetwork parent = fbElements.get(0).getFbNetwork();
			final boolean sameParentNetwork = fbElements.stream().allMatch(el -> parent.equals(el.getFbNetwork()));
			setBaseEnabled(sameParentNetwork);
			return;
		}

		setBaseEnabled(false);
	}

	private static boolean allElementsFromSameNetwork(final List<FBNetworkElement> fbelements) {
		final FBNetwork source = fbelements.get(0).getFbNetwork();
		return fbelements.stream().allMatch(el -> source.equals(el.getFbNetwork()));
	}

	private static GraphicalViewer getViewer(final FBNetwork subappNetwork, final IEditorPart parent) {
		if (!getFBNetwork(parent).equals(subappNetwork)) {
			// source subapp editor, subapp content is not open
			return HandlerHelper.getViewer(openEditor(subappNetwork.eContainer()));
		}
		return HandlerHelper.getViewer(parent);
	}

	private static Point getDestination(final IEditorPart editor, final List<FBNetworkElement> fbelements,
			final FBNetwork destinationNetwork) {
		final GraphicalViewer viewer = getViewer(destinationNetwork, editor);
		viewer.flush();

		EObject obj = fbelements.get(0).getFbNetwork().eContainer();
		while (obj.eContainer() != null && obj.eContainer() != destinationNetwork) {
			obj = obj.eContainer();
		}

		final GraphicalEditPart ep = (GraphicalEditPart) viewer.getEditPartForModel(obj);

		final Rectangle bounds;
		if (ep != null) {
			bounds = ep.getFigure().getBounds().getCopy();
		} else {
			bounds = new Rectangle();
		}
		final int destX = bounds.x;
		final int destY = bounds.y + bounds.height + 20;

		return new Point(destX, destY);
	}

}
