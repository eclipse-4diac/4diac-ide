/*******************************************************************************
 * Copyright (c) 2008 - 2016 Profactor GmbH, fortiss GmbH
 * 						2017 fortiss GmbH
 * 						2019 Johannes Kepler University
 * 				 		2020 Primetals Technologies Germany GmbH
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
 *   Alexander Lumplecker
 *     - changed VirtualIOTooltipFigure
 *     - added getConnections
 *     - code extracted from class FBNetworkElementTooltipFigure
 *     - and from class OpenConnectionOppositeResource
 *     - changed getConnectionOpposite
 *     - use class ConnectionsHelper
 *     - removed getConnections and getOppositeInterfaceElement
 *******************************************************************************/
package org.eclipse.fordiac.ide.resourceediting.editparts;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.application.editparts.InterfaceEditPartForFBNetwork;
import org.eclipse.fordiac.ide.gef.FixedAnchor;
import org.eclipse.fordiac.ide.gef.editparts.AbstractViewEditPart;
import org.eclipse.fordiac.ide.gef.figures.VerticalLineCompartmentFigure;
import org.eclipse.fordiac.ide.model.helpers.ConnectionsHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.jface.util.IPropertyChangeListener;

/**
 * This class implements an EditPart for an "VirtualInOutput". It is required if
 * a connection is "brocken" when mapped. (fbs distributed to different
 * resources)
 *
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class VirtualInOutputEditPart extends AbstractViewEditPart implements NodeEditPart {

	@Override
	public void activate() {
		super.activate();
		updatePos();
	}

	@Override
	protected Adapter createContentAdapter() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(final Notification notification) {
				super.notifyChanged(notification);
				refreshSourceConnections();
				refreshTargetConnections();
				refreshVisuals();
				refreshTooltip();
			}

		};
	}

	private void refreshTooltip() {
		getFigure().setToolTip(new VirtualIOTooltipFigure());

	}

	private void updatePos() {
		if (getParent() instanceof FBNetworkContainerEditPart) {
			final IInterfaceElement element = getIInterfaceElement();
			final Object o = getViewer().getEditPartRegistry().get(element);
			if (o instanceof InterfaceEditPartForResourceFBs) {
				updatePos((InterfaceEditPartForResourceFBs) o);
			}
		}
	}

	void updatePos(InterfaceEditPartForFBNetwork referencedEditPart) {
		final String label = ((Label) getFigure()).getText();

		final Rectangle bounds = referencedEditPart.getFigure().getBounds();
		int x;
		if (isInput()) {
			x = bounds.x - 20 - FigureUtilities.getTextWidth(label, getFigure().getFont());
		} else {
			x = bounds.x + bounds.width + 1;
		}
		((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), new Rectangle(x, bounds.y, -1, -1));
	}

	/**
	 * Gets the casted model.
	 *
	 * @return the casted model
	 */
	@Override
	public VirtualIO getModel() {
		return (VirtualIO) super.getModel();
	}

	public boolean isInput() {
		return getIInterfaceElement().isIsInput();
	}

	private IInterfaceElement getIInterfaceElement() {
		return getModel().getReferencedInterfaceElement();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		// overwrite the rename edit policy - no rename possible
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, null);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.gef.editparts.AbstractEditPart#understandsRequest(org.eclipse
	 * .gef.Request)
	 */
	@Override
	public boolean understandsRequest(Request request) {
		if (request.getType() == RequestConstants.REQ_MOVE) {
			return false;
		}
		if ((request.getType() == RequestConstants.REQ_DIRECT_EDIT)
				|| (request.getType() == RequestConstants.REQ_OPEN)) {
			return false;
		}
		return super.understandsRequest(request);
	}

	@Override
	public void performRequest(Request request) {
		if ((request.getType() == RequestConstants.REQ_DIRECT_EDIT)
				|| (request.getType() == RequestConstants.REQ_OPEN)) {
			return;
		}
		super.performRequest(request);
	}

	/**
	 * The Class VirtualInputOutputFigure.
	 */
	private class VirtualInputOutputFigure extends Label {

		/**
		 * Instantiates a new virtual input output figure.
		 */
		public VirtualInputOutputFigure() {
			super();
			setOpaque(false);
			if (!isInput()) {
				setIcon(FordiacImage.ICON_LINK_OUTPUT.getImage());
				setLabelAlignment(PositionConstants.LEFT);
				setTextAlignment(PositionConstants.LEFT);
			} else {
				setIcon(FordiacImage.ICON_LINK_INPUT.getImage());
				setLabelAlignment(PositionConstants.RIGHT);
				setTextAlignment(PositionConstants.RIGHT);
			}
			setToolTip(new VirtualIOTooltipFigure());
			setSize(-1, -1);
		}

	}

	private class VirtualIOTooltipFigure extends Figure {
		public VirtualIOTooltipFigure() {

			setLayoutManager(new GridLayout());

			boolean drawLine = false;
			final EList<Connection> connections = ConnectionsHelper.getConnections(getIInterfaceElement());

			if (connections == null) {
				return;
			}

			for (int i = 0; i <= (connections.size() - 1); i++) {
				final TextFlow connectionTo = new TextFlow();
				final FlowPage fp = new FlowPage();
				final Figure line = new VerticalLineCompartmentFigure();
				final IInterfaceElement oppositeIE = ConnectionsHelper
						.getOppositeInterfaceElement(getIInterfaceElement(), connections, i);
				final FBNetworkElement oppositefbNetElement = oppositeIE.getFBNetworkElement();

				if (i >= 1) {
					drawLine = true;
				}

				if (drawLine) {
					add(line);
					setConstraint(line, new GridData(PositionConstants.CENTER, PositionConstants.MIDDLE, true, true));
				}

				if (oppositefbNetElement == null) {
					return;
				}
				if (oppositefbNetElement.getResource() == null) {
					return;
				}

				final Resource res = oppositefbNetElement.getResource();
				if (res == null) {
					return;
				}
				final Device dev = res.getDevice();
				if (dev == null) {
					return;
				}

				if (drawLine) {
					connectionTo.setText(dev.getName() + "." + res.getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
							+ oppositefbNetElement.getName() + "." //$NON-NLS-1$
							+ oppositeIE.getName());
					fp.add(connectionTo);
					line.add(fp);
					line.setConstraint(fp,
							new GridData(PositionConstants.CENTER, PositionConstants.MIDDLE, false, true));
				} else {
					final Label nameLabel = new Label(dev.getName() + "." + res.getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
							+ oppositefbNetElement.getName() + "." //$NON-NLS-1$
							+ oppositeIE.getName());
					add(nameLabel);
					setConstraint(nameLabel,
							new GridData(PositionConstants.CENTER, PositionConstants.MIDDLE, true, true));
				}
			}
		}
	}

	@Override
	protected IFigure createFigureForModel() {
		return new VirtualInputOutputFigure();
	}

	@Override
	public INamedElement getINamedElement() {
		return getModel().getReferencedInterfaceElement();
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final ConnectionEditPart connection) {
		return new FixedAnchor(getFigure(), isInput());
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final Request request) {
		return new FixedAnchor(getFigure(), isInput());
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
	public Label getNameLabel() {
		return null;
	}

	@Override
	public IPropertyChangeListener getPreferenceChangeListener() {
		return null;
	}

	@Override
	protected void refreshName() {
		// we don't have a name to refresh and therfore nothing todo here
	}
}
