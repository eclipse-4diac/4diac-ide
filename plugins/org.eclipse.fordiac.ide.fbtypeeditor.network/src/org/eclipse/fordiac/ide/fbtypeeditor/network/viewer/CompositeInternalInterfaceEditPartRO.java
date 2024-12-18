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
 *   Gerhard Ebenhofer, Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.network.viewer;

import org.eclipse.draw2d.IFigure;
import org.eclipse.fordiac.ide.gef.draw2d.ConnectorBorder;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.gef.preferences.GefPreferenceConstants;
import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.ui.IEditorPart;

/** The Class CompositeInternalInterfaceEditPart. */
public class CompositeInternalInterfaceEditPartRO extends InterfaceEditPart {

	private static int interfaceBarPinMaxWidth = -1;

	@Override
	protected GraphicalNodeEditPolicy getNodeEditPolicy() {
		return null;
	}

	@Override
	protected IFigure createFigure() {
		final IFigure figure = super.createFigure();
		figure.setBorder(new ConnectorBorder(getModel()) {
			@Override
			public boolean isInput() {
				return !super.isInput();
			}
		});
		return figure;
	}

	@Override
	public boolean isInput() {
		return !super.isInput();
	}

	@Override
	public void performRequest(final Request request) {
		if ((request.getType() == RequestConstants.REQ_OPEN) && (getModel().getFBNetworkElement() != null)) {
			// REQ_OPEN -> doubleclick and we are inside of a viewer
			goToParent();
		} else {
			super.performRequest(request);
		}
	}

	private void goToParent() {
		final IEditorPart newEditor = HandlerHelper.openParentEditor(getModel().getFBNetworkElement());
		final GraphicalViewer viewer = newEditor.getAdapter(GraphicalViewer.class);
		HandlerHelper.selectElement(getModel(), viewer);
	}

	@Override
	public boolean isConnectable() {
		return false;
	}

	@Override
	protected int getMaxWidth() {
		if (-1 == interfaceBarPinMaxWidth) {
			loadMaxWidth();
		}
		return interfaceBarPinMaxWidth;
	}

	private static synchronized void loadMaxWidth() {
		interfaceBarPinMaxWidth = GefPreferenceConstants.STORE.getInt(GefPreferenceConstants.MAX_INTERFACE_BAR_SIZE);
	}
}
