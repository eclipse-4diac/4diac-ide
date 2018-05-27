/********************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2013 - 2017  Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Gerhard Ebenhofer, Monika Wenger, Alois Zoitl, Waldemar Eisenmenger
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport;

import org.eclipse.fordiac.ide.model.Activator;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Identification;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Class CommonElementImporter.
 */
class CommonElementImporter {

	/**
	 * Parses the identification.
	 * 
	 * @param elem the elem
	 * @param node the node
	 * 
	 * @return the identification
	 */
	public static Identification parseIdentification(final LibraryElement elem,
			final Node node) {
		NamedNodeMap map = node.getAttributes();
		Identification ident = LibraryElementFactory.eINSTANCE
				.createIdentification();
		elem.setIdentification(ident);
		Node standard = map.getNamedItem(LibraryElementTags.STANDARD_ATTRIBUTE);
		if (standard != null) {
			ident.setStandard(standard.getNodeValue());
		}
		Node classification = map
				.getNamedItem(LibraryElementTags.CLASSIFICATION_ATTRIBUTE);
		if (classification != null) {
			ident.setClassification(classification.getNodeValue());
		}
		Node applicationDomain = map
				.getNamedItem(LibraryElementTags.APPLICATION_DOMAIN_ATTRIBUTE);
		if (applicationDomain != null) {
			ident.setApplicationDomain(applicationDomain.getNodeValue());
		}
		Node function = map.getNamedItem(LibraryElementTags.FUNCTION_ELEMENT);
		if (function != null) {
			ident.setFunction(function.getNodeValue());
		}
		Node type = map.getNamedItem(LibraryElementTags.TYPE_ATTRIBUTE);
		if (type != null) {
			ident.setType(type.getNodeValue());
		}
		Node description = map
				.getNamedItem(LibraryElementTags.DESCRIPTION_ELEMENT);
		if (description != null) {
			ident.setDescription(description.getNodeValue());
		}

		return ident;
	}

	/**
	 * Parses the version info.
	 * 
	 * @param elem the elem
	 * @param node the node
	 * 
	 * @return the version info
	 * 
	 * @throws TypeImportException the FBT import exception
	 */
	public static VersionInfo parseVersionInfo(final LibraryElement elem,
			final Node node) throws TypeImportException {
		NamedNodeMap map = node.getAttributes();
		VersionInfo versionInfo = LibraryElementFactory.eINSTANCE
				.createVersionInfo();
		elem.getVersionInfo().add(versionInfo);
		Node organization = map
				.getNamedItem(LibraryElementTags.ORGANIZATION_ATTRIBUTE);
		if (organization != null) {
			versionInfo.setOrganization(organization.getNodeValue());
		} 
		
		Node version = map.getNamedItem(LibraryElementTags.VERSION_ATTRIBUTE);
		if (version != null) {
			versionInfo.setVersion(version.getNodeValue());
		} else {
			throw new TypeImportException(
					Messages.CommonElementImporter_ERROR_MissingVersionInfo);
		}
		Node author = map.getNamedItem(LibraryElementTags.AUTHOR_ATTRIBUTE);
		if (author != null) {
			versionInfo.setAuthor(author.getNodeValue());
		} else {
			throw new TypeImportException(
					Messages.CommonElementImporter_ERROR_MissingAuthorInfo);
		}
		Node date = map.getNamedItem(LibraryElementTags.DATE_ATTRIBUTE);
		if (date != null) {
			versionInfo.setDate(date.getNodeValue()); // TODO: check whether
			// it is better to
			// change type to Date
		}
		Node remarks = map.getNamedItem(LibraryElementTags.REMARKS_ATTRIBUTE);
		if (remarks != null) {
			versionInfo.setRemarks(remarks.getNodeValue());
		}else{
			versionInfo.setRemarks(""); //$NON-NLS-1$
		}

		return versionInfo;
	}

