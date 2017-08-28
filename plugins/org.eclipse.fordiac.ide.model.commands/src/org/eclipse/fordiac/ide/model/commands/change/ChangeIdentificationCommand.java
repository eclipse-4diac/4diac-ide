/*******************************************************************************
 * Copyright (c) 2012 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.libraryElement.Identification;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.gef.commands.Command;

/**
 * The Class ChangeCommentCommand.
 */
public abstract class ChangeIdentificationCommand extends Command {

	/** The identification of the type. */
	protected Identification identification;

	/**
	 * Instantiates a new change comment command.
	 * 
	 * @param type which identification information is about to change
	 * @param comment the comment
	 */
	public ChangeIdentificationCommand(LibraryElement type) {
		super();
		if (null == type.getIdentification()) {
			type.setIdentification(LibraryElementFactory.eINSTANCE
					.createIdentification());
		}
		identification = type.getIdentification();
	}


}
