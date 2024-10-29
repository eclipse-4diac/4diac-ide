/*******************************************************************************
 * Copyright (c) 2013, 2017 fortiss GmbH
 * 				 2019 Johannes Kepler University
 *               2021 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Extracted this class from the FBInsertAction
 *   Bianca Wiesmayr - correctly calculate position for inserting
 *   Alois Zoitl - reworked action to always create a new creation command
 *   Bianca Wiesmayr - updated for breadcrumb editor
 *   Fabio Gandolfi - added inserting fbs to group & expanded subapps
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.actions;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.fordiac.ide.application.commands.ResizeGroupOrSubappCommand;
import org.eclipse.fordiac.ide.application.editors.FBNetworkContextMenuProvider;
import org.eclipse.fordiac.ide.application.editparts.AbstractContainerContentEditPart;
import org.eclipse.fordiac.ide.application.editparts.GroupContentEditPart;
import org.eclipse.fordiac.ide.application.editparts.UnfoldedSubappContentEditPart;
import org.eclipse.fordiac.ide.application.utilities.GetEditPartFromGraficalViewerHelper;
import org.eclipse.fordiac.ide.model.commands.create.AbstractCreateFBNetworkElementCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateFBElementInGroupCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;

public class FBNetworkElementInsertAction extends WorkbenchPartAction {

	private final TypeEntry typeEntry;
	private final FBNetwork fbNetwork;

	public FBNetworkElementInsertAction(final IWorkbenchPart part, final TypeEntry entry, final FBNetwork fbNetwork) {
		super(part);
		this.typeEntry = entry;
		this.fbNetwork = fbNetwork;

		setId(typeEntry.getFile().getFullPath().toString());
		setText(typeEntry.getTypeName());
	}

	@Override
	protected boolean calculateEnabled() {
		return (null != typeEntry) && (null != fbNetwork);
	}

	@Override
	public void run() {
		execute(createFBNetworkElementCreateCommand());
	}

	private Command createFBNetworkElementCreateCommand() {
		Point pt = getPositionInViewer((IEditorPart) getWorkbenchPart());
		final AbstractContainerContentEditPart containerEP = GetEditPartFromGraficalViewerHelper
				.findAbstractContainerContentEditPartAtPosition((IEditorPart) getWorkbenchPart(), pt, fbNetwork);

		if (containerEP instanceof final GroupContentEditPart groupContentEP) {
			pt = applySnapToGrid(pt, groupContentEP);
			final Point topLeft = containerEP.getFigure().getClientArea().getTopLeft();
			pt.translate(-topLeft.x, -topLeft.y);
			return new ResizeGroupOrSubappCommand(containerEP,
					new CreateFBElementInGroupCommand(typeEntry, groupContentEP.getModel().getGroup(), pt.x, pt.y));
		}
		if (containerEP instanceof final UnfoldedSubappContentEditPart subappContentEP) {
			pt = applySnapToGrid(pt, subappContentEP);
			final Point topLeft = containerEP.getFigure().getClientArea().getTopLeft();
			pt.translate(-topLeft.x, -topLeft.y);
			return new ResizeGroupOrSubappCommand(containerEP,
					AbstractCreateFBNetworkElementCommand.createCreateCommand(typeEntry, subappContentEP.getModel(),
							pt.x - containerEP.getFigure().getBounds().x,
							pt.y - containerEP.getFigure().getBounds().y));
		}
		final GraphicalViewer graphicalViewer = getWorkbenchPart().getAdapter(GraphicalViewer.class);
		if (graphicalViewer != null && graphicalViewer
				.getEditPartForModel(fbNetwork) instanceof final GraphicalEditPart graphicalEditPart) {
			pt = applySnapToGrid(pt, graphicalEditPart);
		}
		return AbstractCreateFBNetworkElementCommand.createCreateCommand(typeEntry, fbNetwork, pt.x, pt.y);
	}

	private static Point getPositionInViewer(final IEditorPart editor) {
		final GraphicalViewer viewer = editor.getAdapter(GraphicalViewer.class);
		return ((FBNetworkContextMenuProvider) viewer.getContextMenu()).getTranslatedAndZoomedPoint();
	}

	private static Point applySnapToGrid(final Point refPoint, final GraphicalEditPart gep) {
		final SnapToHelper helper = gep.getAdapter(SnapToHelper.class);
		if (helper != null) {
			gep.getFigure().translateToAbsolute(refPoint);
			final PrecisionPoint preciseLocation = new PrecisionPoint(refPoint);
			final PrecisionPoint result = new PrecisionPoint(refPoint);
			helper.snapPoint(null, PositionConstants.HORIZONTAL | PositionConstants.VERTICAL, preciseLocation, result);
			gep.getFigure().translateToRelative(result);
			return result;
		}
		return refPoint;
	}
}
