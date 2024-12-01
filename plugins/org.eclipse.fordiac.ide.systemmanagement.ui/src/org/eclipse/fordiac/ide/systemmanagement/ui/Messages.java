/*******************************************************************************
 * Copyright (c) 2008, 2014 Profactor GmbH, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
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
@SuppressWarnings("squid:S3008") // tell sonar the java naming convention does not make sense for this class
public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.systemmanagement.ui.messages"; //$NON-NLS-1$

	public static String AutomationSystemEditor_CouldNotLoadSystem;

	public static String AutomationSystemEditor_ShowingFirstApplication;

	public static String AutomationSystemEditor_ShowingSystem;

	/** The New application page_ error message_ empty app name. */
	public static String NewApplicationPage_ErrorMessage_EmptyElementName;

	/** The New application page_ error message invalid app name. */
	public static String NewApplicationPage_ErrorMessageInvalidAppName;

	public static String NewApplicationPage_OpenApplicationForEditing;

	/** The New application page_ error message no system selected. */
	public static String NewApplicationPage_ErrorMessageNoSystemSelected;

	public static String NewSystemWizard_ShowAdvanced;

	public static String NewSystemWizard_HideAdvanced;

	/** The New system wizard_ wizard desc. */
	public static String New4diacProjectWizard_WizardDesc;

	/** The New system wizard_ wizard name. */
	public static String New4diacProjectWizard_WizardName;

	public static String New4diacProjectWizard_InitialSystemName;
	public static String New4diacProjectWizard_InitialApplicationName;
	public static String New4diacProjectWizard_SystemNameNotValid;
	public static String New4diacProjectWizard_DirectoryWithProjectNameAlreadyExistsInWorkspace;

	public static String NewSystemWizardPage_CreateNewSystem;

	public static String SystemEditor_Applications;

	public static String SystemEditor_SystemConfiguration;

	public static String SystemEditor_SystemInformation;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
