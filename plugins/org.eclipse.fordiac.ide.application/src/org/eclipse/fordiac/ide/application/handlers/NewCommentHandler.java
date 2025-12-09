/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.model.commands.create.AbstractCreateFBNetworkElementCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateCommentCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class NewCommentHandler extends AbstractContainerElementHandler {

	@Override
	protected AbstractCreateFBNetworkElementCommand createContainerCreationCommand(final List<?> selection,
			final FBNetwork network, final Rectangle posSizeRef) {
		return new CreateCommentCommand(network, posSizeRef);
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final ISelection sel = (ISelection) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME);
		boolean enabled = false;
		if (sel instanceof final StructuredSelection selection) {
			enabled = createNewEmptyContainerElement(selection);
		}
		setBaseEnabled(enabled);
	}
}
