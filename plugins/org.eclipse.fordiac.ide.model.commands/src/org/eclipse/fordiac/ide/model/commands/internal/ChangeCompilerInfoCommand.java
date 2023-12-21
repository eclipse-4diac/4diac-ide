/*******************************************************************************
 * Copyright (c) 2012, 2014 fortiss GmbH
 *               2019 Johannes Kepler University Linz
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
 *     - command now returns newly created element
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.internal;

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.gef.commands.Command;

/** The Class ChangeComplierInfoCommand. */
public abstract class ChangeCompilerInfoCommand extends Command implements ScopedCommand {
	private final LibraryElement libraryElement;

	/**
	 * Instantiates a new change comment command.
	 *
	 * @param type    which identification information is about to change
	 * @param comment the comment
	 */
	protected ChangeCompilerInfoCommand(final LibraryElement type) {
		this.libraryElement = Objects.requireNonNull(type);
	}

	public CompilerInfo getCompilerInfo() {
		if (libraryElement.getCompilerInfo() == null) {
			libraryElement.setCompilerInfo(LibraryElementFactory.eINSTANCE.createCompilerInfo());
		}
		return libraryElement.getCompilerInfo();
	}

	public LibraryElement getLibraryElement() {
		return libraryElement;
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		// compiler info affects the entire library element (e.g., package, imports)
		return Set.of(libraryElement);
	}
}
