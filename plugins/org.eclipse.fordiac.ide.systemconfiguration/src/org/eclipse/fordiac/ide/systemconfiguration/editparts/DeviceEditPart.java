/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GbmH, TU Wien ACIN, fortiss GmbH
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
package org.eclipse.fordiac.ide.systemconfiguration.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
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
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.fordiac.ide.gef.draw2d.FordiacFigureUtilities;
import org.eclipse.fordiac.ide.gef.editparts.AbstractPositionableElementEditPart;
import org.eclipse.fordiac.ide.gef.figures.InteractionStyleFigure;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.fordiac.ide.systemconfiguration.policies.DeleteDeviceEditPolicy;
import org.eclipse.fordiac.ide.systemconfiguration.policies.DeviceViewLayoutEditPolicy;
import org.eclipse.fordiac.ide.systemconfiguration.policies.SegmentNodeEditPolicy;
import org.eclipse.fordiac.ide.util.ColorHelper;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.widgets.Display;

public class DeviceEditPart extends AbstractPositionableElementEditPart implements NodeEditPart {
	/** necessary that the gradient pattern can be scaled accordingly */
	private ZoomManager zoomManager;
	
	DeviceEditPart(ZoomManager zoomManager) {
		super();
		setConnectable(true);
		this.zoomManager = zoomManager;
	}

	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			((Notifier) getModel()).eAdapters().add(getContentAdapter());
		}
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			((Notifier) getModel()).eAdapters().remove(getContentAdapter());
		}
	}
	
	private EContentAdapter contentAdapter=new EContentAdapter(){
		@Override 
		public void notifyChanged(Notification notification) { 
			Object feature = notification.getFeature();
			if (LibraryElementPackage.eINSTANCE.getColorizableElement_Color().equals(feature)){
				backgroundColorChanged(getFigure());
			} else {			
				super.notifyChanged(notification);
				refreshChildren();
				refreshTargetConnections();
			} 
		}
	};

	@Override
	public Device getModel() {
		return (Device) super.getModel();
	}
	
	@Override
	protected EContentAdapter getContentAdapter() {
		return contentAdapter;
	}

	@Override
	public INamedElement getINamedElement() {
		return getModel();
	}
	
	@Override
	protected PositionableElement getPositionableElement(){
		return getModel();
	}

	@Override
	public DeviceFigure getFigure() {
		return (DeviceFigure)super.getFigure();
	}

	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
		IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		if (childEditPart instanceof DeviceInterfaceEditPart) {
			getFigure().getDataInputs().add(child);
		} else if (childEditPart instanceof ResourceEditPart) {
			getFigure().getContentPane().add(child);
		} else {
			super.addChildVisual(childEditPart, index);
		}
	}

	@Override
	protected void removeChildVisual(final EditPart childEditPart) {
		IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		if (childEditPart instanceof DeviceInterfaceEditPart) {
			getFigure().getDataInputs().remove(child);
		} else if (childEditPart instanceof ResourceEditPart) {
			getFigure().getContentPane().remove(child);
		} else {
			super.removeChildVisual(childEditPart);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected List getModelChildren() {
		ArrayList elements = new ArrayList();
		elements.addAll(getModel().getVarDeclarations());
		elements.addAll(getModel().getResource());
		return elements;
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new DeviceViewLayoutEditPolicy());
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new DeleteDeviceEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new SegmentNodeEditPolicy());
	}

	@Override
	public Label getNameLabel() {
		return getFigure().getName();
	}

	@Override
	public IPropertyChangeListener getPreferenceChangeListener() {
		return null;
	}

	@Override
	protected IFigure createFigureForModel() {
		return new DeviceFigure();
	}

	protected void backgroundColorChanged(IFigure figure) {
		// TODO model refactoring - default value for colors if not persisted
		org.eclipse.fordiac.ide.model.libraryElement.Color fordiacColor = getModel().getColor();
		if (fordiacColor == null) {
			fordiacColor = ColorHelper.createRandomColor();
			getModel().setColor(fordiacColor);
		}
		setColor(figure, fordiacColor);

	}
	

	private final class DeviceConnectionAnchor extends ChopboxAnchor {
		DeviceConnectionAnchor(IFigure owner) {
			super(owner);
		}

		@Override
		protected Rectangle getBox() {
			// calculate a bounding box which consist of device main part and
			// name label
			Rectangle main = getFigure().getConnectionReferenceFigure().getBounds().getCopy();
			Rectangle top = getFigure().getName().getBounds();

			main.setHeight(main.height + top.height());
			main.y = top.y();

			return main;
		}
	}

	private class DeviceFigure extends Shape implements InteractionStyleFigure {
		private final Label instanceNameLabel = new Label();
		private Figure main = new Figure();
		private Figure dataInputs = new Figure();
		private Figure contentPane;

		public int getIntersectionStyle(Point location) {
			if (instanceNameLabel.intersects(new Rectangle(location, new Dimension(1, 1)))) {
				return InteractionStyleFigure.REGION_DRAG; // move/drag
			} 
			return InteractionStyleFigure.REGION_CONNECTION; // connection
		}

		private RoundedRectangle bottom = new RoundedRectangle(){
				protected void fillShape(Graphics graphics){
					Display display=Display.getCurrent();
					Rectangle boundingRect = getBounds().getCopy();
					boundingRect.scale(zoomManager.getZoom());
					Point topLeft = boundingRect.getTopLeft();
					Point bottomRight = boundingRect.getBottomRight();
					Color first=ColorHelper.lighter(getBackgroundColor());
					Pattern pattern = new Pattern(display,topLeft.x,topLeft.y,bottomRight.x,bottomRight.y,first,getBackgroundColor());
					graphics.setBackgroundPattern(pattern);
					graphics.fillRoundRectangle(getBounds(),getCornerDimensions().width,getCornerDimensions().height);
					graphics.setBackgroundPattern(null);pattern.dispose();first.dispose();
				}
			};

			public DeviceFigure() {
				setBackgroundColor(ColorConstants.white);
				this.setFillXOR(true);
				
				GridLayout gridLayout = new GridLayout(1, true);
				gridLayout.verticalSpacing = 2;
				gridLayout.marginHeight = 0;
				gridLayout.marginWidth = 0;
				setLayoutManager(gridLayout);
				GridLayout mainLayout = new GridLayout(1, false);
				mainLayout.marginHeight = 0;
				mainLayout.marginWidth = 0;
				mainLayout.horizontalSpacing = 0;
				mainLayout.verticalSpacing = -1;
				GridData mainLayoutData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL
					| GridData.VERTICAL_ALIGN_FILL | GridData.GRAB_VERTICAL);
				main.setLayoutManager(mainLayout);
				add(main);
				setConstraint(main, mainLayoutData);
				createInstanceNameLabel(main);
				

				IPreferenceStore pf = Activator.getDefault().getPreferenceStore();
				int cornerDim = pf.getInt(DiagramPreferences.CORNER_DIM);
				bottom.setCornerDimensions(new Dimension(cornerDim, cornerDim));
				GridLayout bottomLayout = new GridLayout(1, false);
				bottomLayout.marginHeight = 4;
				bottomLayout.marginWidth = 1;
				bottomLayout.horizontalSpacing = 2;
				bottom.setLayoutManager(bottomLayout);
				GridData bottomLayoutData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL
						| GridData.VERTICAL_ALIGN_FILL | GridData.GRAB_VERTICAL);	
				bottom.setOutline(false);
				
				ToolbarLayout bottomInputValuesFigureLayout = new ToolbarLayout(false);
				bottomInputValuesFigureLayout.setStretchMinorAxis(true);
				GridData bottomInputsFigureLayoutData = new GridData(
						GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL | GridData.VERTICAL_ALIGN_FILL);
				bottomInputsFigureLayoutData.verticalAlignment = SWT.TOP;
				main.add(bottom);
				main.setConstraint(bottom, bottomLayoutData);
				
				createDeviceInfoSection(bottom);
				
				ToolbarLayout bottomInputsLayout = new ToolbarLayout(false);
				bottomInputsLayout.setStretchMinorAxis(true);
				dataInputs.setLayoutManager(bottomInputsLayout);
				GridData bottomInputsLayoutData = new GridData(
						GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL | GridData.VERTICAL_ALIGN_FILL);
				dataInputs.setOpaque(false);
				bottom.add(dataInputs);
				bottom.setConstraint(dataInputs, bottomInputsLayoutData);
				createContentPane(bottom);
			}

			private void createInstanceNameLabel(Figure parent) {
				GridData instanceNameLayout = new GridData();
				instanceNameLayout.grabExcessHorizontalSpace = true;
				instanceNameLayout.horizontalAlignment = SWT.CENTER;
				instanceNameLabel.setText(getINamedElement().getName());
				instanceNameLabel.setTextAlignment(PositionConstants.CENTER);
				instanceNameLabel.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT));
				parent.add(instanceNameLabel);
				parent.setConstraint(instanceNameLabel, instanceNameLayout);
			}

			private void createDeviceInfoSection(Figure parent) {
				GridLayout deviceInfoLayout = new GridLayout();
				deviceInfoLayout.marginHeight = 0;
				deviceInfoLayout.horizontalSpacing = 2;
				deviceInfoLayout.verticalSpacing = 2;
				
				Figure deviceInfo = new Figure();
				deviceInfo.setLayoutManager(deviceInfoLayout);
				Label l = new Label(getModel().getType().getName());
				deviceInfo.add(l);
				l.setTextAlignment(PositionConstants.CENTER);
				l.setFont(JFaceResources.getFontRegistry().getItalic(JFaceResources.DEFAULT_FONT));
				deviceInfo.setConstraint(l, new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL));
				l.setBorder(new MarginBorder(0, 0, 10, 0));
				
				parent.add(deviceInfo);
				GridData deviceInfoLayoutData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL | GridData.VERTICAL_ALIGN_FILL);
				deviceInfoLayoutData.verticalAlignment = SWT.TOP;
				deviceInfoLayoutData.horizontalSpan = 2;
				parent.setConstraint(deviceInfo, deviceInfoLayoutData);
				
				deviceInfo.setBorder(new LineBorder() {	
					@Override
					public void paint(final IFigure figure, final Graphics graphics, final Insets insets) {
						tempRect.setBounds(getPaintRectangle(figure, insets));
						if (1 == (getWidth() & 1)) {
							tempRect.height--;
						}
						tempRect.shrink(getWidth() / 2, getWidth() / 2);
						graphics.setLineWidth(getWidth());
						graphics.setBackgroundColor(bottom.getBackgroundColor());
						FordiacFigureUtilities.paintEtchedBorder(graphics,
								new Rectangle(tempRect.x - 3, tempRect.y + tempRect.height - 3, tempRect.width + 5, 3));
					}
				});
			}
	
			private void createContentPane(RoundedRectangle container) {
				GridData contentPaneLyoutData = new GridData(
						GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL | GridData.VERTICAL_ALIGN_FILL);
				contentPaneLyoutData.verticalAlignment = SWT.TOP;
				contentPaneLyoutData.horizontalSpan = 2;
				contentPaneLyoutData.grabExcessHorizontalSpace = true;
				contentPane = new Figure();
				contentPane.setLayoutManager(new ToolbarLayout());
				container.add(contentPane);
				container.setConstraint(contentPane, contentPaneLyoutData);
			}

			public Label getName() {
				return instanceNameLabel;
			}
	
			public Figure getConnectionReferenceFigure() {
				return bottom;
			}
	
			@Override
			protected void fillShape(final Graphics graphics) {
			}
	
			@Override
			protected void outlineShape(final Graphics graphics) {
			}

			public Figure getDataInputs() {
				return dataInputs;
			}
	
			public Figure getContentPane() {
				return contentPane;
			}
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final ConnectionEditPart connection) {
		return new DeviceConnectionAnchor(getFigure().getConnectionReferenceFigure());
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final Request request) {
		return new DeviceConnectionAnchor(getFigure().getConnectionReferenceFigure());
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final ConnectionEditPart connection) {
		return new DeviceConnectionAnchor(getFigure().getConnectionReferenceFigure());
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final Request request) {
		return new DeviceConnectionAnchor(getFigure().getConnectionReferenceFigure());
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<?> getModelTargetConnections() {
		ArrayList<Object> connections = new ArrayList<Object>();
		connections.addAll(getModel().getInConnections());
		connections.addAll(super.getModelTargetConnections());
		return connections;
	}
}
