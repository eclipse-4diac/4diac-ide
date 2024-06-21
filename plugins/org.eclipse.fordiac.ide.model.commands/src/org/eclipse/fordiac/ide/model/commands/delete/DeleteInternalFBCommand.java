/*******************************************************************************
 * Copyright (c) 2021, 2024 Primetals Technologies Germany GmbH
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
 *   Fabio Gandolfi
 *     - fixed command to search for correct object and save it for later redo
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.delete;

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.gef.commands.Command;

public class DeleteInternalFBCommand extends Command implements ScopedCommand {

	/** The type to whose list the new variable is added. */
	private final BaseFBType baseFbtype;

	/** The variable that is deleted */
	private final FB fbToDelete;

	/** The old index. */
	private int oldIndex;

	public DeleteInternalFBCommand(final FB fb) {
		this.fbToDelete = Objects.requireNonNull(fb);
		if (!(fb.eContainer() instanceof final BaseFBType baseFBtype)) {
			throw new IllegalArgumentException("FB to delete is not contained in BaseFBType!"); //$NON-NLS-1$
		}
		this.baseFbtype = baseFBtype;
	}

	@Override
	public void execute() {
		oldIndex = getIndexOfFBtoDelete();
		removeFB();
	}

	@Override
	public void redo() {
		removeFB();
	}

	@Override
	public void undo() {
		getInteralFBList().add(oldIndex, fbToDelete);
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(baseFbtype);
	}

	private int getIndexOfFBtoDelete() {
		return getInteralFBList().indexOf(fbToDelete);
	}

	private EList<FB> getInteralFBList() {
		return baseFbtype.getInternalFbs();
	}

	private void removeFB() {
		getInteralFBList().remove(fbToDelete);
	}
}
