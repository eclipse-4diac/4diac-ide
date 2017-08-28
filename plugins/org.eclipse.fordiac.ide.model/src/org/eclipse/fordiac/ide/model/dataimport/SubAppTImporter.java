/********************************************************************************
 * Copyright (c) 2008, 2009, 2013 - 2017  Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport;

import java.text.ParseException;
import java.util.HashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.ReferencedTypeNotFoundException;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Managing class for importing SubApplication files.
 * 
 * @author Martijn Rooker (martijn.rooker@profactor.at)
 */
public class SubAppTImporter extends FBTImporter{

	/**This allows that the typeimporter can also be utilized to parse untyped subapp interfaces
	 * 
	 * @param palette
	 */
	public void setPalette(Palette palette){
		this.palette = palette;
	}

	/**
	 * Import sub app type.
	 * 
	 * @param subapptFile the subappt file
	 * @param palette the palette
	 * 
	 * @return the sub app type
	 * 
	 * @throws TypeImportException the FBT import exception
	 * @throws ReferencedTypeNotFoundException the referenced type not found exception
	 */
	public SubAppType importSubAppType(final IFile subapptFile,
			final Palette palette) throws ReferencedTypeNotFoundException {
		FBType newType = importType(subapptFile, palette);
		if((null != newType) && (type instanceof SubAppType)){
			return (SubAppType)type;
		}		
		return null;
	}
	
	@Override
	protected FBType createType(){
		return LibraryElementFactory.eINSTANCE.createSubAppType();
	}

	
	
	@Override
	protected FBType parseType(Node rootNode) throws TypeImportException,
			ReferencedTypeNotFoundException, ParseException {
		if (rootNode.getNodeName().equals(LibraryElementTags.SUBAPPTYPE_ELEMENT)) {
			CommonElementImporter.readNameCommentAttributes(type, rootNode.getAttributes());
			
			NodeList childNodes = rootNode.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node n = childNodes.item(i);
				if (n.getNodeName().equals(IDENTIFICATION_ELEMENT)) {
					type.setIdentification(CommonElementImporter
							.parseIdentification(type, n));
				}
				if (n.getNodeName().equals(VERSION_INFO_ELEMENT)) {
					type.getVersionInfo().add(
							CommonElementImporter.parseVersionInfo(type, n));
				}
				if (n.getNodeName().equals(COMPILER_INFO_ELEMENT)) {
					type.setCompilerInfo(CompilableElementImporter
							.parseCompilerInfo(type, n));
					// parseCompilerInfo(type, n);
				}
				if (n.getNodeName().equals(SUBAPPINTERFACE_LIST_ELEMENT)) {
					type.setInterfaceList(parseInterfaceList(n));
				}
				
				if (n.getNodeName().equals(SERVICE_ELEMENT)) {
					parseService(type, n);
				}
				
				if (n.getNodeName().equals(LibraryElementTags.SUBAPPNETWORK_ELEMENT)) {
					((SubAppType)type).setFBNetwork(new SubAppNetworkImporter(palette, type.getInterfaceList()).parseFBNetwork(n));
				}
			
			}
			
			return type;
		}
		throw new ParseException(Messages.SubAppTImporter_ERROR, 0);
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
	public void parseWithConstructs(NodeList childNodes,
			HashMap<String, Event> eventInputs,
			HashMap<String, Event> eventOutputs,
			HashMap<String, VarDeclaration> variables) {
		//supapps may not have a with construct. Therefore we are doing nothing here
	}
			
}
