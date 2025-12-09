/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.gef.commands.Command;

public class ChangeDocumentationCommand extends Command implements ScopedCommand {

	private final LibraryElement type;
	private final String newDocumentation;
	private String oldDocumentation;

	public ChangeDocumentationCommand(final LibraryElement type, final String newDocumenation) {
		this.type = Objects.requireNonNull(type);
		this.newDocumentation = (null == newDocumenation) ? "" : newDocumenation; //$NON-NLS-1$
	}

	@Override
	public void execute() {
		oldDocumentation = type.getDocumentation();
		setDocumentation(newDocumentation);
	}

	@Override
	public void undo() {
		setDocumentation(oldDocumentation);
	}

	@Override
	public void redo() {
		setDocumentation(newDocumentation);
	}

	private void setDocumentation(final String newValue) {
		type.setDocumentation(newValue);
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(type);
	}
}
