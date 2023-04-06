/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Ernst Blecha
 *     - test for forte_ng
 *******************************************************************************/

package org.eclipse.fordiac.ide.test.export.forte_ng

import org.eclipse.fordiac.ide.test.export.ExporterTestBase
import org.eclipse.fordiac.ide.test.export.ExporterTestBasicFBTypeBase
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.fail

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests
class ForteNgBasicFBTest extends ExporterTestBasicFBTypeBase {

	@Test
	def exportValidBasicFB() {
		functionBlock.getCallables().add(createSTAlgorithm(ALGORITHM_NAME, '''
		VAR_TEMP
		  «VARIABLE_NAME» : ARRAY [0..31] OF DWORD;
		END_VAR'''))

		val exports = generateFunctionBlock(functionBlock);

		var headerfileFound = false;
		var cppfileFound = false;

		for (export : exports) {
			switch export.getName() {
				case '''«ExporterTestBase.BASICFUNCTIONBLOCK_NAME».h''': {
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
						
						
						class «EXPORTED_FUNCTIONBLOCK_NAME»: public CBasicFB {
						  DECLARE_FIRMWARE_FB(«EXPORTED_FUNCTIONBLOCK_NAME»)
						
						private:
						  
						  
						  
						  
						
						  static const SFBInterfaceSpec scm_stFBInterfaceSpec;
						  CIEC_ANY *getVarInternal(size_t) override;
						  void «EXPORTED_ALGORITHM_NAME»(void);
						  static const TForteInt16 scm_nStateINIT = 0;
						  
						  void enterStateINIT(void);
						
						  void executeEvent(int pa_nEIID) override;
						
						  void readInputData(size_t pa_nEIID) override;
						  void writeOutputData(size_t pa_nEIID) override;
						
						public:
						  «EXPORTED_FUNCTIONBLOCK_NAME»(CStringDictionary::TStringId pa_nInstanceNameId, CResource *pa_poSrcRes);
						
						
						  
						  
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
				case '''«ExporterTestBase.BASICFUNCTIONBLOCK_NAME».cpp''': {
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
						
						#include "«ExporterTestBase.BASICFUNCTIONBLOCK_NAME».h"
						#ifdef FORTE_ENABLE_GENERATED_SOURCE_CPP
						#include "«ExporterTestBase.BASICFUNCTIONBLOCK_NAME»_gen.cpp"
						#endif
						
						#include "criticalregion.h"
						#include "resource.h"
						#include "forte_dword.h"
						#include "forte_sint.h"
						#include "iec61131_functions.h"
						#include "forte_array_common.h"
						#include "forte_array.h"
						#include "forte_array_fixed.h"
						#include "forte_array_variable.h"
						
						DEFINE_FIRMWARE_FB(«EXPORTED_FUNCTIONBLOCK_NAME», g_nStringId«ExporterTestBase.BASICFUNCTIONBLOCK_NAME»)
						
						
						
						
						
						
						const SFBInterfaceSpec «EXPORTED_FUNCTIONBLOCK_NAME»::scm_stFBInterfaceSpec = {
						  0, nullptr, nullptr, nullptr,
						  0, nullptr, nullptr, nullptr,
						  0, nullptr, nullptr,
						  0, nullptr, nullptr,
						  0, nullptr
						};
						
						
						«EXPORTED_FUNCTIONBLOCK_NAME»::«EXPORTED_FUNCTIONBLOCK_NAME»(CStringDictionary::TStringId pa_nInstanceNameId, CResource *pa_poSrcRes) :
						    CBasicFB(pa_poSrcRes, &scm_stFBInterfaceSpec, pa_nInstanceNameId, nullptr) {
						}
						
						void «EXPORTED_FUNCTIONBLOCK_NAME»::«EXPORTED_ALGORITHM_NAME»(void) {
						  CIEC_ARRAY_FIXED<CIEC_DWORD, 0, 31> st_lv_variable = CIEC_ARRAY_FIXED<CIEC_DWORD, 0, 31>{};
						  
						}
						
						
						void «EXPORTED_FUNCTIONBLOCK_NAME»::executeEvent(int pa_nEIID){
						  do {
						    switch(m_nECCState) {
						      case scm_nStateINIT:
						        return; //no transition cleared
						      default:
						        DEVLOG_ERROR("The state is not in the valid range! The state value is: %d. The max value can be: 1.", m_nECCState.operator TForteUInt16 ());
						        m_nECCState = 0; // 0 is always the initial state
						        return;
						    }
						    pa_nEIID = cg_nInvalidEventID; // we have to clear the event after the first check in order to ensure correct behavior
						  } while(true);
						}
						
						void «EXPORTED_FUNCTIONBLOCK_NAME»::readInputData(size_t pa_nEIID) {
						}
						
						void «EXPORTED_FUNCTIONBLOCK_NAME»::writeOutputData(size_t pa_nEIID) {
						}
						
						CIEC_ANY *«EXPORTED_FUNCTIONBLOCK_NAME»::getDI(size_t) {
						  return nullptr;
						}
						
						CIEC_ANY *«EXPORTED_FUNCTIONBLOCK_NAME»::getDO(size_t) {
						  return nullptr;
						}
						
						CEventConnection *«EXPORTED_FUNCTIONBLOCK_NAME»::getEOConUnchecked(TPortId) {
						  return nullptr;
						}
						
						CDataConnection **«EXPORTED_FUNCTIONBLOCK_NAME»::getDIConUnchecked(TPortId) {
						  return nullptr;
						}
						
						CDataConnection *«EXPORTED_FUNCTIONBLOCK_NAME»::getDOConUnchecked(TPortId) {
						  return nullptr;
						}
						
						CIEC_ANY *«EXPORTED_FUNCTIONBLOCK_NAME»::getVarInternal(size_t) {
						  return nullptr;
						}
						
						
						void «EXPORTED_FUNCTIONBLOCK_NAME»::enterStateINIT(void) {
						  m_nECCState = scm_nStateINIT;
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
