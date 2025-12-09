/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2020 Johannes Kepler University Linz
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Virendra Ashiwal - added an extracted method createMoveChildCommand and condition canReorder for
 *                      Events/Variables and Adapter Interface(Socket and Plug)
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.policies;

import org.eclipse.fordiac.ide.fbtypeeditor.editparts.AbstractContainerElement;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInterfaceOrderCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.FlowLayoutEditPolicy;

public abstract class AbstractInterfaceContainerLayoutEditPolicy extends FlowLayoutEditPolicy {

	protected AbstractContainerElement getModel() {
		return (AbstractContainerElement) getHost().getModel();
	}

	protected FBType getFBType() {
		return getModel().getFbType();
	}

	@Override
	protected Command createAddCommand(final EditPart child, final EditPart after) {
		return null;
	}

	private int createMoveChildCondition(final EditPart after) {
		if (null == after) {
			return getHost().getChildren().size();
		}
		return getHost().getChildren().indexOf(after);
	}

	protected abstract boolean canReorder(IInterfaceElement childEP, IInterfaceElement afterEP);

	@Override
	protected Command createMoveChildCommand(final EditPart child, final EditPart after) {
		if (null != child && child.getModel() instanceof final IInterfaceElement childEl) {
			IInterfaceElement afterEl = null;
			if (null != after) {
				afterEl = (IInterfaceElement) after.getModel();
			}
			if (canReorder(childEl, afterEl)) {
				final int newIndex = createMoveChildCondition(after);
				return createChangeInterfaceOrderCmd(childEl, newIndex);
			}
		}
		return null;
	}

	@SuppressWarnings("static-method")  // allow children to overwrite and adapt
	protected Command createChangeInterfaceOrderCmd(final IInterfaceElement childEl, final int newIndex) {
		return new ChangeInterfaceOrderCommand(childEl, newIndex);
	}

}
