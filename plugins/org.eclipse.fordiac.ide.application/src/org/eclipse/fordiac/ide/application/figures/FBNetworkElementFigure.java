/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.figures;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.OrderedLayout;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.editparts.AbstractFBNElementEditPart;
import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.fordiac.ide.gef.draw2d.AdvancedRoundedRectangle;
import org.eclipse.fordiac.ide.gef.draw2d.ITransparencyFigure;
import org.eclipse.fordiac.ide.gef.draw2d.UnderlineAlphaLabel;
import org.eclipse.fordiac.ide.gef.listeners.IFontUpdateListener;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceGetter;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

/**
 * The visualization of an FB. It Provides several containers for its interface.
 *
 */
public class FBNetworkElementFigure extends Shape implements ITransparencyFigure, IFontUpdateListener {

	private static final class OpenTypeListener implements MouseListener {
		private final AbstractFBNElementEditPart editPart;

		public OpenTypeListener(AbstractFBNElementEditPart editPart) {
			this.editPart = editPart;
		}

		@Override
		public void mousePressed(MouseEvent me) {
			if (0 != (me.getState() & SWT.CONTROL) && editPart.isOnlyThisOrNothingSelected()) {
				openTypeInEditor(editPart.getModel());
			}
		}

		@Override
		public void mouseReleased(MouseEvent me) {
			// nothing to be done here
		}

		@Override
		public void mouseDoubleClicked(MouseEvent me) {
			// nothing to be done here
		}

	}

	// TODO model refactoring - look for a better place for this function
	public static void openTypeInEditor(FBNetworkElement element) {
		// open the default editor for the adapter file
		PaletteEntry entry = element.getPaletteEntry();
		if (null != entry) {
			IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry()
					.getDefaultEditor(entry.getFile().getName());
			EditorUtils.openEditor(new FileEditorInput(entry.getFile()), desc.getId());
		}
	}

	/** The model. */
	private FBNetworkElement model = null;

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

	private AbstractFBNElementEditPart editPart;

	protected FBNetworkElement getModel() {
		return model;
	}

	protected UnderlineAlphaLabel getTypeLabel() {
		return typeLabel;
	}

	protected ZoomManager getZoomManager() {
		return (null != editPart) ? editPart.getZoomManager() : null;
	}

	// TODO consider moving this into a subclass for adapter fbs and return here
	// only the default color
	protected Color getBorderColor(LibraryElement type) {
		if (type instanceof AdapterFBType) {
			return PreferenceGetter.getColor(PreferenceConstants.P_ADAPTER_CONNECTOR_COLOR);
		}
		return ColorConstants.gray;
	}

	/**
	 * Instantiates a new fB figure.
	 *
	 * @param model the model
	 */
	public FBNetworkElementFigure(final FBNetworkElement model, AbstractFBNElementEditPart editPart) {
		this.model = model;
		this.editPart = editPart;
		configureMainFigure();
		createFBFigureShape(model);
		refreshToolTips();
		setTypeLabelFont();
	}

	private void createFBFigureShape(final FBNetworkElement model) {
		IPreferenceStore pf = Activator.getDefault().getPreferenceStore();
		int cornerDim = pf.getInt(DiagramPreferences.CORNER_DIM);
		Color borderColor = getBorderColor(model.getType());

		Figure fbFigureContainer = createFigureContainer();
		createFBTop(fbFigureContainer, cornerDim, borderColor);
		configureFBMiddle(model, fbFigureContainer, borderColor);
		createFBBottom(fbFigureContainer, cornerDim, borderColor);
	}

	private void createFBBottom(Figure fbFigureContainer, int cornerDim, Color borderColor) {
		bottom = new AdvancedRoundedRectangle(PositionConstants.SOUTH | PositionConstants.EAST | PositionConstants.WEST,
				getZoomManager(), this, true, borderColor);
		bottom.setCornerDimensions(new Dimension(cornerDim, cornerDim));
		GridLayout bottomLayout = new GridLayout(2, false);
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

	private void configureFBMiddle(final FBNetworkElement model, Figure fbFigureContainer, Color borderColor) {
		Figure middleContainer = new Figure();
		BorderLayout borderLayout = new BorderLayout();
		middleContainer.setLayoutManager(borderLayout);
		borderLayout.setHorizontalSpacing(10);
		middleContainer.setBorder(new MarginBorder(0, 7, 0, 7));

		fbFigureContainer.add(middleContainer);
		GridData middleLayouData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL);
		fbFigureContainer.setConstraint(middleContainer, middleLayouData);

		setupTypeNameAndVersion(model, middleContainer, borderColor);
	}

