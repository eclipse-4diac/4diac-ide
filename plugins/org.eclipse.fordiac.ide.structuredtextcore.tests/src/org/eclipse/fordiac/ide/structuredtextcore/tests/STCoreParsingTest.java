/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STSource;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.extensions.InjectionExtension;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.google.inject.Inject;

@SuppressWarnings("nls")
@ExtendWith(InjectionExtension.class)
@InjectWith(STCoreInjectorProvider.class)
class STCoreParsingTest {
	@Inject
	private ParseHelper<STSource> parseHelper;

	@Test
	void loadModel() throws Exception {
		final STSource result = parseHelper.parse("TRUE;");
		assertNotNull(result);
		final List<Diagnostic> errors = result.eResource().getErrors();
		assertTrue(errors.isEmpty(), "Unexpected errors: " + IterableExtensions.join(errors, ", "));
	}

	@ParameterizedTest
	@ValueSource(strings = { //
			"TIME#0s", //
			"TIME#0.0s", //
			"TIME#17ns", //
			"TIME#17.0ns", //
			"TIME#17s4ms", //
			"TIME#17.004s", //
			"TIME#1d_2h_3m_4s_5ms_6us_7ns", //
			"TIME#-4m17.5s", //
			"LTIME#0s", //
			"LTIME#0.0s", //
			"LTIME#17ns", //
			"LTIME#17.0ns", //
			"LTIME#17s4ms", //
			"LTIME#17.004s", //
			"LTIME#1d_2h_3m_4s_5ms_6us_7ns", //
			"LTIME#-4m17.5s" //
	})
	void parseValidTimeLiteral(final String literal) throws Exception {
		final STSource result = parseHelper.parse(literal + ";");
		assertNotNull(result);
		final List<Diagnostic> errors = result.eResource().getErrors();
		assertTrue(errors.isEmpty(), "Unexpected errors for " + literal + ": " + IterableExtensions.join(errors, ", "));
	}

	@ParameterizedTest
	@ValueSource(strings = { //
			"TIME#17s4m", //
			"TIME#1ms2s", //
			"TIME#1h2d", //
			"TIME#1s2s", //
			"TIME#1m2m", //
			"TIME#4_m", //
			"TIME#4m_", //
			"TIME#4m__17s", //
			"TIME#17._5s", //
			"TIME#17_.5s", //
			"TIME#17.5_s", //
			"TIME#1_d2h", //
			"TIME#4.0m17s", //
			"TIME#1.5h30m", //
			"TIME#0.0000000001s", //
			"TIME#4m-17s", //
			"TIME#- 17s", //
			"TIME#17 s", //
			"TIME#17. 5s", //
			"TIME#17 .5s", //
			"TIME#4m17s21", //
			"LTIME#17s4m", //
			"LTIME#1ms2s", //
			"LTIME#1h2d", //
			"LTIME#1s2s", //
			"LTIME#1m2m", //
			"LTIME#4_m", //
			"LTIME#4m_", //
			"LTIME#4m__17s", //
			"LTIME#17._5s", //
			"LTIME#17_.5s", //
			"LTIME#17.5_s", //
			"LTIME#1_d2h", //
			"LTIME#4.0m17s", //
			"LTIME#1.5h30m", //
			"LTIME#0.0000000001s", //
			"LTIME#4m-17s", //
			"LTIME#- 17s", //
			"LTIME#17 s", //
			"LTIME#17. 5s", //
			"LTIME#17 .5s", //
			"LTIME#4m17s21" //
	})
	void parseInvalidTimeLiteral(final String literal) throws Exception {
		final STSource result = parseHelper.parse(literal + ";");
		assertNotNull(result);
		final List<Diagnostic> errors = result.eResource().getErrors();
		assertFalse(errors.isEmpty(), "Expected errors for " + literal);
	}
}