	/** search the child list of a node for a node with a given name
	 * 
	 * @param node the root node
	 * @param nodeName  name of the node to be searched for
	 * @return the found node or null if no node with given name could be found
	 */
	public static Node findChildNodeNamed(Node node, String nodeName) {
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node n = childNodes.item(i);
			if (n.getNodeName().equals(nodeName)) {
				return n;
			}
		}
		return null;
	}
	
	/**
	 * Gets the xand y.
	 * 
	 * @param map
	 *            the map
	 * 
	 * @return the xand y
	 * 
	 * @throws TypeImportException
	 *             the FBT import exception
	 */
	public static void getXandY(final NamedNodeMap map, PositionableElement positionableElement)
			throws TypeImportException {
		try {
			Node x = map.getNamedItem(LibraryElementTags.X_ATTRIBUTE);
			if (x != null) {
				String xValueString = x.getNodeValue();
				double xValue = 0.0;
				if ((null != xValueString) && (xValueString.length() != 0)){
					xValue = ImportUtils.convertCoordinate(Double
							.parseDouble(x.getNodeValue()));
				
				}
				else{
					Activator.getDefault().logWarning(Messages.FBTImporter_POSITION_X_WRONG);
				}
				positionableElement.setX((int) xValue);
			}
			Node y = map.getNamedItem(LibraryElementTags.Y_ATTRIBUTE);
			if (y != null) {
				String yValueString = y.getNodeValue();
				double yValue = 0;
				if ((null != yValueString) && (yValueString.length() != 0)){
					yValue = ImportUtils.convertCoordinate(Double
							.parseDouble(y.getNodeValue()));					
				}
				else{
					Activator.getDefault().logWarning(Messages.FBTImporter_POSITION_Y_WRONG);
				}
				positionableElement.setY((int) yValue);
			}
		} catch (NumberFormatException nfe) {
			throw new TypeImportException(
					Messages.FBTImporter_POSITION_EXCEPTION);
		}
	}
	
	
	static void readNameCommentAttributes(INamedElement namedElement, NamedNodeMap attributeMap){
		readNameAttribute(namedElement, attributeMap);
		readCommentAttribute(namedElement, attributeMap);
	}
	
	private static void readNameAttribute(INamedElement namedElement, NamedNodeMap attributeMap){
		Node name = attributeMap.getNamedItem(LibraryElementTags.NAME_ATTRIBUTE);
		if (name != null) {
			namedElement.setName(name.getNodeValue());
		}
	}
	
	private static void readCommentAttribute(INamedElement namedElement, NamedNodeMap attributeMap){
		Node comment = attributeMap.getNamedItem(LibraryElementTags.COMMENT_ATTRIBUTE);
		if (comment != null) {
			namedElement.setComment(comment.getNodeValue());
		}
	}

	static void parseGenericAttributeNode(ConfigurableObject confObject, NamedNodeMap attributeMap) {
		Node name = attributeMap.getNamedItem(LibraryElementTags.NAME_ATTRIBUTE);
		Node type = attributeMap.getNamedItem(LibraryElementTags.TYPE_ATTRIBUTE);
		Node value = attributeMap.getNamedItem(LibraryElementTags.VALUE_ATTRIBUTE);
		Node comment = attributeMap.getNamedItem(LibraryElementTags.COMMENT_ATTRIBUTE);
		if(null != name && null != value){
			confObject.setAttribute(name.getNodeValue(), null == type ? "STRING" : type.getNodeValue(), value.getNodeValue(), comment.getNodeValue()); //$NON-NLS-1$
		}		
	}
	
	static String getAttributeValue(NamedNodeMap attributeMap, String attName) {
		Node value = attributeMap.getNamedItem(attName);
		return (null != value) ? value.getNodeValue() : null;
	}
		
	static boolean isProfileAttribute(NamedNodeMap attributeMap) {
		Node name = attributeMap.getNamedItem(LibraryElementTags.NAME_ATTRIBUTE);
		return (null != name) && LibraryElementTags.DEVICE_PROFILE.equals(name.getNodeValue());
	}
}
