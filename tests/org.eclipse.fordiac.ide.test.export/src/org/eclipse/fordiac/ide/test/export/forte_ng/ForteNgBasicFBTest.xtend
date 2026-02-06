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

import org.eclipse.fordiac.ide.export.forte_ng.ForteNgExportTemplate
import org.eclipse.fordiac.ide.test.export.ExporterTestBase
import org.eclipse.fordiac.ide.test.export.ExporterTestBasicFBTypeBase
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.fail

import static extension org.eclipse.fordiac.ide.export.forte_ng.util.ForteNgExportUtil.getFORTEStringId

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
				case '''«ExporterTestBase.BASICFUNCTIONBLOCK_NAME»_fbt.h''': {
					headerfileFound = true

					assertEquals('''
						/*************************************************************************
						 *** FORTE Library Element
						 ***
						 *** «ForteNgExportTemplate.HEADER_TEXT»
						 ***
						 *** Name: «ExporterTestBase.BASICFUNCTIONBLOCK_NAME»
						 *** Description:
						 *** Version:
						 *************************************************************************/
						
						#pragma once
						
						#include "forte/basicfb.h"
						
						namespace forte {
						  class «EXPORTED_FUNCTIONBLOCK_NAME» final : public CBasicFB {
						      DECLARE_FIRMWARE_FB(«EXPORTED_FUNCTIONBLOCK_NAME»)
						
						    private:
						
						      CIEC_ANY *getVarInternal(size_t) override;
						
						      void «EXPORTED_ALGORITHM_NAME»(void);
						
						      static const TForteInt16 scmStateINIT = 0;
						
						      void enterStateINIT(CEventChainExecutionThread *const paECET);
						
						      void executeEvent(TEventID paEIID, CEventChainExecutionThread *const paECET) override;
						
						      void readInputData(TEventID paEIID) override;
						      void writeOutputData(TEventID paEIID) override;
						      void setInitialValues() override;
						
						    public:
						      «EXPORTED_FUNCTIONBLOCK_NAME»(StringId paInstanceNameId, CFBContainer &paContainer);
						
						      CIEC_ANY *getDI(size_t) override;
						      CIEC_ANY *getDO(size_t) override;
						      CEventConnection *getEOConUnchecked(TPortId) override;
						      CDataConnection **getDIConUnchecked(TPortId) override;
						      CDataConnection *getDOConUnchecked(TPortId) override;
						  };
						}
						
					'''.toString(), export.data.toString())
					assertNoErrors(export.errors)
				}
				case '''«ExporterTestBase.BASICFUNCTIONBLOCK_NAME»_fbt.cpp''': {
					cppfileFound = true

					assertEquals('''
					/*************************************************************************
					 *** FORTE Library Element
					 ***
					 *** «ForteNgExportTemplate.HEADER_TEXT»
					 ***
					 *** Name: «ExporterTestBase.BASICFUNCTIONBLOCK_NAME»
					 *** Description:
					 *** Version:
					 *************************************************************************/
					
					#include "forte/«ExporterTestBase.BASICFUNCTIONBLOCK_NAME»_fbt.h"
					
					#include "forte/datatypes/forte_array_fixed.h"
					#include "forte/datatypes/forte_dword.h"
					#include "forte/datatypes/forte_sint.h"
					
					using namespace std::literals;
					using namespace forte::literals;
					
					namespace forte {
					  namespace {
					    constexpr std::string_view TypeHash ="1234"sv;
					
					    const SFBInterfaceSpec cFBInterfaceSpec = {
					        .mEINames = {},
					        .mEITypeNames = {},
					        .mEONames = {},
					        .mEOTypeNames = {},
					        .mDINames = {},
					        .mDONames = {},
					        .mDIONames = {},
					        .mSocketNames = {},
					        .mPlugNames = {},
					    };
					  }
					
					  DEFINE_FIRMWARE_FB(«EXPORTED_FUNCTIONBLOCK_NAME», «ExporterTestBase.BASICFUNCTIONBLOCK_NAME.FORTEStringId», TypeHash)
					
					  «EXPORTED_FUNCTIONBLOCK_NAME»::«EXPORTED_FUNCTIONBLOCK_NAME»(const StringId paInstanceNameId, CFBContainer &paContainer) :
					      CBasicFB(paContainer, cFBInterfaceSpec, paInstanceNameId, {}) {
					  }
					
					  void «EXPORTED_FUNCTIONBLOCK_NAME»::setInitialValues() {
					    CBasicFB::setInitialValues();
					  }
					
					  void «EXPORTED_FUNCTIONBLOCK_NAME»::executeEvent(TEventID paEIID, CEventChainExecutionThread *const paECET) {
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
					
					  void «EXPORTED_FUNCTIONBLOCK_NAME»::enterStateINIT(CEventChainExecutionThread *const) {
					    mECCState = scmStateINIT;
					  }
					
					  void «EXPORTED_FUNCTIONBLOCK_NAME»::readInputData(TEventID) {
					    // nothing to do
					  }
					
					  void «EXPORTED_FUNCTIONBLOCK_NAME»::writeOutputData(TEventID) {
					    // nothing to do
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
					
					  void «EXPORTED_FUNCTIONBLOCK_NAME»::«EXPORTED_ALGORITHM_NAME»(void) {
					    CIEC_ARRAY_FIXED<CIEC_DWORD, 0, 31> st_lv_variable = CIEC_ARRAY_FIXED<CIEC_DWORD, 0, 31>{};
					
					  }
					
					}'''.toString(), export.data.toString())
					assertNoErrors(export.errors)
				}
				default:
					fail("unexpected export file")
			}
		}
		assertTrue(headerfileFound, "Header-File missing")
		assertTrue(cppfileFound, "CPP-File missing")
	}

}
