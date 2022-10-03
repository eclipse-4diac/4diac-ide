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
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ant.ant;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.tools.ant.BuildException;

public class ExportFolderLibElements extends ExportLibElements {

	private String folderNameString;
	private List<String> excludeSubfolder = Collections.emptyList();

	public void setFolderName(final String value) {
		this.folderNameString = value;
	}

	public void setExcludeSubfolder(final String value) {
		this.excludeSubfolder = new ArrayList<>(Arrays.asList(value.split(","))); //$NON-NLS-1$
	}

	@Override
	protected File getDirectory() {
		final File folder = findFolderInProject(new File(getFordiacProject().getLocationURI()), folderNameString);

		if (folder == null) {
			throw new BuildException(
					"No folder named '" + folderNameString + "' in Project '" + getFordiacProject().getName() + "'");//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		return folder;
	}

	@Override
	protected String getSingleFBName() {
		return null;
	}

	@Override
	protected List<String> getExcludeSubfolder() {
		return excludeSubfolder;
	}

}
