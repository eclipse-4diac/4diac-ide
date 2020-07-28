/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 				 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *   - initial API and implementation and/or initial documentation
 *   Daniel Lindhuber
 *   - added possibility to free interface elements for layouting
 *   Alois Zoitl - Integrated code from child classes to reduce code duplication
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Layer;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.OrderedLayout;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.gef.editparts.AbstractFBNetworkEditPart;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Point;

public abstract class EditorWithInterfaceEditPart extends AbstractFBNetworkEditPart {
	private Figure leftInterfaceContainer;
	private Figure leftEventContainer;
	private Figure leftVarContainer;
	private Figure leftAdapterContainer;
	private Figure rightInterfaceContainer;
	private Figure rightEventContainer;
	private Figure rightVarContainer;
	private Figure rightAdapterContainer;
	private ControlListener controlListener;

	@Override
	protected IFigure createFigure() {
		leftInterfaceContainer = new Figure();
		leftEventContainer = new Figure();
		leftVarContainer = new Figure();
		leftAdapterContainer = new Figure();
		rightInterfaceContainer = new Figure();
		rightEventContainer = new Figure();
		rightVarContainer = new Figure();
		rightAdapterContainer = new Figure();
		ToolbarLayout tbl;
		ToolbarLayout tbl1;
		ToolbarLayout tbl2;
		ToolbarLayout tbl3;
		leftInterfaceContainer.setLayoutManager(tbl = new ToolbarLayout(false));
		leftEventContainer.setLayoutManager(tbl1 = new ToolbarLayout(false));
		leftVarContainer.setLayoutManager(tbl2 = new ToolbarLayout(false));
		leftAdapterContainer.setLayoutManager(tbl3 = new ToolbarLayout(false));
		tbl.setMinorAlignment(OrderedLayout.ALIGN_BOTTOMRIGHT);
		tbl1.setMinorAlignment(OrderedLayout.ALIGN_BOTTOMRIGHT);
		tbl2.setMinorAlignment(OrderedLayout.ALIGN_BOTTOMRIGHT);
		tbl3.setMinorAlignment(OrderedLayout.ALIGN_BOTTOMRIGHT);
		leftInterfaceContainer.add(leftEventContainer);
		leftInterfaceContainer.add(leftVarContainer);
		leftInterfaceContainer.add(leftAdapterContainer);
		rightInterfaceContainer.setLayoutManager((new ToolbarLayout(false)));
		rightEventContainer.setLayoutManager((new ToolbarLayout(false)));
		rightVarContainer.setLayoutManager((new ToolbarLayout(false)));
		rightAdapterContainer.setLayoutManager((new ToolbarLayout(false)));
		rightInterfaceContainer.add(rightEventContainer);
		rightInterfaceContainer.add(rightVarContainer);
		rightInterfaceContainer.add(rightAdapterContainer);
		Layer editorFigure = new FreeformLayer();
		editorFigure.setBorder(new MarginBorder(10));
		editorFigure.setLayoutManager(new FreeformLayout());
		editorFigure.setOpaque(false);
		editorFigure.add(leftInterfaceContainer);
		editorFigure.add(rightInterfaceContainer);
		updateRouter(editorFigure);
		return editorFigure;
	}

	public FreeformLayer getCastedFigure() {
		return (FreeformLayer) getFigure();
	}

	public Figure getLeftInterfaceContainer() {
		return leftInterfaceContainer;
	}

	public Figure getLeftEventInterfaceContainer() {
		return leftEventContainer;
	}

	public Figure getLeftVarInterfaceContainer() {
		return leftVarContainer;
	}

	public Figure getLeftAdapterInterfaceContainer() {
		return leftAdapterContainer;
	}

	public Figure getRightInterfaceContainer() {
		return rightInterfaceContainer;
	}

	public Figure getRightEventInterfaceContainer() {
		return rightEventContainer;
	}

	public Figure getRightVarInterfaceContainer() {
		return rightVarContainer;
	}

	public Figure getRightAdapterInterfaceContainer() {
		return rightAdapterContainer;
	}

	protected abstract InterfaceList getInterfaceList();

	@Override
	protected List<?> getModelChildren() {
		if (getModel() != null) {
			ArrayList<Object> children = new ArrayList<>(super.getModelChildren());
			InterfaceList ifList = getInterfaceList();
			children.addAll(ifList.getEventInputs());
			children.addAll(ifList.getEventOutputs());
			children.addAll(ifList.getInputVars());
			children.addAll(ifList.getOutputVars());
			if (showAdapterPorts()) {
				children.addAll(ifList.getPlugs());
				children.addAll(ifList.getSockets());
			}
			return children;
		}
		return Collections.emptyList();
	}

