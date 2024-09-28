/*******************************************************************************
 * Copyright (c) 2021-2022 Primetals Technologies Austria GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport;

import java.text.MessageFormat;
import java.util.EnumSet;
import java.util.Set;

import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.validation.LinkConstraints;

public final class ConnectionHelper {

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
				if (!LinkConstraints.typeCheck(sourceEndpoint, destinationEndpoint)) {
					connectionState.add(ConnectionState.DATATYPE_MISSMATCH);
					connectionState.remove(ConnectionState.VALID);
				}

				if (LinkConstraints.duplicateConnection(sourceEndpoint, destinationEndpoint)) {
					connectionState.add(ConnectionState.DUPLICATE);
					connectionState.remove(ConnectionState.VALID);
				}

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

		public String getSourceFbName() {
			if (source == null) {
				return Messages.ConnectionHelper_ErrorMarker_Source_Missing;
			}

			final String[] qualNames = source.split("\\."); //$NON-NLS-1$
			if (qualNames.length == 0) {
				return Messages.ConnectionHelper_ErrorMarker_Source_Missing;
			}
			return qualNames[0];
		}

		public String getDestFbName() {

			if (destination == null) {
				return Messages.ConnectionHelper_ErrorMarker_Dest_Missing;
			}

			final String[] qualNames = destination.split("\\."); //$NON-NLS-1$

			if (qualNames.length == 0) {
				return Messages.ConnectionHelper_ErrorMarker_Dest_Missing;
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
			return (!(sourceEndpoint.getType().getName().equalsIgnoreCase("Event")) && sourceEndpoint.isIsInput()
					&& sourceEndpoint.getInputConnections().size() > 1)
					|| (!(destinationEndpoint.getType().getName().equalsIgnoreCase("Event"))
							&& destinationEndpoint.isIsInput() && destinationEndpoint.getInputConnections().size() > 1);
		}

		public String getSourcePinName() {

			if (source == null) {
				return MessageFormat.format(Messages.ConnectionHelper_pin_not_found, "«null»"); //$NON-NLS-1$
			}

			if (sourceEndpoint != null) {
				return sourceEndpoint.getName();
			}

			final String[] qualNames = source.split("\\."); //$NON-NLS-1$
			if (qualNames.length < 2) {
				return MessageFormat.format(Messages.ConnectionHelper_pin_not_found, source);
			}

			return qualNames[1];
		}

		public String getDestinationPinName() {

			if (destination == null) {
				return MessageFormat.format(Messages.ConnectionHelper_pin_not_found, "«null»"); //$NON-NLS-1$
			}

			if (destinationEndpoint != null) {
				return destinationEndpoint.getName();
			}

			final String[] qualNames = destination.split("\\."); //$NON-NLS-1$
			if (qualNames.length < 2) {
				return MessageFormat.format(Messages.ConnectionHelper_pin_not_found, destination);
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
		SOURCE_ENDPOINT_EXISTS, DEST_EXISTS, DEST_ENPOINT_EXITS, MISSING_TYPE, DATATYPE_MISSMATCH, DUPLICATE
	}

	private ConnectionHelper() {
		throw new UnsupportedOperationException();
	}

}
