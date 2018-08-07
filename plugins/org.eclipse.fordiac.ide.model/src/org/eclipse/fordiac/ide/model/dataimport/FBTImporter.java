/********************************************************************************
 * Copyright (c) 2008 - 2017  Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Gerhard Ebenhofer, Ingo Hegny, Alois Zoitl, Martin Jobst
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.Activator;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.ReferencedTypeNotFoundException;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterEvent;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.OtherAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterface;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Managing class for importing *.fbt files
 * 
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 * @author Martijn Rooker (martijn.rooker@profactor.at)
 */

public class FBTImporter implements LibraryElementTags {

	/** The variables. */
	private Map<String, VarDeclaration> variables = new HashMap<>();

	/** The internal variables. */
	private Map<String, VarDeclaration> internalVariables = new HashMap<>();

	/** The input events. */
	private Map<String, Event> inputEvents = new HashMap<>();
	
	/** The output events. */
	private Map<String, Event> outputEvents = new HashMap<>();

	/** The adapters. */
	private Map<String, AdapterDeclaration> adapters = new HashMap<>();
	private Map<String, PositionableElement> adapterPositions = new HashMap<>();

	/** The algorithm name ec action mapping. */
	private Map<String, ArrayList<ECAction>> algorithmNameECActionMapping = new HashMap<>();

	/** The ec states. */
	private Map<String, ECState> ecStates = new HashMap<>();
	
	private FBType type;

	private IFile file;
	
	private Palette palette;
	
	protected FBType getType() {
		return type;
	}
	
	protected Palette getPalette() {
		return palette; 
	}
	
	protected void setPalette(Palette palette) {
		this.palette = palette;
	}

