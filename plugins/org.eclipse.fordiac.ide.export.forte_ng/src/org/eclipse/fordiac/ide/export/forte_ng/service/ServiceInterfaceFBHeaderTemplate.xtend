/*******************************************************************************
 * Copyright (c) 2019, 2024 fortiss GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng.service

import java.nio.file.Path
import java.util.Map
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType

class ServiceInterfaceFBHeaderTemplate extends ForteFBTemplate<ServiceInterfaceFBType> {

	new(ServiceInterfaceFBType type, String name, Path prefix, Map<?,?> options) {
		super(type, name, prefix, "CFunctionBlock", options)
	}

	override generate() '''
		«generateHeader»
		
		«generateIncludeGuardStart»
		
		«generateHeaderIncludes»
		
		«generateFBClassHeader»
		  «generateFBDeclaration»
		
		  private:
		    «generateFBInterfaceDeclaration»
		
		    «generateFBInterfaceSpecDeclaration»
		
		    void executeEvent(TEventID paEIID, CEventChainExecutionThread *const paECET) override;
		
		    «generateReadInputDataDeclaration»
		    «generateWriteOutputDataDeclaration»
		    «(type.interfaceList.inputVars + type.interfaceList.inOutVars + type.interfaceList.outputVars).generateSetInitialValuesDeclaration»
		
		  public:
		    «FBClassName»(CStringDictionary::TStringId paInstanceNameId, forte::core::CFBContainer &paContainer);
		    «generateInitializeDeclaration»
		
		    «generateInterfaceDeclarations»
		};
		
		«generateIncludeGuardEnd»
		
	'''

	override protected generateHeaderIncludes() '''
		«generateDependencyInclude("funcbloc.h")»
		«super.generateHeaderIncludes»
	'''
}
