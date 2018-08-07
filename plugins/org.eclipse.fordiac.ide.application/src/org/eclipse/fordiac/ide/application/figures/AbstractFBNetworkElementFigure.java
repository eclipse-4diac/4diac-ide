/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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
package org.eclipse.fordiac.ide.application.figures;

import org.eclipse.core.resources.IMarker;
import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
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
import org.eclipse.fordiac.ide.application.ApplicationPlugin;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.editparts.AbstractFBNElementEditPart;
import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.fordiac.ide.gef.draw2d.AdvancedRoundedRectangle;
import org.eclipse.fordiac.ide.gef.draw2d.ITransparencyFigure;
import org.eclipse.fordiac.ide.gef.draw2d.SetableAlphaLabel;
import org.eclipse.fordiac.ide.gef.draw2d.UnderlineAlphaLabel;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Annotation;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.util.imageprovider.FordiacImage;
import org.eclipse.fordiac.ide.util.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.util.preferences.PreferenceGetter;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

/**
 * The visualization of an FB. It Provides several containers for its interface.
 * 
 */
public abstract class AbstractFBNetworkElementFigure extends Shape implements ITransparencyFigure {

	private final class OpenTypeListener implements MouseListener {
		final AbstractFBNElementEditPart editPart;
		
		public OpenTypeListener(AbstractFBNElementEditPart editPart) {
			this.editPart = editPart;
		}

		@Override
		public void mousePressed(MouseEvent me) {
			if( 0 != (me.getState() & SWT.CONTROL) && editPart.isOnlyThisOrNothingSelected()){
				openTypeInEditor(editPart.getModel());
			}					
		}

		@Override
		public void mouseReleased(MouseEvent me) {					
		}

		@Override
		public void mouseDoubleClicked(MouseEvent me) {					
		}
		
	}

	//TODO model refactoring - look for a better place for this function
	public static void openTypeInEditor(FBNetworkElement element){
		//open the default editor for the adapter file
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		
		PaletteEntry entry = element.getPaletteEntry();
		if(null != entry)	{			
			IEditorDescriptor desc = PlatformUI.getWorkbench().
					getEditorRegistry().getDefaultEditor(entry.getFile().getName());
			try {
				page.openEditor(new FileEditorInput(entry.getFile()), desc.getId());
			} catch (PartInitException e) {
				ApplicationPlugin.getDefault().logError(e.getMessage(), e);
			}
		}
	}

	/** The Constant MIN_DIMENSION. */
	protected static final Dimension MIN_DIMENSION = new Dimension(50, 50);

	/** The model. */
	protected FBNetworkElement model = null;

	/** The instance name label. */
	protected SetableAlphaLabel instanceNameLabel = null;

	/** The top. */
	protected RoundedRectangle top;	

	/** The middle. */
	protected AdvancedRoundedRectangle middle; 

	/** The bottom. */
	protected AdvancedRoundedRectangle bottom;

	/** The event inputs. */
	protected final Figure eventInputs = new Figure();

	/** The event outputs. */
	protected final Figure eventOutputs = new Figure();

	/** The data inputs. */
	protected final Figure dataInputs = new Figure();
	
	/** The sockets. */
	protected final Figure sockets = new Figure();

	/** The data outputs. */
	protected final Figure dataOutputs = new Figure();
	
	/** The plugs. */
	protected final Figure plugs = new Figure();

	protected UnderlineAlphaLabel typeLabel;

	protected AbstractFBNElementEditPart editPart; 
	
	protected AbstractFBNetworkElementFigure() {
		configureRectangles();
	}

	public ZoomManager getZoomManager() {
		return (null != editPart) ? editPart.getZoomManager() : null;
	}
	
	protected void configureRectangles() {
		IPreferenceStore pf = Activator.getDefault().getPreferenceStore();
		int cornerDim = pf.getInt(DiagramPreferences.CORNER_DIM);	
		Color borderColor = getBorderColor(model.getType()); 
		
		top = new AdvancedRoundedRectangle(PositionConstants.NORTH | PositionConstants.EAST
				| PositionConstants.WEST, getZoomManager(), this, true, borderColor);
		top.setCornerDimensions(new Dimension(cornerDim, cornerDim));
		
		GridLayout topLayout = new GridLayout(2, false);
		topLayout.marginHeight = 4;
		topLayout.marginWidth = 1;
		topLayout.horizontalSpacing = 2;
		top.setLayoutManager(topLayout);
		
		middle = new AdvancedRoundedRectangle(PositionConstants.EAST | PositionConstants.WEST, getZoomManager(), this, true, borderColor);
		
		bottom = new AdvancedRoundedRectangle(PositionConstants.SOUTH | PositionConstants.EAST
				| PositionConstants.WEST, getZoomManager(), this,  true, borderColor);
		bottom.setCornerDimensions(new Dimension(cornerDim, cornerDim));
		GridLayout bottomLayout = new GridLayout(2, false);
		bottomLayout.marginHeight = 4;
		bottomLayout.marginWidth = 1;
		bottomLayout.horizontalSpacing = 0;
		bottom.setLayoutManager(bottomLayout);
	}
	
