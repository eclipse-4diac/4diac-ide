/*******************************************************************************
 * Copyright (c) 2021, 2024 Primetals Technologies GmbH,
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians, Martin Jobst
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.ui.validation;

import org.eclipse.fordiac.ide.structuredtextcore.ui.Messages;
import org.eclipse.fordiac.ide.structuredtextcore.validation.STCoreValidator;
import org.eclipse.fordiac.ide.structuredtextcore.validation.STCoreValidatorConfigurationBlock;
import org.eclipse.swt.widgets.Composite;

public class STCoreCustomValidatorConfigurationBlock extends STCoreValidatorConfigurationBlock {

	@Override
	protected void fillSettingsPage(final Composite composite, final int nColumns, final int defaultIndent) {
		super.fillSettingsPage(composite, nColumns, defaultIndent);
		fillCodingStyleSection(
				createSection(Messages.STCoreCustomValidatorConfigurationBlock_CodingStyle, composite, nColumns),
				defaultIndent);
		fillPotentialProgrammingProblemsSection(
				createSection(Messages.STCoreCustomValidatorConfigurationBlock_PotentialProgrammingProblems, composite,
						nColumns),
				defaultIndent);
		fillUnnecessaryCodeSection(
				createSection(Messages.STCoreCustomValidatorConfigurationBlock_UnnecessaryCode, composite, nColumns),
				defaultIndent);
		fillLiteralsSection(
				createSection(Messages.STCoreCustomValidatorConfigurationBlock_Literals, composite, nColumns),
				defaultIndent);
	}

	protected void fillCodingStyleSection(final Composite composite, final int defaultIndent) {
		addComboBox(STCoreValidator.WRONG_NAME_CASE, Messages.STCoreCustomValidatorConfigurationBlock_WrongNameCase,
				composite, defaultIndent);
		addComboBox(STCoreValidator.PACKAGE_NAME_MISMATCH,
				Messages.STCoreCustomValidatorConfigurationBlock_PackageNameMismatch, composite, defaultIndent);
	}

	protected void fillPotentialProgrammingProblemsSection(final Composite composite, final int defaultIndent) {
		addComboBox(STCoreValidator.STRING_INDEX_OUT_OF_BOUNDS,
				Messages.STCoreCustomValidatorConfigurationBlock_StringIndexOutOfBounds, composite, defaultIndent);
		addComboBox(STCoreValidator.FOR_CONTROL_VARIABLE_NON_TEMPORARY,
				Messages.STCoreCustomValidatorConfigurationBlock_ForControlVariableNonTemporary, composite,
				defaultIndent);
	}

	protected void fillUnnecessaryCodeSection(final Composite composite, final int defaultIndent) {
		addComboBox(STCoreValidator.UNNECESSARY_CONVERSION,
				Messages.STCoreCustomValidatorConfigurationBlock_UnnecessaryConversion, composite, defaultIndent);
		addComboBox(STCoreValidator.UNNECESSARY_NARROW_CONVERSION,
				Messages.STCoreCustomValidatorConfigurationBlock_UnnecessaryNarrowConversion, composite, defaultIndent);
		addComboBox(STCoreValidator.UNNECESSARY_WIDE_CONVERSION,
				Messages.STCoreCustomValidatorConfigurationBlock_UnnecessaryWideConversion, composite, defaultIndent);
		addComboBox(STCoreValidator.UNNECESSARY_LITERAL_CONVERSION,
				Messages.STCoreCustomValidatorConfigurationBlock_UnnecessaryLiteralConversion, composite,
				defaultIndent);
	}

	protected void fillLiteralsSection(final Composite composite, final int defaultIndent) {
		addComboBox(STCoreValidator.LITERAL_IMPLICIT_CONVERSION,
				Messages.STCoreCustomValidatorConfigurationBlock_LiteralImplicitConversion, composite, defaultIndent);
		addComboBox(STCoreValidator.TRUNCATING_LITERAL_CONVERSION,
				Messages.STCoreCustomValidatorConfigurationBlock_TruncatingLiteralConversion, composite, defaultIndent);
		addComboBox(STCoreValidator.TRUNCATED_LITERAL,
				Messages.STCoreCustomValidatorConfigurationBlock_TruncatedStringLiteral, composite, defaultIndent);
	}
}
