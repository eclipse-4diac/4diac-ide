/*******************************************************************************
 * Copyright (c) 2017, 2024 fortiss GmbH, Andrea Zoitl, Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Andrea Zoitl
 *     - Externalized all translatable strings
 *   Martin Jobst
 *     - add strings for Build Path property page
 *******************************************************************************/

package org.eclipse.fordiac.ide.typemanagement;

import org.eclipse.osgi.util.NLS;

@SuppressWarnings("squid:S3008") // tell sonar the java naming convention does not make sense for this class
public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "plugin"; //$NON-NLS-1$

	public static String AbstractCommandChange_CannotExecuteCommand;

	public static String AbstractCommandChange_NoSuchElement;

	public static String AttributeValueChange_AttributeValueChanged;

	public static String BuildPathPropertyPage_AddExclude;

	public static String BuildPathPropertyPage_AddExcludePattern;

	public static String BuildPathPropertyPage_AddFolder;

	public static String BuildPathPropertyPage_AddInclude;

	public static String BuildPathPropertyPage_AddIncludePattern;

	public static String BuildPathPropertyPage_AddSourceFolder;

	public static String BuildPathPropertyPage_Edit;

	public static String BuildPathPropertyPage_EditAttribute;

	public static String BuildPathPropertyPage_EditPattern;

	public static String BuildPathPropertyPage_EditSourceFolder;

	public static String BuildPathPropertyPage_EmptyPatternError;

	public static String BuildPathPropertyPage_EnterNewPattern;

	public static String BuildPathPropertyPage_EnterNewValue;

	public static String BuildPathPropertyPage_Remove;

	public static String BuildPathPropertyPage_SaveError;

	public static String BuildPathPropertyPage_Toggle;

	public static String CommandRedoChange_CannotRedoCommand;

	public static String CommandUndoChange_CannotUndoCommand;

	public static String ConnectionsToStructRefactoring_ChangeName;
	public static String ConnectionsToStructRefactoring_CheckPreconditions;
	public static String ConnectionsToStructRefactoring_IncompatibleStructType;
	public static String ConnectionsToStructRefactoring_InvalidName;
	public static String ConnectionsToStructRefactoring_InvalidType;
	public static String ConnectionsToStructRefactoring_NameExists;
	public static String ConnectionsToStructRefactoring_ProgressText;
	public static String ConnectionsToStructRefactoring_RefactoringTitle;
	public static String ConnectionsToStructRefactoring_SameFBSameName;
	public static String ConnectionsToStructRefactoring_SelectionIsNoStruct;
	public static String ConnectionsToStructRefactoring_EventConflict;
	public static String ConnectionsToStructRefactoring_IsServiceFB;
	public static String ConnectionsToStructRefactoring_NoFB;
	public static String ConnectionsToStructRefactoring_PortsNoMatch;
	public static String ConnectionsToStructRefactoring_StructExists;
	public static String ConnectionsToStructRefactoring_Source;
	public static String ConnectionsToStructRefactoring_Destination;
	public static String ConnectionsToStructRefactoring_Output;
	public static String ConnectionsToStructRefactoring_Input;
	public static String ConnectionsToStructRefactoring_MissingStructManipulator;
	public static String CreateStructChange_Name;
	public static String RepairBrokenConnectionChange_Name;
	public static String ReplaceVarsWithStructChange_Inputs;
	public static String ReplaceVarsWithStructChange_Outputs;
	public static String ReplaceVarsWithStructChange_Replace;
	public static String ReplaceVarsWithStructChange_Struct;
	public static String SystemConnectStructChange_Name;
	public static String SystemUpdateFBChange_Name;
	public static String ConnectionsToStructRefactoring_FBNotInSystem;
	public static String ReplaceVarsWithStructChange_NotContained;

	public static String DataTypeChange_TypeDeclarationChanged;

	public static String DeleteFBTypeParticipant_Name;
	public static String DeleteFBTypeParticipant_TypeInUseWarning;
	public static String DeleteFBTypeParticipant_Change_DeleteFBTypeInterface;
	public static String DeleteFBTypeParticipant_Change_UpdateMemberVariable;
	public static String DeleteFBTypeParticipant_Change_DeleteSubappPins;
	public static String DeleteFBTypeParticipant_Change_SafeDeletionChangeTitle;
	public static String DeleteFBTypeParticipant_Change_UpdateFBType;
	public static String DeleteFBTypeParticipant_Change_UpdateInstance;
	public static String DeleteFBTypeParticipant_Change_UpdateInternalFB;
	public static String DeleteFBTypeParticipant_Change_UpdateManipulator;
	public static String DeleteFBTypeParticipant_Change_UpdateSubappPins;
	public static String FBTypeComposedAdapterFactory_FBTypecomposedAdapterFactoryShouldNotBeInsantiated;
	public static String IFordiacPreviewChange_Reconnect0;
	public static String ConfigurableChange_noChangeErrorMessage;

	public static String ImportChange_ImportedNamespaceChanged;

	public static String InitialValueChange_InitialValueChanged;

	public static String NewFBTypeWizard_TemplateNotAvailable;
	public static String NewFBTypeWizardPage_TypeAlreadyExists;
	public static String NewFBTypeWizardPage_FileAlreadyExists;
	public static String NewFBTypeWizardPage_CreateNewType;
	public static String NewFBTypeWizardPage_CreateNewTypeFromTemplateType;
	public static String NewFBTypeWizardPage_CouldNotFindTemplateFiles;
	public static String NewFBTypeWizardPage_EmptyTypenameIsNotValid;
	public static String NewFBTypeWizardPage_NewTypePage;
	public static String NewFBTypeWizardPage_NoTypeSelected;
	public static String NewFBTypeWizardPage_NoTypeTemplatesFoundCheckTemplatesDirectory;
	public static String NewFBTypeWizardPage_OpenTypeForEditingWhenDone;
	public static String NewFBTypeWizardPage_InvalidOrNoComment;
	public static String NewVersionOf;
	public static String RenameElementChange_InvalidName;

	public static String RenameElementChange_NameChanged;
	public static String RenameElementRefactoringParticipant_Name;
	public static String RenameElementRefactoringProcessor_Name;

	public static String RenameElementRefactoringProcessor_RenamePinInType;
	public static String RenameElementRefactoringWizardPage_Name;
	public static String RenameType_Name;
	public static String RenameType_TypeExists;
	public static String OpenTypeHandler_EDITOR_OPEN_ERROR_MESSAGE;
	public static String OpenTypeHandler_OPEN_TYPE_ERROR_TITLE;
	public static String OpenTypeHandler_OPEN_TYPE_TITLE;
	public static String OpenTypeHandler_DialogMessage;
	public static String OpenTypeHandler_SearchInAllProjects;

	public static String typeLibraryHasAlreadyBeenExtracted;
	public static String typeManagementPreferencePageTitle;
	public static String typeManagementPreferencePageIdentificationTitle;
	public static String typeManagementPreferencePageVersionTitle;
	public static String typeManagementPreferencePageDescription;

	public static String Refactoring_RenameFromTo;
	public static String Refactoring_AffectedStruct;
	public static String Refactoring_AffectedInstancesOfFB;
	public static String Refactoring_UpdateTypeEntryChange;

	public static String PreviewChange_DeleteChoice;
	public static String PreviewChange_ChangeToAnyStruct;
	public static String PreviewChange_ReplaceWithMarker;
	public static String PreviewChange_NoChange;

	public static String DeleteLibraryParticipant_Name;
	public static String DeleteLibraryParticipant_Change_Title;
	public static String AddLibraryDependency_Change_Title;

	public static String SafeStructDeletionChange_RootNodeChangeText;

	public static String UpdateUntypedSubappPinChange_0;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
