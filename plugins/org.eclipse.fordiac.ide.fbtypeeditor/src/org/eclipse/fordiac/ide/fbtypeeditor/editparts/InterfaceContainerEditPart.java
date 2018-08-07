/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.OrderedLayout;
import org.eclipse.draw2d.TextUtilities;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.fbtypeeditor.policies.EventInputContainerLayoutEditPolicy;
import org.eclipse.fordiac.ide.fbtypeeditor.policies.EventOutputContainerLayoutEditPolicy;
import org.eclipse.fordiac.ide.fbtypeeditor.policies.PlugContainerLayoutEditPolicy;
import org.eclipse.fordiac.ide.fbtypeeditor.policies.SocketContainerLayoutEditPolicy;
import org.eclipse.fordiac.ide.fbtypeeditor.policies.VariableInputContainerLayoutEditPolicy;
import org.eclipse.fordiac.ide.fbtypeeditor.policies.VariableOutputContainerLayoutEditPolicy;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

public class InterfaceContainerEditPart extends AbstractGraphicalEditPart {

	public class InterfaceContainerFigure extends Figure {
		public InterfaceContainerFigure() {
			FlowLayout layout = new FlowLayout();
			layout.setMajorSpacing(0);
			layout.setMinorSpacing(0);
			layout.setHorizontal(false);
			layout.setStretchMinorAxis(true);
			if (getModel() instanceof VariableInputContainer || 
					getModel() instanceof SocketContainer){
			  layout.setMinorAlignment(OrderedLayout.ALIGN_BOTTOMRIGHT);
			}
			setLayoutManager(layout);
			setPreferredSize(30, 10);
		}
	}

	private final EContentAdapter econtentAdapter = new EContentAdapter() {
		@Override
		public void notifyChanged(Notification notification) {
			refreshChildren();
			super.notifyChanged(notification);
		}
	};

	@Override
	public void activate() {
		super.activate();
		getModel().getFbType().eAdapters().add(econtentAdapter);
	}

	@Override
	public void deactivate() {
		super.deactivate();
		getModel().getFbType().eAdapters().remove(econtentAdapter);
	}

	@Override
	protected IFigure createFigure() {
		return new InterfaceContainerFigure();
	}

	@Override
	protected void createEditPolicies() {
		if (getModel() instanceof EventInputContainer) {
			installEditPolicy(EditPolicy.LAYOUT_ROLE,
					new EventInputContainerLayoutEditPolicy());
		}
		if (getModel() instanceof EventOutputContainer) {
			installEditPolicy(EditPolicy.LAYOUT_ROLE,
					new EventOutputContainerLayoutEditPolicy());
		}
		if (getModel() instanceof VariableInputContainer){
			installEditPolicy(EditPolicy.LAYOUT_ROLE,
					new VariableInputContainerLayoutEditPolicy());
		}
		if(getModel() instanceof SocketContainer){
			installEditPolicy(EditPolicy.LAYOUT_ROLE,
					new SocketContainerLayoutEditPolicy());
		}
		if (getModel() instanceof VariableOutputContainer ) {
			installEditPolicy(EditPolicy.LAYOUT_ROLE,
					new VariableOutputContainerLayoutEditPolicy());
		}
		if(getModel() instanceof PlugContainer) {
			installEditPolicy(EditPolicy.LAYOUT_ROLE,
					new PlugContainerLayoutEditPolicy());
		}
	}

	@Override
	public AbstractContainerElement getModel() {
		return (AbstractContainerElement) super.getModel();
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected List getModelChildren() {
		return getModel().getChildren();
	}

	@Override
	protected void addChildVisual(EditPart childEditPart, int index) {
		if (getContentPane().getChildren().size() == 0) {
			getContentPane().setPreferredSize(null);
		}
		super.addChildVisual(childEditPart, index);
	}

	@Override
	protected void removeChildVisual(EditPart childEditPart) {
		super.removeChildVisual(childEditPart);
		if (getContentPane().getChildren().size() == 0) {
			Dimension dim = TextUtilities.INSTANCE.getTextExtents("INT", getContentPane().getFont());
			dim.height =(int) (dim.height * 0.66);
			getContentPane().setPreferredSize(dim);
		}
	}
}