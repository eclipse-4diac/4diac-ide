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
package org.eclipse.fordiac.ide.test.export.forte_ng

import org.eclipse.fordiac.ide.test.export.ExporterTestBase
import org.eclipse.fordiac.ide.test.export.ExporterTestBasicFBTypeBase
import org.junit.jupiter.api.Test

import static org.eclipse.fordiac.ide.model.FordiacKeywords.*
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.fail

class ForteNgTest extends ExporterTestBasicFBTypeBase {

	@Test
	def emptyExpression() {
		var generatedCode = generateExpression(functionBlock, "", errors) // $NON-NLS-1$
		assertNoErrors(errors) // Expression can be empty
		assertNull(generatedCode)
	}

	@Test
	def assignmentExpression() {
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE_NAME, BOOL))

		var generatedCode = generateExpression(functionBlock, '''«VARIABLE_NAME» := 1''', errors) // $NON-NLS-1$
		assertErrors(errors) // Expression can not be an assignment
		assertNull(generatedCode)
	}

	@Test
	def simpleAssignmentAlgorithm() {
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE_NAME, BOOL))
		functionBlock.callables.add(createSTAlgorithm(ALGORITHM_NAME, '''«VARIABLE_NAME» := 1;''')) // $NON-NLS-1$
		var generatedCode = generateAlgorithm(functionBlock, ALGORITHM_NAME, errors)

		assertNoErrors(errors)
		assertNotNull(generatedCode)
		assertEquals('''
			
			«EXPORTED_VARIABLE_NAME» = 1_BOOL;
		'''.toString(), generatedCode.toString()) // $NON-NLS-1$
	}

	@Test
	def functionSQRTExpression() {
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE_NAME, REAL))

		var generatedCode = generateExpression(functionBlock, '''SQRT(«VARIABLE_NAME») = 0''', errors) // $NON-NLS-1$
		assertNoErrors(errors)
		assertNotNull(generatedCode)
		assertEquals('''func_EQ(func_SQRT(«EXPORTED_VARIABLE_NAME»), 0_SINT)'''.toString(), generatedCode.toString()) // $NON-NLS-1$
	}

	@Test
	def powerExpression() {
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE_NAME, REAL))
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE2_NAME, REAL))

		var generatedCode = generateExpression(functionBlock, '''«VARIABLE_NAME» ** «VARIABLE2_NAME» = 0''', errors) // $NON-NLS-1$
		assertNoErrors(errors)
		assertNotNull(generatedCode)
		assertEquals('''func_EQ(func_EXPT<CIEC_REAL>(«EXPORTED_VARIABLE_NAME», «EXPORTED_VARIABLE2_NAME»), 0_SINT)'''.toString(), // $NON-NLS-1$
		generatedCode.toString())
	}

	@Test
	def timeAssignmentAlgorithm() {
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE_NAME, "TIME")) // $NON-NLS-1$
		functionBlock.getCallables().add(createSTAlgorithm(ALGORITHM_NAME, '''«VARIABLE_NAME» := TIME#1m;'''))

		var generatedCode = generateAlgorithm(functionBlock, ALGORITHM_NAME, errors)

		assertNoErrors(errors)
		assertNotNull(generatedCode)
		assertEquals('''
			
			«EXPORTED_VARIABLE_NAME» = 60000000000_TIME;
		'''.toString(), generatedCode.toString())
	}

	@Test
	def dateAssignmentAlgorithm() {
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE_NAME, "DATE")) // $NON-NLS-1$
		functionBlock.getCallables().add(createSTAlgorithm(ALGORITHM_NAME, '''«VARIABLE_NAME» := D#1996-08-12;'''))

		var generatedCode = generateAlgorithm(functionBlock, ALGORITHM_NAME, errors)

		assertNoErrors(errors)
		assertNotNull(generatedCode)
		assertEquals('''
			
			«EXPORTED_VARIABLE_NAME» = 839808000000000000_DATE;
		'''.toString(), generatedCode.toString())
	}

	@Test
	def todAssignmentAlgorithm() {
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE_NAME, "TOD")) // $NON-NLS-1$
		functionBlock.getCallables().add(createSTAlgorithm(ALGORITHM_NAME, '''«VARIABLE_NAME» := TOD#06:06:59;'''))

		var generatedCode = generateAlgorithm(functionBlock, ALGORITHM_NAME, errors)

		assertNoErrors(errors)
		assertNotNull(generatedCode)
		assertEquals('''
			
			«EXPORTED_VARIABLE_NAME» = 22019000000000_TIME_OF_DAY;
		'''.toString(), generatedCode.toString())
	}

	@Test
	def datetimeAssignmentAlgorithm() {
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE_NAME, "DT")) // $NON-NLS-1$
		functionBlock.getCallables().add(
			createSTAlgorithm(ALGORITHM_NAME, '''«VARIABLE_NAME» := DT#1989-06-15-13:56:14.77;'''))

		var generatedCode = generateAlgorithm(functionBlock, ALGORITHM_NAME, errors)

		assertNoErrors(errors)
		assertNotNull(generatedCode)
		assertEquals('''
			
			«EXPORTED_VARIABLE_NAME» = 613922174770000000_DATE_AND_TIME;
		'''.toString(), generatedCode.toString())
	}

	@Test
	def addExpression() {
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE_NAME, REAL))
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE2_NAME, REAL))

		var generatedCode = generateExpression(functionBlock, '''«VARIABLE_NAME» + «VARIABLE2_NAME» = 0''', errors) // $NON-NLS-1$
		assertNoErrors(errors)
		assertNotNull(generatedCode)
		assertEquals('''func_EQ(func_ADD<CIEC_REAL>(«EXPORTED_VARIABLE_NAME», «EXPORTED_VARIABLE2_NAME»), 0_SINT)'''.toString(), // $NON-NLS-1$
		generatedCode.toString())
	}

	@Test
	def subExpression() {
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE_NAME, REAL))
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE2_NAME, REAL))

		var generatedCode = generateExpression(functionBlock, '''«VARIABLE_NAME» - «VARIABLE2_NAME» = 0''', errors) // $NON-NLS-1$
		assertNoErrors(errors)
		assertNotNull(generatedCode)
		assertEquals('''func_EQ(func_SUB<CIEC_REAL>(«EXPORTED_VARIABLE_NAME», «EXPORTED_VARIABLE2_NAME»), 0_SINT)'''.toString(), // $NON-NLS-1$
		generatedCode.toString())
	}

	@Test
	def divExpression() {
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE_NAME, REAL))
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE2_NAME, REAL))

		var generatedCode = generateExpression(functionBlock, '''«VARIABLE_NAME» / «VARIABLE2_NAME» = 0''', errors) // $NON-NLS-1$
		assertNoErrors(errors)
		assertNotNull(generatedCode)
		assertEquals('''func_EQ(func_DIV<CIEC_REAL>(«EXPORTED_VARIABLE_NAME», «EXPORTED_VARIABLE2_NAME»), 0_SINT)'''.toString(), // $NON-NLS-1$
		generatedCode.toString())
	}

	@Test
	def mulExpression() {
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE_NAME, REAL))
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE2_NAME, REAL))

		var generatedCode = generateExpression(functionBlock, '''«VARIABLE_NAME» * «VARIABLE2_NAME» = 0''', errors) // $NON-NLS-1$
		assertNoErrors(errors)
		assertNotNull(generatedCode)
		assertEquals('''func_EQ(func_MUL<CIEC_REAL>(«EXPORTED_VARIABLE_NAME», «EXPORTED_VARIABLE2_NAME»), 0_SINT)'''.toString(), // $NON-NLS-1$
		generatedCode.toString())
	}
	
	@Test
	def mulTimeRealExpression() {
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE_NAME, TIME))
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE2_NAME, REAL))

		var generatedCode = generateExpression(functionBlock, '''«VARIABLE_NAME» * «VARIABLE2_NAME» = T#0s''', errors) // $NON-NLS-1$
		assertNoErrors(errors)
		assertNotNull(generatedCode)
		assertEquals('''func_EQ(func_MUL<CIEC_TIME>(«EXPORTED_VARIABLE_NAME», «EXPORTED_VARIABLE2_NAME»), 0_TIME)'''.toString(), // $NON-NLS-1$
		generatedCode.toString())
	}
	
	@Test
	def mulTimeLintExpression() {
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE_NAME, TIME))
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE2_NAME, LINT))

		var generatedCode = generateExpression(functionBlock, '''«VARIABLE_NAME» * «VARIABLE2_NAME» = T#0s''', errors) // $NON-NLS-1$
		assertNoErrors(errors)
		assertNotNull(generatedCode)
		assertEquals('''func_EQ(func_MUL<CIEC_TIME>(«EXPORTED_VARIABLE_NAME», «EXPORTED_VARIABLE2_NAME»), 0_TIME)'''.toString(), // $NON-NLS-1$
		generatedCode.toString())
	}

	@Test
	def void otherAlgorithmBasic() {
		val ALGORITHM_TEXT = '''int i = 0; i++;'''

		functionBlock.callables.add(createOtherAlgorithm(ALGORITHM_NAME, ALGORITHM_TEXT, "C++"))

		val exports = generateFunctionBlock(functionBlock)

		var headerfileFound = false
		var cppfileFound = false

		for (export : exports) {
			switch export.getName() {
				case '''«ExporterTestBase.BASICFUNCTIONBLOCK_NAME»_fbt.h''': {
					headerfileFound = true

					assertEquals('''
						/*************************************************************************
						 *** FORTE Library Element
						 ***
						 *** This file was generated using the 4DIAC FORTE Export Filter V1.0.x NG!
						 ***
						 *** Name: «ExporterTestBase.BASICFUNCTIONBLOCK_NAME»
						 *** Description:
						 *** Version:
						 *************************************************************************/
						
						#pragma once
						
						#include "basicfb.h"
						#include "iec61131_functions.h"
						#include "forte_array_common.h"
						#include "forte_array.h"
						#include "forte_array_fixed.h"
						#include "forte_array_variable.h"
						
						class «EXPORTED_FUNCTIONBLOCK_NAME» final : public CBasicFB {
						  DECLARE_FIRMWARE_FB(«EXPORTED_FUNCTIONBLOCK_NAME»)
						
						  private:
						
						    static const SFBInterfaceSpec scmFBInterfaceSpec;
						
						    CIEC_ANY *getVarInternal(size_t) override;
						
						    void «EXPORTED_ALGORITHM_NAME»(void);
						
						    static const TForteInt16 scmStateINIT = 0;
						
						    void enterStateINIT(CEventChainExecutionThread *const paECET);
						
						    void executeEvent(TEventID paEIID, CEventChainExecutionThread *const paECET) override;
						
						    void readInputData(TEventID paEIID) override;
						    void writeOutputData(TEventID paEIID) override;
						
						  public:
						    «EXPORTED_FUNCTIONBLOCK_NAME»(CStringDictionary::TStringId paInstanceNameId, forte::core::CFBContainer &paContainer);
						
						    CIEC_ANY *getDI(size_t) override;
						    CIEC_ANY *getDO(size_t) override;
						    CEventConnection *getEOConUnchecked(TPortId) override;
						    CDataConnection **getDIConUnchecked(TPortId) override;
						    CDataConnection *getDOConUnchecked(TPortId) override;
						};
						
					'''.toString(), export.data.toString())
					assertNoErrors(export.errors)
					assertNoErrors(export.warnings)
					assertNoErrors(export.infos)
				}
				case '''«ExporterTestBase.BASICFUNCTIONBLOCK_NAME»_fbt.cpp''': {
					cppfileFound = true

					assertEquals('''
						/*************************************************************************
						 *** FORTE Library Element
						 ***
						 *** This file was generated using the 4DIAC FORTE Export Filter V1.0.x NG!
						 ***
						 *** Name: «ExporterTestBase.BASICFUNCTIONBLOCK_NAME»
						 *** Description:
						 *** Version:
						 *************************************************************************/
						
						#include "«ExporterTestBase.BASICFUNCTIONBLOCK_NAME»_fbt.h"
						#ifdef FORTE_ENABLE_GENERATED_SOURCE_CPP
						#include "«ExporterTestBase.BASICFUNCTIONBLOCK_NAME»_fbt_gen.cpp"
						#endif
						
						#include "iec61131_functions.h"
						#include "forte_array_common.h"
						#include "forte_array.h"
						#include "forte_array_fixed.h"
						#include "forte_array_variable.h"
						
						DEFINE_FIRMWARE_FB(«EXPORTED_FUNCTIONBLOCK_NAME», g_nStringIdfunctionblock)
						
						const SFBInterfaceSpec «EXPORTED_FUNCTIONBLOCK_NAME»::scmFBInterfaceSpec = {
						  0, nullptr, nullptr, nullptr, nullptr,
						  0, nullptr, nullptr, nullptr, nullptr,
						  0, nullptr, nullptr,
						  0, nullptr, nullptr,
						  0, nullptr,
						  0, nullptr
						};
						
						FORTE_«ExporterTestBase.BASICFUNCTIONBLOCK_NAME»::FORTE_«ExporterTestBase.BASICFUNCTIONBLOCK_NAME»(const CStringDictionary::TStringId paInstanceNameId, forte::core::CFBContainer &paContainer) :
						    CBasicFB(paContainer, scmFBInterfaceSpec, paInstanceNameId, nullptr) {
						}
						
						void FORTE_«ExporterTestBase.BASICFUNCTIONBLOCK_NAME»::executeEvent(TEventID paEIID, CEventChainExecutionThread *const paECET) {
						  do {
						    switch(mECCState) {
						      case scmStateINIT:
						        return; //no transition cleared
						      default:
						        DEVLOG_ERROR("The state is not in the valid range! The state value is: %d. The max value can be: 1.", mECCState.operator TForteUInt16 ());
						        mECCState = 0; // 0 is always the initial state
						        return;
						    }
						    paEIID = cgInvalidEventID; // we have to clear the event after the first check in order to ensure correct behavior
						  } while(true);
						}
						
						void FORTE_functionblock::enterStateINIT(CEventChainExecutionThread *const) {
						  mECCState = scmStateINIT;
						}
						
						void FORTE_functionblock::readInputData(TEventID) {
						  // nothing to do
						}
						
						void FORTE_functionblock::writeOutputData(TEventID) {
						  // nothing to do
						}
						
						CIEC_ANY *FORTE_functionblock::getDI(size_t) {
						  return nullptr;
						}
						
						CIEC_ANY *FORTE_functionblock::getDO(size_t) {
						  return nullptr;
						}
						
						CEventConnection *FORTE_functionblock::getEOConUnchecked(TPortId) {
						  return nullptr;
						}
						
						CDataConnection **FORTE_functionblock::getDIConUnchecked(TPortId) {
						  return nullptr;
						}
						
						CDataConnection *FORTE_functionblock::getDOConUnchecked(TPortId) {
						  return nullptr;
						}
						
						CIEC_ANY *FORTE_functionblock::getVarInternal(size_t) {
						  return nullptr;
						}
						
						void FORTE_«ExporterTestBase.BASICFUNCTIONBLOCK_NAME»::«EXPORTED_ALGORITHM_NAME»(void) {
						  #pragma GCC warning "Algorithm of type: 'C++' may lead to unexpected results!"
						  #pragma message ("warning Algorithm of type: 'C++' may lead to unexpected results!")
						  «ALGORITHM_TEXT»
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
