/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 * 			2018, TU Wien/ACIN
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
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

	private final List<String> errors = new ArrayList<>();
	private final List<String> warnings = new ArrayList<>();
	private final List<String> infos = new ArrayList<>();

	/**
	 * Instantiates a new export filter.
	 */
	protected ExportFilter() {

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
