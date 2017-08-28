/*******************************************************************************
 * Copyright (c) 2012, 2015 TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands;

import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.VarInitialization;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.gef.commands.Command;

/**
 * The Class CreateInternalVariableCommand.
 */
public class CreateInternalVariableCommand extends Command {

	/** The data type. */
	private final DataType dataType;

	/** The fb type. */
	private final BasicFBType fbType;

	private VarDeclaration varDecl;
	
	/**
	 * Instantiates a new creates the input variable command.
	 * 
	 * @param dataType
	 *            the data type
	 * @param fbType
	 *            the fb type
	 */
	public CreateInternalVariableCommand(final BasicFBType fbType) {
		this.dataType = DataTypeLibrary.getInstance().getType("BOOL"); //$NON-NLS-1$
		this.fbType = fbType;
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
		VarInitialization varInitialization = DataFactory.eINSTANCE.createVarInitialization();
		varInitialization.setInitialValue(""); //$NON-NLS-1$
		varDecl.setVarInitialization(varInitialization);
		Value value = LibraryElementFactory.eINSTANCE.createValue();
		value.setValue(""); //$NON-NLS-1$
		varDecl.setValue(value);
		redo();
		varDecl.setName(NameRepository.createUniqueName(varDecl, "INTERNALVAR")); //$NON-NLS-1$
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
		fbType.getInternalVars().add(varDecl);
	}
}
