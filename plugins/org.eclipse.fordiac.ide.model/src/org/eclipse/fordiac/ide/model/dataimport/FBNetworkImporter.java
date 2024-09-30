/********************************************************************************
 * Copyright (c) 2016, 2020 fortiss GmbH, Johannes Kepler University, Linz,
 *               2020, 2021, 2022 Primetals Technologies Austria GmbH
 *               2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *               - fixed coordinate system resolution conversion in in- and export
 *   			 - Changed XML parsing to Staxx cursor interface for improved
 *  			   parsing performance
 *   Bianca Wiesmayr - mux support
 *   Alois Zoitl - added error marker generation for missing fb types and
 *                 connection error
 *   Martin Melik Merkumians - moved functionality to base class for usage
 *                 in FBTImporter
 *   Michael Oberlehner - refactored and extented error markers for pins, connections and datatypes
 *   Martin Jobst - refactor marker handling
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.xml.stream.XMLStreamException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.dataimport.ConnectionHelper.ConnectionBuilder;
import org.eclipse.fordiac.ide.model.dataimport.ConnectionHelper.ConnectionState;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarkerInterfaceHelper;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.helpers.BlockInstanceFactory;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.Comment;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.resource.TypeImportDiagnostic;
import org.eclipse.fordiac.ide.model.typelibrary.AdapterTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;

class FBNetworkImporter extends CommonElementImporter {

	private final FBNetwork fbNetwork;
	// this is the interface list needed for checking connection to the containing
	// types interface
	private final InterfaceList interfaceList;

	private final Map<String, FBNetworkElement> fbNetworkElementMap;

	public FBNetworkImporter(final CommonElementImporter importer) {
		// so we need an empty interface list
		// this is a type with no external interface (currently only application)
		this(importer, LibraryElementFactory.eINSTANCE.createFBNetwork(),
				LibraryElementFactory.eINSTANCE.createInterfaceList());
	}

	public FBNetworkImporter(final CommonElementImporter importer, final FBNetwork fbNetwork,
			final InterfaceList interfaceList) {
		this(importer, fbNetwork, interfaceList, new HashMap<>());
	}

	protected FBNetworkImporter(final CommonElementImporter importer, final FBNetwork fbNetwork,
			final Map<String, FBNetworkElement> fbNetworkElementMap) {
		this(importer, fbNetwork, LibraryElementFactory.eINSTANCE.createInterfaceList(), fbNetworkElementMap);
	}

	private FBNetworkImporter(final CommonElementImporter importer, final FBNetwork fbNetwork,
			final InterfaceList interfaceList, final Map<String, FBNetworkElement> fbNetworkElementMap) {
		super(importer);
		this.fbNetwork = fbNetwork;
		this.interfaceList = interfaceList;
		fbNetwork.getNetworkElements().forEach(element -> fbNetworkElementMap.put(element.getName(), element));
		this.fbNetworkElementMap = fbNetworkElementMap;
	}

	public FBNetwork getFbNetwork() {
		return fbNetwork;
	}

	public void parseFBNetwork(final String networkNodeName) throws TypeImportException, XMLStreamException {
		processChildren(networkNodeName, this::handleFBNetworkChild);
		moveElementsToGroup();
	}

	private void moveElementsToGroup() {
		getFbNetwork().getNetworkElements().stream().forEach(el -> {
			final Attribute groupAttr = el.getAttribute(LibraryElementTags.GROUP_NAME);
			if ((groupAttr != null) && (fbNetworkElementMap.get(groupAttr.getValue()) instanceof final Group group)) {
				el.setGroup(group);
				el.deleteAttribute(LibraryElementTags.GROUP_NAME);
			}
		});
	}

	protected boolean handleFBNetworkChild(final String name) throws XMLStreamException, TypeImportException {
		switch (name) {
		case LibraryElementTags.FB_ELEMENT:
			parseFB();
			break;
		case LibraryElementTags.GROUP_ELEMENT:
			parseGroup();
			break;
		case LibraryElementTags.COMMENT_ELEMENT:
			parseComment();
			break;
		case LibraryElementTags.EVENT_CONNECTIONS_ELEMENT:
			parseConnectionList(LibraryElementPackage.eINSTANCE.getEventConnection(), fbNetwork.getEventConnections(),
					LibraryElementTags.EVENT_CONNECTIONS_ELEMENT);
			break;
		case LibraryElementTags.DATA_CONNECTIONS_ELEMENT:
			parseConnectionList(LibraryElementPackage.eINSTANCE.getDataConnection(), fbNetwork.getDataConnections(),
					LibraryElementTags.DATA_CONNECTIONS_ELEMENT);
			break;
		case LibraryElementTags.ADAPTERCONNECTIONS_ELEMENT:
			parseConnectionList(LibraryElementPackage.eINSTANCE.getAdapterConnection(),
					fbNetwork.getAdapterConnections(), LibraryElementTags.ADAPTERCONNECTIONS_ELEMENT);
			break;
		default:
			return false;
		}
		return true;
	}

	private void parseGroup() throws TypeImportException, XMLStreamException {
		final Group group = LibraryElementFactory.eINSTANCE.createGroup();
		readNameCommentAttributes(group);
		getXandY(group);

		final String width = getAttributeValue(LibraryElementTags.WIDTH_ATTRIBUTE);
		if (width != null) {
			group.setWidth(Double.parseDouble(width));
		}
		final String height = getAttributeValue(LibraryElementTags.HEIGHT_ATTRIBUTE);
		if (height != null) {
			group.setHeight(Double.parseDouble(height));
		}
		final String locked = getAttributeValue(LibraryElementTags.LOCKED_ATTRIBUTE);
		if (locked != null) {
			group.setLocked(Boolean.parseBoolean(locked));
		}

		// add FB to FBnetwork so that parameter parsing can create error markers
		// correctly.
		addFBNetworkElement(group);

		processChildren(LibraryElementTags.GROUP_ELEMENT, tagName -> {
			if (LibraryElementTags.ATTRIBUTE_ELEMENT.equals(tagName)) {
				parseGenericAttributeNode(group);
				proceedToEndElementNamed(LibraryElementTags.ATTRIBUTE_ELEMENT);
				return true;
			}
			return false;
		});
	}

	private void parseComment() throws TypeImportException, XMLStreamException {
		final Comment comment = LibraryElementFactory.eINSTANCE.createComment();
		readCommentAttribute(comment);
		getXandY(comment);

		final String width = getAttributeValue(LibraryElementTags.WIDTH_ATTRIBUTE);
		if (width != null) {
			comment.setWidth(Double.parseDouble(width));
		}
		final String height = getAttributeValue(LibraryElementTags.HEIGHT_ATTRIBUTE);
		if (height != null) {
			comment.setHeight(Double.parseDouble(height));
		}

		fbNetwork.getNetworkElements().add(comment);

		processChildren(LibraryElementTags.COMMENT_ELEMENT, name -> {
			if (LibraryElementTags.ATTRIBUTE_ELEMENT.equals(name)) {
				parseGenericAttributeNode(comment);
				proceedToEndElementNamed(LibraryElementTags.ATTRIBUTE_ELEMENT);
				return true;
			}
			return false;
		});

		proceedToEndElementNamed(LibraryElementTags.COMMENT_ELEMENT);
	}

	private void parseFB() throws TypeImportException, XMLStreamException {
		final String typeFbElement = getAttributeValue(LibraryElementTags.TYPE_ATTRIBUTE);
		final FBNetworkElement fb = createFBInstance(typeFbElement);

		readNameCommentAttributes(fb);
		getXandY(fb);

		// add FB to FBnetwork so that parameter parsing can create error markers
		// correctly.
		addFBNetworkElement(fb);

		parseFBChildren(fb, LibraryElementTags.FB_ELEMENT);
	}

	protected void addFBNetworkElement(final FBNetworkElement fb) {
		fbNetwork.getNetworkElements().add(fb);
		fbNetworkElementMap.putIfAbsent(fb.getName(), fb);
	}

	private FBNetworkElement createFBInstance(final String typeFbElement) {
		final FBTypeEntry entry = getTypeEntry(typeFbElement);
		if (null == entry) {
			return addDependency(FordiacMarkerHelper.createTypeErrorMarkerFB(typeFbElement, getTypeLibrary(),
					LibraryElementPackage.eINSTANCE.getFBType()));
		}
		final FB fb = BlockInstanceFactory.createFBInstanceForTypeEntry(entry);
		fb.setInterface(entry.getType().getInterfaceList().copy());
		fb.setTypeEntry(entry);
		return fb;
	}

	protected <T extends Connection> void parseConnectionList(final EClass conType, final EList<T> connectionlist,
			final String parentNodeName) throws XMLStreamException, TypeImportException {
		processChildren(parentNodeName, name -> {
			final T connection = parseConnection(conType);

			if (connection != null) {
				connectionlist.add(connection);
			}
			proceedToEndElementNamed(LibraryElementTags.CONNECTION_ELEMENT);
			return true;
		});
	}

	private <T extends Connection> T parseConnection(final EClass conType)
			throws XMLStreamException, TypeImportException {
		@SuppressWarnings("unchecked")
		final T connection = (T) LibraryElementFactory.eINSTANCE.create(conType);

		final String sourceElement = getAttributeValue(LibraryElementTags.SOURCE_ATTRIBUTE);
		final String destinationElement = getAttributeValue(LibraryElementTags.DESTINATION_ATTRIBUTE);

		final ConnectionBuilder builder = new ConnectionBuilder(sourceElement, destinationElement);

		final IInterfaceElement destinationEndPoint = getConnectionEndPoint(destinationElement, conType, true, builder);
		builder.setDestinationEndpoint(destinationEndPoint);

		final IInterfaceElement sourceEndPoint = getConnectionEndPoint(sourceElement, conType, false, builder);
		builder.setSourceEndpoint(sourceEndPoint);

		builder.validate();

		if (builder.isValidConnection() || builder.isDuplicate() || builder.isDataTypeMissmatch()) {
			final IInterfaceElement src = builder.getSourceEndpoint();
			final IInterfaceElement dst = builder.getDestinationEndpoint();
			connection.setSource(src);
			connection.setDestination(dst);
		}

		if (builder.isMissingConnectionDestination()) {
			handleMissingConnectionDestination(connection, builder);
		}

		if (builder.isMissingConnectionDestinationEndpoint()) {
			handleMissingConnectionDestinationEnpoint(connection, builder);
		}

		if (builder.isMissingConnectionSource()) {
			handleMissingConnectionSource(connection, builder);
		}

		if (builder.isMissingConnectionSourceEndpoint()) {
			handleMissingConnectionSourceEndpoint(connection, builder);
		}

		if (builder.isMissingSourceAndDestEndpoint()) {
			handleMissingSrcAndDestEnpoint(connection, builder);
		}

		final String commentElement = getAttributeValue(LibraryElementTags.COMMENT_ATTRIBUTE);
		if (null != commentElement) {
			connection.setComment(commentElement);
		}
		parseConnectionRouting(connection);
		parseAttributes(connection);

		if (builder.isEmptyConnection()) {
			// if 4diac should be capable of seeing the error marker in the FBNetworkEditor
			// then error marker blocks need to be created at this location
			getErrors().add(new TypeImportDiagnostic("Connection missing both source and destination element", //$NON-NLS-1$
					MessageFormat.format("{0} -> {1}", sourceElement, destinationElement), getLineNumber())); //$NON-NLS-1$
			return null;
		}

		return connection;
	}

	public <T extends Connection> void parseAttributes(final T connection)
			throws XMLStreamException, TypeImportException {
		processChildren(LibraryElementTags.CONNECTION_ELEMENT, tag -> {
			if (LibraryElementTags.ATTRIBUTE_ELEMENT.equals(tag)) {
				parseGenericAttributeNode(connection);
				proceedToEndElementNamed(LibraryElementTags.ATTRIBUTE_ELEMENT);
				return true;
			}
			return false;
		});
	}

	protected void handleMissingConnectionSource(final Connection connection, final ConnectionBuilder builder) {
		final FBNetworkElement sourceFB = FordiacMarkerHelper.createErrorMarkerFB(builder.getSourceFbName());
		builder.setSrcInterfaceList(sourceFB.getInterface());
		getFbNetwork().getNetworkElements().add(sourceFB);
		sourceFB.setName(NameRepository.createUniqueName(sourceFB, sourceFB.getName()));
		createErrorMarkerInterface(connection, builder, false);
	}

	protected static void handleMissingConnectionSourceEndpoint(final Connection connection,
			final ConnectionBuilder builder) {
		createErrorMarkerInterface(connection, builder, false);
	}

	protected void handleMissingConnectionDestination(final Connection connection, final ConnectionBuilder builder) {
		// check if there is already one
		final FBNetworkElement destinationFb = FordiacMarkerHelper.createErrorMarkerFB(builder.getDestFbName());
		builder.setDestInterfaceList(destinationFb.getInterface());
		getFbNetwork().getNetworkElements().add(destinationFb);
		destinationFb.setName(NameRepository.createUniqueName(destinationFb, destinationFb.getName()));

		createErrorMarkerInterface(connection, builder, true);
	}

	private void handleMissingSrcAndDestEnpoint(final Connection connection,
			final ConnectionBuilder connectionBuilder) {
		final DataType pinType = determineConnectionType(connection);

		final ErrorMarkerInterface srcEndpoint = FordiacErrorMarkerInterfaceHelper.createErrorMarkerInterface(pinType,
				connectionBuilder.getSourcePinName(), false, connectionBuilder.getSrcInterfaceList());
		final ErrorMarkerInterface destEndpoint = FordiacErrorMarkerInterfaceHelper.createErrorMarkerInterface(pinType,
				connectionBuilder.getDestinationPinName(), true, connectionBuilder.getDestInterfaceList());
		connection.setSource(srcEndpoint);
		connection.setDestination(destEndpoint);
	}

	private DataType determineConnectionType(final Connection connection) {
		if (connection instanceof EventConnection) {
			return EventTypeLibrary.getInstance().getType(null);
		}
		if (connection instanceof AdapterConnection) {
			final AdapterTypeEntry entry = getTypeLibrary().getAdapterTypeEntry("ANY_ADAPTER"); //$NON-NLS-1$
			if (null != entry) {
				return entry.getType();
			}
			// we don't have an any_adapter return generic adapter, may not be reparable
			return LibraryElementFactory.eINSTANCE.createAdapterType();
		}
		if (connection instanceof DataConnection) {
			return IecTypes.GenericTypes.ANY;
		}
		return null;
	}

	private static ErrorMarkerInterface createErrorMarkerInterface(final Connection connection,
			final ConnectionBuilder connectionBuilder, final boolean isInput) {
		final IInterfaceElement oppositeEndpoint = isInput ? connectionBuilder.getSourceEndpoint()
				: connectionBuilder.getDestinationEndpoint();

		// we need a special treatment for FB's that lost their type
		if ((oppositeEndpoint == null)) {
			connectionBuilder.getConnectionState().add(ConnectionState.MISSING_TYPE);
			return null;
		}

		DataType type = oppositeEndpoint.getType();
		if (type == null) {
			type = IecTypes.GenericTypes.ANY;
		}

		final InterfaceList ieList = isInput ? connectionBuilder.getDestInterfaceList()
				: connectionBuilder.getSrcInterfaceList();
		final String pinName = isInput ? connectionBuilder.getDestinationPinName()
				: connectionBuilder.getSourcePinName();
		final ErrorMarkerInterface errorMarkerInterface = FordiacErrorMarkerInterfaceHelper
				.createErrorMarkerInterface(type, pinName, isInput, ieList);

		if (isInput) {
			connection.setSource(oppositeEndpoint);
			connection.setDestination(errorMarkerInterface);
		} else {
			connection.setSource(errorMarkerInterface);
			connection.setDestination(oppositeEndpoint);
		}
		return errorMarkerInterface;
	}

	protected static <T extends Connection> void handleMissingConnectionDestinationEnpoint(final T connection,
			final ConnectionBuilder builder) {
		createErrorMarkerInterface(connection, builder, true);
	}

	private void parseConnectionRouting(final Connection connection) {
		final ConnectionRoutingData routingData = LibraryElementFactory.eINSTANCE.createConnectionRoutingData();
		final String dx1Element = getAttributeValue(LibraryElementTags.DX1_ATTRIBUTE);
		if (null != dx1Element) {
			routingData.setDx1(parseConnectionValue(dx1Element));
		}
		final String dx2Element = getAttributeValue(LibraryElementTags.DX2_ATTRIBUTE);
		if (null != dx2Element) {
			routingData.setDx2(parseConnectionValue(dx2Element));
		}
		final String dyElement = getAttributeValue(LibraryElementTags.DY_ATTRIBUTE);
		if (null != dyElement) {
			routingData.setDy(parseConnectionValue(dyElement));
		}
		connection.setRoutingData(routingData);
	}

	private IInterfaceElement getConnectionEndPoint(final String path, final EClass conType, final boolean isInput,
			final ConnectionBuilder builder) {
		if (path == null) {
			return null;
		}
		int separatorPos = path.indexOf('.');

		if (separatorPos == -1) {
			// we have a connection to the containing interface
			if (isInput) {
				builder.setDestInterfaceList(interfaceList);
			} else {
				builder.setSrcInterfaceList(interfaceList);
			}
			return getContainingInterfaceElement(path, conType, isInput);
		}

		String elementName = path.substring(0, separatorPos);
		FBNetworkElement element = findFBNetworkElement(elementName);
		while (element == null && separatorPos != -1) {
			separatorPos = path.indexOf('.', separatorPos + 1);
			elementName = path.substring(0, separatorPos);
			element = findFBNetworkElement(elementName);
		}

		if (null != element) {
			final InterfaceList ieList = element.getInterface();
			if (isInput) {
				builder.setDestInterfaceList(ieList);
			} else {
				builder.setSrcInterfaceList(ieList);
			}
			final String pinName = path.substring(separatorPos + 1);
			return getInterfaceElement(ieList, pinName, conType, isInput);
		}
		return null;
	}

	/**
	 * Check if the element that contains the fbnetwork has an interface element
	 * with the given name. this is needed for subapps, cfbs, devices and resources
	 */
	protected IInterfaceElement getContainingInterfaceElement(final String interfaceElement, final EClass conType,
			final boolean isInput) {
		// for connections to the interface inputs are the outputs of the FB
		return getInterfaceElement(interfaceList, interfaceElement, conType, !isInput);
	}

	private static IInterfaceElement getInterfaceElement(final InterfaceList il, final String interfaceElement,
			final EClass conType, final boolean isInput) {
		final Stream<? extends IInterfaceElement> ies = getInterfaceElementList(il, conType, isInput);
		return ies.filter(ie -> ie.getName().equals(interfaceElement)).findAny().orElse(null);
	}

	private static Stream<? extends IInterfaceElement> getInterfaceElementList(final InterfaceList il,
			final EClass conType, final boolean isInput) {
		if (isInput) {
			if (LibraryElementPackage.eINSTANCE.getEventConnection() == conType) {
				return il.getEventInputs().stream();
			}
			if (LibraryElementPackage.eINSTANCE.getDataConnection() == conType) {
				return Stream.concat(il.getInputVars().stream(), il.getInOutVars().stream());
			}
			if (LibraryElementPackage.eINSTANCE.getAdapterConnection().equals(conType)) {
				return il.getSockets().stream();
			}
		} else {
			if (LibraryElementPackage.eINSTANCE.getEventConnection() == conType) {
				return il.getEventOutputs().stream();
			}
			if (LibraryElementPackage.eINSTANCE.getDataConnection() == conType) {
				return Stream.concat(il.getOutputVars().stream(), il.getOutMappedInOutVars().stream());
			}
			if (LibraryElementPackage.eINSTANCE.getAdapterConnection().equals(conType)) {
				return il.getPlugs().stream();
			}
		}
		return Stream.empty();
	}

	protected FBNetworkElement findFBNetworkElement(final String fbName) {
		return fbNetworkElementMap.get(fbName);
	}

	protected static VarDeclaration getVarNamed(final InterfaceList interfaceList, final String varName,
			final boolean input) {
		VarDeclaration retVal;
		boolean hasType = true;

		if (interfaceList.eContainer() instanceof final FB fb) {
			// only if it is an FB check if it is typed
			hasType = (null != fb.getTypeEntry());
		}

		if (hasType) {
			// we have a typed FB
			retVal = interfaceList.getVariable(varName);
			if ((null != retVal) && (retVal.isIsInput() != input)) {
				retVal = null;
			}
		} else {
			// if we couldn't load the type create the interface entry
			retVal = createVarDecl(interfaceList, varName, input);
		}
		return retVal;
	}

	private static VarDeclaration createVarDecl(final InterfaceList interfaceList, final String varName,
			final boolean input) {
		final VarDeclaration variable = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		variable.setName(varName);
		variable.setIsInput(input);
		if (input) {
			interfaceList.getInputVars().add(variable);
		} else {
			interfaceList.getOutputVars().add(variable);
		}

		return variable;
	}

	/**
	 * returns an valid dx, dy integer value
	 *
	 * @param value
	 * @return if value is valid the converted int of that otherwise 0
	 */
	private static double parseConnectionValue(final String value) {
		try {
			return Double.parseDouble(value);
		} catch (final NumberFormatException ex) {
			return 0;
		}
	}

	@Override
	protected LibraryElement createRootModelElement() {
		// Nothing to be done for FBNetworks
		return null;
	}

	@Override
	protected String getStartElementName() {
		// Nothing to be done for FBNetworks
		return null;
	}

	@Override
	protected IChildHandler getBaseChildrenHandler() {
		// Nothing to be done for FBNetworks
		return null;
	}

	protected <T extends FBNetworkElement> T addDependency(final T element) {
		if (element != null) {
			addDependency(element.getTypeEntry());
		}
		return element;
	}
}