	private boolean showAdapterPorts() {
		// show adapters if it is not a cfb type
		return !((getModel().eContainer() instanceof CompositeFBType)
				&& (!(getModel().eContainer() instanceof SubAppType)));
	}

	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
		if (childEditPart instanceof InterfaceEditPart) {
			addChildVisualInterfaceElement((InterfaceEditPart) childEditPart);
		} else {
			super.addChildVisual(childEditPart, index);
		}
	}

	protected void updateInterfacePosition() {
		if (getParent() != null && getParent().getViewer() != null && getParent().getViewer().getControl() != null) {
			int xRight = Integer.MIN_VALUE;
			for (Object obj : getChildren()) {
				if (obj instanceof AbstractFBNElementEditPart) {
					AbstractFBNElementEditPart part = (AbstractFBNElementEditPart) obj;
					int xNew = part.getModel().getX() + 150;
					xNew += ((AbstractGraphicalEditPart) part).getFigure().getPreferredSize(-1, -1).width;
					xRight = Math.max(xNew, xRight);
				}
			}
			Point p = getParent().getViewer().getControl().getSize();
			xRight = Math.max(xRight, p.x - getRightInterfaceContainer().getSize().width - 20);
			Rectangle rect = new Rectangle(xRight, 0, -1, -1);
			getContentPane().setConstraint(getRightInterfaceContainer(), rect);
			rect = new Rectangle(0, 0, -1, -1);
			getContentPane().setConstraint(getLeftInterfaceContainer(), rect);
		}
	}

	/**
	 * positions the interface containers within the editor.
	 */
	@Override
	protected void refreshVisuals() {
		if (controlListener == null) {
			controlListener = new ControlListener() {
				@Override
				public void controlResized(final ControlEvent e) {
					updateInterfacePosition();
				}

				@Override
				public void controlMoved(final ControlEvent e) {
				}
			};
			getParent().getViewer().getControl().addControlListener(controlListener);
		}
		updateInterfacePosition();
	}

	@Override
	public void deactivate() {
		super.deactivate();
		if (controlListener != null && getParent() != null && getParent().getViewer() != null
				&& getParent().getViewer().getControl() != null) {
			getParent().getViewer().getControl().removeControlListener(controlListener);
		}
	}

	public void enableElkLayouting(InterfaceEditPart part) {
		removeChildVisual(part);
		getCastedFigure().add(part.getFigure());
	}

	/**
	 * Removes the childEditParts figures from the correct container.
	 *
	 * @param childEditPart the child edit part
	 */
	@Override
	protected void removeChildVisual(final EditPart childEditPart) {
		if (childEditPart instanceof InterfaceEditPart) {
			removeChildVisualInterfaceElement((InterfaceEditPart) childEditPart);
		} else {
			super.removeChildVisual(childEditPart);
		}
	}

	public void removeChildVisualInterfaceElement(final InterfaceEditPart childEditPart) {
		IFigure child = childEditPart.getFigure();
		IFigure container = getChildVisualContainer(childEditPart);
		if (child.getParent() == container) {
			container.remove(child);
		} else {
			getCastedFigure().remove(child);
		}
	}

	protected Figure getChildVisualContainer(final InterfaceEditPart childEditPart) {
		if (childEditPart.getModel().isIsInput()) {
			if (childEditPart.isEvent()) {
				return getLeftEventInterfaceContainer();
			} else if (childEditPart.isAdapter()) {
				return (showAdapterPorts()) ? getLeftAdapterInterfaceContainer() : getLeftInterfaceContainer();
			} else {
				return getLeftVarInterfaceContainer();
			}
		} else {
			if (childEditPart.isEvent()) {
				return getRightEventInterfaceContainer();
			} else if (childEditPart.isAdapter()) {
				return (showAdapterPorts()) ? getRightAdapterInterfaceContainer() : getRightInterfaceContainer();
			} else {
				return getRightVarInterfaceContainer();
			}
		}
	}

	public void addChildVisualInterfaceElement(final InterfaceEditPart childEditPart) {
		IFigure child = childEditPart.getFigure();
		InterfaceList ifList = getInterfaceList();
		Figure targetFigure = getChildVisualContainer(childEditPart);
		int index = 0;
		if (childEditPart.getModel().isIsInput()) { // use model isInput! because EditPart.isInput treats inputs as
													// outputs for visual appearance
			if (childEditPart.isEvent()) {
				index = ifList.getEventInputs().indexOf(childEditPart.getModel());
			} else if (childEditPart.isAdapter()) {
				index = ifList.getSockets().indexOf(childEditPart.getModel());
			} else {
				index = ifList.getInputVars().indexOf(childEditPart.getModel());
			}
		} else {
			if (childEditPart.isEvent()) {
				index = ifList.getEventOutputs().indexOf(childEditPart.getModel());
			} else if (childEditPart.isAdapter()) {
				index = ifList.getPlugs().indexOf(childEditPart.getModel());
			} else {
				index = ifList.getOutputVars().indexOf(childEditPart.getModel());
			}
		}
		int containerSize = targetFigure.getChildren().size();
		targetFigure.add(child, (index >= containerSize) ? containerSize : index);
		child.setVisible(isVarVisible(childEditPart));
	}

	@SuppressWarnings("static-method") // this method can be overridden so that editors can hide certain interface
										// elements (e.g., adapters in CFBs)
	protected Boolean isVarVisible(final EditPart childEditPart) {
		return true;
	}

}
