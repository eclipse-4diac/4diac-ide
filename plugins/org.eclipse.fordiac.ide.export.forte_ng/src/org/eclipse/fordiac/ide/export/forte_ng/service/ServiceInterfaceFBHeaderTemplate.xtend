/*******************************************************************************
 * Copyright (c) 2019 fortiss GmbH
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
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType
import org.eclipse.xtend.lib.annotations.Accessors

class ServiceInterfaceFBHeaderTemplate extends ForteFBTemplate {

	@Accessors(PROTECTED_GETTER) ServiceInterfaceFBType type

	new(ServiceInterfaceFBType type, String name, Path prefix) {
		super(name, prefix, "CFunctionBlock")
		this.type = type
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
		
		  «type.interfaceList.inputVars.generateAccessors("getDI")»
		  «type.interfaceList.outputVars.generateAccessors("getDO")»
		  «(type.interfaceList.sockets + type.interfaceList.plugs).toList.generateAccessors»
		
		  FORTE_FB_DATA_ARRAY(«type.interfaceList.eventOutputs.size», «type.interfaceList.inputVars.size», «type.interfaceList.outputVars.size», «type.interfaceList.sockets.size + type.interfaceList.plugs.size»);
		
		  void executeEvent(int pa_nEIID);
		
		public:
		   «FBClassName»(const CStringDictionary::TStringId pa_nInstanceNameId, CResource *pa_poSrcRes) :
		       «baseClass»( pa_poSrcRes, &scm_stFBInterfaceSpec, pa_nInstanceNameId, m_anFBConnData, m_anFBVarsData) {
		   };
		
		  virtual ~«FBClassName»() = default;
		};
		
		«generateIncludeGuardEnd»
		
	'''

	override protected generateHeaderIncludes() '''
		#include "funcbloc.h"
		«super.generateHeaderIncludes»
	'''
}
