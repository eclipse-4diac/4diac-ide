/********************************************************************************
 * Copyright (c) 2008, 2025 Profactor GmbH, fortiss GmbH,
 *                          Johannes Kepler University Linz
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl
 *    - initial API and implementation and/or initial documentation
 *  Alois Zoitl - Changed XML parsing to Staxx cursor interface for improved
 *  			  parsing performance
 *  Martin Jobst - extract interface importer
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport;

import org.eclipse.fordiac.ide.model.LibraryElementTags;

public class SubAppInterfaceListImporter extends InterfaceListImporter {

	public SubAppInterfaceListImporter(final CommonElementImporter importer) {
		super(importer);
	}

	@Override
	protected String getEventOutputElement() {
		return LibraryElementTags.SUBAPP_EVENTOUTPUTS_ELEMENT;
	}

	@Override
	protected String getEventInputElement() {
		return LibraryElementTags.SUBAPP_EVENTINPUTS_ELEMENT;
	}

	@Override
	protected String getEventElement() {
		return LibraryElementTags.SUBAPP_EVENT_ELEMENT;
	}

	@Override
	protected void processWiths() {
		// supapps may not have a with construct. Therefore we are doing nothing here
	}
}