	/**
	 * Import fb type.
	 * 
	 * @param fbtFile
	 *            the fbt file
	 * @param parseNetwork
	 *            the parse network
	 * @param palette
	 *            the palette
	 * 
	 * @return the fB type
	 * 
	 * @throws ReferencedTypeNotFoundException
	 *             the referenced type not found exception
	 */
	public FBType importType(final IFile fbtFile, final Palette palette){
		
		this.palette = palette;
		file = fbtFile;
		prepareParseDataBuffers();

		try {
			Document document = createDocument();
			if(null != document){
				Element rootNode = document.getDocumentElement();
				type = createType();
				
				configureType();
				
				// parse document and fill type
				return parseType(rootNode);
			}

		} catch (Exception e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
		
		return null;

	}

	
	/* Perform basic setup of the type's data structures
	 */
	protected void configureType() {
		Service service = LibraryElementFactory.eINSTANCE.createService();
		type.setService(service);
	}
	
	protected FBType createType(){
		return LibraryElementFactory.eINSTANCE.createFBType();
	}

	protected void prepareParseDataBuffers() {
		algorithmNameECActionMapping.clear();
		inputEvents.clear();
		outputEvents.clear();
		variables.clear();
		ecStates.clear();
		adapters.clear();
		adapterPositions.clear();
	}

	protected Document createDocument() throws ParserConfigurationException,
			SAXException, IOException, CoreException {
		if (file.exists()) {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(false);
			DocumentBuilder db;
			// TODO: set local dtd for validating!
			dbf
					.setAttribute(
							"http://apache.org/xml/features/nonvalidating/load-external-dtd", //$NON-NLS-1$
							Boolean.FALSE);
			db = dbf.newDocumentBuilder();
			return db.parse(file.getContents());
		}
		return null;
	}
		
	/**
	 * This method parses the DTD of the imported FBType.
	 * 
	 * @param rootNode
	 *            - The root node in the DTD
	 * @return the FBType that is parsed
	 * 
	 * @throws TypeImportException
	 *             the FBT import exception
	 * @throws ReferencedTypeNotFoundException
	 *             the referenced type not found exception
	 * @throws ParseException 
	 */
	protected FBType parseType(final Node rootNode)
			throws TypeImportException, ReferencedTypeNotFoundException, ParseException {
		if (rootNode.getNodeName().equals(FBTYPE_ELEMENT)) {
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
					type.setCompilerInfo(CompilableElementImporter.parseCompilerInfo(type, n));
				}
				if (n.getNodeName().equals(INTERFACE_LIST_ELEMENT)) {
					type.setInterfaceList(parseInterfaceList(n));
				}
				if (n.getNodeName().equals(BASIC_F_B_ELEMENT)) {
					type = convertoToBasicType(type);
					parseBasicFB((BasicFBType) type, n);
				}
				if (n.getNodeName().equals(FBNETWORK_ELEMENT)) {
					// parse the composite FBs as last
					type = convertToCompositeType(type);
					parseFBNetwork((CompositeFBType) type, n);
				}			
				
				if (n.getNodeName().equals(SERVICE_ELEMENT)) {
					// type = convertToServiceInterfaceType(type); // FIX:
					// gebenh: every
					// fbtype can have a service
					parseService(type, n);
				}
			}
			if ((type instanceof BasicFBType)
					|| (type instanceof CompositeFBType)
					|| (type instanceof ServiceInterfaceFBType)) {
				return type;
			} 
			type = convertToServiceInterfaceType(type);
			return type;
		} 
		throw new ParseException(Messages.FBTImporter_PARSE_FBTYPE_PARSEEXCEPTION, 0);
	}

	/**
	 * This method parses the DTD of a ServiceInterfaceFBType.
	 * 
	 * @param type
	 *            - The ServiceInterfaceFBType that is being parsed
	 * @param n
	 *            - the node of the ServiceInterfaceFBType in the DTD
	 * 
	 * @throws TypeImportException
	 *             the FBT import exception
	 */
	public void parseService(final FBType type, final Node n)
			throws TypeImportException {
		NodeList childNodes = n.getChildNodes();
		NamedNodeMap map = n.getAttributes();
		
		Node rightInterface = map.getNamedItem(RIGHT_INTERFACE_ATTRIBUTE);
		if (rightInterface != null) {
			ServiceInterface rightInter = LibraryElementFactory.eINSTANCE
					.createServiceInterface();
			rightInter.setName(rightInterface.getNodeValue());
			type.getService().setRightInterface(rightInter);
		} else {
			throw new TypeImportException(
					Messages.FBTImporter_SERVICE_INTERFACE_RIGHTINTERFACE_EXCEPTION);
		}
		Node leftInterface = map.getNamedItem(LEFT_INTERFACE_ATTRIBUTE);
		if (leftInterface != null) {
			ServiceInterface leftInter = LibraryElementFactory.eINSTANCE
					.createServiceInterface();
			leftInter.setName(leftInterface.getNodeValue());
			type.getService().setLeftInterface(leftInter);
		} else {
			throw new TypeImportException(
					Messages.FBTImporter_SERVICE_INTERFACE_LEFTINTERFACE_EXCEPTION);
		}
		Node comment = map.getNamedItem(COMMENT_ATTRIBUTE);
		if (comment != null) {
			type.setComment(comment.getNodeValue());
		}
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node node = childNodes.item(i);
			if (node.getNodeName().equals(SERVICE_SEQUENCE_ELEMENT)) {
				parseServiceSequence(type, node);
			}
		}
	}

	/**
	 * This method parses the ServiceSequence of a ServiceInterfaceFBType.
	 * 
	 * @param type
	 *            - The ServiceInterfaceFBType from which the ServiceSequence
	 *            will be parsed
	 * @param node
	 *            - The node in the DTD of the ServiceSequence of the
	 *            ServiceInterfaceFBType that is being parsed
	 * 
	 * @throws TypeImportException
	 *             the FBT import exception
	 */
	private void parseServiceSequence(final FBType type, final Node node)
			throws TypeImportException {
		ServiceSequence serviceSequence = LibraryElementFactory.eINSTANCE
				.createServiceSequence();
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node n = childNodes.item(i);
			if (n.getNodeName().equals(SERVICE_TRANSACTION_ELEMENT)) {
				parseServiceTransaction(serviceSequence, n, type);
			}
		}
		NamedNodeMap map = node.getAttributes();
		CommonElementImporter.readNameCommentAttributes(serviceSequence, map);		
		type.getService().getServiceSequence().add(serviceSequence);
	}

	/**
	 * This method parses the ServiceTransaction of a ServiceSequence.
	 * 
	 * @param serviceSequence
	 *            - The serviceSequence containing the serviceTransaction that
	 *            is being parsed
	 * @param node
	 *            - The node in the DTD of the serviceTransaction of the
	 *            serviceSequence that is being parsed
	 * @param type
	 *            - The serviceInterfaceFBType containing the serviceTransaction
	 * 
	 * @throws TypeImportException
	 *             the FBT import exception
	 */
	private void parseServiceTransaction(final ServiceSequence serviceSequence,
			final Node node, final FBType type) throws TypeImportException {
		ServiceTransaction serviceTransaction = LibraryElementFactory.eINSTANCE
				.createServiceTransaction();
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node n = childNodes.item(i);
			if (n.getNodeName().equals(INPUT_PRIMITIVE_ELEMENT)) {
				parseInputPrimitive(serviceTransaction, n, type);
			}
			if (n.getNodeName().equals(OUTPUT_PRIMITIVE_ELEMENT)) {
				parseOutputPrimitive(serviceTransaction, n, type);
			}
			serviceSequence.getServiceTransaction().add(serviceTransaction);
		}

	}

	/**
	 * This method parses the OutputPrimitive of a ServiceTransaction.
	 * 
	 * @param serviceTransaction
	 *            - The serviceTransaction containing the OutputPrimitive that
	 *            is being parsed
	 * @param n
	 *            - the node in the DTD of the OutputPrimitive of the
	 *            serviceTransaction that is being parsed
	 * @param type
	 *            - the serviceInterfaceFBType containing the OutputPrimitive
	 * 
	 * @throws TypeImportException
	 *             the FBT import exception
	 */
	private void parseOutputPrimitive(
			final ServiceTransaction serviceTransaction, final Node n,
			final FBType type) throws TypeImportException {
		OutputPrimitive outputPrimitive = LibraryElementFactory.eINSTANCE
				.createOutputPrimitive();
		NamedNodeMap map = n.getAttributes();
		Node interFace = map.getNamedItem(INTERFACE_ATTRIBUTE);
		if (interFace != null) {
			if (interFace.getNodeValue().equals(
					type.getService().getLeftInterface().getName())) {
				outputPrimitive.setInterface(type.getService().getLeftInterface());
			} else if (interFace.getNodeValue().equals(
					type.getService().getRightInterface().getName())) {
				outputPrimitive.setInterface(type.getService().getRightInterface());
			}
		} else {
			throw new TypeImportException(
					Messages.FBTImporter_OUTPUT_PRIMITIVE_EXCEPTION);
		}
		Node event = map.getNamedItem(getEventElement());
		if (event != null) {
			outputPrimitive.setEvent(event.getNodeValue());
		} else {
			throw new TypeImportException(
					Messages.FBTImporter_OUTPUT_PRIMITIVE_EVENT_EXCEPTION);
		}
		Node parameters = map.getNamedItem(PARAMETERS_ATTRIBUTE);
		if (parameters != null) {
			outputPrimitive.setParameters(parameters.getNodeValue());
		}

		serviceTransaction.getOutputPrimitive().add(outputPrimitive);
	}

	/**
	 * This method parses the InputPrimitive of a ServiceTransaction.
	 * 
	 * @param serviceTransaction
	 *            - The serviceTransaction containing the InputPrimitive that is
	 *            being parsed
	 * @param n
	 *            - the node in the DTD of the InputPrimitive of the
	 *            serviceTransaction that is being parsed
	 * @param type
	 *            - the serviceInterfaceFBType containing the InputPrimitive
	 * 
	 * @throws TypeImportException
	 *             the FBT import exception
	 */
	private void parseInputPrimitive(
			final ServiceTransaction serviceTransaction, final Node n,
			final FBType type) throws TypeImportException {
		InputPrimitive inputPrimitive = LibraryElementFactory.eINSTANCE
				.createInputPrimitive();
		NamedNodeMap map = n.getAttributes();
		Node interFace = map.getNamedItem(INTERFACE_ATTRIBUTE);
		if (interFace != null) {
			if (interFace.getNodeValue().equals(
					type.getService().getLeftInterface().getName())) {
				inputPrimitive.setInterface(type.getService().getLeftInterface());
			} else if (interFace.getNodeValue().equals(
					type.getService().getRightInterface().getName())) {
				inputPrimitive.setInterface(type.getService().getRightInterface());
			}
		} else {
			throw new TypeImportException(
					Messages.FBTImporter_INPUT_PRIMITIVE_EXCEPTION);
		}
		Node event = map.getNamedItem(getEventElement());
		if (event != null) {
			inputPrimitive.setEvent(event.getNodeValue());
		} else {
			throw new TypeImportException(
					Messages.FBTImporter_INPUT_PRIMITIVE_EVENT_EXCEPTION);
		}
		Node parameters = map.getNamedItem(PARAMETERS_ATTRIBUTE);
		if (parameters != null) {
			inputPrimitive.setParameters(parameters.getNodeValue());
		}

		serviceTransaction.setInputPrimitive(inputPrimitive);
	}

	/**
	 * This method converts a FBType to a ServiceInterfaceFBType.
	 * 
	 * @param type
	 *            - The FBType that is being converted to ServiceInterfaceFBType
	 * 
	 * @return - A FBType that is converted
	 */
	private static FBType convertToServiceInterfaceType(final FBType type) {
		ServiceInterfaceFBType serviceType = LibraryElementFactory.eINSTANCE
				.createServiceInterfaceFBType();
		copyBasicTypeInformation(serviceType, type);
		return serviceType;
	}

	private static void copyBasicTypeInformation(FBType dstType, FBType srcType) {
		dstType.setName(srcType.getName());
		dstType.setComment(srcType.getComment());
		dstType.setCompilerInfo(srcType.getCompilerInfo());
		dstType.setInterfaceList(srcType.getInterfaceList());
		dstType.setIdentification(srcType.getIdentification());
		dstType.getVersionInfo().addAll(srcType.getVersionInfo());
		dstType.setService(srcType.getService());
	}

	/**
	 * This method parses a compositeFBType.
	 * 
	 * @param type
	 *            - the CompositeFBType that is being parsed
	 * @param node
	 *            - the node in the DTD of the CompositeFBType that is being
	 *            parsed
	 * @param palette
	 *            the palette
	 * 
	 * @throws TypeImportException
	 *             the FBT import exception
	 * @throws ReferencedTypeNotFoundException
	 *             the referenced type not found exception
	 */
	private void parseFBNetwork(final CompositeFBType type, final Node node) throws TypeImportException,
			ReferencedTypeNotFoundException {
		FBNetwork fbNetwork = LibraryElementFactory.eINSTANCE.createFBNetwork();
		
		if (adapters.size() > 0) {
			for (AdapterDeclaration adapter : adapters.values()) {
				if (adapter.getType() instanceof AdapterType) {
					addAdapterFB(fbNetwork, adapter, palette);
				}
			}
		}
		
		FBNetworkImporter fbnInmporter = new FBNetworkImporter(palette, fbNetwork, type.getInterfaceList());		
		type.setFBNetwork(fbnInmporter.parseFBNetwork(node));		
	}

	private void addAdapterFB(final FBNetwork fbNetwork, AdapterDeclaration adapter, Palette palette) {
		AdapterFB aFB = LibraryElementFactory.eINSTANCE.createAdapterFB();
		aFB.setPaletteEntry(getAdapterPaletEntry(adapter.getTypeName(), palette));
		aFB.setAdapterDecl(adapter);
		
		aFB.setName(adapter.getName());
		PositionableElement position = adapterPositions.get(adapter.getName());
		if (position == null) {
			aFB.setX(0);
			aFB.setY(0);
		}else{
			aFB.setX(position.getX());
			aFB.setY(position.getY());
		}
		aFB.setInterface(EcoreUtil.copy(aFB.getType().getInterfaceList()));
		fbNetwork.getNetworkElements().add(aFB);
	}

	private static AdapterTypePaletteEntry getAdapterPaletEntry(String name, Palette palette) {
		PaletteEntry entry = palette.getTypeEntry(name);
		return (entry instanceof AdapterTypePaletteEntry) ? (AdapterTypePaletteEntry)entry : null;
	}
	
	/**
	 * This method converts a FBType to a CompositeFBType.
	 * 
	 * @param type
	 *            - The FBType that is being converted to CompositeFBType
	 * 
	 * @return - A FBType that is converted
	 */
	private static FBType convertToCompositeType(final FBType type) {
		CompositeFBType compositeType = LibraryElementFactory.eINSTANCE
				.createCompositeFBType();
		copyBasicTypeInformation(compositeType, type);
		return compositeType;
	}

	/**
	 * This method parses a BasicFBType.
	 * 
	 * @param type
	 *            - the basicFBType that is being parsed
	 * @param node
	 *            - the node in the DTD of the BasicFBType that is being parsed
	 * @param palette
	 *            the palette
	 * 
	 * @throws TypeImportException
	 *             the FBT import exception
	 * @throws ReferencedTypeNotFoundException
	 *             the referenced type not found exception
	 */
	private void parseBasicFB(final BasicFBType type, final Node node) throws TypeImportException,
			ReferencedTypeNotFoundException {
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node n = childNodes.item(i);
			if (n.getNodeName().equals(INTERNAL_VARS_ELEMENT)) {
				parseInternalVars(type, n);
			}
			if (n.getNodeName().equals(ECC_ELEMENT)) {
				parseECC(type, n);
			}
			if (n.getNodeName().equals(ALGORITHM_ELEMENT)) {
				parseAlgorithm(type, n);
			}
		}
	}

	/**
	 * This method parses an Algorithm.
	 * 
	 * @param type
	 *            - the BasicFBType containing the Algorithm
	 * @param n
	 *            - the node in the DTD of the algorithm that is being parsed
	 * @param palette
	 *            the palette
	 * 
	 * @throws TypeImportException
	 *             the FBT import exception
	 * @throws ReferencedTypeNotFoundException
	 *             the referenced type not found exception
	 */
	private void parseAlgorithm(final BasicFBType type, final Node n) throws TypeImportException,
			ReferencedTypeNotFoundException {
		NamedNodeMap map = n.getAttributes();
		Node nameNode = map.getNamedItem(NAME_ATTRIBUTE);
		Node commentNode = map.getNamedItem(COMMENT_ATTRIBUTE);
		String name = null;
		String comment = null;
		if (nameNode != null) {
			name = nameNode.getNodeValue();
		}
		if (commentNode != null) {
			comment = commentNode.getNodeValue();
		}

		NodeList childNodes = n.getChildNodes(); // should have only one
		// child node
		for (int i = 0; i < childNodes.getLength(); i++) {

			Node node = childNodes.item(i);
			String nodeName = node.getNodeName();
			if (nodeName.equals(FBD_ELEMENT)) {
				throw new TypeImportException("Algorithm: Unsupported Algorithmtype (only ST and Other possible)!");
			} else if (nodeName.equals(ST_ELEMENT)) {
				STAlgorithm sT = LibraryElementFactory.eINSTANCE
						.createSTAlgorithm();
				sT.setName(name);
				sT.setComment(comment);
				parseST(sT, node);
				type.getAlgorithm().add(sT);
				ArrayList<ECAction> list = algorithmNameECActionMapping.get(sT
						.getName());
				if (list != null) {
					for (ECAction action : list) {
						action.setAlgorithm(sT);
					}
				}
			} else if (nodeName.equals(LD_ELEMENT)) {
				throw new TypeImportException("Algorithm: Unsupported Algorithmtype (only ST and Other possible)!");
			} else if (nodeName.equals(OTHER_ELEMENT)) {
				OtherAlgorithm other = LibraryElementFactory.eINSTANCE
						.createOtherAlgorithm();
				other.setName(name);
				other.setComment(comment);
				parseOtherAlg(other, node);
				type.getAlgorithm().add(other);
				ArrayList<ECAction> list = algorithmNameECActionMapping
						.get(other.getName());
				if (list != null) {
					for (ECAction action : list) {
						action.setAlgorithm(other);
					}
				}
			}

			// throw new FBTImportException(
			// "Algorithm: Unsupported Algorithmtype (only FBD, ST, LD and Other
			// possible)!");
		}
	}

	/**
	 * Parses the other alg.
	 * 
	 * @param other
	 *            the other
	 * @param node
	 *            the node
	 * 
	 * @throws TypeImportException
	 *             the FBT import exception
	 */
	private static void parseOtherAlg(final OtherAlgorithm other, final Node node) throws TypeImportException {
		NamedNodeMap map = node.getAttributes();
		Node language = map.getNamedItem(LANGUAGE_ATTRIBUTE);
		if (language != null) {
			other.setLanguage(language.getNodeValue());
		} else {
			throw new TypeImportException(
					Messages.FBTImporter_OTHER_ALG_MISSING_LANG_EXCEPTION);
		}

		Node text = map.getNamedItem(TEXT_ATTRIBUTE);
		if (text != null) {
			other.setText(text.getNodeValue());
		} else {
			throw new TypeImportException(
					Messages.FBTImporter_OTHER_ALG_MISSING_TEXT_EXCEPTION);
		}
	}
	

	/**
	 * This method parses a STAlgorithm.
	 * 
	 * @param st
	 *            - the STAlgorithm being parsed
	 * @param node
	 *            - the node in the DTD of the STAlgorithm that is being parsed
	 * 
	 * @throws TypeImportException
	 *             the FBT import exception
	 */
	private static void parseST(final STAlgorithm st, final Node node) throws TypeImportException {
		NamedNodeMap map = node.getAttributes();
		Node text = map.getNamedItem(TEXT_ATTRIBUTE);
		if (text != null) {
			st.setText(text.getNodeValue());
		} else {
			throw new TypeImportException(
					Messages.FBTImporter_ST_TEXTNOTSET_EXCEPTION);
		}

	}

	/**
	 * This method parses an ECC.
	 * 
	 * @param type
	 *            - the BasicFBType containing the ECC being parsed
	 * @param n
	 *            - the node in the DTD of the ECC being parsed
	 * 
	 * @throws TypeImportException
	 *             the FBT import exception
	 */
	private void parseECC(final BasicFBType type, final Node n)
			throws TypeImportException {
		NodeList childNodes = n.getChildNodes();
		ECC ecc = LibraryElementFactory.eINSTANCE.createECC();
		boolean first = true;
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node node = childNodes.item(i);
			if (node.getNodeName().equals(ECSTATE_ELEMENT)) {
				parseECState(ecc, node, first); // IEC 61499 ->
				// "START" state is the
				// first in the list
				first = false;
			}
			if (node.getNodeName().equals(ECTRANSITION_ELEMENT)) {
				parseECTransition(ecc, node);
			}
		}
		type.setECC(ecc);
	}

	/**
	 * This method parses an ECTransition.
	 * 
	 * @param ecc
	 *            - the ECC containing the ECTransition being parsed
	 * @param node
	 *            - the node in the DTD of the ECTransition being parsed
	 * 
	 * @throws TypeImportException
	 *             the FBT import exception
	 */
	private void parseECTransition(final ECC ecc, final Node node)
			throws TypeImportException {
		NamedNodeMap map = node.getAttributes();
		ECTransition ecTransition = LibraryElementFactory.eINSTANCE
				.createECTransition();
		ecc.getECTransition().add(ecTransition);
		Node source = map.getNamedItem(SOURCE_ATTRIBUTE);
		if (source != null) {
			ECState state = ecStates.get(source.getNodeValue());
			if (state != null) {
				ecTransition.setSource(state);
			}
		} else {
			throw new TypeImportException(
					Messages.FBTImporter_ECTRANSITION_SOURCE_EXCEPTION);
		}
		Node destination = map.getNamedItem(DESTINATION_ATTRIBUTE);
		if (destination != null) {
			ECState state = ecStates.get(destination.getNodeValue());
			if (state != null) {
				ecTransition.setDestination(state);
			}

		} else {
			throw new TypeImportException(
					Messages.FBTImporter_ECTRANSITION_DEST_EXCEPTION);
		}
		Node condition = map.getNamedItem(CONDITION_ATTRIBUTE);
		if (condition != null) {
			validateTransitionCondition(ecTransition, condition.getNodeValue());
		} else {
			throw new TypeImportException(
					Messages.FBTImporter_ECTRANASITION_CONDITION_EXCEPTION);
		}
		Node comment = map.getNamedItem(COMMENT_ATTRIBUTE);
		if (comment != null) {
			ecTransition.setComment(comment.getNodeValue());
		}
		CommonElementImporter.getXandY(map, ecTransition);
	}

	private void validateTransitionCondition(ECTransition ecTransition,
			String condition) throws TypeImportException {
		Event event;
		String expression;
		
		// first, try splitting according to 1st edition
		String[] split = condition.split("&", 2); //$NON-NLS-1$
		event = inputEvents.get(split[0].trim());
		if(event != null) {
			// remainder is expression
			expression = split.length > 1 ? split[1].trim() : ""; //$NON-NLS-1$
		}
		else { // otherwise, try splitting according to 2nd edition
			split = condition.split("\\[", 2); //$NON-NLS-1$
			event = inputEvents.get(split[0].trim());
			if(event != null) {
				// remainder is expression (except trailing ']')
				expression = split.length > 1 ? split[1].substring(0, split[1].lastIndexOf(']')).trim() : ""; //$NON-NLS-1$ 
			}
			else {
				// no match (all is expression)
				if(condition.startsWith("[")){ //$NON-NLS-1$
					expression = condition.substring(1, condition.lastIndexOf(']')); 
				}else{
					expression = condition;
				}
			}
		}
		
		ecTransition.setConditionEvent(event);
		ecTransition.setConditionExpression(expression);
	}

	/**
	 * This method parses an ECState.
	 * 
	 * @param ecc
	 *            - the ECC containing the ECState being parsed
	 * @param node
	 *            - the node in the DTD of the ECState being parsed
	 * @param initialState
	 *            the initial state
	 * 
	 * @throws TypeImportException
	 *             the FBT import exception
	 */
	private void parseECState(final ECC ecc, final Node node,
			final boolean initialState) throws TypeImportException {
		NodeList childNodes = node.getChildNodes();
		ECState state = LibraryElementFactory.eINSTANCE.createECState();
		ecc.getECState().add(state);
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node n = childNodes.item(i);
			if (n.getNodeName().equals(ECACTION_ELEMENT)) {
				parseECAction(state, n);
			}
		}
		NamedNodeMap map = node.getAttributes();
		CommonElementImporter.readNameCommentAttributes(state, map);
		CommonElementImporter.getXandY(map, state);
		ecStates.put(state.getName(), state);
		if (initialState) {
			// have to be "START"
			ecc.setStart(state);
		}
	}

	/**
	 * This method parses an ECAction.
	 * 
	 * @param type
	 *            - the ECState belonging to the ECAction being parsed
	 * @param n
	 *            - the node in the DTD of the ECAction being parsed
	 * 
	 * @throws TypeImportException
	 */
	private void parseECAction(final ECState type, final Node n) {
		NamedNodeMap map = n.getAttributes();
		ECAction ecAction = LibraryElementFactory.eINSTANCE.createECAction();
		type.getECAction().add(ecAction);
		Node algorithm = map.getNamedItem(ALGORITHM_ELEMENT);
		if (algorithm != null) {
			if (algorithmNameECActionMapping.containsKey(algorithm
					.getNodeValue())) {
				algorithmNameECActionMapping.get(algorithm.getNodeValue()).add(
						ecAction);
			} else {
				ArrayList<ECAction> temp = new ArrayList<ECAction>();
				temp.add(ecAction);
				algorithmNameECActionMapping
						.put(algorithm.getNodeValue(), temp);
			}
		}
		Node output = map.getNamedItem(OUTPUT_ATTRIBUTE);
		if (output != null) {
			Event outp = outputEvents.get(output.getNodeValue());
			if (outp != null) {
				ecAction.setOutput(outp);
			}
		}

	}

	/**
	 * This method parses Internal Variables of a BasicFBType.
	 * 
	 * @param type
	 *            - the BasicFBType of which the Internal Variables will be
	 *            parsed
	 * @param n
	 *            - the node in the DTD of the Internal Variable being parsed
	 * 
	 * @throws TypeImportException
	 *             the FBT import exception
	 */
	private void parseInternalVars(final BasicFBType type, final Node n)
			throws TypeImportException {
		NodeList childNodes = n.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node node = childNodes.item(i);
			if (node.getNodeName().equals(VAR_DECLARATION_ELEMENT)) {
				VarDeclaration v = ImportUtils.parseVarDeclaration(node);
				type.getInternalVars().add(v);
				internalVariables.put(v.getName(), v);
			}
		}
	}

	/**
	 * This method parses a FBType to a BasicFBType.
	 * 
	 * @param type
	 *            - the FBType being parsed to BasicFBType
	 * 
	 * @return the basicFBType
	 */
	private static FBType convertoToBasicType(final FBType type) {
		BasicFBType basicType = LibraryElementFactory.eINSTANCE
				.createBasicFBType();
		copyBasicTypeInformation(basicType, type);
		return basicType;
	}

	/**
	 * This method parses the InterfaceList of a FBType.
	 * 
	 * @param type
	 *            - the FBType containing the InterfaceList being parsed
	 * @param node
	 *            - the node in the DTD of the interfaceList being parsed
	 * 
	 * @throws TypeImportException
	 *             the FBT import exception
	 */
	protected InterfaceList parseInterfaceList(final Node node) throws TypeImportException {
		InterfaceList interfaceList = LibraryElementFactory.eINSTANCE.createInterfaceList();
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node n = childNodes.item(i);
			if (n.getNodeName().equals(getEventInputElement())) {
				parseEventInputs(interfaceList, n);
			}
			if (n.getNodeName().equals(getEventOutputElement())) {
				parseEventOutputs(interfaceList, n);
			}
			if (n.getNodeName().equals(INPUT_VARS_ELEMENT)) {
				List<VarDeclaration> vars = ImportUtils.parseInputVariables(n);
				for (Iterator<VarDeclaration> iterator = vars.iterator(); iterator
						.hasNext();) {
					VarDeclaration v = iterator.next();
					interfaceList.getInputVars().add(v);
					variables.put(v.getName(), v);
				}
			}
			if (n.getNodeName().equals(OUTPUT_VARS_ELEMENT)) {
				List<VarDeclaration> vars = ImportUtils.parseOutputVariables(n);
				for (Iterator<VarDeclaration> iterator = vars.iterator(); iterator.hasNext();) {
					VarDeclaration v = iterator.next();
					interfaceList.getOutputVars().add(v);
					variables.put(v.getName(), v);
				}
			}
			if (n.getNodeName().equals(SOCKETS_ELEMENT)) {
				parseSockets(interfaceList, n, palette);
			}
			if (n.getNodeName().equals(PLUGS_ELEMENT)) {
				parsePlugs(interfaceList, n, palette);
			}
		}
		parseWithConstructs(childNodes);
		return interfaceList;
	}

	protected String getEventOutputElement() {
		return EVENT_OUTPUTS;
	}

	protected String getEventInputElement() {
		return EVENT_INPUTS_ELEMENT;
	}

	/**
	 * This method parses Plugs of a FBType.
	 * 
	 * @param type
	 *            - the FBType containing the Plugs
	 * @param node
	 *            - the node in the DTD of the Plug being parsed
	 * 
	 * @throws TypeImportException
	 *             the FBT import exception
	 */
	private void parsePlugs(final InterfaceList interfaceList, final Node node, Palette palette)
			throws TypeImportException {
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node n = childNodes.item(i);
			if (n.getNodeName().equals(ADAPTER_DECLARATION_ELEMENT)) {
				AdapterDeclaration a = parseAdapterDeclaration(n, palette);
				a.setIsInput(false);
				adapters.put(a.getName(), a);
				interfaceList.getPlugs().add(a);
				if (a.getType() instanceof AdapterType) {
					addAdapterEventInputs(((AdapterType)a.getType()).getInterfaceList().getEventInputs(), a);
					addAdapterEventOutputs(((AdapterType)a.getType()).getInterfaceList().getEventOutputs(), a);
				}
			}

		}
	}

	private void addAdapterEventOutputs(EList<Event> eventOutputs,
			AdapterDeclaration a) {
		for (Event event : eventOutputs) {
			AdapterEvent ae = ImportUtils.createAdapterEvent(event, a);
			outputEvents.put(ae.getName(), ae);
		}
		
	}

	private void addAdapterEventInputs(EList<Event> eventInputs,
			AdapterDeclaration a) {
		for (Event event : eventInputs) {
			AdapterEvent ae = ImportUtils.createAdapterEvent(event, a);
			inputEvents.put(ae.getName(), ae);
		}		
	}

	/**
	 * This method parses Sockets of a FBType.
	 * 
	 * @param type
	 *            - the FBType containing the Sockets
	 * @param node
	 *            - the node in the DTD of the Socket being parsed
	 * 
	 * @throws TypeImportException
	 *             the FBT import exception
	 */
	private void parseSockets(final InterfaceList interfaceList, final Node node,
			Palette palette) throws TypeImportException {
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node n = childNodes.item(i);
			if (n.getNodeName().equals(ADAPTER_DECLARATION_ELEMENT)) {
				AdapterDeclaration a = parseAdapterDeclaration(n, palette);
				//TODO handle if type was not found!
				a.setIsInput(true);
				adapters.put(a.getName(), a);
				interfaceList.getSockets().add(a);
				if ((AdapterType)a.getType() != null && ((AdapterType)a.getType()).getInterfaceList() != null) {
					addAdapterEventInputs(((AdapterType)a.getType()).getInterfaceList().getEventOutputs(), a);	
				}
				if ((AdapterType)a.getType() != null && ((AdapterType)a.getType()).getInterfaceList() != null) {
					addAdapterEventOutputs(((AdapterType)a.getType()).getInterfaceList().getEventInputs(), a);	
				}
				
			}
		}

	}

	/**
	 * This method parses AdapterDeclaration.
	 * 
	 * @param node
	 *            - the node in the DTD of the AdapterDeclaration being parsed
	 * 
	 * @return a - the AdapterDeclaration
	 * 
	 * @throws TypeImportException
	 *             the FBT import exception
	 */
	private AdapterDeclaration parseAdapterDeclaration(final Node node,
			Palette palette) throws TypeImportException {
		AdapterDeclaration a = LibraryElementFactory.eINSTANCE
				.createAdapterDeclaration();
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node n = childNodes.item(i);
			if (n.getNodeName().equals(PARAMETER_ELEMENT)) {
				// TODO !! import parameter of adapterDeclaration
				// Parameter p = ImportUtils.parseParameter(node);
				// a.getParameter().add(p);
			}
		}
		NamedNodeMap map = node.getAttributes();
		CommonElementImporter.readNameCommentAttributes(a, map);
		Node typeName = map.getNamedItem(LibraryElementTags.TYPE_ATTRIBUTE);
		if (typeName != null) {
			AdapterTypePaletteEntry entry = getAdapterPaletEntry(typeName.getNodeValue(), palette);
			a.setPaletteEntry(entry);
			AdapterType dataType = null;
			if (entry != null) {
				dataType = entry.getType();
			}
			a.setTypeName(typeName.getNodeValue());
			if (dataType != null) {
				a.setType(dataType);
			}
		} else {
			throw new TypeImportException(
					Messages.FBTImporter_ADAPTER_DECLARATION_TYPE_EXCEPTION);
		}
		
		PositionableElement pos = LibraryElementFactory.eINSTANCE.createPositionableElement(); 
		CommonElementImporter.getXandY(map, pos);
		adapterPositions.put(a.getName(), pos);
		return a;
	}

	/**
	 * This method parses WithConstructs.
	 * 
	 * @param childNodes
	 *            - the childNodes in the DTD containing the WithConstructs
	 *            being parsed
	 */
	private void parseWithConstructs(final NodeList childNodes) {
		parseWithConstructs(childNodes, inputEvents, outputEvents, variables);
	}

	public void parseWithConstructs(final NodeList childNodes,
			Map<String, Event> eventInputs,
			Map<String, Event> eventOutputs,
			Map<String, VarDeclaration> variables) {
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node n = childNodes.item(i);
			if (n.getNodeName().equals(getEventInputElement())) {
				NodeList inputEventNodes = n.getChildNodes();
				for (int j = 0; j < inputEventNodes.getLength(); j++) {
					Node eventNode = inputEventNodes.item(j);
					if (eventNode.getNodeName().equals(getEventElement())) {
						NamedNodeMap map = eventNode.getAttributes();
						Node name = map.getNamedItem(NAME_ATTRIBUTE);
						Event e = eventInputs.get(name.getNodeValue());
						NodeList withs = eventNode.getChildNodes();
						for (int k = 0; k < withs.getLength(); k++) {
							Node with = withs.item(k);
							if (with.getNodeName().equals(WITH_ELEMENT)) {
								NamedNodeMap withAttributes = with
										.getAttributes();
								Node var = withAttributes
										.getNamedItem(VAR_ATTRIBUTE);
								if (var != null) {
									With withConstruct = LibraryElementFactory.eINSTANCE
											.createWith();
									e.getWith().add(withConstruct);
									VarDeclaration v = variables.get(var
											.getNodeValue());
									withConstruct.setVariables(v);
								}
							}
						}
					}
				}
			}
			if (n.getNodeName().equals(getEventOutputElement())) {
				NodeList outputEventNodes = n.getChildNodes();
				for (int j = 0; j < outputEventNodes.getLength(); j++) {
					Node eventNode = outputEventNodes.item(j);
					if (eventNode.getNodeName().equals(getEventElement())) {
						NamedNodeMap map = eventNode.getAttributes();
						Node name = map.getNamedItem(NAME_ATTRIBUTE);
						Event e = eventOutputs.get(name.getNodeValue());
						NodeList withs = eventNode.getChildNodes();
						for (int k = 0; k < withs.getLength(); k++) {
							Node with = withs.item(k);
							if (with.getNodeName().equals(WITH_ELEMENT)) {
								NamedNodeMap withAttributes = with
										.getAttributes();
								Node var = withAttributes
										.getNamedItem(VAR_ATTRIBUTE);
								if (var != null) {
									With withConstruct = LibraryElementFactory.eINSTANCE
											.createWith();
									e.getWith().add(withConstruct);
									VarDeclaration v = variables.get(var
											.getNodeValue());
									withConstruct.setVariables(v);
								}
							}
						}
					}
				}
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
	 * 
	 * @throws TypeImportException
	 *             the FBT import exception
	 */
	private void parseEventInputs(final InterfaceList interfaceList, final Node node)
			throws TypeImportException {
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node n = childNodes.item(i);
			if (n.getNodeName().equals(getEventElement())) {
				Event e = ImportUtils.parseEvent(n);
				((IInterfaceElement) e).setIsInput(true);
				inputEvents.put(e.getName(), e);
				interfaceList.getEventInputs().add(e);
			}
		}
	}

	protected String getEventElement() {
		return EVENT_ELEMENT;
	}

	/**
	 * This method parses EventOutputs of FBTypes.
	 * 
	 * @param interfacelist 
	 * @param node
	 *            - the node in the DTD of the EventOutputs being parsed
	 * 
	 * @throws TypeImportException
	 *             the FBT import exception
	 */
	private void parseEventOutputs(final InterfaceList interfaceList, final Node node)
			throws TypeImportException {
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node n = childNodes.item(i);
			if (n.getNodeName().equals(getEventElement())) {

				Event e = ImportUtils.parseEvent(n);
				outputEvents.put(e.getName(), e);
				((IInterfaceElement) e).setIsInput(false);
				interfaceList.getEventOutputs().add(e);
			}
		}
	}

	/**
	 * This method returns a list with all the data types that are referenced by
	 * the imported FBTypes.
	 * 
	 * @param file
	 *            - the file that is being checked if it has references
	 * 
	 * @return references - a list containing all the references
	 */
	public static List<String> getReferencedDataTypes(final File file) {
		ArrayList<String> references = new ArrayList<String>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;

		dbf
				.setAttribute(
						"http://apache.org/xml/features/nonvalidating/load-external-dtd", //$NON-NLS-1$
						Boolean.FALSE);
		try {
			db = dbf.newDocumentBuilder();
			Document document = db.parse(file);
			// parse document for "FBNetwork" tag
			Node rootNode = document.getDocumentElement();
			NodeList childNodes = rootNode.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node n = childNodes.item(i);
				if (n.getNodeName().equals(VAR_DECLARATION_ELEMENT)) {
					String dataType = ""; //$NON-NLS-1$
					dataType = n.getAttributes().getNamedItem(TYPE_ATTRIBUTE)
							.getNodeValue();
					references.add(dataType);
				}

			}
		} catch (Exception e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
		return references;

	}

	/**
	 * This method returns a list with all the types that are referenced by the
	 * imported FBTypes.
	 * 
	 * @param file
	 *            - the file that is being checked if it has references
	 * 
	 * @return references - a list containing all the references
	 */
	public static List<String> getReferencedFBTypes(final File file) {
		ArrayList<String> references = new ArrayList<String>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;

		dbf
				.setAttribute(
						"http://apache.org/xml/features/nonvalidating/load-external-dtd", //$NON-NLS-1$
						Boolean.FALSE);
		try {
			db = dbf.newDocumentBuilder();
			Document document = db.parse(file);
			// parse document for "FBNetwork" tag
			Node rootNode = document.getDocumentElement();
			NodeList childNodes = rootNode.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node n = childNodes.item(i);
				if (n.getNodeName().equals(FBNETWORK_ELEMENT)) {
					// add nodes to NodeList
					for (int j = 0; j < n.getChildNodes().getLength(); j++) {
						Node node = n.getChildNodes().item(j);
						if (node.getNodeName().equals(FB_ELEMENT)) {
							String fbType = ""; //$NON-NLS-1$
							fbType = node.getAttributes().getNamedItem(
									TYPE_ATTRIBUTE).getNodeValue();
							references.add(fbType);
						}

					}
				}
			}
		} catch (Exception e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
		return references;
	}

}
