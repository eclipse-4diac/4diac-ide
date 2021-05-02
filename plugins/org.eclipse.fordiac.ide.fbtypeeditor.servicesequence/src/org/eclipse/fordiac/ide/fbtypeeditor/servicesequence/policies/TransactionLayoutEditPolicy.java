/*******************************************************************************
 * Copyright (c) 2014 - 2015 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.policies;

import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.CreateInputPrimitiveCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.CreateOutputPrimitiveCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.MoveOutputPrimitiveCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.InputPrimitiveEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.OutputPrimitiveEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.PrimitiveEditPart;
import org.eclipse.fordiac.ide.gef.policies.EmptyXYLayoutEditPolicy;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

public class TransactionLayoutEditPolicy extends EmptyXYLayoutEditPolicy {

	@Override
	protected Command createAddCommand(final EditPart child, final Object constraint) {
		//		ServiceTransaction target = (ServiceTransaction)getHost().getModel();
		//		EditPart after = (EditPart) getHost().getChildren().get(getHost().getChildren().indexOf(child) - 1);
		//		if((child instanceof OutputPrimitiveEditPart) &&  ((after != null) || //we are out in the first place or if we are put in the first place there is no inputprimitive
		//				((after == null) && (target.getInputPrimitive() == null)))){
		//			OutputPrimitive element = ((OutputPrimitiveEditPart)child).getCastedModel();
		//			OutputPrimitive refElement = null;
		//			if(after != null){
		//					if(after instanceof OutputPrimitiveEditPart){
		//						refElement = ((OutputPrimitiveEditPart)after).getCastedModel();
		//					}else if (after instanceof InputPrimitiveEditPart){
		//						if(0 < target.getOutputPrimitive().size()){
		//							refElement = target.getOutputPrimitive().get(0);
		//						}
		//					}
		//					else{
		//						refElement = null;
		//					}
		//			}
		//			return new MoveOutputPrimitiveToOtherTransactionCommand((ServiceTransaction)element.eContainer(), target, element, refElement);
		//		}
		//		if(child instanceof InputPrimitiveEditPart){
		//			InputPrimitive element = ((InputPrimitiveEditPart)child).getCastedModel();
		//			if(null == target.getInputPrimitive()){
		//				//we can only move into a transaction without input primitive
		//				if(after != null){
		//					if(after instanceof OutputPrimitiveEditPart){
		//						OutputPrimitive refElement = ((OutputPrimitiveEditPart)after).getCastedModel();
		//						if(0 == target.getOutputPrimitive().indexOf(refElement)){
		//							return new MoveInputPrimitiveToOtherTransactionCommand((ServiceTransaction)element.eContainer(), target, element);
		//						}
		//					}
		//				}
		//				else{
		//					if(0 == target.getOutputPrimitive().size()){
		//						return new MoveInputPrimitiveToOtherTransactionCommand((ServiceTransaction)element.eContainer(), target, element);
		//					}
		//				}
		//			}
		//		}
		return null;
	}

	@Override
	protected Command createChangeConstraintCommand(final ChangeBoundsRequest request, final EditPart child, final Object constraint) {
		final ServiceTransaction target = (ServiceTransaction) getHost().getModel();
		if (!(child instanceof InputPrimitiveEditPart)) {
			final EditPart after = getInsertionReference(request.getLocation());
			if (((child instanceof OutputPrimitiveEditPart) && (after != null))
					|| ((after == null) && (target.getInputPrimitive() == null))) {
				OutputPrimitive refElement = null;
				if (after != null) {
					if (after instanceof OutputPrimitiveEditPart) {
						refElement = ((OutputPrimitiveEditPart) after).getCastedModel();
					} else if ((after instanceof InputPrimitiveEditPart) && (!target.getOutputPrimitive().isEmpty())) {
						refElement = target.getOutputPrimitive().get(0);
					}
				}
				return new MoveOutputPrimitiveCommand(target, ((OutputPrimitiveEditPart) child).getCastedModel(),
						refElement);
			}
		}
		return null;
	}

	@Override
	protected Command getCreateCommand(final CreateRequest request) {
		final Object type = request.getNewObjectType();
		final ServiceTransaction model = (ServiceTransaction) getHost().getModel();
		if (type.equals("LeftInputPrimitive") || type.equals("RightInputPrimitive")) { //$NON-NLS-1$ //$NON-NLS-2$
			return new CreateInputPrimitiveCommand((String) type, model);
		} else if (type.equals("LeftOutputPrimitive") || type.equals("RightOutputPrimitive")) { //$NON-NLS-1$ //$NON-NLS-2$
			final PrimitiveEditPart refPrimitiv = (PrimitiveEditPart) getInsertionReference(request.getLocation());
			if (null != refPrimitiv) {
				if (refPrimitiv instanceof InputPrimitiveEditPart) {
					// we can not be above the input primitive
					return null;
				}
				if (refPrimitiv instanceof OutputPrimitiveEditPart) {
					return new CreateOutputPrimitiveCommand((String) type, model,
							(OutputPrimitive) refPrimitiv.getModel());
				}
			}
			return new CreateOutputPrimitiveCommand((String) type, model, null);
		}
		return null;
	}
}
