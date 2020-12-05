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

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;

/**
 * Managing class for importing SubApplication files.
 *
 * @author Martijn Rooker (martijn.rooker@profactor.at)
 */
public class SubAppTImporter extends FBTImporter {

	public SubAppTImporter(final IFile typeFile) {
		super(typeFile);
	}

	public SubAppTImporter(final CommonElementImporter importer) {
		super(importer);
	}

	@Override
	protected FBType createRootModelElement() {
		final SubAppType newType = LibraryElementFactory.eINSTANCE.createSubAppType();
		newType.setService(LibraryElementFactory.eINSTANCE.createService());
		return newType;
	}

	@Override
	public SubAppType getElement() {
		return (SubAppType) super.getElement();
	}

	@Override
	protected String getStartElementName() {
		return LibraryElementTags.SUBAPPTYPE_ELEMENT;
	}

	@Override
	protected IChildHandler getBaseChildrenHandler() {
		return name -> {
			switch (name) {
			case LibraryElementTags.IDENTIFICATION_ELEMENT:
				parseIdentification(getElement());
				break;
			case LibraryElementTags.VERSION_INFO_ELEMENT:
				parseVersionInfo(getElement());
				break;
			case LibraryElementTags.COMPILER_INFO_ELEMENT:
				parseCompilerInfo(getElement());
				break;
			case LibraryElementTags.SUBAPPINTERFACE_LIST_ELEMENT:
				getElement().setInterfaceList(parseInterfaceList(LibraryElementTags.SUBAPPINTERFACE_LIST_ELEMENT));
				break;
			case LibraryElementTags.SERVICE_ELEMENT:
				parseService(getElement());
				break;
			case LibraryElementTags.SUBAPPNETWORK_ELEMENT:
				final SubAppNetworkImporter subAppImporter = new SubAppNetworkImporter(this,
						getElement().getInterfaceList());
				getElement().setFBNetwork(subAppImporter.getFbNetwork());
				subAppImporter.parseFBNetwork(LibraryElementTags.SUBAPPNETWORK_ELEMENT);
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
