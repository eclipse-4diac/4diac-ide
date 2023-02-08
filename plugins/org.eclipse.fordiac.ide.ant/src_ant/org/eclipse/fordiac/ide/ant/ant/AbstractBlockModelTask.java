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
 *   Ernst Blecha - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ant.ant;

import java.text.MessageFormat;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;

public abstract class AbstractBlockModelTask extends Task {

	public void setProjectname(final String projectname) {
		this.projectname = nullCheckString(projectname);
	}

	public void setBlockname(final String blockname) {
		this.blockname = nullCheckString(blockname);
	}

	@Override
	public final void execute() throws BuildException {

		final IProject fordiacProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectname);
		if (fordiacProject == null) {
			throw new BuildException(MessageFormat.format("Project named {0} not in workspace", projectname));//$NON-NLS-1$
		}

		final var tl = TypeLibraryManager.INSTANCE.getTypeLibrary(fordiacProject);
		if (tl == null) {
			throw new BuildException(MessageFormat.format("Can not get TypeLib for {0}", projectname)); //$NON-NLS-1$
		}

		final var t = tl.getFBTypeEntry(blockname);
		if (t == null) {
			throw new BuildException(MessageFormat.format("Can not get FBTypeEntry for {0}", blockname)); //$NON-NLS-1$
		}

		final FBType fb = t.getTypeEditable();
		if (fb == null) {
			throw new BuildException(MessageFormat.format("Can not get FBType for {0}", blockname)); //$NON-NLS-1$
		}

		modifyBlock(fb);
	}

	protected abstract void modifyBlock(final FBType fb);

	protected String projectname = EMPTY_STRING;
	protected String blockname = EMPTY_STRING;

	protected static final String EMPTY_STRING = ""; //$NON-NLS-1$

	protected static String nullCheckString(final String s) {
		return s == null ? EMPTY_STRING : s;
	}

}