	//TODO consider moving this into a subclass for adapter fbs and return here only the default color
	protected Color getBorderColor(LibraryElement type){
		if(type instanceof AdapterFBType){
			return PreferenceGetter.getColor(PreferenceConstants.P_ADAPTER_CONNECTOR_COLOR);
		}
		return ColorConstants.gray;
	}
	
	
	/**
	 * Instantiates a new fB figure.
	 * 
	 * @param model
	 *            the model
	 */
	public AbstractFBNetworkElementFigure(final FBNetworkElement model, AbstractFBNElementEditPart editPart) {
		this.model = model;
		this.editPart = editPart;
		configureRectangles();
		this.setFillXOR(false);
		this.setOpaque(false);

		GridLayout gridLayout = new GridLayout(1, true);
		gridLayout.verticalSpacing = 2;
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		gridLayout.horizontalSpacing = 0;
		gridLayout.verticalSpacing = -1;
		setLayoutManager(gridLayout);
		
		createInstanceNameLabel(model.getName());
		
		
		add(top);
		GridData topLayoutData = new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL | GridData.VERTICAL_ALIGN_FILL);
		setConstraint(top, topLayoutData);

		setupTopIOs();

		Figure middleContainer = new Figure();
		BorderLayout borderLayout;
		middleContainer.setLayoutManager(borderLayout = new BorderLayout());
		borderLayout.setHorizontalSpacing(10);
		middleContainer.setBorder(new MarginBorder(0, 7, 0, 7));

