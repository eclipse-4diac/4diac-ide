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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.libraryElement.DeviceType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Managing class for importing *.dev files
 */

public class DEVImporter extends TypeImporter {

	public DEVImporter(final IFile typeFile) {
		super(typeFile);
	}

	public DEVImporter(final InputStream inputStream, final TypeLibrary typeLibrary) {
		super(inputStream, typeLibrary);
	}

	@Override
	public DeviceType getElement() {
		return (DeviceType) super.getElement();
	}

	@Override
	protected LibraryElement createRootModelElement() {
		return LibraryElementFactory.eINSTANCE.createDeviceType();
	}

	@Override
	protected String getStartElementName() {
		return LibraryElementTags.DEVICETYPE_ELEMENT;
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
				getElement().setCompilerInfo(parseCompilerInfo());
				break;
			case LibraryElementTags.VAR_DECLARATION_ELEMENT:
				final VarDeclaration v = parseVarDeclaration();
				v.setIsInput(true);
				getElement().getVarDeclaration().add(v);
				break;
			case LibraryElementTags.RESOURCETYPE_NAME_ELEMENT:
				// TODO __gebenh import "supported Resourcetypes"
				break;
			case LibraryElementTags.RESOURCE_ELEMENT:
				getElement().getResource().add(parseResource());
				break;
			case LibraryElementTags.FBNETWORK_ELEMENT:
				final ResDevFBNetworkImporter resNetworkImporter = new ResDevFBNetworkImporter(this,
						getElement().getVarDeclaration());
				getElement().setFBNetwork(resNetworkImporter.getFbNetwork());
				resNetworkImporter.parseFBNetwork(LibraryElementTags.FBNETWORK_ELEMENT);
				break;
			case LibraryElementTags.ATTRIBUTE_ELEMENT:
				parseDeviceTypeAttribute();
				break;
			default:
				return false;
			}
			return true;
		};
	}

	/**
	 * Gets the referenced types.
	 *
	 * @param file the file
	 *
	 * @return the referenced types
	 */
	public static List<String> getReferencedTypes(final File file) {
		final List<String> references = new ArrayList<>();
		final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;

		dbf.setAttribute("http://apache.org/xml/features/nonvalidating/load-external-dtd", //$NON-NLS-1$
				Boolean.FALSE);

		try {
			db = dbf.newDocumentBuilder();
			Document document;
			document = db.parse(file);
			// parse document for "FBNetwork" tag
			final Node rootNode = document.getDocumentElement();
			final NodeList childNodes = rootNode.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				final Node n = childNodes.item(i);
				if (n.getNodeName().equals(LibraryElementTags.RESOURCE_ELEMENT)) {
					// add nodes to NodeList
					String type = ""; //$NON-NLS-1$
					type = n.getAttributes().getNamedItem(LibraryElementTags.TYPE_ATTRIBUTE).getNodeValue();
					references.add(type);
				}

			}
		} catch (final Exception e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}

		return references;

	}

	private void parseDeviceTypeAttribute() {
		if (isProfileAttribute()) {
			parseProfile();
		}
	}

	private void parseProfile() {
		final String value = getAttributeValue(LibraryElementTags.VALUE_ATTRIBUTE);
		if (null != value) {
			getElement().setProfile(value);
		}
	}

}
