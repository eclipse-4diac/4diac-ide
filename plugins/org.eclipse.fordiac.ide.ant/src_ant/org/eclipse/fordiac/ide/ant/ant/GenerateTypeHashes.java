/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ernst Blecha - initial implementation and/or documentation
 *   Alois Zoitl  - taken ResaveTypelib as basis for this task
 *******************************************************************************/
package org.eclipse.fordiac.ide.ant.ant;

import java.text.MessageFormat;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.util.LibraryElementHashException;

public class GenerateTypeHashes extends Task {

	private String projectName = AbstractBlockModelTask.EMPTY_STRING;

	public void setProjectname(final String projectName) {
		this.projectName = AbstractBlockModelTask.nullCheckString(projectName);
	}

	@Override
	public final void execute() throws BuildException {
		final var fordiacProject = AbstractBlockModelTask.getFordiacProject(projectName);
		final var tl = AbstractBlockModelTask.getTypeLibrary(fordiacProject, projectName);

		log(MessageFormat.format("Type Hashes for {0}", projectName)); //$NON-NLS-1$
		tl.getAllTypes().forEach(this::logTypeHash);
	}

	private void logTypeHash(final TypeEntry te) {
		try {
			log(MessageFormat.format("{0}: {1}", te.getFullTypeName(), te.getTypeHash())); //$NON-NLS-1$
		} catch (final LibraryElementHashException e) {
			throw new BuildException(MessageFormat.format("Cannot create type hash for {0}", te.getFullTypeName()), e);//$NON-NLS-1$
		}
	}

}
