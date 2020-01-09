/********************************************************************************
 * Copyright (c) 2010 - 2014, 2017 Profactor Gmbh, TU Wien ACIN, fortiss GmbH
 * 				 2018 Johannes Keppler University
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
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataexport;

import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.w3c.dom.Element;

class AdapterExporter extends AbstractTypeExporter {

	public AdapterExporter(AdapterTypePaletteEntry entry) {
		super(entry.getType().getAdapterFBType());
	}

	@Override
	protected String getRootTag() {
		return LibraryElementTags.ADAPTER_TYPE;
	}

	@Override
	protected void createTypeSpecificXMLEntries(Element rootElement) {
		// for adapters there are no type specific xml entries
	}

}
