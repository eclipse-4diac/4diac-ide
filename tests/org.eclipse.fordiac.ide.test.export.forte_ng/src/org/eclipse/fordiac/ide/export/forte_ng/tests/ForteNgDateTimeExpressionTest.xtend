/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ernst Blecha
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng.tests

import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertEquals
import java.util.stream.Stream
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

class ForteNgDateTimeExpressionTest extends ForteNgTestBasicFBTypeBase {

	static final boolean VALID_VALUE = true
	static final boolean INVALID_VALUE = !VALID_VALUE

	def static Stream<Arguments> testCases()  {
		return Stream.of(
				Arguments.of( "TIME#1m", "CIEC_TIME(\"T#1m\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$

				Arguments.of( "T#14ms", "CIEC_TIME(\"T#14ms\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of( "T#-14ms", "CIEC_TIME(\"T#-14ms\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of( "LT#14.7s", "CIEC_TIME(\"LT#14s700ms\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of( "T#14.7m", "CIEC_TIME(\"T#14m42s\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of( "T#14.7h", "CIEC_TIME(\"T#14h42m\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of( "t#14.7d", "CIEC_TIME(\"T#14d16h48m\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of( "t#25h15m", "CIEC_TIME(\"T#25h15m\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of( "lt#5d14h12m18s3.5ms", "CIEC_TIME(\"LT#5d14h12m18s3ms500us\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of( "t#12h4m34ms230us400ns", "CIEC_TIME(\"T#12h4m34ms230us400ns\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of( "TIME#14ms", "CIEC_TIME(\"T#14ms\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of( "TIME#-14ms", "CIEC_TIME(\"T#-14ms\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of( "time#14.7s", "CIEC_TIME(\"T#14s700ms\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of( "t#25h_15m", "CIEC_TIME(\"T#25h15m\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of( "t#5d_14h_12m_18s_3.5ms", "CIEC_TIME(\"T#5d14h12m18s3ms500us\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of( "LTIME#5m_30s_500ms_100.1us", "CIEC_TIME(\"LT#5m30s500ms100us100ns\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of( "TIME#25h_15m", "CIEC_TIME(\"T#25h15m\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of( "ltime#5d_14h_12m_18s_3.5ms", "CIEC_TIME(\"LT#5d14h12m18s3ms500us\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of( "LTIME#34s_345ns", "CIEC_TIME(\"LT#34s345ns\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$

				Arguments.of( "D#1996-08-12", "CIEC_DATE(\"D#1996-08-12\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of( "TOD#06:06:5", "CIEC_TIME_OF_DAY(\"TOD#06:06:05\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of( "DT#1989-06-15-13:56:14.77", "CIEC_DATE_AND_TIME(\"DT#1989-06-15-13:56:14.770000000\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$

				Arguments.of( "DATE#1984-06-25", "CIEC_DATE(\"D#1984-06-25\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of( "date#2010-09-22", "CIEC_DATE(\"D#2010-09-22\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of( "D#1984-06-25", "CIEC_DATE(\"D#1984-06-25\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of( "LDATE#2012-02-29", "CIEC_DATE(\"LD#2012-02-29\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of( "LD#1984-06-25", "CIEC_DATE(\"LD#1984-06-25\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of( "TIME_OF_DAY#15:36:55.36", "CIEC_TIME_OF_DAY(\"TOD#15:36:55.360000000\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of( "TOD#15:36:55.36", "CIEC_TIME_OF_DAY(\"TOD#15:36:55.360000000\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of( "LTOD#15:36:55.36", "CIEC_TIME_OF_DAY(\"LTOD#15:36:55.360000000\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of( "LTIME_OF_DAY#15:36:55.36", "CIEC_TIME_OF_DAY(\"LTOD#15:36:55.360000000\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of( "DATE_AND_TIME#1984-06-25-15:36:55.360227400", //$NON-NLS-1$
						"CIEC_DATE_AND_TIME(\"DT#1984-06-25-15:36:55.360227400\")", VALID_VALUE ), //$NON-NLS-1$
				Arguments.of( "DT#1984-06-25-15:36:55.360_227_400", "CIEC_DATE_AND_TIME(\"DT#1984-06-25-15:36:55.360227400\")", //$NON-NLS-1$ //$NON-NLS-2$
						VALID_VALUE ), //
				Arguments.of( "LDATE_AND_TIME#1984-06-25-15:36:55.360_227_400", //$NON-NLS-1$
						"CIEC_DATE_AND_TIME(\"LDT#1984-06-25-15:36:55.360227400\")", VALID_VALUE ), //$NON-NLS-1$
				Arguments.of( "LDT#1984-06-25-15:36:55.360_227_400", "CIEC_DATE_AND_TIME(\"LDT#1984-06-25-15:36:55.360227400\")", //$NON-NLS-1$ //$NON-NLS-2$
						VALID_VALUE ), //

				Arguments.of( "LDT#1984-06-25-15:36:55.360_227_400_123", "", //$NON-NLS-1$ //$NON-NLS-2$
						INVALID_VALUE ), //
				Arguments.of( "D#1996-13-12", "", INVALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of( "TOD#24:06:5", "", INVALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of( "DT#1989-06-31-13:56:14.77", "", INVALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$

				Arguments.of( "TIME#61m", "CIEC_TIME(\"T#61m\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of( "TIME#1h61m", "", INVALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of( "TIME#61d", "CIEC_TIME(\"T#61d\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of( "TIME#1d24h", "", INVALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of( "T#1m52", "", INVALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of( "DWORD#1m52", "", INVALID_VALUE ) //$NON-NLS-1$ //$NON-NLS-2$
				)
	}

	@ParameterizedTest(name = "{index}: {0}->{1}")
	@MethodSource("testCases")
	def datetimeExpression(String expression, String expectation, boolean isValid) {
		val generatedCode = generateExpression(getFunctionBlock(), expression, getErrors())

		if (isValid) {
			assertNoErrors(getErrors())
			assertNotNull(generatedCode)
			assertEquals(expectation, generatedCode.toString())
		} else {
			assertErrors(getErrors())
			assertNull(generatedCode)
		}
	}

}