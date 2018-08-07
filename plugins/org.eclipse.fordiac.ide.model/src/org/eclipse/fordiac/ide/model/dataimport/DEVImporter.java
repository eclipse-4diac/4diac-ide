/********************************************************************************
 * Copyright (c) 2008, 2009, 2011 - 2017  Profactor GmbH, TU Wien ACIN, fortiss GmbH
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

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.Activator;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.data.BaseType1;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.ReferencedTypeNotFoundException;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.DeviceType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Managing class for importing *.dev files
 */

public class DEVImporter {

	/**
	 * Import dev type.
	 * 
	 * @param devFile the fbt file
	 * @param parseNetwork the parse network
	 * @param palette the palette
	 * @param entry the entry
	 * 
	 * @return the device type
	 */
	public static DeviceType importDEVType(final IFile devFile, final Palette palette) {
		if (devFile.exists()) {

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(false);
			DocumentBuilder db;

			try {
				// TODO: set local dtd for validating!
				dbf.setAttribute("http://apache.org/xml/features/nonvalidating/load-external-dtd", //$NON-NLS-1$
						Boolean.FALSE);
				db = dbf.newDocumentBuilder();
				Document document = db.parse(devFile.getContents());
				Element rootNode = document.getDocumentElement();
				DeviceType type = LibraryElementFactory.eINSTANCE.createDeviceType();
				// parse document and fill type
				return parseDEVType(type, rootNode, palette);

			} catch (Exception e) {
				Activator.getDefault().logError(e.getMessage(), e);
			}
		}
		return null;

	}

