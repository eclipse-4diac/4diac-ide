/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.figures;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.fordiac.ide.gef.draw2d.AdvancedRoundedRectangle;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

public class FBTypeFigure extends Shape {
	private final Figure main = new Figure();
	private RoundedRectangle top;
	private final Figure topInputs = new Figure();
	private final Figure topOutputs = new Figure();
	private RoundedRectangle middle;
	private final Figure middleInputs = new Figure();
	private final Figure middleOutputs = new Figure();
	private RoundedRectangle bottom;
	private final Figure bottomInputs = new Figure();
	private final Figure bottomInputValuesFigure = new Figure();
	private final Figure bottomOutputs = new Figure();
	private final Figure eventInputs = new Figure();
	private final Figure eventOutputs = new Figure();
	private final Figure dataInputs = new Figure();
	private final Figure sockets = new Figure();
	private final Figure dataOutputs = new Figure();
	private final Figure plugs = new Figure();
	protected TopBorder bottomBorder;
	protected Label typeName;
	private Label versionInfoLabel;
	private FBType type;
	private ZoomManager zoomManager; 
	
	public FBTypeFigure() {
		configureRectangles();
	}
		
	private void configureRectangles() {		
		top = new AdvancedRoundedRectangle(
				PositionConstants.NORTH | PositionConstants.EAST
				| PositionConstants.WEST, zoomManager, main, true, null);		
		middle = new AdvancedRoundedRectangle(
				PositionConstants.EAST | PositionConstants.WEST, zoomManager,main, true, null);		
		bottom = new AdvancedRoundedRectangle(
				PositionConstants.SOUTH | PositionConstants.EAST
				| PositionConstants.WEST, zoomManager,main,  true, null);
	}

	public FBTypeFigure(final FBType type, ZoomManager zoomManager) {
		this.type = type;
		this.zoomManager = zoomManager;
		setBackgroundColor(ColorConstants.white);
		this.setFillXOR(true);
		configureRectangles();	
		GridLayout gridLayout = new GridLayout(1, true);
		gridLayout.verticalSpacing = 2;
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		setLayoutManager(gridLayout);
		GridLayout mainLayout = new GridLayout(3, false);
		mainLayout.marginHeight = 0;
		mainLayout.marginWidth = 0;
		mainLayout.horizontalSpacing = 0;
		mainLayout.verticalSpacing = -1;
		GridData mainLayoutData = new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL | GridData.VERTICAL_ALIGN_FILL
				| GridData.GRAB_VERTICAL);
		main.setLayoutManager(mainLayout);
		add(main);
		setConstraint(main, mainLayoutData);
		IPreferenceStore pf = Activator.getDefault().getPreferenceStore();
		int cornerDim = pf.getInt(DiagramPreferences.CORNER_DIM);
		top.setCornerDimensions(new Dimension(cornerDim, cornerDim));

		top.setCornerDimensions(new Dimension(cornerDim, cornerDim));

