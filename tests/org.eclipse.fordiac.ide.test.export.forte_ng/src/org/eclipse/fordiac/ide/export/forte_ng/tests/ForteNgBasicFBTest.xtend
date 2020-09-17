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

package org.eclipse.fordiac.ide.export.forte_ng.tests

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.fail
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

class ForteNgBasicFBTest extends ForteNgTestBasicFBTypeBase {

	@Test
	def exportValidBasicFB() {
		functionBlock.getAlgorithm().add(createSTAlgorithm(ALGORITHM_NAME, '''
		VAR
		  «VARIABLE_NAME» : ARRAY [0..31] OF DWORD;
		END_VAR'''))

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


					DEFINE_FIRMWARE_FB(«EXPORTED_FUNCTIONBLOCK_NAME», g_nStringId«FUNCTIONBLOCK_NAME»)






					const SFBInterfaceSpec «EXPORTED_FUNCTIONBLOCK_NAME»::scm_stFBInterfaceSpec = {
					  0, nullptr, nullptr, nullptr,
					  0, nullptr, nullptr, nullptr,
					  0, nullptr, nullptr,
					  0, nullptr, nullptr,
					  0, nullptr
					};

					void «EXPORTED_FUNCTIONBLOCK_NAME»::«EXPORTED_ALGORITHM_NAME»(void) {
					  CIEC_DWORD «EXPORTED_VARIABLE_NAME»[32];
					}



					void «EXPORTED_FUNCTIONBLOCK_NAME»::executeEvent(int pa_nEIID){
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
