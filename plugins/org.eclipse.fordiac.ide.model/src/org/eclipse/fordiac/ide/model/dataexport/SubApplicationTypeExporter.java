/********************************************************************************
 * Copyright (c) 2014, 2106 - 2017  fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Alois Zoitl
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataexport;

import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

class SubApplicationTypeExporter extends CommonElementExporter {

	@Override
	protected FBType getType(PaletteEntry entry) {
		return ((SubApplicationTypePaletteEntry)entry).getSubApplicationType();
	}

	@Override
	protected void addType(Document dom, FBType fbType) {
		Element rootElement = createRootElement(dom, fbType, LibraryElementTags.SUBAPPTYPE_ELEMENT);
		addCompileAbleTypeData(dom, rootElement, fbType);
		addInterfaceList(dom, rootElement, fbType.getInterfaceList());
		rootElement.appendChild(new FBNetworkExporter(dom).createFBNetworkElement(((SubAppType)fbType).getFBNetwork()));
		addService(dom, rootElement, fbType);
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
