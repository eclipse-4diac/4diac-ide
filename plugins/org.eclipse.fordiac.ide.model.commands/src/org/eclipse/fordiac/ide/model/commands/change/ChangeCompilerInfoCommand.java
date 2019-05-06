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

import org.eclipse.fordiac.ide.model.libraryElement.CompilableType;
import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.gef.commands.Command;

/**
 * The Class ChangeComplierInfoCommand.
 */
public abstract class ChangeCompilerInfoCommand extends Command {

	/** The identification of the type. */
	private CompilerInfo compilerInfo;
	
	public CompilerInfo getCompilerInfo() {
		return compilerInfo;
	}

	/**
	 * Instantiates a new change comment command.
	 * 
	 * @param type which identification information is about to change
	 * @param comment the comment
	 */
	public ChangeCompilerInfoCommand(final CompilableType type) {
		super();
		
		if (type.getCompilerInfo() == null) {
			type.setCompilerInfo(LibraryElementFactory.eINSTANCE.createCompilerInfo());
		}	
		
		this.compilerInfo = type.getCompilerInfo();
	}


}
