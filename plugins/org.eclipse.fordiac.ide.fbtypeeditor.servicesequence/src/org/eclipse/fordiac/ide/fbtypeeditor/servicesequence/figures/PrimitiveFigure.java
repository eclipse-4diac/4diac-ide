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
 *   Melanie Winter - added parameter figure
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.Layer;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.swt.SWT;

public class PrimitiveFigure extends Layer {
	private final Label nameLabel;
	private final Label emptyLabel;
	private final Figure centerFigure;
	private final Figure leftFigure;
	private final Figure rightFigure;
	private final Figure parameterFigure;

	public PrimitiveFigure(final boolean isLeftInterface, final String name) {
		final GridLayout mainLayout = new GridLayout(6, false);
		setLayoutManager(mainLayout);
		mainLayout.marginHeight = 0;
		mainLayout.marginWidth = 0;
		mainLayout.horizontalSpacing = 0;

		centerFigure = new Figure();
		final GridData spaceData = new GridData();
		spaceData.widthHint = 37;

		nameLabel = new Label();
		final GridData nameLabelData = new GridData(SWT.FILL, SWT.NONE, true, false);
		nameLabelData.widthHint = 100;

		emptyLabel = new Label();
		final GridData emptyLabelData = new GridData(SWT.FILL, SWT.NONE, true, false);
		emptyLabelData.widthHint = 100;

		final GridData arrowLeftData = new GridData();
		arrowLeftData.widthHint = 50;
		leftFigure = new Figure();


		final GridData arrowRightData = new GridData();
		arrowRightData.widthHint = 50;
		rightFigure = new Figure();

		final GridData parameterData = new GridData();
		parameterData.widthHint = 50;
		parameterFigure = new Figure();
		parameterFigure.setLayoutManager(new GridLayout());

		setInterfaceDirection(isLeftInterface);

		nameLabel.setText(name);
		setConstraint(emptyLabel, emptyLabelData);
		setConstraint(centerFigure, spaceData);
		setConstraint(leftFigure, arrowLeftData);
		setConstraint(rightFigure, arrowRightData);
		setConstraint(nameLabel, nameLabelData);
		setConstraint(parameterFigure, parameterData);
	}

	public void setInterfaceDirection(final boolean interfaceDirection) {
		if (!this.getChildren().isEmpty()) {
			this.getChildren().clear();
		}
		if (interfaceDirection) {
			nameLabel.setLabelAlignment(PositionConstants.RIGHT);
			add(nameLabel);
			add(leftFigure);
			add(centerFigure);
			add(rightFigure);
			add(emptyLabel);
			add(parameterFigure);
		} else {
			nameLabel.setLabelAlignment(PositionConstants.LEFT);
			add(emptyLabel);
			add(leftFigure);
			add(centerFigure);
			add(rightFigure);
			add(nameLabel);
			add(parameterFigure);
		}
	}

	public Label getLabel() {
		return nameLabel;
	}

	public void setLabelText(final String name) {
		this.nameLabel.setText(null != name ? name : ""); //$NON-NLS-1$
	}

	public Figure getCenterFigure() {
		return centerFigure;
	}

	public Figure getParameterFigure() {
		return parameterFigure;
	}
}
