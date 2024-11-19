/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2014 - 2016 Profactor GbmH, fortiss GmbH
 * 				 2020						   Andrea Zoitl
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Andrea Zoitl
 *     - externalized translatable strings
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef;

import org.eclipse.osgi.util.NLS;

/** The Class Messages. */
@SuppressWarnings("squid:S3008") // tell sonar the java naming convention does not make sense for this class
public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.gef.messages"; //$NON-NLS-1$

	/** The Abstract view edit part_ erro r_create figure. */
	public static String AbstractAttributeSection_CreateAttribute;
	public static String AbstractAttributeSection_DeleteSelectedAttribute;
	public static String AdjustConnectionCommand_WrongConnectionSegmentIndex;
	public static String AbstractViewEditPart_ERROR_createFigure;
	public static String Abstract4diacEditPartFactory_ERROR_CantCreatePartForModelElement;
	public static String Abstract4diacEditPartFactory_LABEL_RUNTIMEException_CantCreateModelForElement;
	public static String AppearancePropertySection_ChangeBackgroundColor;
	public static String AppearancePropertySection_LABEL_BackgroundColor;
	public static String AppearancePropertySection_LABEL_ChooseColor;
	public static String AppearancePropertySection_LABLE_Color;
	public static String ChangeCommentCommand_LABEL_ChangeComment;
	public static String ConnectionSection_Comment;
	public static String ConnectionSection_ShowConnection;

	public static String ConnectionSection_Source;
	public static String ConnectionSection_Target;
	public static String DiagramPreferences_CornerDimension;

	public static String DiagramPreferences_FB;
	public static String DiagramPreferences_GeneralDiagramPreferences;
	public static String DiagramPreferences_LabelSize;
	public static String DiagramPreferences_LayoutConnectionsAutomatically;

	public static String DiagramPreferences_LayoutOptions;

	public static String DiagramPreferences_MaximumDefaultValueSize;

	public static String DiagramPreferences_MaximumValueLabelSize;
	public static String DiagramPreferences_MinimumPinLabelSize;
	public static String DiagramPreferences_MaximumPinLabelSize;
	public static String DiagramPreferences_MaximumHiddenConnectionLabelSize;
	public static String DiagramPreferences_MaximumTypeLabelSize;
	public static String DiagramPreferences_FieldEditors_GridSpacingInPixels;
	public static String DiagramPreferences_FieldEditors_RulerAndGrid;
	public static String DiagramPreferences_FieldEditors_ShowGrid;
	public static String DiagramPreferences_FieldEditors_ShowRuler;
	public static String DiagramPreferences_FieldEditors_SnapToGrid;

	public static String DiagramPreferences_PinLabelText;
	public static String DiagramPreferences_ShowPinName;
	public static String DiagramPreferences_ShowPinComment;
	public static String DiagramPreferences_ShowConnectedOutputPinName;
	public static String DiagramPreferences_MaximumInterfaceBarSize;
	public static String DiagramPreferences_MinimumInterfaceBarSize;
	public static String DiagramPreferences_BlockMargins;
	public static String DiagramPreferences_TopBottom;
	public static String DiagramPreferences_LeftRight;

	public static String DiagramPreferences_Restart;

	public static String DiagramPreferences_ExpandedInterfaceGroupText;
	public static String DiagramPreferences_ExpandedInterfaceStackPins;
	public static String DiagramPreferences_ExpandedInterfaceEvents;

	public static String FBFigure_TYPE_NOT_SET;
	public static String FordiacContextMenuProvider_Align;

	public static String NewInstanceCellEditor_SearchForType;

	public static String PropertyUtil_LABEL_Description_Complianceprofile;
	public static String PropertyUtil_LABEL_Instancecomment;
	public static String PropertyUtil_LABEL_Instancename;
	public static String PrintPreviewAction_LABEL_Print;
	public static String PrintPreviewAction_LABEL_PrintPreview;
	public static String PrintPreview_ERROR_StartingNewPage;
	public static String PrintPreview_ERROR_StartingPrintJob;
	public static String PrintPreview_LABEL_Close;
	public static String PrintPreview_LABEL_CM;
	public static String PrintPreview_LABEL_FitHeight;
	public static String PrintPreview_LABEL_FitPage;
	public static String PrintPreview_LABEL_FitWidth;
	public static String PrintPreview_LABEL_Margin;
	public static String PrintPreview_LABEL_NextPage;
	public static String PrintPreview_LABEL_Of;
	public static String PrintPreview_LABEL_Page;
	public static String PrintPreview_LABEL_Print;
	public static String PrintPreview_LABEL_PrinterSettings;
	public static String PrintPreview_LABEL_PrintBorder;
	public static String PrintPreview_LABEL_PrintPreview;
	public static String PrintPreview_LABEL_Scale;
	public static String PrintPreview_LABEL_Tile;
	public static String SetProfileCommand_LABEL_SetProfile;

	public static String ValidationJob_ValidationJobName;

	public static String VariableDialog_DefaultTitle;

	public static String VariableDialog_ValueError;

	public static String VariableWidget_CollapseAll;

	public static String VariableWidget_ExpandAll;

	public static String ViewRenameCommand_LABEL_RenameView;

	public static String InterfaceElementSection_ConnectionGroup;
	public static String InterfaceElementSection_InConnections;
	public static String InterfaceElementSection_OutConnections;
	public static String InterfaceElementSection_MessageDialog_TITLE;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// private empty constructor
	}
}
