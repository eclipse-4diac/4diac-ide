/********************************************************************************
 * Copyright (c) 2008 - 2010, 2025 Profactor GmbH, TU Wien ACIN
 * 								   Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl
 *    - initial API and implementation and/or initial documentation
 *  Sebastian Hollersbacher
 *	  - externalized translatable strings
 ********************************************************************************/
package org.eclipse.fordiac.ide.model;

import org.eclipse.osgi.util.NLS;

/** The Class Messages. */
@SuppressWarnings("squid:S3008") // tell sonar the java naming convention does not make sense for this class
public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "plugin"; //$NON-NLS-1$

	public static String ArrayValueConverter_IllegalElementValue;

	public static String ArrayValueConverter_InvalidArrayLiteral;

	public static String ArrayValueConverter_InvalidRepeatSyntax;

	public static String CommonElementImporter_ERROR_DeclarationNotSet;
	public static String CommonElementImporter_ERROR_MissingAuthorInfo;
	public static String CommonElementImporter_ERROR_MissingVersionInfo;
	public static String CommonElementImporter_ReservedAttributesValidation;
	public static String CompilableElementImporter_ERROR_ProductNotSet;
	public static String CompilableElementImporter_ERROR_UnsupportedLanguage;
	public static String CompilableElementImporter_ERROR_VendorNotSet;
	public static String CompilableElementImporter_ERROR_VersionNotSet;
	public static String ConnectionAnnotations_DestinationElementMissing;
	public static String ConnectionAnnotations_DestinationEndpointMissing;
	public static String ConnectionAnnotations_DuplicateConnection;

	public static String BaseFBTypeAnnotations_UnusedSimpleFBOutputEvent;

	public static String ConnectionAnnotations_GenericEndpoints;
	public static String ConnectionAnnotations_SourceElementMissing;
	public static String ConnectionAnnotations_SourceEndpointMissing;
	public static String ConnectionAnnotations_TypeMismatch;
	public static String ConnectionAnnotations_TypeMismatchIn2If;
	public static String ConnectionAnnotations_TypeMismatchIf2In;
	public static String ConnectionAnnotations_TypeMismatchInOut;

	public static String ConnectionAnnotations_TypeMismatchInOutIntermediate;

	public static String ConnectionAnnotations_TypeMismatchInOutSeparator;

	public static String ConnectionAnnotations_TypeMismatchInOutVia;
	public static String ConnectionHelper_ErrorMarker_Dest_Missing;
	public static String ConnectionHelper_ErrorMarker_Source_Missing;
	public static String ConnectionHelper_pin_not_found;
	public static String ConnectionValidator_OutputsCannotBeConnectedToVarInOuts;
	public static String ConnectionValidator_VarInOutArraySizeMismatch;
	public static String ConnectionValidator_VarInOutConnectionCrossesResourceBoundaries;
	public static String ConnectionValidator_VarInOutConnectionsIsALoop;
	public static String ConnectionValidator_VarInOutInputSideHasNoWith;
	public static String ConnectionValidator_VarInOutOutputSideHasNoWith;
	public static String ConnectionValidator_VarInOutSourceNotWellDefined;
	public static String ConnectionValidator_VarInOutStringSizeMismatch;
	public static String ConnectionValidator_NegatedConnectionIsNotValid;
	public static String ContainerVarDeclarationAnnotations_MemberInitialValue;

	public static String ContainerVarDeclarationAnnotations_MemberInputConnection;

	public static String DataTypeExporter_UNSUPPORTED_DATA_TYPE;
	public static String DataTypeImporter_UNSUPPORTED_DATATYPE_IN_FILE;
	public static String DataTypeLibrary_InvalidMaxLengthInStringType;
	public static String DataTypeLibrary_MissingDatatype;
	public static String EnumValueConverter_NoSuchValue;

	public static String Error_RecursiveType;
	public static String Error_SelfInsertion;
	public static String Error_TSAinCFB;
	public static String ErrorMarkerInterfaceAnnotations_MissingVariableForAttribute;
	public static String AttributeAnnotations_MissingAttributeDeclaration;

	public static String ErrorMarkerInterfaceAnnotations_MissingVariableForValue;
	public static String FBNetworkAnnotations_CollisionMessage;
	public static String FBNetworkAnnotations_InterfaceBarCollisionMessage;

	public static String FBTImporter_ADAPTER_DECLARATION_TYPE_EXCEPTION;
	public static String FBTImporter_ECTRANASITION_CONDITION_EXCEPTION;
	public static String FBTImporter_ECTRANSITION_DEST_EXCEPTION;
	public static String FBTImporter_ECTRANSITION_SOURCE_EXCEPTION;
	public static String FBTImporter_OTHER_ALG_MISSING_LANG_EXCEPTION;
	public static String FBTImporter_OTHER_METHOD_MISSING_LANG_EXCEPTION;
	public static String FBTImporter_OUTPUT_PRIMITIVE_EVENT_EXCEPTION;
	public static String FBTImporter_OUTPUT_PRIMITIVE_EXCEPTION;
	public static String FBTImporter_POSITION_EXCEPTION;
	public static String FBTImporter_SERVICE_INTERFACE_LEFTINTERFACE_EXCEPTION;
	public static String FBTImporter_SERVICE_INTERFACE_RIGHTINTERFACE_EXCEPTION;
	public static String FordiacTypeResFactory_URINoTypeFile;
	public static String FordiacTypeResource_LoadFromUnsupportedURI;
	public static String FordiacTypeResource_NoContentToSave;
	public static String FordiacTypeResource_NotInWorkspace;
	public static String FordiacTypeResource_SaveToUnsupportedURI;
	public static String FordiacTypeResource_TypeImportError;
	public static String FordiacTypeResource_UnsupportedFileType;
	public static String FordiacTypeResource_UnsupportedContent;
	public static String FordiacTypeResource_XMLError;
	public static String IdentifierVerifier_NameConsecutiveUnderscore;
	public static String IdentifierVerifier_NameNotAValidIdentifier;
	public static String IdentifierVerifier_NameReservedKeyWord;
	public static String IdentifierVerifier_NameTrailingUnderscore;
	public static String IdentifierVerifier_PackageNameMessage;
	public static String IdentifierVerifier_QualifiedNameNotValid;
	public static String IdentifierVerifier_NotMatchingWithFilename;
	public static String IdentifierVerifier_PackageNameMismatch;
	public static String Import_ERROR_InputVariableTypeNotDefined;
	public static String Import_ERROR_NameNotDefined;
	public static String ImportUtils_ERROR_ParameterNotSet;
	public static String ImportUtils_ERROR_ParameterValueNotSet;
	public static String InterfaceElementAnnotations_DuplicateName;
	public static String LinkConstraints_ClassLinkconstraintsShouldNotBeCreated;
	public static String LinkConstraints_ConnectingIncompatibleInterfaceTypes;
	public static String LinkConstraints_ERROR_NotConnectedToAnEventByAWithConstruct;
	public static String LinkConstraints_STATUSMessage_hasAlreadyInputConnection;
	public static String LinkConstraints_STATUSMessage_ChildHasInputConnection;
	public static String LinkConstraints_STATUSMessage_ParentHasInputConnection;
	public static String LinkConstraints_STATUSMessage_hasAlreadyOutputConnection;
	public static String LinkConstraints_STATUSMessage_IN_IN_OUT_OUT_notAllowed;
	public static String LinkConstraints_STATUSMessage_NotCompatible;
	public static String NameRepository_NameAlreadyExists;
	public static String StructValueConverter_IllegalMemberValue;

	public static String StructValueConverter_InvalidStructLiteral;

	public static String StructValueConverter_NoValueConverter;
	public static String TypedElementAnnotations_TypeNotFound;
	public static String TypedElementAnnotations_TypeNotSet;
	public static String TypeLibrary_TypeExists;
	public static String VALIDATOR_ARRAY_MISSES_BRACKETS;
	public static String VALIDATOR_CONSECUTIVE_UNDERSCORES_ERROR_MESSAGE;
	public static String VALIDATOR_DatatypeRequiresTypeSpecifier;
	public static String VALIDATOR_IllegalEscapeInStringLiteral;
	public static String VALIDATOR_IllegalStringLiteral;
	public static String VALIDATOR_INVALID_BOOL_LITERAL;
	public static String VALIDATOR_INVALID_DATE_AND_TIME_FORMAT;
	public static String VALIDATOR_INVALID_DATE_FORMAT;
	public static String VALIDATOR_INVALID_NUMBER_LITERAL;
	public static String VALIDATOR_INVALID_TIME_LITERAL;
	public static String VALIDATOR_InvalidTimeOfDayLiteral;
	public static String VALIDATOR_InvalidTimeUnit;
	public static String VALIDATOR_LITERAL_TYPE_INCOMPATIBLE_WITH_INPUT_TYPE;
	public static String VALIDATOR_TypeNotSupported;
	public static String VALIDATOR_UnevenlyQuotedStringLiteral;
	public static String VALIDATOR_UNKNOWN_LITERAL_TYPE;
	public static String VarDeclarationAnnotations_IllegalVariableLengthArray;

	public static String VarDeclarationAnnotations_MultipleInputConnections;

	public static String VarDeclarationAnnotations_MustNotSpecifyValueForVariableWithVariableArrayBounds;

	public static String VarDeclarationAnnotations_ShouldNotSpecifyValueForGenericVariableInType;

	public static String VarDeclarationAnnotations_ShouldSpecifyValueForGenericVariableInInstance;

	public static String VarDeclarationAnnotations_ValueOverriddenBySubAppInput;

	public static String VarDeclarationAnnotations_VarInOutLeftNotConnected;

	public static String VarDeclarationAnnotations_VarInOutRightNotConnected;

	public static String VarDeclarationAnnotations_VarInOutSubappNetwork;

	// === AttributeTarget Groups ===
	public static String AttributeTarget_General_group;
	public static String AttributeTarget_Types_group;
	public static String AttributeTarget_Instances_group;
	public static String AttributeTarget_InstancePins_group;
	public static String AttributeTarget_TypePins_group;
	public static String AttributeTarget_UntypedSubAppPins_group;
	// === AttributeTarget Display Names ===
	public static String AttributeTarget_FunctionBlock_display;
	public static String AttributeTarget_SubApp_display;
	public static String AttributeTarget_DataTypes_display;
	public static String AttributeTarget_DataTypeMember_display;
	public static String AttributeTarget_AttributeTypes_display;
	public static String AttributeTarget_TypedSubApp_display;
	public static String AttributeTarget_UntypedSubApp_display;
	public static String AttributeTarget_Device_display;
	public static String AttributeTarget_Resource_display;
	public static String AttributeTarget_Segment_display;
	public static String AttributeTarget_Adapter_display;
	public static String AttributeTarget_Event_display;
	public static String AttributeTarget_Data_display;
	public static String AttributeTarget_Systems_display;
	public static String AttributeTarget_Applications_display;
	public static String AttributeTarget_GlobalConstants_display;
	public static String AttributeTarget_Connections_display;
	public static String AttributeTarget_Comments_display;
	public static String AttributeTarget_Groups_display;
	public static String AttributeTarget_Link_display;
	public static String AttributeTarget_ServiceSequence_display;
	// === AttributeTarget Tooltips ===
	public static String AttributeTarget_FBTypes_tooltip;
	public static String AttributeTarget_SubAppTypes_tooltip;
	public static String AttributeTarget_DeviceType_tooltip;
	public static String AttributeTarget_ResourceType_tooltip;
	public static String AttributeTarget_SegmentType_tooltip;
	public static String AttributeTarget_DataTypes_tooltip;
	public static String AttributeTarget_DataTypeMember_tooltip;
	public static String AttributeTarget_AttributeTypes_tooltip;
	public static String AttributeTarget_FBInstances_tooltip;
	public static String AttributeTarget_TypedSubApps_tooltip;
	public static String AttributeTarget_UntypedSubApps_tooltip;
	public static String AttributeTarget_DeviceInstances_tooltip;
	public static String AttributeTarget_ResourceInstances_tooltip;
	public static String AttributeTarget_SegmentInstances_tooltip;
	public static String AttributeTarget_InstanceAdapter_tooltip;
	public static String AttributeTarget_InstanceEvent_tooltip;
	public static String AttributeTarget_InstanceData_tooltip;
	public static String AttributeTarget_TypeAdapter_tooltip;
	public static String AttributeTarget_TypeEvent_tooltip;
	public static String AttributeTarget_TypeData_tooltip;
	public static String AttributeTarget_UntypedSubAppAdapter_tooltip;
	public static String AttributeTarget_UntypedSubAppEvent_tooltip;
	public static String AttributeTarget_UntypedSubAppData_tooltip;
	public static String AttributeTarget_Systems_tooltip;
	public static String AttributeTarget_Applications_tooltip;
	public static String AttributeTarget_GlobalConstants_tooltip;
	public static String AttributeTarget_Connections_tooltip;
	public static String AttributeTarget_Comments_tooltip;
	public static String AttributeTarget_Groups_tooltip;
	public static String AttributeTarget_Links_tooltip;
	public static String AttributeTarget_ServiceSequences_tooltip;

	public static String BaseFBTypeAnnotations_UnsupportedInternalFBType;

	public static String HidePinCommand_PinCannotBeHidden_ConnectedInside;

	public static String SystemImporter_Mapping_WrongString;
	public static String SystemImporter_Mapping_MissingDevice;
	public static String SystemImporter_Mapping_MissingResource;
	public static String SystemImporter_Mapping_LocationFormat;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
