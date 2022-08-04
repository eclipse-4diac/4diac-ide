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

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.application.commands.ResizeGroupCommand;
import org.eclipse.fordiac.ide.application.editors.FBNetworkContextMenuProvider;
import org.eclipse.fordiac.ide.application.editparts.AbstractContainerContentEditPart;
import org.eclipse.fordiac.ide.application.editparts.GroupContentEditPart;
import org.eclipse.fordiac.ide.application.editparts.UnfoldedSubappContentEditPart;
import org.eclipse.fordiac.ide.model.commands.create.AbstractCreateFBNetworkElementCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateFBElementInGroupCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
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
		final Point pt = getPositionInViewer((IEditorPart) getWorkbenchPart());
		final AbstractContainerContentEditPart abstractContainerContentEditPart = findAbstractContainerContentEditPartAtPosition((IEditorPart) getWorkbenchPart(),
				pt);

		if (abstractContainerContentEditPart instanceof GroupContentEditPart) {
			return new ResizeGroupCommand(new CreateFBElementInGroupCommand(typeEntry,
					((GroupContentEditPart) abstractContainerContentEditPart).getModel().getGroup(),
					pt.x - abstractContainerContentEditPart.getFigure().getBounds().x,
					pt.y - abstractContainerContentEditPart.getFigure().getBounds().y),
					abstractContainerContentEditPart.getViewer(),
					abstractContainerContentEditPart);
		} else if (abstractContainerContentEditPart instanceof UnfoldedSubappContentEditPart) {

			return AbstractCreateFBNetworkElementCommand.createCreateCommand(typeEntry,
					abstractContainerContentEditPart.getModel(),
					pt.x - abstractContainerContentEditPart.getFigure().getBounds().x,
					pt.y - abstractContainerContentEditPart.getFigure().getBounds().y);
		}
		return AbstractCreateFBNetworkElementCommand.createCreateCommand(typeEntry, fbNetwork, pt.x, pt.y);
	}

	private static Point getPositionInViewer(final IEditorPart editor) {
		final GraphicalViewer viewer = editor.getAdapter(GraphicalViewer.class);
		return ((FBNetworkContextMenuProvider) viewer.getContextMenu()).getTranslatedAndZoomedPoint();
	}

	private AbstractContainerContentEditPart findAbstractContainerContentEditPartAtPosition(final IEditorPart editor,
			final Point pos) {
		final GraphicalViewer graphicalViewer = editor.getAdapter(GraphicalViewer.class);
		final Object object = graphicalViewer.getEditPartRegistry().get(fbNetwork);
		if (object instanceof GraphicalEditPart) {
			final IFigure figure = ((GraphicalEditPart) object).getFigure().findFigureAt(pos.x, pos.y);
			if (figure != null) {
				final Object targetObject = graphicalViewer.getVisualPartMap().get(figure);
				if (targetObject instanceof AbstractContainerContentEditPart) {
					return ((AbstractContainerContentEditPart) targetObject);
				}
			}
		}
		return null;

	}
}
