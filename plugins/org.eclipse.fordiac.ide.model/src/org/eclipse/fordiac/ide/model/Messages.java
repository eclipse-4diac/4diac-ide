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

	public static String CommonElementImporter_ERROR_DeclarationNotSet;

	/** The Common element importer_ erro r_ missing_ organization. */
	public static String CommonElementImporter_ERROR_Missing_Organization;

	/** The Common element importer_ erro r_ missing author info. */
	public static String CommonElementImporter_ERROR_MissingAuthorInfo;

	/** The Common element importer_ erro r_ missing version info. */
	public static String CommonElementImporter_ERROR_MissingVersionInfo;

	public static String CommonElementImporter_ERROR_MissingPinForParameter;

	/** The Compilable element importer_ erro r_ language not defined. */
	public static String CompilableElementImporter_ERROR_LanguageNotDefined;

	/** The Compilable element importer_ erro r_ product not set. */
	public static String CompilableElementImporter_ERROR_ProductNotSet;

	/** The Compilable element importer_ erro r_ unsupported language. */
	public static String CompilableElementImporter_ERROR_UnsupportedLanguage;

	/** The Compilable element importer_ erro r_ vendor not set. */
	public static String CompilableElementImporter_ERROR_VendorNotSet;

	/** The Compilable element importer_ erro r_ version not set. */
	public static String CompilableElementImporter_ERROR_VersionNotSet;

	public static String DataTypeExporter_UNSUPPORTED_DATA_TYPE;

	public static String DataTypeImporter_UNSUPPORTED_DATATYPE_IN_FILE;

	public static String DataTypeLibrary_InvalidMaxLengthInStringType;

	public static String DataTypeLibrary_MissingDatatype;

	/** The device importer_ erro r_ resource name has to be set. */
	public static String DEVImporter_ERROR_ResourceNameHasToBeSet;

	/** The device importer_ erro r_ resource type has to be set. */
	public static String DEVImporter_ERROR_ResourceTypeHasToBeSet;

	/** The FBT importer_ adapte r_ connectio n_ sourc e_ exception. */
	public static String FBTImporter_ADAPTER_CONNECTION_SOURCE_EXCEPTION;

	/** The FBT importer_ adapte r_ declaratio n_ typ e_ exception. */
	public static String FBTImporter_ADAPTER_DECLARATION_TYPE_EXCEPTION;

	/** The FBT importer_ adapterconnectio n_ des t_ exception. */
	public static String FBTImporter_ADAPTERCONNECTION_DEST_EXCEPTION;

	/** The FBT importer_ adapterdeclaratio n_ nam e_ exception. */
	public static String FBTImporter_ADAPTERDECLARATION_NAME_EXCEPTION;

	/** The FBT importer_ dat a_ connectio n_ destinatio n_ exception. */
	public static String FBTImporter_DATA_CONNECTION_DESTINATION_EXCEPTION;

	/** The FBT importer_ dat a_ connectio n_ sourc e_ exception. */
	public static String FBTImporter_DATA_CONNECTION_SOURCE_EXCEPTION;

	/** The FBT importer_ ecstat e_ nam e_ exception. */
	public static String FBTImporter_ECSTATE_NAME_EXCEPTION;

	/** The FBT importer_ ectranasitio n_ conditio n_ exception. */
	public static String FBTImporter_ECTRANASITION_CONDITION_EXCEPTION;

	/** The FBT importer_ ectransitio n_ des t_ exception. */
	public static String FBTImporter_ECTRANSITION_DEST_EXCEPTION;

	/** The FBT importer_ ectransitio n_ sourc e_ exception. */
	public static String FBTImporter_ECTRANSITION_SOURCE_EXCEPTION;

	/** The FBT importer_ even t_ connectio n_ des t_ exception. */
	public static String FBTImporter_EVENT_CONNECTION_DEST_EXCEPTION;

	/** The FBT importer_ even t_ connectio n_ sourc e_ excepti on. */
	public static String FBTImporter_EVENT_CONNECTION_SOURCE_EXCEPTIOn;

	/** The FBT importer_ f b_ typ e_ exception. */
	public static String FBTImporter_FB_TYPE_EXCEPTION;

	/** The FBT importer_ inpu t_ primitiv e_ even t_ exception. */
	public static String FBTImporter_INPUT_PRIMITIVE_EVENT_EXCEPTION;

	/** The FBT importer_ inpu t_ primitiv e_ exception. */
	public static String FBTImporter_INPUT_PRIMITIVE_EXCEPTION;

	/** The FBT importer_ instancenam e_ exception. */
	public static String FBTImporter_INSTANCENAME_EXCEPTION;

	/** The FBT importer_ othe r_ al g_ missin g_ lan g_ exception. */
	public static String FBTImporter_OTHER_ALG_MISSING_LANG_EXCEPTION;

	/** The FBT importer_ othe r_ al g_ missin g_ tex t_ exception. */
	public static String FBTImporter_OTHER_ALG_MISSING_TEXT_EXCEPTION;

	public static String FBTImporter_OTHER_METHOD_MISSING_LANG_EXCEPTION;

	/** The FBT importer_ outpu t_ primitiv e_ even t_ exception. */
	public static String FBTImporter_OUTPUT_PRIMITIVE_EVENT_EXCEPTION;

	/** The FBT importer_ outpu t_ primitiv e_ exception. */
	public static String FBTImporter_OUTPUT_PRIMITIVE_EXCEPTION;

	/** The FBT importer_ pars e_ fbtyp e_ parseexception. */
	public static String FBTImporter_PARSE_FBTYPE_PARSEEXCEPTION;

	/** The FBT importer_ positio n_ exception. */
	public static String FBTImporter_POSITION_EXCEPTION;

	public static String FBTImporter_POSITION_X_WRONG;

	public static String FBTImporter_POSITION_Y_WRONG;

	/** The FBT importer_ require d_ f b_ typ e_ exception. */
	public static String FBTImporter_REQUIRED_FB_TYPE_EXCEPTION;

	/** The FBT importer_ run g_ expressio n_ exception. */
	public static String FBTImporter_RUNG_EXPRESSION_EXCEPTION;

	/** The FBT importer_ run g_ outpu t_ exception. */
	public static String FBTImporter_RUNG_OUTPUT_EXCEPTION;

	/** The FBT importer_ run g_ outputnam e_ exception. */
	public static String FBTImporter_RUNG_OUTPUTNAME_EXCEPTION;

	/** The FBT importer_ servic e_ interfac e_ leftinterfac e_ exception. */
	public static String FBTImporter_SERVICE_INTERFACE_LEFTINTERFACE_EXCEPTION;

	/** The FBT importer_ servic e_ interfac e_ rightinterfac e_ exception. */
	public static String FBTImporter_SERVICE_INTERFACE_RIGHTINTERFACE_EXCEPTION;

	/** The FBT importer_ servic e_ sequenc e_ nam e_ exception. */
	public static String FBTImporter_SERVICE_SEQUENCE_NAME_EXCEPTION;

	/** The FBT importer_ s t_ textnotse t_ exception. */
	public static String FBTImporter_ST_TEXTNOTSET_EXCEPTION;

	/** The Sub app t importer_ error. */
	public static String SubAppTImporter_ERROR;

	/** The Sub app t importer_ erro r_ sub app type. */
	public static String SubAppTImporter_ERROR_SubAppType;

	/** The Sub app t importer_ erro r_ sub app type name. */
	public static String SubAppTImporter_ERROR_SUBAppTypeName;

	/** The FB type library_ erro r_ not imported. */
	public static String FBTypeLibrary_ERROR_NotImported;

	/** The FB type library_ erro r_ not supported. */
	public static String FBTypeLibrary_ERROR_NotSupported;

	public static String FordiacMarkerHelper_RemoveErrorMarkersFromFile;

	/** Identifier has length zero */
	public static String IdentifierVerifier_ERROR_IdentifierLengthZero;

	/** Identifier has invalid starting symbol */
	public static String IdentifierVerifier_ERROR_InvalidStartSymbol;

	/** Invalid symbol used in identifier */
	public static String IdentifierVerifier_ERROR_InvalidSymbolUsedInIdentifer;

	/** Identifier pattern does not match for unknown reasons */
	public static String IdentifierVerifier_ERROR_UnkownExpressionError;

	/** The Import utils_ erro r_ arraysize_ number format. */
	public static String Import_ERROR_ArraySize_NumberFormat;

	public static String Import_ERROR_NameNotDefined;

	/** The Import utils_ erro r_ input variable type not defined. */
	public static String Import_ERROR_InputVariableTypeNotDefined;

	/** The Import utils_ erro r_ parameter not set. */
	public static String ImportUtils_ERROR_ParameterNotSet;

	/** The Import utils_ erro r_ parameter value not set. */
	public static String ImportUtils_ERROR_ParameterValueNotSet;

	public static String InterfaceElementAnnotations_DuplicateName;

	public static String InterfaceElementAnnotations_MemberNameCollidesWithDataType;

	public static String NameRepository_NameAlreadyExists;

	public static String NameRepository_NameNotAValidIdentifier;

	public static String NameRepository_NameReservedKeyWord;

	public static String TypeLibrary_ImportDataTypeFileDialogTitle;

	public static String TypeLibrary_LoadReferencedFile_DialogTitle;

	public static String TypeLibrary_OverwriteMessage;

	public static String TypeLibrary_ImportAbortByUser;

	public static String TypeLibrary_FBTImportException;

	public static String TypeLibrary_ERROR_ReferencedDataTypeNotFound;

	public static String TypeLibrary_ERROR_ReferencedTypeNotFound;

	public static String TypeLibrary_TypeExists;

	public static String FBNetworkImporter_ConnectionDestinationMissing;
	public static String FBNetworkImporter_ConnectionDestinationNotFound;
	public static String FBNetworkImporter_ConnectionSourceMissing;
	public static String FBNetworkImporter_ConnectionSourceNotFound;
	public static String FBNetworkImporter_MultipleInputs;
	public static String FBNetworkImporter_NameCollision;

	public static String FBNetworkImporter_ConnectionTypeMismatch;

	public static String VALIDATOR_LITERAL_TYPE_INCOMPATIBLE_WITH_INPUT_TYPE;
	public static String VALIDATOR_INVALID_BOOL_LITERAL;
	public static String VALIDATOR_TypeNotSupported;
	public static String VALIDATOR_DatatypeRequiresTypeSpecifier;
	public static String VALIDATOR_NO_BASE_SPECIFIER_FOR_BOOL;
	public static String VALIDATOR_UNKNOWN_LITERAL_TYPE;
	public static String VALIDATOR_CONCRETE_TYPE_SPECIFIER_MANDATORY_FOR_ANYS;
	public static String VALIDATOR_VIRTUAL_DNS_CHARACTERS_OUT_OF_BOUNDS;
	public static String VALIDATOR_VIRTUAL_DNS_MULTIPLE_BOUNDING_CHARACTERS;
	public static String VALIDATOR_VIRTUAL_DNS_ILLEGAL_FORMAT;
	public static String VALIDATOR_ARRAY_MISSES_BRACKETS;
	public static String VALIDATOR_INVALID_DATE_FORMAT;
	public static String VALIDATOR_INVALID_DATE_AND_TIME_FORMAT;
	public static String VALIDATOR_CONSECUTIVE_UNDERSCORES_ERROR_MESSAGE;
	public static String VALIDATOR_INVALID_NUMBER_LITERAL;
	public static String VALIDATOR_INVALID_TIME_LITERAL;
	public static String VALIDATOR_IllegalStringLiteral;
	public static String VALIDATOR_UnevenlyQuotedStringLiteral;
	public static String VALIDATOR_IllegalEscapeInStringLiteral;
	public static String VALIDATOR_InvalidTimeOfDayLiteral;

	public static String VALIDATOR_InvalidTimeUnit;

	public static String Error_SelfInsertion;
	public static String Error_RecursiveType;

	public static String ConnectionHelper_ErrorMarker_Source_Missing;
	public static String ConnectionHelper_ErrorMarker_Dest_Missing;
	public static String ConnectionHelper_pin_not_found;

	public static String LinkConstraints_ClassLinkconstraintsShouldNotBeCreated;
	public static String LinkConstraints_ERROR_NotConnectedToAnEventByAWithConstruct;
	public static String LinkConstraints_STATUSMessage_hasAlreadyInputConnection;
	public static String LinkConstraints_STATUSMessage_hasAlreadyOutputConnection;
	public static String LinkConstraints_STATUSMessage_IN_IN_OUT_OUT_notAllowed;
	public static String LinkConstraints_STATUSMessage_NotCompatible;
	public static String ConnectingIncompatibleInterfaceTypes;

	public static String UpdateFBTypeCommand_type_mismatch;

	public static String ConnectionValidator_VarInOutConnectionCrossesResourceBoundaries;
	public static String ConnectionValidator_VarInOutSourceNotWellDefined;
	public static String ConnectionValidator_VarInOutInputSideHasNoWith;
	public static String ConnectionValidator_VarInOutOutputSideHasNoWith;
	public static String ConnectionValidator_VarInOutArraySizeMismatch;
	public static String ConnectionValidator_VarInOutStringSizeMismatch;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
