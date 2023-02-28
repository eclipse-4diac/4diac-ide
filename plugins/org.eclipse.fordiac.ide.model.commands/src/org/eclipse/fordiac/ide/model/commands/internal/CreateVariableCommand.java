/*******************************************************************************
 * Copyright (c) 2012, 2015 TU Wien ACIN, fortiss GmbH
 *               2019 - 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr
 *     - command now returns newly created elements, improve insertion
 *     - extracted reusable code from CreateInternalVariablesCommand to reuse command
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.internal;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;

/**
 * abstract command to add a variable to a list of a LibraryElement
 *
 */
public abstract class CreateVariableCommand extends CreationCommand {
	/** The type that the variable is added to */
	private final LibraryElement type;

	/** The new variable declaration */
	private VarDeclaration varDecl;
	private DataType dataType;
	private String name;
	private final int index;

	/**
	 * Instantiates a new create variable command.
	 *
	 * @param dataType the data type
	 * @param type     the library element the new variable is added to
	 */
	protected CreateVariableCommand(final LibraryElement type) {
		this(type, 0, null, null);
	}

	protected CreateVariableCommand(final LibraryElement type, final int index, final String name, final DataType dataType) {
		this.dataType = dataType;
		if (null == this.dataType) {
			this.dataType = IecTypes.ElementaryTypes.BOOL;
		}
		this.name = name;
		this.index = index;
		this.type = type;
	}

	/**
	 * subclasses most provide the list, to which the newly created variable shall
	 * be added
	 *
	 * @return EList<VarDeclaration> the list containing variable declarations
	 */
	protected abstract EList<VarDeclaration> getVariableList();

	/**
	 * subclasses must provide a default name for newly created variable
	 *
	 * @return String name
	 */
	protected abstract String getDefaultVarName();

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		if (null == name) {
			// if we haven't got a name proposal create a name now.
			name = getDefaultVarName();
		}
		varDecl = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		varDecl.setType(dataType);
		varDecl.setTypeName(dataType.getName());
		varDecl.setComment(""); //$NON-NLS-1$
		varDecl.setArraySize(0);
		final Value value = LibraryElementFactory.eINSTANCE.createValue();
		value.setValue(""); //$NON-NLS-1$
		varDecl.setValue(value);
		redo();
		varDecl.setName(NameRepository.createUniqueName(varDecl, name));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		getVariableList().remove(varDecl);
	}

	public VarDeclaration getVarDeclaration() {
		return varDecl;
	}

	protected LibraryElement getType() {
		return type;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		getVariableList().add(index, varDecl);
	}

	@Override
	public Object getCreatedElement() {
		return getVarDeclaration();
	}
}
