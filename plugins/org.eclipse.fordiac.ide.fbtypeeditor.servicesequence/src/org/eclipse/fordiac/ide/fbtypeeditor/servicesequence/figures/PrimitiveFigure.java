/*******************************************************************************
 * Copyright (c) 2014 fortiss GmbH
 *               2021 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Melanie Winter - added parameterLabel
 *******************************************************************************/

package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.Layer;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.ServiceConstants;
import org.eclipse.fordiac.ide.util.ColorManager;
import org.eclipse.swt.SWT;

public class PrimitiveFigure extends Layer {
	private final Label nameLabel;
	private final Label emptyLabel;
	private final Label parameterLabel;

	private final Figure centerFigure;
	private final Figure leftFigure;
	private final Figure rightFigure;

	private static final int ARROW_LENGTH = 100;

	public PrimitiveFigure(final boolean isLeftInterface, final String name, final String parameter) {
		final GridLayout mainLayout = new GridLayout(6, false);
		setLayoutManager(mainLayout);
		mainLayout.marginHeight = 0;
		mainLayout.marginWidth = 0;
		mainLayout.horizontalSpacing = 0;


		nameLabel = new Label();
		nameLabel.setForegroundColor(ColorConstants.black);
		final GridData nameLabelData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		nameLabelData.widthHint = 200;

		parameterLabel = new Label();
		parameterLabel.setForegroundColor(ColorManager.getColor(ServiceConstants.GRAY));
		final GridData parameterLabelData = new GridData(SWT.FILL, SWT.CENTER, false, false);
		parameterLabelData.widthHint = 150;

		emptyLabel = new Label();
		final GridData emptyLabelData = new GridData(SWT.FILL, SWT.NONE, false, false);
		emptyLabelData.widthHint = 500;

		final GridData arrowLeftData = new GridData(SWT.NONE, SWT.CENTER, false, false);
		arrowLeftData.widthHint = ARROW_LENGTH;
		leftFigure = new Figure();

		centerFigure = new Figure();
		final GridData spaceData = new GridData(SWT.FILL, SWT.CENTER, false, false);
		spaceData.widthHint = 55;

		final GridData arrowRightData = new GridData(SWT.NONE, SWT.CENTER, false, false);
		arrowRightData.widthHint = ARROW_LENGTH;
		rightFigure = new Figure();

		setInterfaceDirection(isLeftInterface);

		setNameLabelText(name);
		setParameterLabelText(parameter);
		setConstraint(emptyLabel, emptyLabelData);
		setConstraint(leftFigure, arrowLeftData);
		setConstraint(centerFigure, spaceData);
		setConstraint(rightFigure, arrowRightData);
		setConstraint(nameLabel, nameLabelData);
		setConstraint(parameterLabel, parameterLabelData);
	}

	public void setInterfaceDirection(final boolean interfaceDirection) {
		if (!this.getChildren().isEmpty()) {
			this.getChildren().clear();
		}
		if (interfaceDirection) {
			nameLabel.setLabelAlignment(PositionConstants.RIGHT);
			parameterLabel.setLabelAlignment(PositionConstants.LEFT);
			nameLabel.setBorder(new MarginBorder(0, 0, 0, 10));

			add(parameterLabel);
			add(nameLabel);
			add(leftFigure);
			add(centerFigure);
			add(rightFigure);
			add(emptyLabel);
		} else {
			nameLabel.setLabelAlignment(PositionConstants.LEFT);
			parameterLabel.setLabelAlignment(PositionConstants.RIGHT);
			nameLabel.setBorder(new MarginBorder(0, 10, 0, 0));

			add(emptyLabel);
			add(leftFigure);
			add(centerFigure);
			add(rightFigure);
			add(nameLabel);
			add(parameterLabel);
		}
	}

	public Label getNameLabel() {
		return nameLabel;
	}

	public void setNameLabelText(final String name) {
		this.nameLabel.setText(null != name ? name : ""); //$NON-NLS-1$
	}

	public Figure getCenterFigure() {
		return centerFigure;
	}

	public Label getParameterLabel() {
		return parameterLabel;
	}

	public void setParameterLabelText(final String parameter) {
		if ((parameter != null) && !parameter.isEmpty()) {
			this.parameterLabel.setText("(" + parameter + ")"); //$NON-NLS-1$ //$NON-NLS-2$
			this.parameterLabel.setToolTip(new Label(parameter));
		} else {
			this.parameterLabel.setText(""); //$NON-NLS-1$
		}
	}
}
