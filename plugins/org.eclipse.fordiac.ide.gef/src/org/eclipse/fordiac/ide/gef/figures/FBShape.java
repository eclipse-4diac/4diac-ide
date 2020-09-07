/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 				 2019 - 2020 Johannes Kepler University Linz
 * 				 2020        Primetals Technologies Germany GmbH
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
 *   Alois Zoitl - moved openEditor helper function to EditorUtils
 *               - added diagram font preference
 *   			 - separated FBNetworkElement from instance name for better
 *                 direct editing of instance names
 *               - extracted common FB shape for interface and fbn editors
 *   Bianca Wiesmayr - edited appearance of FBs
 *   Daniel Lindhuber - changed layout of top part
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.figures;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.OrderedLayout;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.fordiac.ide.gef.Messages;
import org.eclipse.fordiac.ide.gef.draw2d.AdvancedRoundedRectangle;
import org.eclipse.fordiac.ide.gef.draw2d.UnderlineAlphaLabel;
import org.eclipse.fordiac.ide.gef.listeners.IFontUpdateListener;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.fordiac.ide.model.edit.providers.ResultListLabelProvider;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceGetter;
import org.eclipse.fordiac.ide.util.ColorHelper;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

public class FBShape extends Shape implements IFontUpdateListener {

	/** The top. */
	private RoundedRectangle top;

	/** The middle. */
	private AdvancedRoundedRectangle middle;

	/** The bottom. */
	private AdvancedRoundedRectangle bottom;

	/** The event inputs. */
	private final Figure eventInputs = new Figure();

	/** The event outputs. */
	private final Figure eventOutputs = new Figure();

	/** The data inputs. */
	private final Figure dataInputs = new Figure();

	/** The sockets. */
	private final Figure sockets = new Figure();

	/** The data outputs. */
	private final Figure dataOutputs = new Figure();

	/** The plugs. */
	private final Figure plugs = new Figure();

	private UnderlineAlphaLabel typeLabel;

	public FBShape(FBType fbType) {
		configureMainFigure();
		createFBFigureShape(fbType);
		setTypeLabelFont();
	}

	/**
	 * Gets the event inputs.
	 *
	 * @return the event inputs
	 */
	public Figure getEventInputs() {
		return eventInputs;
	}

	/**
	 * Gets the event outputs.
	 *
	 * @return the event outputs
	 */
	public Figure getEventOutputs() {
		return eventOutputs;
	}

	/**
	 * Gets the data inputs.
	 *
	 * @return the data inputs
	 */
	public Figure getDataInputs() {
		return dataInputs;
	}

	public Figure getSockets() {
		return sockets;
	}

	/**
	 * Gets the data outputs.
	 *
	 * @return the data outputs
	 */
	public Figure getDataOutputs() {
		return dataOutputs;
	}

	public Figure getPlugs() {
		return plugs;
	}

	public UnderlineAlphaLabel getTypeLabel() {
		return typeLabel;
	}

	public RoundedRectangle getTop() {
		return top;
	}

	public AdvancedRoundedRectangle getMiddle() {
		return middle;
	}

	public AdvancedRoundedRectangle getBottom() {
		return bottom;
	}

	@Override
	public void setAlpha(int value) {
		super.setAlpha(value);

		bottom.setAlpha(value);
		top.setAlpha(value);
		getMiddle().setAlpha(value);

		if (getTypeLabel() != null) {
			getTypeLabel().setAlpha(value);
		}
	}

	@Override
	public void updateFonts() {
		setTypeLabelFont();
		invalidateTree();
		revalidate();
	}

	private void setTypeLabelFont() {
		typeLabel.setFont(JFaceResources.getFontRegistry().getItalic(PreferenceConstants.DIAGRAM_FONT));
	}

	@Override
	protected void fillShape(Graphics graphics) {
		// not used
	}

	@Override
	protected void outlineShape(Graphics graphics) {
		// not used
	}

	private void configureMainFigure() {
		setFillXOR(false);
		setOpaque(false);

		GridLayout mainLayout = new GridLayout(1, true);
		mainLayout.marginHeight = 0;
		mainLayout.marginWidth = 0;
		mainLayout.horizontalSpacing = 0;
		mainLayout.verticalSpacing = -1;
		setLayoutManager(mainLayout);
	}

