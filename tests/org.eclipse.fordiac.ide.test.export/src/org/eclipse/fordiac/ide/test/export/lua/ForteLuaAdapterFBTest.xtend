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

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory
import org.eclipse.fordiac.ide.test.export.ExporterTestAdapterFBType
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests
class ForteLuaAdapterFBTest extends ExporterTestAdapterFBType {

	@Test
	def exportValidBasicFB() {

		val adt = LibraryElementFactory.eINSTANCE.createAdapterType;
		adt.adapterFBType = functionBlock;
		val luaString = generateLuaString(adt);

		assertEquals('''
			local interfaceSpec = {
			  numEIs = 1,
			  EINames = {"«ADAPTER_EVENT_INPUT_NAME»"},
			  EIWith = {0, 65535},
			  EIWithIndexes = {0},
			  numEOs = 1,
			  EONames = {"«ADAPTER_EVENT_OUTPUT_NAME»"},
			  EOWith = {0, 65535},
			  EOWithIndexes = {0},
			  numDIs = 1,
			  DINames = {"«ADAPTER_DATA_INPUT_NAME»"},
			  DIDataTypeNames = {"INT"},
			  numDOs = 1,
			  DONames = {"«ADAPTER_DATA_OUTPUT_NAME»"},
			  DODataTypeNames = {"INT"}
			}
			
			return {interfaceSpec = interfaceSpec}
		'''.toString(), luaString)
	}

}
