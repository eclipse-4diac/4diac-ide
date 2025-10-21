/*******************************************************************************
 * Copyright (c) 2008, 2025 Profactor GmbH, fortiss GmbH
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
package org.eclipse.fordiac.ide.application.policies;

import java.util.Optional;

import org.eclipse.core.resources.IProject;
import org.eclipse.fordiac.ide.application.commands.ConnectThroughCommand;
import org.eclipse.fordiac.ide.gef.preferences.GefPreferenceConstants;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteFBNetworkElementCommand;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.ui.editors.AdvancedScrollingGraphicalViewer;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceStoreProvider;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * An EditPolicy which returns a command for deleting a FB from a fbnetwork.
 */
public class DeleteFBNElementEditPolicy extends org.eclipse.gef.editpolicies.ComponentEditPolicy {

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.
	 * eclipse.gef.requests.GroupRequest)
	 */
	@Override
	protected Command createDeleteCommand(final GroupRequest request) {
		if (getHost().getModel() instanceof final FBNetworkElement fbne) {
			if (getPreferenceStore().getBoolean(GefPreferenceConstants.MANAGE_EVENT_CONNECTIONS_AUTOMATICALLY)) {
				final Command rerouteCommand = createRerouteConnectionCommand(fbne);
				if (rerouteCommand != null) {
					return rerouteCommand.chain(new DeleteFBNetworkElementCommand(fbne));
				}
			}
			return new DeleteFBNetworkElementCommand(fbne);
		}
		return null;
	}

	private static Command createRerouteConnectionCommand(final FBNetworkElement fbne) {
		if (!(fbne instanceof final BlockFBNetworkElement bfbne)) {
			return null;
		}

		final InterfaceList fbInterface = bfbne.getInterface();
		if (fbInterface != null && !fbInterface.getEventInputs().isEmpty()
				&& !fbInterface.getEventOutputs().isEmpty()) {
			final Optional<IInterfaceElement> inputEvent = fbInterface.getEventInputs().stream()
					.filter(e -> !e.getInputConnections().isEmpty()).map(e -> ((IInterfaceElement) e)).findFirst();
			final Optional<IInterfaceElement> outputEvent = fbInterface.getEventOutputs().stream()
					.filter(e -> !e.getOutputConnections().isEmpty()).map(e -> (IInterfaceElement) e).findFirst();

			if (outputEvent.isPresent() && inputEvent.isPresent()) {
				return new ConnectThroughCommand(inputEvent.get(), outputEvent.get());
			}
		}
		return null;
	}

	private IPreferenceStore getPreferenceStore() {
		final IProject project = ((AdvancedScrollingGraphicalViewer) getHost().getViewer()).getPreferencesCache()
				.getProject();
		return PreferenceStoreProvider.getStore(GefPreferenceConstants.GEF_PREFERENCES_ID, project);
	}

}
