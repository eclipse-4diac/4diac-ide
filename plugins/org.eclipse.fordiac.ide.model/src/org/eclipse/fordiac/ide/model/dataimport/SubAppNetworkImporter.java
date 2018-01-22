/*******************************************************************************
 * Copyright (c) 2016 - 2017 fortiss GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class SubAppNetworkImporter extends FBNetworkImporter {

	public SubAppNetworkImporter(Palette palette) {
		super(palette);
	}

	public SubAppNetworkImporter(Palette palette, InterfaceList interfaceList) {
		super(palette, LibraryElementFactory.eINSTANCE.createFBNetwork(), interfaceList);
	}
	
	protected SubAppNetworkImporter(Palette palette, FBNetwork fbNetwork) {
		super(palette, fbNetwork);
	}

	@Override
	protected void parseFBNetworkEntryNode(Node n) throws TypeImportException {
		if (n.getNodeName().equals(LibraryElementTags.SUBAPP_ELEMENT)) {
			parseSubApp(n);
		}else{
			super.parseFBNetworkEntryNode(n);
		}
	}

	private void parseSubApp(Node node) throws TypeImportException {
		SubApp subApp = LibraryElementFactory.eINSTANCE.createSubApp();
		
		NamedNodeMap map = node.getAttributes();
		Node name = map.getNamedItem(LibraryElementTags.NAME_ATTRIBUTE);
		if (name != null) {
			subApp.setName(name.getNodeValue());
		} else {
			throw new TypeImportException(
					Messages.SubAppTImporter_ERROR_SubAppName);
		}
		Node type = map.getNamedItem(LibraryElementTags.TYPE_ATTRIBUTE);
		if (type != null) {
			configureSubAppInterface(subApp, node, type.getNodeValue());					
		} else {
			parseUntypedSubapp(subApp, node.getChildNodes()); 
		}
		Node comment = map.getNamedItem(LibraryElementTags.COMMENT_ATTRIBUTE);
		if (comment != null) {
			subApp.setComment(comment.getNodeValue());
		}
		
		CommonElementImporter.getXandY(map, subApp);
		
		configureParameters(subApp.getInterface(), node.getChildNodes());
		
		fbNetwork.getNetworkElements().add(subApp);
		fbNetworkElementMap.put(subApp.getName(), subApp);
	}
	
	private void configureSubAppInterface(SubApp subApp, Node node, String typeName) throws TypeImportException {
		PaletteEntry entry = palette.getTypeEntry(typeName);
		
		if(entry instanceof SubApplicationTypePaletteEntry){
			subApp.setPaletteEntry(entry);
			SubApplicationTypePaletteEntry subEntry = (SubApplicationTypePaletteEntry)entry;			
			subApp.setInterface(EcoreUtil.copy(subEntry.getSubApplicationType().getInterfaceList()));
			configureParameters(subApp.getInterface(), node.getChildNodes());
		} 		
	}

	private void parseUntypedSubapp(SubApp subApp, NodeList subAppChildNodes) throws TypeImportException {
		for (int i = 0; i < subAppChildNodes.getLength(); i++) {
			Node n = subAppChildNodes.item(i);
			switch (n.getNodeName()) {			
			case LibraryElementTags.SUBAPPINTERFACE_LIST_ELEMENT:
				SubAppTImporter interfaceImporter = new SubAppTImporter();				
				interfaceImporter.setPalette(palette);
				subApp.setInterface(interfaceImporter.parseInterfaceList(n));				
				break;
			case LibraryElementTags.SUBAPPNETWORK_ELEMENT:
				subApp.setSubAppNetwork(new SubAppNetworkImporter(palette, subApp.getInterface()).parseFBNetwork(n));				
				break;
			default:
				//TODO consider if more elements of a subapp should be parseable				
				break;
			}
		}
	}


}
