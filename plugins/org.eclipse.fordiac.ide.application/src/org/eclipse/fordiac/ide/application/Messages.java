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

/**
 * The Class Messages.
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.application.messages"; //$NON-NLS-1$

	public static String AddFBBookMark_AddBookmark;

	public static String AddFBBookMark_AddBookMarkTitle;

	public static String AddFBBookMark_EnterBookmarkName;

	public static String ConnectionConstraintsPrefernecePage_Description;

	public static String ConnectionConstraintsPrefernecePage_EnableFORTETypeCasts;

	public static String CopyEditPartsAction_Text;

	public static String CreateConnectionSection_CreateConnection;

	/** The Create sub app command_ label create sub app command. */
	public static String CreateSubAppCommand_LABELCreateSubAppCommand;

	public static String CutEditPartsAction_Text;

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

	public static String InstanceCommentEditPart_EMPTY_COMMENT;

	public static String InterfaceElementSection_ConnectionGroup;

	public static String InterfaceElementSection_DeleteConnectionToolTip;

	public static String InterfaceElementSection_InConnections;

	public static String InterfaceElementSection_OutConnections;

	public static String ListFBCreateCommand_FBTypeNotFound;

	/** The Map sub app to command_ staus message_ already mapped. */
	public static String MapSubAppToCommand_STAUSMessage_AlreadyMapped;

	public static String MapToContributionItem_No_Device;

	public static String MapToContributionItem_No_FB_Or_SubApp_Selected;

	public static String NewInstanceCellEditor_SearchForType;

	/** The New sub application action_ new subapplication text. */
	public static String NewSubApplicationAction_NewSubapplicationText;

	/** The Open application editor action_ erro r_ open application editor. */
	public static String OpenApplicationEditorAction_ERROR_OpenApplicationEditor;

	public static String OpenApplicationEditorAction_Name;

	public static String OpenCompositeInstanceViewerAction_Name;
	/**
	 * The Open sub application editor action_ erro r_ open subapplication editor.
	 */
	public static String OpenSubApplicationEditorAction_ERROR_OpenSubapplicationEditor;

	public static String OpenSubApplicationEditorAction_Name;

	public static String PasteEditPartsAction_Text;

	public static String ReconnectDataConnectionCommand_LABEL;

	public static String ReconnectEventConnectionCommand_LABEL;

	public static String ReconnectAdapterConnectionCommand_LABEL;

	public static String SaveAsSubApplicationTypeAction_UntypedSubappError;

	public static String SaveAsSubApplicationTypeAction_UntypedSubappErrorDescription;

	public static String SaveAsSubappHandler_ReplaceDialogText;

	public static String SaveAsSubApplicationTypeAction_WizardTitle;

	public static String SaveAsSubApplicationTypeAction_WizardPageName;

	public static String SaveAsSubApplicationTypeAction_WizardPageTitel;

	public static String SaveAsSubApplicationTypeAction_WizardPageDescription;

	public static String SaveAsSubApplicationTypeAction_WizardPageOpenType;

	public static String SaveAsSubApplicationTypeAction_WizardPageNameLabel;

	public static String SaveAsSubApplicationTypeAction_WizardOverrideTitle;

	public static String SaveAsSubApplicationTypeAction_WizardOverrideMessage;

	public static String StructManipulatorSection_MEMBERVAR_COLUMN_COMMENT;

	public static String StructManipulatorSection_MEMBERVAR_COLUMN_NAME;

	public static String StructManipulatorSection_MEMBERVAR_COLUMN_TYPE;

	public static String StructManipulatorSection_STRUCTURED_TYPE;

	public static String StructManipulatorSection_Contained_variables;

	/** The UIFB network context menu provider_ labe l_ hardware mapping. */
	public static String UIFBNetworkContextMenuProvider_LABEL_HardwareMapping;

	public static String UIFBNetworkContextMenuProvider_InsertFB;

	public static String UIFBNetworkContextMenuProvider_ChangeType;

	public static String UntypeSubappCommand_Label;

	public static String UpdateFBTypeAction_Text;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
