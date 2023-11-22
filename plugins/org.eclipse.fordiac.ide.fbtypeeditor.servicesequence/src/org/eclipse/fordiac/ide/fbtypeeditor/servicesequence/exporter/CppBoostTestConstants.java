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

	static String newLine = System.getProperty("line.separator");

	public static String testIncludeString = """
			#include "../../core/fbtests/fbtestfixture.h"
			#include <forte_uint.h>
			#include <forte_bool.h>\n""";

	public static String tester_genString = """
			#ifdef FORTE_ENABLE_GENERATED_SOURCE_CPP
			#include "{0}_tester_gen.cpp"
			#endif
			""";

	public static String testFixtureStruct = "struct {0}_TestFixture : public CFBTestFixtureBase '{'";

	public static String testFixtureBase = "{0}_TestFixture() : " + newLine + "CFBTestFixtureBase(g_nStringId{0}) '{'";

	public static String testFixtureSetup = "CFBTestFixtureBase::setup();" + newLine + "}";

	public static String setInputDataSTART = "setInputData({";
	public static String setInputDataEND = "});";

	public static String setOutputDataSTART = "setOutputData({";
	public static String setOutputDataEND = "});";

	public static String testSuiteName = "BOOST_FIXTURE_TEST_SUITE({0}TEST, {0}_TestFixture)";
	public static String testSuiteEND = "BOOST_AUTO_TEST_SUITE_END()";

	public static String testCase = "BOOST_AUTO_TEST_CASE({0}) '{'";

	public static String triggerEvent = "triggerEvent({0});";
	public static String triggerEventByID = "triggerEvent(CFBTestFixtureBase::getFB()->getEIID(CStringDictionary::getInstance().getId(\"{0}\")));";

	public static String boostAssertEQUAL = "BOOST_ASSERT(func_EQ({0},{1}));";
	public static String boostAssertNOTEQUAL = "BOOST_ASSERT(func_NE({0},{1}));";

	private CppBoostTestConstants() {
		// empty private constructor
	}
}
