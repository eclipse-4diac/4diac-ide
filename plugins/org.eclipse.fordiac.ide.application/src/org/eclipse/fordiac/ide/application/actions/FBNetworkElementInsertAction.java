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
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.actions;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.application.editors.FBNetworkContextMenuProvider;
import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.fordiac.ide.model.commands.create.AbstractCreateFBNetworkElementCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateSubAppInstanceCommand;
import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.SubAppTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.gef.GraphicalViewer;
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

	private AbstractCreateFBNetworkElementCommand createFBNetworkElementCreateCommand() {
		final Point pt = getPositionInViewer((IEditorPart) getWorkbenchPart());
		if (typeEntry instanceof FBTypeEntry) {
			return new FBCreateCommand((FBTypeEntry) typeEntry, fbNetwork, pt.x, pt.y);
		} else if (typeEntry instanceof SubApplicationTypePaletteEntry) {
			return new CreateSubAppInstanceCommand((SubAppTypeEntry) typeEntry, fbNetwork, pt.x, pt.y);
		}

		return null;
	}

	private static Point getPositionInViewer(final IEditorPart editor) {
		final GraphicalViewer viewer = editor.getAdapter(GraphicalViewer.class);
		return ((FBNetworkContextMenuProvider) viewer.getContextMenu()).getTranslatedAndZoomedPoint();
	}
}
