/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.commands.TargetLabelReconnectCommand;
import org.eclipse.fordiac.ide.application.editparts.TargetInterfaceElementEditPart;
import org.eclipse.fordiac.ide.gef.FixedAnchor;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.gef.policies.ModifiedMoveHandle;
import org.eclipse.fordiac.ide.gef.policies.ModifiedNonResizeableEditPolicy;
import org.eclipse.fordiac.ide.gef.tools.FordiacConnectionDragCreationTool;
import org.eclipse.fordiac.ide.ui.preferences.ConnectionPreferenceValues;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Handle;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.handles.SquareHandle;
import org.eclipse.gef.requests.CreateConnectionRequest;

public class TargetInterfaceSelectionPolicy extends ModifiedNonResizeableEditPolicy {
	private Connection line;

	@Override
	protected List<Handle> createSelectionHandles() {
		final List<Handle> list = new ArrayList<>(super.createSelectionHandles());
		if (line != null) {
			list.add(new ConnectionHandle(getHost(), line));
		}
		return list;
	}

	@Override
	protected void showSelection() {
		if (line == null) {
			addConnectionLine();
		}
		super.showSelection();
	}

	@Override
	protected void hideSelection() {
		super.hideSelection();

		if (line != null) {
			deleteLine();
		}
	}

	@Override
	public Command getCommand(final Request request) {
		if (REQ_CONNECTION_START.equals(request.getType())) {
			return getConnectionCreateCommand((CreateConnectionRequest) request);
		}

		return super.getCommand(request);
	}

	protected final Command getConnectionCreateCommand(final CreateConnectionRequest request) {
		final TargetLabelReconnectCommand cmd = new TargetLabelReconnectCommand(null,
				getHost().getModel().getRefElement());
		request.setStartCommand(cmd);
		return cmd;
	}

	@Override
	public TargetInterfaceElementEditPart getHost() {
		return (TargetInterfaceElementEditPart) super.getHost();
	}

	private void addConnectionLine() {
		final PolylineConnection newCon = new PolylineConnection();
		newCon.setConnectionRouter(((ConnectionLayer) getLayer(LayerConstants.CONNECTION_LAYER)).getConnectionRouter());
		newCon.setLineWidth(ConnectionPreferenceValues.NORMAL_LINE_WIDTH);
		newCon.setForegroundColor(ModifiedMoveHandle.getSelectionColor());

		if (getHost().getParent() instanceof final InterfaceEditPart iep && !iep.isInput()) {
			if (iep.getTargetConnections().isEmpty() || iep.getChildren().stream()
					.filter(TargetInterfaceElementEditPart.class::isInstance).limit(2).count() < 2) {
				// we don't want a connection if pin has no connection or only 1 TargetLabel
				return;
			}

			newCon.setTargetAnchor(new FixedAnchor(getHostFigure(), true));
			if (iep.getTargetConnections().getFirst()
					.getSource() instanceof final InterfaceEditPart connectionSourceIEP) {
				newCon.setSourceAnchor(connectionSourceIEP.getSourceConnectionAnchor(new Request()));
			}
		}

		line = newCon;
		addFeedback(line);
	}

	private void deleteLine() {
		removeFeedback(line);
		line = null;
	}

	// TODO: cleanup this class (maybe extract)
	private class ConnectionHandle extends SquareHandle {
		public ConnectionHandle(final GraphicalEditPart host, final Connection connectionLine) {
			super(host, null);
			setLocator(new ConnectionLocator(connectionLine, ConnectionLocator.SOURCE) {
				@Override
				protected Point getLocation(final PointList points) {
					final Point p = super.getLocation(points);
					// Offset for Source connection endpoint
					p.x += ((getPreferredSize().width / 2) - 4);
					return p;
				}
			});
		}

		@Override
		protected DragTracker createDragTracker() {
			return new FordiacConnectionDragCreationTool() {
				@Override
				protected boolean updateTargetUnderMouse() {
					if (isInState(STATE_INITIAL)) {
						// we are the first target under mouse
						setTargetEditPart(getHost());
						return true;
					}

					return super.updateTargetUnderMouse();
				}
			};
		}

		@Override
		protected void init() {
			super.init();
			setPreferredSize((ConnectionPreferenceValues.HANDLE_SIZE * 4) / 3, ConnectionPreferenceValues.HANDLE_SIZE);
		}

		@Override
		public Dimension getPreferredSize(final int wHint, final int hHint) {
			final Dimension dim = super.getPreferredSize(wHint, hHint).getCopy();

			final double zoomFactor = getZoomFactor();
			if (dim.height * zoomFactor < ConnectionPreferenceValues.MIN_HANDLE_SIZE) {
				dim.height = (int) (ConnectionPreferenceValues.MIN_HANDLE_SIZE / zoomFactor);
			}
			if (dim.width * zoomFactor < ConnectionPreferenceValues.MIN_HANDLE_SIZE) {
				dim.width = (int) (ConnectionPreferenceValues.MIN_HANDLE_SIZE / zoomFactor);
			}
			return dim;
		}

		@Override
		public void paintFigure(final Graphics g) {
			g.setLineStyle(Graphics.LINE_SOLID);
			g.setLineWidth((ModifiedMoveHandle.SELECTION_BORDER_WIDTH));
			g.setXORMode(false);
			g.setForegroundColor(ModifiedMoveHandle.getSelectionColor());
			g.setBackgroundColor(ModifiedMoveHandle.getSelectionColor());
			final int radius = (int) (ConnectionPreferenceValues.HANDLE_SIZE * 0.45);
			final Rectangle r = Rectangle.SINGLETON.setBounds(getBounds()).shrink(1, 1);
			g.drawRoundRectangle(r, radius, radius);
			g.setAlpha(ModifiedMoveHandle.SELECTION_FILL_ALPHA);
			g.fillRoundRectangle(r, radius, radius);
			g.setAlpha(255);

			paintInnerFigure(g, r);
		}

		private void paintInnerFigure(final Graphics g, final Rectangle r) {
			final int shrink = getInnerShrinkVal();
			r.shrink(new Insets(shrink + 1, 0, shrink + 1, shrink * 2));
			g.fillRoundRectangle(r, r.height / 2, r.height / 2);
		}

		private double getZoomFactor() {
			return ((ScalableFreeformRootEditPart) getOwner().getRoot()).getZoomManager().getZoom();
		}

		private int getInnerShrinkVal() {
			int shrinkVal = 2;
			if (shrinkVal * getZoomFactor() < 2) {
				shrinkVal = (int) (2 / getZoomFactor());
			}
			return shrinkVal;
		}
	}
}
