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

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.Figure;
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
import org.eclipse.fordiac.ide.gef.draw2d.ITransparencyFigure;
import org.eclipse.fordiac.ide.gef.draw2d.OverlayAlphaLabel;
import org.eclipse.fordiac.ide.gef.listeners.IFontUpdateListener;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.fordiac.ide.model.edit.providers.TypeImageProvider;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;

public class FBShape extends Figure implements IFontUpdateListener, ITransparencyFigure {

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

	private final Figure varInOutInputs = new Figure();

	/** The sockets. */
	private final Figure sockets = new Figure();

	/** The data outputs. */
	private final Figure dataOutputs = new Figure();

	private final Figure varInOutOutputs = new Figure();

	/** The sockets. */
	private final Figure errorMarkerInput = new Figure();

	/** The data outputs. */
	private final Figure errorMarkerOutput = new Figure();

	/** The indicator for hidden input pins */
	private final Figure pinIndicatorInput = new Figure();

	/** The indicator for hidden output pins */
	private final Figure pinIndicatorOutput = new Figure();

	/** The plugs. */
	private final Figure plugs = new Figure();

	private OverlayAlphaLabel typeLabel;

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

	public Figure getVarInOutInputs() {
		return varInOutInputs;
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

	public Figure getVarInOutOutputs() {
		return varInOutOutputs;
	}

	public Figure getErrorMarkerInput() {
		return errorMarkerInput;
	}

	public Figure getErrorMarkerOutput() {
		return errorMarkerOutput;
	}

	public Figure getPinIndicatorInput() {
		return pinIndicatorInput;
	}

	public Figure getPinIndicatorOutput() {
		return pinIndicatorOutput;
	}

	public Figure getPlugs() {
		return plugs;
	}

	public OverlayAlphaLabel getTypeLabel() {
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

	private static int getMaxWidth() {
		if (-1 == maxWidth) {
			final IPreferenceStore pf = Activator.getDefault().getPreferenceStore();
			return pf.getInt(DiagramPreferences.MAX_TYPE_LABEL_SIZE);
		}
		return maxWidth;
	}

	@Override
	public void setTransparency(final int value) {
		bottom.setAlpha(value);
		top.setAlpha(value);
		getMiddle().setAlpha(value);

		if (getTypeLabel() != null) {
			getTypeLabel().setAlpha(value);
		}
	}

	@Override
	public int getTransparency() {
		return bottom.getAlpha().intValue();
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

	private void configureMainFigure() {
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
		setPinIndicators();
	}

	private void setPinIndicators() {
		pinIndicatorInput.setLayoutManager(createPinIndicatorLayout());
		errorMarkerInput.add(pinIndicatorInput);

		// use the error marker container for now as this will anyhow be changed later

		pinIndicatorOutput.setLayoutManager(createPinIndicatorLayout());
		errorMarkerOutput.add(pinIndicatorOutput);
	}

	private static ToolbarLayout createPinIndicatorLayout() {
		final ToolbarLayout layout = new ToolbarLayout(false);
		layout.setMinorAlignment(OrderedLayout.ALIGN_BOTTOMRIGHT);

		return layout;
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
		final GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL
				| GridData.VERTICAL_ALIGN_FILL | GridData.GRAB_VERTICAL);
		gridData.verticalAlignment = SWT.TOP;
		return gridData;
	}

	private static GridLayout createTopBottomLayout() {
		final GridLayout topLayout = new GridLayout(2, false);
		topLayout.marginHeight = 1;
		topLayout.marginWidth = 0;
		topLayout.verticalSpacing = 0;
		topLayout.horizontalSpacing = INPUT_OUTPUT_INTERLEAVE;
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
		eventInputs.setLayoutManager(createInputContainerLayout());
		eventOutputs.setLayoutManager(createOutputContainerLayout());
		addTopIOs();
	}

	protected void addTopIOs() {
		top.add(eventInputs, createInputLayoutData(), -1);
		top.add(eventOutputs, createOutputLayoutData(), -1);
	}

	private void setBottomIOs() {
		dataInputs.setLayoutManager(createInputContainerLayout());
		dataOutputs.setLayoutManager(createOutputContainerLayout());

		varInOutInputs.setLayoutManager(createInputContainerLayout());
		varInOutOutputs.setLayoutManager(createOutputContainerLayout());

		sockets.setLayoutManager(createInputContainerLayout());
		plugs.setLayoutManager(createOutputContainerLayout());

		errorMarkerInput.setLayoutManager(createInputContainerLayout());
		errorMarkerOutput.setLayoutManager(createOutputContainerLayout());

		addBottomIOs();
	}

	private static ToolbarLayout createInputContainerLayout() {
		final ToolbarLayout toolbarLayout = new ToolbarLayout(false);
		toolbarLayout.setStretchMinorAxis(true);
		return toolbarLayout;
	}

	private static ToolbarLayout createOutputContainerLayout() {
		final ToolbarLayout layout = new ToolbarLayout(false);
		layout.setStretchMinorAxis(true);
		layout.setMinorAlignment(OrderedLayout.ALIGN_BOTTOMRIGHT);

		return layout;
	}

	protected void addBottomIOs() {
		bottom.add(dataInputs, createInputLayoutData(), -1);
		bottom.add(dataOutputs, createOutputLayoutData(), -1);
		bottom.add(varInOutInputs, createInputLayoutData(), -1);
		bottom.add(varInOutOutputs, createOutputLayoutData(), -1);
		bottom.add(sockets, createInputLayoutData(), -1);
		bottom.add(plugs, createOutputLayoutData(), -1);
		bottom.add(errorMarkerInput, createInputLayoutData(), -1);
		bottom.add(errorMarkerOutput, createOutputLayoutData(), -1);
	}

	protected static GridData createInputLayoutData() {
		return new GridData(SWT.BEGINNING, SWT.TOP, true, false);
	}

	protected static GridData createOutputLayoutData() {
		return new GridData(SWT.END, SWT.TOP, true, false);
	}

	protected void setupTypeNameAndVersion(final FBType type, final Figure container) {
		middle = new RectangleFigure();
		middle.setOutline(false);
		container.add(middle, BorderLayout.CENTER);

		final GridLayout middleLayout = new GridLayout(1, true);
		middleLayout.marginHeight = 0;
		middleLayout.verticalSpacing = 0;
		middleLayout.marginWidth = 3;

		middle.setLayoutManager(middleLayout);

		typeLabel = new OverlayAlphaLabel();
		changeTypeLabelText((null != type) ? type.getName() : Messages.FBFigure_TYPE_NOT_SET);
		typeLabel.setTextAlignment(PositionConstants.CENTER);
		typeLabel.setOpaque(true);
		typeLabel.setIcon((null != type) ? TypeImageProvider.getImageForTypeEntry(type.getTypeEntry()) : null);
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
