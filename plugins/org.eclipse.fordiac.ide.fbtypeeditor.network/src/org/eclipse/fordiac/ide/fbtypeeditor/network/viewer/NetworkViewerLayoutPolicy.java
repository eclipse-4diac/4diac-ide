/*******************************************************************************
 * Copyright (c) 2013 - 2017 Profactor GmbH, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.network.viewer;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.fordiac.ide.gef.policies.ModifiedMoveHandle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.SelectionEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

class NetworkViewerLayoutPolicy extends LayoutEditPolicy {

	/**
	 * A simple selection edit policy which will show a rounded rectangle around the
	 * host
	 */
	private static class NetworkViewerSelectionPolicy extends SelectionEditPolicy {
		private ModifiedMoveHandle handle = null;

		private ModifiedMoveHandle getHandle() {
			if (null == handle) {
				handle = new ModifiedMoveHandle(getHost(), new Insets(2), 14);
			}
			return handle;
		}

		@Override
		protected void hideSelection() {
			if (handle != null) {
				final IFigure layer = getLayer(LayerConstants.HANDLE_LAYER);
				layer.remove(handle);
			}
		}

		@Override
		protected void showSelection() {
			final IFigure layer = getLayer(LayerConstants.HANDLE_LAYER);
			layer.add(getHandle());
		}
	}

	@Override
	protected Command getCreateCommand(final CreateRequest request) {
		return null;
	}

	@Override
	protected EditPolicy createChildEditPolicy(final EditPart child) {
		return new NetworkViewerSelectionPolicy();
	}

	@Override
	protected Command getMoveChildrenCommand(final Request request) {
		return null;
	}
}