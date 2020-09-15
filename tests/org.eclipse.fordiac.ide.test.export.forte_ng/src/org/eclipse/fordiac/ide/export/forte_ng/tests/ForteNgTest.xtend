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

import org.junit.jupiter.api.Test
import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.fail
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.eclipse.fordiac.ide.model.FordiacKeywords.*

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

	@Test
	def void otherAlgorithmBasic() {
		val ALGORITHM_TEXT = '''int i = 0; i++;'''
		
		functionBlock.algorithm.add(createOtherAlgorithm(ALGORITHM_NAME, ALGORITHM_TEXT, "C++"))

		val exports = generateFunctionBlock(functionBlock);

		var headerfileFound = false;
		var cppfileFound = false;

		for (export : exports) {
			switch export.getName() {
				case '''«FUNCTIONBLOCK_NAME».h''': {
					headerfileFound = true
					
					assertEquals('''
					/*************************************************************************
					 *** FORTE Library Element
					 ***
					 *** This file was generated using the 4DIAC FORTE Export Filter V1.0.x NG!
					 ***
					 *** Name: «FUNCTIONBLOCK_NAME»
					 *** Description: 
					 *** Version:
					 *************************************************************************/

					#ifndef _«FUNCTIONBLOCK_NAME.toUpperCase»_H_
					#define _«FUNCTIONBLOCK_NAME.toUpperCase»_H_

					#include "basicfb.h"
					#include "forte_array_at.h"


					class «EXPORTED_FUNCTIONBLOCK_NAME»: public CBasicFB {
					  DECLARE_FIRMWARE_FB(«EXPORTED_FUNCTIONBLOCK_NAME»)

					private:
					  
					  
					  
					  

					  static const SFBInterfaceSpec scm_stFBInterfaceSpec;


					  void «EXPORTED_ALGORITHM_NAME»(void);

					  

					  virtual void executeEvent(int pa_nEIID);

					  FORTE_BASIC_FB_DATA_ARRAY(0, 0, 0, 0, 0);

					public:
					  «EXPORTED_FUNCTIONBLOCK_NAME»(CStringDictionary::TStringId pa_nInstanceNameId, CResource *pa_poSrcRes) :
					       CBasicFB(pa_poSrcRes, &scm_stFBInterfaceSpec, pa_nInstanceNameId, nullptr, m_anFBConnData, m_anFBVarsData) {
					  };

					  virtual ~«EXPORTED_FUNCTIONBLOCK_NAME»() = default;
					};

					#endif // _«FUNCTIONBLOCK_NAME.toUpperCase»_H_

					'''.toString(), export.data.toString())
					assertNoErrors(export.errors)
					assertNoErrors(export.warnings)
					assertNoErrors(export.infos)
				}
				case '''«FUNCTIONBLOCK_NAME».cpp''': {
					cppfileFound = true
					
					assertEquals('''
					/*************************************************************************
					 *** FORTE Library Element
					 ***
					 *** This file was generated using the 4DIAC FORTE Export Filter V1.0.x NG!
					 ***
					 *** Name: «FUNCTIONBLOCK_NAME»
					 *** Description: 
					 *** Version:
					 *************************************************************************/
					
					#include "«FUNCTIONBLOCK_NAME».h"
					#ifdef FORTE_ENABLE_GENERATED_SOURCE_CPP
					#include "«FUNCTIONBLOCK_NAME»_gen.cpp"
					#endif
					
					
					DEFINE_FIRMWARE_FB(«EXPORTED_FUNCTIONBLOCK_NAME», g_nStringIdfunctionblock)
					
					
					
					
					
					
					const SFBInterfaceSpec «EXPORTED_FUNCTIONBLOCK_NAME»::scm_stFBInterfaceSpec = {
					  0, nullptr, nullptr, nullptr,
					  0, nullptr, nullptr, nullptr,
					  0, nullptr, nullptr,
					  0, nullptr, nullptr,
					  0, nullptr
					};
					
					void FORTE_«FUNCTIONBLOCK_NAME»::«EXPORTED_ALGORITHM_NAME»(void) {
					  #pragma GCC warning "Algorithm of type: 'C++' may lead to unexpected results!"
					  #pragma message ("warning Algorithm of type: 'C++' may lead to unexpected results!")
					  «ALGORITHM_TEXT»
					}
					
					
					
					void FORTE_«FUNCTIONBLOCK_NAME»::executeEvent(int pa_nEIID){
					  bool bTransitionCleared;
					  do {
					    bTransitionCleared = true;
					    switch(m_nECCState) {
					      default:
					        DEVLOG_ERROR("The state is not in the valid range! The state value is: %d. The max value can be: 0.", m_nECCState.operator TForteUInt16 ());
					        m_nECCState = 0; // 0 is always the initial state
					        break;
					    }
					    pa_nEIID = cg_nInvalidEventID; // we have to clear the event after the first check in order to ensure correct behavior
					  } while(bTransitionCleared);
					}

					'''.toString(), export.data.toString())
					assertNoErrors(export.errors)
					assertNoErrors(export.warnings)
					assertNoErrors(export.infos)
				}
				default:
					fail("unexpected export file")
			}
		}
		assertTrue(headerfileFound, "Header-File missing")
		assertTrue(cppfileFound, "CPP-File missing")
	}

}