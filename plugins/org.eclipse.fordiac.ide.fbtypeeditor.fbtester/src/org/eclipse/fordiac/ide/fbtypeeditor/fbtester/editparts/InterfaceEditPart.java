/*******************************************************************************
 * Copyright (c) 2012, 2014, 2016 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *    - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.fbtester.editparts;

import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.InputWithAnchor;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.OutputWithAnchor;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;

public class InterfaceEditPart extends
		org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart {

	@Override
	protected GraphicalNodeEditPolicy getNodeEditPolicy() {
		return null;
	}

	@Override
	protected void createEditPolicies() {
		// should be readonly for tester
	}

	@Override
	public boolean understandsRequest(Request req) {
		// should be readonly for tester
		return false;
	}

	@Override
	public void performRequest(Request request) {
		// should be readonly for tester

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelSourceConnections
	 * ()
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected List getModelSourceConnections() {
		if (isEvent()) {
			return ((Event)getModel()).getWith();
		}
		return Collections.emptyList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelTargetConnections
	 * ()
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected List getModelTargetConnections() {
		if (isVariable()) {
			return ((VarDeclaration) getModel()).getWiths();
		}
		return Collections.emptyList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef.
	 * ConnectionEditPart)
	 */
	@Override
	public ConnectionAnchor getSourceConnectionAnchor(
			final ConnectionEditPart connection) {
		int pos = 1;
		if (isInput()) {
			pos = calculateInputWithPos(connection);
			return new InputWithAnchor(getFigure(), pos, this);
		} 
		pos = calculateOutputWithPos(connection);
		return new OutputWithAnchor(getFigure(), pos, this);		
	}

	private int calculateInputWithPos(final ConnectionEditPart connection) {
		int pos;
		With with = (With) connection.getModel();
		Event event = (Event) with.eContainer();
		InterfaceList interfaceList = (InterfaceList) event.eContainer();
		pos = interfaceList.getEventInputs().indexOf(event) + 1;
		return pos;
	}

	private int calculateOutputWithPos(final ConnectionEditPart connection) {
		int pos;
		With with = (With) connection.getModel();
		Event event = (Event) with.eContainer();

		InterfaceList interfaceList = (InterfaceList) event.eContainer();
		pos = interfaceList.getEventOutputs().indexOf(event) + 1;
		return pos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef.
	 * Request)
	 */
	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final Request request) {
		return new ChopboxAnchor(getFigure());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef.
	 * ConnectionEditPart)
	 */
	@Override
	public ConnectionAnchor getTargetConnectionAnchor(
			final ConnectionEditPart connection) {
		int pos = 1;
		if (isInput()) {
			pos = calculateInputWithPos(connection);
			return new InputWithAnchor(getFigure(), pos, this);
		} 
		pos = calculateOutputWithPos(connection);
		return new OutputWithAnchor(getFigure(), pos, this);		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef.
	 * Request)
	 */
	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final Request request) {
		return new ChopboxAnchor(getFigure());
	}
}
