/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.delete;

import org.eclipse.fordiac.ide.model.libraryElement.CommunicationChannel;
import org.eclipse.fordiac.ide.model.libraryElement.CommunicationMappingTarget;

public class DeleteMappedCommunicationFbCommand extends DeleteFBNetworkElementCommand {
	private CommunicationMappingTarget target;
	private final CommunicationChannel fb;

	public DeleteMappedCommunicationFbCommand(final CommunicationChannel element) {
		super(element);
		this.target = (CommunicationMappingTarget) element.eContainer();
		this.fb = element;
	}

	@Override
	public boolean canExecute() {
		return fb.eContainer() != null;
	}

	@Override
	public void execute() {
		target = (CommunicationMappingTarget) fb.eContainer();
		target.getMappedElements().remove(fb);
	}

	@Override
	public void undo() {
		target.getMappedElements().add(fb);
	}

	@Override
	public void redo() {
		target.getMappedElements().remove(fb);
	}
}
