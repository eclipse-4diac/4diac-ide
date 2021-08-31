/*******************************************************************************
 * Copyright (c) 2008 - 2015 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.figures.SequenceFigure;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.figures.ServiceFigure;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.policies.ChangeInterfaceNameEditPolicy;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.policies.TransactionLayoutEditPolicy;
import org.eclipse.fordiac.ide.gef.editparts.LabelDirectEditManager;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.swt.SWT;

public class SequenceRootEditPart extends AbstractGraphicalEditPart {

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

	@Override
	protected IFigure createFigure() {
		return new ServiceFigure();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new TransactionLayoutEditPolicy());
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new ChangeInterfaceNameEditPolicy());
	}

	public boolean isLeft(final DirectEditRequest request) {
		Point point;
		if (null == request.getLocation()) {
			point = new Point(request.getCellEditor().getControl().getLocation());
		} else {
			point = request.getLocation().getCopy();
		}
		getFigure().translateToRelative(point);
		return point.x < (getFigure().getClientArea().width / 2);
	}

	public DirectEditManager getManager(final boolean isLeft) {
		final Label l = isLeft ? ((ServiceFigure) getFigure()).getLeftLabel()
				: ((ServiceFigure) getFigure()).getRightLabel();
		return new LabelDirectEditManager(this, l);
	}

	@Override
	public void performRequest(final Request request) {
		if ((request.getType() == RequestConstants.REQ_DIRECT_EDIT) && (request instanceof DirectEditRequest)) {
			performDirectEdit(isLeft((DirectEditRequest) request));
		} else {
			super.performRequest(request);
		}
	}

	public void performDirectEdit(final char initialCharacter, final boolean isLeft) {
		if (getManager(isLeft) instanceof LabelDirectEditManager) {
			((LabelDirectEditManager) getManager(isLeft)).show(initialCharacter);
		} else {
			performDirectEdit(isLeft);
		}
	}

	public void performDirectEdit(final boolean isLeft) {
		getManager(isLeft).show();
	}

	public Service getCastedModel() {
		return ((FBType) getModel()).getService();
	}

	public FBType getFBType() {
		return (FBType) getModel();
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		final ServiceFigure figure = (ServiceFigure) getFigure();
		if (null != getCastedModel()) {
			if (null != getCastedModel().getLeftInterface()) {
				figure.setLeftLabelText(
						null != getCastedModel().getLeftInterface() ? getCastedModel().getLeftInterface().getName()
								: "", //$NON-NLS-1$
								getCastedModel().getLeftInterface().getComment());
			}
			if (null != getCastedModel().getRightInterface()) {
				figure.setRightLabelText(
						null != getCastedModel().getRightInterface() ? getCastedModel().getRightInterface().getName()
								: "", //$NON-NLS-1$
								getCastedModel().getRightInterface().getComment());
			}
		}
	}

	@Override
	protected List<ServiceSequence> getModelChildren() {
		return new ArrayList<>(getCastedModel().getServiceSequence());
	}

	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
		if (childEditPart instanceof ServiceSequenceEditPart) {
			final SequenceFigure child = (SequenceFigure) ((GraphicalEditPart) childEditPart).getFigure();
			final ServiceFigure thisFigure = (ServiceFigure) getFigure();
			final GridData childData = new GridData(SWT.FILL, SWT.NONE, true, false);
			thisFigure.getServiceSequenceContainer().add(child, index);
			thisFigure.getServiceSequenceContainer().getLayoutManager().setConstraint(child, childData);
		}
	}

	@Override
	protected void removeChildVisual(final EditPart childEditPart) {
		if (childEditPart instanceof ServiceSequenceEditPart) {
			final SequenceFigure child = (SequenceFigure) ((GraphicalEditPart) childEditPart).getFigure();
			final ServiceFigure thisFigure = (ServiceFigure) getFigure();
			thisFigure.getServiceSequenceContainer().remove(child);
		}
	}
}
