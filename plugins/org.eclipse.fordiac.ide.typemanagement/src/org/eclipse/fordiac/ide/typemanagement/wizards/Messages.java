/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mathias Garstenauer - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.typemanagement.wizards;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = Messages.class.getPackageName() + ".messages"; //$NON-NLS-1$
	public static String ConnectionsToStructWizard_PageTitle;
	public static String ConnectionsToStructWizard_WindowTitle;
	public static String ConnectionsToStructWizardPage_AnyStruct;
	public static String ConnectionsToStructWizardPage_Description;
	public static String ConnectionsToStructWizardPage_ExistingStruct;
	public static String ConnectionsToStructWizardPage_InName;
	public static String ConnectionsToStructWizardPage_InvalidStructName;
	public static String ConnectionsToStructWizardPage_Name;
	public static String ConnectionsToStructWizardPage_NewType;
	public static String ConnectionsToStructWizardPage_OutName;
	public static String ConnectionsToStructWizardPage_SolveConflicts;
	public static String ConnectionsToStructWizardPage_Title;
	public static String RepairBrokenConnectionWizardPage_Description;
	public static String RepairBrokenConnectionWizardPage_Dots;
	public static String RepairBrokenConnectionWizardPage_Name;
	public static String RepairBrokenConnectionWizardPage_Title;
	public static String RepairBrokenConnectionWizardPage_Type;

	public static String MoveToPackageChangePreview_EnterPackageName;
	public static String MoveTypeToPackage;
	public static String MoveTypeToPackage_RenamePackageTo;
	public static String MoveTypeToPackage_PackageNameIsTheSame;
	public static String MoveTypeToPackage_PackageNameIsEmpty;
	public static String MoveTypeToPackage_InvalidDestination;
	public static String MoveTypeToPackage_UpdateInstances;
	public static String MoveTypeToPackage_NameChanged;
	public static String MoveTypeToPackage_UpdateTypeEntryFile;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
