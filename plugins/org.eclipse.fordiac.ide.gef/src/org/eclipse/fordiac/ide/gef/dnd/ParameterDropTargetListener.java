/*******************************************************************************
 * Copyright (c) 2012 Profactor GbmH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.dnd;

import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.dnd.AbstractTransferDropTargetListener;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.swt.dnd.DND;

public class ParameterDropTargetListener extends
		AbstractTransferDropTargetListener {

	private ParameterValueFactory factory = new ParameterValueFactory();

	public ParameterDropTargetListener(EditPartViewer viewer) {
		super(viewer, ParameterValueTemplateTransfer.getInstance());
	}

	@Override
	protected Request createTargetRequest() {
		CreateRequest request = new CreateRequest();
		request.setFactory(factory);
		if (getCurrentEvent().data != null) {
			factory.setText(getCurrentEvent().data.toString());
		}
		return request;
	}

	@Override
	protected void updateTargetRequest() {
		((CreateRequest) getTargetRequest()).setLocation(getDropLocation());
	}

	@Override
	protected void handleDragOver() {
		EditPart ep = getTargetEditPart();
		if (ep instanceof InterfaceEditPart) {
			if (((InterfaceEditPart) ep).isInput()
					&& !((InterfaceEditPart) ep).isEvent())
				getCurrentEvent().detail = DND.DROP_COPY;
		} else {
			getCurrentEvent().detail = DND.DROP_NONE;
		}
		super.handleDragOver();
	}

}