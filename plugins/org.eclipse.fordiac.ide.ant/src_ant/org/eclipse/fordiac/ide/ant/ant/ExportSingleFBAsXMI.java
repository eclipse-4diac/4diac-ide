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
import java.util.LinkedList;
import java.util.List;

import org.apache.tools.ant.BuildException;

public class ExportSingleFBAsXMI extends ExportFBsAsXMI {

	private String fBNameString;

	public void setFBName(final String value) {
		this.fBNameString = value;
	}

	@Override
	public void execute() throws BuildException {
		super.execute();

		final List<File> files = new LinkedList<>();
		getFBsFiles(files, new File(getFordiacProject().getLocationURI()), fBNameString, new ArrayList<>());
		exportFiles(files);
	}

}