	/**
	 * Parses the dev type.
	 * 
	 * @param type
	 *          the type
	 * @param rootNode
	 *          the root node
	 * @param parseNetwork
	 *          the parse network
	 * 
	 * @return the device type
	 * 
	 * @throws TypeImportException
	 *           the FBT import exception
	 * @throws ReferencedTypeNotFoundException
	 *           the referenced type not found exception
	 * @throws ParseException 
	 */
	private static DeviceType parseDEVType(final DeviceType type,
			final Node rootNode, final Palette palette) throws TypeImportException,
			ReferencedTypeNotFoundException, ParseException {
		if (rootNode.getNodeName().equals(LibraryElementTags.DEVICETYPE_ELEMENT)) {
			NamedNodeMap map = rootNode.getAttributes();
			Node name = map.getNamedItem(LibraryElementTags.NAME_ATTRIBUTE);
			if (name != null) {
				type.setName(name.getNodeValue());
			}
			Node comment = map.getNamedItem(LibraryElementTags.COMMENT_ATTRIBUTE);
			if (comment != null) {
				type.setComment(comment.getNodeValue());
			}
			NodeList childNodes = rootNode.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node n = childNodes.item(i);
				switch(n.getNodeName()){
					case LibraryElementTags.IDENTIFICATION_ELEMENT:
						type.setIdentification(CommonElementImporter.parseIdentification(type, n));
						break;
					case LibraryElementTags.VERSION_INFO_ELEMENT:
						type.getVersionInfo().add(CommonElementImporter.parseVersionInfo(type, n));
						break;
					case LibraryElementTags.COMPILER_INFO_ELEMENT:
						type.setCompilerInfo(CompilableElementImporter.parseCompilerInfo(type, n));
						break;
					case LibraryElementTags.VAR_DECLARATION_ELEMENT:
						VarDeclaration v = ImportUtils.parseVarDeclaration(n);
						v.setIsInput(true);
						type.getVarDeclaration().add(v);
						break;
					case LibraryElementTags.RESOURCETYPE_NAME_ELEMENT:
						// TODO __gebenh import "supported Resourcetypes"
						break;
					case LibraryElementTags.RESOURCE_ELEMENT:
						try {
							type.getResource().add(parseResource(n, palette));
						} catch (TypeImportException e) {
							Activator.getDefault().logError(e.getMessage(), e);
						}
						break;
					case LibraryElementTags.FBNETWORK_ELEMENT:
						type.setFBNetwork(new ResDevFBNetworkImporter(palette, type.getVarDeclaration()).parseFBNetwork(n));
						break;
					case LibraryElementTags.ATTRIBUTE_ELEMENT:
						parseDeviceTypeAttribute(type, n);
						break;
					case LibraryElementTags.ATTRIBUTE_DECLARATION_ELEMENT:
						parseDeviceTypeAttributeDeclaration(type, n);
						break;
					default:
						break;
				}
			}
			return type;
		} 
		throw new ParseException(Messages.FBTImporter_PARSE_FBTYPE_PARSEEXCEPTION, 0);
	}

	/**
	 * Parses the resource.
	 * 
	 * @param node
	 *          the node
	 * 
	 * @return the resource
	 * 
	 * @throws DevTypeImportException
	 *           the dev type import exception
	 */
	private static Resource parseResource(final Node node, final Palette palette) throws TypeImportException {
		Resource res = LibraryElementFactory.eINSTANCE.createResource();
		NodeList childNodes = node.getChildNodes();
		NamedNodeMap map = node.getAttributes();
		Node name = map.getNamedItem(LibraryElementTags.NAME_ATTRIBUTE);
		if (name != null) {
			res.setName(name.getNodeValue());
		} else {
			throw new TypeImportException(
					Messages.DEVImporter_ERROR_ResourceNameHasToBeSet);
		}
		Node type = map.getNamedItem(LibraryElementTags.TYPE_ATTRIBUTE);
		if (type != null) {
			// FIX - ResTypeLibrary was used by error
			PaletteEntry entry = null;
			if(type.getNodeValue().contains("/")){ //$NON-NLS-1$
				entry = palette.getTypeEntryForPath(type.getNodeValue(), TypeLibraryTags.RESOURCE_TYPE_FILE_ENDING_WITH_DOT);				
			}
			else{
				entry = palette.getTypeEntry(type.getNodeName());
			}
			res.setPaletteEntry(entry);
			
			
		} else {
			throw new TypeImportException(
					Messages.DEVImporter_ERROR_ResourceTypeHasToBeSet);
		}
		Node comment = map.getNamedItem(LibraryElementTags.COMMENT_ATTRIBUTE);
		if (comment != null) {
			res.setComment(comment.getNodeValue());
		}
		Node x = map.getNamedItem(LibraryElementTags.X_ATTRIBUTE);
		if (x != null) {
			res.setX(x.getNodeValue());
		}
		Node y = map.getNamedItem(LibraryElementTags.Y_ATTRIBUTE);
		if (y != null) {
			res.setY(y.getNodeValue());
		}

		for (int i = 0; i < childNodes.getLength(); i++) {
			Node n = childNodes.item(i);
			if (n.getNodeName().equals(LibraryElementTags.PARAMETER_ELEMENT)) {
				try {
					res.getVarDeclarations().add(ImportUtils.parseParameter(n));
				} catch (TypeImportException e) {
					Activator.getDefault().logError(e.getMessage(), e);
				}
			}
			if (n.getNodeName().equals(LibraryElementTags.FBNETWORK_ELEMENT)) {
				res.setFBNetwork(new ResDevFBNetworkImporter(palette, res.getVarDeclarations()).parseFBNetwork(n));
			}
		}

		return res;
	}

	/**
	 * Gets the referenced types.
	 * 
	 * @param file the file
	 * 
	 * @return the referenced types
	 */
	public static List<String> getReferencedTypes(final File file) {
		ArrayList<String> references = new ArrayList<String>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;

		dbf.setAttribute(
				"http://apache.org/xml/features/nonvalidating/load-external-dtd", //$NON-NLS-1$
				Boolean.FALSE);

		try {
			db = dbf.newDocumentBuilder();
			Document document;
			document = db.parse(file);
			// parse document for "FBNetwork" tag
			Node rootNode = document.getDocumentElement();
			NodeList childNodes = rootNode.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node n = childNodes.item(i);
				if (n.getNodeName().equals(LibraryElementTags.RESOURCE_ELEMENT)) {
					// add nodes to NodeList
					String type = ""; //$NON-NLS-1$
					type = n.getAttributes().getNamedItem(
							LibraryElementTags.TYPE_ATTRIBUTE).getNodeValue();
					references.add(type);
				}

			}
		} catch (Exception e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}

		return references;

	}
	
	private static void parseDeviceTypeAttributeDeclaration(DeviceType device, Node node) {
		NamedNodeMap attributeMap = node.getAttributes();
		AttributeDeclaration attributeDeclaration = LibraryElementFactory.eINSTANCE.createAttributeDeclaration();
		attributeDeclaration.setName(attributeMap.getNamedItem(LibraryElementTags.NAME_ATTRIBUTE).getNodeValue());
		attributeDeclaration.setComment(attributeMap.getNamedItem(LibraryElementTags.COMMENT_ATTRIBUTE).getNodeValue());	
		attributeDeclaration.setInitialValue(attributeMap.getNamedItem(LibraryElementTags.INITIALVALUE_ATTRIBUTE).getNodeValue());
		attributeDeclaration.setType(BaseType1.getByName(attributeMap.getNamedItem(LibraryElementTags.TYPE_ATTRIBUTE).getNodeValue()));
		device.getAttributeDeclarations().add(attributeDeclaration);
	}
	
	private static void parseDeviceTypeAttribute(DeviceType device, Node node) {
		NamedNodeMap attributeMap = node.getAttributes();
		if(CommonElementImporter.isProfileAttribute(attributeMap)){
			parseProfile(device, attributeMap);	
		}
	}
	
	private static void parseProfile(DeviceType device, NamedNodeMap attributeMap) {
		Node value = attributeMap.getNamedItem(LibraryElementTags.VALUE_ATTRIBUTE);
		if(null != value){
			device.setProfile(value.getNodeValue());
		}
	}

}
