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
import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.fordiac.ide.gef.figures.HideableConnection;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.GraphicalEditPart;

public class FBNetworkConnection extends HideableConnection {

	private static int maxWidth = Activator.getDefault().getPreferenceStore()
			.getInt(DiagramPreferences.MAX_HIDDEN_CONNECTION_LABEL_SIZE);

	private static String pinLabelStyle = Activator.getDefault().getPreferenceStore()
			.getString(DiagramPreferences.PIN_LABEL_STYLE);

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

	private static List<Connection> getHiddenConnections(final EList<Connection> connections) {
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
		if (isHidden()) {
			getSourceDecoration().getLabel().setText(createDestinationLabelText());
			if ((getSourceDecoration() instanceof final GroupInterfaceConnectionLabel srcLabel)
					&& (srcLabel.getGroupFigure() == null)) {
				srcLabel.setGroupFigure(getGroupFigure(getModel().getSourceElement()));
			}
			updateSourceTooltip();

			getTargetDecoration().getLabel().setText(createSourceLabelText());
			if ((getTargetDecoration() instanceof final GroupInterfaceConnectionLabel dstLabel)
					&& (dstLabel.getGroupFigure() == null)) {
				dstLabel.setGroupFigure(getGroupFigure(getModel().getDestinationElement()));
			}
			updateTargetTooltip();
		}
	}

	private RotatableDecoration createTargetLabel() {
		FBNetworkConnectionLabel label;
		if (isSubappCrossingConnection() && isInGroup(getModel().getDestinationElement())) {
			label = new GroupInterfaceConnectionLabel(false, getGroupFigure(getModel().getDestinationElement()), this);
		} else {
			label = new FBNetworkConnectionLabel(false);
		}
		label.setBackgroundColor(getForegroundColor());
		return label;
	}

	private RotatableDecoration createSourceLabel() {
		FBNetworkConnectionLabel label;
		if (isSubappCrossingConnection() && isInGroup(getModel().getSourceElement())) {
			label = new GroupInterfaceConnectionLabel(true, getGroupFigure(getModel().getSourceElement()), this);
		} else {
			label = new FBNetworkConnectionLabel(true);
		}
		label.setBackgroundColor(getForegroundColor());
		return label;
	}

	private IFigure getGroupFigure(final FBNetworkElement sourceElement) {
		final GraphicalEditPart groupEP = (GraphicalEditPart) connEP.getViewer().getEditPartRegistry()
				.get(sourceElement.getGroup());
		return (groupEP != null) ? groupEP.getFigure() : null;
	}

	private boolean isSubappCrossingConnection() {
		final Group srcGroup = getGroup(getModel().getSourceElement());
		final Group dstGroup = getGroup(getModel().getDestinationElement());
		return ((srcGroup != null) || (dstGroup != null)) && (srcGroup != dstGroup);
	}

	private Group getGroup(final FBNetworkElement dstElement) {
		return (isInGroup(dstElement)) ? dstElement.getGroup() : null;
	}

	private boolean isInGroup(final FBNetworkElement element) {
		return (element != null) && (element.isInGroup()) && element.getFbNetwork().equals(getModel().getFBNetwork());
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
			builder.delete(0, builder.length() - maxWidth);
			builder.insert(0, "\u2026"); //$NON-NLS-1$
		}
		return builder.toString();
	}

	private IFigure createSourceLabelToolTip(final EList<Connection> connections) {
		final List<Connection> hiddenConnections = getHiddenConnections(connections);
		if (hiddenConnections.size() > 1) {
			final StringBuilder builder = new StringBuilder();
			hiddenConnections.forEach(con -> {
				if (con.getDestination() != null) {
					builder.append(generateFullIEString(con.getDestination()));
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
					builder.append(generateFullIEString(con.getSource()));
					builder.append('\n');
				}
			});
			builder.deleteCharAt(builder.length() - 1);
			return new ConLabelToolTip(builder.toString());
		}
		return null;
	}

	private StringBuilder generateFullIEString(final IInterfaceElement ie) {
		final StringBuilder builder = new StringBuilder();
		if (pinLabelStyle.equals(DiagramPreferences.PIN_LABEL_STYLE_PIN_COMMENT) && ie.getComment() != null
				&& !ie.getComment().isBlank()) {
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
