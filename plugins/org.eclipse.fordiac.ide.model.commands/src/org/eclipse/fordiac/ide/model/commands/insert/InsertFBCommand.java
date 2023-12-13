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

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.gef.commands.Command;

public class InsertFBCommand extends Command implements ScopedCommand {

	private final BaseFBType baseFBType;
	private final FB fb;
	private FB internalFB;
	private final int index;

	public InsertFBCommand(final BaseFBType baseFBType, final FB fb, final int index) {
		this.baseFBType = Objects.requireNonNull(baseFBType);
		this.fb = Objects.requireNonNull(fb);
		this.index = index;
	}

	@Override
	public void execute() {
		if (fb.getType() instanceof CompositeFBType) {
			internalFB = LibraryElementFactory.eINSTANCE.createCFBInstance();
		} else {
			internalFB = LibraryElementFactory.eINSTANCE.createFB();
		}
		internalFB.setTypeEntry(fb.getType().getTypeEntry());
		internalFB.setComment(""); //$NON-NLS-1$
		redo();
		internalFB.setName(NameRepository.createUniqueName(internalFB, fb.getName()));
	}

	@Override
	public void redo() {
		if (index > baseFBType.getInternalFbs().size()) {
			baseFBType.getInternalFbs().add(internalFB);
		} else {
			baseFBType.getInternalFbs().add(index, internalFB);
		}
	}

	@Override
	public void undo() {
		baseFBType.getInternalFbs().remove(internalFB);
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(baseFBType);
	}
}
