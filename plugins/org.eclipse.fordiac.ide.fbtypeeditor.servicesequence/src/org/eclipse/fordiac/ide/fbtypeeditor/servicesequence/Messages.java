/*******************************************************************************
 * Copyright (c) 2020 Andrea Zoitl
 *               2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Andrea Zoitl
 *      - externalized all translatable strings
 *    Melanie Winter - clean up
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence;

import org.eclipse.osgi.util.NLS;

@SuppressWarnings("squid:S3008") // tell sonar the java naming convention does not make sense for this class
public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.messages"; //$NON-NLS-1$

	public static String CreateOutputPrimitiveCommand_NotAvailable;
	public static String InterfaceSelectorButton_Interface;
	public static String PrimitiveSection_CreateControls_PrimitiveSpecification;
	public static String PrimitiveSection_CreateEventSection_Event;
	public static String PrimitiveSection_CustomEvent;
	public static String PrimitiveSection_CreatePrimitiveSection_Interface;
	public static String PrimitiveSection_DataQualifyingToolTip;
	public static String ServiceInterfacePaletteFactory_DrawerName;
	public static String ServiceInterfacePaletteFactory_LeftInterface;
	public static String ServiceInterfacePaletteFactory_OutputPrimitive;
	public static String ServiceInterfacePaletteFactory_OutputPrimitive_Desc;
	public static String ServiceInterfacePaletteFactory_RightInterface;
	public static String ServiceInterfacePaletteFactory_ServiceTransaction;
	public static String ServiceInterfacePaletteFactory_ServiceTransaction_Desc;
	public static String ServiceSection_LeftInterface;
	public static String ServiceSection_Comment;
	public static String ServiceSection_Name;
	public static String ServiceSection_RightInterface;
	public static String ServiceSection_ServiceSequences;
	public static String ServiceSection_StartState;
	public static String ServiceSection_Type;
	public static String ServiceSequenceEditor_Service;
	public static String ServiceSequenceSection_Index;
	public static String ServiceSequenceSection_InputPrimitive;
	public static String ServiceSequenceSection_OutputPrimitives;
	public static String ServiceSequenceSection_ServiceSequence;
	public static String ServiceSequenceSection_Transaction;
	public static String TransactionSection_CollectOutputPrimitiveGroupName_PrimitivesRightInterface;
	public static String TransactionSection_CollectOutputPrimitiveGroupName_AndLeftInterface;
	public static String TransactionSection_CreateTableLayout_Parameter;
	public static String TransactionSection_CreateTableLayout_Index;
	public static String TransactionSection_InputPrimitive;
	public static String TransactionSection_OutputPrimitives;
	public static String TransactionSection_Parameter;
	public static String RunServiceSequenceHandler_InconsistencyDetected;
	public static String RunServiceSequenceHandler_SequenceDoesNotMatchECC;
	public static String RunServiceSequenceHandler_SequenceMatchesECC;
	public static String RunServiceSequenceHandler_Success;
	public static String RecordSequenceDialog_AddRandomEvents;

	public static String RecordSequenceDialog_AddRandomValues;

	public static String RecordSequenceDialog_Button;

	public static String RecordSequenceDialog_Info;

	public static String RecordSequenceDialog_InitialData;

	public static String RecordSequenceDialog_InputEvents;

	public static String RecordSequenceDialog_ProvideInputs;

	public static String RecordSequenceDialog_SpecifyEventsTooltipText;

	public static String RecordSequenceDialog_SpecifyParametersTooltipText;

	public static String RecordSequenceDialog_StartState;

	public static String RecordSequenceDialog_Title;

	public static String RecordServiceSequenceHandler_APPEND;
	public static String RecordServiceSequenceHandler_CHECK_VARIABLE_NAMES;
	public static String RecordServiceSequenceHandler_CONFIGURATION;
	public static String RecordServiceSequenceHandler_INPUT_DATA;
	public static String RecordServiceSequenceHandler_INPUT_EVENTS;
	public static String RecordServiceSequenceHandler_PARAMETERS;
	public static String RecordServiceSequenceHandler_PROBLEM;
	public static String RecordServiceSequenceHandler_RECORD_SEQUENCE;
	public static String RecordServiceSequenceHandler_RUN;
	public static String RecordServiceSequenceHandler_RANDOM;
	public static String RecordServiceSequenceHandler_COUNT;
	public static String ServiceSequenceAssignView_RELOAD;
	public static String ServiceSequenceAssignView_NAME;
	public static String ServiceSequenceAssignView_INITIAL_EVENTS;
	public static String ServiceSequenceAssignView_INITIAL_PARAMETERS;
	public static String ServiceSequenceAssignView_EVENT_COUNT;
	public static String ServiceSequenceAssignView_AUTO_CONTINUE;
	public static String ServiceSequenceAssignView_GENERATE_SEQUENCE;
	public static String ServiceSequenceAssignView_POSSIBLE;
	public static String ServiceSequenceAssignView_CONDITIONAL;
	public static String ServiceSequenceAssignView_ALWAYS;
	public static String ServiceSequenceAssignView_FORBIDDEN;
	public static String ServiceSequenceAssignView_SKIP;
	public static String ServiceSequenceAssignView_START;
	public static String Coverage_NAME;
	public static String Coverage_VISITED_STATES;
	public static String Coverage_VISITED_PATHS;
	public static String Coverage_NODECOVERAGE;
	public static String Coverage_PATHCOVERAGE;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}