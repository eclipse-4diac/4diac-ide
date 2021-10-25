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
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.figures;

import org.eclipse.draw2d.AbstractBackground;
import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.OrderedLayout;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.fordiac.ide.gef.Messages;
import org.eclipse.fordiac.ide.gef.draw2d.UnderlineAlphaLabel;
import org.eclipse.fordiac.ide.gef.listeners.IFontUpdateListener;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.fordiac.ide.model.edit.providers.ResultListLabelProvider;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;

public class FBShape extends Shape implements IFontUpdateListener {

	private static final int INPUT_OUTPUT_INTERLEAVE = 7;

	private static final int FB_NOTCH_INSET = 9;

	/** The top. */
	private RoundedRectangle top;

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

	public RoundedRectangle getTop() {
		return top;
	}

	public Shape getMiddle() {
		return middle;
	}

	public RoundedRectangle getBottom() {
		return bottom;
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
		final Figure fbFigureContainer = createFigureContainer();
		createFBTop(fbFigureContainer, DiagramPreferences.CORNER_DIM);
		configureFBMiddle(fbType, fbFigureContainer);
		createFBBottom(fbFigureContainer, DiagramPreferences.CORNER_DIM);
	}

	private void createFBBottom(final Figure fbFigureContainer, final int cornerDim) {
		bottom = new RoundedRectangle();
		bottom.setOutline(false);
		bottom.setCornerDimensions(new Dimension(cornerDim, cornerDim));
		bottom.setLayoutManager(createTopBottomLayout());

		final GridData bottomLayoutData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL
				| GridData.VERTICAL_ALIGN_FILL | GridData.GRAB_VERTICAL);
		bottomLayoutData.verticalAlignment = SWT.TOP;

		fbFigureContainer.add(bottom);
		fbFigureContainer.setConstraint(bottom, bottomLayoutData);

