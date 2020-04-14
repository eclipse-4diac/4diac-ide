/********************************************************************************
 * Copyright (c) 2008, 2009, 2013 - 2017  Profactor GmbH, fortiss GmbH
 * 				 2020 Johannes Kepler University Linz
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
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport;

import javax.xml.stream.XMLStreamReader;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

/**
 * Managing class for importing SubApplication files.
 *
 * @author Martijn Rooker (martijn.rooker@profactor.at)
 */
public class SubAppTImporter extends FBTImporter {

	public SubAppTImporter() {
		super();
	}

	public SubAppTImporter(XMLStreamReader reader, final TypeLibrary typeLib) {
		super(reader, typeLib);
	}

	@Override
	public LibraryElement importType(IFile typeFile) throws TypeImportException {
		LibraryElement newType = super.importType(typeFile);
		return (newType instanceof SubAppType) ? newType : null;
	}

	@Override
	protected FBType createType() {
		SubAppType newType = LibraryElementFactory.eINSTANCE.createSubAppType();
		newType.setService(LibraryElementFactory.eINSTANCE.createService());
		return newType;
	}

	@Override
	protected SubAppType getType() {
		return (SubAppType) super.getType();
	}

	@Override
	protected String getStartElementName() {
		return LibraryElementTags.SUBAPPTYPE_ELEMENT;
	}

	@Override
	protected IChildHandler getTypeChildrenHandler() {
		return name -> {
			switch (name) {
			case LibraryElementTags.IDENTIFICATION_ELEMENT:
				parseIdentification(getType());
				break;
			case LibraryElementTags.VERSION_INFO_ELEMENT:
				parseVersionInfo(getType());
				break;
			case LibraryElementTags.COMPILER_INFO_ELEMENT:
				parseCompilerInfo(getType());
				break;
			case LibraryElementTags.SUBAPPINTERFACE_LIST_ELEMENT:
				getType().setInterfaceList(parseInterfaceList(LibraryElementTags.SUBAPPINTERFACE_LIST_ELEMENT));
				break;
			case LibraryElementTags.SERVICE_ELEMENT:
				parseService(getType());
				break;
			case LibraryElementTags.SUBAPPNETWORK_ELEMENT:
				getType()
						.setFBNetwork(new SubAppNetworkImporter(getTypeLib(), getType().getInterfaceList(), getReader())
								.parseFBNetwork(LibraryElementTags.SUBAPPNETWORK_ELEMENT));
				break;
			default:
				return false;
			}
			return true;
		};
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
