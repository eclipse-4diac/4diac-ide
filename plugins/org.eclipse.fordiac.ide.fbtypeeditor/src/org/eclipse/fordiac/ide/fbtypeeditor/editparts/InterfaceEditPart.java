/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 				 2019 Johannes Kepler University
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
 *   Alois Zoitl - Moved position calculation to the comment type edit part
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.AncestorListener;
import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.fbtypeeditor.policies.DeleteInterfaceEditPolicy;
import org.eclipse.fordiac.ide.fbtypeeditor.policies.WithNodeEditPolicy;
import org.eclipse.fordiac.ide.gef.draw2d.ConnectorBorder;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDirectEditableEditPart;
import org.eclipse.fordiac.ide.gef.editparts.LabelDirectEditManager;
import org.eclipse.fordiac.ide.gef.editparts.NameCellEditorLocator;
import org.eclipse.fordiac.ide.gef.figures.InteractionStyleFigure;
import org.eclipse.fordiac.ide.gef.policies.INamedElementRenameEditPolicy;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.fordiac.ide.util.IdentifierVerifyListener;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.viewers.TextCellEditor;

public class InterfaceEditPart extends AbstractInterfaceElementEditPart implements NodeEditPart {

	public InterfaceEditPart() {
		super();
		setConnectable(true);
	}

	public class InterfaceFigure extends Label implements InteractionStyleFigure {
		public InterfaceFigure() {
			super();
			setText(getINamedElement().getName());
			setBorder(new ConnectorBorder(getCastedModel()));
			setOpaque(false);
			if (isInput()) {
				setLabelAlignment(PositionConstants.LEFT);
				setTextAlignment(PositionConstants.LEFT);
			} else {
				setLabelAlignment(PositionConstants.RIGHT);
				setTextAlignment(PositionConstants.RIGHT);
			}
		}

		@Override
		public int getIntersectionStyle(Point location) {
			if (isInput()) {
				Rectangle bounds = getBounds().getCopy();
				bounds.width = 5;
				if (bounds.intersects(new Rectangle(location, new Dimension(1, 1)))) {
					return InteractionStyleFigure.REGION_CONNECTION;
				}
				return InteractionStyleFigure.REGION_DRAG;
			}
			Rectangle bounds = getBounds().getCopy();
			bounds.x = bounds.x + (bounds.width - 5);
			bounds.width = 5;
			if (bounds.intersects(new Rectangle(location, new Dimension(1, 1)))) {
				return InteractionStyleFigure.REGION_CONNECTION;
			}
			return InteractionStyleFigure.REGION_DRAG;
		}
	}

	@Override
	protected IFigure createFigure() {
		IFigure fig = new InterfaceFigure();
		fig.addAncestorListener(new AncestorListener() {
			@Override
			public void ancestorRemoved(IFigure ancestor) {
				// nothing to do here
			}

			@Override
			public void ancestorMoved(IFigure ancestor) {
				update();
			}

			@Override
			public void ancestorAdded(IFigure ancestor) {
				update();
			}

		});
		return fig;
	}

	@Override
	protected void update() {
		if (getCastedModel() instanceof Event && null != sourceConnections) {
			for (Object con : sourceConnections) {
				WithEditPart with = (WithEditPart) con;
				with.updateWithPos();
			}
		}
	}

	@Override
	public IInterfaceElement getCastedModel() {
		return (IInterfaceElement) getModel();
	}

	@SuppressWarnings("rawtypes")
	public void setInOutConnectionsWith(int with) {
		for (Iterator iterator = getSourceConnections().iterator(); iterator.hasNext();) {
			ConnectionEditPart cep = (ConnectionEditPart) iterator.next();
			if (cep.getFigure() instanceof PolylineConnection) {
				((PolylineConnection) cep.getFigure()).setLineWidth(with);
			}
		}
		for (Iterator iterator = getTargetConnections().iterator(); iterator.hasNext();) {
			ConnectionEditPart cep = (ConnectionEditPart) iterator.next();
			if (cep.getFigure() instanceof PolylineConnection) {
				((PolylineConnection) cep.getFigure()).setLineWidth(with);
			}
		}
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new INamedElementRenameEditPolicy() {
			@Override
			protected Command getDirectEditCommand(final DirectEditRequest request) {
				if (getHost() instanceof AbstractDirectEditableEditPart) {
					return new ChangeNameCommand(getCastedModel(), (String) request.getCellEditor().getValue());
				}
				return null;
			}

		});

		// allow delete of a FB
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new DeleteInterfaceEditPolicy());

		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new WithNodeEditPolicy());
	}

	@Override
	public void performRequest(final Request request) {
		// REQ_DIRECT_EDIT -> first select 0.4 sec pause -> click -> edit
		// REQ_OPEN -> doubleclick
		if (request.getType() == RequestConstants.REQ_OPEN) {
			// Perform double click as direct edit
			request.setType(RequestConstants.REQ_DIRECT_EDIT);
		}
		super.performRequest(request);
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
		int pos = calculateWithPos(connection, isInput());
		if (isInput()) {
			return new InputWithAnchor(getFigure(), pos, this);

		}
		return new OutputWithAnchor(getFigure(), pos, this);
	}

	private static int calculateWithPos(final ConnectionEditPart connection, boolean isInput) {
		int pos = 1;
		With with = (With) connection.getModel();
		Event event = (Event) with.eContainer();

		InterfaceList interfaceList = (InterfaceList) event.eContainer();
		if (null != interfaceList) {
			pos += ((isInput) ? interfaceList.getEventInputs() : interfaceList.getEventOutputs()).indexOf(event);
		}
		return pos;
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final Request request) {
		return new ChopboxAnchor(getFigure());
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final ConnectionEditPart connection) {
		return getSourceConnectionAnchor(connection);
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final Request request) {
		return new ChopboxAnchor(getFigure());
	}

	@Override
	public Label getNameLabel() {
		return (Label) getFigure();
	}

	@Override
	public INamedElement getINamedElement() {
		return getCastedModel();
	}

	@Override
	protected DirectEditManager createDirectEditManager() {
		Label l = getNameLabel();
		return new LabelDirectEditManager(this, TextCellEditor.class, new NameCellEditorLocator(l), l,
				new IdentifierVerifyListener()) {
			@Override
			protected void bringDown() {
				if (getEditPart() instanceof InterfaceEditPart) {
					((InterfaceEditPart) getEditPart()).refreshName();
				}
				super.bringDown();
			}
		}; // ensures that interface elements are only valid identifiers
	}
}
