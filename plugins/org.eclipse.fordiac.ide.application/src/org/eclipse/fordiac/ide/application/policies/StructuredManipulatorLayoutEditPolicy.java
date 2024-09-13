/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University, Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Prankur Agarwal - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.policies;

import org.eclipse.fordiac.ide.gef.policies.ModifiedNonResizeableEditPolicy;
import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

public class StructuredManipulatorLayoutEditPolicy extends LayoutEditPolicy {

	@Override
	protected EditPolicy createChildEditPolicy(final EditPart child) {
		return new ModifiedNonResizeableEditPolicy();
	}

	@Override
	protected Command getCreateCommand(final CreateRequest request) {
		if (null != request) {
			final Object childClass = request.getNewObjectType();
			if ((getHost().getModel() instanceof final StructManipulator structManipulator)
					&& (childClass instanceof final DataTypeEntry dataTypeEntry)) {
				return new ChangeStructCommand(structManipulator, dataTypeEntry.getType());
			}
		}
		return null;
	}

	@Override
	protected Command getMoveChildrenCommand(final Request request) {
		// children should not be moveable
		return null;
	}

}
