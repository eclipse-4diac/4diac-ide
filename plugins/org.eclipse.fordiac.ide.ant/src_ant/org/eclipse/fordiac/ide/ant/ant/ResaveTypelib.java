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
 *******************************************************************************/
package org.eclipse.fordiac.ide.ant.ant;

import java.text.MessageFormat;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class ResaveTypelib extends Task {

	public void setAutoFormat(final boolean enable) {
		this.autoformat = enable;
	}

	public void setProjectname(final String projectname) {
		this.projectname = AbstractBlockModelTask.nullCheckString(projectname);
	}

	@Override
	public final void execute() throws BuildException {
		final var fordiacProject = AbstractBlockModelTask.getFordiacProject(projectname);
		final var tl = AbstractBlockModelTask.getTypeLibrary(fordiacProject, projectname);

		log(MessageFormat.format("Save {0}", projectname)); //$NON-NLS-1$
		tl.getAllTypes().forEach(te -> SaveBlock.saveBlock(te, autoformat, this::log));
	}

	protected String projectname = AbstractBlockModelTask.EMPTY_STRING;

	private boolean autoformat = false;

}
