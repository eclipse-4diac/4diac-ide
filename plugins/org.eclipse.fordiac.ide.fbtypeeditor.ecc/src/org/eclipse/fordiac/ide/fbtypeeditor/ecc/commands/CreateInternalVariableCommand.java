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
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands;

import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.ui.providers.AbstractCreationCommand;

/**
 * The Class CreateInternalVariableCommand.
 */
public class CreateInternalVariableCommand extends AbstractCreationCommand {

	private static final String DEFAULT_VAR_NAME = "INTERNALVAR1"; // $NON-NLS-1$

	/** The data type. */
	private final DataType dataType;

	/** The fb type. */
	private final BasicFBType fbType;

	private VarDeclaration varDecl;
	private String name;
	private int index;

	/**
	 * Instantiates a new creates the input variable command.
	 *
	 * @param dataType the data type
	 * @param fbType   the fb type
	 */
	public CreateInternalVariableCommand(final BasicFBType fbType) {
		this(fbType, fbType.getInternalVars().size() - 1, null, null);
	}

	public CreateInternalVariableCommand(final BasicFBType fbType, int index, String name, DataType dataType) {
		this.dataType = (null != dataType) ? dataType : DataTypeLibrary.getInstance().getType("BOOL"); //$NON-NLS-1$
		this.fbType = fbType;
		this.name = (null != name) ? name : DEFAULT_VAR_NAME;
		this.index = index;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		varDecl = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		varDecl.setType(dataType);
		varDecl.setTypeName(dataType.getName());
		varDecl.setComment("Internal Variable"); //$NON-NLS-1$
		varDecl.setArraySize(0);
		Value value = LibraryElementFactory.eINSTANCE.createValue();
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
		fbType.getInternalVars().remove(varDecl);
	}

	public VarDeclaration getVarDecl() {
		return varDecl;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		fbType.getInternalVars().add(index, varDecl);
	}

	@Override
	public Object getCreatedElement() {
		return varDecl;
	}
}
