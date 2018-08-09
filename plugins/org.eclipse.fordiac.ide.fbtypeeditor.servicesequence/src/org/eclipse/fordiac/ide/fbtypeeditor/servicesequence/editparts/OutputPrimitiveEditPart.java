/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011 - 2015 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Label;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.figures.AdvancedFixedAnchor;
import org.eclipse.fordiac.ide.gef.FixedAnchor;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;

public class OutputPrimitiveEditPart extends PrimitiveEditPart{

	OutputPrimitiveEditPart(){
		connection = new PrimitiveConnection(false);
	}
	
	@Override
	public List<Object> getModelSourceConnections() {
		ArrayList<Object> temp = new ArrayList<>();
		temp.add(connection);
		return temp;
	}

	@Override
	public List<Object> getModelTargetConnections() {
		ArrayList<Object> temp = new ArrayList<>();
		InputPrimitive view = getCastedParent().getPossibleInputPrimitive(getCastedModel());
		if (view != null) {
			EditPart part = (EditPart) getViewer().getEditPartRegistry().get(view);
			if (part instanceof InputPrimitiveEditPart) {
				temp.add(((InputPrimitiveEditPart) part).getConnectingConnection());
			}
		}
		temp.add(connection);
		return temp;
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final Request request) {
		return null;
	}
	
	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final ConnectionEditPart connection) {
		if (connection instanceof PrimitiveConnectionEditPart) {
			return new FixedAnchor(getCenterFigure(), isLeftInterface());
		} else if (connection instanceof ConnectingConnectionEditPart) {
			return new AdvancedFixedAnchor(getCenterFigure(), isLeftInterface(), isLeftInterface() ? 3 : -4, 0);
		}
		return null;

	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final ConnectionEditPart connection) {
		if (connection instanceof PrimitiveConnectionEditPart) {
			return new FixedAnchor(getNameLabel(), ! isLeftInterface());
		} else if (connection instanceof ConnectingConnectionEditPart) {
			return new AdvancedFixedAnchor(getCenterFigure(), isLeftInterface(), isLeftInterface() ? 3 : -4, 0);
		}
		return null;

	}

	@Override
	public OutputPrimitive getCastedModel() {
		return (OutputPrimitive) getModel();
	}

	@Override
	public int getFeatureID() {
		return 0;
	}

	@Override
	public EObject getElement() {
		return getCastedModel();
	}

	@Override
	public Label getLabel() {
		return getNameLabel();
	}

	@Override
	public INamedElement getINamedElement() {
		return null;
	}
}
