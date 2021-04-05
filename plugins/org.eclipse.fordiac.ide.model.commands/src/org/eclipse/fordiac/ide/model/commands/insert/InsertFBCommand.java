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

package org.eclipse.fordiac.ide.model.commands.insert;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.gef.commands.Command;

public class InsertFBCommand extends Command {

	private final FBType fbTypeEntry;
	private FB internalFB;
	private final EList<FB> internalFbs;
	private final int index;

	public InsertFBCommand(final EList<FB> internalFbs, final FBType fbTypeEntry, final int index) {
		this.internalFbs = internalFbs;
		this.fbTypeEntry = fbTypeEntry;
		this.index = index;
	}

	@Override
	public void execute() {
		if (fbTypeEntry instanceof CompositeFBType) {
			internalFB = LibraryElementFactory.eINSTANCE.createCFBInstance();
		} else {
			internalFB = LibraryElementFactory.eINSTANCE.createFB();
		}
		internalFB.setPaletteEntry(fbTypeEntry.getPaletteEntry());
		internalFB.setComment(""); //$NON-NLS-1$
		redo();
		internalFB.setName(NameRepository.createUniqueName(internalFB, fbTypeEntry.getName()));
	}

	@Override
	public void redo() {
		internalFbs.add(index, internalFB);
	}

	@Override
	public void undo() {
		internalFbs.remove(internalFB);
	}

}
