/*******************************************************************************
 * Copyright (c) 2012 Profactor GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.iec61499;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLMapImpl;
import org.eclipse.fordiac.ide.deployment.devResponse.DevResponsePackage;

public class ResponseMapping {
	private final Map<String, Object> loadOptions;

	public ResponseMapping() {
		loadOptions = new HashMap<>();
		loadOptions.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);

		final XMLMapImpl map = new XMLMapImpl();
		map.setNoNamespacePackage(DevResponsePackage.eINSTANCE);

		// mappings do not seem to make any difference this is why they have been
		// deleted
		// the loading seems to be case sensitive, therefore attributes in lower case
		// letters
		loadOptions.put(XMLResource.OPTION_XML_MAP, map);
	}

	/*
	 * query Resources, query FBs <Response ID="0"> <FBList> <FB Name="RTest"
	 * Type="EMB_RES"/> </FBList> </Response>
	 */

	/*
	 * query FB Interface <Response ID="0"> <FBType Comment="" Name="BOOL2BOOL">
	 * <InterfaceList> <EventInputs> <Event Comment="Normal Execution Request"
	 * Name="REQ" Type="Event"> <With Var="IN"/> </Event> </EventInputs>
	 * <EventOutputs> <Event Comment="Execution Confirmation" Name="CNF"
	 * Type="Event"> <With Var="OUT"/> </Event> </EventOutputs> <InputVars>
	 * <VarDeclaration Comment="Input event qualifier" Name="IN" Type="BOOL"/>
	 * </InputVars> <OutputVars> <VarDeclaration Comment="Output event qualifier"
	 * Name="OUT" Type="BOOL"/> </OutputVars> </InterfaceList> </FBType> </Response>
	 */

	/*
	 * Monitoring <Response ID="43"> <Watches> <Resource name="RTest"> <FB
	 * name="FlipFlop"> <Port name="Q"> <Data value="FALSE" forced="false"></Data>
	 * </Port> <Port name="CNF"> <Data value="0" time="0"></Data> </Port> <Port
	 * name="REQ"> <Data value="0" time="0"></Data> </Port> </FB> </Resource>
	 * </Watches> </Response>
	 */
	public Map<String, Object> getLoadOptions() {
		return loadOptions;
	}
}