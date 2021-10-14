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

import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.figures.TransactionFigure;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.policies.DeleteTransactionEditPolicy;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.policies.TransactionLayoutEditPolicy;
import org.eclipse.fordiac.ide.gef.policies.HighlightEditPolicy;
import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.Primitive;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

public class TransactionEditPart extends AbstractGraphicalEditPart {

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
	protected IFigure createFigure() {
		return new TransactionFigure();
	}

	@Override
	public ServiceTransaction getModel() {
		return (ServiceTransaction) super.getModel();
	}

	public OutputPrimitive getPossibleOutputPrimitive(final InputPrimitive inputPrimitive) {
		final int index = getModelChildren().indexOf(inputPrimitive);
		Assert.isTrue(index >= 0); // input primitive should be among model children
		if (index < (getModelChildren().size() - 1)) {
			final Primitive primitive = getModelChildren().get(index + 1);
			if (primitive instanceof OutputPrimitive) {
				return (OutputPrimitive) primitive;
			}
		}
		return null;
	}

	public InputPrimitive getPossibleInputPrimitive(final Primitive outputPrimitive) {
		final int index = getModelChildren().indexOf(outputPrimitive);
		if ((index > 0) && (index < getModelChildren().size())) {
			final Primitive primitive = getModelChildren().get(index - 1);
			if (primitive instanceof InputPrimitive) {
				return (InputPrimitive) primitive;
			}
		}
		return null;
	}

	@Override
	protected List<Primitive> getModelChildren() {
		final ServiceTransaction transaction = getModel();
		final List<Primitive> primitives = new ArrayList<>();
		if (transaction.getInputPrimitive() != null) {
			primitives.add(transaction.getInputPrimitive());
		}
		if (!transaction.getOutputPrimitive().isEmpty()) {
			primitives.addAll(transaction.getOutputPrimitive());
		}
		return primitives;
	}

	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
		if ((childEditPart instanceof InputPrimitiveEditPart) || (childEditPart instanceof OutputPrimitiveEditPart)) {
			final TransactionFigure thisFigure = (TransactionFigure) getFigure();
			final IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
			thisFigure.add(child, childEditPart instanceof InputPrimitiveEditPart ? 0 : index);
		}
	}

	@Override
	protected void removeChildVisual(final EditPart childEditPart) {
		if ((childEditPart instanceof InputPrimitiveEditPart) || (childEditPart instanceof OutputPrimitiveEditPart)) {
			final IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
			((TransactionFigure) getFigure()).remove(child);
		}
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new TransactionLayoutEditPolicy());
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new HighlightEditPolicy());
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new DeleteTransactionEditPolicy());
	}

}