		GridData topLayoutData = new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL | GridData.VERTICAL_ALIGN_FILL);

		BorderLayout topLayout = new BorderLayout();

		top.setLayoutManager(topLayout);
		main.add(topInputs);
		main.add(top);
		main.add(topOutputs);
		main.setConstraint(top, topLayoutData);

		GridLayout tpl = new GridLayout(3, false);
		tpl.marginHeight = 0;

		topInputs.setLayoutManager(tpl);
		// topInputs.add(new Label("comment ..."));
		// topInputs.add(new Label("type "));
		// topInputs.add(new Label(" "));

		//		
		ToolbarLayout topInputsLayout = new ToolbarLayout(false);

		topInputsLayout.setStretchMinorAxis(true);
		eventInputs.setLayoutManager(topInputsLayout);
		eventInputs.setBorder(new MarginBorder(4, 0, 4, 0));
		//
		top.add(eventInputs);
		top.setConstraint(eventInputs, BorderLayout.LEFT);

		//			
		ToolbarLayout topOutputsLayout = new ToolbarLayout(false);

		topOutputsLayout.setStretchMinorAxis(true);
		eventOutputs.setLayoutManager(topOutputsLayout);
		eventOutputs.setMinimumSize(new Dimension(40, 18));
		eventOutputs.setBorder(new MarginBorder(4, 0, 4, 0));
		top.add(eventOutputs);
		top.setConstraint(eventOutputs, BorderLayout.RIGHT);

		Figure middleContainer = new Figure();
		BorderLayout borderLayout;
		middleContainer.setLayoutManager(borderLayout = new BorderLayout());
		borderLayout.setHorizontalSpacing(10);
		middleContainer.setBorder(new MarginBorder(0, 7, 0, 7));

		main.add(middleInputs);
		middleInputs.setBorder(new LineBorder(ColorConstants.orange));
		main.add(middleContainer);
		main.add(middleOutputs);
		middleContainer.add(middle, BorderLayout.CENTER);
		middle.setCornerDimensions(new Dimension());
		
		GridLayout middleLayout = new GridLayout(1, true);
		GridData middleLayouData = new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL);
		main.setConstraint(middleContainer, middleLayouData);

		middle.setLayoutManager(middleLayout);
		middle.setBorder(new TopBorder(getBackgroundColor(), 0));
		middleLayout.marginHeight = 0;
		middleLayout.verticalSpacing = 1;
		middle.add(typeName = new Label(type.getName() != null ? type.getName()
				: "N/D"));
		typeName.setFont(JFaceResources.getFontRegistry().getItalic(JFaceResources.DEFAULT_FONT));
		typeName.setTextAlignment(PositionConstants.CENTER);
		middle.setConstraint(typeName, new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL));

		//Version info label
		versionInfoLabel = new Label();		
		updateVersionInfoLabel();
		middle.add(versionInfoLabel);
		versionInfoLabel.setTextAlignment(PositionConstants.CENTER);
		middle.setConstraint(versionInfoLabel, new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL));

		bottom.setCornerDimensions(new Dimension(cornerDim, cornerDim));
		bottom
				.setBorder(bottomBorder = new TopBorder(getBackgroundColor(), 14 - 4));

		// GridLayout bottomLayout = new GridLayout(2, false);
		// bottomLayout.marginHeight = 4;
		// bottomLayout.marginWidth = 1;
		// bottomLayout.horizontalSpacing = 0;
		BorderLayout bottomLayout = new BorderLayout();
		bottom.setLayoutManager(bottomLayout);
		GridData bottomLayoutData = new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL | GridData.VERTICAL_ALIGN_FILL
				| GridData.GRAB_VERTICAL);
		bottomLayoutData.verticalAlignment = SWT.TOP;

		GridLayout bottomInputValuesLayout = new GridLayout();
		bottomInputValuesLayout.marginHeight = 4;
		bottomInputValuesLayout.marginWidth = 2;
		bottomInputValuesLayout.horizontalSpacing = 0;

		GridData bottomILayoutData = new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL | GridData.VERTICAL_ALIGN_FILL
				| GridData.GRAB_VERTICAL);
		bottomILayoutData.verticalAlignment = SWT.TOP;
		main.add(bottomInputs);
		main.setConstraint(bottomInputs, bottomILayoutData);
		bottomInputs.setLayoutManager(bottomInputValuesLayout);

		ToolbarLayout bottomInputValuesFigureLayout = new ToolbarLayout(false);
		bottomInputValuesFigureLayout.setStretchMinorAxis(true);

		bottomInputValuesFigure.setLayoutManager(bottomInputValuesFigureLayout);
		GridData bottomInputsFigureLayoutData = new GridData(
				GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL
						| GridData.VERTICAL_ALIGN_FILL | GridData.GRAB_VERTICAL);
		bottomInputsFigureLayoutData.verticalAlignment = SWT.TOP;
		bottomInputs.add(bottomInputValuesFigure);
		bottomInputs.setConstraint(bottomInputValuesFigure,
				bottomInputsFigureLayoutData);

		main.add(bottom);
		main.add(bottomOutputs);
		main.setConstraint(bottom, bottomLayoutData);
		
		Figure bottomInputContainer = new Figure();
		FlowLayout inputContainerLayout = new FlowLayout();
		inputContainerLayout.setHorizontal(false);
		inputContainerLayout.setStretchMinorAxis(true);		
		bottomInputContainer.setLayoutManager(inputContainerLayout);
		
		bottom.add(bottomInputContainer);
		bottom.setConstraint(bottomInputContainer, BorderLayout.LEFT);
		//		
		ToolbarLayout bottomInputsLayout = new ToolbarLayout(false);
		bottomInputsLayout.setStretchMinorAxis(true);
		dataInputs.setLayoutManager(bottomInputsLayout);
		dataInputs.setBorder(new MarginBorder(4, 0, 0, 0));		
		bottomInputContainer.add(dataInputs);		
		
		ToolbarLayout bottomSocketsLayout = new ToolbarLayout(false);
		bottomSocketsLayout.setStretchMinorAxis(true);
		sockets.setLayoutManager(bottomSocketsLayout);
		sockets.setBorder(new MarginBorder(0, 0, 4, 0));
		bottomInputContainer.add(sockets);		
		
		Figure bottomOutputContainer = new Figure();
		FlowLayout outputContainerLayout = new FlowLayout();
		outputContainerLayout.setHorizontal(false);
		outputContainerLayout.setStretchMinorAxis(true);
		outputContainerLayout.setMajorSpacing(0);
		outputContainerLayout.setMinorAlignment(FlowLayout.ALIGN_BOTTOMRIGHT);
		bottomOutputContainer.setLayoutManager(outputContainerLayout);
		
		bottom.add(bottomOutputContainer);
		bottom.setConstraint(bottomOutputContainer, BorderLayout.RIGHT);

		//
		ToolbarLayout bottomOutputsLayout = new ToolbarLayout(false);
		bottomOutputsLayout.setStretchMinorAxis(true);
		bottomOutputsLayout.setMinorAlignment(ToolbarLayout.ALIGN_BOTTOMRIGHT);
		bottomOutputsLayout.setSpacing(0);

		dataOutputs.setLayoutManager(bottomOutputsLayout);
		dataOutputs.setBorder(new MarginBorder(4, 0, 0, 0));
		
		Figure bottomDataOutputContainer = new Figure();
		BorderLayout bottomDataOutputLayout = new BorderLayout();
		bottomDataOutputContainer.setLayoutManager(bottomDataOutputLayout);
		
		bottomOutputContainer.add(bottomDataOutputContainer);
		bottomOutputContainer.setConstraint(bottomDataOutputContainer, FlowLayout.ALIGN_BOTTOMRIGHT);
		bottomDataOutputContainer.add(dataOutputs);
		bottomDataOutputContainer.setConstraint(dataOutputs, BorderLayout.RIGHT);
		
		ToolbarLayout bottomPlugsLayout = new ToolbarLayout(false);
		bottomPlugsLayout.setStretchMinorAxis(true);
		bottomPlugsLayout.setMinorAlignment(ToolbarLayout.ALIGN_BOTTOMRIGHT);
		plugs.setLayoutManager(bottomPlugsLayout);
		plugs.setBorder(new MarginBorder(0, 0, 4, 0));
		
		Figure bottomPlugsContainer = new Figure();
		BorderLayout bottomPlugsContainerLayout = new BorderLayout();
		bottomPlugsContainer.setLayoutManager(bottomPlugsContainerLayout);
		
		bottomOutputContainer.add(bottomPlugsContainer);
		bottomOutputContainer.setConstraint(bottomPlugsContainer, FlowLayout.ALIGN_BOTTOMRIGHT);
		bottomPlugsContainer.add(plugs);
		bottomPlugsContainer.setConstraint(plugs, BorderLayout.RIGHT);
	}

	public Figure getInputEvents() {
		return eventInputs;
	}

	public Figure getOutputEvents() {
		return eventOutputs;
	}

	public Figure getInputVariables() {
		return dataInputs;
	}
	
	public Figure getSockets() {
		return sockets;
	}
	
	public Figure getPlugs() {
		return plugs;
	}
	
	public Figure getOutputVariables() {
		return dataOutputs;
	}

	public Label getTypeNameLabel() {
		return typeName;
	}
	
	public void updateVersionInfoLabel(){
		VersionInfo versionInfo = null;
		if (type.getVersionInfo().size() > 0) {
			versionInfo = type.getVersionInfo().get(type.getVersionInfo().size() - 1);
		}	
		versionInfoLabel.setText(versionInfo != null ? versionInfo.getVersion()
				: "N/D");
	}

	private class TopBorder extends LineBorder {
		private int cornerDimensions = 0;

		private TopBorder(final Color color, final int cornerDimensions) {
			super(color);
			this.cornerDimensions = cornerDimensions;
		}

		@Override
		public Insets getInsets(final IFigure figure) {
			return new Insets(0, 0, 0, 0);
		}

		@Override
		public void paint(final IFigure figure, final Graphics graphics,
				final Insets insets) {
			graphics.setAntialias(SWT.ON);
			tempRect.setBounds(getPaintRectangle(figure, insets));
			if (1 == (getWidth() & 1)) {
				tempRect.width--;
				tempRect.height--;
			}
			tempRect.shrink(getWidth() / 2, getWidth() / 2);
			graphics.setLineWidth(getWidth());
			if (getColor() != null) {
				graphics.setForegroundColor(getColor());
			}
			graphics.drawLine(tempRect.x + cornerDimensions, tempRect.y, tempRect.x
					+ tempRect.width - cornerDimensions, tempRect.y);
		}
	}

	@Override
	protected void fillShape(final Graphics graphics) {
	}

	@Override
	protected void outlineShape(final Graphics graphics) {
	}
}
