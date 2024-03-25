/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.commands.change;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.datatype.helper.InternalAttributeDeclarations;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.commands.Command;

public class ChangeRetainAttributeCommand extends Command implements ScopedCommand {

	private final IInterfaceElement pin;
	private final String oldValue;
	private final String newValue;

	public ChangeRetainAttributeCommand(final IInterfaceElement pin, final String oldValue, final String newValue) {
		this.pin = pin;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(pin);
	}

	@Override
	public void execute() {
		if (isTagged(newValue)) {
			pin.setAttribute(InternalAttributeDeclarations.RETAIN, newValue, "");
		} else {
			pin.deleteAttribute(LibraryElementTags.RETAIN_ATTRIBUTE);
		}
	}

	@Override
	public void redo() {
		execute();
	}

	@Override
	public void undo() {
		if (!isTagged(oldValue)) {
			pin.setAttribute(InternalAttributeDeclarations.RETAIN, oldValue, "");
		} else {
			pin.deleteAttribute(LibraryElementTags.RETAIN_ATTRIBUTE);
		}
	}

	private static boolean isTagged(final String tag) {
		return tag.equalsIgnoreCase("RETAIN") || tag.equalsIgnoreCase("NON_RETAIN");
	}

}
