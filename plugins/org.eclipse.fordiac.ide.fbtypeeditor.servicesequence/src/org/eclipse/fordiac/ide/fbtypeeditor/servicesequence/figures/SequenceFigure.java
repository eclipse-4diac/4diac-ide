/*******************************************************************************
 * Copyright (c) 2008 - 2015 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *               2021 Johannes Kepler University Linz
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
 *   Bianca Wiesmayr, Melanie Winter
 *     - extracted from ServiceSequenceEditPart, cleanup, allow expanding
 *   Felix Roithmayr
 *     - added startstate and type support
 *******************************************************************************/

package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.Layer;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.ServiceConstants;
import org.eclipse.fordiac.ide.model.ServiceSequenceTypes;
import org.eclipse.fordiac.ide.util.ColorManager;
import org.eclipse.swt.SWT;

public final class SequenceFigure extends Layer {
	private static final String TRIANGLE_RIGHTWARDS = "\u25B8  "; //$NON-NLS-1$
	private static final String TRIANGLE_DOWNWARDS = "\u25BE  ";//$NON-NLS-1$
	private static final String TYPE_POSSIBLE = "  "; //$NON-NLS-1$
	private static final String TYPE_ALWAYS = "  \u2705  "; //$NON-NLS-1$
	private static final String TYPE_FORBIDDEN = "  \u26D4  "; //$NON-NLS-1$
	private static final String TYPE_CONDITIONAL = "  \u2753  "; //$NON-NLS-1$
	private final Label nameLabel;
	private final Label commentLabel;
	private String name;
	private String comment;
	private String serviceSequenceType;
	private String startState;

	private final IFigure titleBar;

	private final Layer transactionContainer;
	private boolean isExpanded;

	public SequenceFigure(final boolean isExpanded) {
		nameLabel = new Label();
		commentLabel = new Label();
		transactionContainer = new Layer();
		titleBar = new Figure();
		setExpanded(isExpanded);
		createVisuals();
	}

	public void setExpanded(final boolean isExpanded) {
		this.isExpanded = isExpanded;
		setLabelText(name, comment, serviceSequenceType, startState);
	}

	private void createVisuals() {
		// layout of SequenceFigure
		final FlowLayout sequenceLayout = new FlowLayout();
		setLayoutManager(sequenceLayout);

		// creating title bar
		titleBar.setLayoutManager(new GridLayout(2, true));
		titleBar.setBackgroundColor(ColorManager.getColor(ServiceConstants.LIGHT_GRAY));
		titleBar.setOpaque(true);

		final MarginBorder titleBorder = new MarginBorder(new Insets(10, 20, 3, 0));
		nameLabel.setBorder(titleBorder);
		nameLabel.setForegroundColor(ColorManager.getColor(ServiceConstants.TEXT_BLUE));
		nameLabel.setFont(ServiceFigure.getFontRegistry().get(ServiceFigure.LABEL_FONT));
		nameLabel.setLabelAlignment(PositionConstants.LEFT);

		commentLabel.setOpaque(true);
		commentLabel.setLabelAlignment(PositionConstants.RIGHT);
		commentLabel.setForegroundColor(ColorConstants.black);

		final GridData nameLayoutData = new GridData(SWT.LEFT, SWT.FILL, true, true);
		titleBar.getLayoutManager().setConstraint(nameLabel, nameLayoutData);

		final GridData commentLayoutData = new GridData(SWT.RIGHT, SWT.FILL, true, true);
		titleBar.getLayoutManager().setConstraint(commentLabel, commentLayoutData);
		titleBar.add(nameLabel);
		titleBar.add(commentLabel);

		add(titleBar);
		setConstraint(titleBar, new GridData(SWT.FILL, SWT.FILL, true, true));

		// container for service transactions
		final FlowLayout containerLayout = new FlowLayout();
		transactionContainer.setLayoutManager(containerLayout);

		add(transactionContainer);

	}

	public Label getNameLabel() {
		return nameLabel;
	}

	public Label getCommentLabel() {
		return commentLabel;
	}

	public void setLabelText(final String name, final String comment, final String serviceSequenceType,
			final String startState) {
		final String sequenceName = null != name ? name : ""; //$NON-NLS-1$
		this.name = sequenceName;
		final String sequenceType = null != serviceSequenceType ? serviceSequenceType : ""; //$NON-NLS-1$
		this.serviceSequenceType = sequenceType;
		final String sequenceStartState = null != startState ? startState : ""; //$NON-NLS-1$
		this.startState = sequenceStartState;
		final String symbol = isExpanded ? TRIANGLE_DOWNWARDS : TRIANGLE_RIGHTWARDS;
		final String icon = sequenceType.equals(ServiceSequenceTypes.ALWAYS) ? TYPE_ALWAYS
				: sequenceType.equals(ServiceSequenceTypes.FORBIDDEN) ? TYPE_FORBIDDEN
						: sequenceType.equals(ServiceSequenceTypes.CONDITIONAL) ? TYPE_CONDITIONAL : TYPE_POSSIBLE;
		this.nameLabel.setText(symbol + sequenceName + icon + sequenceStartState);

		final String serviceComment = comment != null ? comment : ""; //$NON-NLS-1$
		this.comment = comment;
		this.commentLabel.setText(serviceComment);

		this.commentLabel.setToolTip(new Label(serviceComment));

	}

	public Layer getTransactionContainer() {
		return transactionContainer;
	}

}