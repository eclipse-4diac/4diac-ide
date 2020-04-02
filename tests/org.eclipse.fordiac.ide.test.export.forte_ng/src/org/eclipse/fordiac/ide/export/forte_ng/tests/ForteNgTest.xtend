package org.eclipse.fordiac.ide.export.forte_ng.tests

import org.junit.Test
import static org.junit.Assert.assertNull
import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertEquals

class ForteNgTest extends ForteNgTestBasicFBTypeBase implements DatatypeConstants {

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
		assertEquals('''«addExportPrefix("SQRT")»(«EXPORTED_VARIABLE_NAME»())'''.toString(), generatedCode.toString()) //$NON-NLS-1$
	}

	@Test
	def powerExpression() {
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE_NAME, REAL))
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE2_NAME, REAL))

		var generatedCode = generateExpression(functionBlock, '''«VARIABLE_NAME» ** «VARIABLE2_NAME»''', errors) //$NON-NLS-1$

		assertNoErrors(errors); // Expression can not be an assignment
		assertNotNull(generatedCode);
		assertEquals('''«addExportPrefix("EXPT")»(«EXPORTED_VARIABLE_NAME»(), «EXPORTED_VARIABLE2_NAME»())'''.toString(), //$NON-NLS-1$
				generatedCode.toString())
	}

}