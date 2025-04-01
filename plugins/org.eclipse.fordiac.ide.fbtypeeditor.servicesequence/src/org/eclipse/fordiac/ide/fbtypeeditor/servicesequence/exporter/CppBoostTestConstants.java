/*******************************************************************************
 * Copyright (c) 2023 Fabio Gandolfi
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.exporter;

@SuppressWarnings("nls")
public final class CppBoostTestConstants {

	static final String NEW_LINE = System.getProperty("line.separator");

	public static final String TEST_INCLUDE_STRING = """
			#include "core/fbtests/fbtestfixture.h"
			#include <forte_uint.h>
			#include <forte_bool.h>""" + NEW_LINE;

	public static final String TESTER_GEN_STRING = """
			#ifdef FORTE_ENABLE_GENERATED_SOURCE_CPP
			#include "{0}_ServiceSeq_gen.cpp"
			#endif
			""";

	public static final String TEST_FIXTURE_STRUCT = "struct {0}_TestFixture : public CFBTestFixtureBase '{'";

	public static final String TEST_FICTURE_BASE = "{0}_TestFixture() : " + NEW_LINE
			+ "CFBTestFixtureBase(g_nStringIdservSeq__{0}) '{'";

	public static final String TEST_FICTURE_SETUP = "CFBTestFixtureBase::setup();" + NEW_LINE + "}";

	public static final String SET_INPUT_DATA_START = "setInputData({";
	public static final String SET_INPUT_DATA_END = "});";

	public static final String SET_OUTPUT_DATA_START = "setOutputData({";
	public static final String SET_OUTPUT_DATA_END = "});";

	public static final String TEST_SUITE_NAME = "BOOST_FIXTURE_TEST_SUITE({0}TEST, {0}_TestFixture)";
	public static final String TEST_SUITE_END = "BOOST_AUTO_TEST_SUITE_END()";

	public static final String TEST_CASE = "BOOST_AUTO_TEST_CASE({0}) '{'";

	public static final String SET_ECC_STATE = "setECCState(CIEC_STATE({0}));";

	public static final String TRIGGER_EVENT = "triggerEvent({0});";
	public static final String TRIGGER_EVENT_BY_ID = "triggerEvent(CFBTestFixtureBase::getFB()->getEIID(CStringDictionary::getInstance().getId(\"{0}\")));";

	public static final String BOOS_ASSERT_EQUAL = "BOOST_TEST({0} == {1});";
	public static final String BOOS_ASSERT_NOT_EQUAL = "BOOST_TEST({0} != {1});";

	private CppBoostTestConstants() {
		// empty private constructor
	}
}
