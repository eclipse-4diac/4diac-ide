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
import org.eclipse.draw2d.ConnectionRouter;
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
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Handle;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.handles.SquareHandle;
import org.eclipse.gef.requests.CreateConnectionRequest;

public class TargetInterfaceSelectionPolicy extends ModifiedNonResizeableEditPolicy {
	private final List<Connection> lines = new ArrayList<>();

	@Override
	protected List<Handle> createSelectionHandles() {
		final List<Handle> list = new ArrayList<>(super.createSelectionHandles());
		lines.forEach(connection -> list.add(new ConnectionHandle(getHost(), connection)));
		return list;
	}

	@Override
	protected void showSelection() {
		addConnectionLine();
		super.showSelection();
	}

	@Override
	protected void hideSelection() {
		super.hideSelection();
		clearLines();
	}

	@Override
	public TargetInterfaceElementEditPart getHost() {
		return (TargetInterfaceElementEditPart) super.getHost();
	}

	private void addConnectionLine() {
		if (getHost().getParent() instanceof final InterfaceEditPart iep && !iep.isInput()) {
			if (iep.getChildren().stream().filter(TargetInterfaceElementEditPart.class::isInstance).limit(2)
					.count() < 2) {
				// user can use the existing connection
				return;
			}

			clearLines();
			final ConnectionRouter connectionRouter = ((ConnectionLayer) getLayer(LayerConstants.CONNECTION_LAYER))
					.getConnectionRouter();
			iep.getTargetConnections().forEach(connectionEP -> {
				final PolylineConnection newCon = new PolylineConnection();
				newCon.setConnectionRouter(connectionRouter);
				newCon.setLineWidth(ConnectionPreferenceValues.NORMAL_LINE_WIDTH);
				newCon.setForegroundColor(ModifiedMoveHandle.getSelectionColor());

				newCon.setTargetAnchor(new FixedAnchor(getHostFigure(), true));
				if (connectionEP.getSource() instanceof final InterfaceEditPart connectionSourceIEP) {
					newCon.setSourceAnchor(connectionSourceIEP.getSourceConnectionAnchor(new Request()));
				}

				addFeedback(newCon);
				lines.add(newCon);
				newCon.layout(); // layout so the handle gets the correct coordinates
			});
		}
	}

	private void clearLines() {
		lines.forEach(this::removeFeedback);
		lines.clear();
	}

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
			final List<TargetInterfaceElementEditPart> selections = getHost().getViewer().getSelectedEditParts()
					.stream().filter(TargetInterfaceElementEditPart.class::isInstance)
					.map(TargetInterfaceElementEditPart.class::cast).toList();

			final var sourceEP = ((AbstractGraphicalEditPart) getHost().getParent()).getTargetConnections().stream()
					.map(ConnectionEditPart::getSource).filter(InterfaceEditPart.class::isInstance)
					.map(InterfaceEditPart.class::cast).findFirst().orElse(null);

			return new MultiDragTool(selections, sourceEP);
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

	private class MultiDragTool extends FordiacConnectionDragCreationTool {
		private final List<TargetInterfaceElementEditPart> selections;
		private final InterfaceEditPart source;

		public MultiDragTool(final List<TargetInterfaceElementEditPart> selections, final InterfaceEditPart source) {
			this.selections = selections;
			this.source = source;
		}

		@Override
		protected Command getCommand() {
			if (getTargetEditPart() == null) {
				return null;
			}
			final CompoundCommand cmd = new CompoundCommand();
			final CreateConnectionRequest targetRequest = getTargetRequest();

			selections.forEach(destinationEP -> {
				final var targetCmd = new TargetLabelReconnectCommand(this.source.getModel(), null,
						destinationEP.getModel().getRefElement());
				targetRequest.setStartCommand(targetCmd);
				cmd.add(getTargetEditPart().getCommand(targetRequest));
			});

			return cmd;
		}
	}
}
