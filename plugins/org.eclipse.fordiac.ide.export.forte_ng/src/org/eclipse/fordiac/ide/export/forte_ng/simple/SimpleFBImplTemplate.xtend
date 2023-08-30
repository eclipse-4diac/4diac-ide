/*******************************************************************************
 * Copyright (c) 2019 fortiss GmbH
 *               2020 Johannes Kepler University
 *               2020 TU Wien/ACIN
 *               2022 Martin Erich Jobst
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
 *     - adopt algorithm support
 *     - adopt multi algorithm support
 *   Alois Zoitl
 *     - Add internal var generation
 *   Ernst Blecha
 *     - Add array-like bitaccess
 *   Martin Melik Merkumians - adds generation of initial value assignment
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng.simple

import java.nio.file.Path
import org.eclipse.fordiac.ide.export.forte_ng.base.BaseFBImplTemplate
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType

class SimpleFBImplTemplate extends BaseFBImplTemplate<SimpleFBType> {
	new(SimpleFBType type, String name, Path prefix) {
		super(type, name, prefix, "CSimpleFB")
	}
	
	override generateExecuteEvent() '''
		void «FBClassName»::executeEvent(const TEventID paEIID, CEventChainExecutionThread *const paECET) {
		  switch(paEIID) {
		    «FOR event : type.interfaceList.eventInputs»
		    	case «event.generateEventID»:
		    	  «FOR alg : type.algorithm.filter[name == event.name]»
		    	  	«alg.generateAlgorithmName»();
		    	  «ENDFOR»
		    	  break;
		    «ENDFOR»
		    default:
		      break;
		  }
		  «FOR event : type.interfaceList.eventOutputs»
		  	«event.generateSendEvent»
		  «ENDFOR»
		}
		
	'''
}
