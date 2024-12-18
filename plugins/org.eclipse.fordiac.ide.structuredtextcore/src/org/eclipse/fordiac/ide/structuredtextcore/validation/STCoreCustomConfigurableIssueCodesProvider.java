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
package org.eclipse.fordiac.ide.structuredtextcore.validation;

import org.eclipse.xtext.preferences.PreferenceKey;
import org.eclipse.xtext.util.IAcceptor;
import org.eclipse.xtext.validation.SeverityConverter;

public class STCoreCustomConfigurableIssueCodesProvider extends STCoreConfigurableIssueCodesProvider {
	@Override
	protected void initialize(final IAcceptor<PreferenceKey> acceptor) {
		super.initialize(acceptor);
		acceptor.accept(create(STCoreValidator.WRONG_NAME_CASE, SeverityConverter.SEVERITY_WARNING));
		acceptor.accept(create(STCoreValidator.STRING_INDEX_OUT_OF_BOUNDS, SeverityConverter.SEVERITY_WARNING));
		acceptor.accept(create(STCoreValidator.FOR_CONTROL_VARIABLE_NON_TEMPORARY, SeverityConverter.SEVERITY_WARNING));
		acceptor.accept(create(STCoreValidator.UNNECESSARY_CONVERSION, SeverityConverter.SEVERITY_WARNING));
		acceptor.accept(create(STCoreValidator.UNNECESSARY_NARROW_CONVERSION, SeverityConverter.SEVERITY_WARNING));
		acceptor.accept(create(STCoreValidator.UNNECESSARY_WIDE_CONVERSION, SeverityConverter.SEVERITY_WARNING));
		acceptor.accept(create(STCoreValidator.UNNECESSARY_LITERAL_CONVERSION, SeverityConverter.SEVERITY_WARNING));
		acceptor.accept(create(STCoreValidator.LITERAL_IMPLICIT_CONVERSION, SeverityConverter.SEVERITY_WARNING));
		acceptor.accept(create(STCoreValidator.TRUNCATED_LITERAL, SeverityConverter.SEVERITY_WARNING));
		acceptor.accept(create(STCoreValidator.PACKAGE_NAME_MISMATCH, SeverityConverter.SEVERITY_IGNORE));
	}
}
