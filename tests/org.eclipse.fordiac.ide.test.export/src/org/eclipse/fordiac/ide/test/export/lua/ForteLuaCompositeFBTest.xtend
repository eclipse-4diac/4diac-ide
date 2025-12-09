/*******************************************************************************
 * Copyright (c) 2020, 2021 fortiss GmbH.
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

import org.eclipse.fordiac.ide.test.export.ExporterTestCompositeFBTypeBase
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests
class ForteLuaCompositeFBTest extends ExporterTestCompositeFBTypeBase {

	@Test
	def exportValidBasicFB() {

		val luaString = generateLuaString(functionBlock);

		assertEquals('''
			local EI_«EVENT_INPUT_NAME» = 0
			local EO_«EVENT_OUTPUT_NAME» = 0
			local DI_«DATA_INPUT_NAME» = 33554432
			local DO_«DATA_OUTPUT_NAME» = 67108864
					
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
			
			local fbnSpec = {
			  internalFBs = {
			  },
			  parameters = {
			  },
			  eventConnections = {
			    {srcID = "EI1", srcFBNum = -1, dstID = "EO1", dstFBNum = -1}
			  },
			  fannedOutEventConnections = {
			  },
			  dataConnections = {
			    {srcID = "DI1", srcFBNum = -1, dstID = "DO1", dstFBNum = -1}
			  },
			  fannedOutDataConnections = {
			  },
			  adapterConnections = {
			  },
			  numFBs = 0,
			  numECons = 1,
			  numFECons = 0,
			  numDCons = 1,
			  numFDCons = 0,
			  numAdpCons = 0,
			  numParams = 0
			}
			
			return {interfaceSpec = interfaceSpec, fbnSpec = fbnSpec}
		'''.toString(), luaString)
	}

}
