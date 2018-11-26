/*******************************************************************************
 * Copyright (c) 2012, 2014 fortiss GmbH
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
package org.eclipse.fordiac.ide.model.commands.create;

import org.eclipse.fordiac.ide.model.commands.change.ChangeComplierInfoCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Compiler;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.Language;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;

/**
 * The Class AddNewCompilerCommand.
 */
public class AddNewCompilerCommand extends ChangeComplierInfoCommand {
	
	/** The new Compiler value. */
	private Compiler compiler;


	public AddNewCompilerCommand(final FBType type) {
		super(type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		compiler = LibraryElementFactory.eINSTANCE.createCompiler();
		compiler.setLanguage(Language.OTHER);
		compiler.setVersion("1.0"); //$NON-NLS-1$
		compiler.setVendor("Unknown");
		compiler.setProduct("Unknown");
		
		redo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		compilerInfo.getCompiler().remove(compiler);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		compilerInfo.getCompiler().add(compiler);
	}

}
