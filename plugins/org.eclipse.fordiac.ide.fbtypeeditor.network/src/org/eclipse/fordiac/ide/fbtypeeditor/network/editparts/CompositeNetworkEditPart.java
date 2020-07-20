/*******************************************************************************
 * Copyright (c) 2008 - 2012, 2014, 2016, 2017  Profactor GmbH, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.network.editparts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.emf.common.notify.Adapter;
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
	private Adapter adapter;

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
	public Adapter getContentAdapter() {
		if (null == adapter) {
			adapter = new EContentAdapter() {
				@Override
				public void notifyChanged(final Notification notification) {
					switch (notification.getEventType()) {
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
					default:
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
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		// // handles constraint changes of model elements and creation of new
		// // model elements
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new CompositeFBNetworkLayoutEditPolicy());

	}

	/**
	 * Returns the children of the FBNetwork.
	 *
	 * @return the list of children s
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@Override
	protected List<?> getModelChildren() {
		if (getModel() != null) {
			ArrayList<Object> children = new ArrayList<>(super.getModelChildren());
			CompositeFBType fbType = getFbType();
			children.addAll(fbType.getInterfaceList().getEventInputs());
			children.addAll(fbType.getInterfaceList().getEventOutputs());
			children.addAll(fbType.getInterfaceList().getInputVars());
			children.addAll(fbType.getInterfaceList().getOutputVars());
			if (fbType instanceof SubAppType) {
				children.addAll(fbType.getInterfaceList().getPlugs());
				children.addAll(fbType.getInterfaceList().getSockets());
			}
			return children;
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * Adds the childEditParts figure to the corresponding container.
	 *
	 * @param childEditPart the child edit part
	 * @param index         the index
	 */
	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
		EditPart refEditPart = null;
		if (index < getChildren().size()) {
			refEditPart = (EditPart) getChildren().get(index);
		}
		if (childEditPart instanceof InterfaceEditPart) {
			IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
			insertChild(getChildVisualContainer(childEditPart), refEditPart, child);
			child.setVisible(isVarVisible(childEditPart));
		} else {
			super.addChildVisual(childEditPart, -1);
		}
	}

	public void addChildVisualInterfaceElement(final InterfaceEditPart childEditPart) {
		IFigure child = childEditPart.getFigure();
		if (childEditPart.getModel().isIsInput()) { // use model isInput! because EditPart.isInput treats inputs as
													// outputs for visual appearance
			if (childEditPart.isEvent()) {
				int index = getFbType().getInterfaceList().getEventInputs().indexOf(childEditPart.getModel());
				int containerSize = getLeftEventInterfaceContainer().getChildren().size();
				getLeftEventInterfaceContainer().add(child, (index >= containerSize) ? containerSize : index);
			} else if (childEditPart.isAdapter()) {
				int index = getFbType().getInterfaceList().getSockets().indexOf(childEditPart.getModel());
				int containerSize = getLeftAdapterInterfaceContainer().getChildren().size();
				getLeftAdapterInterfaceContainer().add(child, (index >= containerSize) ? containerSize : index);
			} else {
				int index = getFbType().getInterfaceList().getInputVars().indexOf(childEditPart.getModel());
				int containerSize = getLeftVarInterfaceContainer().getChildren().size();
				getLeftVarInterfaceContainer().add(child, (index >= containerSize) ? containerSize : index);
			}
		} else {
			if (childEditPart.isEvent()) {
				int index = getFbType().getInterfaceList().getEventOutputs().indexOf(childEditPart.getModel());
				int containerSize = getRightEventInterfaceContainer().getChildren().size();
				getRightEventInterfaceContainer().add(child, (index >= containerSize) ? containerSize : index);
			} else if (childEditPart.isAdapter()) {
				int index = getFbType().getInterfaceList().getPlugs().indexOf(childEditPart.getModel());
				int containerSize = getRightAdapterInterfaceContainer().getChildren().size();
				getRightAdapterInterfaceContainer().add(child, (index >= containerSize) ? containerSize : index);
			} else {
				int index = getFbType().getInterfaceList().getOutputVars().indexOf(childEditPart.getModel());
				int containerSize = getRightVarInterfaceContainer().getChildren().size();
				getRightVarInterfaceContainer().add(child, (index >= containerSize) ? containerSize : index);
			}
		}
	}

	private static void insertChild(Figure container, EditPart refEditPart, IFigure child) {
		if (null != refEditPart) {
			int index = container.getChildren().indexOf(((GraphicalEditPart) refEditPart).getFigure());
			container.add(child, index);
		} else {
			container.add(child);
		}
	}

	/**
	 * Removes the childEditParts figures from the correct container.
	 *
	 * @param childEditPart the child edit part
	 */
	@Override
	protected void removeChildVisual(final EditPart childEditPart) {
		if (childEditPart instanceof InterfaceEditPart) {
			IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
			try {
				removeChildVisualInterfaceElement((InterfaceEditPart) childEditPart);
			} catch (IllegalArgumentException e) {
				getCastedFigure().remove(child);
			}
		} else {
			super.removeChildVisual(childEditPart);
		}
	}

	private Figure getChildVisualContainer(final EditPart childEditPart) {
		if (((InterfaceEditPart) childEditPart).getModel().isIsInput()) {
			if (((InterfaceEditPart) childEditPart).isEvent()) {
				return getLeftEventInterfaceContainer();
			} else {
				if (isVarVisible(childEditPart)) {
					return getLeftVarInterfaceContainer();
				} else {
					return getLeftInterfaceContainer();
				}
			}
		} else {
			if (((InterfaceEditPart) childEditPart).isEvent()) {
				return getRightEventInterfaceContainer();
			} else {
				if (isVarVisible(childEditPart)) {
					return getRightVarInterfaceContainer();
				} else {
					return getRightInterfaceContainer();
				}
			}
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
		// addChildvisual needs to be done before the children list is updated in order
		// to allow add child visual to determine the place to put the part
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

		// addChildvisual needs to be done before the children list is updated in order
		// to allow add child visual to determine the place to put the part
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
		return (CompositeFBType) getModel().eContainer();
	}

	protected Boolean isVarVisible(final EditPart childEditPart) {
		return !(childEditPart.getModel() instanceof AdapterDeclaration);
	}
}
