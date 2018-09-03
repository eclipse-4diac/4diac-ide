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
 *   - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

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
	
	protected List<?> getInterfaceChildren() {
		return Collections.EMPTY_LIST;
	}

	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
		super.addChildVisual(childEditPart, index);
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
}
