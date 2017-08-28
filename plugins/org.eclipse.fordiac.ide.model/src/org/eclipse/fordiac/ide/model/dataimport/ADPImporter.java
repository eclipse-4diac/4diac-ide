/********************************************************************************
 * Copyright (c) 2010, 2011, 2013, 2014  Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Gerhard Ebenhofer, Monika Wenger, Alois Zoitl
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.Activator;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Managing class for importing *.atp files
 * 
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */

public class ADPImporter {
	/** The variables. */
	public static HashMap<String, VarDeclaration> variables = new HashMap<String, VarDeclaration>();
	public static HashMap<String, VarDeclaration> adapterVariables = new HashMap<String, VarDeclaration>();

	/** The events. */
	public static HashMap<String, Event> events = new HashMap<String, Event>();
	public static HashMap<String, Event> adapterEvents = new HashMap<String, Event>();

	/**
	 * Import Adapter types.
	 * 
	 * @param iFile
	 *            the adaptertype file
	 * 
	 * @return the segment type
	 */
	public static AdapterType importAdapterType(final IFile iFile) {
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
				AdapterType type = LibraryElementFactory.eINSTANCE
						.createAdapterType();
				// parse document and fill type
				return parseAdapterType(type, rootNode);

			} catch (Exception e) {
				Activator.getDefault().logError(e.getMessage(), e);
			}
		}
		return null;

	}

	/**
	 * Parses the seg type.
	 * 
	 * @param type
	 *            the type
	 * @param rootNode
	 *            the root node
	 * 
	 * @return the segment type
	 * 
	 * @throws TypeImportException
	 *             the FBT import exception
	 * @throws ParseException 
	 */
	private static AdapterType parseAdapterType(final AdapterType type,
			final Node rootNode) throws TypeImportException, ParseException {
		variables.clear();
		adapterVariables.clear();
		events.clear();
		adapterEvents.clear();

		AdapterFBType adapterFBType = LibraryElementFactory.eINSTANCE
				.createAdapterFBType();
		type.setAdapterFBType(adapterFBType);
		adapterFBType.setAdapterType(type);
		if (rootNode.getNodeName().equals(LibraryElementTags.ADAPTER_TYPE)) {
			NamedNodeMap map = rootNode.getAttributes();
			Node name = map.getNamedItem(LibraryElementTags.NAME_ATTRIBUTE);
			if (name != null) {
				adapterFBType.setName(name.getNodeValue());
			}
			Node comment = map
					.getNamedItem(LibraryElementTags.COMMENT_ATTRIBUTE);
			if (comment != null) {
				adapterFBType.setComment(type.getComment());
			}
			NodeList childNodes = rootNode.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node n = childNodes.item(i);
				if (n.getNodeName().equals(
						LibraryElementTags.IDENTIFICATION_ELEMENT)) {
					adapterFBType.setIdentification(CommonElementImporter
							.parseIdentification(adapterFBType, n));
				}
				if (n.getNodeName().equals(
						LibraryElementTags.VERSION_INFO_ELEMENT)) {
					adapterFBType.getVersionInfo().add(
							CommonElementImporter.parseVersionInfo(adapterFBType, n));
				}
				if (n.getNodeName().equals(
						LibraryElementTags.COMPILER_INFO_ELEMENT)) {
					// TODO compiler info for Adapter
					// type.setCompilerInfo(CompilableElementImporter
					// .parseCompilerInfo(type, n));
				}
				if (n.getNodeName().equals(
						LibraryElementTags.INTERFACE_LIST_ELEMENT)) {
					InterfaceList interfaceList_socket = LibraryElementFactory.eINSTANCE
							.createInterfaceList();
					adapterFBType.setInterfaceList(interfaceList_socket);
					parseInterfaceList(interfaceList_socket, n, adapterEvents,
							adapterVariables, false);

				}
				
				if (n.getNodeName().equals(LibraryElementTags.SERVICE_ELEMENT)) {
					// type = convertToServiceInterfaceType(type); // FIX:
					// gebenh: every
					// fbtype can have a service
					
					Service service = LibraryElementFactory.eINSTANCE.createService();
					adapterFBType.setService(service);
					new FBTImporter().parseService(adapterFBType, n);
				}
			}

			return type;
		}
		throw new ParseException("Parse Adaptertype Exception", 0);
	}

	/**
	 * This method parses EventOutputs of FBTypes.
	 * 
	 * @param fBtype
	 *            - the FBType containing the EventOutputs being parsed
	 * @param node
	 *            - the node in the DTD of the EventOutputs being parsed
	 * @throws TypeImportException
	 * 
	 * @throws TypeImportException
	 *             the FBT import exception
	 */
	private static void parseEventOutputs(List<Event> eventOutputs,
			final Node node, HashMap<String, Event> events)
			throws TypeImportException {
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node n = childNodes.item(i);
			if (n.getNodeName().equals(LibraryElementTags.EVENT_ELEMENT)) {

				Event e = ImportUtils.parseEvent(n);
				events.put(e.getName(), e);
				((IInterfaceElement) e).setIsInput(false);
				eventOutputs.add(e);
			}
		}
	}

	/**
	 * This method parses EventInputs of FBTypes.
	 * 
	 * @param fBtype
	 *            - the FBType containing the EventInputs being parsed
	 * @param node
	 *            - the node in the DTD of the EventInputs being parsed
	 * @throws TypeImportException
	 * 
	 * @throws TypeImportException
	 *             the FBT import exception
	 */
	private static void parseEventInputs(List<Event> eventInputs,
			final Node node, HashMap<String, Event> events)
			throws TypeImportException {
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node n = childNodes.item(i);
			if (n.getNodeName().equals(LibraryElementTags.EVENT_ELEMENT)) {
				Event e = ImportUtils.parseEvent(n);
				((IInterfaceElement) e).setIsInput(true);
				events.put(e.getName(), e);
				eventInputs.add(e);
			}
		}
	}

	/**
	 * This method parses the InterfaceList of a FBType.
	 * 
	 * @param node
	 *            - the node in the DTD of the interfaceList being parsed
	 * @param interfaceList
	 *            the interface list
	 * @param events
	 *            the events
	 * @param variables
	 *            the variables
	 * 
	 * @throws TypeImportException
	 *             the FBT import exception
	 */
	private static void parseInterfaceList(InterfaceList interfaceList,
			final Node node, HashMap<String, Event> events,
			HashMap<String, VarDeclaration> variables, boolean invertInterface)
			throws TypeImportException {
		// InterfaceList interfaceList = LibraryElementFactory.eINSTANCE
		// .createInterfaceList();
		// type.setInterfaceList(interfaceList);
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node n = childNodes.item(i);
			if (n.getNodeName().equals(LibraryElementTags.EVENT_INPUTS_ELEMENT)) {
				if (invertInterface) {
					parseEventOutputs(interfaceList.getEventOutputs(), n,
							events);
				} else {
					parseEventInputs(interfaceList.getEventInputs(), n, events);
				}
			}
			if (n.getNodeName().equals(LibraryElementTags.EVENT_OUTPUTS)) {
				if (invertInterface) {
					parseEventInputs(interfaceList.getEventInputs(), n, events);
				} else {
					parseEventOutputs(interfaceList.getEventOutputs(), n,
							events);
				}
			}
			if (n.getNodeName().equals(LibraryElementTags.INPUT_VARS_ELEMENT)) {
				List<VarDeclaration> vars = ImportUtils.parseInputVariables(n);
				for (Iterator<VarDeclaration> iterator = vars.iterator(); iterator
						.hasNext();) {
					VarDeclaration v = iterator.next();
					if (invertInterface) {
						v.setIsInput(false);
						interfaceList.getOutputVars().add(v);
					} else {
						v.setIsInput(true);
						interfaceList.getInputVars().add(v);
					}
					variables.put(v.getName(), v);
				}
			}
			if (n.getNodeName().equals(LibraryElementTags.OUTPUT_VARS_ELEMENT)) {
				List<VarDeclaration> vars = ImportUtils.parseOutputVariables(n);
				for (Iterator<VarDeclaration> iterator = vars.iterator(); iterator
						.hasNext();) {
					VarDeclaration v = iterator.next();
					if (invertInterface) {
						v.setIsInput(true);
						interfaceList.getInputVars().add(v);
					} else {
						v.setIsInput(false);
						interfaceList.getOutputVars().add(v);
					}
					variables.put(v.getName(), v);
				}
			}

		}
		new FBTImporter().parseWithConstructs(childNodes, events, events, variables);
	}
}
