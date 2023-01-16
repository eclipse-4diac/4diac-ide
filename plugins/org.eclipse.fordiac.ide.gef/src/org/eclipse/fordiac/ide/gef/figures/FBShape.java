/*******************************************************************************
 * Copyright (c) 2008, 2021 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 *                          Johannes Kepler University Linz,
 *                          Primetals Technologies Germany GmbH
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
 *   Alois Zoitl - Added shadow border, removed sharp border
 *               - clean-up to make expanded subapp nicer
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.figures;

import org.eclipse.draw2d.AbstractBackground;
import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.OrderedLayout;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.fordiac.ide.gef.Messages;
import org.eclipse.fordiac.ide.gef.draw2d.UnderlineAlphaLabel;
import org.eclipse.fordiac.ide.gef.listeners.IFontUpdateListener;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.fordiac.ide.model.edit.providers.ResultListLabelProvider;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;

public class FBShape extends Shape implements IFontUpdateListener {

	private static final String TYPE_TRUNCATION_STRING = "\u2026"; //$NON-NLS-1$

	private static final int INPUT_OUTPUT_INTERLEAVE = 7;

	private static final int FB_NOTCH_INSET = 9;

	private Figure fbFigureContainer;

	/** The top. */
	private RoundedRectangle top;

	Figure middleContainer;

	/** The middle. */
	private RectangleFigure middle;

	/** The bottom. */
	private RoundedRectangle bottom;

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

	/** The sockets. */
	private final Figure errorMarkerInput = new Figure();

	/** The data outputs. */
	private final Figure errorMarkerOutput = new Figure();

	/** The plugs. */
	private final Figure plugs = new Figure();

	private UnderlineAlphaLabel typeLabel;

	private static int maxWidth = -1;

	private Figure bottomInputArea;

	private Figure bottomOutputArea;

	public FBShape(final FBType fbType) {
		configureMainFigure();
		createFBFigureShape(fbType);
		setTypeLabelFont();
		setBorder(new FBShapeShadowBorder());
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

	public Figure getErrorMarkerInput() {
		return errorMarkerInput;
	}

	public Figure getErrorMarkerOutput() {
		return errorMarkerOutput;
	}

	public Figure getPlugs() {
		return plugs;
	}

	public UnderlineAlphaLabel getTypeLabel() {
		return typeLabel;
	}

	protected RoundedRectangle getTop() {
		return top;
	}

	public Shape getMiddle() {
		return middle;
	}

	protected RoundedRectangle getBottom() {
		return bottom;
	}

	protected final Figure getMiddleContainer() {
		return middleContainer;
	}

	protected Figure getFbFigureContainer() {
		return fbFigureContainer;
	}

	protected Figure getBottomInputArea() {
		return bottomInputArea;
	}

	protected Figure getBottomOutputArea() {
		return bottomOutputArea;
	}

	private static int getMaxWidth() {
		if (-1 == maxWidth) {
			final IPreferenceStore pf = Activator.getDefault().getPreferenceStore();
			return pf.getInt(DiagramPreferences.MAX_TYPE_LABEL_SIZE);
		}
		return maxWidth;
	}

	@Override
	public void setAlpha(final int value) {
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
	protected void fillShape(final Graphics graphics) {
		// not used
	}

	@Override
	protected void outlineShape(final Graphics graphics) {
		// not used
	}

	@Override
	public void paintFigure(final Graphics graphics) {
		// paint figure of shape does not check for background borders, needed for drop shadow
		if (getBorder() instanceof AbstractBackground) {
			((AbstractBackground) getBorder()).paintBackground(this, graphics, NO_INSETS);
		}
		super.paintFigure(graphics);
	}

	private void configureMainFigure() {
		setFillXOR(false);
		setOpaque(false);

		final GridLayout mainLayout = new GridLayout(1, true);
		mainLayout.marginHeight = 0;
		mainLayout.marginWidth = 0;
		mainLayout.horizontalSpacing = 0;
		mainLayout.verticalSpacing = -1;
		setLayoutManager(mainLayout);
	}

	private void createFBFigureShape(final FBType fbType) {
		createFigureContainer();
		createFBTop(DiagramPreferences.CORNER_DIM);
		configureFBMiddle(fbType);
		createFBBottom(DiagramPreferences.CORNER_DIM);
	}

	private void createFBBottom(final int cornerDim) {
		bottom = new RoundedRectangle();
		bottom.setOutline(false);
		bottom.setCornerDimensions(new Dimension(cornerDim, cornerDim));
		bottom.setLayoutManager(createTopBottomLayout());
		addBottom();
		setBottomIOs();
	}

	protected void addBottom() {
		fbFigureContainer.add(bottom, createTopBottomLayoutData(), -1);
	}

	protected void configureFBMiddle(final FBType fbType) {
		middleContainer = new Figure();
		middleContainer.setLayoutManager(new BorderLayout());
		middleContainer.setBorder(new MarginBorder(0, FB_NOTCH_INSET, 0, FB_NOTCH_INSET));

		addMiddle();
		setupTypeNameAndVersion(fbType, middleContainer);
	}

	protected void addMiddle() {
		fbFigureContainer.add(middleContainer, createMiddleLayoutData(), -1);
	}

	protected static GridData createMiddleLayoutData() {
		return new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL);
	}

	private void createFBTop(final int cornerDim) {
		top = new RoundedRectangle();
		top.setOutline(false);
		top.setCornerDimensions(new Dimension(cornerDim, cornerDim));
		top.setLayoutManager(createTopBottomLayout());

		addTop();
		setupTopIOs();
	}

	protected void addTop() {
		fbFigureContainer.add(top, createTopBottomLayoutData(), -1);
	}

	protected static GridData createTopBottomLayoutData() {
		final GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL | GridData.VERTICAL_ALIGN_FILL
				| GridData.GRAB_VERTICAL);
		gridData.verticalAlignment = SWT.TOP;
		return gridData;
	}

	protected static GridLayout createTopBottomLayout() {
		final GridLayout topLayout = new GridLayout(3, false);
		topLayout.marginHeight = 1;
		topLayout.marginWidth = 0;
		topLayout.verticalSpacing = 0;
		topLayout.horizontalSpacing = 0;
		return topLayout;
	}

	private void createFigureContainer() {
		fbFigureContainer = new Figure() {
			@Override
			public Insets getInsets() {
				// even if we have a border do not return insets.
				return NO_INSETS;
			}
		};
		add(fbFigureContainer);
		setConstraint(fbFigureContainer, createDefaultFBContainerLayoutData());

		final GridLayout gridLayout = new GridLayout(1, true);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		gridLayout.horizontalSpacing = 0;
		gridLayout.verticalSpacing = -1;
		fbFigureContainer.setLayoutManager(gridLayout);
	}

	protected static GridData createDefaultFBContainerLayoutData() {
		return new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
	}

	private void setupTopIOs() {
		eventInputs.setLayoutManager(new ToolbarLayout(false));

		final ToolbarLayout topOutputsLayout = new ToolbarLayout(false);
		topOutputsLayout.setMinorAlignment(OrderedLayout.ALIGN_BOTTOMRIGHT);
		eventOutputs.setLayoutManager(topOutputsLayout);
		addTopIOs();
	}

	protected void addTopIOs() {
		top.add(eventInputs, createTopBottomLayoutData(), -1);
		top.add(eventOutputs, createTopBottomOutputLayoutData(), -1);
	}

	private void setBottomIOs() {
		bottomInputArea = new Figure();
		bottomInputArea.setLayoutManager(new ToolbarLayout(false));

		dataInputs.setLayoutManager(new ToolbarLayout(false));
		bottomInputArea.add(dataInputs);

		sockets.setLayoutManager(new ToolbarLayout(false));
		bottomInputArea.add(sockets);

		errorMarkerInput.setLayoutManager(new ToolbarLayout(false));
		bottomInputArea.add(errorMarkerInput);

		bottomOutputArea = new Figure();
		bottomOutputArea.setLayoutManager(new ToolbarLayout(false));
		((ToolbarLayout) bottomOutputArea.getLayoutManager()).setMinorAlignment(OrderedLayout.ALIGN_BOTTOMRIGHT);

		dataOutputs.setLayoutManager(new ToolbarLayout(false));
		((ToolbarLayout) dataOutputs.getLayoutManager()).setMinorAlignment(OrderedLayout.ALIGN_BOTTOMRIGHT);
		bottomOutputArea.add(dataOutputs);

		plugs.setLayoutManager(new ToolbarLayout(false));
		((ToolbarLayout) plugs.getLayoutManager()).setMinorAlignment(OrderedLayout.ALIGN_BOTTOMRIGHT);
		bottomOutputArea.add(plugs);

		errorMarkerOutput.setLayoutManager(new ToolbarLayout(false));
		((ToolbarLayout) errorMarkerOutput.getLayoutManager()).setMinorAlignment(OrderedLayout.ALIGN_BOTTOMRIGHT);
		bottomOutputArea.add(errorMarkerOutput);

		addBottomIOs();
	}

	protected void addBottomIOs() {
		bottom.add(bottomInputArea, createTopBottomLayoutData(), -1);
		bottom.add(bottomOutputArea, createTopBottomOutputLayoutData(), -1);
	}

	protected static GridData createTopBottomOutputLayoutData() {
		final GridData outputsLayoutData = createTopBottomLayoutData();
		outputsLayoutData.horizontalIndent = INPUT_OUTPUT_INTERLEAVE;
		return outputsLayoutData;
	}

	protected void setupTypeNameAndVersion(final FBType type, final Figure container) {
		middle = new RectangleFigure();
		middle.setOutline(false);
		container.add(middle, BorderLayout.CENTER);

		final GridLayout middleLayout = new GridLayout(1, true);
		middleLayout.marginHeight = 1;
		middleLayout.verticalSpacing = 1;
		middleLayout.marginWidth = 3;

		middle.setLayoutManager(middleLayout);

		typeLabel = new UnderlineAlphaLabel();
		changeTypeLabelText((null != type) ? type.getName() : Messages.FBFigure_TYPE_NOT_SET);
		typeLabel.setTextAlignment(PositionConstants.CENTER);
		typeLabel.setOpaque(true);
		typeLabel.setIcon(ResultListLabelProvider.getTypeImage(type));
		typeLabel.setIconTextGap(2);
		middle.add(typeLabel);
		middle.setConstraint(typeLabel, new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL));
	}

	protected void changeTypeLabelText(String text) {
		if (text.length() > getMaxWidth()) {
			text = text.substring(0, getMaxWidth()) + TYPE_TRUNCATION_STRING;
		}
		typeLabel.setText(text);
	}

}
