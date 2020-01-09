/********************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2013 - 2017  Profactor GmbH, TU Wien ACIN, fortiss GmbH
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
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport;

import java.text.ParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.Activator;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.ReferencedTypeNotFoundException;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.ResourceType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Managing class for importing *.res files
 * 
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */

public final class RESImporter {

	/**
	 * Import res type.
	 * 
	 * @param resFile      the fbt file
	 * @param parseNetwork the parse network
	 * @param palette      the palette
	 * 
	 * @return the resource type
	 */
	public static ResourceType importResType(final IFile resFile, final Palette palette) {

		if (resFile.exists()) {

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(false);
			DocumentBuilder db;

			try {
				// TODO: set local dtd for validating!
				dbf.setAttribute("http://apache.org/xml/features/nonvalidating/load-external-dtd", //$NON-NLS-1$
						Boolean.FALSE);
				db = dbf.newDocumentBuilder();
				Document document = db.parse(resFile.getContents());
				Element rootNode = document.getDocumentElement();
				ResourceType type = LibraryElementFactory.eINSTANCE.createResourceType();
				// parse document and fill type
				return parseResType(type, rootNode, palette);

			} catch (Exception e) {
				Activator.getDefault().logError(e.getMessage(), e);
			}
		}
		return null;

	}

	/**
	 * Parses the res type.
	 * 
	 * @param type         the type
	 * @param rootNode     the root node
	 * @param parseNetwork the parse network
	 * 
	 * @return the resource type
	 * 
	 * @throws TypeImportException             the FBT import exception
	 * @throws ReferencedTypeNotFoundException the referenced type not found
	 *                                         exception
	 * @throws ParseException
	 */
	private static ResourceType parseResType(final ResourceType type, final Node rootNode, final Palette palette)
			throws TypeImportException, ReferencedTypeNotFoundException, ParseException {
		if (rootNode.getNodeName().equals(LibraryElementTags.RESOURCETYPE_ELEMENT)) {
			NamedNodeMap map = rootNode.getAttributes();

			CommonElementImporter.readNameCommentAttributes(type, map);

			NodeList childNodes = rootNode.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node n = childNodes.item(i);
				if (n.getNodeName().equals(LibraryElementTags.IDENTIFICATION_ELEMENT)) {
					type.setIdentification(CommonElementImporter.parseIdentification(type, n));
				}
				if (n.getNodeName().equals(LibraryElementTags.VERSION_INFO_ELEMENT)) {
					type.getVersionInfo().add(CommonElementImporter.parseVersionInfo(type, n));
				}
				if (n.getNodeName().equals(LibraryElementTags.COMPILER_INFO_ELEMENT)) {
					type.setCompilerInfo(CompilableElementImporter.parseCompilerInfo(type, n));
				}
				if (n.getNodeName().equals(LibraryElementTags.VAR_DECLARATION_ELEMENT)) {
					VarDeclaration v = ImportUtils.parseVarDeclaration(n);
					v.setIsInput(true);
					type.getVarDeclaration().add(v);
				}
				if (n.getNodeName().equals(LibraryElementTags.FBTYPENAME_ELEMENT)) {
					// TODO __gebenh import "supported fbtypes"
				}
				if (n.getNodeName().equals(LibraryElementTags.FBNETWORK_ELEMENT)) {
					type.setFBNetwork(new ResDevFBNetworkImporter(palette, type.getVarDeclaration()).parseFBNetwork(n));
				}
			}
			return type;
		}
		throw new ParseException(Messages.FBTImporter_PARSE_FBTYPE_PARSEEXCEPTION, 0);
	}

	private RESImporter() {
		throw new UnsupportedOperationException("RESImporter utility class should not be instantiated!"); //$NON-NLS-1$
	}

}