		setBottomIOs(bottom);
	}

	private void configureFBMiddle(final FBType fbType, final Figure fbFigureContainer) {
		final Figure middleContainer = new Figure();
		middleContainer.setLayoutManager(new BorderLayout());
		middleContainer.setBorder(new MarginBorder(0, FB_NOTCH_INSET, 0, FB_NOTCH_INSET));

		fbFigureContainer.add(middleContainer);
		final GridData middleLayouData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL);
		fbFigureContainer.setConstraint(middleContainer, middleLayouData);

		setupTypeNameAndVersion(fbType, middleContainer);
	}

	private void createFBTop(final Figure fbFigureContainer, final int cornerDim) {
		top = new RoundedRectangle();
		top.setOutline(false);
		top.setCornerDimensions(new Dimension(cornerDim, cornerDim));
		top.setLayoutManager(createTopBottomLayout());

		fbFigureContainer.add(top);
		final GridData topLayoutData = new GridData(
				GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL | GridData.VERTICAL_ALIGN_FILL);
		fbFigureContainer.setConstraint(top, topLayoutData);

		setupTopIOs(top);
	}

	private static GridLayout createTopBottomLayout() {
		final GridLayout topLayout = new GridLayout(3, false);
		topLayout.marginHeight = 1;
		topLayout.marginWidth = 0;
		topLayout.verticalSpacing = 0;
		topLayout.horizontalSpacing = 0;
		return topLayout;
	}

	private Figure createFigureContainer() {
		final Figure fbFigureContainer = new Figure();
		add(fbFigureContainer);
		setConstraint(fbFigureContainer, new GridData(GridData.HORIZONTAL_ALIGN_CENTER));

		final GridLayout gridLayout = new GridLayout(1, true);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		gridLayout.horizontalSpacing = 0;
		gridLayout.verticalSpacing = -1;
		fbFigureContainer.setLayoutManager(gridLayout);
		return fbFigureContainer;
	}

	private void setupTopIOs(final IFigure parent) {
		final ToolbarLayout topInputsLayout = new ToolbarLayout(false);
		final GridData topInputsLayoutData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL
				| GridData.VERTICAL_ALIGN_FILL | GridData.GRAB_VERTICAL);
		eventInputs.setLayoutManager(topInputsLayout);
		parent.add(eventInputs);
		parent.setConstraint(eventInputs, topInputsLayoutData);

		//
		final ToolbarLayout topOutputsLayout = new ToolbarLayout(false);
		final GridData topOutputsLayoutData = new GridData(GridData.HORIZONTAL_ALIGN_END | GridData.GRAB_HORIZONTAL
				| GridData.VERTICAL_ALIGN_FILL | GridData.GRAB_VERTICAL);
		topOutputsLayout.setMinorAlignment(OrderedLayout.ALIGN_BOTTOMRIGHT);
		topOutputsLayoutData.horizontalIndent = INPUT_OUTPUT_INTERLEAVE;
		eventOutputs.setLayoutManager(topOutputsLayout);
		parent.add(eventOutputs);
		parent.setConstraint(eventOutputs, topOutputsLayoutData);
	}

	private void setBottomIOs(final IFigure parent) {
		final Figure bottomInputArea = new Figure();
		bottomInputArea.setLayoutManager(new ToolbarLayout(false));

		final GridData bottomInputsLayoutData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL
				| GridData.VERTICAL_ALIGN_FILL | GridData.GRAB_VERTICAL);
		bottomInputsLayoutData.verticalAlignment = SWT.TOP;

		parent.add(bottomInputArea);
		parent.setConstraint(bottomInputArea, bottomInputsLayoutData);

		dataInputs.setLayoutManager(new ToolbarLayout(false));
		bottomInputArea.add(dataInputs);

		sockets.setLayoutManager(new ToolbarLayout(false));
		bottomInputArea.add(sockets);


		errorMarkerInput.setLayoutManager(new ToolbarLayout(false));
		bottomInputArea.add(errorMarkerInput);


		final Figure bottomOutputArea = new Figure();
		bottomOutputArea.setLayoutManager(new ToolbarLayout(false));
		((ToolbarLayout) bottomOutputArea.getLayoutManager()).setMinorAlignment(OrderedLayout.ALIGN_BOTTOMRIGHT);

		final GridData bottomOutputsLayoutData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL
				| GridData.VERTICAL_ALIGN_FILL | GridData.GRAB_VERTICAL);
		bottomOutputsLayoutData.horizontalIndent = INPUT_OUTPUT_INTERLEAVE;
		parent.add(bottomOutputArea);
		parent.setConstraint(bottomOutputArea, bottomOutputsLayoutData);

		dataOutputs.setLayoutManager(new ToolbarLayout(false));
		((ToolbarLayout) dataOutputs.getLayoutManager()).setMinorAlignment(OrderedLayout.ALIGN_BOTTOMRIGHT);
		bottomOutputArea.add(dataOutputs);

		plugs.setLayoutManager(new ToolbarLayout(false));
		((ToolbarLayout) plugs.getLayoutManager()).setMinorAlignment(OrderedLayout.ALIGN_BOTTOMRIGHT);
		bottomOutputArea.add(plugs);

		errorMarkerOutput.setLayoutManager(new ToolbarLayout(false));
		((ToolbarLayout) errorMarkerOutput.getLayoutManager()).setMinorAlignment(OrderedLayout.ALIGN_BOTTOMRIGHT);
		bottomOutputArea.add(errorMarkerOutput);


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

		String typeName = (null != type) ? type.getName() : Messages.FBFigure_TYPE_NOT_SET;

		if(typeName.length() > getMaxWidth()) {
			typeName = typeName.substring(0, getMaxWidth()) + getTruncationString();
		}

		typeLabel = new UnderlineAlphaLabel(null != typeName ? typeName : FordiacMessages.ND);
		typeLabel.setTextAlignment(PositionConstants.CENTER);
		typeLabel.setOpaque(true);
		typeLabel.setIcon(ResultListLabelProvider.getTypeImage(type));
		typeLabel.setIconTextGap(2);
		middle.add(typeLabel);
		middle.setConstraint(typeLabel, new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL));
	}

	protected void changeTypeLabelText(String text) {
		if (text.length() > getMaxWidth()) {
			text = text.substring(0, getMaxWidth()) + getTruncationString();
		}
		typeLabel.setText(text);
		typeLabel.setIcon(null);
	}

	protected static String getTruncationString() {
		return "\u2026"; //$NON-NLS-1$
	}

}
