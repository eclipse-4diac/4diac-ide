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

import org.eclipse.fordiac.ide.model.helpers.InterfaceListCopier;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class ConnectionHelper {

	public static class ConnectionBuilder {

		private final EnumSet<ConnectionState> connectionState;

		private final String destination;
		private final FBNetworkElement destinationElement;
		private final IInterfaceElement destinationEndpoint;

		private final String source;
		private final FBNetworkElement sourceElement;
		private final IInterfaceElement sourceEndpoint;

		private ConnectionBuilder(final EnumSet<ConnectionState> connectionState, final String destination,
				final FBNetworkElement destinationElement, final IInterfaceElement destinationEndpoint,
				final String source, final FBNetworkElement sourceElement, final IInterfaceElement sourceEndpoint) {
			this.connectionState = connectionState;
			this.destination = destination;
			this.destinationElement = destinationElement;
			this.destinationEndpoint = destinationEndpoint;
			this.source = source;
			this.sourceElement = sourceElement;
			this.sourceEndpoint = sourceEndpoint;
		}

		public static ConnectionBuilder createConnectionBuilder(final String destination,
				final IInterfaceElement destinationEndpoint, final String source,
				final IInterfaceElement sourceEndpoint, final FBNetwork fbNetwork) {

			final EnumSet<ConnectionState> states = EnumSet.of(ConnectionState.VALID);
			states.add(ConnectionState.VALID);

			FBNetworkElement sourceElement = null;
			FBNetworkElement destinationElement = null;

			if (source != null && getElementFromQualString(source, fbNetwork) != null) {
				sourceElement = getElementFromQualString(source, fbNetwork);
				states.add(ConnectionState.SOURCE_EXITS);
			} else {
				states.add(ConnectionState.SOURCE_MISSING);
				states.remove(ConnectionState.VALID);
			}

			if (sourceEndpoint != null) {
				states.add(ConnectionState.SOURCE_ENDPOINT_EXISTS);
			} else {
				states.add(ConnectionState.SOURCE_ENDPOINT_MISSING);
				states.remove(ConnectionState.VALID);
			}

			if (destination != null && getElementFromQualString(destination, fbNetwork) != null) {
				destinationElement = getElementFromQualString(destination, fbNetwork);
				states.add(ConnectionState.DEST_EXISTS);
			} else {
				states.add(ConnectionState.DEST_MISSING);
				states.remove(ConnectionState.VALID);
			}

			if (destinationEndpoint != null) {
				states.add(ConnectionState.DEST_ENPOINT_EXITS);
			} else {
				states.add(ConnectionState.DEST_ENDPOINT_MISSING);
				states.remove(ConnectionState.VALID);
			}

			return new ConnectionBuilder(states, destination, destinationElement,
					destinationEndpoint,
					source, sourceElement, sourceEndpoint);
		}

		protected static FBNetworkElement getElementFromQualString(final String source, final FBNetwork fbNetwork) {
			final String[] qualNames = source.split("\\."); //$NON-NLS-1$
			if (qualNames.length == 0) {
				return null;
			}

			return fbNetwork.getElementNamed(qualNames[0]);
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

		public EnumSet<ConnectionState> getConnectionState() {
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
					ConnectionState.SOURCE_EXITS, ConnectionState.DEST_EXISTS));
		}

		public boolean isMissingConnectionSource() {
			return connectionState
					.containsAll(EnumSet.of(ConnectionState.SOURCE_MISSING, ConnectionState.DEST_ENPOINT_EXITS));
		}

		public boolean isMissingConnectionSourceEndpoint() {
			return connectionState.containsAll(
					EnumSet.of(ConnectionState.SOURCE_ENDPOINT_MISSING, ConnectionState.DEST_ENPOINT_EXITS));
		}

		public FBNetworkElement getDestinationElement() {
			return destinationElement;
		}

		public FBNetworkElement getSourceElement() {
			return sourceElement;
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

	}

	public enum ConnectionState {
		VALID, SOURCE_MISSING, SOURCE_ENDPOINT_MISSING, DEST_MISSING, DEST_ENDPOINT_MISSING, SOURCE_EXITS,
		SOURCE_ENDPOINT_EXISTS, DEST_EXISTS, DEST_ENPOINT_EXITS
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
		if (connection instanceof VarDeclaration) {
			final VarDeclaration varDeclaration = InterfaceListCopier.copyVar((VarDeclaration) connection, false);
			varDeclaration.setIsInput(!connection.isIsInput()); // this needs to be inverted for the dummy connection
			varDeclaration.setName(name);
			return varDeclaration;
		}

		// TODO check other connettionstypes

		return null;
	}

	public static ErrorMarkerInterface createErrorMarkerInterface(final IInterfaceElement source, final String name,
			final boolean isInput) {
		final ErrorMarkerInterface errorMarkerInterface = LibraryElementFactory.eINSTANCE.createErrorMarkerInterface();
		errorMarkerInterface.setName(name);
		errorMarkerInterface.setIsInput(isInput);
		errorMarkerInterface.setType(source.getType());
		errorMarkerInterface.setTypeName(source.getName());
		return errorMarkerInterface;
	}

}
