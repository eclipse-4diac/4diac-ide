/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
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

package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.gef.commands.Command;

public class ChangeInternalFBOrderCommand extends Command {

	private final FB fb;
	private final LibraryElement element;
	private final int oldIndex;
	private int newIndex;

	public ChangeInternalFBOrderCommand(final LibraryElement element, final FB fb, IndexUpDown updown) {
		this.element = element;
		this.fb = fb;

		oldIndex = getInteralFBList().indexOf(fb);

		if (updown == IndexUpDown.DOWN) {
			newIndex = oldIndex + 1;
		} else if (updown == IndexUpDown.UP) {
			newIndex = oldIndex - 1;
		}

		if (newIndex < 0) {
			newIndex = 0;
		}
		if (newIndex >= getInteralFBList().size()) {
			newIndex = getInteralFBList().size() - 1;
		}
	}

	private EList<FB> getInteralFBList() {
		BasicFBType type = (BasicFBType) element;
		return type.getInternalFbs();
	}

	@Override
	public boolean canExecute() {
		return (null != fb) && (getInteralFBList().size() > 1) && (getInteralFBList().size() > newIndex);
	}

	@Override
	public void execute() {
		getInteralFBList().move(newIndex, fb);
	}

	@Override
	public void redo() {
		getInteralFBList().move(newIndex, fb);
	}

	@Override
	public void undo() {
		getInteralFBList().move(oldIndex, fb);
	}

}
