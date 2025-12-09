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
package org.eclipse.fordiac.ide.structuredtextalgorithm.validation;

import org.eclipse.fordiac.ide.structuredtextcore.validation.STCoreCustomConfigurableIssueCodesProvider;
import org.eclipse.xtext.preferences.PreferenceKey;
import org.eclipse.xtext.util.IAcceptor;
import org.eclipse.xtext.validation.SeverityConverter;

public class STAlgorithmCustomConfigurableIssueCodesProvider extends STCoreCustomConfigurableIssueCodesProvider {

	@Override
	protected void initialize(final IAcceptor<PreferenceKey> acceptor) {
		super.initialize(acceptor);
		acceptor.accept(create(STAlgorithmValidator.UNUSED_ALGORITHM, SeverityConverter.SEVERITY_WARNING));
		acceptor.accept(create(STAlgorithmValidator.SHADOWING_FUNCTION, SeverityConverter.SEVERITY_IGNORE));
	}
}
