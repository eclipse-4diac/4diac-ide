/********************************************************************************
 * Copyright (c) 2014, 2106 - 2017  fortiss GmbH
 * 				 2018 Johannes Keppler University
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Alois Zoitl
 *    - initial API and implementation and/or initial documentation
 *  Alois Zoitl - Refactored class hierarchy of xml exporters  
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataexport;

import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

class SubApplicationTypeExporter extends AbstractTypeExporter {

	SubApplicationTypeExporter(SubApplicationTypePaletteEntry entry) {
		super(entry.getSubApplicationType());
	}

	/* constructor to allow to use this exporter also for exporting the 
	 * interface element of untyped subapps in fbnetworks
	 */
	SubApplicationTypeExporter(Document dom) {
		super(dom);
	}

	@Override
	protected SubAppType  getType() {
		return (SubAppType)super.getType();
	}
	
	@Override
	protected String getRootTag() {
		return  LibraryElementTags.SUBAPPTYPE_ELEMENT;
	}
	
	@Override
	protected void createTypeSpecificXMLEntries(Element rootElement) {
		rootElement.appendChild(new FBNetworkExporter(getDom()).createFBNetworkElement(getType().getFBNetwork()));
	}

	@Override
	protected String getInterfaceListElementName() {
		return LibraryElementTags.SUBAPPINTERFACE_LIST_ELEMENT;
	}

	@Override
	protected String getEventOutputsElementName() {
		return LibraryElementTags.SUBAPP_EVENTOUTPUTS_ELEMENT;
	}

	@Override
	protected String getEventInputsElementName() {
		return LibraryElementTags.SUBAPP_EVENTINPUTS_ELEMENT;
	}

	@Override
	protected String getEventElementName() {
		return LibraryElementTags.SUBAPP_EVENT_ELEMENT;
	}

}
