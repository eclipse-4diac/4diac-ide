/*******************************************************************************
 * Copyright (c) 2014, 2021 Profactor GbmH, fortiss GmbH,
 *               2023       Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Muddasir Shakil - Added double line for Adapter and Struct connection
 *   Prankur Agarwal - Added handling for truncating label according to the
 *   				   max size preference option value
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.figures;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Geometry;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.ParagraphTextLayout;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.fordiac.ide.model.data.AnyType;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.util.ColorHelper;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;

public class HideableConnection extends PolylineConnection {

	private static final int CONNECTION_SELECTION_TOLERANCE = 6;
	public static final int BEND_POINT_BEVEL_SIZE = 5;
	private static final int NORMAL_DOUBLE_LINE_WIDTH = 3;
	private static final int DOUBLE_LINE_AMPLIFICATION = 2;

	private boolean hidden = false;
	private Connection model;
	private Color lighterColor;
	private static int maxWidth;

	static {
		maxWidth = Activator.getDefault().getPreferenceStore()
				.getInt(DiagramPreferences.MAX_HIDDEN_CONNECTION_LABEL_SIZE);
	}

	public static class ConnectionLabel extends RoundedRectangle implements RotatableDecoration {

		private final Label label;
		private final boolean srcLabel;

		public ConnectionLabel(final boolean srcLabel) {
			super();
			this.srcLabel = srcLabel;
			setLayoutManager(new StackLayout());
			setFill(true);
			setAntialias(1);
			setOutline(false);
			setCornerDimensions(new Dimension(10, 10));

			label = createLabel();
			add(label);
		}

		private static Label createLabel() {
			final Label label = new Label();
			label.setOpaque(false);
			label.setForegroundColor(ColorConstants.white);
			label.setFont(getLabelFont());
			label.setBorder(new MarginBorder(0,2,0,2));
			return label;
		}

		public Label getLabel() {
			return label;
		}

		private static Font getLabelFont() {
			return JFaceResources.getFontRegistry()
					.get(org.eclipse.fordiac.ide.ui.preferences.PreferenceConstants.DIAGRAM_FONT);
		}

		@Override
		public void setReferencePoint(final Point p) {
			// we don't want this decorator to be rotated so that the number keeps readable
		}

		@Override
		public void setLocation(final Point p) {
			final Dimension preferredSize = super.getPreferredSize();
			if (!srcLabel) {
				p.x -= preferredSize.width;
			}
			p.y -= preferredSize.height / 2;
			super.setBounds(new Rectangle(p, preferredSize));
		}

	}

	private static class ConLabelToolTip extends Figure {
		private final TextFlow content = new TextFlow();
		private final FlowPage fp = new FlowPage();

		public ConLabelToolTip(final String tooltipText) {
			setLayoutManager(new GridLayout());
			content.setText(tooltipText);
			content.setLayoutManager(new ParagraphTextLayout(content, ParagraphTextLayout.WORD_WRAP_HARD));
			fp.add(content);
			add(fp);
		}

	}

	public void setModel(final Connection newModel) {
		model = newModel;
	}

	public Connection getModel() {
		return model;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(final boolean hidden) {
		final boolean oldHidden = this.hidden;
		this.hidden = hidden;
		if (oldHidden != hidden) {
			if (hidden) {
				setSourceDecoration(createSourceLabel());
				setTargetDecoration(createTargetLabel());
				updateConLabels();
			} else {
				setSourceDecoration(null);
				setTargetDecoration(null);
			}
			invalidate();
			repaint();
		}
	}

	@Override
	public ConnectionLabel getSourceDecoration() {
		return (ConnectionLabel) super.getSourceDecoration();
	}

	@Override
	public ConnectionLabel getTargetDecoration() {
		return (ConnectionLabel) super.getTargetDecoration();
	}

	public void updateConLabels() {
		if (isHidden()) {
			getSourceDecoration().getLabel().setText(createDestinationLabelText());
			updateSourceTooltip();
			getTargetDecoration().getLabel().setText(createSourceLabelText());
			updateTargetTooltip();
		}
	}

	private RotatableDecoration createTargetLabel() {
		final ConnectionLabel label = new ConnectionLabel(false);
		label.setBackgroundColor(getForegroundColor());
		return label;
	}
	private RotatableDecoration createSourceLabel() {
		final ConnectionLabel label = new ConnectionLabel(true);
		label.setBackgroundColor(getForegroundColor());
		return label;
	}

	private String createSourceLabelText() {
		if (getModel().getSource() != null && getModel().getDestination() != null) {
			return createLabelText(getModel().getSource(), getModel().getDestination().getInputConnections());
		}
		return ""; //$NON-NLS-1$
	}

	private String createDestinationLabelText() {
		if (getModel().getSource() != null && getModel().getDestination() != null) {
			return createLabelText(getModel().getDestination(), getModel().getSource().getOutputConnections());
		}
		return ""; //$NON-NLS-1$
	}

	private void updateSourceTooltip() {
		if (getModel().getSource() != null && getModel().getDestination() != null) {
			getSourceDecoration().setToolTip(createSourceLabelToolTip(getModel().getSource().getOutputConnections()));
		}
	}

	private void updateTargetTooltip() {
		if (getModel().getSource() != null && getModel().getDestination() != null) {
			getTargetDecoration().setToolTip(createDstLabelToolTip(getModel().getDestination().getInputConnections()));
		}
	}

	private IFigure createSourceLabelToolTip(final EList<Connection> connections) {
		final List<Connection> hiddenConnections = getHiddenConnections(connections);
		if (hiddenConnections.size() > 1) {
			final StringBuilder builder = new StringBuilder();
			hiddenConnections.forEach(con -> {
				if (con.getDestination() != null) {
					builder.append(generateIEString(con.getDestination()));
					builder.append('\n');
				}
			});
			builder.deleteCharAt(builder.length() - 1);
			return new ConLabelToolTip(builder.toString());
		}
		return null;
	}

	private IFigure createDstLabelToolTip(final EList<Connection> connections) {
		final List<Connection> hiddenConnections = getHiddenConnections(connections);
		if (hiddenConnections.size() > 1) {
			final StringBuilder builder = new StringBuilder();
			hiddenConnections.forEach(con -> {
				if (con.getSource() != null) {
					builder.append(generateIEString(con.getSource()));
					builder.append('\n');
				}
			});
			builder.deleteCharAt(builder.length() - 1);
			return new ConLabelToolTip(builder.toString());
		}
		return null;
	}

	private String createLabelText(final IInterfaceElement ie, final EList<Connection> connections) {
		final List<Connection> hiddenConnections = getHiddenConnections(connections);
		if (hiddenConnections.size() > 1) {
			// we have more then one hidden connection so we show the number
			return Integer.toString(hiddenConnections.size());
		}
		if (ie != null) {
			return generateIEString(ie);
		}
		return ""; //$NON-NLS-1$
	}


	private String generateIEString(final IInterfaceElement ie) {
		final StringBuilder builder = new StringBuilder();
		if (ie.getFBNetworkElement() != null && !isInterfaceBarElement(ie)) {
			builder.append(ie.getFBNetworkElement().getName());
			builder.append('.');
		}
		builder.append(ie.getName());
		final String iesString = builder.toString();

		if (iesString.length() > maxWidth) {
			return "..." + iesString.substring(iesString.length() - maxWidth); //$NON-NLS-1$
		}

		return iesString;
	}

	private boolean isInterfaceBarElement(final IInterfaceElement ie) {
		if (ie.getFBNetworkElement() instanceof SubApp) {
			final SubApp subapp = (SubApp) ie.getFBNetworkElement();
			return (subapp.getSubAppNetwork() != null) && subapp.getSubAppNetwork().equals(getModel().getFBNetwork());
		}
		return false;
	}

	private static List<Connection> getHiddenConnections(final EList<Connection> connections) {
		return connections.stream().filter(con -> !con.isVisible()).collect(Collectors.toList());
	}

	@Override
	public void setForegroundColor(final Color fg) {
		super.setForegroundColor(fg);
		if (isAdapterConnectionOrStructConnection()) {
			lighterColor = ColorHelper.lighter(fg);
		}
	}

	@Override
	protected void outlineShape(final Graphics g) {
		if (!isHidden()) {
			if (isAdapterConnectionOrStructConnection()) {
				drawDoublePolyline(g, getBeveledPoints());
			} else {
				g.drawPolyline(getBeveledPoints());
			}
		}
	}

	private boolean isAdapterConnectionOrStructConnection() {
		if (model instanceof DataConnection) {
			final IInterfaceElement refElement = getRefPin();
			return ((null != refElement) && (refElement.getType() instanceof StructuredType));
		}
		return (model instanceof AdapterConnection);
	}

	private IInterfaceElement getRefPin() {
		// if the connections end point fb type could not be loaded it source or
		// destination may be null
		IInterfaceElement refElement = model.getSource();
		if (null == refElement) {
			refElement = model.getDestination();
		}
		if (refElement != null) {   // during reconnect or delete both sides could be null see Bug 579040 for details
			final DataType dataType = refElement.getType();
			if ((dataType instanceof AnyType) && (dataType == IecTypes.GenericTypes.ANY)
					&& (refElement == model.getSource())) {
				// if source is of any type change to destination so that source any target struct are shown correctly
				refElement = getModel().getDestination();
			}
		}
		return refElement;
	}

	private void drawDoublePolyline(final Graphics g, final PointList beveledPoints) {
		g.drawPolyline(beveledPoints);
		g.setLineWidth(getLineWidth() / NORMAL_DOUBLE_LINE_WIDTH);
		if (g.getAbsoluteScale() >= 1) {
			g.setForegroundColor(lighterColor);
			g.drawPolyline(beveledPoints);
		}
	}

	@Override
	public Rectangle getBounds() {
		final int CLIPPING_BUFFER = 2;
		return super.getBounds().getExpanded(CLIPPING_BUFFER, CLIPPING_BUFFER);
	}

	@Override
	public void setLineWidth(final int w) {
		int width = w;
		if (isAdapterConnectionOrStructConnection()) {
			width = Math.max(w * DOUBLE_LINE_AMPLIFICATION, NORMAL_DOUBLE_LINE_WIDTH);
		}
		super.setLineWidth(width);
	}

	@Override
	protected boolean shapeContainsPoint(final int x, final int y) {
		return !isHidden()
				&& Geometry.polylineContainsPoint(getPoints(), x, y, getLineWidth() + CONNECTION_SELECTION_TOLERANCE);
	}

	private PointList getBeveledPoints() {
		final PointList beveledPoints = new PointList();

		beveledPoints.addPoint(getPoints().getFirstPoint());

		for (int i = 1; i < (getPoints().size() - 1); i++) {
			final Point before = getPoints().getPoint(i - 1);
			final Point after = getPoints().getPoint(i + 1);

			final int verDistance = Math.abs(before.y - after.y);
			final int horDistance = Math.abs(before.y - after.y);
			int bevelSize = BEND_POINT_BEVEL_SIZE;
			if (verDistance < (2 * BEND_POINT_BEVEL_SIZE)) {
				bevelSize = verDistance / 2;
			}
			if (horDistance < (2 * BEND_POINT_BEVEL_SIZE)) {
				bevelSize = horDistance / 2;
			}

			beveledPoints.addPoint(calculatedBeveledPoint(getPoints().getPoint(i), before, bevelSize));
			beveledPoints.addPoint(calculatedBeveledPoint(getPoints().getPoint(i), after, bevelSize));
		}

		beveledPoints.addPoint(getPoints().getLastPoint());
		return beveledPoints;
	}

	private static Point calculatedBeveledPoint(final Point refPoint, final Point otherPoint, final int bevelSize) {
		if (0 == (refPoint.x - otherPoint.x)) {
			return new Point(refPoint.x, refPoint.y + (((refPoint.y - otherPoint.y) > 0) ? -bevelSize : bevelSize));
		}
		return new Point(refPoint.x + (((refPoint.x - otherPoint.x) > 0) ? -bevelSize : bevelSize), refPoint.y);
	}

}
