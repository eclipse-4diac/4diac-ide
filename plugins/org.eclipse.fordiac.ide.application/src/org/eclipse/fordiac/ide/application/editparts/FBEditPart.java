/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
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
 *   Alois Zoitl - separated FBNetworkElement from instance name for better
 *                 direct editing of instance names
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.actions.OpenCompositeInstanceViewerAction;
import org.eclipse.fordiac.ide.application.figures.FBNetworkElementFigure;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;

/**
 * This class implements an EditPart for a FunctionBlock.
 */
public class FBEditPart extends AbstractFBNElementEditPart {

	public FBEditPart() {
		super();
	}

	/**
	 * Creates the figure (for the specified model) to be used as this parts
	 * visuals.
	 *
	 * @return IFigure The figure for the model
	 */
	@Override
	protected IFigure createFigureForModel() {
		// extend this if FunctionBlock gets extended!
		FBNetworkElementFigure f = null;
		if (getModel() != null) {
			f = new FBNetworkElementFigure(getModel(), this);
		} else {
			throw new IllegalArgumentException(Messages.FBEditPart_ERROR_UnsupportedFBType);
		}
		return f;
	}

	@Override
	public FB getModel() {
		return (FB) super.getModel();
	}

	@Override
	public void performRequest(Request request) {
		if (request.getType().equals(RequestConstants.REQ_OPEN) && getModel() != null
				&& getModel().getType() instanceof CompositeFBType) {
			new OpenCompositeInstanceViewerAction(this, getModel()).run();
		} else {
			super.performRequest(request);
		}
	}

}
