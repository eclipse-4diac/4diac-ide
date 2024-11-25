/*******************************************************************************
 * Copyright (c) 2019 fortiss GmbH
 *               2020 Johannes Kepler University
 *               2020 TU Wien/ACIN
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
 *   Alois Zoitl
 *     - Add internal var generation
 *   Martin Melik Merkumians - adds generation of initial value assignment
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng.simple

import java.nio.file.Path
import org.eclipse.fordiac.ide.export.forte_ng.base.BaseFBHeaderTemplate
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType
import org.eclipse.fordiac.ide.model.libraryElement.SimpleECState

class SimpleFBHeaderTemplate extends BaseFBHeaderTemplate<SimpleFBType> {
	new(SimpleFBType type, String name, Path prefix) {
		super(type, name, prefix, "CSimpleFB")
	}

	override generateClassInclude() '''«generateDependencyInclude("simplefb.h")»'''

	override generateAdditionalDeclarations() '''
		«generateStates»
	'''
	
	def protected generateStates() '''
		«FOR state : type.simpleECStates AFTER '\n'»
			«state.generateState»
		«ENDFOR»
	'''

	def protected CharSequence generateState(SimpleECState state) '''
		void enterState«state.name»(CEventChainExecutionThread *const paECET);
	'''
}
