/*******************************************************************************
 * Copyright (c) 2008 - 2015 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *               2021 Johannes Kepler University Linz
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
 *   Melanie Winter - made expandable
 *   Felix Roithmayr - added startstate and type support
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.DeleteServiceSequenceCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.figures.SequenceFigure;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.policies.TransactionLayoutEditPolicy;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDirectEditableEditPart;
import org.eclipse.fordiac.ide.gef.editparts.LabelCellEditorLocator;
import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.fordiac.ide.gef.policies.HighlightEditPolicy;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.swt.SWT;

public class ServiceSequenceEditPart extends AbstractDirectEditableEditPart /* ResizableCompartmentEditPart */ {


	private boolean isExpanded = true;


	private final Adapter adapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			refresh();

		}
	};

	@Override
	public void activate() {
		if (!isActive()) {
			getModel().eAdapters().add(adapter);
		}
		super.activate();
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			getModel().eAdapters().remove(adapter);
		}
		super.deactivate();
	}



	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ComponentEditPolicy() {
			@Override
			protected Command getDeleteCommand(final GroupRequest request) {
				return new DeleteServiceSequenceCommand(getModel().getService().getFBType(),
						getModel());
			}

		});
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new HighlightEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new TransactionLayoutEditPolicy());

	}

	public void toggleExpanded() {
		isExpanded = !isExpanded;
		refresh();
		getFigure().setExpanded(isExpanded);
	}

	@Override
	public SequenceFigure getFigure() {
		return (SequenceFigure) super.getFigure();
	}

	@Override
	public ServiceSequence getModel() {
		return (ServiceSequence) super.getModel();
	}

	@Override
	protected IFigure createFigure() {
		final SequenceFigure figure = new SequenceFigure(isExpanded);
		figure.setBorder(new MarginBorder(new Insets(12, 0, 0, 0)));
		final GridData layoutData = new GridData(SWT.FILL, SWT.NONE, true, false);
		figure.getLayoutManager().setConstraint(figure, layoutData);

		return figure;
	}

	@Override
	protected List<ServiceTransaction> getModelChildren() {
		if (isExpanded) {
			return new ArrayList<>(getModel().getServiceTransaction());
		}
		return Collections.emptyList();
	}

	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
		if (childEditPart instanceof TransactionEditPart) {
			final IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
			getFigure().getTransactionContainer().add(child, index);
		}
	}

	@Override
	protected void removeChildVisual(final EditPart childEditPart) {
		final IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		if (childEditPart instanceof TransactionEditPart) {
			getFigure().getTransactionContainer().remove(child);
		}
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		final SequenceFigure figure = getFigure();
		if (null != getModel()) {
			figure.setLabelText(getModel().getName(), getModel().getComment(), getModel().getServiceSequenceType(),
					getModel().getStartState());
		}
	}

	@Override
	public INamedElement getINamedElement() {
		return getModel();
	}

	@Override
	protected DirectEditManager createDirectEditManager() {
		final DirectEditManager dem = super.createDirectEditManager();
		final CellEditorLocator locator = new LabelCellEditorLocator(getFigure().getNameLabel(),
				getZoomManager(), (FigureCanvas) getViewer().getControl());
		dem.setLocator(locator);
		return dem;
	}

	private ZoomManager getZoomManager() {
		return ((ZoomScalableFreeformRootEditPart) getRoot()).getZoomManager();
	}

	@Override
	public Label getNameLabel() {
		return new Label(getModel().getName());
	}

	public boolean isExpanded() {
		return isExpanded;
	}

	public void setExpanded(final boolean isExpanded) {
		this.isExpanded = !isExpanded;
		refresh();
	}
}
