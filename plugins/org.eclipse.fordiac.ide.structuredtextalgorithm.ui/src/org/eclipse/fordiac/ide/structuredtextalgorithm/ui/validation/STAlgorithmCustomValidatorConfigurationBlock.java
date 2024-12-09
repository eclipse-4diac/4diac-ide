/*******************************************************************************
 * Copyright (c) 2022, 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui.validation;

import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.Messages;
import org.eclipse.fordiac.ide.structuredtextalgorithm.validation.STAlgorithmValidator;
import org.eclipse.fordiac.ide.structuredtextcore.ui.validation.STCoreCustomValidatorConfigurationBlock;
import org.eclipse.swt.widgets.Composite;

public class STAlgorithmCustomValidatorConfigurationBlock extends STCoreCustomValidatorConfigurationBlock {

	@Override
	protected void fillPotentialProgrammingProblemsSection(final Composite composite, final int defaultIndent) {
		super.fillPotentialProgrammingProblemsSection(composite, defaultIndent);
		addComboBox(STAlgorithmValidator.SHADOWING_FUNCTION,
				Messages.STAlgorithmCustomValidatorConfigurationBlock_ShadowingFunction, composite, defaultIndent);
	}

	@Override
	protected void fillUnnecessaryCodeSection(final Composite composite, final int defaultIndent) {
		super.fillUnnecessaryCodeSection(composite, defaultIndent);
		addComboBox(STAlgorithmValidator.NO_INPUT_EVENT_FOR_ALGORITHM,
				Messages.STAlgorithmCustomValidatorConfigurationBlock_UnusedAlgorithm, composite, defaultIndent);
	}
}
