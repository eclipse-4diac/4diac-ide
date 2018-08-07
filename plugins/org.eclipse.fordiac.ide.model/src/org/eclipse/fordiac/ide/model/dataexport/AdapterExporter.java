/********************************************************************************
 * Copyright (c) 2010 - 2014, 2017 Profactor Gmbh, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Gerhard Ebenhofer, Monika Wenger, Alois Zoitl
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataexport;

import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

class AdapterExporter extends CommonElementExporter {

	@Override
	protected void addType(final Document dom, final FBType fbType){
		Element rootElement = createRootElement(dom, fbType, LibraryElementTags.ADAPTER_TYPE);
		addCompileAbleTypeData(dom, rootElement, fbType);
		
		addInterfaceList(dom, rootElement, fbType.getInterfaceList());
		addService(dom, rootElement, fbType);
	}
	
	@Override
	protected FBType getType(PaletteEntry entry){
		return ((AdapterTypePaletteEntry)entry).getType().getAdapterFBType();
	}
}
