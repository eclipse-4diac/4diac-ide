/*******************************************************************************
 * Copyright (c) 2012, 2024 Profactor GmbH, fortiss GmbH,
 *                          Johannes Kepler University Linz,
 *                          Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Harmonized deployment and monitoring
 *   Lukas Wais - Implemented a max size for monitoring values
 *   Michael Oberlehner - Added deletion of monitorign elements
 *   Martin Erich Jobst - rewrite based on AbstractMonitoringBaseEditPart
 *                        for new deployment monitoring framework
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.debug.ui.editparts;

import org.eclipse.draw2d.AncestorListener;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.deployment.debug.ui.annotation.WatchValueAnnotation;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.gef.editparts.SpecificLayerEditPart;
import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

public abstract class AbstractWatchValueEditPart extends AbstractGraphicalEditPart implements SpecificLayerEditPart {

	protected class AncestorMoveListener implements AncestorListener {
		@Override
		public void ancestorRemoved(final IFigure ancestor) {
			// nothing to do
		}

		@Override
		public void ancestorMoved(final IFigure ancestor) {
			refreshVisuals();
		}

		@Override
		public void ancestorAdded(final IFigure ancestor) {
			// nothing to do
		}
	}

	private final AncestorMoveListener hostMoveListener = new AncestorMoveListener();

	private InterfaceEditPart host;

	@Override
	public void activate() {
		super.activate();
		refreshVisuals();
	}

	@Override
	public void deactivate() {
		if (host != null) {
			host.getFigure().removeAncestorListener(hostMoveListener);
		}
		super.deactivate();
	}

	public IInterfaceElement getInterfaceElement() {
		return getModel().getElement();
	}

	@Override
	public WatchValueAnnotation getModel() {
		return (WatchValueAnnotation) super.getModel();
	}

	public InterfaceEditPart getHost() {
		if (host == null && getViewer().getEditPartRegistry()
				.get(getInterfaceElement()) instanceof final InterfaceEditPart iep) {
			host = iep;
			host.getFigure().addAncestorListener(hostMoveListener);
		}
		return host;
	}

	@Override
	public String getSpecificLayer() {
		return ZoomScalableFreeformRootEditPart.TOP_LAYER;
	}

	@Override
	public boolean understandsRequest(final Request request) {
		if (request.getType() == RequestConstants.REQ_MOVE) {
			return false;
		}
		return super.understandsRequest(request);
	}

	@Override
	public void removeNotify() {
		if (isGrandParentDeletion()) {
			// if a grandparent is removed or a subapp collapsed our figure is not removed
			// as it is in a specific layer.
			// Therefore we have to do it here separatly.
			final IFigure layerFig = getLayer(getSpecificLayer());
			if (layerFig != null && layerFig.equals(getFigure().getParent())) {
				layerFig.remove(getFigure());
				return;
			}
		}
		super.removeNotify();
	}

	private boolean isGrandParentDeletion() {
		// if the interface element has a fbnetworkelement and this fbnetworkelement a
		// network a grandparent was deleted
		// or an expanded subapp folded
		return (getInterfaceElement().getFBNetworkElement() != null
				&& getInterfaceElement().getFBNetworkElement().getFbNetwork() != null);
	}

	protected Point calculatePos() {
		if (getHost() != null) {
			final Rectangle bounds = getHost().getFigure().getBounds();
			int x = 0;
			if (getInterfaceElement().isIsInput()) {
				final int width = calculateSize().width;
				x = bounds.x - 2 - width;
			} else {
				x = bounds.x + bounds.width + 2;
			}
			final int y = bounds.y;
			return new Point(x, y);
		}
		return new Point(0, 0);
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		refreshPosition();
	}

	protected void refreshPosition() {
		if (getParent() != null) {
			Rectangle bounds = null;
			final Point p = calculatePos();
			final Dimension dimension = calculateSize();
			bounds = new Rectangle(p.x, p.y, dimension.width, dimension.height);
			((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), bounds);
		}
	}

	protected abstract Dimension calculateSize();

	@Override
	public <T> T getAdapter(final Class<T> key) {
		if (key == IInterfaceElement.class) {
			return key.cast(getInterfaceElement());
		}
		return super.getAdapter(key);
	}
}
