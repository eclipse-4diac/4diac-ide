/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GbmH, TU Wien ACIN, fortiss GmbH,
 * 				 2018 - 2019 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger, Waldemar Eisenmenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - allowed resource drop on on whole interfaces
 *   Alois Zoitl - extracted interface selection policy and added connection
 *   			   creation feedback
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.editparts;

import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.gef.FixedAnchor;
import org.eclipse.fordiac.ide.gef.draw2d.ConnectorBorder;
import org.eclipse.fordiac.ide.gef.draw2d.SetableAlphaLabel;
import org.eclipse.fordiac.ide.gef.figures.ToolTipFigure;
import org.eclipse.fordiac.ide.gef.policies.DataInterfaceLayoutEditPolicy;
import org.eclipse.fordiac.ide.gef.policies.InterfaceElementSelectionPolicy;
import org.eclipse.fordiac.ide.gef.policies.ValueEditPartChangeEditPolicy;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;

public abstract class InterfaceEditPart extends AbstractConnectableEditPart
		implements NodeEditPart, IDeactivatableConnectionHandleRoleEditPart {
	private ValueEditPart referencedPart;
	private int mouseState;

	private Adapter contentAdapter = null;

	public InterfaceEditPart() {
		setConnectable(true);
	}

	@Override
	public DragTracker getDragTracker(Request request) {
		return new ConnCreateDirectEditDragTrackerProxy(this);
	}

	public int getMouseState() {
		return mouseState;
	}

	@Override
	protected void refreshTargetConnections() {
		super.refreshTargetConnections();
		if (getReferencedValueEditPart() != null) {
			getReferencedValueEditPart().refreshValue();
		}
	}

	protected String getLabelText() {
		return getModel().getName();
	}

	public class InterfaceFigure extends SetableAlphaLabel {
		public InterfaceFigure() {
			super();
			setOpaque(false);
			setText(getLabelText());
			setBorder(new ConnectorBorder(getModel()));
			if (isInput()) {
				setLabelAlignment(PositionConstants.LEFT);
				setTextAlignment(PositionConstants.LEFT);
			} else {
				setLabelAlignment(PositionConstants.RIGHT);
				setTextAlignment(PositionConstants.RIGHT);
			}
			setToolTip(new ToolTipFigure(getModel()));

			if (isAdapter()) {
				// this mouse listener acquires the current mouse state including the modifier
				// keys so that we can use it in
				// case we are clicking on an adapter with the ctrl key pressed.
				addMouseMotionListener(new MouseMotionListener() {

					@Override
					public void mouseDragged(MouseEvent me) {
						mouseState = me.getState();
					}

					@Override
					public void mouseEntered(MouseEvent me) {
						mouseState = me.getState();

					}

					@Override
					public void mouseExited(MouseEvent me) {
						mouseState = me.getState();
					}

					@Override
					public void mouseHover(MouseEvent me) {
						mouseState = me.getState();
					}

					@Override
					public void mouseMoved(MouseEvent me) {
						mouseState = me.getState();
					}

				});
			}
		}
	}

	@Override
	protected IFigure createFigure() {
		return new InterfaceFigure();
	}

	@Override
	protected void refreshVisuals() {
		// nothing to do
	}

	protected abstract GraphicalNodeEditPolicy getNodeEditPolicy();

	@SuppressWarnings("unchecked")
	public void setInOutConnectionsWidth(int width) {
		getSourceConnections().forEach(cep -> checkConnection(width, (ConnectionEditPart) cep));
		getTargetConnections().forEach(cep -> checkConnection(width, (ConnectionEditPart) cep));
	}

	private static void checkConnection(int width, ConnectionEditPart cep) {
		if (cep.getFigure() instanceof PolylineConnection) {
			((PolylineConnection) cep.getFigure()).setLineWidth(width);
		}
	}

	@Override
	protected void createEditPolicies() {
		GraphicalNodeEditPolicy nodeEditPolicy = getNodeEditPolicy();
		if (null != nodeEditPolicy) {
			installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, nodeEditPolicy);
		}
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new InterfaceElementSelectionPolicy(this));

		if (isVariable()) {
			// layoutrole that allows to drop "strings" to an Input Variable
			// which is than used as Parameter
			installEditPolicy(EditPolicy.LAYOUT_ROLE, getLayoutPolicy());

			installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new ValueEditPartChangeEditPolicy() {
				@Override
				protected ValueEditPart getValueEditPart() {
					return getReferencedValueEditPart();
				}
			});
		}
	}

	@SuppressWarnings("static-method") // we want to allow subclasses to provide different layout policies
	protected LayoutEditPolicy getLayoutPolicy() {
		return new DataInterfaceLayoutEditPolicy();
	}

	@Override
	public void setConnectionHandleRoleEnabled(boolean enabled) {
		// nothing to do
	}

	@Override
	public IInterfaceElement getModel() {
		return (IInterfaceElement) super.getModel();
	}

	public boolean isInput() {
		return getModel().isIsInput();
	}

	public boolean isEvent() {
		return getModel() instanceof Event;
	}

	public boolean isAdapter() {
		return getModel() instanceof AdapterDeclaration;
	}

	public boolean isVariable() {
		return getModel() instanceof VarDeclaration;
	}

	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
	}

	private Adapter getContentAdapter() {
		if (null == contentAdapter) {
			contentAdapter = createContentAdapter();
		}
		return contentAdapter;
	}

	// Allows childclasses to provide their own content adapters
	protected Adapter createContentAdapter() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(final Notification notification) {
				Object feature = notification.getFeature();
				if (LibraryElementPackage.eINSTANCE.getIInterfaceElement_InputConnections().equals(feature)
						|| LibraryElementPackage.eINSTANCE.getIInterfaceElement_OutputConnections().equals(feature)
						|| LibraryElementPackage.eINSTANCE.getINamedElement_Name().equals(feature)) {
					refresh();
				}
				super.notifyChanged(notification);
			}
		};
	}

	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			getModel().eAdapters().add(getContentAdapter());
		}
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			getModel().eAdapters().remove(getContentAdapter());
		}
		referencedPart = null;
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final ConnectionEditPart connection) {
		return new FixedAnchor(getFigure(), isInput(), this);
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final Request request) {
		return new FixedAnchor(getFigure(), isInput(), this);
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final ConnectionEditPart connection) {
		return new FixedAnchor(getFigure(), isInput());
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final Request request) {
		return new FixedAnchor(getFigure(), isInput());
	}

	@Override
	protected List<?> getModelSourceConnections() {
		if (!isInput()) {
			// only outputs have source connections
			return getModel().getOutputConnections();
		}
		return Collections.emptyList();
	}

	@Override
	protected List<?> getModelTargetConnections() {
		if (isInput()) {
			// only outputs have source connections
			return getModel().getInputConnections();
		}
		return Collections.emptyList();
	}

	@Override
	public void setSelected(int value) {
		super.setSelected(value);
	}

	@Override
	public void performRequest(final Request request) {
		// REQ_DIRECT_EDIT -> first select 0.4 sec pause -> click -> edit
		// REQ_OPEN -> doubleclick
		if (request.getType() == RequestConstants.REQ_MOVE) {
			// TODO: move parent editpart??
		}
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT || request.getType() == RequestConstants.REQ_OPEN) {
			ValueEditPart part = getReferencedValueEditPart();
			if ((part != null) && (isVariable()) && (!(getModel() instanceof AdapterDeclaration))) {
				part.performDirectEdit();
			}
		} else {
			super.performRequest(request);
		}
	}

	public ValueEditPart getReferencedValueEditPart() {
		if (null == referencedPart && getModel() instanceof VarDeclaration) {
			Object temp = getViewer().getEditPartRegistry().get(((VarDeclaration) getModel()).getValue());
			if (temp instanceof ValueEditPart) {
				referencedPart = (ValueEditPart) temp;
			}
		}
		return referencedPart;
	}
}
