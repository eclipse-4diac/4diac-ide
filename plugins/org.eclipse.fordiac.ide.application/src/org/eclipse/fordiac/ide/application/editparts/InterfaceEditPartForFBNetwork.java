/*******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, fortiss GmbH
 * 				 2020 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Waldemar Eisenmenger, Monika Wenger
 *   - initial API and implementation and/or initial documentation
 *   Daniel Lindhuber, Bianca Wiesmayr - connection methods for unfolded subapp
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.application.policies.AdapterNodeEditPolicy;
import org.eclipse.fordiac.ide.application.policies.EventNodeEditPolicy;
import org.eclipse.fordiac.ide.application.policies.VariableNodeEditPolicy;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;

/**
 * The edit part for interface elements of FBs and Subapps shown in FBNetwork
 * editors
 */
public class InterfaceEditPartForFBNetwork extends InterfaceEditPart {
	@Override
	protected GraphicalNodeEditPolicy getNodeEditPolicy() {
		if (isEvent()) {
			return new EventNodeEditPolicy();
		}
		if (isAdapter()) {
			return new AdapterNodeEditPolicy();
		}
		if (isVariable()) {
			return new VariableNodeEditPolicy();
		}
		return null;
	}

	@Override
	protected List<?> getModelSourceConnections() {
		List<Object> connections = new ArrayList<>(super.getModelSourceConnections());
		if (isUnfoldedSubapp()) {
			if (isInput()) {
				connections.addAll(getModel().getOutputConnections());
			}
			return connections;
		}
		return connections;
	}

	@Override
	protected List<?> getModelTargetConnections() {
		List<Object> connections = new ArrayList<>(super.getModelTargetConnections());
		if (isUnfoldedSubapp()) {
			if (!isInput()) {
				connections.addAll(getModel().getInputConnections());
			}
			return connections;
		}
		return connections;
	}

	private boolean isUnfoldedSubapp() {
		if (getModel().getFBNetworkElement() instanceof SubApp) {
			SubApp subapp = (SubApp) getModel().getFBNetworkElement();
			if (subapp.isUnfolded()) {
				return true;
			}
		}
		return false;
	}

}
