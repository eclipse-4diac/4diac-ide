/*******************************************************************************
 * Copyright (c) 2022, 2024 Markus Meingast, Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Markus Meingast
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.opcua.helpers;

import java.util.AbstractMap;
import java.util.Map;

import org.eclipse.fordiac.ide.deployment.devResponse.DevResponseFactory;
import org.eclipse.fordiac.ide.deployment.devResponse.Response;
import org.eclipse.milo.opcua.stack.core.StatusCodes;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.StatusCode;

public class Constants {

	/** OPCUA Nodes **/
	/* Root Node */
	public static final NodeId MGMT_NODE = new NodeId(1, "IEC61499Mgmt"); //$NON-NLS-1$

	public static final NodeId CREATE_RESOURCE_NODE = new NodeId(1, "createResource"); //$NON-NLS-1$
	public static final NodeId CREATE_FB_NODE = new NodeId(1, "createFB"); //$NON-NLS-1$
	public static final NodeId CREATE_CONNECTION_NODE = new NodeId(1, "createConnection"); //$NON-NLS-1$
	public static final NodeId WRITE_DEVICE_NODE = new NodeId(1, "writeDevice"); //$NON-NLS-1$
	public static final NodeId WRITE_RESOURCE_NODE = new NodeId(1, "writeResource"); //$NON-NLS-1$
	public static final NodeId WRITE_FB_NODE = new NodeId(1, "writeFB"); //$NON-NLS-1$
	public static final NodeId START_DEVICE_NODE = new NodeId(1, "startDevice"); //$NON-NLS-1$
	public static final NodeId START_RESOURCE_NODE = new NodeId(1, "startResource"); //$NON-NLS-1$
	public static final NodeId START_FB_NODE = new NodeId(1, "startFB"); //$NON-NLS-1$
	public static final NodeId KILL_DEVICE_NODE = new NodeId(1, "killDevice"); //$NON-NLS-1$
	public static final NodeId KILL_RESOURCE_NODE = new NodeId(1, "killResource"); //$NON-NLS-1$
	public static final NodeId DELETE_RESOURCE_NODE = new NodeId(1, "deleteResource"); //$NON-NLS-1$
	public static final NodeId DELETE_FB_NODE = new NodeId(1, "deleteFB"); //$NON-NLS-1$
	public static final NodeId DELETE_CONNECTION_NODE = new NodeId(1, "deleteConnection"); //$NON-NLS-1$
	public static final NodeId ADD_WATCH_NODE = new NodeId(1, "addWatch"); //$NON-NLS-1$
	public static final NodeId READ_WATCHES_NODE = new NodeId(1, "readWatches"); //$NON-NLS-1$
	public static final NodeId REMOVE_WATCH_NODE = new NodeId(1, "removeWatch"); //$NON-NLS-1$

	/** Deployment Console Messages **/
	public static final String CREATE_RESOURCE_INSTANCE = "<Request Action=\"CREATE RESOURCE\"><FB Name=\"{0}\" Type=\"{1}\" /></Request>"; //$NON-NLS-1$
	public static final String CREATE_FB_INSTANCE = "<Request Action=\"CREATE FB\"><FB Name=\"{0}\" Type=\"{1}\" /></Request>"; //$NON-NLS-1$
	public static final String CREATE_CONNECTION = "<Request Action=\"CREATE CONNECTION\"><Connection Destination=\"{0}\" Source=\"{1}\" /></Request>"; //$NON-NLS-1$
	public static final String WRITE_DEVICE_PARAMETER = "<Request Action=\"WRITE DEVICE\"><Name=\"{0}\" Value=\"{1}\" /></Request>"; //$NON-NLS-1$
	public static final String WRITE_RESOURCE_PARAMETER = "<Request Action=\"WRITE RESOURCE\"><Name=\"{0}\" Value=\"{1}\" /></Request>"; //$NON-NLS-1$
	public static final String WRITE_FB_PARAMETER = "<Request Action=\"WRITE FB\"><Connection Destination=\"{0}\" Source=\"{1}\" /></Request>"; //$NON-NLS-1$
	public static final String START_DEVICE = "<Request Action=\"START DEVICE\"/>"; //$NON-NLS-1$
	public static final String START_RESOURCE = "<Request Action=\"START RESOURCE\"/>"; //$NON-NLS-1$
	public static final String START_FB = "<Request Action=\"START FB\"><FB Name=\"{0}\" Type=\"{1}\" /></Request>"; //$NON-NLS-1$
	public static final String KILL_DEVICE = "<Request Action=\"KILL DEVICE\"/>"; //$NON-NLS-1$
	public static final String KILL_RESOURCE = "<Request Action=\"KILL RESOURCE\"/>"; //$NON-NLS-1$
	public static final String DELETE_RESOURCE = "<Request Action=\"DELETE RESOURCE\"><FB Name=\"{0}\" Type=\"{1}\" /></Request>"; //$NON-NLS-1$
	public static final String DELETE_FB_INSTANCE = "<Request Action=\"DELETE FB\"><FB Name=\"{0}\" /></Request>"; //$NON-NLS-1$
	public static final String DELETE_CONNECTION = "<Request Action=\"DELETE CONNECTION\"><Connection Destination=\"{0}\" Source=\"{1}\" /></Request>"; //$NON-NLS-1$
	public static final String ADD_WATCH = "<Request Action=\"ADD WATCH\"><Connection Destination=\"{0}\" /></Request>"; //$NON-NLS-1$
	public static final String READ_WATCHES = "<Request Action=\"READ\"><Watches/></Request>"; //$NON-NLS-1$
	public static final String REMOVE_WATCH = "<Request Action=\"REMOVE WATCH\"><Connection Destination=\"{0}\" /></Request>"; //$NON-NLS-1$

	public static final String RESPONSE = "<Response Reason=\"{0}\" />\n"; //$NON-NLS-1$

	public static final String WATCHES_RESPONSE = "<Response>\n  <Watches>\n    {0}\n  </Watches>\n</Response>"; //$NON-NLS-1$
	public static final Response EMPTY_WATCHES_RESPONSE;

	/* IEC61499 Status Codes */
	public static final String MGM_RESPONSE_READY = "Ready"; //$NON-NLS-1$
	public static final String MGM_RESPONSE_BAD_PARAMS = "BadParams"; //$NON-NLS-1$
	public static final String MGM_RESPONSE_LOCAL_TERMINATION = "LocalTermination"; //$NON-NLS-1$
	public static final String MGM_RESPONSE_SYSTEM_TERMINATION = "SystemTermination"; //$NON-NLS-1$
	public static final String MGM_RESPONSE_NOT_READY = "NotReady"; //$NON-NLS-1$
	public static final String MGM_RESPONSE_UNSUPPORTED_CMD = "UnsupportedCmd"; //$NON-NLS-1$
	public static final String MGM_RESPONSE_UNSUPPORTED_TYPE = "UnsupportedType"; //$NON-NLS-1$
	public static final String MGM_RESPONSE_NO_SUCH_OBJECT = "NoSuchObject"; //$NON-NLS-1$
	public static final String MGM_RESPONSE_INVALID_OBJECT = "InvalidObject"; //$NON-NLS-1$
	public static final String MGM_RESPONSE_INVALID_OPERATION = "InvalidOperation"; //$NON-NLS-1$
	public static final String MGM_RESPONSE_INVALID_STATE = "InvalidState"; //$NON-NLS-1$
	public static final String MGM_RESPONSE_OVERFLOW = "Overflow"; //$NON-NLS-1$
	public static final String MGM_RESPONSE_UNKNOWN = "Unknown"; //$NON-NLS-1$

	public static final Map<Long, String> RESPONSE_MAP = Map.ofEntries(
			new AbstractMap.SimpleEntry<>(StatusCode.GOOD.getValue(), MGM_RESPONSE_READY),
			new AbstractMap.SimpleEntry<>(StatusCodes.Bad_InvalidArgument, MGM_RESPONSE_BAD_PARAMS),
			new AbstractMap.SimpleEntry<>(StatusCodes.Bad_Shutdown, MGM_RESPONSE_LOCAL_TERMINATION),
			new AbstractMap.SimpleEntry<>(StatusCodes.Bad_ResourceUnavailable, MGM_RESPONSE_SYSTEM_TERMINATION),
			new AbstractMap.SimpleEntry<>(StatusCodes.Bad_StateNotActive, MGM_RESPONSE_NOT_READY),
			new AbstractMap.SimpleEntry<>(StatusCodes.Bad_NotImplemented, MGM_RESPONSE_UNSUPPORTED_CMD),
			new AbstractMap.SimpleEntry<>(StatusCodes.Bad_DataTypeIdUnknown, MGM_RESPONSE_UNSUPPORTED_TYPE),
			new AbstractMap.SimpleEntry<>(StatusCodes.Bad_NotFound, MGM_RESPONSE_NO_SUCH_OBJECT),
			new AbstractMap.SimpleEntry<>(StatusCodes.Bad_NotSupported, MGM_RESPONSE_INVALID_OBJECT),
			new AbstractMap.SimpleEntry<>(StatusCodes.Bad_RequestNotAllowed, MGM_RESPONSE_INVALID_OPERATION),
			new AbstractMap.SimpleEntry<>(StatusCodes.Bad_InvalidState, MGM_RESPONSE_INVALID_STATE),
			new AbstractMap.SimpleEntry<>(StatusCodes.Bad_TcpNotEnoughResources, MGM_RESPONSE_OVERFLOW));

	static {
		// ensure that all entries in the empty response return appropriate empty values
		EMPTY_WATCHES_RESPONSE = DevResponseFactory.eINSTANCE.createResponse();
		EMPTY_WATCHES_RESPONSE.setFblist(DevResponseFactory.eINSTANCE.createFBList());
		EMPTY_WATCHES_RESPONSE.setID("0"); //$NON-NLS-1$
		EMPTY_WATCHES_RESPONSE.setWatches(DevResponseFactory.eINSTANCE.createWatches());
	}

	private Constants() {
		// empty private constructor
	}
}
