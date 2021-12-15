/*******************************************************************************
 * Copyright (c) 2012, 2014, 2018 Profactor GmbH, fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
 * 				 2021 Primetals Technologies Austria GmbH
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Monika Wenger
 *    - initial implementation
 *   Alois Zoitl - separated FBNetworkElement from instance name for better
 *                 direct editing of instance names
 *   Christoph Binder - removing dependency from application plugin
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.fbtester.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.fbtypeeditor.figures.FBTypeFigure;
import org.eclipse.fordiac.ide.gef.editparts.AbstractPositionableElementEditPart;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.jface.util.IPropertyChangeListener;

public class TesterFBEditPart extends AbstractPositionableElementEditPart {

	public TesterFBEditPart() {
		super();
	}
	
	@Override
	public FB getModel() {
		return (FB) super.getModel();
	}

	@Override
	protected Adapter createContentAdapter() {
		// Provide an empty content adpater as we don't want to react in the tester to
		// the classical FBN editing changes
		return new AdapterImpl();
	}

	@Override
	protected void createEditPolicies() {
		// should be readonly in fb tester
	}

	@Override
	public boolean understandsRequest(final Request req) {
		// should be readonly in fb tester
		return false;
	}

	@Override
	public void performRequest(final Request request) {
		// should be readonly in fb tester
	}

	@Override
	public void setSelected(final int value) {
		// should be readonly in fb tester
	}

	/**
	 * Update.
	 *
	 * @param bounds the bounds
	 */
	protected void update(final Rectangle bounds) {
		((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), bounds);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		updatePosition();
	}

	private void updatePosition() {
		if (getParent() != null && getParent().getViewer() != null && getParent().getViewer().getControl() != null) {
			final Rectangle rect = new Rectangle(-1, -1, -1, -1);
			update(rect);
		}
	}

	@Override
	protected PositionableElement getPositionableElement() {
		return getModel();
	}

	@Override
	public INamedElement getINamedElement() {
		return getModel();
	}

	@Override
	public Label getNameLabel() {
		return null;
	}

	@Override
	protected IPropertyChangeListener getPreferenceChangeListener() {
		return null;
	}

	@Override
	protected IFigure createFigureForModel() {
		// extend this if FunctionBlock gets extended!
		FBTypeFigure f = null;
		if (getModel() != null) {
			f = new FBTypeFigure(getModel().getType());
		} else {
			throw new IllegalArgumentException();
		}
		return f;
	}
	
	@Override
	public FBTypeFigure getFigure() {
		return (FBTypeFigure) super.getFigure();
	}
	
	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
		final IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		if (childEditPart instanceof InterfaceEditPart) {
			final InterfaceEditPart interfaceEditPart = (InterfaceEditPart) childEditPart;
			getTargetFigure(interfaceEditPart).add(child, getInterfaceElementIndex(interfaceEditPart));
		} else {
			getFigure().add(child, new GridData(GridData.HORIZONTAL_ALIGN_CENTER), index);
		}
	}
	
	private IFigure getTargetFigure(final InterfaceEditPart interfaceEditPart) {
		if (interfaceEditPart.isInput()) {
			if (interfaceEditPart.isEvent()) {
				return getFigure().getEventInputs();
			}
			if (interfaceEditPart.isAdapter()) {
				return getFigure().getSockets();
			}
			if (interfaceEditPart.isVariable()) {
				return getFigure().getDataInputs();
			}

		} else {
			if (interfaceEditPart.isEvent()) {
				return getFigure().getEventOutputs();
			}
			if (interfaceEditPart.isAdapter()) {
				return getFigure().getPlugs();
			}
			if (interfaceEditPart.isVariable()) {
				return getFigure().getDataOutputs();
			}
		}
		return getFigure();
	}

	private int getInterfaceElementIndex(final InterfaceEditPart interfaceEditPart) {
		final InterfaceList interfaceList = getModel().getInterface();
		if (interfaceEditPart.isInput()) {
			if (interfaceEditPart.isEvent()) {
				return interfaceList.getEventInputs().indexOf(interfaceEditPart.getModel());
			}
			if (interfaceEditPart.isAdapter()) {
				return interfaceList.getSockets().indexOf(interfaceEditPart.getModel());
			}
			if (interfaceEditPart.isVariable()) {
				return interfaceList.getInputVars().indexOf(interfaceEditPart.getModel());
			}
		} else {
			if (interfaceEditPart.isEvent()) {
				return interfaceList.getEventOutputs().indexOf(interfaceEditPart.getModel());
			}
			if (interfaceEditPart.isAdapter()) {
				return interfaceList.getPlugs().indexOf(interfaceEditPart.getModel());
			}
			if (interfaceEditPart.isVariable()) {
				return interfaceList.getOutputVars().indexOf(interfaceEditPart.getModel());
			}
		}
		return -1;
	}

	@Override
	protected void removeChildVisual(final EditPart childEditPart) {
		final IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		if (childEditPart instanceof InterfaceEditPart) {
			final InterfaceEditPart interfaceEditPart = (InterfaceEditPart) childEditPart;
			getTargetFigure(interfaceEditPart).remove(child);
		} else {
			super.removeChildVisual(childEditPart);
		}
	}

	@Override
	protected List<Object> getModelChildren() {
		final List<Object> elements = new ArrayList<>();
		elements.addAll(getModel().getInterface().getAllInterfaceElements());
		return elements;
	}
	
	

}
