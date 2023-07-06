/*******************************************************************************
 * Copyright (c) 2008, 2023 Profactor GbmH, TU Wien ACIN, fortiss GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Martin Jobst
 *     - add direct editable check
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.editparts;

import org.eclipse.draw2d.Label;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.gef.policies.INamedElementRenameEditPolicy;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.ui.IEditorPart;

/** The Class AbstractDirectEditableEditPart. */
public abstract class AbstractDirectEditableEditPart extends AbstractConnectableEditPart {

	private final Adapter adapter = new AdapterImpl() {

		@Override
		public void notifyChanged(final Notification notification) {
			final Object feature = notification.getFeature();
			if (LibraryElementPackage.eINSTANCE.getINamedElement_Name().equals(feature)) {
				refreshName();
			}
			super.notifyChanged(notification);
		}

	};

	protected Adapter getNameAdapter() {
		return adapter;
	}

	public void refreshName() {
		getNameLabel().setText(getINamedElement().getName());
	}

	/** Gets the i named element.
	 *
	 * @return the i named element */
	public abstract INamedElement getINamedElement();

	/* (non-Javadoc)
	 *
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#activate() */
	@Override
	public void activate() {
		super.activate();
		if (getINamedElement() != null) {
			getINamedElement().eAdapters().add(adapter);
		}

	}

	/* (non-Javadoc)
	 *
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#deactivate() */
	@Override
	public void deactivate() {
		super.deactivate();
		if (getINamedElement() != null) {
			getINamedElement().eAdapters().remove(adapter);
		}
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		// EditPolicy which allows the direct edit of the Instance Name
		if (isDirectEditable()) {
			installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new INamedElementRenameEditPolicy());
		}

	}

	/* (non-Javadoc)
	 *
	 * @see org.eclipse.gef.editparts.AbstractEditPart#performRequest(org.eclipse.gef. Request) */
	@Override
	public void performRequest(final Request request) {
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT && isDirectEditable()) {
			performDirectEdit();
		} else {
			super.performRequest(request);
		}
	}

	protected DirectEditManager createDirectEditManager() {
		return new LabelDirectEditManager(this, getNameLabel());
	}

	/** Gets the name label.
	 *
	 * @return the name label */
	public abstract Label getNameLabel();

	/** performs the directEdit. */
	public void performDirectEdit() {
		createDirectEditManager().show();
	}

	// TODO already duplicated on several places put it into a util class
	public static void executeCommand(final Command cmd) {
		final IEditorPart currentActiveEditor = EditorUtils.getCurrentActiveEditor();
		if (currentActiveEditor != null) {
			final GraphicalViewer viewer = currentActiveEditor.getAdapter(GraphicalViewer.class);
			if (viewer != null) {
				viewer.getEditDomain().getCommandStack().execute(cmd);
				return;
			}
		}
		cmd.execute();
	}

	@SuppressWarnings("static-method") // can be overridden by subclasses
	public boolean isDirectEditable() {
		return true;
	}
}
