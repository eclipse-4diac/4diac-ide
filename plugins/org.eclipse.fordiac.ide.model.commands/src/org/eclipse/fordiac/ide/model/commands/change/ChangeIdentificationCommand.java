/*******************************************************************************
 * Copyright (c) 2012 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Identification;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.gef.commands.Command;

/**
 * The Class ChangeCommentCommand.
 */
public abstract class ChangeIdentificationCommand extends Command implements ScopedCommand {

	/** The identification of the type. */
	private final Identification identification;

	protected Identification getIdentification() {
		return identification;
	}

	/**
	 * Instantiates a new change comment command.
	 *
	 * @param type    which identification information is about to change
	 * @param comment the comment
	 */
	protected ChangeIdentificationCommand(final LibraryElement type) {
		if (null == type.getIdentification()) {
			type.setIdentification(LibraryElementFactory.eINSTANCE.createIdentification());
		}
		identification = type.getIdentification();
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(identification);
	}
}
