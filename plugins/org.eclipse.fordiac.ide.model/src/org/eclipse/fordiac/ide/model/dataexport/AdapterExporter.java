/********************************************************************************
 * Copyright (c) 2010, 2024 Profactor Gmbh, TU Wien ACIN, fortiss GmbH,
 * 							Johannes Keppler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Gerhard Ebenhofer, Monika Wenger, Alois Zoitl
 *    - initial API and implementation and/or initial documentation
 *  Alois Zoitl - Refactored class hierarchy of xml exporters
 *              - changed exporting the Saxx cursor api
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataexport;

import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;

public class AdapterExporter extends AbstractBlockTypeExporter {

	public AdapterExporter(final AdapterType type) {
		super(type);
	}

	@Override
	protected String getRootTag() {
		return LibraryElementTags.ADAPTER_TYPE;
	}

	@Override
	protected void createBlockTypeSpecificXMLEntries() {
		// for adapters there are no type specific xml entries
	}

}
