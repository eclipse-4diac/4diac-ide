/*******************************************************************************
 * Copyright (c) 2008 Profactor GmbH
 * 				 2019 Johannes Kepler University Linz
 * 				 2020 Andrea Zoitl
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
 *   Alois Zoitl
 *     - moved preference messages from utils plugin
 *   Andrea Zoitl
 *     - Externalized translatable strings
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui;

import org.eclipse.osgi.util.NLS;

/** The Class Messages. */
@SuppressWarnings("squid:S3008") // tell sonar the java naming convention does not make sense for this class
public final class FordiacMessages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.ui.messages"; //$NON-NLS-1$

	public static String OPEN_TYPE_EDITOR_MESSAGE; // NOSONAR

	public static String Algorithm; // NOSONAR
	public static String Algorithms; // NOSONAR
	public static String ApplicationDomain; // NOSONAR
	public static String ApplicationName; // NOSONAR
	public static String Author; // NOSONAR
	public static String BaseType; // NOSONAR
	public static String Classdef; // NOSONAR
	public static String Classification; // NOSONAR
	public static String Comment; // NOSONAR
	public static String CompilerInfo; // NOSONAR
	public static String ComputingPlaceholderValue; // NOSONAR
	public static String Date; // NOSONAR
	public static String DataType; // NOSONAR
	public static String Description; // NOSONAR
	public static String Destination; // NOSONAR
	public static String Device; // NOSONAR
	public static String EmptyField; // NOSONAR
	public static String Event; // NOSONAR
	public static String FBNetwork; // NOSONAR
	public static String Function; // NOSONAR
	public static String Header; // NOSONAR
	public static String Height; // NOSONAR
	public static String Identification; // NOSONAR
	public static String InitialValue; // NOSONAR
	public static String Inputs; // NOSONAR
	public static String Imports; // NOSONAR
	public static String InstanceComment; // NOSONAR
	public static String InstanceName; // NOSONAR
	public static String InstanceInfo; // NOSONAR
	public static String Interface; // NOSONAR
	public static String Language; // NOSONAR
	public static String NA; // NOSONAR
	public static String ND; // NOSONAR
	public static String Name; // NOSONAR
	public static String NewType; // NOSONAR
	public static String Organization; // NOSONAR
	public static String Outputs; // NOSONAR
	public static String Package; // NOSONAR

	public static String PackageInfoWidget_OrganizeImports;
	public static String Pin; // NOSONAR
	public static String Product; // NOSONAR
	public static String Properties; // NOSONAR
	public static String Remarks; // NOSONAR

	public static String RepairCommandHandler_ChangeType0;
	public static String Resource; // NOSONAR
	public static String SelectType; // NOSONAR
	public static String Standard; // NOSONAR
	public static String Size; // NOSONAR
	public static String SystemName; // NOSONAR
	public static String Target; // NOSONAR
	public static String Type; // NOSONAR
	public static String TypeInfo; // NOSONAR
	public static String TypeLibrary; // NOSONAR
	public static String TypeName; // NOSONAR
	public static String Unmap; // NOSONAR
	public static String Unknown; // NOSONAR
	public static String DefaultValue; // NOSONAR
	public static String Value; // NOSONAR
	public static String ValueTooLarge; // NOSONAR
	public static String Variable; // NOSONAR
	public static String Vendor; // NOSONAR
	public static String Version; // NOSONAR
	public static String VersionInfo; // NOSONAR
	public static String Visible; // NOSONAR
	public static String Visible_IN; // NOSONAR
	public static String Visible_OUT; // NOSONAR
	public static String VarConfig; // NOSONAR
	public static String Width; // NOSONAR
	public static String With; // NOSONAR
	public static String Constants; // NOSONAR
	public static String Retain; // NOSONAR

	public static String DirectoryChooserControl_LABEL_Browse; // NOSONAR
	public static String DirectoryChooserControl_LABEL_ChooseDirectory; // NOSONAR
	public static String DirectoryChooserControl_LABEL_SelectdDirectoryDialogMessage; // NOSONAR

	public static String FordiacPreferencePage_LABEL_DefaultComplianceProfile; // NOSONAR
	public static String FordiacPreferencePage_LABEL_DefaultEventConnectorColor; // NOSONAR
	public static String FordiacPreferencePage_LABEL_BoolConnectorColor; // NOSONAR
	public static String FordiacPreferencePage_LABEL_AnyBitConnectorColor; // NOSONAR
	public static String FordiacPreferencePage_LABEL_AnyIntConnectorColor; // NOSONAR
	public static String FordiacPreferencePage_LABEL_AnyRealConnectorColor; // NOSONAR
	public static String FordiacPreferencePage_LABEL_AnyStringConnectorColor; // NOSONAR
	public static String FordiacPreferencePage_LABEL_DataConnectorColor; // NOSONAR
	public static String FordiacPreferencePage_LABEL_ShowErrorsAtMouseCursor; // NOSONAR

	public static String FordiacPreferencePage_LABEL_DeactivateTransferingComments_DEMUX_to_MUX; // NOSONAR
	public static String FordiacPreferencePage_LABEL_DefaultAdapterConnectorColor; // NOSONAR
	public static String FordiacPreferencePage_LABEL_PreferencePageDescription; // NOSONAR

	public static String TableCopyPaste_TEXT_Copy; // NOSONAR
	public static String TableCopyPaste_TEXT_Paste; // NOSONAR
	public static String TableCopyPaste_TEXT_Cut; // NOSONAR
	public static String NatTable_TEXT_Paste; // NOSONAR

	public static String Group_LABEL_DisableAutoResize; // NOSONAR
	public static String Group_TOOLTIP_DisableAutoResize; // NOSONAR
	public static String Delete_Elements; // NOSONAR
	public static String Dialog_Repair_Pin;// NOSONAR
	public static String Dialog_Repair_Add;// NOSONAR
	public static String Dialog_Repair_Remove;// NOSONAR

	public static String Repair_Dialog_Delete_Elements; // NOSONAR
	public static String Repair_Dialog_New_DataType;// NOSONAR
	public static String Repair_Dialog_Pin_Add;// NOSONAR
	public static String Repair_Dialog_Pin_Remove;// NOSONAR
	public static String Repair_Dialog_FB_Title;// NOSONAR
	public static String Repair_Dialog_New_FB;// NOSONAR
	public static String Repair_Dialog_ChangeDataType;// NOSONAR
	public static String Repair_Dialog_ChangeFBType;// NOSONAR
	public static String Repair_Dialog_BestFitDataType;// NOSONAR
	public static String Repair_Dialog_BestFitFBType;// NOSONAR

	public static String Subapp_Size_DisableAutoResize; // NOSONAR

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, FordiacMessages.class);
	}

	private FordiacMessages() {
		// private empty constructor
	}
}