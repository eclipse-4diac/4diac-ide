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
 *     - adopt new ST language support
 *   Alois Zoitl
 *     - Add internal var generation
 *   Ernst Blecha
 *     - Add array-like bitaccess
 *   Martin Melik Merkumians - adds generation of initial value assignment
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng.basic

import java.nio.file.Path
import org.eclipse.fordiac.ide.export.forte_ng.base.BaseFBHeaderTemplate
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType
import org.eclipse.fordiac.ide.model.libraryElement.ECState

class BasicFBHeaderTemplate extends BaseFBHeaderTemplate<BasicFBType> {
	new(BasicFBType type, String name, Path prefix) {
		super(type, name, prefix, "CBasicFB")
	}

	override generateAdditionalDeclarations() '''
		«generateStates»
	'''
	
	override generateClassInclude() '''«generateDependencyInclude("basicfb.h")»'''

	def protected generateStates() '''
		«FOR state : type.ECC.ECState AFTER '\n'»
			static const TForteInt16 scmState«state.name» = «type.ECC.ECState.indexOf(state)»;
		«ENDFOR»
		«FOR state : type.ECC.ECState AFTER '\n'»
			«state.generateState»
		«ENDFOR»
	'''

	def protected CharSequence generateState(ECState state) '''
		void enterState«state.name»(CEventChainExecutionThread *const paECET);
	'''
}
