/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 *               2019 Johannes Kepler University Linz
 *               2020 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *    - initial API and implementation and/or initial documentation
 *  Bianca Wiesmayr - fix positioning of subapp, fix unfolded subapp
 *  Bianca Wiesmayr, Alois Zoitl - make newsubapp available for breadcrumb editor
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.Status;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.application.commands.NewSubAppCommand;
import org.eclipse.fordiac.ide.application.editors.FBNetworkContextMenuProvider;
import org.eclipse.fordiac.ide.gef.AdvancedScrollingGraphicalViewer;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class NewSubApplication extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws org.eclipse.core.commands.ExecutionException {
		final IEditorPart activeEditor = HandlerUtil.getActiveEditor(event);
		final StructuredSelection selection = (StructuredSelection) HandlerUtil.getCurrentSelection(event);
		final GraphicalViewer viewer = activeEditor.getAdapter(GraphicalViewer.class);

		final CommandStack cmdstack = activeEditor.getAdapter(CommandStack.class);
		final FBNetwork network = getFBNetwork(selection, event);
		final Point pos = getInsertPos(viewer, selection);
		final NewSubAppCommand cmd = new NewSubAppCommand(network, selection.toList(), pos.x, pos.y);
		cmdstack.execute(cmd);
		selectElement(cmd.getElement(), viewer);

		return Status.OK_STATUS;
	}

	private static FBNetwork getFBNetwork(StructuredSelection selection, ExecutionEvent event) {
		if (createNewEmptySubapp(selection)) {
			return HandlerUtil.getActiveEditor(event).getAdapter(FBNetwork.class);
		} else {
			for (final Object o : selection) {
				if (o instanceof EditPart) {
					final Object model = ((EditPart) o).getModel();
					if (model instanceof FBNetworkElement) {
						return ((FBNetworkElement) model).getFbNetwork();
					}
				}
			}
			return null;
		}
	}

	public static Point getInsertPos(final EditPartViewer viewer, final StructuredSelection selection) {
		if (createNewEmptySubapp(selection)) {
			// new empty subapp at mouse cursor location
			return ((FBNetworkContextMenuProvider) viewer.getContextMenu()).getTranslatedAndZoomedPoint();
		} else {
			final org.eclipse.swt.graphics.Point swtPos1 = FBNetworkHelper
					.getTopLeftCornerOfFBNetwork(selection.toList());
			return new Point(swtPos1.x, swtPos1.y);
		}
	}

	private static boolean createNewEmptySubapp(StructuredSelection selection) {
		return (selection.size() == 1)
				&& !(((EditPart) selection.getFirstElement()).getModel() instanceof FBNetworkElement);
	}

	// TODO breadcrumb: duplicated code move to common helper
	private static void selectElement(final Object element, final GraphicalViewer viewer) {
		final EditPart editPart = (EditPart) viewer.getEditPartRegistry().get(element);
		if (null != editPart) {
			if (viewer instanceof AdvancedScrollingGraphicalViewer) {
				((AdvancedScrollingGraphicalViewer) viewer).selectAndRevealEditPart(editPart);
			} else {
				viewer.select(editPart);
				viewer.reveal(editPart);
			}
		}
	}
}
