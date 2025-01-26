/********************************************************************************
 * Copyright (c) 2008, 2023 Profactor GmbH, fortiss GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl, Waldemar Eisenmenger
 *    - initial API and implementation and/or initial documentation
 *  Martin Jobst
 *    - add function FB type
 *    - add global constants
 ********************************************************************************/
package org.eclipse.fordiac.ide.model;

/** The Interface LibraryElementTags. */
public final class LibraryElementTags {
	public static final String FB_TYPE = "FBType"; //$NON-NLS-1$
	public static final String ADAPTER_TYPE = "AdapterType"; //$NON-NLS-1$
	public static final String DATA_TYPE = "DataType"; //$NON-NLS-1$

	public static final String FB_TYPE_STRUCT_MUX = "STRUCT_MUX";//$NON-NLS-1$
	public static final String FB_TYPE_STRUCT_DEMUX = "STRUCT_DEMUX";//$NON-NLS-1$
	public static final String FB_TYPE_COMM_MESSAGE = "COMM_MESSAGE";//$NON-NLS-1$

	public static final String SERVICE_ELEMENT = "Service"; //$NON-NLS-1$
	public static final String RIGHT_INTERFACE_ATTRIBUTE = "RightInterface"; //$NON-NLS-1$
	public static final String LEFT_INTERFACE_ATTRIBUTE = "LeftInterface"; //$NON-NLS-1$
	public static final String SERVICE_SEQUENCE_ELEMENT = "ServiceSequence"; //$NON-NLS-1$
	public static final String OUTPUT_PRIMITIVE_ELEMENT = "OutputPrimitive"; //$NON-NLS-1$
	public static final String INPUT_PRIMITIVE_ELEMENT = "InputPrimitive"; //$NON-NLS-1$
	public static final String SERVICE_TRANSACTION_ELEMENT = "ServiceTransaction"; //$NON-NLS-1$
	public static final String INTERFACE_ATTRIBUTE = "Interface"; //$NON-NLS-1$
	public static final String START_STATE_ATTRIBUTE = "StartState"; //$NON-NLS-1$
	public static final String SERVICE_SEQUENCE_TYPE_ATTRIBUTE = "ServiceSequenceType"; //$NON-NLS-1$

	public static final String PARAMETERS_ATTRIBUTE = "Parameters"; //$NON-NLS-1$

	public static final String EXPRESSION_ATTRIBUTE = "Expression"; //$NON-NLS-1$
	public static final String CONDITION_ATTRIBUTE = "Condition"; //$NON-NLS-1$

	public static final String TEXT_ATTRIBUTE = "Text"; //$NON-NLS-1$

	public static final String DX1_ATTRIBUTE = "dx1"; //$NON-NLS-1$
	public static final String DX2_ATTRIBUTE = "dx2"; //$NON-NLS-1$
	public static final String DY_ATTRIBUTE = "dy"; //$NON-NLS-1$
	public static final String X_ATTRIBUTE = "x"; //$NON-NLS-1$
	public static final String Y_ATTRIBUTE = "y"; //$NON-NLS-1$
	public static final String WIDTH_ATTRIBUTE = "width"; //$NON-NLS-1$
	public static final String HEIGHT_ATTRIBUTE = "height"; //$NON-NLS-1$
	public static final String LOCKED_ATTRIBUTE = "locked"; //$NON-NLS-1$

	public static final String CONNECTION_ELEMENT = "Connection"; //$NON-NLS-1$
	public static final String ELEMENT_VISIBLE = "Visible"; //$NON-NLS-1$
	public static final String VAR_CONFIG = "VarConfig"; //$NON-NLS-1$
	public static final String DESTINATION_ATTRIBUTE = "Destination"; //$NON-NLS-1$
	public static final String SOURCE_ATTRIBUTE = "Source"; //$NON-NLS-1$
	public static final String ADAPTERCONNECTIONS_ELEMENT = "AdapterConnections"; //$NON-NLS-1$
	public static final String DATA_CONNECTIONS_ELEMENT = "DataConnections"; //$NON-NLS-1$
	public static final String EVENT_CONNECTIONS_ELEMENT = "EventConnections"; //$NON-NLS-1$
	public static final String RETAIN_ATTRIBUTE = "Retain";

