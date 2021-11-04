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

public class InterfaceEditPart extends org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart {

	@Override
	protected GraphicalNodeEditPolicy getNodeEditPolicy() {
		return null;
	}

	@Override
	protected void createEditPolicies() {
		// should be readonly for tester
	}

	@Override
	public boolean understandsRequest(final Request req) {
		// should be readonly for tester
		return false;
	}

	@Override
	public void performRequest(final Request request) {
		// should be readonly for tester

	}

	@Override
	protected List<With> getModelSourceConnections() {
		if (isEvent()) {
			return ((Event) getModel()).getWith();
		}
		return Collections.emptyList();
	}

	@Override
	protected List<With> getModelTargetConnections() {
		if (isVariable()) {
			return ((VarDeclaration) getModel()).getWiths();
		}
		return Collections.emptyList();
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final ConnectionEditPart connection) {
		return getWithAnchors(connection);
	}

	private ConnectionAnchor getWithAnchors(final ConnectionEditPart connection) {
		if (isInput()) {
			return new InputWithAnchor(getFigure(), calculateInputWithPos(connection), this);
		}
		return new OutputWithAnchor(getFigure(), calculateOutputWithPos(connection), this);
	}

	private static int calculateInputWithPos(final ConnectionEditPart connection) {
		final With with = (With) connection.getModel();
		final Event event = (Event) with.eContainer();
		final InterfaceList interfaceList = (InterfaceList) event.eContainer();
		return interfaceList.getEventInputs().indexOf(event) + 1;
	}

	private static int calculateOutputWithPos(final ConnectionEditPart connection) {
		final With with = (With) connection.getModel();
		final Event event = (Event) with.eContainer();

		final InterfaceList interfaceList = (InterfaceList) event.eContainer();
		return interfaceList.getEventOutputs().indexOf(event) + 1;
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final Request request) {
		return new ChopboxAnchor(getFigure());
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final ConnectionEditPart connection) {
		return getWithAnchors(connection);
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final Request request) {
		return new ChopboxAnchor(getFigure());
	}
}
