/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.insert;

import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.gef.commands.Command;

public class InsertAlgorithmCommand extends Command {

	private final BasicFBType fbType;
	private STAlgorithm newAlgorithm;
	private final Algorithm oldAlgorithm;
	private final int index;

	public InsertAlgorithmCommand(final BasicFBType fbType, final Algorithm oldAlgorithm, final int index) {
		this.fbType = fbType;
		this.oldAlgorithm = oldAlgorithm;
		this.index = index;
	}

	@Override
	public void execute() {
		newAlgorithm = LibraryElementFactory.eINSTANCE.createSTAlgorithm();
		newAlgorithm.setName(oldAlgorithm.getName());
		newAlgorithm.setComment(oldAlgorithm.getComment());
		newAlgorithm.setText(((STAlgorithm) oldAlgorithm).getText());
		redo();
		newAlgorithm.setName(NameRepository.createUniqueName(newAlgorithm, oldAlgorithm.getName()));
	}

	@Override
	public void undo() {
		fbType.getCallables().remove(newAlgorithm);
	}

	@Override
	public void redo() {
		fbType.getCallables().add(index, newAlgorithm);
	}

}
