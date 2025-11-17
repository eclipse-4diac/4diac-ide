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

import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.ContainerVarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class HideMemberAccessPinCommand extends HidePinCommand {

	private ContainerVarDeclaration parent;
	private int index;

	public HideMemberAccessPinCommand(final BlockFBNetworkElement fb, final List<String> path) {
		super(getPin(fb, path), false);
	}

	@Override
	public void execute() {
		super.execute();
		checkRemovePin();
	}

	@Override
	public void undo() {
		checkReAddPin();
		super.undo();
	}

	@Override
	public void redo() {
		super.redo();
		if (parent != null) {
			parent.getCachedMembers().remove(index);
		}
	}

	private void checkRemovePin() {
		final ContainerVarDeclaration pinParent = getRemoveParent(getInterfaceElement());
		if (pinParent != null) {
			index = pinParent.getCachedMembers().indexOf(getInterfaceElement());
			if (index >= 0) {
				parent = pinParent;
				parent.getCachedMembers().remove(index);
			}
		}
	}

	public static ContainerVarDeclaration getRemoveParent(final IInterfaceElement pin) {
		if ((pin.eContainer() instanceof final ContainerVarDeclaration contVarDecl)
				&& (pin instanceof final ContainerVarDeclaration contPin && contPin.getCachedMembers().isEmpty())) {
			// the pin to remove still has no visible children, so we can remove
			return contVarDecl;
		}
		return null;
	}

	private void checkReAddPin() {
		if (parent != null) {
			parent.getCachedMembers().add(index, (VarDeclaration) getInterfaceElement());
		}
	}

	private static IInterfaceElement getPin(final BlockFBNetworkElement fb, final List<String> path) {
		return fb.getInterface().getInterfaceElement(path, false);
	}

}
