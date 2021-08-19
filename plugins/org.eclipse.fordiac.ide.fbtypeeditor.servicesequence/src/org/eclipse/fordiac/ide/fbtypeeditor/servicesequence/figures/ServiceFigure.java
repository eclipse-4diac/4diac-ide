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
 *   Bianca Wiesmayr - extracted from SequenceRootEditPart
 *******************************************************************************/

package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayeredPane;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.Layer;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.ServiceConstants;
import org.eclipse.fordiac.ide.gef.draw2d.AdvancedLineBorder;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.fordiac.ide.util.ColorManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;

public class ServiceFigure extends FreeformLayeredPane {
	private Figure leftFigure;
	private Figure middleFigure;
	private Figure rightFigure;
	private Label leftLabel;
	private Label rightLabel;
	private Layer serviceSequenceContainer;

	private static final int MIDDLE_LINE_WIDTH = 5;


	public ServiceFigure() {
		// nothing to do here, call createVisuals() manually to avoid call of overridable method from constructor
	}

	public void createVisuals() {
		setLayoutManager(new StackLayout());
		setForegroundColor(ColorConstants.blue);
		createBaseLayer();
		createInterfaceLayer();
		createServiceSequenceLayer();
	}

	private void createBaseLayer() {
		final Layer baseLayer = new Layer();
		baseLayer.setBorder(new MarginBorder(0, 10, 0, 10));
		final GridLayout layout = new GridLayout(3, false);
		layout.horizontalSpacing = 0;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.verticalSpacing = 0;
		baseLayer.setLayoutManager(layout);

		final LineBorder middleLines = new AdvancedLineBorder(PositionConstants.EAST);
		middleLines.setWidth(MIDDLE_LINE_WIDTH);
		middleLines.setColor(ColorManager.getColor(ServiceConstants.TEXT_BLUE));

		leftFigure = new Figure();
		final GridLayout leftLayout = new GridLayout();
		leftFigure.setLayoutManager(leftLayout);
		leftLayout.horizontalSpacing = 0;
		leftFigure.setBorder(middleLines);
		leftLayout.marginWidth = 0;
		leftLayout.marginHeight = 0;
		leftLayout.verticalSpacing = 0;
		baseLayer.add(leftFigure);

		middleFigure = new Figure();
		middleFigure.setPreferredSize(150, 0);
		middleFigure.setBorder(middleLines);
		middleFigure.setBackgroundColor(ColorManager.getColor(ServiceConstants.LIGHTER_GRAY));
		middleFigure.setOpaque(true);
		baseLayer.add(middleFigure);

		rightFigure = new Figure();
		final GridLayout rightLayout = new GridLayout();
		rightFigure.setLayoutManager(rightLayout);
		rightLayout.horizontalSpacing = 0;
		rightLayout.marginWidth = 0;
		rightLayout.marginHeight = 0;
		rightLayout.verticalSpacing = 0;
		baseLayer.add(rightFigure);

		layout.setConstraint(middleFigure, new GridData(SWT.NONE, SWT.FILL, false, true));
		layout.setConstraint(leftFigure, new GridData(SWT.FILL, SWT.FILL, true, true));
		layout.setConstraint(rightFigure, new GridData(SWT.FILL, SWT.FILL, true, true));

		add(baseLayer);
	}

	private void createInterfaceLayer() {
		final Layer interfaceLayer = new Layer();
		interfaceLayer.setBorder(new MarginBorder(5, 0, 0, 0));
		final GridLayout layout = new GridLayout(3, false);
		interfaceLayer.setForegroundColor(ColorManager.getColor(ServiceConstants.TEXT_BLUE));
		layout.horizontalSpacing = 0;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.verticalSpacing = 0;
		interfaceLayer.setLayoutManager(layout);

		leftLabel = new Label();
		leftLabel.setLabelAlignment(PositionConstants.RIGHT);
		leftLabel.setBorder(new MarginBorder(5, 0, 0, 0));
		leftLabel.setFont(new Font(Display.getDefault(), "Arial", 10, SWT.NONE));

		final GridData leftLabelData = new GridData(SWT.RIGHT, SWT.NONE, true, false);
		interfaceLayer.getLayoutManager().setConstraint(leftLabel, leftLabelData);
		interfaceLayer.add(leftLabel);

		final Label fillerLabel = new Label();
		fillerLabel.setBorder(new MarginBorder(5, 100, 0, 110));
		fillerLabel.setLabelAlignment(PositionConstants.CENTER);
		final GridData fillerLabelData = new GridData(SWT.CENTER, SWT.NONE, false, false);
		interfaceLayer.getLayoutManager().setConstraint(fillerLabel, fillerLabelData);
		interfaceLayer.add(fillerLabel);

		rightLabel = new Label();
		rightLabel.setFont(new Font(Display.getDefault(), "Arial", 10, SWT.NONE));
		rightLabel.setLabelAlignment(PositionConstants.LEFT);
		rightLabel.setBorder(new MarginBorder(5, 0, 0, 0));

		final GridData rightLabelData = new GridData(SWT.LEFT, SWT.NONE, true, false);
		interfaceLayer.getLayoutManager().setConstraint(rightLabel, rightLabelData);
		interfaceLayer.add(rightLabel);

		add(interfaceLayer);
	}

	private void createServiceSequenceLayer() {
		serviceSequenceContainer = new Layer();
		serviceSequenceContainer.setBorder(new MarginBorder(20, 10, 5, 10));
		final GridLayout layout = new GridLayout();
		layout.horizontalSpacing = 0;
		layout.marginWidth = 0;
		serviceSequenceContainer.setLayoutManager(layout);
		add(serviceSequenceContainer);
	}

	public Figure getLeftFigure() {
		return leftFigure;
	}

	public Figure getMiddleFigure() {
		return middleFigure;
	}

	public Figure getRightFigure() {
		return rightFigure;
	}

	public Label getLeftLabel() {
		return leftLabel;
	}

	public Label getRightLabel() {
		return rightLabel;
	}

	public Layer getServiceSequenceContainer() {
		return serviceSequenceContainer;
	}

	public void setLeftLabelText(final String name, final String comment) {
		leftLabel.setText(null != name ? name : ""); //$NON-NLS-1$
		leftLabel.setIcon(FordiacImage.ICON_LEFT_INPUT_PRIMITIVE.getImage());
		leftLabel.setTextPlacement(PositionConstants.WEST);
		if ((comment != null) && !comment.isEmpty()) {
			leftLabel.setToolTip(new Label(comment));
		}
	}

	public void setRightLabelText(final String name, final String comment) {
		rightLabel.setText(null != name ? name : ""); //$NON-NLS-1$
		rightLabel.setIcon(FordiacImage.ICON_RIGHT_OUTPUT_PRIMITIVE.getImage());
		rightLabel.setTextPlacement(PositionConstants.EAST);

		if ((comment != null) && !comment.isEmpty()) {
			rightLabel.setToolTip(new Label(comment));
		}
	}
}