	public static final String INTERNAL_VARS_ELEMENT = "InternalVars"; //$NON-NLS-1$
	public static final String INTERNAL_CONST_VARS_ELEMENT = "InternalConstVars"; //$NON-NLS-1$
	public static final String OUTPUT_ATTRIBUTE = "Output"; //$NON-NLS-1$
	public static final String TYPE_ATTRIBUTE = "Type"; //$NON-NLS-1$
	public static final String VAR_ATTRIBUTE = "Var"; //$NON-NLS-1$
	public static final String WITH_ELEMENT = "With"; //$NON-NLS-1$
	public static final String EVENT_ELEMENT = "Event"; //$NON-NLS-1$

	public static final String ADAPTER_DECLARATION_ELEMENT = "AdapterDeclaration"; //$NON-NLS-1$
	public static final String PLUGS_ELEMENT = "Plugs"; //$NON-NLS-1$
	public static final String SOCKETS_ELEMENT = "Sockets"; //$NON-NLS-1$

	public static final String VAR_DECLARATION_ELEMENT = "VarDeclaration"; //$NON-NLS-1$
	public static final String VALUE_ATTRIBUTE = "Value"; //$NON-NLS-1$
	public static final String ARRAYSIZE_ATTRIBUTE = "ArraySize"; //$NON-NLS-1$
	public static final String INITIALVALUE_ATTRIBUTE = "InitialValue"; //$NON-NLS-1$
	public static final String OUTPUT_VARS_ELEMENT = "OutputVars"; //$NON-NLS-1$
	public static final String INPUT_VARS_ELEMENT = "InputVars"; //$NON-NLS-1$
	public static final String INOUT_VARS_ELEMENT = "InOutVars"; //$NON-NLS-1$
	public static final String EVENT_OUTPUTS = "EventOutputs"; //$NON-NLS-1$
	public static final String EVENT_INPUTS_ELEMENT = "EventInputs"; //$NON-NLS-1$

	public static final String ECC_ELEMENT = "ECC"; //$NON-NLS-1$
	public static final String ECACTION_ELEMENT = "ECAction"; //$NON-NLS-1$
	public static final String ECTRANSITION_ELEMENT = "ECTransition"; //$NON-NLS-1$
	public static final String ECSTATE_ELEMENT = "ECState"; //$NON-NLS-1$
	public static final String ALGORITHM_ELEMENT = "Algorithm"; //$NON-NLS-1$
	public static final String METHOD_ELEMENT = "Method"; //$NON-NLS-1$

	public static final String PARAMETER_ELEMENT = "Parameter"; //$NON-NLS-1$

	public static final String RUNG_ELEMENT = "Rung"; //$NON-NLS-1$
	public static final String LD_ELEMENT = "LD"; //$NON-NLS-1$
	public static final String FBD_ELEMENT = "FBD"; //$NON-NLS-1$

	public static final String LANGUAGE_ATTRIBUTE = "Language"; //$NON-NLS-1$
	public static final String OTHER_ELEMENT = "Other"; //$NON-NLS-1$
	public static final String ST_ELEMENT = "ST"; //$NON-NLS-1$

