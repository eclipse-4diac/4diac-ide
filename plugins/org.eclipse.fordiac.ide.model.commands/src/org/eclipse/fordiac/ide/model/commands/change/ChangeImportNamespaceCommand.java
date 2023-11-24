/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Import;
import org.eclipse.gef.commands.Command;

/** The Class ChangeCompilerProductCommand. */
public class ChangeImportNamespaceCommand extends Command implements ScopedCommand {

	/** The new Compiler value. */
	private final Import importer;

	private final String newNamespace;
	private String oldNamespace;

	public ChangeImportNamespaceCommand(final Import importer, final String newNamespace) {
		this.importer = Objects.requireNonNull(importer);
		this.newNamespace = (null == newNamespace) ? "" : newNamespace; //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		oldNamespace = importer.getImportedNamespace();
		redo();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		importer.setImportedNamespace(oldNamespace);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		importer.setImportedNamespace(newNamespace);
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		// imports affect the root container
		return Set.of(EcoreUtil.getRootContainer(importer));
	}
}
