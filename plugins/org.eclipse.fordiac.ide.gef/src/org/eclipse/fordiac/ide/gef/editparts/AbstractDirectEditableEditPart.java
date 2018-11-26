/*******************************************************************************
 * Copyright (c) 2008 - 2017  Profactor GbmH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.editparts;

import org.eclipse.draw2d.Label;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.gef.policies.INamedElementRenameEditPolicy;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.ui.controls.Abstract4DIACUIPlugin;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.viewers.TextCellEditor;

/**
 * The Class AbstractDirectEditableEditPart.
 */
public abstract class AbstractDirectEditableEditPart extends
		AbstractConnectableEditPart {

	/** The manager. */
	protected DirectEditManager manager;

	private final EContentAdapter adapter = new EContentAdapter() {

		@Override
		public void notifyChanged(Notification notification) {
			Object feature = notification.getFeature();
			if (LibraryElementPackage.eINSTANCE.getINamedElement_Name().equals(
					feature)) {
						refreshName();				
			}
			super.notifyChanged(notification);
		}

	};
	
	protected EContentAdapter getNameAdapter() {
		return adapter;
	}

	public void refreshName() {
		getNameLabel().setText(getINamedElement().getName());
	}

	/**
	 * Gets the i named element.
	 * 
	 * @return the i named element
	 */
	public abstract INamedElement getINamedElement();

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#activate()
	 */
	@Override
	public void activate() {
		super.activate();
		if (getINamedElement() != null) {
			getINamedElement().eAdapters().add(adapter);
		}

	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#deactivate()
	 */
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
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new INamedElementRenameEditPolicy());

	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#performRequest(org.eclipse.gef.Request)
	 */
	@Override
	public void performRequest(final Request request) {
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT) {
			performDirectEdit();
		} else {
			super.performRequest(request);
		}
	}

	/**
	 * Gets the manager.
	 * 
	 * @return the manager
	 */
	public DirectEditManager getManager() {
		if (manager == null) {
			Label l = getNameLabel();
			manager = new LabelDirectEditManager(this, TextCellEditor.class, new NameCellEditorLocator(l), l);
		}
		return manager;
	}

	/**
	 * Gets the name label.
	 * 
	 * @return the name label
	 */
	public abstract Label getNameLabel();

	/**
	 * performs the directEdit.
	 */
	public void performDirectEdit() {
		getManager().show();
	}
	
	//TODO already duplicated on several places put it into a util class
	public static void executeCommand(Command cmd){
		Object viewer = Abstract4DIACUIPlugin.getCurrentActiveEditor().getAdapter(GraphicalViewer.class);
		if (viewer instanceof GraphicalViewer) {
			((GraphicalViewer) viewer).getEditDomain().getCommandStack().execute(cmd);
		} else {
			cmd.execute();
		}
	}

}
