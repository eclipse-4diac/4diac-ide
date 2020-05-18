/*******************************************************************************
 * Copyright (c) 2012 Profactor GbmH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.dnd;

import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.dnd.AbstractTransferDropTargetListener;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.swt.dnd.DND;

public class ParameterDropTargetListener extends AbstractTransferDropTargetListener {

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
		if (null != getTargetEditPart()) {
			Object model = getTargetEditPart().getModel();
			if (model instanceof IInterfaceElement) {
				if (((IInterfaceElement) model).isIsInput() && !(model instanceof Event)) {
					getCurrentEvent().detail = DND.DROP_COPY;
				}
			} else {
				getCurrentEvent().detail = DND.DROP_NONE;
			}
		}
		super.handleDragOver();
	}

}