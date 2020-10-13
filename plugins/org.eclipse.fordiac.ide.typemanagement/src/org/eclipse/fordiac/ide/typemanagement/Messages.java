/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 *				 2020 Andrea Zoitl
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
 *******************************************************************************/

package org.eclipse.fordiac.ide.typemanagement;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.typemanagement.messages"; //$NON-NLS-1$

	public static String DeleteFBTypeParticipant_Name;
	public static String DeleteFBTypeParticipant_TypeInUseWarning;
	public static String FBTypeComposedAdapterFactory_FBTypecomposedAdapterFactoryShouldNotBeInsantiated;
	public static String NewFBTypeWizard_TemplateNotAvailable;
	public static String NewFBTypeWizardPage_TypeAlreadyExists;
	public static String NewFBTypeWizardPage_CreateNewType;
	public static String NewFBTypeWizardPage_CreateNewTypeFromTemplateType;
	public static String NewFBTypeWizardPage_CouldNotFindTemplateFiles;
	public static String NewFBTypeWizardPage_EmptyTypenameIsNotValid;
	public static String NewFBTypeWizardPage_NewTypePage;
	public static String NewFBTypeWizardPage_NoTypeSelected;
	public static String NewFBTypeWizardPage_NoTypeTemplatesFoundCheckTemplatesDirectory;
	public static String NewFBTypeWizardPage_OpenTypeForEditingWhenDone;
	public static String NewFBTypeWizardPage_InvalidOrNoComment;
	public static String RenameType_InvalidIdentifierErrorMessage;
	public static String RenameType_Name;
	public static String OpenTypeHandler_EDITOR_OPEN_ERROR_MESSAGE;
	public static String OpenTypeHandler_NO_FILES_IN_WORKSPACE;
	public static String OpenTypeHandler_NO_FILES_SELECTED;
	public static String OpenTypeHandler_OPEN_TYPE_ERROR_TITLE;
	public static String OpenTypeHandler_OPEN_TYPE_TITLE;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
