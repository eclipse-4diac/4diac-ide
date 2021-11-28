/*******************************************************************************
 * Copyright (c) 2017, 2021 fortiss GmbH, Johannes Kepler University Linz,
 * 							Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger - initial API and implementation and/or initial
 *                                documentation
 *   Bianca Wiesmayr - fix positioning of subapp, fix unfolded subapp
 *   Bianca Wiesmayr, Alois Zoitl - make newsubapp available for breadcrumb editor
 *   Alois Zoitl - extracted common elements into base class for reuseing it for
 *                 the group creation handler
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import static org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper.getViewer;
import static org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper.selectElement;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.Status;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.application.editors.FBNetworkContextMenuProvider;
import org.eclipse.fordiac.ide.model.commands.create.AbstractCreateFBNetworkElementCommand;
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

abstract class AbstractContainerElementHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws org.eclipse.core.commands.ExecutionException {
		final IEditorPart activeEditor = HandlerUtil.getActiveEditor(event);
		final StructuredSelection selection = (StructuredSelection) HandlerUtil.getCurrentSelection(event);
		final GraphicalViewer viewer = getViewer(activeEditor);

		final CommandStack cmdstack = activeEditor.getAdapter(CommandStack.class);
		final FBNetwork network = getFBNetwork(selection, event);
		final Point pos = getInsertPos(viewer, selection);
		final AbstractCreateFBNetworkElementCommand cmd = createContainerCreationCommand(selection.toList(), network,
				pos);
		cmdstack.execute(cmd);
		selectElement(cmd.getElement(), viewer);

		return Status.OK_STATUS;
	}

	protected abstract AbstractCreateFBNetworkElementCommand createContainerCreationCommand(
			final List<?> selection, final FBNetwork network, final Point pos);

	private static FBNetwork getFBNetwork(final StructuredSelection selection, final ExecutionEvent event) {
		if (createNewEmptyContainerElement(selection)) {
			return HandlerUtil.getActiveEditor(event).getAdapter(FBNetwork.class);
		}
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

	public static Point getInsertPos(final EditPartViewer viewer, final StructuredSelection selection) {
		if (createNewEmptyContainerElement(selection)) {
			// new empty subapp at mouse cursor location
			return ((FBNetworkContextMenuProvider) viewer.getContextMenu()).getTranslatedAndZoomedPoint();
		}
		final org.eclipse.swt.graphics.Point swtPos1 = FBNetworkHelper.getTopLeftCornerOfFBNetwork(selection.toList());
		return new Point(swtPos1.x, swtPos1.y);
	}

	private static boolean createNewEmptyContainerElement(final StructuredSelection selection) {
		return (selection.size() == 1)
				&& !(((EditPart) selection.getFirstElement()).getModel() instanceof FBNetworkElement);
	}

}
