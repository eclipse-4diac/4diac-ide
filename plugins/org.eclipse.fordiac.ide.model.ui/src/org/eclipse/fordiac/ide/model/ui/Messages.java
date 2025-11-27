/*******************************************************************************
 * Copyright (c) 2020 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui;

import org.eclipse.osgi.util.NLS;

@SuppressWarnings("squid:S3008") // tell sonar the java naming convention does not make sense for this class
public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "plugin"; //$NON-NLS-1$
	public static String AutoReloadError_PathNotFound_Title;
	public static String AutoReloadError_PathNotFound;

	public static String BlockSizePreferences_BlockMargins;
	public static String BlockSizePreferences_TopBottom;
	public static String BlockSizePreferences_LeftRight;

	public static String BlockSizePreferences_LabelSize;
	public static String BlockSizePreferences_MaximumValueLabelSize;
	public static String BlockSizePreferences_MinimumPinLabelSize;
	public static String BlockSizePreferences_MaximumPinLabelSize;
	public static String BlockSizePreferences_MaximumHiddenConnectionLabelSize;
	public static String BlockSizePreferences_MaximumTypeLabelSize;
	public static String BlockSizePreferences_MaximumInterfaceBarSize;
	public static String BlockSizePreferences_MinimumInterfaceBarSize;

	public static String BlockSizePreferences_Refresh;

	public static String DataTypeDropdown_Adapter_Types;
	public static String DataTypeDropdown_Type_Selection;
	public static String DataTypeDropdown_Select_Type;
	public static String DataTypeDropdown_Elementary_Types;
	public static String AttributeTypeDropdown_Attribute_Types;
	public static String DataTypeDropdown_STRUCT_Types;
	public static String OpenEditorAction_text;
	public static String OpenEditorAction_viewertext;
	public static String OpenEditorProvider_OpenWithMenu_label;
	public static String DataTypeDropdown_FB_Types;
	public static String DeviceTypeSelectionTreeContentProvider_DeviceTypes;
	public static String ResourceTypeSelectionTreeContentProvider_ResourceTypes;
	public static String Validation_CollisionSeverityLabel;
	public static String Validation_ErrorLabel;
	public static String Validation_IgnoreLabel;
	public static String Validation_InterfaceCollisionSeverityLabel;
	public static String Validation_InfoLabel;
	public static String Validation_WarningLabel;
	public static String Validation_Section_Collision;
	public static String Validation_Section_TypeManagement;
	public static String Validation_Severity_Info;
	public static String Validation_PackagenameMismatchFolder;

	public static String DataTypeDialog_OneElement;
	public static String DataTypeDialog_NoFolder;
	public static String DataTypeDialog_InvalidSelection;
	public static String DataTypeDialog_Selected;

	public static String ValidationJob_RefreshJobName;
	public static String ValidationJob_ReloadJobName;
	public static String ValidationJob_UpdateJobName;
	public static String ValidationJob_ValidationJobName;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
