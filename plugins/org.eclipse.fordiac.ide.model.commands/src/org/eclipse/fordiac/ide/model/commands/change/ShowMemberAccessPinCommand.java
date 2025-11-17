/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
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

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.ContainerVarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;

public class ShowMemberAccessPinCommand extends Command implements ScopedCommand {

	private final BlockFBNetworkElement fb;
	private final List<String> path;
	private IInterfaceElement pin;
	private ContainerVarDeclaration parent;
	private int index;

	public ShowMemberAccessPinCommand(final BlockFBNetworkElement fb, final List<String> path) {
		this.fb = fb;
		this.path = path;
	}

	@Override
	public boolean canExecute() {
		return fb != null && path != null && !path.isEmpty();
	}

	@Override
	public boolean canRedo() {
		return pin != null;
	}

	@Override
	public boolean canUndo() {
		return pin != null;
	}

	@Override
	public void execute() {
		pin = fb.getInterface().getInterfaceElement(path, true);
		if (pin != null) {
			pin.setVisible(true);
		}
	}

	@Override
	public void undo() {
		pin.setVisible(false);
		checkRemoval();
	}

	@Override
	public void redo() {
		if (parent != null) {
			parent.getCachedMembers().add(index, (VarDeclaration) pin);
		}
		pin.setVisible(true);
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		if (pin != null) {
			Set.of(pin);
		}
		return Collections.emptySet();
	}

	private void checkRemoval() {
		final ContainerVarDeclaration pinParent = HideMemberAccessPinCommand.getRemoveParent(pin);
		if (pinParent != null) {
			index = pinParent.getCachedMembers().indexOf(pin);
			if (index >= 0) {
				parent = pinParent;
				parent.getCachedMembers().remove(index);
			}
		}
	}

}
