/********************************************************************************
 * Copyright (c) 2008 - 2010  Profactor GmbH, TU Wien ACIN
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
 ********************************************************************************/
package org.eclipse.fordiac.ide.model;

import org.eclipse.osgi.util.NLS;

/** The Class Messages. */
@SuppressWarnings("squid:S3008") // tell sonar the java naming convention does not make sense for this class
public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.model.messages"; //$NON-NLS-1$

	public static String ArrayValueConverter_IllegalElementValue;

	public static String ArrayValueConverter_InvalidRepeatSyntax;

	public static String CommonElementImporter_ERROR_DeclarationNotSet;
	public static String CommonElementImporter_ERROR_MissingAuthorInfo;
	public static String CommonElementImporter_ERROR_MissingVersionInfo;
	public static String CompilableElementImporter_ERROR_ProductNotSet;
	public static String CompilableElementImporter_ERROR_UnsupportedLanguage;
	public static String CompilableElementImporter_ERROR_VendorNotSet;
	public static String CompilableElementImporter_ERROR_VersionNotSet;
	public static String ConnectionAnnotations_DestinationElementMissing;
	public static String ConnectionAnnotations_DestinationEndpointMissing;
	public static String ConnectionAnnotations_DuplicateConnection;

	public static String ConnectionAnnotations_GenericEndpoints;
	public static String ConnectionAnnotations_SourceElementMissing;
	public static String ConnectionAnnotations_SourceEndpointMissing;
	public static String ConnectionAnnotations_TypeMismatch;

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
	public static String DataTypeExporter_UNSUPPORTED_DATA_TYPE;
	public static String DataTypeImporter_UNSUPPORTED_DATATYPE_IN_FILE;
	public static String DataTypeLibrary_InvalidMaxLengthInStringType;
	public static String DataTypeLibrary_MissingDatatype;
	public static String EnumValueConverter_NoSuchValue;

	public static String Error_RecursiveType;
	public static String Error_SelfInsertion;
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
	public static String FordiacTypeResource_LoadFromUnsupportedURI;
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
	public static String Import_ERROR_InputVariableTypeNotDefined;
	public static String Import_ERROR_NameNotDefined;
	public static String ImportUtils_ERROR_ParameterNotSet;
	public static String ImportUtils_ERROR_ParameterValueNotSet;
	public static String InterfaceElementAnnotations_DuplicateName;
	public static String InterfaceElementAnnotations_MemberNameCollidesWithDataType;
	public static String LinkConstraints_ClassLinkconstraintsShouldNotBeCreated;
	public static String LinkConstraints_ConnectingIncompatibleInterfaceTypes;
	public static String LinkConstraints_ERROR_NotConnectedToAnEventByAWithConstruct;
	public static String LinkConstraints_STATUSMessage_hasAlreadyInputConnection;
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
	public static String VarDeclarationAnnotations_MultipleInputConnections;

	public static String VarDeclarationAnnotations_MustNotSpecifyValueForVariableWithVariableArrayBounds;

	public static String VarDeclarationAnnotations_ShouldNotSpecifyValueForGenericVariableInType;

	public static String VarDeclarationAnnotations_ShouldSpecifyValueForGenericVariableInInstance;

	public static String VarDeclarationAnnotations_VarInOutLeftNotConnected;

	public static String VarDeclarationAnnotations_VarInOutRightNotConnected;

	public static String VarDeclarationAnnotations_VarInOutSubappNetwork;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
