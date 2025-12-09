/*******************************************************************************
 * Copyright (c) 2008, 2024 Profactor GmbH, fortiss GmbH,
 *                          Johannes Kepler University Linz
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
import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.figures.AdvancedFixedAnchor;
import org.eclipse.fordiac.ide.gef.FixedAnchor;
import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.gef.Request;

public class OutputPrimitiveEditPart extends AbstractPrimitiveEditPart {

	private final ConnectingConnection connectingConnection;

	OutputPrimitiveEditPart() {
		super(new PrimitiveConnection(false));
		connectingConnection = new ConnectingConnection();
	}

	public ConnectingConnection getConnectingConnection() {
		return connectingConnection;
	}

	@Override
	public void refresh() {
		super.refresh();
		if (getModel() != null) {
			if (srcAnchor != null) {
				srcAnchor.setIsInput(isLeftInterface());
			}
			if (srcNeighbourAnchor != null) {
				srcNeighbourAnchor.setIsInput(isLeftInterface());
			}
			if (dstAnchor != null) {
				dstAnchor.setIsInput(!isLeftInterface());
			}
			if (dstNeighbourAnchor != null) {
				dstNeighbourAnchor.setIsInput(isLeftInterface());
			}
		}
	}

	@Override
	public List<Object> getModelSourceConnections() {
		if (getModel().getServiceTransaction() == null) { // has been removed already
			return Collections.emptyList();
		}

		final List<Object> conns = new ArrayList<>();
		if (getModel().getServiceTransaction().getOutputPrimitive()
				.indexOf(getModel()) < (getModel().getServiceTransaction().getOutputPrimitive().size() - 1)) {
			conns.add(getConnectingConnection());
		}
		conns.add(getPrimitiveConnection());
		return conns;
	}

	@Override
	public List<Object> getModelTargetConnections() {
		if (getModel().getServiceTransaction() == null) { // has been removed already
			return Collections.emptyList();
		}

		final List<Object> conns = new ArrayList<>();
		conns.add(getPrimitiveConnection());
		final int currentIndex = getModel().getServiceTransaction().getOutputPrimitive().indexOf(getModel());
		if (currentIndex == 0) { // First output primitive: connection from input primitive
			final InputPrimitive iP = getModel().getServiceTransaction().getInputPrimitive();
			final InputPrimitiveEditPart part = (InputPrimitiveEditPart) getViewer().getEditPartForModel(iP);
			if (part != null && !part.getModelSourceConnections().isEmpty()) {
				conns.add(part.getModelSourceConnections().get(0));
			}
			return conns;
		}
		// return previous output primitive

		final OutputPrimitive oP = getModel().getServiceTransaction().getOutputPrimitive().get(currentIndex - 1);
		final OutputPrimitiveEditPart part = (OutputPrimitiveEditPart) getViewer().getEditPartForModel(oP);
		conns.add(part.getModelSourceConnections().get(0));

		return conns;
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final Request request) {
		return null;
	}

	@Override
	public OutputPrimitive getModel() {
		return (OutputPrimitive) super.getModel();
	}

	@Override
	protected FixedAnchor getPrimitiveConnSourceAnchor() {
		return new FixedAnchor(getCenterFigure(), isLeftInterface());
	}

	@Override
	protected AdvancedFixedAnchor getConnectingConnSourceAnchor() {
		return new AdvancedFixedAnchor(getCenterFigure(), isLeftInterface(), isLeftInterface() ? 3 : -4, 0);
	}

	@Override
	protected FixedAnchor getPrimitiveConnTargetAnchor() {
		return new FixedAnchor(getNameLabel(), !isLeftInterface());
	}

	@Override
	protected AdvancedFixedAnchor getConnectingConnTargetAnchor() {
		return new AdvancedFixedAnchor(getCenterFigure(), isLeftInterface(), isLeftInterface() ? 3 : -4, 0);
	}
}