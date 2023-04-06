/*******************************************************************************
 * Copyright (c) 2022, 2023 Primetals Technologies Austria GmbH
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
 *   Ernst Blecha - changed base class
 *******************************************************************************/
package org.eclipse.fordiac.ide.ant.ant;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExportProjectLibElements extends AbstractExportLibElements {

	private List<String> excludeSubfolder = new ArrayList<>();

	public void setExcludeSubfolder(final String value) {
		this.excludeSubfolder = new ArrayList<>(Arrays.asList(value.split(","))); //$NON-NLS-1$
	}

	@Override
	protected File getDirectory() {
		return new File(getFordiacProject().getLocationURI());
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
