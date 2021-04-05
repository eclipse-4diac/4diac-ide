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
import org.eclipse.fordiac.ide.fbtypeeditor.network.editparts.CompositeNetworkEditPart;
import org.eclipse.fordiac.ide.gef.policies.ModifiedMoveHandle;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.gef.editpolicies.SelectionEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

/**
 * Edit Part for the visualization of Composite Networks.
 *
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class CompositeNetworkViewerEditPart extends CompositeNetworkEditPart {

	@Override
	protected InterfaceList getInterfaceList() {
		return ((FBNetworkElement) getModel().eContainer()).getInterface();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new LayoutEditPolicy() {

			@Override
			protected Command getCreateCommand(final CreateRequest request) {
				return null;
			}

			@Override
			protected EditPolicy createChildEditPolicy(final EditPart child) {
				/*
				 * a simple selection edit policy which will show a rounded rectangle around the
				 * host
				 */
				return new SelectionEditPolicy() {
					private ModifiedMoveHandle handle = null;

					private ModifiedMoveHandle getHandle() {
						if (null == handle) {
							handle = new ModifiedMoveHandle((GraphicalEditPart) getHost(), new Insets(2), 14);
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

				};
			}

			@Override
			protected Command getMoveChildrenCommand(final Request request) {
				return null;
			}
		});
	}

	@Override
	public void performRequest(final Request request) {
		// do nothing as this is a viewer
	}

}
