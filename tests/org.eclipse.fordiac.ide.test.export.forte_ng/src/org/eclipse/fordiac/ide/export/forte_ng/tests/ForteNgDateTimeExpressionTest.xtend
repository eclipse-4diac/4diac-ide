package org.eclipse.fordiac.ide.export.forte_ng.tests

import java.util.Collection
import org.junit.Test
import org.junit.runners.Parameterized
import org.junit.runner.RunWith
import static org.junit.Assert.assertNull
import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertEquals

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

@RunWith(Parameterized)
class ForteNgDateTimeExpressionTest extends ForteNgTestBasicFBTypeBase {

	static final boolean VALID_VALUE = true
	static final boolean INVALID_VALUE = !VALID_VALUE

	String expression
	String expectation
	boolean isValid

	@Parameterized.Parameters(name = "{index}: {0}->{1}")
	def static Collection<Object[]> testCases()  {
		return #[
				testCase( "TIME#1m", "CIEC_TIME(\"T#1m\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$

				testCase( "T#14ms", "CIEC_TIME(\"T#14ms\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				testCase( "T#-14ms", "CIEC_TIME(\"T#-14ms\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				testCase( "LT#14.7s", "CIEC_TIME(\"LT#14s700ms\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				testCase( "T#14.7m", "CIEC_TIME(\"T#14m42s\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				testCase( "T#14.7h", "CIEC_TIME(\"T#14h42m\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				testCase( "t#14.7d", "CIEC_TIME(\"T#14d16h48m\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				testCase( "t#25h15m", "CIEC_TIME(\"T#25h15m\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				testCase( "lt#5d14h12m18s3.5ms", "CIEC_TIME(\"LT#5d14h12m18s3ms500us\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				testCase( "t#12h4m34ms230us400ns", "CIEC_TIME(\"T#12h4m34ms230us400ns\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				testCase( "TIME#14ms", "CIEC_TIME(\"T#14ms\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				testCase( "TIME#-14ms", "CIEC_TIME(\"T#-14ms\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				testCase( "time#14.7s", "CIEC_TIME(\"T#14s700ms\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				testCase( "t#25h_15m", "CIEC_TIME(\"T#25h15m\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				testCase( "t#5d_14h_12m_18s_3.5ms", "CIEC_TIME(\"T#5d14h12m18s3ms500us\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				testCase( "LTIME#5m_30s_500ms_100.1us", "CIEC_TIME(\"LT#5m30s500ms100us100ns\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				testCase( "TIME#25h_15m", "CIEC_TIME(\"T#25h15m\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				testCase( "ltime#5d_14h_12m_18s_3.5ms", "CIEC_TIME(\"LT#5d14h12m18s3ms500us\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				testCase( "LTIME#34s_345ns", "CIEC_TIME(\"LT#34s345ns\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$

				testCase( "D#1996-08-12", "CIEC_DATE(\"D#1996-08-12\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				testCase( "TOD#06:06:5", "CIEC_TIME_OF_DAY(\"TOD#06:06:05\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				testCase( "DT#1989-06-15-13:56:14.77", "CIEC_DATE_AND_TIME(\"DT#1989-06-15-13:56:14.770000000\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$

				testCase( "DATE#1984-06-25", "CIEC_DATE(\"D#1984-06-25\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				testCase( "date#2010-09-22", "CIEC_DATE(\"D#2010-09-22\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				testCase( "D#1984-06-25", "CIEC_DATE(\"D#1984-06-25\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				testCase( "LDATE#2012-02-29", "CIEC_DATE(\"LD#2012-02-29\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				testCase( "LD#1984-06-25", "CIEC_DATE(\"LD#1984-06-25\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				testCase( "TIME_OF_DAY#15:36:55.36", "CIEC_TIME_OF_DAY(\"TOD#15:36:55.360000000\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				testCase( "TOD#15:36:55.36", "CIEC_TIME_OF_DAY(\"TOD#15:36:55.360000000\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				testCase( "LTOD#15:36:55.36", "CIEC_TIME_OF_DAY(\"LTOD#15:36:55.360000000\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				testCase( "LTIME_OF_DAY#15:36:55.36", "CIEC_TIME_OF_DAY(\"LTOD#15:36:55.360000000\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				testCase( "DATE_AND_TIME#1984-06-25-15:36:55.360227400", //$NON-NLS-1$
						"CIEC_DATE_AND_TIME(\"DT#1984-06-25-15:36:55.360227400\")", VALID_VALUE ), //$NON-NLS-1$
				testCase( "DT#1984-06-25-15:36:55.360_227_400", "CIEC_DATE_AND_TIME(\"DT#1984-06-25-15:36:55.360227400\")", //$NON-NLS-1$ //$NON-NLS-2$
						VALID_VALUE ), //
				testCase( "LDATE_AND_TIME#1984-06-25-15:36:55.360_227_400", //$NON-NLS-1$
						"CIEC_DATE_AND_TIME(\"LDT#1984-06-25-15:36:55.360227400\")", VALID_VALUE ), //$NON-NLS-1$
				testCase( "LDT#1984-06-25-15:36:55.360_227_400", "CIEC_DATE_AND_TIME(\"LDT#1984-06-25-15:36:55.360227400\")", //$NON-NLS-1$ //$NON-NLS-2$
						VALID_VALUE ), //

				testCase( "LDT#1984-06-25-15:36:55.360_227_400_123", "", //$NON-NLS-1$ //$NON-NLS-2$
						INVALID_VALUE ), //
				testCase( "D#1996-13-12", "", INVALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				testCase( "TOD#24:06:5", "", INVALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				testCase( "DT#1989-06-31-13:56:14.77", "", INVALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$

				testCase( "TIME#61m", "CIEC_TIME(\"T#61m\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				testCase( "TIME#1h61m", "", INVALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				testCase( "TIME#61d", "CIEC_TIME(\"T#61d\")", VALID_VALUE ), //$NON-NLS-1$ //$NON-NLS-2$
				testCase( "TIME#1d24h", "", INVALID_VALUE ) //$NON-NLS-1$ //$NON-NLS-2$
				]
	}

	@Test
	def datetimeExpression() {
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

	new(String expression, String expectation, Boolean isValid) {
		this.expression = expression
		this.expectation = expectation
		this.isValid = isValid
	}
}