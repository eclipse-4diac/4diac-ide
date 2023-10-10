/*******************************************************************************
 * Copyright (c) 2020 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr - initial implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.ConnectionLayoutTagger;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.commands.Command;

public class ToggleSubAppRepresentationCommand extends Command implements ConnectionLayoutTagger {

	private final SubApp subapp;
	private String oldAttribute;
	private String newAttribute;

	public ToggleSubAppRepresentationCommand(final SubApp subapp) {
		this.subapp = subapp;
	}

	@Override
	public void execute() {
		oldAttribute = subapp.getAttributeValue(LibraryElementTags.SUBAPP_REPRESENTATION_ATTRIBUTE);
		newAttribute = subapp.isUnfolded() ? null : "true"; //$NON-NLS-1$

		setRepresentationAttribute(newAttribute);
	}

	@Override
	public void undo() {
		setRepresentationAttribute(oldAttribute);
	}

	@Override
	public void redo() {
		setRepresentationAttribute(newAttribute);
	}

	private void setRepresentationAttribute(final String text) {
		if (null == text) {
			subapp.deleteAttribute(LibraryElementTags.SUBAPP_REPRESENTATION_ATTRIBUTE);
		} else {
			subapp.setAttribute(LibraryElementTags.SUBAPP_REPRESENTATION_ATTRIBUTE, ElementaryTypes.STRING, text, ""); //$NON-NLS-1$
		}
	}
}
