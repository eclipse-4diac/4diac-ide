/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011 - 2015 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *               2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr, Melanie Winter - cleanup, implemented anchor refresh
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.figures.AdvancedFixedAnchor;
import org.eclipse.fordiac.ide.gef.FixedAnchor;
import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.gef.Request;

public class InputPrimitiveEditPart extends AbstractPrimitiveEditPart {

	private final ConnectingConnection connectingConnection;


	InputPrimitiveEditPart() {
		super(new PrimitiveConnection(true));
		connectingConnection = new ConnectingConnection();
	}

	@Override
	public InputPrimitive getModel() {
		return (InputPrimitive) super.getModel();
	}

	public ConnectingConnection getConnectingConnection() {
		return connectingConnection;
	}


	@Override
	public void refresh() {
		super.refresh();
		if (getModel() != null) {
			if (srcAnchor != null) {
				srcAnchor.setIsInput(!isLeftInterface());
			}
			if (srcNeighbourAnchor != null) {
				srcNeighbourAnchor.setIsInput(isLeftInterface());
			}
			if (dstAnchor != null) {
				dstAnchor.setIsInput(isLeftInterface());
			}
		}
	}

	@Override
	public List<Object> getModelSourceConnections() {
		final List<Object> conns = new ArrayList<>();
		if (!getModel().getServiceTransaction().getOutputPrimitive().isEmpty()) {
			conns.add(connectingConnection);
		}
		conns.add(getPrimitiveConnection());
		return conns;
	}

	@Override
	public List<Object> getModelTargetConnections() {
		return Arrays.asList(getPrimitiveConnection());
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final Request request) {
		return null;
	}

	@Override
	protected FixedAnchor getPrimitiveConnSourceAnchor() {
		return new FixedAnchor(getNameLabel(), !isLeftInterface());
	}

	@Override
	protected AdvancedFixedAnchor getConnectingConnSourceAnchor() {
		return new AdvancedFixedAnchor(getCenterFigure(), isLeftInterface(), isLeftInterface() ? 3 : -4, 0);
	}

	@Override
	protected FixedAnchor getPrimitiveConnTargetAnchor() {
		return new FixedAnchor(getCenterFigure(), isLeftInterface());
	}

	@Override
	protected AdvancedFixedAnchor getConnectingConnTargetAnchor() {
		// input primitive is always at the top
		return null;
	}
}
