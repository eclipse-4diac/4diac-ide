/*******************************************************************************
 * Copyright (c) 2026 Vikash Kumar Sinha
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vikash Kumar Sinha
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.actions.ECCCopyPasteData;
import org.eclipse.fordiac.ide.gef.utilities.ElementSelector;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.gef.commands.Command;

public class PasteECCCommand extends Command {

	private final ECCCopyPasteData data;
	private final ECC ecc;
	private final Position refPos;
	private final List<ECState> pasted = new ArrayList<>();

	public PasteECCCommand(final ECCCopyPasteData data, final ECC ecc, final Position refPos) {
		this.data = data;
		this.ecc = ecc;
		this.refPos = refPos;
	}

	@Override
	public boolean canExecute() {
		return (data != null) && !data.isEmpty() && (ecc != null) && (refPos != null);
	}

	@Override
	public void execute() {
		pasted.clear();

		final double minX = data.states().stream().mapToDouble(s -> s.getPosition().getX()).min().orElse(0.0);
		final double minY = data.states().stream().mapToDouble(s -> s.getPosition().getY()).min().orElse(0.0);

		final double deltaX = refPos.getX() - minX;
		final double deltaY = refPos.getY() - minY;

		for (final ECState src : data.states()) {
			final ECState copy = EcoreUtil.copy(src);
			final Position pos = LibraryElementFactory.eINSTANCE.createPosition();
			pos.setX(src.getPosition().getX() + deltaX);
			pos.setY(src.getPosition().getY() + deltaY);
			copy.setPosition(pos);

			ecc.getECState().add(copy);
			copy.setName(NameRepository.createUniqueName(copy, src.getName()));
			pasted.add(copy);
		}

		ElementSelector.selectViewObjects(pasted);
	}

	@Override
	public void undo() {
		ecc.getECState().removeAll(pasted);
	}

	@Override
	public void redo() {
		ecc.getECState().addAll(pasted);
		ElementSelector.selectViewObjects(pasted);
	}
}
