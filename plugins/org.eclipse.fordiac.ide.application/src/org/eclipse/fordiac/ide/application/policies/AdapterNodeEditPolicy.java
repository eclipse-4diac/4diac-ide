/*******************************************************************************
 * Copyright (c) 2016 - 2018 fortiss GmbH, Johannes Kepler University
 * 				 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Alois Zoitl - moved openEditor helper function to EditorUtils
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.policies;

import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.commands.change.AbstractReconnectConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.change.ReconnectAdapterConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.create.AdapterConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.typelibrary.AdapterTypeEntry;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

public class AdapterNodeEditPolicy extends InterfaceElementEditPolicy {

	@Override
	protected Command getConnectionCreateCommand(final CreateConnectionRequest request) {

		final InterfaceEditPart host = (InterfaceEditPart) getHost();

		if ((host.isAdapter()) && (0 != (host.getMouseState() & SWT.CONTROL))) {
			openAdapterType(host);
			return null;
		}

		final AdapterConnectionCreateCommand cmd = new AdapterConnectionCreateCommand(getParentNetwork());
		cmd.setSource(((InterfaceEditPart) getHost()).getModel());
		request.setStartCommand(cmd);
		return new AdapterConnectionCreateCommand(getParentNetwork());

	}

	private static void openAdapterType(final InterfaceEditPart host) {
		final AdapterTypeEntry entry = (AdapterTypeEntry) ((AdapterDeclaration) host.getModel()).getTypeEntry();
		if (null != entry) {
			final IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry()
					.getDefaultEditor(entry.getFile().getName());
			EditorUtils.openEditor(new FileEditorInput(entry.getFile()), desc.getId());
		}
	}

	@Override
	protected AbstractReconnectConnectionCommand createReconnectCommand(final Connection connection,
			final boolean isSourceReconnect, final IInterfaceElement newTarget) {
		return new ReconnectAdapterConnectionCommand(connection, isSourceReconnect, newTarget, getParentNetwork());
	}

}
