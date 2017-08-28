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
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.libraryElement.Compiler;
import org.eclipse.fordiac.ide.model.libraryElement.Language;
import org.eclipse.gef.commands.Command;

/**
 * The Class ChangeCompilerLanguageCommand.
 */
public class ChangeCompilerLanguageCommand extends Command {
	
	
	/** The new Compiler value. */
	private Compiler compiler;
	
	private Language newLanguage;
	private Language oldLanguage;


	public ChangeCompilerLanguageCommand(final Compiler compiler, final Language newLanguage) {
		super();
		this.compiler = compiler;
		this.newLanguage = newLanguage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		oldLanguage = compiler.getLanguage();
		redo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		compiler.setLanguage(oldLanguage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		compiler.setLanguage(newLanguage);
	}

}
