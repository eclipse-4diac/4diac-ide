/*******************************************************************************
 * Copyright (c) 2014 - 2015 fortiss GmbH
 *               2021 Johannes Kepler University Linz
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
 *   Bianca Wiesmayr, Melanie Winter - cleanup
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.policies;

import static org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.ServiceInterfacePaletteFactory.LEFT_OUTPUT_PRIMITIVE;
import static org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.ServiceInterfacePaletteFactory.SERVICE_SEQUENCE;
import static org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.ServiceInterfacePaletteFactory.SERVICE_TRANSACTION;

import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.CreateOutputPrimitiveCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.CreateServiceSequenceCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.CreateTransactionCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.AbstractPrimitiveEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.InputPrimitiveEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.OutputPrimitiveEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.ServiceSequenceEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.TransactionEditPart;
import org.eclipse.fordiac.ide.gef.policies.ModifiedNonResizeableEditPolicy;
import org.eclipse.fordiac.ide.gef.preferences.GefPreferenceConstants;
import org.eclipse.fordiac.ide.model.commands.change.ChangeOutputPrimitiveOrderCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeServiceSequenceOrderCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeTransactionOrderCommand;
import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.Primitive;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceTransactionImpl;
import org.eclipse.fordiac.ide.model.libraryElement.impl.SimpleFBTypeImpl;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.FlowLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

public class TransactionLayoutEditPolicy extends FlowLayoutEditPolicy {
	@Override
	protected EditPolicy createChildEditPolicy(final EditPart child) {
		return new ModifiedNonResizeableEditPolicy(GefPreferenceConstants.CORNER_DIM_HALF, new Insets(1));
	}

	@Override
	protected Command getCreateCommand(final CreateRequest request) {
		final Object type = request.getNewObjectType();
		final EditPart refEP = getInsertionReference(request);

		if (LEFT_OUTPUT_PRIMITIVE.equals(type)) {
			return createLeftOutputPrimitive(refEP);
		}
		if (SERVICE_TRANSACTION.equals(type)) {
			return createServiceTransaction(refEP);
		} else if (SERVICE_SEQUENCE.equals(type)) {
			return createServiceSequence(refEP);
		}
		return null;
	}

	private Command createLeftOutputPrimitive(final EditPart refEP) {
		if (refEP instanceof OutputPrimitiveEditPart) {
			final ServiceTransaction transaction = ((OutputPrimitive) refEP.getModel()).getServiceTransaction();
			final int index = transaction.getOutputPrimitive().indexOf(refEP.getModel());
			return new CreateOutputPrimitiveCommand(transaction, index, true);
		}
		if (refEP instanceof InputPrimitiveEditPart) {
			final ServiceTransaction transaction = ((InputPrimitive) refEP.getModel()).getServiceTransaction();
			return new CreateOutputPrimitiveCommand(transaction, 0, true);
		}

		else if (refEP instanceof TransactionEditPart) {
			final ServiceTransaction transaction = (ServiceTransaction) refEP.getModel();
			final int index = transaction.getOutputPrimitive().size();
			return new CreateOutputPrimitiveCommand(transaction, index, true);
		}

		else if (refEP instanceof ServiceSequenceEditPart) {
			int sequenceBefore = ((ServiceSequence) refEP.getModel()).getService().getServiceSequence()
					.indexOf(refEP.getModel()) - 1;
			if (sequenceBefore < 0) {
				sequenceBefore = 0;
			}
			final ServiceSequence sequence = ((ServiceSequence) refEP.getModel()).getService().getServiceSequence()
					.get(sequenceBefore);
			final ServiceTransaction transaction = sequence.getServiceTransaction()
					.get(sequence.getServiceTransaction().size() - 1);
			final int index = transaction.getOutputPrimitive().size();
			return new CreateOutputPrimitiveCommand(transaction, index, true);
		}

		else if (refEP == null) { // insert at the end of the list
			final ServiceTransaction transaction = (ServiceTransaction) getHost().getModel();
			return new CreateOutputPrimitiveCommand(transaction, true);
		}
		return null;
	}

	private Command createServiceTransaction(final EditPart refEP) {
		if (refEP instanceof AbstractPrimitiveEditPart) {
			final ServiceSequence sequence = ((Primitive) refEP.getModel()).getServiceTransaction()
					.getServiceSequence();
			final ServiceTransaction refElement = ((Primitive) refEP.getModel()).getServiceTransaction();
			return new CreateTransactionCommand(sequence, refElement);
		}
		if (refEP instanceof TransactionEditPart) {
			final ServiceSequence sequence = ((ServiceTransaction) refEP.getModel()).getServiceSequence();
			final int indexLastTransaction = sequence.getServiceTransaction().size() - 1;
			return new CreateTransactionCommand(sequence, sequence.getServiceTransaction().get(indexLastTransaction));
		}

		else if (refEP instanceof ServiceSequenceEditPart) {
			int indexSequenceBefore = ((ServiceSequence) refEP.getModel()).getService().getServiceSequence()
					.indexOf(refEP.getModel()) - 1;
			if (indexSequenceBefore < 0) {
				indexSequenceBefore = 0;
			}
			final ServiceSequence sequenceBefore = ((ServiceSequence) refEP.getModel()).getService()
					.getServiceSequence().get(indexSequenceBefore);
			int indexLastTransaction = sequenceBefore.getServiceTransaction().size() - 1;
			if (indexLastTransaction < 0) {
				indexLastTransaction = 0;
			}
			final ServiceTransaction transaction = sequenceBefore.getServiceTransaction().get(indexLastTransaction);
			return new CreateTransactionCommand(sequenceBefore, transaction);
		} else if (refEP == null) { // insert at the end of the list
			final ServiceTransaction transaction = (ServiceTransaction) getHost().getModel();
			return new CreateTransactionCommand(transaction.getServiceSequence(), transaction);
		}
		return null;
	}

	private Command createServiceSequence(final EditPart refEP) {
		if (refEP instanceof AbstractPrimitiveEditPart) {
			final Service service = ((Primitive) refEP.getModel()).getService();
			final ServiceSequence sequence = ((Primitive) refEP.getModel()).getServiceTransaction()
					.getServiceSequence();
			return new CreateServiceSequenceCommand(service, sequence);
		}
		if (refEP instanceof TransactionEditPart) {
			final Service service = ((ServiceTransaction) refEP.getModel()).getServiceSequence().getService();
			return new CreateServiceSequenceCommand(service,
					((ServiceTransaction) refEP.getModel()).getServiceSequence());
		}

		else if (refEP instanceof ServiceSequenceEditPart) {
			final ServiceSequence sequence = (ServiceSequence) refEP.getModel();
			int indexSequenceBefore = sequence.getService().getServiceSequence().indexOf(sequence) - 1;
			if (indexSequenceBefore < 0) {
				indexSequenceBefore = 0;
			}
			return new CreateServiceSequenceCommand(sequence.getService(),
					sequence.getService().getServiceSequence().get(indexSequenceBefore));
		}

		else if (refEP == null) { // insert at the end of the list
			Service service;
			if (getHost().getModel() instanceof SimpleFBTypeImpl) {
				final SimpleFBTypeImpl fb = (SimpleFBTypeImpl) getHost().getModel();
				service = fb.getService();
				final int indexLastSequence = service.getServiceSequence().size() - 1;
				return new CreateServiceSequenceCommand(service, service.getServiceSequence().get(indexLastSequence));
			} else if (getHost().getModel() instanceof ServiceTransactionImpl) {
				final ServiceTransactionImpl serviceTransactionImpl = (ServiceTransactionImpl) getHost().getModel();
				service = serviceTransactionImpl.getServiceSequence().getService();
				final int indexSequenceBefore = service.getServiceSequence()
						.indexOf(serviceTransactionImpl.getServiceSequence());
				return new CreateServiceSequenceCommand(service, service.getServiceSequence().get(indexSequenceBefore));
			}
			return null;
		}
		return null;
	}

	@Override
	protected boolean isLayoutHorizontal() {
		return false;
	}

	@Override
	protected Command createAddCommand(final EditPart child, final EditPart after) {
		return null;
	}

	@Override
	protected Command createMoveChildCommand(final EditPart child, final EditPart after) {
		if (child instanceof OutputPrimitiveEditPart) {
			return moveOutputPrimitive(child, after);
		}
		if (child instanceof TransactionEditPart) {
			return moveTransaction(child, after);
		} else if (child instanceof ServiceSequenceEditPart) {
			return moveServiceSequence(child, after);
		}
		// input primitives can not be reordered
		return null;
	}

	private static Command moveOutputPrimitive(final EditPart child, final EditPart after) {
		final OutputPrimitive outputP = (OutputPrimitive) child.getModel();
		if (after instanceof InputPrimitiveEditPart) {
			return new ChangeOutputPrimitiveOrderCommand(outputP, 0);
		}
		if (after instanceof OutputPrimitiveEditPart) {
			final OutputPrimitive afterOutputP = (OutputPrimitive) after.getModel();
			if (afterOutputP.getServiceTransaction().equals(outputP.getServiceTransaction())) {
				return new ChangeOutputPrimitiveOrderCommand(outputP, afterOutputP, false);
			}
		}
		return null;
	}

	private static Command moveTransaction(final EditPart child, final EditPart after) {
		final ServiceTransaction transaction = (ServiceTransaction) child.getModel();
		if (after instanceof AbstractPrimitiveEditPart) {
			final ServiceTransaction afterT = ((Primitive) after).getServiceTransaction();
			return new ChangeTransactionOrderCommand(transaction, afterT, false);
		}
		if (after instanceof TransactionEditPart) {
			final ServiceTransaction afterT = (ServiceTransaction) after.getModel();
			if (transaction.getServiceSequence().equals(afterT.getServiceSequence())) {
				return new ChangeTransactionOrderCommand(transaction, afterT, false);
			}
		}

		else if (after instanceof ServiceSequenceEditPart) {
			return new ChangeTransactionOrderCommand(transaction, 0);
		}
		return null;
	}

	private Command moveServiceSequence(final EditPart child, final EditPart after) {
		final ServiceSequence sequence = (ServiceSequence) child.getModel();
		if (after instanceof ServiceSequenceEditPart) {
			final ServiceSequence afterS = (ServiceSequence) after.getModel();
			if (sequence.getService().equals(afterS.getService())) {
				return new ChangeServiceSequenceOrderCommand(sequence, afterS, false);
			}
		} else if (after instanceof TransactionEditPart) {
			final ServiceTransaction afterT = (ServiceTransaction) after;
			return new ChangeServiceSequenceOrderCommand(sequence, afterT.getServiceSequence(), false);
		} else if (after instanceof AbstractPrimitiveEditPart) {
			final Primitive afterP = (Primitive) after;
			return new ChangeServiceSequenceOrderCommand(sequence, afterP.getServiceTransaction().getServiceSequence(),
					false);
		} else if ((after == null) && (getHost().getModel() instanceof SimpleFBTypeImpl)) {
			final SimpleFBTypeImpl fb = (SimpleFBTypeImpl) getHost().getModel();
			final int indexLastS = fb.getService().getServiceSequence().size() - 1;
			return new ChangeServiceSequenceOrderCommand(sequence, fb.getService().getServiceSequence().get(indexLastS),
					false);
		}
		return null;
	}
}
