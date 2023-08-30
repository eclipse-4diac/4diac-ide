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

import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.gef.commands.Command;

/** The Class ChangeComplierInfoCommand. */
public abstract class ChangeCompilerInfoCommand extends Command {

	/** The identification of the type. */
	private final CompilerInfo compilerInfo;

	public CompilerInfo getCompilerInfo() {
		return compilerInfo;
	}

	/** Instantiates a new change comment command.
	 *
	 * @param type    which identification information is about to change
	 * @param comment the comment */
	protected ChangeCompilerInfoCommand(final LibraryElement type) {
		if (type.getCompilerInfo() == null) {
			type.setCompilerInfo(LibraryElementFactory.eINSTANCE.createCompilerInfo());
		}

		this.compilerInfo = type.getCompilerInfo();
	}

}
