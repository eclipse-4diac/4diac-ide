/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.delete;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.gef.commands.Command;

public class DeleteInternalFBCommand extends Command {

	/** The type to whose list the new variable is added. */
	private final LibraryElement element;

	/** The variable that is deleted */
	private FB fbToDelete;

	/** The old index. */
	private int oldIndex;

	public DeleteInternalFBCommand(final LibraryElement element, final FB fb) {
		this.element = element;
		this.fbToDelete = fb;
	}

	private EList<FB> getInteralFBList() {
		BasicFBType type = (BasicFBType) element;
		return type.getInternalFbs();
	}

	@Override
	public void execute() {
		oldIndex = getInteralFBList().indexOf(fbToDelete);
		redo();
	}

	@Override
	public void redo() {
		getInteralFBList().remove(fbToDelete);
	}

	@Override
	public void undo() {
		getInteralFBList().add(oldIndex, fbToDelete);
	}

}