		add(middleContainer);
		GridData middleLayouData = new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL);
		setConstraint(middleContainer, middleLayouData);
		
		setupTypeNameAndVersion(model, middleContainer);
		
		GridData bottomLayoutData = new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL | GridData.VERTICAL_ALIGN_FILL
				| GridData.GRAB_VERTICAL);
		bottomLayoutData.verticalAlignment = SWT.TOP;

		add(bottom);
		setConstraint(bottom, bottomLayoutData);
		
		setBottomIOs();		
		
		refreshToolTips();

		updateResourceTypeFigure();		
	}

	protected void setupTopIOs() {
		ToolbarLayout topInputsLayout = new ToolbarLayout(false);
		GridData topInputsLayoutData = new GridData(
				GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL
						| GridData.VERTICAL_ALIGN_FILL | GridData.GRAB_VERTICAL);
		eventInputs.setLayoutManager(topInputsLayout);
		//
		top.add(eventInputs);
		top.setConstraint(eventInputs, topInputsLayoutData);

		//
		ToolbarLayout topOutputsLayout = new ToolbarLayout(false);
		GridData topOutputsLayoutData = new GridData(
				GridData.HORIZONTAL_ALIGN_END | GridData.GRAB_HORIZONTAL
						| GridData.VERTICAL_ALIGN_FILL | GridData.GRAB_VERTICAL);
		topOutputsLayout.setMinorAlignment(OrderedLayout.ALIGN_BOTTOMRIGHT);
		eventOutputs.setLayoutManager(topOutputsLayout);
		top.add(eventOutputs);
		top.setConstraint(eventOutputs, topOutputsLayoutData);
	}

	protected void setBottomIOs() {
		Figure bottomInputArea = new Figure();
		bottomInputArea.setLayoutManager(new ToolbarLayout(false));
				
		GridData bottomInputsLayoutData = new GridData(
				GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL
						| GridData.VERTICAL_ALIGN_FILL | GridData.GRAB_VERTICAL);
		bottomInputsLayoutData.verticalAlignment = SWT.TOP;
		bottom.add(bottomInputArea);
		bottom.setConstraint(bottomInputArea, bottomInputsLayoutData);
		
		dataInputs.setLayoutManager(new ToolbarLayout(false));
		bottomInputArea.add(dataInputs);
		
		sockets.setLayoutManager(new ToolbarLayout(false));
		bottomInputArea.add(sockets);
		
		//
		Figure bottomOutputArea = new Figure();
		bottomOutputArea.setLayoutManager(new ToolbarLayout(false));
		((ToolbarLayout)bottomOutputArea.getLayoutManager()).setMinorAlignment(OrderedLayout.ALIGN_BOTTOMRIGHT);
		
		// bottomOutputsLayout.setStretchMinorAxis(true);		
		GridData bottomOutputsLayoutData = new GridData(
				GridData.HORIZONTAL_ALIGN_END | GridData.GRAB_HORIZONTAL
						| GridData.VERTICAL_ALIGN_FILL);
		bottom.add(bottomOutputArea);
		bottom.setConstraint(bottomOutputArea, bottomOutputsLayoutData);
		
		
		dataOutputs.setLayoutManager(new ToolbarLayout(false));
		((ToolbarLayout)dataOutputs.getLayoutManager()).setMinorAlignment(OrderedLayout.ALIGN_BOTTOMRIGHT);
		bottomOutputArea.add(dataOutputs);
		
		plugs.setLayoutManager(new ToolbarLayout(false));
		((ToolbarLayout)plugs.getLayoutManager()).setMinorAlignment(OrderedLayout.ALIGN_BOTTOMRIGHT);
		bottomOutputArea.add(plugs);
	}

	protected void createInstanceNameLabel(final String instanceName) {
		GridData instanceNameLayout = new GridData();
		instanceNameLayout.grabExcessHorizontalSpace = true;
		instanceNameLayout.horizontalAlignment = SWT.CENTER;

		instanceNameLabel = new SetableAlphaLabel();
		instanceNameLabel.setText(instanceName);
		instanceNameLabel.setTextAlignment(PositionConstants.CENTER);
		instanceNameLabel.setLabelAlignment(PositionConstants.CENTER);
		instanceNameLabel.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT));

		add(instanceNameLabel);
		setConstraint(instanceNameLabel, instanceNameLayout);
	}

	protected void setupTypeNameAndVersion(final FBNetworkElement model, Figure container) {
		container.add(middle, BorderLayout.CENTER);
		middle.setCornerDimensions(new Dimension());
		
		GridLayout middleLayout = new GridLayout(1, true);
		middleLayout.marginHeight = 0;
		middleLayout.verticalSpacing = 1;

		middle.setLayoutManager(middleLayout);
		
		LibraryElement type = model.getType();
		String typeName = (null != type) ? type.getName() : Messages.FBFigure_TYPE_NOT_SET;
			
		typeLabel = new UnderlineAlphaLabel(typeName != null ? typeName : Messages.FBFigure_NOT_DEFINED_Text);
		middle.add(typeLabel);
		typeLabel.setTextAlignment(PositionConstants.CENTER);
		middle.setConstraint(typeLabel, new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL));
		typeLabel.setOpaque(false);
		typeLabel.setFont(JFaceResources.getFontRegistry().getItalic(JFaceResources.DEFAULT_FONT));
		
		setupMouseListener(editPart);
	}

	private void setupMouseListener(final AbstractFBNElementEditPart editPart) {
		
		middle.addMouseMotionListener(new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent me) {
			}

			@Override
			public void mouseEntered(MouseEvent me) {
				if( 0 != (me.getState() & SWT.CONTROL) && editPart.isOnlyThisOrNothingSelected()){
					typeLabel.setDrawUnderline(true);
				}
			}

			@Override
			public void mouseExited(MouseEvent me) {
				typeLabel.setDrawUnderline(false);
			}

			@Override
			public void mouseHover(MouseEvent me) {
				//currently mouseHover should be the same as mouse moved
				mouseMoved(me);
			}

			@Override
			public void mouseMoved(MouseEvent me) {
				if( 0 != (me.getState() & SWT.CONTROL) && editPart.isOnlyThisOrNothingSelected()){
					if(!typeLabel.isDrawUnderline()){
						typeLabel.setDrawUnderline(true);
					}
				}
				else{
					if(typeLabel.isDrawUnderline()){
						typeLabel.setDrawUnderline(false);
					}
				}
			}
			
		});
		
		middle.addMouseListener(createOpenTypeMouseListener(editPart));
		
	}

	protected OpenTypeListener createOpenTypeMouseListener(final AbstractFBNElementEditPart editPart) {
		return new OpenTypeListener(editPart);
	}

	protected void updateResourceTypeFigure() {
		if (isResoruceTypeFBNElement()) {
			getInstanceNameLabel().setIcon(FordiacImage.ICON_LockedState.getImage());
		}
	}

	private void updateOverlay() {
		Image image = getInstanceNameLabel().getIcon();
		for (Annotation anno : model.getAnnotations()) {
			if (anno.getServity() == IMarker.SEVERITY_ERROR) {
				image = FordiacImage.getErrorOverlayImage(image);
				break;
			}
		}
		if(null == model.getPaletteEntry() && !(model instanceof SubApp)){  // TODO model refactoring - consider to move this to something specific for untyped subapps
			image = FordiacImage.getErrorOverlayImage(image);
		}
		getInstanceNameLabel().setIcon(image);
	}
	
	public void refreshIcon() {
		if (isResoruceTypeFBNElement()) {
			updateResourceTypeFigure();
		} else {
			getInstanceNameLabel().setIcon(null);
		}
		updateOverlay();
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
	 * Gets the instance name SetableAlphaLabel.
	 * 
	 * @return the instance name SetableAlphaLabel
	 */
	public SetableAlphaLabel getInstanceNameLabel() {
		return instanceNameLabel;
	}

	/**
	 * Refresh tool tips.
	 */
	public void refreshToolTips() {
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

		if (instanceNameLabel != null) {
			instanceNameLabel.setAlpha(value);
		}
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

	public Image getIcon() {
		return getInstanceNameLabel().getIcon();
	}
	
	public void setIcon(Image image) {
		getInstanceNameLabel().setIcon(image);
	}
	
	protected abstract boolean isResoruceTypeFBNElement();

}
