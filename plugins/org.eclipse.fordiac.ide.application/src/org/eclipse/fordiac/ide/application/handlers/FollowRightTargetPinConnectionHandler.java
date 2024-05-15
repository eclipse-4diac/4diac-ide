/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
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

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.application.editparts.TargetInterfaceElementEditPart;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;

public class FollowRightTargetPinConnectionHandler extends AbstractFollowTargetPinConnectionHandler {

	@Override
	protected boolean useTargetRefElement(final TargetInterfaceElementEditPart targetIEEditPart) {
		return !targetIEEditPart.getModel().getHost().isIsInput();
	}

	@Override
	protected boolean isLeft() {
		return false;
	}

	@Override
	protected EList<Connection> getConnectionList(final IInterfaceElement ie) {
		return ie.getOutputConnections();
	}

	@Override
	protected IInterfaceElement getInternalOppositeEventPin(final InterfaceEditPart pin) {
		return null;
	}

	@Override
	protected IInterfaceElement getInternalOppositeVarPin(final InterfaceEditPart pin) {
		return null;
	}

	@Override
	protected IInterfaceElement getInternalOppositeVarInOutPin(final InterfaceEditPart pin) {
		return null;
	}

	@Override
	protected IInterfaceElement getInternalOppositePlugOrSocketPin(final InterfaceEditPart pin) {
		return null;
	}

}
