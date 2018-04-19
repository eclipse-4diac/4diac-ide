/*******************************************************************************
 * Copyright (c) 2008, 2014 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui;

import org.eclipse.osgi.util.NLS;

/**
 * The Class Messages.
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.systemmanagement.ui.messages"; //$NON-NLS-1$


	public static String OpenEditorAction_text;

	public static String OpenEditorProvider_OpenWithMenu_label;
	
	/** The New application page_ applcation name label. */
	public static String NewElementPage_ElementNameLabel;
	
	/** The New application page_ error message_ empty app name. */
	public static String NewElementPage_ErrorMessage_EmptyElementName;
	
	/** The New application page_ error message invalid app name. */
	public static String NewApplicationPage_ErrorMessageInvalidAppName;
	
	public static String NewApplicationPage_OpenApplicationForEditing;
	
	/** The New application page_ error message no system selected. */
	public static String NewElementPage_ErrorMessageNoSystemSelected;
	
	/** The New application page_ parent system label. */
	public static String NewElementPage_ParentSystemLabel;
	
	/** The New application wizard_ comment. */
	public static String NewApplicationWizard_Comment;
	
	/** The New application wizard_ description. */
	public static String NewApplicationWizard_Description;
	
	/** The New application wizard_ title. */
	public static String NewApplicationWizard_Title;
	
	public static String NewSystemWizard_ShowAdvanced;
	
	public static String NewSystemWizard_HideAdvanced;
	
	/** The New system wizard_ wizard desc. */
	public static String NewSystemWizard_WizardDesc;
	
	/** The New system wizard_ wizard name. */
	public static String NewSystemWizard_WizardName;
	
	public static String NewSystemWizard_InitialApplicationName;

	/** The Palette management page_ labe l_ default palette. */
	public static String PaletteManagementPage_LABEL_DefaultTypeLibrary;
	
	public static String SystemNameNotValid;
	
	public static String SystemNameAlreadyUsed;





	
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
