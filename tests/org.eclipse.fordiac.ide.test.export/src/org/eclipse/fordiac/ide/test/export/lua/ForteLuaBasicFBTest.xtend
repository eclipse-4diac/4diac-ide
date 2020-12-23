/*******************************************************************************
 * Copyright (c) 2020 fortiss GmbH.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Kirill Dorofeev
 *     - tests for lua exporter
 *******************************************************************************/

package org.eclipse.fordiac.ide.test.export.lua

import org.eclipse.fordiac.ide.test.export.ExporterTestBasicFBTypeAdvanced
import org.junit.Test

import static org.junit.Assert.assertEquals

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests
class ForteLuaBasicFBTest extends ExporterTestBasicFBTypeAdvanced {

	@Test
	def exportValidBasicFB() {

		val luaString = generateLuaString(functionBlock);

		assertEquals('''
			local FB_STATE = 0
			local ECC_START = 0
			local EI_«EVENT_INPUT_NAME» = 0
			local EO_«EVENT_OUTPUT_NAME» = 0
			local DI_«DATA_INPUT_NAME» = 33554432
			local DO_«DATA_OUTPUT_NAME» = 67108864
			local AEO_«ADAPTER_SOCKET_NAME»_«ADAPTER_EVENT_INPUT_NAME» = 134283264
			local AEI_«ADAPTER_SOCKET_NAME»_«ADAPTER_EVENT_OUTPUT_NAME» = 134283264
			local ADO_«ADAPTER_SOCKET_NAME»_«ADAPTER_DATA_INPUT_NAME» = 167837696
			local ADI_«ADAPTER_SOCKET_NAME»_«ADAPTER_DATA_OUTPUT_NAME» = 201392128
			local AEO_«ADAPTER_PLUG_NAME»_«ADAPTER_EVENT_OUTPUT_NAME» = 134217728
			local AEI_«ADAPTER_PLUG_NAME»_«ADAPTER_EVENT_INPUT_NAME» = 134217728
			local ADO_«ADAPTER_PLUG_NAME»_«ADAPTER_DATA_OUTPUT_NAME» = 167772160
			local ADI_«ADAPTER_PLUG_NAME»_«ADAPTER_DATA_INPUT_NAME» = 201326592
			
			
			local function enterECC_START(fb)
			  fb[FB_STATE] = ECC_START
			  return true
			end
			
			
			local function transition(fb, id)
			  local STATE = fb[FB_STATE]
			  local VAR_«DATA_INPUT_NAME» = fb[DI_«DATA_INPUT_NAME»]
			  local VAR_«DATA_OUTPUT_NAME» = fb[DO_«DATA_OUTPUT_NAME»]
			  local VAR_«ADAPTER_SOCKET_NAME»_«ADAPTER_DATA_INPUT_NAME» = fb[ADO_«ADAPTER_SOCKET_NAME»_«ADAPTER_DATA_INPUT_NAME»]
			  local VAR_«ADAPTER_SOCKET_NAME»_«ADAPTER_DATA_OUTPUT_NAME» = fb[ADI_«ADAPTER_SOCKET_NAME»_«ADAPTER_DATA_OUTPUT_NAME»]
			  local VAR_«ADAPTER_PLUG_NAME»_«ADAPTER_DATA_INPUT_NAME» = fb[ADI_«ADAPTER_PLUG_NAME»_«ADAPTER_DATA_INPUT_NAME»]
			  local VAR_«ADAPTER_PLUG_NAME»_«ADAPTER_DATA_OUTPUT_NAME» = fb[ADO_«ADAPTER_PLUG_NAME»_«ADAPTER_DATA_OUTPUT_NAME»]
			  if ECC_START == STATE then
			  
			  else return false
			  end
			end
			
			local function executeEvent(fb, id)
			  local modified = transition(fb, id)
			  while modified do
			    modified = transition(fb, -1)
			  end
			end
			
			local interfaceSpec = {
			  numEIs = 1,
			  EINames = {"«EVENT_INPUT_NAME»"},
			  EIWith = {},
			  EIWithIndexes = {-1},
			  numEOs = 1,
			  EONames = {"«EVENT_OUTPUT_NAME»"},
			  EOWith = {},
			  EOWithIndexes = {-1},
			  numDIs = 1,
			  DINames = {"«DATA_INPUT_NAME»"},
			  DIDataTypeNames = {"INT"},
			  numDOs = 1,
			  DONames = {"«DATA_OUTPUT_NAME»"},
			  DODataTypeNames = {"INT"},
			  numAdapters = 2,
			  adapterInstanceDefinition = {
			    {adapterNameID = "«ADAPTER_PLUG_NAME»", adapterTypeNameID = "«ADAPTERFUNCTIONBLOCK_NAME»", isPlug = true},
			    {adapterNameID = "«ADAPTER_SOCKET_NAME»", adapterTypeNameID = "«ADAPTERFUNCTIONBLOCK_NAME»", isPlug = false}
			  }
			}
			
			local internalVarsInformation = {
			  numIntVars = 0,
			  intVarsNames = {},
			  intVarsDataTypeNames = {}
			}
			
			return {ECC = executeEvent, interfaceSpec = interfaceSpec, internalVarsInformation = internalVarsInformation}
		'''.toString(), luaString)
	}

}
