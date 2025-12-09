/*******************************************************************************
 * Copyright (c) 2012 fortiss GmbH
 *               2020 Johannes Kepler University
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
 *   Bianca Wiesmayr
 *     - fix behaviour on passing null
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.eclipse.gef.commands.Command;

/**
 * change the author in a version info
 */
public class ChangeAuthorCommand extends Command implements ScopedCommand {

	private final VersionInfo versionInfo;

	/** The new ApplicationDomain value. */
	private final String newAuthor;

	/** The old ApplicationDomain value. */
	private String oldAuthor;

	public ChangeAuthorCommand(final VersionInfo versionInfo, final String newAuthor) {
		this.versionInfo = Objects.requireNonNull(versionInfo);
		this.newAuthor = (newAuthor == null) ? "" : newAuthor; //$NON-NLS-1$
	}

	@Override
	public void execute() {
		oldAuthor = versionInfo.getAuthor();
		setNewAuthor();
	}

	@Override
	public void undo() {
		versionInfo.setAuthor(oldAuthor);
	}

	@Override
	public void redo() {
		setNewAuthor();
	}

	private void setNewAuthor() {
		versionInfo.setAuthor(newAuthor);
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(versionInfo);
	}
}