	private void createFBFigureShape(final FBType fbType) {
		Color borderColor = getBorderColor(fbType);

		Figure fbFigureContainer = createFigureContainer();
		createFBTop(fbFigureContainer, DiagramPreferences.CORNER_DIM, borderColor);
		configureFBMiddle(fbType, fbFigureContainer, borderColor);
		createFBBottom(fbFigureContainer, DiagramPreferences.CORNER_DIM, borderColor);
	}

	private void createFBBottom(Figure fbFigureContainer, int cornerDim, Color borderColor) {
		bottom = new AdvancedRoundedRectangle(PositionConstants.SOUTH | PositionConstants.EAST | PositionConstants.WEST,
				borderColor);
		bottom.setCornerDimensions(new Dimension(cornerDim, cornerDim));
		GridLayout bottomLayout = new GridLayout(3, false);
		bottomLayout.marginHeight = 4;
		bottomLayout.marginWidth = 0;
		bottomLayout.horizontalSpacing = 0;
		bottomLayout.verticalSpacing = 0;
		bottom.setLayoutManager(bottomLayout);

		GridData bottomLayoutData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL
				| GridData.VERTICAL_ALIGN_FILL | GridData.GRAB_VERTICAL);
		bottomLayoutData.verticalAlignment = SWT.TOP;

		fbFigureContainer.add(bottom);
		fbFigureContainer.setConstraint(bottom, bottomLayoutData);

