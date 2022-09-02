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
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.editors.FBNetworkContextMenuProvider;
import org.eclipse.fordiac.ide.application.editparts.AbstractContainerContentEditPart;
import org.eclipse.fordiac.ide.application.editparts.GroupEditPart;
import org.eclipse.fordiac.ide.application.editparts.IContainerEditPart;
import org.eclipse.fordiac.ide.model.commands.change.AddElementsToGroup;
import org.eclipse.fordiac.ide.model.commands.create.AbstractCreateFBNetworkElementCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

abstract class AbstractContainerElementHandler extends AbstractHandler {

	Group group;

	@Override
	public Object execute(final ExecutionEvent event) throws org.eclipse.core.commands.ExecutionException {
		group = null;  // reset the group to be save from previous executions
		final IEditorPart activeEditor = HandlerUtil.getActiveEditor(event);
		final StructuredSelection selection = (StructuredSelection) HandlerUtil.getCurrentSelection(event);
		final GraphicalViewer viewer = getViewer(activeEditor);

		final CommandStack cmdstack = activeEditor.getAdapter(CommandStack.class);
		final FBNetwork network = getFBNetwork(selection, event);
		if (network != null) {
			final Rectangle posSizeRef = getPosSizeRef(event, viewer, selection, network);
			Command cmd = createContainerCreationCommand(selection.toList(), network, posSizeRef);
			final FBNetworkElement newElement = ((AbstractCreateFBNetworkElementCommand) cmd).getElement();
			if (group != null) {
				cmd = cmd.chain(new AddElementsToGroup(group, List.of(newElement), new Point())); // point with 0,0 to
				// keep
				// the position
			}
			cmdstack.execute(cmd);
			selectElement(newElement, viewer);
		}

		return Status.OK_STATUS;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final ISelection sel = (ISelection) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME);

		boolean enabeled = false;
		if (sel instanceof StructuredSelection) {
			final StructuredSelection selection = (StructuredSelection) sel;
			if (createNewEmptyContainerElement(selection)) {
				enabeled = true;
			} else {
				// only enable this handler if all FBNetworkElements are within the same parent network
				enabeled = (selection.toList().stream().map(AbstractContainerElementHandler::getModelElement)
						.filter(FBNetworkElement.class::isInstance)
						.map(fbel -> ((FBNetworkElement) fbel).getFbNetwork()).distinct().count() == 1);
			}
		}
		setBaseEnabled(enabeled);
	}

	protected abstract AbstractCreateFBNetworkElementCommand createContainerCreationCommand(
			final List<?> selection, final FBNetwork network, final Rectangle posSizeRef);

	private FBNetwork getFBNetwork(final StructuredSelection selection, final ExecutionEvent event) {
		if (createNewEmptyContainerElement(selection)) {
			return getFBNetworkFromContainer(selection.getFirstElement(), event);
		}
		for (final Object o : selection) {
			if (o instanceof EditPart) {
				final Object model = ((EditPart) o).getModel();
				if (model instanceof FBNetworkElement) {
					// we check already in the setEnabled() that we are in the same group and same network so we can
					// stop at the first element
					group = ((FBNetworkElement) model).getGroup();
					return ((FBNetworkElement) model).getFbNetwork();
				}
			}
		}
		return null;
	}

	protected FBNetwork getFBNetworkFromContainer(final Object selection,
			final ExecutionEvent event) {
		if (selection instanceof AbstractContainerContentEditPart) {
			final FBNetworkElement containerElement = ((AbstractContainerContentEditPart) selection)
					.getContainerElement();
			if (containerElement instanceof Group) {
				group = (Group) containerElement;
				return containerElement.getFbNetwork();
			}
			if (containerElement instanceof SubApp) {
				return ((SubApp) containerElement).getSubAppNetwork();
			}
		}
		return HandlerUtil.getActiveEditor(event).getAdapter(FBNetwork.class);
	}

	public Rectangle getPosSizeRef(final ExecutionEvent event, final EditPartViewer viewer,
			final StructuredSelection selection, final FBNetwork network) {
		if (createNewEmptyContainerElement(selection)) {
			// new empty subapp at mouse cursor location
			return getEmptyContainerPosSizeRef(event, viewer, network);
		}
		Rectangle selectionExtend = null;
		for (final Object selElem : selection.toList()) {
			if (selElem instanceof GraphicalEditPart
					&& ((GraphicalEditPart) selElem).getModel() instanceof FBNetworkElement) {
				// only consider the selected FBNetworkElements
				final Rectangle fbBounds = ((GraphicalEditPart) selElem).getFigure().getBounds();
				final Position position = ((FBNetworkElement) ((GraphicalEditPart) selElem).getModel()).getPosition();
				fbBounds.x = position.getX();
				fbBounds.y = position.getY();
				if (selectionExtend == null) {
					selectionExtend = fbBounds.getCopy();
				} else {
					selectionExtend.union(fbBounds);
				}
			}
		}
		return selectionExtend;
	}

	protected Rectangle getEmptyContainerPosSizeRef(final ExecutionEvent event, final EditPartViewer viewer,
			final FBNetwork network) {
		Rectangle selectionExtend;
		final org.eclipse.swt.graphics.Point point = ((FBNetworkContextMenuProvider) viewer.getContextMenu())
				.getPoint();
		selectionExtend = new Rectangle(point.x, point.y, 200, 100);
		final IFigure targetFigure = getTargetFigure(event, viewer, network);
		targetFigure.translateToRelative(selectionExtend);
		final Rectangle bounds = targetFigure.getBounds();
		selectionExtend.translate(-bounds.x, -bounds.y);
		return selectionExtend;
	}

	protected IFigure getTargetFigure(final ExecutionEvent event, final EditPartViewer viewer,
			final FBNetwork network) {
		GraphicalEditPart ep = null;
		if (group != null) {
			ep = ((GroupEditPart) viewer.getEditPartRegistry().get(group)).getContentEP();
		} else if (HandlerUtil.getActiveEditor(event).getAdapter(FBNetwork.class) == network) {
			// we are creating in the root network of the editor
			ep = (GraphicalEditPart) viewer.getEditPartRegistry().get(network);
		} else {
			// we are inside of expanded subapp
			ep = ((IContainerEditPart) viewer.getEditPartRegistry().get(network.eContainer())).getContentEP();
		}

		return ep.getFigure();
	}

	private static boolean createNewEmptyContainerElement(final StructuredSelection selection) {
		return (selection.size() == 1)
				&& !(((EditPart) selection.getFirstElement()).getModel() instanceof FBNetworkElement);
	}

	protected static Object getModelElement(final Object ep) {
		if (ep instanceof EditPart) {
			return ((EditPart) ep).getModel();
		}
		return ep;
	}

}
