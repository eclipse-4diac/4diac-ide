package org.eclipse.fordiac.ide.export.forte_ng.tests

import org.junit.Test
import static org.junit.Assert.assertNull
import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertEquals

class ForteNgTest extends ForteNgTestBasicFBTypeBase {

	@Test
	def emptyExpression() {
		var generatedCode = generateExpression(functionBlock, "", errors) //$NON-NLS-1$

		assertErrors(errors) // Expression can not be empty
		assertNull(generatedCode)
	}

	@Test
	def assignmentExpression() {
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE_NAME, BOOL))

		var generatedCode = generateExpression(functionBlock, '''«VARIABLE_NAME» := 1''', errors) //$NON-NLS-1$

		assertErrors(errors) // Expression can not be an assignment
		assertNull(generatedCode)
	}

	@Test
	def simpleAssignmentAlgorithm() {
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE_NAME, BOOL))
		functionBlock.getAlgorithm()
				.add(createSTAlgorithm(ALGORITHM_NAME, '''«VARIABLE_NAME» := 1;''')) //$NON-NLS-1$

		var generatedCode = generateAlgorithm(functionBlock, ALGORITHM_NAME, errors)

		assertNoErrors(errors)
		assertNotNull(generatedCode)
		assertEquals('''«EXPORTED_VARIABLE_NAME»() = 1;
		'''.toString(), generatedCode.toString()) //$NON-NLS-1$
	}

	@Test
	def functionSQRTExpression() {
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE_NAME, REAL))

		var generatedCode = generateExpression(functionBlock, '''SQRT(«VARIABLE_NAME»)''', errors) //$NON-NLS-1$

		assertNoErrors(errors) // Expression can not be an assignment
		assertNotNull(generatedCode)
		assertEquals('''SQRT(«EXPORTED_VARIABLE_NAME»())'''.toString(), generatedCode.toString()) //$NON-NLS-1$
	}

	@Test
	def powerExpression() {
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE_NAME, REAL))
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE2_NAME, REAL))

		var generatedCode = generateExpression(functionBlock, '''«VARIABLE_NAME» ** «VARIABLE2_NAME»''', errors) //$NON-NLS-1$

		assertNoErrors(errors); // Expression can not be an assignment
		assertNotNull(generatedCode);
		assertEquals('''EXPT(«EXPORTED_VARIABLE_NAME»(), «EXPORTED_VARIABLE2_NAME»())'''.toString(), //$NON-NLS-1$
				generatedCode.toString())
	}

	@Test
	def timeAssignmentAlgorithm() {
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE_NAME, "TIME"))//$NON-NLS-1$
		functionBlock.getAlgorithm().add(createSTAlgorithm(ALGORITHM_NAME, '''«VARIABLE_NAME» := TIME#1m;'''))

		var generatedCode = generateAlgorithm(functionBlock, ALGORITHM_NAME, errors)

		assertNoErrors(errors)
		assertNotNull(generatedCode)
		assertEquals('''«EXPORTED_VARIABLE_NAME»() = CIEC_TIME("T#1m");
'''.toString(), generatedCode.toString())
	}

	@Test
	def dateAssignmentAlgorithm() {
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE_NAME, "DATE"))//$NON-NLS-1$
		functionBlock.getAlgorithm().add(createSTAlgorithm(ALGORITHM_NAME, '''«VARIABLE_NAME» := D#1996-08-12;'''))

		var generatedCode = generateAlgorithm(functionBlock, ALGORITHM_NAME, errors)

		assertNoErrors(errors)
		assertNotNull(generatedCode)
		assertEquals('''«EXPORTED_VARIABLE_NAME»() = CIEC_DATE("D#1996-08-12");
'''.toString(), generatedCode.toString())
	}

	@Test
	def todAssignmentAlgorithm() {
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE_NAME, "TOD"))//$NON-NLS-1$
		functionBlock.getAlgorithm().add(createSTAlgorithm(ALGORITHM_NAME, '''«VARIABLE_NAME» := TOD#06:06:59;'''))

		var generatedCode = generateAlgorithm(functionBlock, ALGORITHM_NAME, errors)

		assertNoErrors(errors)
		assertNotNull(generatedCode)
		assertEquals('''«EXPORTED_VARIABLE_NAME»() = CIEC_TIME_OF_DAY("TOD#06:06:59");
'''.toString(), generatedCode.toString())
	}

	@Test
	def datetimeAssignmentAlgorithm() {
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE_NAME, "DT"))//$NON-NLS-1$
		functionBlock.getAlgorithm().add(createSTAlgorithm(ALGORITHM_NAME, '''«VARIABLE_NAME» := DT#1989-06-15-13:56:14.77;'''))

		var generatedCode = generateAlgorithm(functionBlock, ALGORITHM_NAME, errors)

		assertNoErrors(errors)
		assertNotNull(generatedCode)
		assertEquals('''«EXPORTED_VARIABLE_NAME»() = CIEC_DATE_AND_TIME("DT#1989-06-15-13:56:14.770000000");
'''.toString(), generatedCode.toString())
	}

	@Test
	def addExpression() {
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE_NAME, REAL))
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE2_NAME, REAL))

		var generatedCode = generateExpression(functionBlock, '''«VARIABLE_NAME» + «VARIABLE2_NAME»''', errors) //$NON-NLS-1$

		assertNoErrors(errors); // Expression can not be an assignment
		assertNotNull(generatedCode);
		assertEquals('''ADD(«EXPORTED_VARIABLE_NAME»(), «EXPORTED_VARIABLE2_NAME»())'''.toString(), //$NON-NLS-1$
				generatedCode.toString())
	}
	
	@Test
	def subExpression() {
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE_NAME, REAL))
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE2_NAME, REAL))

		var generatedCode = generateExpression(functionBlock, '''«VARIABLE_NAME» - «VARIABLE2_NAME»''', errors) //$NON-NLS-1$

		assertNoErrors(errors); // Expression can not be an assignment
		assertNotNull(generatedCode);
		assertEquals('''SUB(«EXPORTED_VARIABLE_NAME»(), «EXPORTED_VARIABLE2_NAME»())'''.toString(), //$NON-NLS-1$
				generatedCode.toString())
	}
	
	@Test
	def divExpression() {
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE_NAME, REAL))
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE2_NAME, REAL))

		var generatedCode = generateExpression(functionBlock, '''«VARIABLE_NAME» / «VARIABLE2_NAME»''', errors) //$NON-NLS-1$

		assertNoErrors(errors); // Expression can not be an assignment
		assertNotNull(generatedCode);
		assertEquals('''DIV(«EXPORTED_VARIABLE_NAME»(), «EXPORTED_VARIABLE2_NAME»())'''.toString(), //$NON-NLS-1$
				generatedCode.toString())
	}
	
	@Test
	def mulExpression() {
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE_NAME, REAL))
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE2_NAME, REAL))

		var generatedCode = generateExpression(functionBlock, '''«VARIABLE_NAME» * «VARIABLE2_NAME»''', errors) //$NON-NLS-1$

		assertNoErrors(errors); // Expression can not be an assignment
		assertNotNull(generatedCode);
		assertEquals('''MUL(«EXPORTED_VARIABLE_NAME»(), «EXPORTED_VARIABLE2_NAME»())'''.toString(), //$NON-NLS-1$
				generatedCode.toString())
	}
	
}