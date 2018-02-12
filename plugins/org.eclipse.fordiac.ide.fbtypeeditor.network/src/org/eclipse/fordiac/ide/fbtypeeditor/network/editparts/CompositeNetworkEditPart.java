/*******************************************************************************
 * Copyright (c) 2008 - 2012, 2014, 2016, 2017  Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.network.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.application.editparts.EditorWithInterfaceEditPart;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;

/**
 * Edit Part for the visualization of Composite Networks.
 * 
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class CompositeNetworkEditPart extends EditorWithInterfaceEditPart {

	/** The adapter. */
	protected EContentAdapter adapter;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#activate()
	 */
	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			getModel().eAdapters().add(getContentAdapter());
			getModel().eContainer().eAdapters().add(getContentAdapter());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#deactivate()
	 */
	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			getModel().eAdapters().remove(getContentAdapter());
			getModel().eContainer().eAdapters().remove(getContentAdapter());
		}
	}

	/**
	 * Gets the content adapter.
	 * 
	 * @return the content adapter
	 */
	public EContentAdapter getContentAdapter() {
		if (adapter == null) {
			adapter = new EContentAdapter() {
				@Override
				public void notifyChanged(final Notification notification) {
					int type = notification.getEventType();
					switch (type) {
					case Notification.ADD:
					case Notification.ADD_MANY:
						refreshChildren();
						break;
					case Notification.MOVE:
						if (notification.getNewValue() instanceof IInterfaceElement) {
							refreshChildren();
						}
						break;
					case Notification.REMOVE:
					case Notification.REMOVE_MANY:
						refreshChildren();
						break;
					case Notification.SET:
						//refreshVisuals();
						break;
					}
				}
			};
		}
		return adapter;
	}

	/**
	 * Creates the EditPolicies used for this EditPart.
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new RootComponentEditPolicy());
		// // handles constraint changes of model elements and creation of new
		// // model elements
		installEditPolicy(EditPolicy.LAYOUT_ROLE,
				new CompositeFBNetworkLayoutEditPolicy());

	}

	/**
	 * Returns the children of the FBNetwork.
	 * 
	 * @return the list of children s
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@Override
	protected List<?> getModelChildren() {
		ArrayList<Object> children = new ArrayList<>();
		if (getModel() != null) {
			CompositeFBType fbType = getFbType();
			children.addAll(fbType.getInterfaceList().getEventInputs());
			children.addAll(fbType.getInterfaceList().getEventOutputs());
			children.addAll(fbType.getInterfaceList().getInputVars());
			children.addAll(fbType.getInterfaceList().getOutputVars());
			if(fbType instanceof SubAppType){
				children.addAll(fbType.getInterfaceList().getPlugs());
				children.addAll(fbType.getInterfaceList().getSockets());
			}
			children.addAll(super.getModelChildren());
		} 
		 return children;
	}

	/**
	 * Adds the childEditParts figure to the corresponding container.
	 * 
	 * @param childEditPart
	 *            the child edit part
	 * @param index
	 *            the index
	 */
	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
		boolean visible = true;
		if (childEditPart instanceof InterfaceEditPart) {
			IInterfaceElement iElement = ((InterfaceEditPart) childEditPart).getModel();
			if (iElement instanceof AdapterDeclaration){
					//if we are in a subapptype we want to show the adapter as type interface element
					visible = iElement.eContainer().eContainer() instanceof SubAppType;
			}
		}
		
		EditPart refEditPart = null;
		if(index < getChildren().size()){
			refEditPart = (EditPart)getChildren().get(index);
		}
		
		if (childEditPart instanceof InterfaceEditPart) {
			IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
			if (((InterfaceEditPart) childEditPart).getModel().isIsInput()) {			
				if (((InterfaceEditPart) childEditPart).isEvent()) {
					insertChild(getLeftEventInterfaceContainer(), refEditPart, child);
				} else {
					if (visible) { // add adapter interface elemetns directly to the container and set them to visible = false
						insertChild(getLeftVarInterfaceContainer(), refEditPart, child);
					} else {
						insertChild(getLeftInterfaceContainer(), refEditPart, child);
					}
				}
			} else {
				if (((InterfaceEditPart) childEditPart).isEvent()) {
					insertChild(getRightEventInterfaceContainer(), refEditPart, child);
				} else {
					if (visible) { // add adapter interface elemetns directly to the container and set them to visible = false
						insertChild(getRightVarInterfaceContainer(), refEditPart, child);
					} else {
						insertChild(getRightInterfaceContainer(), refEditPart, child);
					}
				}
			}
			child.setVisible(visible);
		} else {
			super.addChildVisual(childEditPart, -1);
		}
	}

	private static void insertChild(Figure container,
			EditPart refEditPart, IFigure child) {
		if(null != refEditPart){
			int index = container.getChildren().indexOf(((GraphicalEditPart) refEditPart).getFigure());
			container.add(child, index);
		}else{
			container.add(child);
		}
	}

	/**
	 * Removes the childEditParts figures from the correct container.
	 * 
	 * @param childEditPart
	 *            the child edit part
	 */
	@Override
	protected void removeChildVisual(final EditPart childEditPart) {
		boolean visible = true;
		if (childEditPart.getModel() instanceof AdapterDeclaration) {
			visible = false;
		}

		if (childEditPart instanceof InterfaceEditPart){
			if (((InterfaceEditPart) childEditPart).getModel().isIsInput()) {
				IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
				if (((InterfaceEditPart) childEditPart).isEvent()) {
					getLeftEventInterfaceContainer().remove(child);
				} else {
					if (visible) {
						getLeftVarInterfaceContainer().remove(child);
					} else{
						getLeftInterfaceContainer().remove(child);
					}
				}
			} else {
				IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
				if (((InterfaceEditPart) childEditPart).isEvent()) {
					getRightEventInterfaceContainer().remove(child);
				} else {
					if (visible) {
						getRightVarInterfaceContainer().remove(child);
					} else {
						getRightInterfaceContainer().remove(child);
					}
				}
			}
		} else {
			super.removeChildVisual(childEditPart);
		}
	}
	
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void reorderChild(EditPart editpart, int index) {
		// Save the constraint of the child so that it does not
		// get lost during the remove and re-add.
		IFigure childFigure = ((GraphicalEditPart) editpart).getFigure();
		LayoutManager layout = getContentPane().getLayoutManager();
		Object constraint = null;
		if (layout != null) {
			constraint = layout.getConstraint(childFigure);
		}
		
		removeChildVisual(editpart);
		//addChildvisual needs to be done before the children list is updated in order to allow add child visual to determine the place to put the part
		addChildVisual(editpart, index);  
		List children = getChildren();
		children.remove(editpart);
		children.add(index, editpart);
		
		setLayoutConstraint(editpart, childFigure, constraint);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void addChild(EditPart child, int index) {
		Assert.isNotNull(child);
		if (index == -1) {
			index = getChildren().size();
		}
		if (children == null) {
			children = new ArrayList(2);
		}

		//addChildvisual needs to be done before the children list is updated in order to allow add child visual to determine the place to put the part
		addChildVisual(child, index);
		children.add(index, child);
		child.setParent(this);
		child.addNotify();

		if (isActive()) {
			child.activate();
		}
		fireChildAdded(child, index);
	}

	
	private CompositeFBType getFbType() {
		return (CompositeFBType)getModel().eContainer();
	}
}
