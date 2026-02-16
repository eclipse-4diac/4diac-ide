/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.tools;

import java.util.List;

import org.eclipse.fordiac.ide.application.commands.TargetLabelReconnectCommand;
import org.eclipse.fordiac.ide.application.editparts.TargetInterfaceElementEditPart;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.gef.tools.FordiacConnectionDragCreationTool;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.CreateConnectionRequest;

public class MultiTargetLabelConnectionDragTool extends FordiacConnectionDragCreationTool {
	private final List<TargetInterfaceElementEditPart> selections;
	private final InterfaceEditPart originalSource;

	public MultiTargetLabelConnectionDragTool(final List<TargetInterfaceElementEditPart> selections,
			final InterfaceEditPart originalSource) {
		this.selections = selections;
		this.originalSource = originalSource;
	}

	@Override
	protected void showSourceFeedback() {
		final CreateConnectionRequest targetRequest = getTargetRequest();
		targetRequest.setLocation(getLocation());
		selections.forEach(con -> con.showSourceFeedback(targetRequest));
		super.showSourceFeedback();
	}

	@Override
	protected void eraseSourceFeedback() {
		final CreateConnectionRequest targetRequest = getTargetRequest();
		targetRequest.setLocation(getLocation());
		selections.forEach(con -> con.eraseSourceFeedback(targetRequest));
		super.eraseSourceFeedback();
	}

	@Override
	protected Command getCommand() {
		if (getTargetEditPart() == null) {
			return null;
		}
		final CompoundCommand cmd = new CompoundCommand();
		final CreateConnectionRequest targetRequest = getTargetRequest();

		selections.forEach(destinationEP -> {
			final var targetCmd = new TargetLabelReconnectCommand(this.originalSource.getModel(), null,
					destinationEP.getModel().getRefElement());
			targetRequest.setStartCommand(targetCmd);
			cmd.add(getTargetEditPart().getCommand(targetRequest));
		});

		return cmd;
	}
}
