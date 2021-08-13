/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr, Melanie Winter
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts;

import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.ChangePrimitiveParameterCommand;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDirectEditableEditPart;
import org.eclipse.fordiac.ide.gef.policies.INamedElementRenameEditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.DirectEditRequest;

public class ParameterEditPolicy extends INamedElementRenameEditPolicy {
	@Override
	protected Command getDirectEditCommand(final DirectEditRequest request) {
		if (getHost() instanceof AbstractDirectEditableEditPart) {
			final AbstractDirectEditableEditPart viewEditPart = (AbstractDirectEditableEditPart) getHost();
			return new ChangePrimitiveParameterCommand(((ParameterEditPart) getHost()).getModel().getPrimitive(),
					REQ_ADD);
		}
		return null;
	}
}
