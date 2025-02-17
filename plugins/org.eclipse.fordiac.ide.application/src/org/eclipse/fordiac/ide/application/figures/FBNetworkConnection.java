/*******************************************************************************
 * Copyright (c) 2014, 2024 Profactor GbmH, fortiss GmbH,
 *                          Johannes Kepler University Linz
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
package org.eclipse.fordiac.ide.application.figures;

import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.ParagraphTextLayout;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.gef.figures.HideableConnection;
import org.eclipse.fordiac.ide.gef.preferences.GefPreferenceConstants;
import org.eclipse.fordiac.ide.model.edit.helper.CommentHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

public class FBNetworkConnection extends HideableConnection {

	private static final String THREE_DOTS = "\u2026"; //$NON-NLS-1$

	private static int maxWidth = GefPreferenceConstants.STORE
			.getInt(GefPreferenceConstants.MAX_HIDDEN_CONNECTION_LABEL_SIZE);

	private static String pinLabelStyle = GefPreferenceConstants.STORE
			.getString(GefPreferenceConstants.PIN_LABEL_STYLE);

	private final ConnectionEditPart connEP;

	public FBNetworkConnection(final ConnectionEditPart connEP) {
		this.connEP = connEP;
		super.setModel(connEP.getModel());
	}

	@Override
	public FBNetworkConnectionLabel getSourceDecoration() {
		return (FBNetworkConnectionLabel) super.getSourceDecoration();
	}

	@Override
	public FBNetworkConnectionLabel getTargetDecoration() {
		return (FBNetworkConnectionLabel) super.getTargetDecoration();
	}

	static List<Connection> getHiddenConnections(final EList<Connection> connections) {
		return connections.stream().filter(con -> !con.isVisible()).toList();
	}

	private boolean isInterfaceBarElement(final IInterfaceElement ie) {
		if (ie.getFBNetworkElement() instanceof final SubApp subapp) {
			return (subapp.getSubAppNetwork() != null) && subapp.getSubAppNetwork().equals(getModel().getFBNetwork());
		}
		return false;
	}

	@Override
	public void handleVisibilityChange(final boolean hidden) {
		if (hidden) {
			setSourceDecoration(createSourceLabel());
			setTargetDecoration(createTargetLabel());
			updateConLabels();
		} else {
			setSourceDecoration(null);
			setTargetDecoration(null);
		}
	}

	@Override
	public void paint(final Graphics graphics) {
		if (isHidden()) {
			if (getSourceDecoration() != null) {
				getSourceDecoration().paint(graphics);
			}
			if (getTargetDecoration() != null) {
				getTargetDecoration().paint(graphics);
			}
		} else {
			if (getLocalForegroundColor() != null) {
				graphics.setForegroundColor(getLocalForegroundColor());
			}
			paintFigure(graphics);
			paintBorder(graphics);
		}
	}

	@Override
	protected void outlineShape(final Graphics g) {
		if (!isHidden()) {
			super.outlineShape(g);
		}
	}

	@Override
	protected boolean shapeContainsPoint(final int x, final int y) {
		return !isHidden() && super.shapeContainsPoint(x, y);
	}

	public void updateConLabels() {
		if (getSourceDecoration() != null) {
			getSourceDecoration().getLabel().setText(createDestinationLabelText());
			updateSourceTooltip();
		}
		if (getTargetDecoration() != null) {
			getTargetDecoration().getLabel().setText(createSourceLabelText());
			updateTargetTooltip();
		}
	}

	private RotatableDecoration createTargetLabel() {
		FBNetworkConnectionLabel label = null;
		if (showLabel(getModel().getDestinationElement())) {
			label = new FBNetworkConnectionLabel(false);
			label.setBackgroundColor(getForegroundColor());
		}
		return label;
	}

	private RotatableDecoration createSourceLabel() {
		FBNetworkConnectionLabel label = null;
		if (showLabel(getModel().getSourceElement())) {
			label = new FBNetworkConnectionLabel(true);
			label.setBackgroundColor(getForegroundColor());
		}
		return label;
	}

	private static boolean showLabel(final FBNetworkElement toCheck) {
		return !((toCheck instanceof final SubApp subApp) && subApp.isUnfolded());
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
		final StringBuilder builder = generateFullIEString(ie);
		if (builder.length() > maxWidth) {
			switch (pinLabelStyle) {
			case GefPreferenceConstants.PIN_LABEL_STYLE_PIN_COMMENT: {
				if (CommentHelper.hasComment(ie)) {
					builder.delete(maxWidth, builder.length()); // start inclusive, end exclusive
					builder.insert(maxWidth, THREE_DOTS);
				} else {
					builder.delete(0, builder.length() - maxWidth);
					builder.insert(0, THREE_DOTS);
				}

				break;
			}
			case GefPreferenceConstants.PIN_LABEL_STYLE_PIN_NAME, GefPreferenceConstants.PIN_LABEL_STYLE_SRC_PIN_NAME: {
				builder.delete(0, builder.length() - maxWidth);
				builder.insert(0, THREE_DOTS);
				break;
			}
			default:
				break;
			}
		}
		return builder.toString();
	}

	private static IFigure createSourceLabelToolTip(final EList<Connection> connections) {
		final List<Connection> hiddenConnections = getHiddenConnections(connections);
		if (!hiddenConnections.isEmpty()) {
			final StringBuilder builder = new StringBuilder();
			hiddenConnections.forEach(con -> {
				if (con.getDestination() != null) {
					// < block instance name >.< pin name >
					final FBNetworkElement destinationElement = con.getDestinationElement();
					if (destinationElement != null) {
						builder.append(destinationElement.getName() + "."); //$NON-NLS-1$
					}
					builder.append(con.getDestination().getName());
					builder.append(System.lineSeparator());

					// < pin comment >
					if (CommentHelper.hasComment(con.getDestination())) {
						builder.append(con.getDestination().getComment());
						builder.append(System.lineSeparator());
					}

					// < name of the group, where the block of the connected pin is located >
					if (destinationElement != null && destinationElement.isInGroup()) {
						builder.append(destinationElement.getGroup().getName());
						builder.append(System.lineSeparator());
					}
				}
				builder.append(System.lineSeparator());
			});
			return new ConLabelToolTip(builder.toString().trim());
		}
		return null;
	}

	private IFigure createDstLabelToolTip(final EList<Connection> connections) {
		final List<Connection> hiddenConnections = getHiddenConnections(connections);
		if (!hiddenConnections.isEmpty()) {
			final StringBuilder builder = new StringBuilder();
			hiddenConnections.forEach(con -> {
				if (con.getSource() != null) {
					final IInterfaceElement ie = con.getSource();
					final FBNetworkElement sourceElement = ie.getFBNetworkElement();
					if (sourceElement != null && !isInterfaceBarElement(ie)) {
						builder.append(sourceElement.getName());
						builder.append('.');
					}
					builder.append(ie.getName());
					builder.append(System.lineSeparator());
					if (CommentHelper.hasComment(ie)) {
						builder.append(ie.getComment());
						builder.append(System.lineSeparator());
					}
					if (sourceElement != null && sourceElement.isInGroup()) {
						builder.append(sourceElement.getGroup().getName());
						builder.append(System.lineSeparator());
					}
					builder.append(System.lineSeparator());
				}
			});
			return new ConLabelToolTip(builder.toString().trim());
		}
		return null;
	}

	private StringBuilder generateFullIEString(final IInterfaceElement ie) {
		final StringBuilder builder = new StringBuilder();
		if (pinLabelStyle.equals(GefPreferenceConstants.PIN_LABEL_STYLE_PIN_COMMENT) && CommentHelper.hasComment(ie)) {
			builder.append(ie.getComment());
		} else {
			if (ie.getFBNetworkElement() != null && !isInterfaceBarElement(ie)) {
				builder.append(ie.getFBNetworkElement().getName());
				builder.append('.');
			}
			builder.append(ie.getName());
		}
		return builder;
	}

	int getMaxFanOutLabelWidth() {
		final InterfaceEditPart source = (InterfaceEditPart) connEP.getSource();
		return ((List<ConnectionEditPart>) source.getSourceConnections()).stream()
				.filter(conn -> !conn.getModel().isVisible())
				.mapToInt(ep -> ep.getFigure().getSourceDecoration().getLabel().getBounds().width).max().orElse(0);
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

}
