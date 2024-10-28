/*******************************************************************************
 * Copyright (c) 2008, 2020 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *                          Johannes Kepler University Linz
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
 *               - merged ApplicationEditorTemplatetransferlistern into this class
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.utilities;

import org.eclipse.core.resources.IProject;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.SubAppTypeEntry;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.dnd.TemplateTransfer;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.swt.dnd.DND;

public class FbTypeTemplateTransferDropTargetListener extends TemplateTransferDropTargetListener {

	private final IProject targetProject;

	/**
	 * Constructs a listener on the specified viewer.
	 *
	 * @param viewer the EditPartViewer
	 */
	public FbTypeTemplateTransferDropTargetListener(final EditPartViewer viewer, final IProject targetProject) {
		super(viewer);
		this.targetProject = targetProject;
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
		final Object template = TemplateTransfer.getInstance().getTemplate();
		if (template == null) {
			getCurrentEvent().detail = DND.DROP_NONE;
			getCurrentEvent().operations = DND.DROP_NONE;

		} else if (template instanceof FBTypeEntry) {
			final FBTypeEntry entry = (FBTypeEntry) TemplateTransfer.getInstance().getTemplate();
			final IProject srcProject = entry.getFile().getProject();

			// Only allow drag from the same project
			if ((null != targetProject) && (targetProject.equals(srcProject))) {
				getCurrentEvent().detail = DND.DROP_COPY;
			} else {
				getCurrentEvent().detail = DND.DROP_NONE;
				getCurrentEvent().operations = DND.DROP_NONE;
			}
		} else if (TemplateTransfer.getInstance().getTemplate() instanceof final DataTypeEntry dataTypeEntry
				&& dataTypeEntry.getType() instanceof StructuredType && null != getTargetEditPart()) {
			final Object model = getTargetEditPart().getModel();
			if (model instanceof StructManipulator) {
				getCurrentEvent().detail = DND.DROP_COPY;
			} else {
				getCurrentEvent().detail = DND.DROP_NONE;
				getCurrentEvent().operations = DND.DROP_NONE;
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
		if (!(getCurrentEvent().data instanceof FBTypeEntry) && !(getCurrentEvent().data instanceof SubAppTypeEntry)
				&& !(getCurrentEvent().data instanceof final DataTypeEntry dataTypeEntry
						&& dataTypeEntry.getType() instanceof StructuredType)) {
			// only allow FB type & struct data type drops
			return;
		}
		super.handleDrop();
		TemplateTransfer.getInstance().setTemplate(null);
	}

	@Override
	protected CreationFactory getFactory(final Object template) {
		getCurrentEvent().detail = DND.DROP_COPY;

		if (template instanceof FBType || template instanceof FBTypeEntry || template instanceof SubAppTypeEntry
				|| (template instanceof final DataTypeEntry dataTypeEntry
						&& dataTypeEntry.getType() instanceof StructuredType)) {
			return new FBTypeTemplateCreationFactory(template);
		}
		return null;
	}

}
