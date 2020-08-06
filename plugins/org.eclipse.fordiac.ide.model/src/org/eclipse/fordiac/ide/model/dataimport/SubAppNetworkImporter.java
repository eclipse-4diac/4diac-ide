/*******************************************************************************
 * Copyright (c) 2016 - 2017 fortiss GmbH
 * 				 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *  			 - Changed XML parsing to Staxx cursor interface for improved
 *  			   parsing performance
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport;

import javax.xml.stream.XMLStreamException;

import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

class SubAppNetworkImporter extends FBNetworkImporter {

	public SubAppNetworkImporter(CommonElementImporter importer) {
		super(importer);
	}

	public SubAppNetworkImporter(CommonElementImporter importer, InterfaceList interfaceList) {
		super(importer, LibraryElementFactory.eINSTANCE.createFBNetwork(), interfaceList);
	}

	protected SubAppNetworkImporter(CommonElementImporter importer, FBNetwork fbNetwork) {
		super(importer, fbNetwork);
	}

	@Override
	public FBNetwork parseFBNetwork(String networkNodeName) throws TypeImportException, XMLStreamException {
		processChildren(networkNodeName, name -> {
			switch (name) {
			case LibraryElementTags.FB_ELEMENT:
				parseFB();
				break;
			case LibraryElementTags.SUBAPP_ELEMENT:
				parseSubApp();
				break;
			case LibraryElementTags.EVENT_CONNECTIONS_ELEMENT:
				parseConnectionList(LibraryElementPackage.eINSTANCE.getEventConnection(),
						getFbNetwork().getEventConnections(), LibraryElementTags.EVENT_CONNECTIONS_ELEMENT);
				break;
			case LibraryElementTags.DATA_CONNECTIONS_ELEMENT:
				parseConnectionList(LibraryElementPackage.eINSTANCE.getDataConnection(),
						getFbNetwork().getDataConnections(), LibraryElementTags.DATA_CONNECTIONS_ELEMENT);
				break;
			case LibraryElementTags.ADAPTERCONNECTIONS_ELEMENT:
				parseConnectionList(LibraryElementPackage.eINSTANCE.getAdapterConnection(),
						getFbNetwork().getAdapterConnections(), LibraryElementTags.ADAPTERCONNECTIONS_ELEMENT);
				break;
			default:
				return false;
			}
			return true;
		});
		return getFbNetwork();
	}

	private void parseSubApp() throws TypeImportException, XMLStreamException {
		SubApp subApp = LibraryElementFactory.eINSTANCE.createSubApp();

		readNameCommentAttributes(subApp);
		getXandY(subApp);

		String type = getAttributeValue(LibraryElementTags.TYPE_ATTRIBUTE);
		if (null != type) {
			configureSubAppInterface(subApp, type);
		} else {
			parseUntypedSubapp(subApp);
		}
		getFbNetwork().getNetworkElements().add(subApp);
		fbNetworkElementMap.put(subApp.getName(), subApp);
	}

	private void configureSubAppInterface(SubApp subApp, String typeName)
			throws TypeImportException, XMLStreamException {
		SubApplicationTypePaletteEntry subEntry = getPalette().getSubAppTypeEntry(typeName);

		if (null != subEntry) {
			subApp.setPaletteEntry(subEntry);
			subApp.setInterface(subEntry.getSubApplicationType().getInterfaceList().copy());
		} else {
			// TODO add error marker
			// put an empty interface list so that the system can load
			subApp.setInterface(LibraryElementFactory.eINSTANCE.createInterfaceList());
		}
		parseFBChildren(subApp, LibraryElementTags.SUBAPP_ELEMENT);
	}

	private void parseUntypedSubapp(SubApp subApp) throws TypeImportException, XMLStreamException {
		processChildren(LibraryElementTags.SUBAPP_ELEMENT, name -> {
			switch (name) {
			case LibraryElementTags.SUBAPPINTERFACE_LIST_ELEMENT:
				SubAppTImporter interfaceImporter = new SubAppTImporter(this);
				subApp.setInterface(
						interfaceImporter.parseInterfaceList(LibraryElementTags.SUBAPPINTERFACE_LIST_ELEMENT));
				return true;
			case LibraryElementTags.SUBAPPNETWORK_ELEMENT:
				subApp.setSubAppNetwork(new SubAppNetworkImporter(this, subApp.getInterface())
						.parseFBNetwork(LibraryElementTags.SUBAPPNETWORK_ELEMENT));
				return true;
			case LibraryElementTags.PARAMETER_ELEMENT:
				VarDeclaration parameter = parseParameter();
				VarDeclaration vInput = getVarNamed(subApp.getInterface(), parameter.getName(), true);
				if (null != vInput) {
					vInput.setValue(parameter.getValue());
				}
				return true;
			case LibraryElementTags.ATTRIBUTE_ELEMENT:
				parseGenericAttributeNode(subApp);
				proceedToEndElementNamed(LibraryElementTags.ATTRIBUTE_ELEMENT);
				return true;
			default:
				return false;
			}
		});
	}

}
