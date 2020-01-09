/********************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2013, 2014  Profactor GmbH, TU Wien ACIN, fortiss GmbH
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
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SegmentType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Managing class for importing *.seg files
 * 
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */

public final class SEGImporter {

	/**
	 * Import seg type.
	 * 
	 * @param iFile the segmentType file
	 * 
	 * @return the segment type
	 */
	public static SegmentType importSEGType(final IFile iFile) {
		if (iFile.exists()) {

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(false);
			DocumentBuilder db;

			try {
				// TODO: set local dtd for validating!
				dbf.setAttribute("http://apache.org/xml/features/nonvalidating/load-external-dtd", //$NON-NLS-1$
						Boolean.FALSE);
				db = dbf.newDocumentBuilder();
				Document document = db.parse(iFile.getContents());
				Element rootNode = document.getDocumentElement();
				SegmentType type = LibraryElementFactory.eINSTANCE.createSegmentType();
				// parse document and fill type
				return parseSEGType(type, rootNode);

			} catch (Exception e) {
				Activator.getDefault().logError(e.getMessage(), e);
			}
		}
		return null;

	}

	/**
	 * Parses the seg type.
	 * 
	 * @param type     the type
	 * @param rootNode the root node
	 * 
	 * @return the segment type
	 * 
	 * @throws TypeImportException the FBT import exception
	 * @throws ParseException
	 */
	private static SegmentType parseSEGType(final SegmentType type, final Node rootNode)
			throws TypeImportException, ParseException {
		if (rootNode.getNodeName().equals(LibraryElementTags.SEGMENT_TYPE_ELEMENT)) {
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

			}
			return type;
		}
		throw new ParseException(Messages.FBTImporter_PARSE_FBTYPE_PARSEEXCEPTION, 0);
	}

	private SEGImporter() {
		throw new UnsupportedOperationException("SEGImporter utility class should not be instantiated!"); //$NON-NLS-1$
	}

}
