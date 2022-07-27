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
 *   Fabio Gandolfi - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ant.ant;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.tools.ant.BuildException;

public class ExportFolderFBsAsXMI extends ExportFBsAsXMI {

	private String folderNameString;
	private List<String> excludeSubfolder = new ArrayList<>();

	public void setFolderName(final String value) {
		this.folderNameString = value;
	}

	public void setExcludeSubfolder(final String value) {
		this.excludeSubfolder = new ArrayList<>(Arrays.asList(value.split(","))); //$NON-NLS-1$
	}

	@Override
	public void execute() throws BuildException {
		super.execute();

		final File folder = findFolderInProject(new File(getFordiacProject().getLocationURI()), folderNameString);

		if (folder == null) {
			throw new BuildException(
					"No folder named '" + folderNameString + "' in Project '" + getFordiacProject().getName() + "'");//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}

		final List<File> files = new LinkedList<>();
		getFBsFiles(files, folder, null, excludeSubfolder);
		exportFiles(files);
	}

	private static File findFolderInProject(final File dir, final String dirName) {
		if (dir.isDirectory() && dir.getName().equals(dirName)) {
			return dir;
		}

		if (dir.listFiles() != null) {
			File returnValue = null;
			for (final File file : dir.listFiles()) {
				if (returnValue == null) {
					if (file.isDirectory()) {
						returnValue = findFolderInProject(file, dirName);
					}
				} else {
					return returnValue;
				}
			}
		}
		return null;
	}

}
