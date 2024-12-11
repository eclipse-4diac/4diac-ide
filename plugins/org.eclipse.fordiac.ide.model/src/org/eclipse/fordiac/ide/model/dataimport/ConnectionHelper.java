/*******************************************************************************
 * Copyright (c) 2021, 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *                      - added data type check for connection
 *   Alois Zoitl        - moved connection checking and building here
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport;

import java.text.MessageFormat;
import java.util.EnumSet;
import java.util.Set;

import javax.xml.stream.XMLStreamException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarkerInterfaceHelper;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.resource.TypeImportDiagnostic;
import org.eclipse.fordiac.ide.model.typelibrary.AdapterTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;
import org.eclipse.fordiac.ide.model.validation.LinkConstraints;

public final class ConnectionHelper {

	public static class ConnectionBuilder<T extends Connection> {

		private final Set<ConnectionState> connectionState;

		final T connection;
		final FBNetworkImporter importer;

		private final String destinationString;
		private InterfaceList destInterfaceList;

		private final String sourceString;
		private InterfaceList srcInterfaceList;

		public ConnectionBuilder(final String sourceString, final String destinationString, final T connection,
				final FBNetworkImporter importer) {
			this.connectionState = EnumSet.of(ConnectionState.VALID);
			this.sourceString = sourceString;
			this.destinationString = destinationString;
			this.connection = connection;
			this.importer = importer;

			connection.setSource(getConnectionEndPoint(sourceString, false));
			connection.setDestination(getConnectionEndPoint(destinationString, true));
		}

		public void validate() {
			final IInterfaceElement sourcePin = connection.getSource();
			final IInterfaceElement destPin = connection.getDestination();

			if (sourcePin != null && destPin != null) {
				if (!LinkConstraints.typeCheck(sourcePin, destPin)) {
					connectionState.add(ConnectionState.DATATYPE_MISSMATCH);
					connectionState.remove(ConnectionState.VALID);
				}

				if (LinkConstraints.duplicateConnection(sourcePin, destPin)) {
					connectionState.add(ConnectionState.DUPLICATE);
					connectionState.remove(ConnectionState.VALID);
				}

				return;
			}

			if (sourcePin != null) {
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

			if (destPin != null) {
				connectionState.add(ConnectionState.DEST_ENPOINT_EXITS);
			} else {
				connectionState.add(ConnectionState.DEST_ENDPOINT_MISSING);
				connectionState.remove(ConnectionState.VALID);
			}

			if (destInterfaceList != null) {
				connectionState.add(ConnectionState.DEST_EXISTS);
			} else {
				connectionState.add(ConnectionState.DEST_MISSING);
				connectionState.remove(ConnectionState.VALID);
			}
		}

		public void handleErrorCases() {
			if (isMissingConnectionDestination()) {
				handleMissingConnectionDestination();
			}

			if (isMissingConnectionDestinationEndpoint()) {
				createErrorMarkerInterface(true);
			}

			if (isMissingConnectionSource()) {
				handleMissingConnectionSource();
			}

			if (isMissingConnectionSourceEndpoint()) {
				createErrorMarkerInterface(false);
			}

			if (isMissingSourceAndDestEndpoint()) {
				handleMissingSrcAndDestEnpoint();
			}

			if (isEmptyConnection()) {
				// if 4diac should be capable of seeing the error marker in the FBNetworkEditor
				// then error marker blocks need to be created at this location
				importer.getErrors()
						.add(new TypeImportDiagnostic("Connection missing both source and destination element", //$NON-NLS-1$
								MessageFormat.format("{0} -> {1}", sourceString, destinationString), //$NON-NLS-1$
								importer.getLineNumber()));
			}
		}

		public String getSourceFbName() {
			if (sourceString == null) {
				return Messages.ConnectionHelper_ErrorMarker_Source_Missing;
			}

			final String[] qualNames = sourceString.split("\\."); //$NON-NLS-1$
			if (qualNames.length == 0) {
				return Messages.ConnectionHelper_ErrorMarker_Source_Missing;
			}
			return qualNames[0];
		}

		public String getDestFbName() {

			if (destinationString == null) {
				return Messages.ConnectionHelper_ErrorMarker_Dest_Missing;
			}

			final String[] qualNames = destinationString.split("\\."); //$NON-NLS-1$

			if (qualNames.length == 0) {
				return Messages.ConnectionHelper_ErrorMarker_Dest_Missing;
			}
			return qualNames[0];
		}

		public Set<ConnectionState> getConnectionState() {
			return connectionState;
		}

		public IInterfaceElement getDestinationEndpoint() {
			return connection.getDestination();
		}

		public IInterfaceElement getSourceEndpoint() {
			return connection.getSource();
		}

		public T getConnection() {
			return isEmptyConnection() ? null : connection;
		}

		public boolean isMissingConnectionDestination() {
			return connectionState
					.containsAll(EnumSet.of(ConnectionState.DEST_MISSING, ConnectionState.SOURCE_ENDPOINT_EXISTS));
		}

		public boolean isValidConnection() {
			return connectionState.contains(ConnectionState.VALID);
		}

		public boolean isDuplicate() {
			return connectionState.contains(ConnectionState.DUPLICATE);
		}

		public boolean isDataTypeMissmatch() {
			return connectionState.contains(ConnectionState.DATATYPE_MISSMATCH);
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

		public boolean isEmptyConnection() {
			return connectionState.containsAll(
					EnumSet.of(ConnectionState.SOURCE_ENDPOINT_MISSING, ConnectionState.DEST_ENDPOINT_MISSING,
							ConnectionState.SOURCE_MISSING, ConnectionState.DEST_MISSING));
		}

		public boolean dataInputHasMultipleConnections() {
			final IInterfaceElement sourceEndpoint = connection.getSource();
			final IInterfaceElement destinationEndpoint = connection.getDestination();

			return (!(sourceEndpoint instanceof Event) && sourceEndpoint.isIsInput()
					&& sourceEndpoint.getInputConnections().size() > 1)
					|| (!(destinationEndpoint instanceof Event) && destinationEndpoint.isIsInput()
							&& destinationEndpoint.getInputConnections().size() > 1);
		}

		public String getSourcePinName() {

			if (sourceString == null) {
				return MessageFormat.format(Messages.ConnectionHelper_pin_not_found, "«null»"); //$NON-NLS-1$
			}

			if (connection.getSource() != null) {
				return connection.getSource().getName();
			}

			final String[] qualNames = sourceString.split("\\."); //$NON-NLS-1$
			if (qualNames.length < 2) {
				return MessageFormat.format(Messages.ConnectionHelper_pin_not_found, sourceString);
			}

			return qualNames[1];
		}

		public String getDestinationPinName() {

			if (destinationString == null) {
				return MessageFormat.format(Messages.ConnectionHelper_pin_not_found, "«null»"); //$NON-NLS-1$
			}

			if (connection.getDestination() != null) {
				return connection.getDestination().getName();
			}

			final String[] qualNames = destinationString.split("\\."); //$NON-NLS-1$
			if (qualNames.length < 2) {
				return MessageFormat.format(Messages.ConnectionHelper_pin_not_found, destinationString);
			}

			return qualNames[1];
		}

		private void handleMissingConnectionSource() {
			final FBNetworkElement sourceFB = FordiacMarkerHelper.createErrorMarkerFB(getSourceFbName());
			srcInterfaceList = sourceFB.getInterface();
			importer.getFbNetwork().getNetworkElements().add(sourceFB);
			sourceFB.setName(NameRepository.createUniqueName(sourceFB, sourceFB.getName()));
			createErrorMarkerInterface(false);
		}

		private void handleMissingConnectionDestination() {
			// check if there is already one
			final FBNetworkElement destinationFb = FordiacMarkerHelper.createErrorMarkerFB(getDestFbName());
			destInterfaceList = destinationFb.getInterface();
			importer.getFbNetwork().getNetworkElements().add(destinationFb);
			destinationFb.setName(NameRepository.createUniqueName(destinationFb, destinationFb.getName()));
			createErrorMarkerInterface(true);
		}

		private void handleMissingSrcAndDestEnpoint() {
			final DataType pinType = determineConnectionType();

			final ErrorMarkerInterface srcEndpoint = FordiacErrorMarkerInterfaceHelper
					.createErrorMarkerInterface(pinType, getSourcePinName(), false, srcInterfaceList);
			final ErrorMarkerInterface destEndpoint = FordiacErrorMarkerInterfaceHelper
					.createErrorMarkerInterface(pinType, getDestinationPinName(), true, destInterfaceList);
			connection.setSource(srcEndpoint);
			connection.setDestination(destEndpoint);
		}

		private DataType determineConnectionType() {
			if (connection instanceof EventConnection) {
				return EventTypeLibrary.getInstance().getType(null);
			}
			if (connection instanceof AdapterConnection) {
				final AdapterTypeEntry entry = importer.getTypeLibrary().getAdapterTypeEntry("ANY_ADAPTER"); //$NON-NLS-1$
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

		private ErrorMarkerInterface createErrorMarkerInterface(final boolean isInput) {
			final IInterfaceElement oppositeEndpoint = isInput ? getSourceEndpoint() : getDestinationEndpoint();

			// we need a special treatment for FB's that lost their type
			if (oppositeEndpoint == null) {
				getConnectionState().add(ConnectionState.MISSING_TYPE);
				return null;
			}

			DataType type = oppositeEndpoint.getType();
			if (type == null) {
				type = determineConnectionType();
			}

			final InterfaceList ieList = isInput ? destInterfaceList : srcInterfaceList;
			final String pinName = isInput ? getDestinationPinName() : getSourcePinName();
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

		private IInterfaceElement getConnectionEndPoint(final String path, final boolean isInput) {
			if (path == null) {
				return null;
			}
			int separatorPos = path.indexOf('.');

			if (separatorPos == -1) {
				// we have a connection to the containing interface
				if (isInput) {
					destInterfaceList = importer.getInterfaceList();
				} else {
					srcInterfaceList = importer.getInterfaceList();
				}
				return importer.getContainingInterfaceElement(path, connection.eClass(), isInput);
			}

			String elementName = path.substring(0, separatorPos);
			FBNetworkElement element = importer.findFBNetworkElement(elementName);
			while (element == null && separatorPos != -1) {
				separatorPos = path.indexOf('.', separatorPos + 1);
				elementName = path.substring(0, separatorPos);
				element = importer.findFBNetworkElement(elementName);
			}

			if (null != element) {
				final InterfaceList ieList = element.getInterface();
				if (isInput) {
					destInterfaceList = ieList;
				} else {
					srcInterfaceList = ieList;
				}
				final String pinName = path.substring(separatorPos + 1);
				return FBNetworkImporter.getInterfaceElement(ieList, pinName, connection.eClass(), isInput);
			}
			return null;
		}
	}

	public static <T extends Connection> ConnectionBuilder<T> createConnectionBuilder(final EClass conType,
			final FBNetworkImporter importer) throws XMLStreamException, TypeImportException {
		@SuppressWarnings("unchecked")
		final T connection = (T) LibraryElementFactory.eINSTANCE.create(conType);

		final String sourceElement = importer.getAttributeValue(LibraryElementTags.SOURCE_ATTRIBUTE);
		final String destinationElement = importer.getAttributeValue(LibraryElementTags.DESTINATION_ATTRIBUTE);

		final String commentElement = importer.getAttributeValue(LibraryElementTags.COMMENT_ATTRIBUTE);
		if (null != commentElement) {
			connection.setComment(commentElement);
		}
		parseConnectionRouting(connection, importer);
		importer.parseAttributes(connection);

		return new ConnectionBuilder<>(sourceElement, destinationElement, connection, importer);
	}

	private static void parseConnectionRouting(final Connection connection, final CommonElementImporter importer) {
		final ConnectionRoutingData routingData = LibraryElementFactory.eINSTANCE.createConnectionRoutingData();
		final String dx1Element = importer.getAttributeValue(LibraryElementTags.DX1_ATTRIBUTE);
		if (null != dx1Element) {
			routingData.setDx1(parseConnectionValue(dx1Element));
		}
		final String dx2Element = importer.getAttributeValue(LibraryElementTags.DX2_ATTRIBUTE);
		if (null != dx2Element) {
			routingData.setDx2(parseConnectionValue(dx2Element));
		}
		final String dyElement = importer.getAttributeValue(LibraryElementTags.DY_ATTRIBUTE);
		if (null != dyElement) {
			routingData.setDy(parseConnectionValue(dyElement));
		}
		connection.setRoutingData(routingData);
	}

	private static double parseConnectionValue(final String value) {
		try {
			return Double.parseDouble(value);
		} catch (final NumberFormatException ex) {
			return 0;
		}
	}

	public enum ConnectionState {
		VALID, SOURCE_MISSING, SOURCE_ENDPOINT_MISSING, DEST_MISSING, DEST_ENDPOINT_MISSING, SOURCE_EXITS,
		SOURCE_ENDPOINT_EXISTS, DEST_EXISTS, DEST_ENPOINT_EXITS, MISSING_TYPE, DATATYPE_MISSMATCH, DUPLICATE
	}

	private ConnectionHelper() {
		throw new UnsupportedOperationException();
	}

}