		setBottomIOs(bottom);
	}


	private void configureFBMiddle(final FBType fbType, Figure fbFigureContainer, Color borderColor) {
		Figure middleContainer = new Figure();
		BorderLayout borderLayout = new BorderLayout();
		middleContainer.setLayoutManager(borderLayout);
		borderLayout.setHorizontalSpacing(10);
		middleContainer.setBorder(new MarginBorder(0, 7, 0, 7));

		fbFigureContainer.add(middleContainer);
		GridData middleLayouData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL);
		fbFigureContainer.setConstraint(middleContainer, middleLayouData);

		setupTypeNameAndVersion(fbType, middleContainer, borderColor);
	}

	private void createFBTop(Figure fbFigureContainer, int cornerDim, Color borderColor) {
		top = new AdvancedRoundedRectangle(PositionConstants.NORTH | PositionConstants.EAST | PositionConstants.WEST,
				borderColor);
		top.setCornerDimensions(new Dimension(cornerDim, cornerDim));

		GridLayout topLayout = new GridLayout(3, false);
		topLayout.marginHeight = 4;
		topLayout.marginWidth = 0;
		topLayout.horizontalSpacing = 0;
		topLayout.verticalSpacing = 0;
		top.setLayoutManager(topLayout);

		fbFigureContainer.add(top);
		GridData topLayoutData = new GridData(
				GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL | GridData.VERTICAL_ALIGN_FILL);
		fbFigureContainer.setConstraint(top, topLayoutData);

		setupTopIOs(top);
	}

	private Figure createFigureContainer() {
		Figure fbFigureContainer = new Figure();
		add(fbFigureContainer);
		setConstraint(fbFigureContainer, new GridData(GridData.HORIZONTAL_ALIGN_CENTER));

		GridLayout gridLayout = new GridLayout(1, true);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		gridLayout.horizontalSpacing = 0;
		gridLayout.verticalSpacing = -1;
		fbFigureContainer.setLayoutManager(gridLayout);
		return fbFigureContainer;
	}

	private void setupTopIOs(IFigure parent) {
		ToolbarLayout topInputsLayout = new ToolbarLayout(false);
		GridData topInputsLayoutData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL
				| GridData.VERTICAL_ALIGN_FILL | GridData.GRAB_VERTICAL);
		eventInputs.setLayoutManager(topInputsLayout);
		//
		parent.add(eventInputs);
		parent.setConstraint(eventInputs, topInputsLayoutData);

		//
		ToolbarLayout topOutputsLayout = new ToolbarLayout(false);
		GridData topOutputsLayoutData = new GridData(GridData.HORIZONTAL_ALIGN_END | GridData.GRAB_HORIZONTAL
				| GridData.VERTICAL_ALIGN_FILL | GridData.GRAB_VERTICAL);
		topOutputsLayout.setMinorAlignment(OrderedLayout.ALIGN_BOTTOMRIGHT);
		eventOutputs.setLayoutManager(topOutputsLayout);
		parent.add(eventOutputs);
		parent.setConstraint(eventOutputs, topOutputsLayoutData);
	}

	private void setBottomIOs(IFigure parent) {
		Figure bottomInputArea = new Figure();
		bottomInputArea.setLayoutManager(new ToolbarLayout(false));

		GridData bottomInputsLayoutData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL
				| GridData.VERTICAL_ALIGN_FILL | GridData.GRAB_VERTICAL);
		bottomInputsLayoutData.verticalAlignment = SWT.TOP;

		parent.add(bottomInputArea);
		parent.setConstraint(bottomInputArea, bottomInputsLayoutData);

		dataInputs.setLayoutManager(new ToolbarLayout(false));
		bottomInputArea.add(dataInputs);

		sockets.setLayoutManager(new ToolbarLayout(false));
		bottomInputArea.add(sockets);

		Figure bottomOutputArea = new Figure();
		bottomOutputArea.setLayoutManager(new ToolbarLayout(false));
		((ToolbarLayout) bottomOutputArea.getLayoutManager()).setMinorAlignment(OrderedLayout.ALIGN_BOTTOMRIGHT);

		GridData bottomOutputsLayoutData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL
				| GridData.VERTICAL_ALIGN_FILL | GridData.GRAB_VERTICAL);
		parent.add(bottomOutputArea);
				parent.setConstraint(bottomOutputArea, bottomOutputsLayoutData);

				dataOutputs.setLayoutManager(new ToolbarLayout(false));
				((ToolbarLayout) dataOutputs.getLayoutManager()).setMinorAlignment(OrderedLayout.ALIGN_BOTTOMRIGHT);
				bottomOutputArea.add(dataOutputs);

				plugs.setLayoutManager(new ToolbarLayout(false));
				((ToolbarLayout) plugs.getLayoutManager()).setMinorAlignment(OrderedLayout.ALIGN_BOTTOMRIGHT);
				bottomOutputArea.add(plugs);
	}

	protected void createContainedFigure(IFigure parent) {
		// potential hook for Figure representing FB contents
	}

	protected void setupTypeNameAndVersion(final FBType type, Figure container, Color borderColor) {
		middle = new AdvancedRoundedRectangle(PositionConstants.EAST | PositionConstants.WEST, borderColor);

		container.add(middle, BorderLayout.CENTER);
		middle.setCornerDimensions(new Dimension());

		GridLayout middleLayout = new GridLayout(1, true);
		middleLayout.marginHeight = 2;
		middleLayout.verticalSpacing = 1;

		middle.setLayoutManager(middleLayout);

		String typeName = (null != type) ? type.getName() : Messages.FBFigure_TYPE_NOT_SET;

		typeLabel = new UnderlineAlphaLabel(null != typeName ? typeName : FordiacMessages.ND);
		typeLabel.setTextAlignment(PositionConstants.CENTER);
		typeLabel.setOpaque(false);
		typeLabel.setIcon(ResultListLabelProvider.getTypeImage(type));
		middle.add(typeLabel);
		middle.setConstraint(typeLabel, new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL));
	}

	@Override
	public void setBackgroundColor(Color bg) {
		// set border color
		super.setBackgroundColor(bg);
		if (bg == null) {
			((AdvancedRoundedRectangle) top).setBorderColor(getLocalForegroundColor());
			middle.setBorderColor(getLocalForegroundColor());
			bottom.setBorderColor(getLocalForegroundColor());
		} else {
			Color darkerColor = ColorHelper.darker(bg);
			((AdvancedRoundedRectangle) top).setBorderColor(darkerColor);
			middle.setBorderColor(darkerColor);
			bottom.setBorderColor(darkerColor);
		}
	}

	private static Color getBorderColor(LibraryElement type) {
		if (type instanceof AdapterFBType) {
			return PreferenceGetter.getColor(PreferenceConstants.P_ADAPTER_CONNECTOR_COLOR);
		}
		return ColorConstants.gray;
	}

}
