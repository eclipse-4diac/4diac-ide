/********************************************************************************
 * Copyright (c) 2016, 2020 fortiss GmbH, Johannes Kepler University, Linz,
 *               2020, 2021, 2022 Primetals Technologies Austria GmbH
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
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.dataimport.ConnectionHelper.ConnectionBuilder;
import org.eclipse.fordiac.ide.model.dataimport.ConnectionHelper.ConnectionState;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.helpers.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerRef;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.AdapterTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;

class FBNetworkImporter extends CommonElementImporter {

	private final FBNetwork fbNetwork;
	// this is the interface list needed for checking connection to the containg
	// types interface
	private final InterfaceList interfaceList;

	protected final Map<String, FBNetworkElement> fbNetworkElementMap = new HashMap<>();

	public FBNetworkImporter(final CommonElementImporter importer) {
		// so we need an empty interface list
		// this is a type with no external interface (currently only application)
		this(importer, LibraryElementFactory.eINSTANCE.createFBNetwork(),
				LibraryElementFactory.eINSTANCE.createInterfaceList());
	}

	public FBNetworkImporter(final CommonElementImporter importer, final FBNetwork fbNetwork,
			final InterfaceList interfaceList) {
		super(importer);
		this.fbNetwork = fbNetwork;
		this.interfaceList = interfaceList;
		fbNetwork.getNetworkElements().forEach(element -> fbNetworkElementMap.put(element.getName(), element));
	}

	protected FBNetworkImporter(final CommonElementImporter importer, final FBNetwork fbNetwork) {
		this(importer, fbNetwork, LibraryElementFactory.eINSTANCE.createInterfaceList());
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
			if (groupAttr != null) {
				final FBNetworkElement group = fbNetworkElementMap.get(groupAttr.getValue());
				if (group instanceof Group) {
					el.setGroup((Group) group);
					el.deleteAttribute(LibraryElementTags.GROUP_NAME);
				}
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
		group.setInterface(LibraryElementFactory.eINSTANCE.createInterfaceList());
		readNameCommentAttributes(group);
		getXandY(group);

		final String width = getAttributeValue(LibraryElementTags.WIDTH_ATTRIBUTE);
		if (width != null) {
			group.setWidth(CoordinateConverter.INSTANCE.convertFrom1499XML(width));
		}
		final String height = getAttributeValue(LibraryElementTags.HEIGHT_ATTRIBUTE);
		if (height != null) {
			group.setHeight(CoordinateConverter.INSTANCE.convertFrom1499XML(height));
		}

		// add FB to FBnetwork so that parameter parsing can create error markers correctly.
		fbNetwork.getNetworkElements().add(group);
		fbNetworkElementMap.put(group.getName(), group);
		proceedToEndElementNamed(LibraryElementTags.GROUP_ELEMENT);
	}

	private void parseFB() throws TypeImportException, XMLStreamException {
		final String typeFbElement = getAttributeValue(LibraryElementTags.TYPE_ATTRIBUTE);
		final FBNetworkElement fb = createFBInstance(typeFbElement);

		readNameCommentAttributes(fb);
		getXandY(fb);

		// add FB to FBnetwork so that parameter parsing can create error markers correctly.
		fbNetwork.getNetworkElements().add(fb);
		fbNetworkElementMap.put(fb.getName(), fb);

		parseFBChildren(fb, LibraryElementTags.FB_ELEMENT);

		if ((null == fb.getTypeEntry()) || (fb instanceof ErrorMarkerRef)) {
			// we don't have a type create error marker.
			// This can only be done after fb has been added to FB network,
			// so that the error marker can determine the location!
			final ErrorMarkerBuilder e = ErrorMarkerBuilder.createErrorMarkerBuilder(
					MessageFormat.format("Type ({0}) could not be loaded for FB: {1}", typeFbElement, fb.getName()), //$NON-NLS-1$
					fb, getLineNumber());
			e.setErrorMarkerRef((ErrorMarkerRef) fb);
			errorMarkerAttributes.add(e);
		}
	}

	private FBNetworkElement createFBInstance(final String typeFbElement) {
		FB fb = LibraryElementFactory.eINSTANCE.createFB();
		final FBTypeEntry entry = getTypeEntry(typeFbElement);

		if (null != entry) {
			final FBType type = entry.getType();
			if (type instanceof CompositeFBType) {
				fb = LibraryElementFactory.eINSTANCE.createCFBInstance();
			} else {
				if ("STRUCT_MUX".equals(type.getName())) { //$NON-NLS-1$
					fb = LibraryElementFactory.eINSTANCE.createMultiplexer();
				} else if ("STRUCT_DEMUX".equals(type.getName())) { //$NON-NLS-1$
					fb = LibraryElementFactory.eINSTANCE.createDemultiplexer();
				}
			}
			fb.setInterface(type.getInterfaceList().copy());
		} else {
			return FordiacMarkerHelper.createTypeErrorMarkerFB(typeFbElement, getTypeLibrary(),
					LibraryElementFactory.eINSTANCE.createFBType());
		}
		fb.setTypeEntry(entry);
		return fb;
	}

	protected <T extends Connection> void parseConnectionList(final EClass conType, final EList<T> connectionlist,
			final String parentNodeName) throws XMLStreamException, TypeImportException {
		processChildren(parentNodeName, name -> {
			final T connection = parseConnection(conType);
			if (null != connection) {
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
		connection.setResTypeConnection(false);

		final String sourceElement = getAttributeValue(LibraryElementTags.SOURCE_ATTRIBUTE);
		final String destinationElement = getAttributeValue(LibraryElementTags.DESTINATION_ATTRIBUTE);

		final ConnectionBuilder builder = new ConnectionBuilder(sourceElement, destinationElement);

		final IInterfaceElement destinationEndPoint = getConnectionEndPoint(destinationElement, conType, true, builder);
		builder.setDestinationEndpoint(destinationEndPoint);

		final IInterfaceElement sourceEndPoint = getConnectionEndPoint(sourceElement, conType, false, builder);
		builder.setSourceEndpoint(sourceEndPoint);

		builder.validate();

		if (builder.isValidConnection()) {
			final IInterfaceElement src = builder.getSourceEndpoint();
			final IInterfaceElement dst = builder.getDestinationEndpoint();
			connection.setSource(src);
			connection.setDestination(dst);
		}

		if (builder.isDataTypeMissmatch()) {
			handleDataTypeMissmatch(builder, connection);
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

		if (builder.getConnectionState().contains(ConnectionState.MISSING_TYPE)) {
			return null;
		}

		final String commentElement = getAttributeValue(LibraryElementTags.COMMENT_ATTRIBUTE);
		if (null != commentElement) {
			connection.setComment(commentElement);
		}
		parseConnectionRouting(connection);
		parseAttributes(connection);
		return connection;
	}

	public <T extends Connection> void handleDataTypeMissmatch(final ConnectionBuilder builder,
			final T connection) {
		final IInterfaceElement src = builder.getSourceEndpoint();
		final IInterfaceElement dst = builder.getDestinationEndpoint();
		final String errorMessage = MessageFormat.format(Messages.FBNetworkImporter_ConnectionTypeMismatch,
				src.getName() + ":" + src.getTypeName(), dst.getName() + ":" + dst.getTypeName()); //$NON-NLS-1$ //$NON-NLS-2$
		final ErrorMarkerInterface srcErrorMarker = FordiacMarkerHelper.createWrongDataTypeMarker(src, src,
				src.getFBNetworkElement(), new ArrayList<>(), src.getName() + ":" + src.getTypeName()); //$NON-NLS-1$
		final ErrorMarkerInterface dstErrorMarker = FordiacMarkerHelper.createWrongDataTypeMarker(dst, dst,
				dst.getFBNetworkElement(), new ArrayList<>(), dst.getName() + ":" + dst.getTypeName()); //$NON-NLS-1$

		final ErrorMarkerBuilder errorMarkerBuilder = FordiacMarkerHelper.createConnectionErrorMarkerBuilder(
				errorMessage,
				getFbNetwork(), builder.getSourcePinName(),
				builder.getDestinationPinName(), getLineNumber());
		connection.setErrorMessage(errorMessage);
		errorMarkerBuilder.setErrorMarkerRef(connection);
		errorMarkerAttributes.add(errorMarkerBuilder);
		connection.setSource(srcErrorMarker);
		connection.setDestination(dstErrorMarker);
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
		final ErrorMarkerBuilder e = FordiacMarkerHelper.createConnectionErrorMarkerBuilder(
				Messages.FBNetworkImporter_ConnectionSourceMissing, getFbNetwork(), builder.getSource(),
				builder.getDestination(), getLineNumber());
		errorMarkerAttributes.add(e);
		final FBNetworkElement sourceFB = FordiacMarkerHelper.createErrorMarkerFB(builder.getSourceFbName());
		builder.setSrcInterfaceList(sourceFB.getInterface());
		getFbNetwork().getNetworkElements().add(sourceFB);
		sourceFB.setName(NameRepository.createUniqueName(sourceFB, sourceFB.getName()));
		createErrorMarkerInterface(connection, builder, false, e);

	}

	protected void handleMissingConnectionSourceEndpoint(final Connection connection, final ConnectionBuilder builder) {
		final ErrorMarkerBuilder e = FordiacMarkerHelper.createConnectionErrorMarkerBuilder(
				MessageFormat.format(Messages.FBNetworkImporter_ConnectionSourceNotFound, builder.getSource()),
				getFbNetwork(), builder.getSource(), builder.getDestination(), getLineNumber());
		errorMarkerAttributes.add(e);
		createErrorMarkerInterface(connection, builder, false, e);
	}

	protected void handleMissingConnectionDestination(final Connection connection,
			final ConnectionBuilder connectionBuilder) {

		final ErrorMarkerBuilder e = FordiacMarkerHelper.createConnectionErrorMarkerBuilder(
				Messages.FBNetworkImporter_ConnectionDestinationMissing, getFbNetwork(),
				connectionBuilder.getSource(), null, getLineNumber());
		errorMarkerAttributes.add(e);
		// check if there is already one
		final FBNetworkElement destinationFb = FordiacMarkerHelper
				.createErrorMarkerFB(connectionBuilder.getDestFbName());
		connectionBuilder.setDestInterfaceList(destinationFb.getInterface());
		getFbNetwork().getNetworkElements().add(destinationFb);
		destinationFb.setName(NameRepository.createUniqueName(destinationFb, destinationFb.getName()));
		createErrorMarkerInterface(connection, connectionBuilder, true,e);
	}

	private void handleMissingSrcAndDestEnpoint(final Connection connection,
			final ConnectionBuilder connectionBuilder) {
		final DataType pinType = determineConnectionType(connection);

		final ErrorMarkerInterface srcEndpoint = ConnectionHelper.createErrorMarkerInterface(pinType,
				connectionBuilder.getSourcePinName(), false, connectionBuilder.getSrcInterfaceList());
		final ErrorMarkerInterface destEndpoint = ConnectionHelper.createErrorMarkerInterface(pinType,
				connectionBuilder.getDestinationPinName(), true, connectionBuilder.getDestInterfaceList());
		createMissingErrorMarker(connectionBuilder, srcEndpoint, Messages.FBNetworkImporter_ConnectionSourceNotFound);
		createMissingErrorMarker(connectionBuilder, destEndpoint,
				Messages.FBNetworkImporter_ConnectionDestinationNotFound);
		connection.setSource(srcEndpoint);
		connection.setDestination(destEndpoint);
	}

	private DataType determineConnectionType(final Connection connection) {
		if(connection instanceof EventConnection) {
			return EventTypeLibrary.getInstance().getType(null);
		} else if(connection instanceof AdapterConnection) {
			final AdapterTypeEntry entry = getTypeLibrary().getAdapterTypeEntry("ANY_ADAPTER"); //$NON-NLS-1$
			if (null != entry) {
				return entry.getType();
			}
			// we don't have an any_adapter return generic adapter, may not be reparable
			return LibraryElementFactory.eINSTANCE.createAdapterType();
		} else if (connection instanceof DataConnection) {
			return IecTypes.GenericTypes.ANY;
		}
		return null;
	}

	private void createMissingErrorMarker(final ConnectionBuilder builder, final ErrorMarkerInterface endpoint,
			final String errorMsg) {
		if (errorMarkerAttributes.stream().noneMatch(e -> e.getErrorMarkerRef().equals(endpoint))) {
			// we don't have yet an error marker
			final ErrorMarkerBuilder e = FordiacMarkerHelper.createConnectionErrorMarkerBuilder(
					MessageFormat.format(errorMsg, builder.getSource()), getFbNetwork(),
					builder.getSource(), builder.getDestination(), getLineNumber());
			e.setErrorMarkerRef(endpoint);
			errorMarkerAttributes.add(e);
		}
	}

	private static void createErrorMarkerInterface(final Connection connection,
			final ConnectionBuilder connectionBuilder, final boolean isInput, final ErrorMarkerBuilder e) {



		final IInterfaceElement oppositeEndpoint = isInput ? connectionBuilder.getSourceEndpoint()
				: connectionBuilder.getDestinationEndpoint();



		// we need a special treatment for FB's that lost their type
		if ((oppositeEndpoint == null)) {
			connectionBuilder.getConnectionState().add(ConnectionState.MISSING_TYPE);
			return;
		}

		DataType type = oppositeEndpoint.getType();
		if (type == null) {
			type = IecTypes.GenericTypes.ANY;
		}

		final InterfaceList ieList = isInput ? connectionBuilder.getDestInterfaceList()
				: connectionBuilder.getSrcInterfaceList();
		final String pinName = isInput ? connectionBuilder.getDestinationPinName()
				: connectionBuilder.getSourcePinName();
		final ErrorMarkerInterface errorMarkerInterface = ConnectionHelper
				.createErrorMarkerInterface(type,
						pinName, isInput, ieList);
		e.setErrorMarkerRef(errorMarkerInterface);
		final IInterfaceElement repairedEndpoint = ConnectionHelper.createRepairInterfaceElement(oppositeEndpoint,
				pinName);
		if (repairedEndpoint != null) {
			errorMarkerInterface.setRepairedEndpoint(repairedEndpoint);
		}


		if (isInput) {
			connection.setSource(oppositeEndpoint);
			connection.setDestination(errorMarkerInterface);
		} else {
			connection.setSource(errorMarkerInterface);
			connection.setDestination(oppositeEndpoint);
		}
	}

	protected <T extends Connection> void handleMissingConnectionDestinationEnpoint(final T connection,
			final ConnectionBuilder builder) {
		final ErrorMarkerBuilder e = FordiacMarkerHelper.createConnectionErrorMarkerBuilder(
				MessageFormat.format(Messages.FBNetworkImporter_ConnectionDestinationNotFound,
						builder.getDestination()),
				getFbNetwork(), builder.getSource(), builder.getDestination(), getLineNumber());
		errorMarkerAttributes.add(e);
		createErrorMarkerInterface(connection, builder, true, e);
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
		final String[] split = path.split("\\."); //$NON-NLS-1$

		if (1 == split.length) {
			if (isInput) {
				builder.setDestInterfaceList(interfaceList);
			} else {
				builder.setSrcInterfaceList(interfaceList);
			}

			return getContainingInterfaceElement(path, conType, isInput);
		}
		if (split.length >= 2) {
			final FBNetworkElement element = findFBNetworkElement(split[0]);
			if (null != element) {
				final InterfaceList ieList = element.getInterface();
				if (isInput) {
					builder.setDestInterfaceList(ieList);
				} else {
					builder.setSrcInterfaceList(ieList);
				}
				return getInterfaceElement(ieList, path.substring(split[0].length() + 1), conType, isInput);
			}
		}
		return null;
	}

	/** Check if the element that contains the fbnetwork has an interface element with the given name. this is needed
	 * for subapps, cfbs, devices and resources */
	protected IInterfaceElement getContainingInterfaceElement(final String interfaceElement, final EClass conType,
			final boolean isInput) {
		return getInterfaceElement(interfaceList, interfaceElement, conType, !isInput); // for connections to the
		// interface inputs are the
		// outputs of the FB
	}

	private static IInterfaceElement getInterfaceElement(final InterfaceList il, final String interfaceElement,
			final EClass conType, final boolean isInput) {
		final EList<? extends IInterfaceElement> ieList = getInterfaceElementList(il, conType, isInput);
		for (final IInterfaceElement ie : ieList) {
			if (ie.getName().equals(interfaceElement)) {
				return ie;
			}
		}
		return null;
	}

	private static EList<? extends IInterfaceElement> getInterfaceElementList(final InterfaceList il,
			final EClass conType, final boolean isInput) {
		if (isInput) {
			if (LibraryElementPackage.eINSTANCE.getEventConnection() == conType) {
				return il.getEventInputs();
			}
			if (LibraryElementPackage.eINSTANCE.getDataConnection() == conType) {
				return il.getInputVars();
			}
			if (LibraryElementPackage.eINSTANCE.getAdapterConnection().equals(conType)) {
				return il.getSockets();
			}
		} else {
			if (LibraryElementPackage.eINSTANCE.getEventConnection() == conType) {
				return il.getEventOutputs();
			}
			if (LibraryElementPackage.eINSTANCE.getDataConnection() == conType) {
				return il.getOutputVars();
			}
			if (LibraryElementPackage.eINSTANCE.getAdapterConnection().equals(conType)) {
				return il.getPlugs();
			}
		}
		return ECollections.emptyEList();
	}

	protected FBNetworkElement findFBNetworkElement(final String fbName) {
		return fbNetworkElementMap.get(fbName);
	}

	protected static VarDeclaration getVarNamed(final InterfaceList interfaceList, final String varName,
			final boolean input) {
		VarDeclaration retVal;
		boolean hasType = true;

		if (interfaceList.eContainer() instanceof FB) {
			// only if it is an FB check if it is typed
			hasType = (null != ((FB) interfaceList.eContainer()).getTypeEntry());
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

	/** returns an valid dx, dy integer value
	 *
	 * @param value
	 * @return if value is valid the converted int of that otherwise 0 */
	private static int parseConnectionValue(final String value) {
		try {
			return CoordinateConverter.INSTANCE.convertFrom1499XML(value);
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

}