	private void createFBTop(Figure fbFigureContainer, int cornerDim, Color borderColor) {
		top = new AdvancedRoundedRectangle(PositionConstants.NORTH | PositionConstants.EAST | PositionConstants.WEST,
				getZoomManager(), this, true, borderColor);
		top.setCornerDimensions(new Dimension(cornerDim, cornerDim));

		GridLayout topLayout = new GridLayout(2, false);
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

		GridData bottomOutputsLayoutData = new GridData(
				GridData.HORIZONTAL_ALIGN_END | GridData.GRAB_HORIZONTAL | GridData.VERTICAL_ALIGN_FILL);
		parent.add(bottomOutputArea);
		parent.setConstraint(bottomOutputArea, bottomOutputsLayoutData);

		dataOutputs.setLayoutManager(new ToolbarLayout(false));
		((ToolbarLayout) dataOutputs.getLayoutManager()).setMinorAlignment(OrderedLayout.ALIGN_BOTTOMRIGHT);
		bottomOutputArea.add(dataOutputs);

		plugs.setLayoutManager(new ToolbarLayout(false));
		((ToolbarLayout) plugs.getLayoutManager()).setMinorAlignment(OrderedLayout.ALIGN_BOTTOMRIGHT);
		bottomOutputArea.add(plugs);
	}

	protected void setupTypeNameAndVersion(final FBNetworkElement model, Figure container, Color borderColor) {
		middle = new AdvancedRoundedRectangle(PositionConstants.EAST | PositionConstants.WEST, getZoomManager(), this,
				true, borderColor);

		container.add(middle, BorderLayout.CENTER);
		middle.setCornerDimensions(new Dimension());

		GridLayout middleLayout = new GridLayout(1, true);
		middleLayout.marginHeight = 2;
		middleLayout.verticalSpacing = 1;

		middle.setLayoutManager(middleLayout);

		LibraryElement type = model.getType();
		String typeName = (null != type) ? type.getName() : Messages.FBFigure_TYPE_NOT_SET;

		typeLabel = new UnderlineAlphaLabel(null != typeName ? typeName : Messages.FBFigure_NOT_DEFINED_Text);
		typeLabel.setTextAlignment(PositionConstants.CENTER);
		typeLabel.setOpaque(false);
		middle.add(typeLabel);
		middle.setConstraint(typeLabel, new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL));
		setupMouseListener(editPart);
	}

	private void setupMouseListener(final AbstractFBNElementEditPart editPart) {

		middle.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent me) {
				// nothing to bo done here
			}

			@Override
			public void mouseEntered(MouseEvent me) {
				if (0 != (me.getState() & SWT.CONTROL) && editPart.isOnlyThisOrNothingSelected()) {
					typeLabel.setDrawUnderline(true);
				}
			}

			@Override
			public void mouseExited(MouseEvent me) {
				typeLabel.setDrawUnderline(false);
			}

			@Override
			public void mouseHover(MouseEvent me) {
				// currently mouseHover should be the same as mouse moved
				mouseMoved(me);
			}

			@Override
			public void mouseMoved(MouseEvent me) {
				if (0 != (me.getState() & SWT.CONTROL) && editPart.isOnlyThisOrNothingSelected()) {
					if (!typeLabel.isDrawUnderline()) {
						typeLabel.setDrawUnderline(true);
					}
				} else {
					if (typeLabel.isDrawUnderline()) {
						typeLabel.setDrawUnderline(false);
					}
				}
			}

		});

		middle.addMouseListener(createOpenTypeMouseListener(editPart));

	}

	private static OpenTypeListener createOpenTypeMouseListener(final AbstractFBNElementEditPart editPart) {
		return new OpenTypeListener(editPart);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return model.getTypeName() + ":" //$NON-NLS-1$
				+ model.getName();
	}

	/**
	 * Refresh tool tips.
	 */
	public final void refreshToolTips() {
		setToolTip(new FBNetworkElementTooltipFigure(model));
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

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.draw2d.Shape#fillShape(org.eclipse.draw2d.Graphics)
	 */
	@Override
	protected void fillShape(final Graphics graphics) {
		// not used
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.draw2d.Shape#outlineShape(org.eclipse.draw2d.Graphics)
	 */
	@Override
	protected void outlineShape(final Graphics graphics) {
		// not used
	}

	@Override
	public void setAlpha(int value) {
		super.setAlpha(value);

		bottom.setAlpha(value);
		top.setAlpha(value);
		middle.setAlpha(value);

		if (typeLabel != null) {
			typeLabel.setAlpha(value);
		}
	}

	@Override
	public void setTransparency(int value) {
		setAlpha(value);
	}

	@Override
	public int getTransparency() {
		return getAlpha();
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

}
