/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2015, 2016 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 				 2020                         Andrea Zoitl
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard EbenhoferIngo Hegny, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Andrea Zoitl
 *     - externalized translatable strings
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.fbtypeeditor.ecc.messages"; //$NON-NLS-1$
	public static String ActionEditingComposite_Actions;
	public static String ActionEditingComposite_ConfigureActionTableLayout_Algorithm;
	public static String ActionEditingComposite_ConfigureActionTableLayout_Event;
	public static String ActionSection_Algorithm;
	public static String ActionSection_AllAlgorithms;
	public static String ActionSection_OutputEvent;
	public static String AlgorithmComposite_Comment;
	public static String AlgorithmComposite_Language;
	public static String AlgorithmList_ConfigureTableLayout_Comment;
	public static String AlgorithmList_ConfigureTableLayout_Name;
	public static String AlgorithmList_ConfigureTableLayout_Language;
	public static String CreateAlgorithmCommand_NewAlgorithm;
	public static String ECAlgorithmGroup_Title;
	public static String ECCActions_AddAction;
	public static String ECCActions_AddState;
	public static String ECCActions_InitialState;
	public static String ECCContentAndLabelProvider_EmptyField;
	public static String ECCEditor_LABEL_ECCEditorTabName;
	public static String ECCPaletteFactory_LABEL_Action;
	public static String ECCPaletteFactory_LABEL_ECCGroup;
	public static String ECCPaletteFactory_LABEL_STAlgorithm;
	public static String ECCPaletteFactory_LABEL_State;
	public static String ECCPaletteFactory_TOOLTIP_Action;
	public static String ECCPaletteFactory_TOOLTIP_State;
	public static String ECCPaletteFactory_TOOLTIP_STAlgorithm;
	public static String ECStateSetPositionCommand_LABEL_Move;
	public static String FordiacECCPreferencePage_LABEL_ECCAlgorithmBorderColor;
	public static String FordiacECCPreferencePage_LABEL_ECCAlgorithmColor;
	public static String FordiacECCPreferencePage_LABEL_ECCEventBorderColor;
	public static String FordiacECCPreferencePage_LABEL_ECCEventColor;
	public static String FordiacECCPreferencePage_LABEL_ECCStateBorderColor;
	public static String FordiacECCPreferencePage_LABEL_ECCStateColor;
	public static String FordiacECCPreferencePage_LABEL_ECCTransitionColor;
	public static String FordiacECCPreferencePage_LABEL_PreferencePageDescription;
	public static String InternalVarsSection_ConfigureTableLayout_ArraySize;
	public static String InternalVarsSection_ConfigureTableLayout_Comment;
	public static String InternalVarsSection_ConfigureTableLayout_InitialValue;
	public static String InternalVarsSection_ConfigureTableLayout_Name;
	public static String InternalVarsSection_ConfigureTableLayout_Type;
	public static String ReconnectTransitionCommand_ReconnectTransition;
	public static String StateCreationFactory_LABEL_NewECState;
	public static String StateSection_Comment;
	public static String StateSection_Name;
	public static String TransitionEditingComposite_TransitionsFromSelectedState;
	public static String TransitionEditingComposite_ConfigureTransitionTableLayout_Comment;
	public static String TransitionEditingComposite_ConfigureTransitionTableLayout_Condition;
	public static String TransitionEditingComposite_ConfigureTransitionTableLayout_Destination;
	public static String TransitionEditingComposite_ConfigureTransitionTableLayout_Event;
	public static String TransitionSection_Comment;
	public static String TransitionSection_Condition;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
