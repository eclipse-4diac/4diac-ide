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
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.helpers.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
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
		FB fb = LibraryElementFactory.eINSTANCE.createFB();

		readNameCommentAttributes(fb);

		final String typeFbElement = getAttributeValue(LibraryElementTags.TYPE_ATTRIBUTE);
		final FBTypePaletteEntry entry = getTypeEntry(typeFbElement);

		if (null != entry) {
			fb.setPaletteEntry(entry);
			fb.setInterface(fb.getType().getInterfaceList().copy());
			if ("STRUCT_MUX".equals(fb.getType().getName())) { //$NON-NLS-1$
				final Multiplexer mux = LibraryElementFactory.eINSTANCE.createMultiplexer();
				fb = convertFBtoMux(fb, mux);
			} else if ("STRUCT_DEMUX".equals(fb.getType().getName())) { //$NON-NLS-1$
				final Demultiplexer demux = LibraryElementFactory.eINSTANCE.createDemultiplexer();
				fb = convertFBtoMux(fb, demux);
			}
		} else {
			// add it to the fbnetwork so that the error marker can determine the location
			fbNetwork.getNetworkElements().add(fb);
			createErrorMarker(
					MessageFormat.format("Type ({0}) could not be loaded for FB: {1}", typeFbElement, fb.getName()),
					fb);

			// as we don't have type information we create an empty interface list
			fb.setInterface(LibraryElementFactory.eINSTANCE.createInterfaceList());
			// TODO add attribute value for missing instance name and
			// indicate that FB is missing for usage in outline views
		}


		getXandY(fb);

		parseFBChildren(fb, LibraryElementTags.FB_ELEMENT);

		fbNetwork.getNetworkElements().add(fb);
		fbNetworkElementMap.put(fb.getName(), fb);

		if (fb instanceof StructManipulator) {
			final Attribute attr = fb.getAttribute("StructuredType"); //$NON-NLS-1$
			final StructuredType structType = getTypeLibrary().getDataTypeLibrary().getStructuredType(attr.getValue());
			((StructManipulator) fb).setStructTypeElementsAtInterface(structType);
		}
	}


	private static FB convertFBtoMux(final FB fb, final StructManipulator mux) {
		mux.setName(fb.getName());
		mux.setComment(fb.getComment());
		mux.setPosition(fb.getPosition());
		mux.setPaletteEntry(fb.getPaletteEntry());
		mux.setInterface(fb.getInterface());
		return mux;
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

		final String destinationElement = getAttributeValue(LibraryElementTags.DESTINATION_ATTRIBUTE);
		final String sourceElement = getAttributeValue(LibraryElementTags.SOURCE_ATTRIBUTE);
		if (null != destinationElement) {
			final IInterfaceElement destination = getConnectionEndPoint(destinationElement, conType, true);
			if (null != destination) {
				connection.setDestination(destination);
			} else {
				// TODO model refactoring - this connection is missing an end point. Add a dummy
				// connection points so
				// that the connection can be handled in the according FBNetorwk editor
				createConnectionErrorMarker("Connection destination not found: " + destinationElement, getFbNetwork(),
						sourceElement, destinationElement);
				return null;
			}
		} else {
			createConnectionErrorMarker("Connection destination missing!", getFbNetwork(), sourceElement,
					destinationElement);
		}
		if (null != sourceElement) {
			final IInterfaceElement source = getConnectionEndPoint(sourceElement, conType, false);
			if (null != source) {
				connection.setSource(source);
			} else {
				createConnectionErrorMarker("Connection source not found: " + sourceElement, getFbNetwork(),
						sourceElement, destinationElement);
				return null;
			}
		} else {
			createConnectionErrorMarker("Connection source missing!", getFbNetwork(), sourceElement,
					destinationElement);
		}

		final String commentElement = getAttributeValue(LibraryElementTags.COMMENT_ATTRIBUTE);
		if (null != commentElement) {
			connection.setComment(commentElement);
		}

		parseConnectionRouting(connection);
		return connection;
	}

	private void createConnectionErrorMarker(final String message, final FBNetwork fbNetwork,
			final String sourceIdentifier, final String destinationIdentifier) {
		final Map<String, String> attrs = new HashMap<>();
		attrs.put(IMarker.MESSAGE, message);

		// use a dummy connection to get target identifier
		FordiacMarkerHelper.addTargetIdentifier(LibraryElementFactory.eINSTANCE.createDataConnection(), attrs);
		final String location = FordiacMarkerHelper.getLocation(fbNetwork) + "." + sourceIdentifier + " -> " //$NON-NLS-1$
				+ destinationIdentifier;
		attrs.put(IMarker.LOCATION, location);
		createErrorMarker(attrs);
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

	private IInterfaceElement getConnectionEndPoint(final String path, final EClass conType, final boolean isInput) {
		final String[] split = path.split("\\."); //$NON-NLS-1$

		if (1 == split.length) {
			return getContainingInterfaceElement(path, conType, isInput);
		}
		if (split.length >= 2) {
			final FBNetworkElement element = findFBNetworkElement(split[0]);
			if (null != element) {
				return getInterfaceElement(element.getInterface(), path.substring(split[0].length() + 1), conType,
						isInput);
			}
		}
		return null;
	}

	/**
	 * Check if the element that contains the fbnetwork has an interface element
	 * with the given name. this is needed for subapps, cfbs, devices and resources
	 */
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

	/**
	 * returns an valid dx, dy integer value
	 *
	 * @param value
	 * @return if value is valid the converted int of that otherwise 0
	 */
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
