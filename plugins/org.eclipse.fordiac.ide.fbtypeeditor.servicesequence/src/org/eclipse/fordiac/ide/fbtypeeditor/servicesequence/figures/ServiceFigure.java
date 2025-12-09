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
 *   Melanie Winter - clean up, modernize
 *******************************************************************************/

package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.FreeformLayer;
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
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;

public final class ServiceFigure extends FreeformLayeredPane {
	private static final int MIDDLE_LINE_WIDTH = 2;
	public static final String LABEL_FONT = "labelFont"; //$NON-NLS-1$

	private Label leftLabel;
	private Label rightLabel;
	private Layer serviceSequenceContainer;
	private static FontRegistry fontRegistry = null; // the registry will be disposed with the swt display that created
	// it

	public ServiceFigure() {
		setLayoutManager(new StackLayout());
		setForegroundColor(ColorConstants.blue);
		createBaseLayer();
		createInterfaceLayer();
		createServiceSequenceLayer();
	}

	public static FontRegistry getFontRegistry() {
		if (fontRegistry == null) {
			fontRegistry = createFontRegistry();
		}
		return fontRegistry;
	}

	private static FontRegistry createFontRegistry() {
		final FontRegistry newfontRegistry = new FontRegistry();
		newfontRegistry.put(LABEL_FONT, new FontData[] { new FontData("Arial", 10, SWT.NONE) //$NON-NLS-1$
		});
		return newfontRegistry;
	}

	private void createBaseLayer() {
		final Layer baseLayer = new FreeformLayer();
		baseLayer.setBorder(new MarginBorder(0, 10, 0, 10));
		final GridLayout layout = new GridLayout(3, false);
		layout.horizontalSpacing = 0;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.verticalSpacing = 0;
		baseLayer.setLayoutManager(layout);

		final Figure leftFigure = new Figure();
		final AdvancedLineBorder leftMiddleLine = new AdvancedLineBorder(PositionConstants.EAST);
		leftMiddleLine.setWidth(2 * MIDDLE_LINE_WIDTH);
		leftFigure.setBorder(leftMiddleLine);
		baseLayer.add(leftFigure);

		// final LineBorder middleLines = new LineBorder();
		final LineBorder middleLines = leftMiddleLine;
		middleLines.setWidth(MIDDLE_LINE_WIDTH);
		middleLines.setColor(ColorManager.getColor(ServiceConstants.TEXT_BLUE));

		final Figure middleFigure = new Figure();
		middleFigure.setBorder(middleLines);
		middleFigure.setBackgroundColor(ColorManager.getColor(ServiceConstants.LIGHTER_GRAY));
		middleFigure.setOpaque(true);
		baseLayer.add(middleFigure);

		final Figure rightFigure = new Figure();
		baseLayer.add(rightFigure);

		final GridLayout rightLayout = new GridLayout();
		rightFigure.setLayoutManager(rightLayout);
		rightLayout.horizontalSpacing = 0;
		rightLayout.marginWidth = 0;
		rightLayout.marginHeight = 0;
		rightLayout.verticalSpacing = 0;
		baseLayer.add(rightFigure);

		final GridData middleGridData = new GridData(SWT.NONE, SWT.FILL, false, true);
		middleGridData.widthHint = ServiceConstants.getMiddleSectionWidth();
		layout.setConstraint(middleFigure, middleGridData);
		final GridData leftGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		leftGridData.widthHint = ServiceConstants.getRightLeftSectionWidth();
		final GridData rightGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		rightGridData.widthHint = ServiceConstants.getRightLeftSectionWidth();
		layout.setConstraint(leftFigure, leftGridData);
		layout.setConstraint(rightFigure, rightGridData);

		add(baseLayer);
	}

	private void createInterfaceLayer() {
		final Layer interfaceLayer = new FreeformLayer();
		interfaceLayer.setBorder(new MarginBorder(5, 0, 0, 0));
		final GridLayout layout = new GridLayout(2, true);
		interfaceLayer.setForegroundColor(ColorManager.getColor(ServiceConstants.TEXT_BLUE));
		layout.horizontalSpacing = 0;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.verticalSpacing = 0;
		interfaceLayer.setLayoutManager(layout);

		leftLabel = createLabel(true);
		final GridData leftLabelData = new GridData(SWT.RIGHT, SWT.NONE, true, false);
		interfaceLayer.getLayoutManager().setConstraint(leftLabel, leftLabelData);
		interfaceLayer.add(leftLabel);

		rightLabel = createLabel(false);
		final GridData rightLabelData = new GridData(SWT.LEFT, SWT.NONE, true, false);
		interfaceLayer.getLayoutManager().setConstraint(rightLabel, rightLabelData);
		interfaceLayer.add(rightLabel);

		add(interfaceLayer);
	}

	private static Label createLabel(final boolean isLeftInterface) {
		final int centerMargin = 100;
		final int topMargin = 5;
		final int borderMargin = 30;
		final Label label = new Label();
		label.setFont(getFontRegistry().get(LABEL_FONT));
		if (isLeftInterface) {
			label.setLabelAlignment(PositionConstants.RIGHT);
			label.setBorder(new MarginBorder(topMargin, borderMargin, 0, centerMargin));
		} else {
			label.setLabelAlignment(PositionConstants.LEFT);
			label.setBorder(new MarginBorder(topMargin, centerMargin, 0, borderMargin));
		}
		return label;
	}

	private void createServiceSequenceLayer() {
		serviceSequenceContainer = new FreeformLayer();
		serviceSequenceContainer.setBorder(new MarginBorder(20, 10, 5, 10));
		final FlowLayout layout = new FlowLayout();
		serviceSequenceContainer.setLayoutManager(layout);
		add(serviceSequenceContainer);
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