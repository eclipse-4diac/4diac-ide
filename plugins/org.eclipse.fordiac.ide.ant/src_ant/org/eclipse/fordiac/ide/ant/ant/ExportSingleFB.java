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
import java.util.List;

public class ExportSingleFB extends ExportFBs {

	private String fBNameString;

	public void setFBName(final String value) {
		this.fBNameString = value;
	}

	@Override
	protected File getDirectory() {
		return new File(getFordiacProject().getLocationURI());
	}

	@Override
	protected String getSingleFBName() {
		return fBNameString;
	}

	@Override
	protected List<String> getExcludeSubfolder() {
		return new ArrayList<>();
	}

}
