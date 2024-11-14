/*******************************************************************************
 * Copyright (c) 2023, 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.ui;

import org.eclipse.osgi.util.NLS;

@SuppressWarnings("squid:S3008") // tell sonar the java naming convention does not make sense for this class
public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.structuredtextcore.ui.messages"; //$NON-NLS-1$
	public static String ExpandAllContribution_CollapseAllDescription;
	public static String ExpandAllContribution_CollapseAllLabel;
	public static String ExpandAllContribution_CollapseAllToolTip;
	public static String ExpandAllContribution_ExpandAllDescription;
	public static String ExpandAllContribution_ExpandAllLabel;
	public static String ExpandAllContribution_ExpandAllToolTip;
	public static String FilterHeadingsContribution_Description;
	public static String FilterHeadingsContribution_Text;
	public static String FilterHeadingsContribution_ToolTipText;
	public static String STCoreQuickfixProvider_AddExplicitTypecastDescription;
	public static String STCoreQuickfixProvider_AddExplicitTypecastLabel;
	public static String STCoreQuickfixProvider_ChangeConversionDescription;
	public static String STCoreQuickfixProvider_ChangeConversionLabel;
	public static String STCoreQuickfixProvider_ChangeVariableCaseAsDeclaredDescription;
	public static String STCoreQuickfixProvider_ChangeVariableCaseAsDeclaredLabel;
	public static String STCoreQuickfixProvider_CreateImport;
	public static String STCoreQuickfixProvider_CreateMissingInOutVariable;
	public static String STCoreQuickfixProvider_CreateMissingInputVariable;
	public static String STCoreQuickfixProvider_CreateMissingOutputVariable;
	public static String STCoreQuickfixProvider_CreateMissingTempVariable;
	public static String STCoreQuickfixProvider_RemoveConsecutiveUnderscoresDescription;
	public static String STCoreQuickfixProvider_RemoveConsecutiveUnderscoresLabel;
	public static String STCoreQuickfixProvider_RemoveTrailingUnderscoreDescription;
	public static String STCoreQuickfixProvider_RemoveTrailingUnderscoreLabel;
	public static String STCoreQuickfixProvider_RemoveUnnecessaryConversionDescription;
	public static String STCoreQuickfixProvider_RemoveUnnecessaryConversionLabel;
	public static String STCoreQuickfixProvider_RemoveUnnecessaryLiteralConversionDescription;
	public static String STCoreQuickfixProvider_RemoveUnnecessaryLiteralConversionLabel;
	public static String STCoreQuickfixProvider_RemoveImportDescription;
	public static String STCoreQuickfixProvider_RemoveImportLabel;
	public static String STCoreQuickfixProvider_RemoveInvalidExitStatementLabel;
	public static String STCoreQuickfixProvider_RemoveInvalidExitStatementDescription;
	public static String STCoreQuickfixProvider_RemoveInvalidContinueStatementLabel;
	public static String STCoreQuickfixProvider_RemoveInvalidContinueStatementDescription;
	public static String STCoreQuickfixProvider_OrganizeImports;
	public static String STCoreCodeMiningPreferencePage_EnableCodeMinings;
	public static String STCoreCodeMiningPreferencePage_EnableLiteralTypeCodeMinings;
	public static String STCoreCodeMiningPreferencePage_EnableReferencedVariablesCodeMinings;
	public static String STCoreContentAssistPreferencePage_ContentAssist;
	public static String STCoreContentAssistPreferencePage_AutoActivationTriggers;
	public static String STCoreContentAssistPreferencePage_ChangedSettingsEffect;
	public static String STCoreEditorPreferencePage_PerformanceModeThreshold;
	public static String STCoreHoverProvider_EventKind;
	public static String STCoreHoverProvider_FBKind;
	public static String STCoreHoverProvider_FBTypeKind;
	public static String STCoreHoverProvider_StructKind;
	public static String STCoreHoverProvider_VarInOutKind;
	public static String STCoreHoverProvider_VarInputKind;
	public static String STCoreHoverProvider_VarInternalConstKind;
	public static String STCoreHoverProvider_VarInternalKind;
	public static String STCoreHoverProvider_VarOutputKind;
	public static String STCoreLabelProvider_FBText;
	public static String STCoreLabelProvider_VarDeclarationText;
	public static String STCoreLabelProvider_VarInOutDeclarationBlockText;
	public static String STCoreLabelProvider_VarInputDeclarationBlockText;
	public static String STCoreLabelProvider_VarOutputDeclarationBlockText;
	public static String STCoreLabelProvider_VarPlainDeclarationBlockConstantText;
	public static String STCoreLabelProvider_VarPlainDeclarationBlockText;
	public static String STCoreLabelProvider_VarTempDeclarationBlockConstantText;
	public static String STCoreLabelProvider_VarTempDeclarationBlockText;
	public static String STCoreOutlineTreeProvider_MissingHeadingText;
	public static String STCoreRenameElementRefactoringParticpant_Name;
	public static String STCoreSaveActionsPreferencePage_EnableFormat;
	public static String STCoreSaveActionsPreferencePage_EnableSaveActions;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