	public static final String FB_ELEMENT = "FB"; //$NON-NLS-1$
	public static final String GROUP_ELEMENT = "Group"; //$NON-NLS-1$
	public static final String GROUP_NAME = "GroupName"; ////$NON-NLS-1$
	public static final String PIN_COMMENT = "PinComment"; ////$NON-NLS-1$
	public static final String FBNETWORK_ELEMENT = "FBNetwork"; //$NON-NLS-1$
	public static final String BASIC_F_B_ELEMENT = "BasicFB"; //$NON-NLS-1$
	public static final String SIMPLE_F_B_ELEMENT = "SimpleFB"; //$NON-NLS-1$
	public static final String INTERFACE_LIST_ELEMENT = "InterfaceList"; //$NON-NLS-1$
	public static final String COMPILER_INFO_ELEMENT = "CompilerInfo"; //$NON-NLS-1$
	public static final String VERSION_INFO_ELEMENT = "VersionInfo"; //$NON-NLS-1$
	public static final String IDENTIFICATION_ELEMENT = "Identification"; //$NON-NLS-1$
	// currently the comment element is duplicated to open the possibility to change
	// the xml tag if needed
	public static final String COMMENT_ELEMENT = "Comment"; //$NON-NLS-1$
	public static final String COMMENT_ATTRIBUTE = "Comment"; //$NON-NLS-1$
	public static final String NAME_ATTRIBUTE = "Name"; //$NON-NLS-1$
	public static final String DESCRIPTION_ELEMENT = "Description"; //$NON-NLS-1$
	public static final String ORGANIZATION_ATTRIBUTE = "Organization"; //$NON-NLS-1$
	public static final String VERSION_ATTRIBUTE = "Version"; //$NON-NLS-1$
	public static final String AUTHOR_ATTRIBUTE = "Author"; //$NON-NLS-1$

	public static final String SUBAPP_REPRESENTATION_ATTRIBUTE = "Unfolded"; //$NON-NLS-1$

	public static final String FBTYPE_ELEMENT = "FBType"; //$NON-NLS-1$
	public static final String DEVICETYPE_ELEMENT = "DeviceType"; //$NON-NLS-1$
	public static final String RESOURCETYPE_NAME_ELEMENT = "ResourceTypeName"; //$NON-NLS-1$
	public static final String RESOURCE_ELEMENT = "Resource"; //$NON-NLS-1$
	public static final String STANDARD_ATTRIBUTE = "Standard"; //$NON-NLS-1$
	public static final String CLASSIFICATION_ATTRIBUTE = "Classification"; //$NON-NLS-1$
	public static final String APPLICATION_DOMAIN_ATTRIBUTE = "ApplicationDomain"; //$NON-NLS-1$
	public static final String SEGMENT_COMMUNICATION_CONFIG = "CommunicationConfiguration"; //$NON-NLS-1$

	public static final String FUNCTION_ELEMENT = "Function"; //$NON-NLS-1$
	public static final String FUNCTION_BODY_ELEMENT = "FunctionBody"; //$NON-NLS-1$

	public static final String DATE_ATTRIBUTE = "Date"; //$NON-NLS-1$
	public static final String REMARKS_ATTRIBUTE = "Remarks"; //$NON-NLS-1$
	public static final String COMPILER_ELEMENT = "Compiler"; //$NON-NLS-1$
	public static final String IMPORT_ELEMENT = "Import"; //$NON-NLS-1$
	public static final String HEADER_ATTRIBUTE = "header"; //$NON-NLS-1$
	public static final String CLASSDEF_ATTRIBUTE = "classdef"; //$NON-NLS-1$
	public static final String PACKAGE_NAME_ATTRIBUTE = "packageName"; //$NON-NLS-1$
	public static final String DECLARATION_ATTRIBUTE = "declaration"; //$NON-NLS-1$
	public static final String VENDOR_ATTRIBUTE = "Vendor"; //$NON-NLS-1$
	public static final String PRODUCT_ATTRIBUTE = "Product"; //$NON-NLS-1$

	public static final String SUBAPPTYPE_ELEMENT = "SubAppType"; //$NON-NLS-1$
	public static final String SUBAPPINTERFACE_LIST_ELEMENT = "SubAppInterfaceList"; //$NON-NLS-1$
	public static final String SUBAPPNETWORK_ELEMENT = "SubAppNetwork"; //$NON-NLS-1$
	public static final String SUBAPP_ELEMENT = "SubApp"; //$NON-NLS-1$
	public static final String SUBAPP_EVENTINPUTS_ELEMENT = "SubAppEventInputs"; //$NON-NLS-1$
	public static final String SUBAPP_EVENTOUTPUTS_ELEMENT = "SubAppEventOutputs"; //$NON-NLS-1$
	public static final String SUBAPP_EVENT_ELEMENT = "SubAppEvent"; //$NON-NLS-1$

