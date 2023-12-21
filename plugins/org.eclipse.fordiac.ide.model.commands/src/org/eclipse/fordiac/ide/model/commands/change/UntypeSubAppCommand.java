/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz
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
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.Messages;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.typelibrary.SubAppTypeEntry;
import org.eclipse.gef.commands.Command;

public class UntypeSubAppCommand extends Command implements ScopedCommand {
	private final SubApp subapp;
	private final SubAppTypeEntry typeEntry;

	public UntypeSubAppCommand(final SubApp subapp) {
		super(Messages.UntypeSubappCommand_Label);
		this.subapp = Objects.requireNonNull(subapp);
		typeEntry = (SubAppTypeEntry) subapp.getTypeEntry();
	}

	public SubApp getSubapp() {
		return subapp;
	}

	@Override
	public boolean canExecute() {
		return null != typeEntry;
	}

	@Override
	public void execute() {
		if (subapp.getSubAppNetwork() == null) {
			// the subapp network was not yet copied from the type, i.e., subapp was never
			// shown in viewer
			subapp.setSubAppNetwork(
					FBNetworkHelper.copyFBNetWork(subapp.getType().getFBNetwork(), subapp.getInterface()));
		}
		removeType();
	}

	@Override
	public void redo() {
		removeType();
	}

	@Override
	public void undo() {
		subapp.setTypeEntry(typeEntry);
	}

	private void removeType() {
		subapp.setTypeEntry(null);
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(subapp);
	}
}
