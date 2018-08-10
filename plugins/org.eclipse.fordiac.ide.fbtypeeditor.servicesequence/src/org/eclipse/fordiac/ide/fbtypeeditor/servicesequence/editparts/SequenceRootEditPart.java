/*******************************************************************************
 * Copyright (c) 2008 - 2015 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayeredPane;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.Layer;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.ServiceSequenceEditPart.ServiceSequenceFigure;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.policies.ChangeInterfaceNameEditPolicy;
import org.eclipse.fordiac.ide.gef.draw2d.AdvancedLineBorder;
import org.eclipse.fordiac.ide.gef.editparts.LabelDirectEditManager;
import org.eclipse.fordiac.ide.gef.editparts.NameCellEditorLocator;
import org.eclipse.fordiac.ide.gef.policies.EmptyXYLayoutEditPolicy;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;

public class SequenceRootEditPart extends AbstractGraphicalEditPart {
	
	private EContentAdapter adapter = new EContentAdapter() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);		
			if(getCastedModel().eAdapters().contains(adapter)){
				refresh();
			}
		}
	};
	
	@Override
	public void activate() {
		if (!isActive()) {
			getCastedModel().eAdapters().add(adapter);
		}
		super.activate();
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			getCastedModel().eAdapters().remove(adapter);
		}
		super.deactivate();
	}
	
	public class ServiceFigure extends FreeformLayeredPane {
		private Figure leftFigure;
		private Figure middleFigure;
		private Figure rightFigure;
		private Label leftLabel;
		private Label rightLabel;
		private Layer serviceSequenceContainer;
		
		public ServiceFigure(){	
			setLayoutManager(new StackLayout());
			setForegroundColor(ColorConstants.blue);
			createBaseLayer();
			createInterfaceLayer();
			createServiceSequenceLayer();
		}
		
		private void createBaseLayer(){
			Layer baseLayer = new Layer();
			baseLayer.setBorder(new MarginBorder(0,10,0,10));
			GridLayout layout = new GridLayout(3,false);
			layout.horizontalSpacing = 0;
			layout.marginWidth = 0;
			layout.marginHeight = 0;
			layout.verticalSpacing = 0;
			baseLayer.setLayoutManager(layout);
			
			leftFigure = new Figure();
			GridLayout leftLayout = new GridLayout();
			leftFigure.setLayoutManager(leftLayout);
			leftLayout.horizontalSpacing = 0;
			leftLayout.marginWidth = 0;
			leftLayout.marginHeight = 0;
			leftLayout.verticalSpacing = 0;
			baseLayer.add(leftFigure);			
			
			middleFigure = new Figure();
			middleFigure.setPreferredSize(37, 0);
			middleFigure.setBorder(new AdvancedLineBorder(PositionConstants.EAST | PositionConstants.WEST));
			baseLayer.add(middleFigure);
			
			rightFigure = new Figure();	
			GridLayout rightLayout = new GridLayout();
			rightFigure.setLayoutManager(rightLayout);
			rightLayout.horizontalSpacing = 0;
			rightLayout.marginWidth = 0;
			rightLayout.marginHeight = 0;
			rightLayout.verticalSpacing = 0;
			baseLayer.add(rightFigure);
			
			GridData leftGridData = new GridData();
			leftGridData.horizontalAlignment = SWT.FILL;
			leftGridData.verticalAlignment = SWT.FILL;
			leftGridData.grabExcessVerticalSpace = true;
			leftGridData.grabExcessHorizontalSpace = true;	
			GridData middleGridData = new GridData();
			middleGridData.verticalAlignment = SWT.FILL;
			middleGridData.grabExcessVerticalSpace = true;
			GridData rightGridData = new GridData();
			rightGridData.horizontalAlignment = SWT.FILL;
			rightGridData.verticalAlignment = SWT.FILL;
			rightGridData.grabExcessVerticalSpace = true;
			rightGridData.grabExcessHorizontalSpace = true;
			
			layout.setConstraint(middleFigure, middleGridData);
			layout.setConstraint(leftFigure, leftGridData);
			layout.setConstraint(rightFigure, rightGridData);

			add(baseLayer);
		}
		
		private void createInterfaceLayer(){	
			Layer interfaceLayer = new Layer();
			interfaceLayer.setBorder(new MarginBorder(5,0,0,0));
			GridLayout layout = new GridLayout(2,true);
			layout.horizontalSpacing = 0;
			layout.marginWidth = 0;
			layout.marginHeight = 0;
			layout.verticalSpacing = 0;
			interfaceLayer.setLayoutManager(layout);
			
			leftLabel = new Label();
			leftLabel.setLabelAlignment(PositionConstants.RIGHT);
			leftLabel.setBorder(new MarginBorder(5,0,0,35));
			GridData leftLabelData = new GridData();
			leftLabelData.grabExcessHorizontalSpace = true;
			leftLabelData.horizontalAlignment = GridData.FILL;
			interfaceLayer.getLayoutManager().setConstraint(leftLabel, leftLabelData);
			interfaceLayer.add(leftLabel);
					
			rightLabel = new Label();
			rightLabel.setLabelAlignment(PositionConstants.LEFT);
			rightLabel.setBorder(new MarginBorder(5,35,0,0));		
			GridData rightLabelData = new GridData();
			rightLabelData.grabExcessHorizontalSpace = true;
			rightLabelData.horizontalAlignment = GridData.FILL;
			interfaceLayer.getLayoutManager().setConstraint(rightLabel, rightLabelData);
			interfaceLayer.add(rightLabel);
			
			add(interfaceLayer);
		}
		
		private void createServiceSequenceLayer(){
			serviceSequenceContainer = new Layer();
			serviceSequenceContainer.setBorder(new MarginBorder(20,10,5,10));
			GridLayout layout = new GridLayout();
			layout.horizontalSpacing = 0;
			layout.marginWidth = 0;
			serviceSequenceContainer.setLayoutManager(layout);
			add(serviceSequenceContainer);
		}
		
		public Figure getLeftFigure(){
			return leftFigure;
		}
		public Figure getMiddleFigure(){
			return middleFigure;
		}
		public Figure getRightFigure(){
			return rightFigure;
		}
		
		public Label getLeftLabel(){
			return leftLabel;
		}
		
		public Label getRightLabel(){
			return rightLabel;
		}
		
		public Layer getServiceSequenceContainer(){
			return serviceSequenceContainer;
		}
		
		public void setLeftLabelText(String name){
			leftLabel.setText(null != name ? name : ""); //$NON-NLS-1$
		}
		
		public void setRightLabelText(String name){
			rightLabel.setText(null != name ? name : ""); //$NON-NLS-1$
		}
	}

	@Override
	protected IFigure createFigure() {
		return new ServiceFigure();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EmptyXYLayoutEditPolicy());
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new ChangeInterfaceNameEditPolicy());
	}
	
	public boolean isLeft(DirectEditRequest request){
		Point point;
		if(null == request.getLocation()){
			point = new Point(request.getCellEditor().getControl().getLocation());
		}else{
			point = request.getLocation().getCopy();
		}
		getFigure().translateToRelative(point);
		return point.x < (getFigure().getClientArea().width / 2);
	}
	
	public DirectEditManager getManager(boolean isLeft) {
		Label l = isLeft ? ((ServiceFigure)getFigure()).getLeftLabel() : ((ServiceFigure)getFigure()).getRightLabel();
		return new LabelDirectEditManager(this, TextCellEditor.class, new NameCellEditorLocator(l), l);
	}

	@Override
	public void performRequest(final Request request) {
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT && request instanceof DirectEditRequest) {
			performDirectEdit(isLeft((DirectEditRequest) request));
		} else {
			super.performRequest(request);
		}
	}
	
	public void performDirectEdit(final char initialCharacter, boolean isLeft) {
		if (getManager(isLeft) instanceof LabelDirectEditManager) {
			((LabelDirectEditManager) getManager(isLeft)).show(initialCharacter);
		} else {
			performDirectEdit(isLeft);
		}
	}
	
	public void performDirectEdit(boolean isLeft) {
		getManager(isLeft).show();
	}
	
	
	
	public Service getCastedModel() {
		return ((FBType)getModel()).getService();
	}
	
	public FBType getFBType() {
		return (FBType)getModel();
	}
	
	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		ServiceFigure figure = (ServiceFigure) getFigure();
		if(null != getCastedModel()){
			if(null != getCastedModel().getLeftInterface()){
				figure.setLeftLabelText(null != getCastedModel().getLeftInterface() ? getCastedModel().getLeftInterface().getName() : ""); //$NON-NLS-1$
			}
			if(null != getCastedModel().getRightInterface()){
				figure.setRightLabelText(null != getCastedModel().getRightInterface() ? getCastedModel().getRightInterface().getName() : ""); //$NON-NLS-1$
			}
		}
	}
	
	@Override
	protected List<?> getModelChildren() {
		ArrayList<Object> children = new ArrayList<Object>();
		if (getCastedModel().getServiceSequence() != null) {
			children.addAll(getCastedModel().getServiceSequence());
		}
		return children;
	}
	
	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
		if (childEditPart instanceof ServiceSequenceEditPart) {
			ServiceSequenceFigure child = (ServiceSequenceFigure) ((GraphicalEditPart) childEditPart).getFigure();
			ServiceFigure thisFigure = (ServiceFigure) getFigure();
			GridData childData = new GridData();
			childData.grabExcessHorizontalSpace = true;
			childData.horizontalAlignment = GridData.FILL;
			thisFigure.getServiceSequenceContainer().add(child);
			thisFigure.getServiceSequenceContainer().getLayoutManager().setConstraint(child, childData);
		} 
	}
	
	@Override
	protected void removeChildVisual(final EditPart childEditPart) {
		if (childEditPart instanceof ServiceSequenceEditPart) {
			ServiceSequenceFigure child = (ServiceSequenceFigure) ((GraphicalEditPart) childEditPart).getFigure();
			ServiceFigure thisFigure = (ServiceFigure) getFigure();
			thisFigure.getServiceSequenceContainer().remove(child);
		}
	}	
}