	public static final String RESOURCETYPE_ELEMENT = "ResourceType"; //$NON-NLS-1$
	public static final String FBTYPENAME_ELEMENT = "FBTypeName"; //$NON-NLS-1$
	public static final String APPLICATION_ELEMENT = "Application"; //$NON-NLS-1$
	public static final String DEVICE_ELEMENT = "Device"; //$NON-NLS-1$
	public static final String DEVICE_PROFILE = "Profile"; //$NON-NLS-1$

	public static final String MAPPING_ELEMENT = "Mapping"; //$NON-NLS-1$
	public static final String MAPPING_FROM_ATTRIBUTE = "From"; //$NON-NLS-1$
	public static final String MAPPING_TO_ATTRIBUTE = "To"; //$NON-NLS-1$

	public static final String SEGMENT_ELEMENT = "Segment"; //$NON-NLS-1$
	public static final String SEGMENT_NAME_ELEMENT = "SegmentName"; //$NON-NLS-1$
	public static final String SEGMENT_TYPE_ELEMENT = "SegmentType"; //$NON-NLS-1$
	public static final String SEGMENT_COMM_RESOURCE = "CommResource"; //$NON-NLS-1$
	public static final String LINK_ELEMENT = "Link"; //$NON-NLS-1$

	public static final String ATTRIBUTE_DECLARATION_ELEMENT = "AttributeDeclaration"; //$NON-NLS-1$
	public static final String ATTRIBUTE_ELEMENT = "Attribute"; //$NON-NLS-1$
	public static final String COLOR = "Color"; //$NON-NLS-1$
	public static final String SYSTEM = "System"; //$NON-NLS-1$
	public static final String DOCUMENTATION = "Documentation"; //$NON-NLS-1$

	public static final String TYPENAME_FMOVE = "F_MOVE"; //$NON-NLS-1$
	public static final String F_MOVE_CONFIG = "DataType"; //$NON-NLS-1$
	public static final String TYPENAME_MUX = "STRUCT_MUX"; //$NON-NLS-1$
	public static final String TYPENAME_DEMUX = "STRUCT_DEMUX"; //$NON-NLS-1$
	public static final String STRUCT_MANIPULATOR_CONFIG = "StructuredType"; //$NON-NLS-1$
	public static final String DEMUX_VISIBLE_CHILDREN = "VisibleChildren"; //$NON-NLS-1$
	public static final String VARIABLE_SEPARATOR = ","; //$NON-NLS-1$

	public static final String STRUCTURED_TYPE_ELEMENT = "StructuredType"; //$NON-NLS-1$
	public static final String ENUMERATED_TYPE_ELEMENT = "EnumeratedType"; //$NON-NLS-1$
	public static final String ENUMERATED_VALUE_ELEMENT = "EnumeratedValue"; //$NON-NLS-1$
	public static final String ASN1_TAG = "ASN1Tag"; //$NON-NLS-1$

	public static final String GLOBAL_CONSTANTS_ELEMENT = "GlobalConstants"; //$NON-NLS-1$
	public static final String ORIGINAL_SOURCE_ELEMENT = "OriginalSource"; //$NON-NLS-1$

	public static final String DIRECTLY_DERIVED_TYPE = "DirectlyDerivedType"; //$NON-NLS-1$
	public static final String BASE_TYPE_ATTRIBUTE = "BaseType"; //$NON-NLS-1$
	public static final String TARGET_ATTRIBUTE_DEFINITION = "TARGET"; //$NON-NLS-1$
	public static final String ELEMENT_INOUTVISIBLEOUT = "VisibleOutSide"; //$NON-NLS-1$

	private LibraryElementTags() {
		throw new UnsupportedOperationException("Class should not be instantiated!"); //$NON-NLS-1$
	}

}