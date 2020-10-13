/*******************************************************************************
 * Copyright (c) 2008 - 2015 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Michael Hofmann, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - fixed snapp to grid placement of new FBs based on a commit on
 *   			   the Eclipse Siriuse project by Laurent Redor:
 *   			   https://git.eclipse.org/c/sirius/org.eclipse.sirius.git/commit/?id=278bcefbf04a5e93636b16b45ccce27e455cc3be
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.utilities;

import org.eclipse.core.resources.IProject;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.util.dnd.TransferDataSelectionOfFb;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.dnd.TemplateTransfer;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.swt.dnd.DND;

public abstract class FbTypeTemplateTransferDropTargetListener extends TemplateTransferDropTargetListener {

	private final IProject targetProject;

	/**
	 * Constructs a listener on the specified viewer.
	 *
	 * @param viewer the EditPartViewer
	 */
	public FbTypeTemplateTransferDropTargetListener(final EditPartViewer viewer, AutomationSystem system) {
		super(viewer);
		targetProject = (null != system) ? system.getSystemFile().getProject() : null;
	}

	/**
	 * The purpose of a template is to be copied. Therefore, the Drop operation is
	 * set to <code>DND.DROP_COPY</code> by default.
	 *
	 * @see org.eclipse.gef.dnd.AbstractTransferDropTargetListener#handleDragOver()
	 */
	@Override
	protected void handleDragOver() {
		super.handleDragOver();
		getCurrentEvent().feedback = DND.FEEDBACK_SCROLL | DND.FEEDBACK_EXPAND;
		if (TemplateTransfer.getInstance().getTemplate() == null) {
			getCurrentEvent().detail = DND.DROP_NONE;
			getCurrentEvent().operations = DND.DROP_NONE;

		} else {
			if (TemplateTransfer.getInstance().getTemplate() instanceof FBTypePaletteEntry) {
				FBTypePaletteEntry entry = (FBTypePaletteEntry) TemplateTransfer.getInstance().getTemplate();
				IProject srcProject = entry.getFile().getProject();

				// Only allow drag from the same project
				if ((null != targetProject) && (targetProject.equals(srcProject))) {
					getCurrentEvent().detail = DND.DROP_COPY;
				} else {
					getCurrentEvent().detail = DND.DROP_NONE;
					getCurrentEvent().operations = DND.DROP_NONE;
				}
			}
		}
	}

	/**
	 * Overridden to select the created object.
	 *
	 * @see org.eclipse.gef.dnd.AbstractTransferDropTargetListener#handleDrop()
	 */
	@Override
	protected void handleDrop() {

		if (!(getCurrentEvent().data instanceof FBTypePaletteEntry)
				&& !(getCurrentEvent().data instanceof SubApplicationTypePaletteEntry)
				&& !(getCurrentEvent().data instanceof TransferDataSelectionOfFb[])) {
			// only allow FB type drops and of TransferDataSelectionOfFb -->
			// filter e.g. Folder Drops from Type Navigator
			return;
		}
		super.handleDrop();
		TemplateTransfer.getInstance().setTemplate(null);
	}

	/*
	 * The code in this method is based on a commit to the Eclipse Siriuse project
	 * by Laurent Redor
	 * https://git.eclipse.org/c/sirius/org.eclipse.sirius.git/commit/?id=
	 * 278bcefbf04a5e93636b16b45ccce27e455cc3be
	 */
	@Override
	protected void updateTargetRequest() {
		super.updateTargetRequest();
		if (null != getTargetEditPart()) {
			SnapToHelper helper = getTargetEditPart().getAdapter(SnapToHelper.class);
			if (null != helper) {
				PrecisionPoint preciseLocation = new PrecisionPoint(getDropLocation());
				PrecisionPoint result = new PrecisionPoint(getDropLocation());
				CreateRequest req = getCreateRequest();
				helper.snapPoint(req, PositionConstants.HORIZONTAL | PositionConstants.VERTICAL, preciseLocation,
						result);
				req.setLocation(result.getCopy());
			}
		}
	}

}
