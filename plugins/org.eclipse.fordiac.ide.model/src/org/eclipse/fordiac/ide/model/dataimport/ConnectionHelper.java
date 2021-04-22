/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport;

import java.util.EnumSet;
import java.util.Set;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.helpers.InterfaceListCopier;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class ConnectionHelper {

	public static class ConnectionBuilder {

		private Set<ConnectionState> connectionState;

		private String destination;
		private InterfaceList destInterfaceList;
		private IInterfaceElement destinationEndpoint;

		private String source;
		private InterfaceList srcInterfaceList;
		private IInterfaceElement sourceEndpoint;

		public ConnectionBuilder(final String source, final String destination) {
			this.connectionState = EnumSet.of(ConnectionState.VALID);
			this.source = source;
			this.destination = destination;
		}

		public void validate() {

			if (sourceEndpoint != null && destinationEndpoint != null) {
				return;
			}

			if (sourceEndpoint != null) {
				connectionState.add(ConnectionState.SOURCE_ENDPOINT_EXISTS);
			} else {
				connectionState.add(ConnectionState.SOURCE_ENDPOINT_MISSING);
				connectionState.remove(ConnectionState.VALID);
			}

			if (srcInterfaceList != null) {
				connectionState.add(ConnectionState.SOURCE_EXITS);
			} else {
				connectionState.add(ConnectionState.SOURCE_MISSING);
				connectionState.remove(ConnectionState.VALID);
			}

			if (destinationEndpoint != null) {
				connectionState.add(ConnectionState.DEST_ENPOINT_EXITS);
			} else {
				connectionState.add(ConnectionState.DEST_ENDPOINT_MISSING);
				connectionState.remove(ConnectionState.VALID);
			}

			if (destination != null && destInterfaceList != null) {
				connectionState.add(ConnectionState.DEST_EXISTS);
			} else {
				connectionState.add(ConnectionState.DEST_MISSING);
				connectionState.remove(ConnectionState.VALID);
			}
		}

		protected static InterfaceList getInterfaceFromQualString(final String source, final FBNetwork fbNetwork) {
			if (source == null) {
				return null;
			}

			final String[] qualNames = source.split("\\."); //$NON-NLS-1$
			if (qualNames.length == 0) {
				return null;
			}

			/* final EObject parent = fbNetwork.eContainer();
			 *
			 * if (parent instanceof SubApp || ((FBNetworkElement) parent).getType() instanceof CompositeFBType) {
			 * return (FBNetworkElement) parent; } */

			final FBNetworkElement fbNetworkElement = fbNetwork.getElementNamed(qualNames[0]);
			if (fbNetworkElement == null) {
				return getElementFromType(fbNetwork);
			}
			return fbNetworkElement.getInterface();
		}

		private static InterfaceList getElementFromType(final FBNetwork fbNetwork) {
			if (fbNetwork.eContainer() instanceof FBType) {
				final FBType type = (FBType) fbNetwork.eContainer();
				return type.getInterfaceList();
			}

			return null;
		}

		public String getSourceFbName() {
			if (source == null) {
				return "Source FB not found"; //$NON-NLS-1$
			}

			final String[] qualNames = source.split("\\."); //$NON-NLS-1$
			if (qualNames.length == 0) {
				return "Source FB NOT FOUND"; //$NON-NLS-1$
			}
			return qualNames[0];
		}

		public String getDestFbName() {

			if (destination == null) {
				return "Destination FB NOT FOUND"; //$NON-NLS-1$
			}

			final String[] qualNames = destination.split("\\."); //$NON-NLS-1$

			if (qualNames.length == 0) {
				return "Destination FB NOT FOUND"; //$NON-NLS-1$
			}
			return qualNames[0];
		}

		public Set<ConnectionState> getConnectionState() {
			return connectionState;
		}

		public String getDestination() {
			return destination;
		}

		public IInterfaceElement getDestinationEndpoint() {
			return destinationEndpoint;
		}

		public String getSource() {
			return source;
		}

		public IInterfaceElement getSourceEndpoint() {
			return sourceEndpoint;
		}

		public boolean isMissingConnectionDestination() {
			return connectionState
					.containsAll(EnumSet.of(ConnectionState.DEST_MISSING, ConnectionState.SOURCE_ENDPOINT_EXISTS));
		}

		public boolean isValidConnection() {
			return connectionState.contains(ConnectionState.VALID);
		}

		public boolean isMissingConnectionDestinationEndpoint() {
			return connectionState.containsAll(EnumSet.of(ConnectionState.DEST_ENDPOINT_MISSING,
					ConnectionState.SOURCE_EXITS, ConnectionState.SOURCE_ENDPOINT_EXISTS, ConnectionState.DEST_EXISTS));
		}

		public boolean isMissingConnectionSource() {
			return connectionState
					.containsAll(EnumSet.of(ConnectionState.SOURCE_MISSING, ConnectionState.SOURCE_ENDPOINT_MISSING,
							ConnectionState.DEST_EXISTS, ConnectionState.DEST_ENPOINT_EXITS));
		}

		public boolean isMissingConnectionSourceEndpoint() {
			return connectionState.containsAll(EnumSet.of(ConnectionState.SOURCE_ENDPOINT_MISSING,
					ConnectionState.SOURCE_EXITS, ConnectionState.DEST_EXISTS, ConnectionState.DEST_ENPOINT_EXITS));
		}

		public boolean isMissingSourceAndDestEndpoint() {
			return connectionState.containsAll(EnumSet.of(ConnectionState.SOURCE_ENDPOINT_MISSING,
					ConnectionState.DEST_ENDPOINT_MISSING, ConnectionState.SOURCE_EXITS, ConnectionState.DEST_EXISTS));
		}

		public String getSourcePinName() {

			if (source == null) {
				return "pin not found"; //$NON-NLS-1$
			}

			if (sourceEndpoint != null) {
				return sourceEndpoint.getName();
			}

			final String[] qualNames = source.split("\\."); //$NON-NLS-1$
			if (qualNames.length < 2) {
				return null;
			}

			return qualNames[1];
		}

		public String getDestinationPinName() {

			if (destination == null) {
				return "pin not found"; //$NON-NLS-1$
			}

			if (destinationEndpoint != null) {
				return destinationEndpoint.getName();
			}

			final String[] qualNames = destination.split("\\."); //$NON-NLS-1$
			if (qualNames.length < 2) {
				return "Pin not found"; //$NON-NLS-1$
			}

			return qualNames[1];
		}

		public InterfaceList getDestInterfaceList() {
			return destInterfaceList;
		}

		public void setDestInterfaceList(final InterfaceList destInterfaceList) {
			this.destInterfaceList = destInterfaceList;
		}

		public InterfaceList getSrcInterfaceList() {
			return srcInterfaceList;
		}

		public void setSrcInterfaceList(final InterfaceList srcInterfaceList) {
			this.srcInterfaceList = srcInterfaceList;
		}

		public void setConnectionState(final Set<ConnectionState> connectionState) {
			this.connectionState = connectionState;
		}

		public void setDestination(final String destination) {
			this.destination = destination;
		}

		public void setDestinationEndpoint(final IInterfaceElement destinationEndpoint) {
			this.destinationEndpoint = destinationEndpoint;
		}

		public void setSource(final String source) {
			this.source = source;
		}

		public void setSourceEndpoint(final IInterfaceElement sourceEndpoint) {
			this.sourceEndpoint = sourceEndpoint;
		}

	}

	public enum ConnectionState {
		VALID, SOURCE_MISSING, SOURCE_ENDPOINT_MISSING, DEST_MISSING, DEST_ENDPOINT_MISSING, SOURCE_EXITS,
		SOURCE_ENDPOINT_EXISTS, DEST_EXISTS, DEST_ENPOINT_EXITS, MISSING_TYPE
	}

	protected static ErrorMarkerFBNElement createErrorMarkerFB(final String name) {
		final ErrorMarkerFBNElement createErrorMarkerFBNElement = LibraryElementFactory.eINSTANCE
				.createErrorMarkerFBNElement();
		createErrorMarkerFBNElement.getTypeLibrary();
		// String createUniqueName = NameRepository.createUniqueName(createErrorMarkerFBNElement, name);
		createErrorMarkerFBNElement.setName(name);
		createErrorMarkerFBNElement.setInterface(LibraryElementFactory.eINSTANCE.createInterfaceList());
		final Position position = LibraryElementFactory.eINSTANCE.createPosition();
		position.setX(0);
		position.setY(0);
		createErrorMarkerFBNElement.setPosition(position);
		return createErrorMarkerFBNElement;
	}

	public static IInterfaceElement createRepairInterfaceElement(final IInterfaceElement connection,
			final String name) {
		IInterfaceElement repairIE = null;
		// adapter check has to be first
		if (connection instanceof AdapterDeclaration) {
			repairIE = InterfaceListCopier.copyAdapter((AdapterDeclaration) connection);
		} else if (connection instanceof VarDeclaration) {
			repairIE = InterfaceListCopier.copyVar((VarDeclaration) connection, false);
		} else if (connection instanceof Event) {
			repairIE = InterfaceListCopier.copyEvent((Event) connection);
		}

		if (null != repairIE) {
			repairIE.setIsInput(!connection.isIsInput()); // this needs to be inverted for the dummy connection
			repairIE.setName(name);
		}
		return repairIE;
	}

	public static ErrorMarkerInterface createErrorMarkerInterface(final DataType type, final String name,
			final boolean isInput, final InterfaceList ieList) {
		return (ErrorMarkerInterface) ieList.getErrorMarker().stream().filter(e -> e.getName().equals(name)).findAny()
				.orElseGet(() -> createErrorMarker(type, name, isInput, ieList));
	}

	private static ErrorMarkerInterface createErrorMarker(final DataType type, final String name, final boolean isInput,
			final InterfaceList ieList) {
		final ErrorMarkerInterface errorMarkerInterface = LibraryElementFactory.eINSTANCE.createErrorMarkerInterface();
		errorMarkerInterface.setName(name);
		errorMarkerInterface.setIsInput(isInput);
		errorMarkerInterface.setType(type);
		errorMarkerInterface.setTypeName(type.getName());
		ieList.getErrorMarker().add(errorMarkerInterface);
		return errorMarkerInterface;
	}

}
