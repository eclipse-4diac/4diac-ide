/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner - initial implementation and/or documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.application.handlers;

import org.eclipse.fordiac.ide.application.editparts.AbstractBlockFBNElementEditPart;
import org.eclipse.fordiac.ide.application.editparts.FBNetworkRootEditPart;
import org.eclipse.fordiac.util.marker.MarkerDescriptor;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

public class MarkPredecessorHandler extends AbstractMarkerHandler {

	@Override
	protected MarkerDescriptor getDescriptor() {
		return MarkerDescriptor.PREDECESSOR;
	}

	@Override
	protected GraphicalEditPart getValidSelectedElement(final ISelection selection) {
		if (selection instanceof final IStructuredSelection structSel
				&& structSel.getFirstElement() instanceof final AbstractBlockFBNElementEditPart ep
				&& ep.getRoot() instanceof FBNetworkRootEditPart) {
			return ep;
		}
		return null;
	}

}
