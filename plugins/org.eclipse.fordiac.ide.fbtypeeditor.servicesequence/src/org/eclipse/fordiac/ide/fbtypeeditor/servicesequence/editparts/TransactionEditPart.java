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

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.policies.DeleteTransactionEditPolicy;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.policies.TransactionLayoutEditPolicy;
import org.eclipse.fordiac.ide.gef.draw2d.AdvancedLineBorder;
import org.eclipse.fordiac.ide.gef.policies.HighlightEditPolicy;
import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.Primitive;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.swt.SWT;

/**
 * The Class TransactionEditPart.
 */
public class TransactionEditPart extends AbstractGraphicalEditPart {

	private EContentAdapter adapter = new EContentAdapter() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			if(getCastedModel().eAdapters().contains(adapter)){
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
		
	public static class TransactionFigure extends Figure{
		public TransactionFigure(){
			GridLayout layout = new GridLayout();
			layout.marginWidth = 0;
			layout.horizontalSpacing = 0;
			setBorder(new AdvancedLineBorder(PositionConstants.NORTH, SWT.LINE_DASH));
			setLayoutManager(layout);
		}
	}
	
	@Override
	protected IFigure createFigure() {
		return new TransactionFigure();
	}

	/**
	 * Gets the casted model.
	 * 
	 * @return the casted model
	 */
	public ServiceTransaction getCastedModel() {
		return (ServiceTransaction) getModel();
	}

	public OutputPrimitive getPossibleOutputPrimitive(final InputPrimitive inputPrimitive) {
		try {
			int index = getModelChildren().indexOf(inputPrimitive);
			if (getModelChildren().get(index + 1) instanceof OutputPrimitive) {
				return (OutputPrimitive) getModelChildren().get(index + 1);
			}
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
		return null;
	}

	public InputPrimitive getPossibleInputPrimitive(final Primitive outputPrimitive) {
		try {
			int index = getModelChildren().indexOf(outputPrimitive);
			if (getModelChildren().get(index - 1) instanceof InputPrimitive) {
				return (InputPrimitive) getModelChildren().get(index - 1);
			}
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
		return null;
	}

	@Override
	protected List<?> getModelChildren() {
		ServiceTransaction transaction = getCastedModel();
		ArrayList<Object> children = new ArrayList<Object>();
		if (transaction.getInputPrimitive() != null) {
			children.add(transaction.getInputPrimitive());
		}
		if(! transaction.getOutputPrimitive().isEmpty()){
			children.addAll(transaction.getOutputPrimitive());			
		}
		return children;
	}

	
	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
		if (childEditPart instanceof InputPrimitiveEditPart || childEditPart instanceof OutputPrimitiveEditPart) {
			TransactionFigure thisFigure = (TransactionFigure) getFigure();
			IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
			GridData childData = new GridData();
			childData.grabExcessHorizontalSpace = true;
			childData.horizontalAlignment = GridData.FILL;
			thisFigure.getLayoutManager().setConstraint(child, childData);
			thisFigure.add(child, childEditPart instanceof InputPrimitiveEditPart ? 0 : index);
		}
	}

	@Override
	protected void removeChildVisual(final EditPart childEditPart) {
		if (childEditPart instanceof InputPrimitiveEditPart || childEditPart instanceof OutputPrimitiveEditPart) {
			IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
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
