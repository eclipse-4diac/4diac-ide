/********************************************************************************
 * Copyright (c) 2016 - 2017  fortiss GmbH
 * 				 2019 - 2020 Johannes Kepler University, Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *    - initial API and implementation and/or initial documentation
 *   Alois Zoitl - fixed coordinate system resolution conversion in in- and export
 *   			 - Changed XML parsing to Staxx cursor interface for improved
 *  			   parsing performance
 *   Bianca Wiesmayr - mux support
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport;

import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.fordiac.ide.model.Activator;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
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

	public FBNetworkImporter(CommonElementImporter importer) {
		// so we need an empty interface list
		// this is a type with no external interface (currently only application)
		this(importer, LibraryElementFactory.eINSTANCE.createFBNetwork(),
				LibraryElementFactory.eINSTANCE.createInterfaceList());
	}

	public FBNetworkImporter(CommonElementImporter importer, FBNetwork fbNetwork, InterfaceList interfaceList) {
		super(importer);
		this.fbNetwork = fbNetwork;
		this.interfaceList = interfaceList;
		fbNetwork.getNetworkElements().forEach(element -> fbNetworkElementMap.put(element.getName(), element));
	}

	protected FBNetworkImporter(CommonElementImporter importer, FBNetwork fbNetwork) {
		this(importer, fbNetwork, LibraryElementFactory.eINSTANCE.createInterfaceList());
	}

	public FBNetwork getFbNetwork() {
		return fbNetwork;
	}

	public FBNetwork parseFBNetwork(String networkNodeName) throws TypeImportException, XMLStreamException {
		processChildren(networkNodeName, name -> {
			switch (name) {
			case LibraryElementTags.FB_ELEMENT:
				parseFB();
				break;
			case LibraryElementTags.EVENT_CONNECTIONS_ELEMENT:
				parseConnectionList(LibraryElementPackage.eINSTANCE.getEventConnection(),
						fbNetwork.getEventConnections(), LibraryElementTags.EVENT_CONNECTIONS_ELEMENT);
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
		});
		return fbNetwork;
	}

	protected void parseFB() throws TypeImportException, XMLStreamException {
		FB fb = LibraryElementFactory.eINSTANCE.createFB();

		readNameCommentAttributes(fb);

		FBTypePaletteEntry entry = getTypeEntry();

		if (null != entry) {
			fb.setPaletteEntry(entry);
			fb.setInterface(fb.getType().getInterfaceList().copy());
		} else {
//TODO model refactoring - think about where and if such markers should be created maybe move to validator
//				createFBTypeProblemMarker(IMarker.SEVERITY_ERROR, Messages.FBTImporter_REQUIRED_FB_TYPE_EXCEPTION + typeFbElement.getNodeValue() + " not available");
			// as we don't have type information we create an empty interface list
			fb.setInterface(LibraryElementFactory.eINSTANCE.createInterfaceList());
			// TODO add attribute value for missing instance name and
			// indicate that FB is missing for usage in outline views
		}

		getXandY(fb);

		parseFBChildren(fb, LibraryElementTags.FB_ELEMENT);

		for (VarDeclaration var : fb.getInterface().getInputVars()) {
			if (null == var.getValue()) {
				var.setValue(LibraryElementFactory.eINSTANCE.createValue());
			}
		}

		if (fb.getType().getName().equals("STRUCT_MUX")) { //$NON-NLS-1$
			Multiplexer mux = LibraryElementFactory.eINSTANCE.createMultiplexer();
			fb = convertFBtoMux(fb, mux);
		} else if (fb.getType().getName().contentEquals("STRUCT_DEMUX")) { //$NON-NLS-1$
			Demultiplexer demux = LibraryElementFactory.eINSTANCE.createDemultiplexer();
			fb = convertFBtoMux(fb, demux);
		}
		fbNetwork.getNetworkElements().add(fb);
		fbNetworkElementMap.put(fb.getName(), fb);
	}

	private FB convertFBtoMux(FB fb, StructManipulator mux) {
		mux.setName(fb.getName());
		mux.setComment(fb.getComment());
		mux.setX(fb.getX());
		mux.setY(fb.getY());
		mux.setPaletteEntry(fb.getPaletteEntry());
		mux.setInterface(fb.getInterface());
		String structName = fb.getAttributeValue("StructuredType"); //$NON-NLS-1$
		mux.setStructType((StructuredType) getDataTypeLibrary().getType(structName));
		return mux;
	}

	private FBTypePaletteEntry getTypeEntry() {
		String typeFbElement = getAttributeValue(LibraryElementTags.TYPE_ATTRIBUTE);
		if (null != typeFbElement) {
			return getPalette().getFBTypeEntry(typeFbElement);
		}
		return null;
	}

//	private IMarker createFBTypeProblemMarker(int severity, String message) {
//		IMarker marker = null;
//		if(null != file){
//			try {
//				marker = file.createMarker(IMarker.PROBLEM);
//				marker.setAttribute(IMarker.SEVERITY, severity);
//				marker.setAttribute(IMarker.MESSAGE, message);
//			} catch (CoreException e) {
//				Activator.getDefault().logError(e.getMessage(), e);
//			}
//		}
//		return marker;
//	}

	protected void parseFBChildren(FBNetworkElement block, String parentNodeName)
			throws TypeImportException, XMLStreamException {
		processChildren(parentNodeName, name -> {
			switch (name) {
			case LibraryElementTags.PARAMETER_ELEMENT: {
				VarDeclaration parameter = parseParameter();
				VarDeclaration vInput = getVarNamed(block.getInterface(), parameter.getName(), true);
				if (null != vInput) {
					vInput.setValue(parameter.getValue());
				}
				return true;
			}

			case LibraryElementTags.ATTRIBUTE_ELEMENT:
				parseGenericAttributeNode(block);
				proceedToEndElementNamed(LibraryElementTags.ATTRIBUTE_ELEMENT);

				return true;
			default:
				return false;
			}
		});
	}

	protected <T extends Connection> void parseConnectionList(EClass conType, EList<T> connectionlist,
			String parentNodeName) throws XMLStreamException, TypeImportException {
		processChildren(parentNodeName, name -> {
			if (LibraryElementTags.CONNECTION_ELEMENT.equals(LibraryElementTags.CONNECTION_ELEMENT)) {
				T connection = parseConnection(conType);
				if (null != connection) {
					connectionlist.add(connection);
				}
				proceedToEndElementNamed(LibraryElementTags.CONNECTION_ELEMENT);
				return true;
			}
			return false;
		});

	}

	private <T extends Connection> T parseConnection(EClass conType) {
		T connection = (T) LibraryElementFactory.eINSTANCE.create(conType);
		connection.setResTypeConnection(false);

		String destinationElement = getAttributeValue(LibraryElementTags.DESTINATION_ATTRIBUTE);
		if (null != destinationElement) {
			IInterfaceElement destination = getConnectionEndPoint(destinationElement, conType, true);
			if (null != destination) {
				connection.setDestination(destination);
			} else {
				// TODO model refactoring - this connection is missing an endpoint. add error
				// markers or dummy connection points so that the conenction can be handled in
				// the according FBNetowrk editor
				Activator.getDefault().logError("Connection destination not found: " + destinationElement);
				return null;
			}
		}
		String sourceElement = getAttributeValue(LibraryElementTags.SOURCE_ATTRIBUTE);
		if (null != sourceElement) {
			IInterfaceElement source = getConnectionEndPoint(sourceElement, conType, false);
			if (null != source) {
				connection.setSource(source);
			} else {
				Activator.getDefault().logError("Connection source not found: " + sourceElement);
				return null;
			}
		}

		String commentElement = getAttributeValue(LibraryElementTags.COMMENT_ATTRIBUTE);
		if (null != commentElement) {
			connection.setComment(commentElement);
		}

		parseConnectionRouting(connection);
		return connection;
	}

	private void parseConnectionRouting(Connection connection) {
		String dx1Element = getAttributeValue(LibraryElementTags.DX1_ATTRIBUTE);
		if (null != dx1Element) {
			connection.setDx1(parseConnectionValue(dx1Element));
		}
		String dx2Element = getAttributeValue(LibraryElementTags.DX2_ATTRIBUTE);
		if (null != dx2Element) {
			connection.setDx2(parseConnectionValue(dx2Element));
		}
		String dyElement = getAttributeValue(LibraryElementTags.DY_ATTRIBUTE);
		if (null != dyElement) {
			connection.setDy(parseConnectionValue(dyElement));
		}
	}

	private IInterfaceElement getConnectionEndPoint(String path, EClass conType, boolean isInput) {
		String[] split = path.split("\\."); //$NON-NLS-1$

		if (1 == split.length) {
			return getContainingInterfaceElement(path, conType, isInput);
		}
		if (2 == split.length) {
			FBNetworkElement element = findFBNetworkElement(split[0]);
			if (null != element) {
				return getInterfaceElement(element.getInterface(), split[1], conType, isInput);
			}
		}
		return null;
	}

	/**
	 * Check if the element that contains the fbnetwork has an interface element
	 * with the given name. this is needed for subapps, cfbs, devices and resources
	 */
	protected IInterfaceElement getContainingInterfaceElement(String interfaceElement, EClass conType,
			boolean isInput) {
		return getInterfaceElement(interfaceList, interfaceElement, conType, !isInput); // for connections to the
																						// interface inputs are the
																						// outputs of the FB
	}

	private static IInterfaceElement getInterfaceElement(InterfaceList il, String interfaceElement, EClass conType,
			boolean isInput) {
		EList<? extends IInterfaceElement> ieList = getInterfaceElementList(il, conType, isInput);
		for (IInterfaceElement ie : ieList) {
			if (ie.getName().equals(interfaceElement)) {
				return ie;
			}
		}
		return null;
	}

	private static EList<? extends IInterfaceElement> getInterfaceElementList(InterfaceList il, EClass conType,
			boolean isInput) {
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

	protected FBNetworkElement findFBNetworkElement(String fbName) {
		return fbNetworkElementMap.get(fbName);
	}

	protected static VarDeclaration getVarNamed(InterfaceList interfaceList, String varName, boolean input) {
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

	private static VarDeclaration createVarDecl(InterfaceList interfaceList, String varName, boolean input) {
		VarDeclaration var = LibraryElementFactory.eINSTANCE.createVarDeclaration();
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
	private static int parseConnectionValue(String value) {
		try {
			return CoordinateConverter.INSTANCE.convertFrom1499XML(value);
		} catch (NumberFormatException ex) {
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
