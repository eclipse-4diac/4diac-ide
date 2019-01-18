/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 * 			2018, TU Wien/ACIN
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger, Ingo Hegny, Martin Jobst
 *     - initial API and implementation and/or initial documentation
 *   Martin Melik Merkumians - adds export for SimpleFB
 *******************************************************************************/
package org.eclipse.fordiac.ide.export;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class ExportFilter.
 */
public abstract class ExportFilter implements IExportFilter {

	private List<String> errors = new ArrayList<String>();
	private List<String> warnings = new ArrayList<String>();
	private List<String> infos = new ArrayList<String>();

	/**
	 * Instantiates a new export filter.
	 */
	public ExportFilter() {

	}

	@Override
	public List<String> getErrors() {
		return errors;
	}

	@Override
	public List<String> getWarnings() {
		return warnings;
	}

	@Override
	public List<String> getInfos() {
		return infos;
	}
}
