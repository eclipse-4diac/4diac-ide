/********************************************************************************
 * Copyright (c) 2008, 2009, 2011 - 2017  Profactor GmbH, TU Wien ACIN, fortiss GmbH
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLStreamException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.model.Activator;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.data.BaseType1;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.DeviceType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Managing class for importing *.dev files
 */

public final class DEVImporter extends TypeImporter {

	private final Palette palette;

	public DEVImporter(final IFile devFile, final Palette palette) throws XMLStreamException, CoreException {
		super(devFile);
		this.palette = palette;
	}

	@Override
	protected DeviceType getType() {
		return (DeviceType) super.getType();
	}

	@Override
	protected LibraryElement createType() {
		return LibraryElementFactory.eINSTANCE.createDeviceType();
	}

	@Override
	protected String getStartElementName() {
		return LibraryElementTags.DEVICETYPE_ELEMENT;
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
			case LibraryElementTags.VAR_DECLARATION_ELEMENT:
				VarDeclaration v = parseVarDeclaration();
				v.setIsInput(true);
				getType().getVarDeclaration().add(v);
				break;
			case LibraryElementTags.RESOURCETYPE_NAME_ELEMENT:
				// TODO __gebenh import "supported Resourcetypes"
				break;
			case LibraryElementTags.RESOURCE_ELEMENT:
				getType().getResource().add(parseResource());
				break;
			case LibraryElementTags.FBNETWORK_ELEMENT:
				getType().setFBNetwork(new ResDevFBNetworkImporter(palette, getType().getVarDeclaration(), getReader())
						.parseFBNetwork(LibraryElementTags.FBNETWORK_ELEMENT));
				break;
			case LibraryElementTags.ATTRIBUTE_ELEMENT:
				parseDeviceTypeAttribute();
				break;
			case LibraryElementTags.ATTRIBUTE_DECLARATION_ELEMENT:
				parseDeviceTypeAttributeDeclaration();
				break;
			default:
				return false;
			}
			return true;
		};
	}

	/**
	 * Parses the resource.
	 *
	 * @return the resource
	 * @throws XMLStreamException
	 *
	 * @throws DevTypeImportException the dev type import exception
	 */
	private Resource parseResource() throws TypeImportException, XMLStreamException {
		Resource res = LibraryElementFactory.eINSTANCE.createResource();

		readNameCommentAttributes(res);

		String resType = getAttributeValue(LibraryElementTags.TYPE_ATTRIBUTE);
		if (null != resType) {
			// FIX - ResTypeLibrary was used by error
			PaletteEntry entry = null;
			if (resType.contains("/")) { //$NON-NLS-1$
				entry = palette.getTypeEntryForPath(resType, TypeLibraryTags.RESOURCE_TYPE_FILE_ENDING_WITH_DOT);
			} else {
				entry = palette.getTypeEntry(resType);
			}
			res.setPaletteEntry(entry);
		} else {
			throw new TypeImportException(Messages.DEVImporter_ERROR_ResourceTypeHasToBeSet);
		}

		String x = getAttributeValue(LibraryElementTags.X_ATTRIBUTE);
		if (null != x) {
			res.setX(x);
		}
		String y = getAttributeValue(LibraryElementTags.Y_ATTRIBUTE);
		if (null != y) {
			res.setY(y);
		}

		processChildren(LibraryElementTags.DEVICE_ELEMENT, name -> {
			switch (name) {
			case LibraryElementTags.PARAMETER_ELEMENT:
				res.getVarDeclarations().add(parseParameter());
				break;
			case LibraryElementTags.FBNETWORK_ELEMENT:
				res.setFBNetwork(new ResDevFBNetworkImporter(palette, res.getVarDeclarations(), getReader())
						.parseFBNetwork(LibraryElementTags.FBNETWORK_ELEMENT));
				break;
			default:
				return false;
			}
			return true;
		});

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
		List<String> references = new ArrayList<>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;

		dbf.setAttribute("http://apache.org/xml/features/nonvalidating/load-external-dtd", //$NON-NLS-1$
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
					type = n.getAttributes().getNamedItem(LibraryElementTags.TYPE_ATTRIBUTE).getNodeValue();
					references.add(type);
				}

			}
		} catch (Exception e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}

		return references;

	}

	private void parseDeviceTypeAttributeDeclaration() {
		AttributeDeclaration attributeDeclaration = LibraryElementFactory.eINSTANCE.createAttributeDeclaration();
		attributeDeclaration.setName(getAttributeValue(LibraryElementTags.NAME_ATTRIBUTE));
		attributeDeclaration.setComment(getAttributeValue(LibraryElementTags.COMMENT_ATTRIBUTE));
		attributeDeclaration.setInitialValue(getAttributeValue(LibraryElementTags.INITIALVALUE_ATTRIBUTE));
		attributeDeclaration.setType(BaseType1.getByName(getAttributeValue(LibraryElementTags.TYPE_ATTRIBUTE)));
		getType().getAttributeDeclarations().add(attributeDeclaration);
	}

	private void parseDeviceTypeAttribute() {
		if (isProfileAttribute()) {
			parseProfile();
		}
	}

	private void parseProfile() {
		String value = getAttributeValue(LibraryElementTags.VALUE_ATTRIBUTE);
		if (null != value) {
			getType().setProfile(value);
		}
	}

}
