/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst, Patrick Aigner
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *   Patrick Aigner - adapted for Lua Code generation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_lua.st

import java.util.Map
import org.eclipse.fordiac.ide.export.language.ILanguageSupportFactory
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm
import org.eclipse.fordiac.ide.model.libraryElement.STMethod
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource

class StructuredTextSupportFactory implements ILanguageSupportFactory {

	override createLanguageSupport(Object source, Map<?,?> options) {
		if (source instanceof STAlgorithm) {
			new STAlgorithmSupport(source)
		} else if (source instanceof ECTransition) {
			new ECTransitionSupport(source)
		} else if (source instanceof STMethod) {
			new STMethodSupport(source)
		} else if (source instanceof STFunctionSource) {
			new STFunctionSupport(source)
		}
	}

	def static void register() {
		val factory = new StructuredTextSupportFactory
		ILanguageSupportFactory.Registry.INSTANCE.registerFactory("forte_lua", STAlgorithm, factory)
		ILanguageSupportFactory.Registry.INSTANCE.registerFactory("forte_lua", ECTransition, factory)
		ILanguageSupportFactory.Registry.INSTANCE.registerFactory("forte_lua", STMethod, factory)
		ILanguageSupportFactory.Registry.INSTANCE.registerFactory("forte_lua", STFunctionSource, factory)
	}
}
