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

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.Layer;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.DeleteServiceSequenceCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.policies.SequenceLayoutEditPolicy;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDirectEditableEditPart;
import org.eclipse.fordiac.ide.gef.policies.HighlightEditPolicy;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

public class ServiceSequenceEditPart extends AbstractDirectEditableEditPart /* ResizableCompartmentEditPart */ {

	private EContentAdapter adapter = new EContentAdapter() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			if (getCastedModel().eAdapters().contains(adapter)) {
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

	public class ServiceSequenceFigure extends Layer {
		private Label nameLabel;
		private final Layer transactionContainer;

		public ServiceSequenceFigure() {
			GridLayout sequenceLayout;
			setLayoutManager(sequenceLayout = new GridLayout());
			sequenceLayout.verticalSpacing = 0;
			nameLabel = new Label();
			nameLabel.setOpaque(true);
			nameLabel.setBackgroundColor(ColorConstants.lightGray);
			nameLabel.setLabelAlignment(PositionConstants.CENTER);
			GridData nameLayoutData = new GridData();
			nameLayoutData.grabExcessHorizontalSpace = true;
			nameLayoutData.horizontalAlignment = GridData.FILL;
			getLayoutManager().setConstraint(nameLabel, nameLayoutData);
			add(nameLabel);

			transactionContainer = new Layer();
			GridLayout containerLayout;
			transactionContainer.setLayoutManager(containerLayout = new GridLayout());
			containerLayout.verticalSpacing = 0;
			GridData containerData = new GridData();
			containerData.grabExcessHorizontalSpace = true;
			containerData.horizontalAlignment = GridData.FILL;
			getLayoutManager().setConstraint(transactionContainer, containerData);
			add(transactionContainer);
		}

		public Label getLabel() {
			return nameLabel;
		}

		public void setLabelText(String name) {
			this.nameLabel.setText(null != name ? name : ""); //$NON-NLS-1$
		}

		public Layer getTransactionContainer() {
			return transactionContainer;
		}

	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ComponentEditPolicy() {
			@Override
			protected Command getDeleteCommand(final GroupRequest request) {
				return new DeleteServiceSequenceCommand((FBType) getCastedModel().eContainer().eContainer(),
						getCastedModel());
			}
		});
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new HighlightEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new SequenceLayoutEditPolicy());
	}

	public ServiceSequence getCastedModel() {
		return (ServiceSequence) getModel();
	}

	@Override
	protected IFigure createFigure() {
		ServiceSequenceFigure figure = new ServiceSequenceFigure();
		GridData layoutData = new GridData();
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.horizontalAlignment = GridData.FILL;
		figure.getLayoutManager().setConstraint(figure, layoutData);

		return figure;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected List getModelChildren() {
		List<Object> children = new ArrayList<>();
		children.addAll(getCastedModel().getServiceTransaction());
		return children;
	}

	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
		if (childEditPart instanceof TransactionEditPart) {
			ServiceSequenceFigure thisFigure = (ServiceSequenceFigure) getFigure();
			IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
			GridData childData = new GridData();
			childData.grabExcessHorizontalSpace = true;
			childData.horizontalAlignment = GridData.FILL;
			thisFigure.getTransactionContainer().getLayoutManager().setConstraint(child, childData);
			thisFigure.getTransactionContainer().add(child, index);
		}
	}

	@Override
	protected void removeChildVisual(final EditPart childEditPart) {
		IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		if (childEditPart instanceof TransactionEditPart) {
			((ServiceSequenceFigure) getFigure()).getTransactionContainer().remove(child);
		}
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		ServiceSequenceFigure figure = (ServiceSequenceFigure) getFigure();
		if (null != getCastedModel()) {
			figure.setLabelText(getCastedModel().getName());
		}
	}

	@Override
	public INamedElement getINamedElement() {
		return getCastedModel();
	}

	@Override
	public Label getNameLabel() {
		return ((ServiceSequenceFigure) getFigure()).getLabel();
	}
}
