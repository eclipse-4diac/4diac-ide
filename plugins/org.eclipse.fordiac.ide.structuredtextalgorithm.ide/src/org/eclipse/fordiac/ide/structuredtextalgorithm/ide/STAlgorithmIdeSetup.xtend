/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextalgorithm.ide

import com.google.inject.Guice
import org.eclipse.fordiac.ide.structuredtextalgorithm.STAlgorithmRuntimeModule
import org.eclipse.fordiac.ide.structuredtextalgorithm.STAlgorithmStandaloneSetup
import org.eclipse.xtext.util.Modules2

/**
 * Initialization support for running Xtext languages as language servers.
 */
class STAlgorithmIdeSetup extends STAlgorithmStandaloneSetup {

	override createInjector() {
		Guice.createInjector(Modules2.mixin(new STAlgorithmRuntimeModule, new STAlgorithmIdeModule))
	}
	
}
