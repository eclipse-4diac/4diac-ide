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
 *   Melanie Winter
 *     - extracted from ServiceSequenceEditPart, cleanup, allow expanding
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.Layer;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.ServiceConstants;
import org.eclipse.fordiac.ide.util.ColorManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;

public class ServiceSequenceFigure extends Layer {
	private final Label nameLabel;
	private final Label commentLabel;

	private final IFigure nameCommentFigure;

	private final Layer transactionContainer;
	private boolean isExpanded;

	public ServiceSequenceFigure(final boolean isExpanded) {
		nameLabel = new Label();
		commentLabel = new Label();
		transactionContainer = new Layer();
		nameCommentFigure = new Figure();
		setExpanded(isExpanded);
	}


	private void setExpanded(final boolean isExpanded) {
		this.isExpanded = isExpanded;
	}

	// avoid call of overridable method from constructor
	public void createVisuals() {
		final GridLayout sequenceLayout = new GridLayout();
		setLayoutManager(sequenceLayout);
		sequenceLayout.verticalSpacing = 0;

		nameCommentFigure.setLayoutManager(new GridLayout(2, true));
		nameCommentFigure.setBackgroundColor(ColorManager.getColor(ServiceConstants.LIGHT_GRAY));
		nameCommentFigure.setOpaque(true);

		final MarginBorder titleBorder = new MarginBorder(new Insets(3, 20, 3, 0));
		nameLabel.setBorder(titleBorder);
		nameLabel.setForegroundColor(ColorManager.getColor(ServiceConstants.TEXT_BLUE));
		nameLabel.setFont(new Font(Display.getDefault(), "Arial", 10, SWT.BOLD));
		nameLabel.setLabelAlignment(PositionConstants.LEFT);
		// commentLabel.setLabelAlignment(PositionConstants.RIGHT);

		commentLabel.setOpaque(true);
		commentLabel.setLabelAlignment(PositionConstants.RIGHT);
		commentLabel.setForegroundColor(ColorConstants.black);
		// nameLabel.add(commentLabel, new GridData(SWT.RIGHT, SWT.None, true, false));

		final GridData nameLayoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		nameCommentFigure.getLayoutManager().setConstraint(nameLabel, nameLayoutData);

		final GridData commentLayoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		nameCommentFigure.getLayoutManager().setConstraint(commentLabel, commentLayoutData);
		// getLayoutManager().setConstraint(commentLabel, nameLayoutData);

		nameCommentFigure.add(nameLabel);
		nameCommentFigure.add(commentLabel);


		// add(commentLabel);
		add(nameCommentFigure);
		setConstraint(nameCommentFigure, new GridData(SWT.FILL, SWT.FILL, true, true));

		final GridLayout containerLayout = new GridLayout();
		transactionContainer.setLayoutManager(containerLayout);
		containerLayout.verticalSpacing = 0;

		final GridData containerData = new GridData(SWT.FILL, SWT.NONE, true, false);
		getLayoutManager().setConstraint(transactionContainer, containerData);
		add(transactionContainer);

	}

	public Label getLabel() {
		return nameLabel;
	}

	public void setLabelText(final String name, final String comment) {
		final String sequenceName = null != name ? name : ""; //$NON-NLS-1$
		final String symbol = isExpanded ? "\u25BE  " : "\u25B8  "; //$NON-NLS-1$ //$NON-NLS-2$
		this.nameLabel.setText(symbol + sequenceName);

		this.commentLabel.setText(comment);

	}

	public Layer getTransactionContainer() {
		return transactionContainer;
	}

}