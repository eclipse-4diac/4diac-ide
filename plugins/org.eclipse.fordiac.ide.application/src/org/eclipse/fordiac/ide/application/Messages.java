/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 				 2019 - 2020 Andrea Zoitl
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Andrea Zoitl
 *     - Externalized all translatable strings
 *******************************************************************************/
package org.eclipse.fordiac.ide.application;

import org.eclipse.osgi.util.NLS;

/** The Class Messages. */
@SuppressWarnings("squid:S3008") // tell sonar the java naming convention does not make sense for this class
public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "plugin"; //$NON-NLS-1$

	public static String AbstractCommandMarkerResolution_CannotCreateCommand;

	public static String AbstractCommandMarkerResolution_CannotExecuteCommand;

	public static String AbstractCommandMarkerResolution_CommitTask;

	public static String AbstractCommandMarkerResolution_NoSuchElement;

	public static String AbstractCommandMarkerResolution_PerformTask;

	public static String AddFBBookMark_AddBookmark;

	public static String AddFBBookMark_AddBookMarkTitle;

	public static String AddFBBookMark_EnterBookmarkName;

	public static String CommentPropertySection_DataInputs;

	public static String CommentPropertySection_DataOutputs;

	public static String ConnectionConstraintsPrefernecePage_Description;

	public static String ConnectionConstraintsPrefernecePage_EnableFORTETypeCasts;

	public static String ConvertToStructHandler_ErrorMessage;

	public static String ConvertToStructHandler_ErrorTitle;

	public static String ConvertToStructHandler_NotAllowedReasons;

	public static String ConvertToStructHandler_OperationNotPossible;

	public static String ConvertToStructHandler_Title;

	public static String ConfigurableMoveFBSection_DataType;

	public static String CopyEditPartsAction_Text;

	public static String CreateConnectionSection_CreateConnection;

	public static String CreateGroupPasteCommand;

	/** The Create sub app command_ label create sub app command. */
	public static String CreateSubAppCommand_LABELCreateSubAppCommand;

	public static String CutEditPartsAction_Text;

	public static String EnterPackageName_Text;

	public static String ErrorMarkerFBNEditPart_ErrorMarker;

	public static String ErrorMarkerFBNEditPart_OldType;

	/** The FB edit part_ erro r_ unsupported fb type. */
	public static String FBEditPart_ERROR_UnsupportedFBType;

	public static String FBNetworkElementTooltipFigure_VersionLabel;

	public static String FBPaletteViewer_SearchForType;

	public static String FBPaletteViewer_SelectConnectionEnd;

	/** The FB tooltip figure_ labe l_ application. */
	public static String FBTooltipFigure_LABEL_Application;

	/** The FB tooltip figure_ labe l_ mapped to. */
	public static String FBTooltipFigure_LABEL_MappedTo;

	/** The FB tooltip figure_ labe l_ system. */
	public static String FBTooltipFigure_LABEL_System;

	/** The Flatten sub app command_ labe l_ flatten sub app command. */
	public static String FlattenSubAppCommand_LABEL_FlattenSubAppCommand;

	public static String FunctionBlock;

	public static String InstanceCommentEditPart_EMPTY_COMMENT;

	public static String InterfaceElementSection_ConnectionGroup;

	public static String InterfaceElementSection_DeleteConnectionToolTip;

	public static String InterfaceElementSection_InConnections;

	public static String InterfaceElementSection_Instance;

	public static String InterfaceElementSection_OutConnections;

	public static String InterfaceElementSection_InterfaceElement;

	public static String ListFBCreateCommand_FBTypeNotFound;

	/** The Map sub app to command_ staus message_ already mapped. */
	public static String MapSubAppToCommand_STAUSMessage_AlreadyMapped;

	public static String MapToContributionItem_No_Device;

	public static String MapToContributionItem_No_FB_Or_SubApp_Selected;

	public static String MapToDialog_Message;

	public static String MapToDialog_Title;

	public static String MonitoringEditPart_Not_Available;

	public static String MoveElementDialogTitle;

	/** The New sub application action_ new subapplication text. */
	public static String NewSubApplicationAction_NewSubapplicationText;

	/** The Open application editor action_ erro r_ open application editor. */
	public static String OpenApplicationEditorAction_ERROR_OpenApplicationEditor;

	public static String OpenApplicationEditorAction_Name;

	/**
	 * The Open sub application editor action_ erro r_ open subapplication editor.
	 */
	public static String OpenSubApplicationEditorAction_ERROR_OpenSubapplicationEditor;

	public static String OpenSubApplicationEditorAction_Name;

	public static String PasteEditPartsAction_Text;

	public static String SaveAsStructTypeWizard_WindowTitle;

	public static String SaveAsStructTypeWizard_WizardPageName;

	public static String SaveAsStructWizard_WizardPageName;

	public static String SaveAsStructWizardPage_ConvertSourceElements;

	public static String SaveAsStructWizardPage_TypeName;

	public static String SaveAsStructWizardPage_WizardPageDescription;

	public static String SaveAsStructWizardPage_WizardPageTitle;

	public static String SaveAsSubappHandler_ReplaceDialogText;

	public static String SaveAsSubApplicationTypeAction_WizardTitle;

	public static String SaveAsSubApplicationTypeAction_WizardPageName;

	public static String SaveAsSubApplicationTypeAction_WizardPageTitle;

	public static String SaveAsSubApplicationTypeAction_WizardPageDescription;

	public static String SaveAsSubApplicationTypeAction_WizardPageOpenType;

	public static String SaveAsSubApplicationTypeAction_WizardPageNameLabel;

	public static String SaveAsSubApplicationTypeAction_WizardOverrideTitle;

	public static String SaveAsSubApplicationTypeAction_WizardOverrideMessage;

	public static String SaveAsSubApplicationTypeAction_TemplateMissingErrorTitle;

	public static String SaveAsSubApplicationTypeAction_TemplateMissingErrorMessage;

	public static String SaveAsWizardPage_SaveAsStructType_Description;

	public static String SaveAsWizardPage_SaveAsStructType_PageName;

	public static String SaveAsWizardPage_SaveAsStructType_WizardPageTitle;

	public static String SearchStringProperties;

	public static String StructManipulatorSection_MEMBERVAR_COLUMN_COMMENT;

	public static String StructManipulatorSection_MEMBERVAR_COLUMN_NAME;

	public static String StructManipulatorSection_MEMBERVAR_COLUMN_TYPE;

	public static String StructManipulatorSection_STRUCTURED_TYPE;

	public static String StructManipulatorSection_Contained_variables;

	public static String TransferInstanceComments_WizardTitle;

	public static String TransferInstanceComments_TransferLabel;

	/** The UIFB network context menu provider_ labe l_ hardware mapping. */
	public static String UIFBNetworkContextMenuProvider_LABEL_HardwareMapping;

	public static String UIFBNetworkContextMenuProvider_InsertFB;

	public static String UIFBNetworkContextMenuProvider_ChangeType;

	public static String Untyped;

	public static String UpdateFBTypeAction_Text;

	public static String PasteRecreateNotPossible;

	public static String ToggleSubAppRepresentation_Collapse;

	public static String ToggleSubAppRepresentation_Expand;

	public static String ToggleConnections_Show;

	public static String ToggleConnections_Hide;

	public static String ToggleConnections_Singular_Hide;

	public static String ToggleConnections_Singular_Show;

	public static String ToggleConnections_Target_Show;

	public static String VarConfigurationSection_VarConfigs;

	public static String InfoPropertySection_SystemInfo;

	public static String InfoPropertySection_Number_Of_Connections_Label;

	public static String InfoPropertySection_Number_Of_Used_Types_Label;

	public static String InfoPropertySection_Number_Of_Untyped_SubApps_Label;

	public static String InfoPropertySection_Number_Of_All_Instances_Label;

	public static String InfoPropertySection_CheckBox_Label;

	public static String InfoPropertySection_All_Types_And_Counts_Label;

	public static String InfoPropertySection_Combo_Text_SortBy;

	public static String InfoPropertySection_Combo_Text_Name;

	public static String InfoPropertySection_Combo_Text_CountASC;

	public static String InfoPropertySection_Combo_Text_CountDESC;

	public static String QuickFixDialog_Title;

	public static String QuickFixDialog_NoResolutionsFound;

	public static String QuickFixDialog_NoResolutionsFoundForMultiSelection;

	public static String QuickFixDialog_Message;

	public static String QuickFixDialog_SelectAll;

	public static String QuickFixDialog_DeselectAll;

	public static String QuickFixDialog_Resolutions_List_Title;

	public static String QuickFixDialog_Problems_List_Title;

	public static String QuickFixDialog_Problems_List_Location;

	public static String QuickFixDialog_Problems_List_Resource;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
