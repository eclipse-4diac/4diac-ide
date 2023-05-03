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

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;

public class InsertVariableCommand extends Command {

	private final VarDeclaration type;
	private VarDeclaration varDecl;
	private final EList<VarDeclaration> list;
	private final int index;

	public InsertVariableCommand(final EList<VarDeclaration> list, final VarDeclaration type, final int index) {
		this.list = list;
		this.type = type;
		this.index = index;
	}

	@Override
	public void execute() {
		varDecl = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		varDecl.setType(type.getType());
		varDecl.setComment(type.getComment());
		varDecl.setArraySize(type.getArraySize());
		final Value value = LibraryElementFactory.eINSTANCE.createValue();
		final Value typeValue = type.getValue();
		value.setValue((typeValue == null) ? "" : typeValue.getValue()); //$NON-NLS-1$
		varDecl.setValue(value);
		redo();
		varDecl.setName(NameRepository.createUniqueName(varDecl, type.getName()));
	}

	@Override
	public void redo() {
		if (index > list.size()) {
			list.add(varDecl);
		} else {
			list.add(index, varDecl);
		}
	}

	@Override
	public void undo() {
		getVariableList().remove(varDecl);
	}

	private EList<VarDeclaration> getVariableList() {
		return list;
	}

}
