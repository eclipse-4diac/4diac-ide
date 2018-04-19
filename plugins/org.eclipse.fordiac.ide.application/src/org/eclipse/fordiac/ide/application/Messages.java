/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application;

import org.eclipse.osgi.util.NLS;

/**
 * The Class Messages.
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.application.messages"; //$NON-NLS-1$



	public static String FBFigure_TYPE_NOT_SET;
	
	/** The Command util_ erro r_ reopen sub app. */
	public static String CommandUtil_ERROR_ReopenSubApp;
	
	public static String CreateConnectionSection_CreateConnection;


	/** The Create sub app command_ label create sub app command. */
	public static String CreateSubAppCommand_LABELCreateSubAppCommand;
	
	public static String NewApplicationCommand_LABEL_NewApplication;
	
	/** The FB edit part_ erro r_ unsupported fb type. */
	public static String FBEditPart_ERROR_UnsupportedFBType;
		
	/** The FB figure_ no t_ define d_ text. */
	public static String FBFigure_NOT_DEFINED_Text;
	
	/** The FB tooltip figure_ labe l_ application. */
	public static String FBTooltipFigure_LABEL_Application;
	
	/** The FB tooltip figure_ labe l_ mapped to. */
	public static String FBTooltipFigure_LABEL_MappedTo;
	
	/** The FB tooltip figure_ labe l_ system. */
	public static String FBTooltipFigure_LABEL_System;
	
	/** The Flatten sub app command_ labe l_ flatten sub app command. */
	public static String FlattenSubAppCommand_LABEL_FlattenSubAppCommand;
			
	
	/** The Map sub app to command_ staus message_ already mapped. */
	public static String MapSubAppToCommand_STAUSMessage_AlreadyMapped;
	
	/** The New sub application action_ new subapplication text. */
	public static String NewSubApplicationAction_NewSubapplicationText;
	
	/** The Open application editor action_ erro r_ open application editor. */
	public static String OpenApplicationEditorAction_ERROR_OpenApplicationEditor;


	public static String OpenApplicationEditorAction_Name;


	public static String OpenCompositeInstanceViewerAction_Name;
	
	/** The Open sub application editor action_ erro r_ open subapplication editor. */
	public static String OpenSubApplicationEditorAction_ERROR_OpenSubapplicationEditor;


	public static String OpenSubApplicationEditorAction_Name;
			
	public static String ReconnectDataConnectionCommand_LABEL;
	public static String ReconnectEventConnectionCommand_LABEL;
	public static String ReconnectAdapterConnectionCommand_LABEL;
	
	
	/** The Sub app for fb network figure_ labe l_ not defined. */
	public static String SubAppForFbNetworkFigure_LABEL_NotDefined;
	
	/** The Sub app tooltip figure_ labe l_ application. */
	public static String SubAppTooltipFigure_LABEL_Application;
	
	/** The Sub app tooltip figure_ labe l_ mapped to. */
	public static String SubAppTooltipFigure_LABEL_MappedTo;
	
	/** The Sub app tooltip figure_ labe l_ system. */
	public static String SubAppTooltipFigure_LABEL_System;
	
	/** The UIFB network context menu provider_ labe l_ hardware mapping. */
	public static String UIFBNetworkContextMenuProvider_LABEL_HardwareMapping;
	
	/** The Unmap action_ unmap_ label. */
	public static String UnmapAction_Unmap_Label;

	public static String SaveAsSubApplicationTypeAction_SaveAsSubApplicationTypeText;

	public static String SaveAsSubApplicationTypeAction_UntypedSubappError;

	public static String SaveAsSubApplicationTypeAction_UntypedSubappErrorDescription;
	
	public static String SaveAsSubApplicationTypeAction_WizardTitle;

	public static String SaveAsSubApplicationTypeAction_WizardPageName;

	public static String SaveAsSubApplicationTypeAction_WizardPageTitel;

	public static String SaveAsSubApplicationTypeAction_WizardPageDescription;

	public static String SaveAsSubApplicationTypeAction_WizardPageOpenType;

	public static String SaveAsSubApplicationTypeAction_WizardPageNameLabel;

	public static String SaveAsSubApplicationTypeAction_WizardOverrideTitle;

	public static String SaveAsSubApplicationTypeAction_WizardOverrideMessage;
	
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
