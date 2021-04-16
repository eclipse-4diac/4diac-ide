/********************************************************************************
 * Copyright (c) 2016, 2020 fortiss GmbH, Johannes Kepler University, Linz,
 *               2020, 2021 Primetals Technologies Austria GmbH
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
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.dataimport.ConnectionHelper.ConnectionBuilder;
import org.eclipse.fordiac.ide.model.dataimport.ConnectionHelper.ConnectionState;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.helpers.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

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
	}

	protected boolean handleFBNetworkChild(final String name) throws XMLStreamException, TypeImportException {
		switch (name) {
		case LibraryElementTags.FB_ELEMENT:
			parseFB();
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

	protected void parseFB() throws TypeImportException, XMLStreamException {
		final String typeFbElement = getAttributeValue(LibraryElementTags.TYPE_ATTRIBUTE);
		final FB fb = createFBInstance(typeFbElement);

		readNameCommentAttributes(fb);
		getXandY(fb);
		parseFBChildren(fb, LibraryElementTags.FB_ELEMENT);

		fbNetwork.getNetworkElements().add(fb);
		fbNetworkElementMap.put(fb.getName(), fb);

		if (null == fb.getPaletteEntry()) {
			// we don't have a type create error marker.
			// This can only be done after fb has been added to FB network,
			// so that the error marker can determine the location!
			createErrorMarker(
					MessageFormat.format("Type ({0}) could not be loaded for FB: {1}", typeFbElement, fb.getName()), //$NON-NLS-1$
					fb);
		}

		if (fb instanceof StructManipulator) {
			final Attribute attr = fb.getAttribute("StructuredType"); //$NON-NLS-1$
			final StructuredType structType = getTypeLibrary().getDataTypeLibrary().getStructuredType(attr.getValue());
			((StructManipulator) fb).setStructTypeElementsAtInterface(structType);
		}
	}

	private FB createFBInstance(final String typeFbElement) {
		FB fb = LibraryElementFactory.eINSTANCE.createFB();
		final FBTypePaletteEntry entry = getTypeEntry(typeFbElement);

		if (null != entry) {
			final FBType type = entry.getFBType();
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
			// as we don't have type information we create an empty interface list
			fb.setInterface(LibraryElementFactory.eINSTANCE.createInterfaceList());
			// TODO add attribute value for missing instance name and
			// indicate that FB is missing for usage in outline views
		}
		fb.setPaletteEntry(entry);
		return fb;
	}

	@Override
	protected void parseFBChildren(final FBNetworkElement block, final String parentNodeName)
			throws TypeImportException, XMLStreamException {
		processChildren(parentNodeName, name -> {
			switch (name) {
			case LibraryElementTags.PARAMETER_ELEMENT:
				final VarDeclaration parameter = parseParameter();
				final VarDeclaration vInput = getVarNamed(block.getInterface(), parameter.getName(), true);
				if (null != vInput) {
					vInput.setValue(parameter.getValue());
				}
				return true;
			case LibraryElementTags.ATTRIBUTE_ELEMENT:
				parseGenericAttributeNode(block);
				proceedToEndElementNamed(LibraryElementTags.ATTRIBUTE_ELEMENT);
				return true;
			default:
				return false;
			}
		});
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

	private <T extends Connection> T parseConnection(final EClass conType) {
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
			connection.setSource(builder.getSourceEndpoint());
			connection.setDestination(builder.getDestinationEndpoint());
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

		return connection;
	}

	protected <T extends Connection> void handleMissingConnectionSource(final T connection,
			final ConnectionBuilder builder) {
		final ErrorMarkerAttribute e = createConnectionErrorMarker(Messages.FBNetworkImporter_ConnectionSourceMissing,
				getFbNetwork(),
				builder.getSource(), builder.getDestination());
		final FBNetworkElement sourceFB = ConnectionHelper.createErrorMarkerFB(builder.getSourceFbName());
		builder.setSrcInterfaceList(sourceFB.getInterface());
		getFbNetwork().getNetworkElements().add(sourceFB);
		createErrorMarkerInterface(connection, builder, false, e);

	}

	protected void handleMissingConnectionSourceEndpoint(final Connection connection, final ConnectionBuilder builder) {
		final ErrorMarkerAttribute e = createConnectionErrorMarker(
				Messages.FBNetworkImporter_ConnectionSourceNotFound + builder.getSource(),
				getFbNetwork(), builder.getSource(), builder.getDestination());
		createErrorMarkerInterface(connection, builder, false, e);
	}

	protected <T extends Connection> void handleMissingConnectionDestination(final Connection connection,
			final ConnectionBuilder connectionBuilder) {

		final ErrorMarkerAttribute e = createConnectionErrorMarker(Messages.FBNetworkImporter_ConnectionDestinationMissing, getFbNetwork(),
				connectionBuilder.getSource(), null);

		// check if there is already one
		final FBNetworkElement destinationFb = ConnectionHelper.createErrorMarkerFB(connectionBuilder.getDestFbName());
		connectionBuilder.setDestInterfaceList(destinationFb.getInterface());
		getFbNetwork().getNetworkElements().add(destinationFb);
		createErrorMarkerInterface(connection, connectionBuilder, true,e);
	}

	private static void handleMissingSrcAndDestEnpoint(final Connection connection,
			final ConnectionBuilder connectionBuilder) {
		final ErrorMarkerInterface srcEndpoint = ConnectionHelper.createErrorMarkerInterface(IecTypes.GenericTypes.ANY,
				connectionBuilder.getSourcePinName(), false);
		final IInterfaceElement destEnpoint = ConnectionHelper.createErrorMarkerInterface(IecTypes.GenericTypes.ANY,
				connectionBuilder.getDestinationPinName(), true);

		connectionBuilder.getSrcInterfaceList().getErrorMarker().add(srcEndpoint);
		connectionBuilder.getDestInterfaceList().getErrorMarker().add(destEnpoint);

		connection.setSource(srcEndpoint);
		connection.setDestination(destEnpoint);

	}

	private static void createErrorMarkerInterface(final Connection connection,
			final ConnectionBuilder connectionBuilder, final boolean isInput, final ErrorMarkerAttribute e) {

		final String pinName = isInput ? connectionBuilder.getDestinationPinName()
				: connectionBuilder.getSourcePinName();

		final IInterfaceElement oppositeEndpoint = isInput ? connectionBuilder.getSourceEndpoint()
				: connectionBuilder.getDestinationEndpoint();

		final InterfaceList ieList = isInput ? connectionBuilder.getDestInterfaceList()
				: connectionBuilder.getSrcInterfaceList();

		// we need a special treatment for FB's that lost their type
		if ((oppositeEndpoint == null)) {
			connectionBuilder.getConnectionState().add(ConnectionState.MISSING_TYPE);
			return;
		}

		final ErrorMarkerInterface errorMarkerInterface = ConnectionHelper
				.createErrorMarkerInterface(oppositeEndpoint.getType(),
						pinName, isInput);
		e.setErrorMarkerIe(errorMarkerInterface);
		final IInterfaceElement repairedEndpoint = ConnectionHelper.createRepairInterfaceElement(oppositeEndpoint,
				pinName);
		if (repairedEndpoint != null) {
			errorMarkerInterface.setRepairedEndpoint(repairedEndpoint);
		}

		ieList.getErrorMarker().add(errorMarkerInterface);

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
		final ErrorMarkerAttribute e = createConnectionErrorMarker(
				Messages.FBNetworkImporter_ConnectionDestinationNotFound + builder.getDestination(),
				getFbNetwork(), builder.getSource(), builder.getDestination());
		createErrorMarkerInterface(connection, builder, true, e);
	}

	private ErrorMarkerAttribute createConnectionErrorMarker(final String message, final FBNetwork fbNetwork,
			final String sourceIdentifier, final String destinationIdentifier) {
		final Map<String, Object> attrs = new HashMap<>();
		attrs.put(IMarker.MESSAGE, message);

		// use a dummy connection to get target identifier
		FordiacMarkerHelper.addTargetIdentifier(LibraryElementFactory.eINSTANCE.createDataConnection(), attrs);
		final String location = FordiacMarkerHelper.getLocation(fbNetwork) + "." + sourceIdentifier + " -> " //$NON-NLS-1$ //$NON-NLS-2$
				+ destinationIdentifier;
		attrs.put(IMarker.LOCATION, location);
		return createErrorMarkerAtrribute(attrs);
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
		return null;
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
			hasType = (null != ((FB) interfaceList.eContainer()).getPaletteEntry());
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
		final VarDeclaration var = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		var.setName(varName);
		var.setIsInput(input);
		if (input) {
			interfaceList.getInputVars().add(var);
		} else {
			interfaceList.getOutputVars().add(var);
		}
		return var;
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
