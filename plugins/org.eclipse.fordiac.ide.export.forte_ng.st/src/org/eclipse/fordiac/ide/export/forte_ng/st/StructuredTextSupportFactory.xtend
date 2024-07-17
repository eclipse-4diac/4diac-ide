/*******************************************************************************
 * Copyright (c) 2022 - 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.export.forte_ng.st

import java.util.Map
import org.eclipse.fordiac.ide.export.language.ILanguageSupportFactory
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STGlobalConstsSource
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition
import org.eclipse.fordiac.ide.model.libraryElement.GlobalConstants
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm
import org.eclipse.fordiac.ide.model.libraryElement.STFunctionBody
import org.eclipse.fordiac.ide.model.libraryElement.STMethod
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource

class StructuredTextSupportFactory implements ILanguageSupportFactory {

	override createLanguageSupport(Object source, Map<?, ?> options) {
		if (source instanceof STAlgorithm) {
			new STAlgorithmSupport(source, options)
		} else if (source instanceof STMethod) {
			new STMethodSupport(source, options)
		} else if (source instanceof STFunctionSource) {
			new STFunctionSupport(source)
		} else if (source instanceof ECTransition) {
			new ECTransitionSupport(source)
		} else if (source instanceof GlobalConstants) {
			new VarGlobalConstantsSupport(source)
		} else if (source instanceof STGlobalConstsSource) {
			new VarGlobalConstantsSupport(source)
		} else if (source instanceof VarDeclaration) {
			new VarDeclarationSupport(source)
		} else if (source instanceof STFunctionBody) {
			new STFunctionBodySupport(source)
		}
	}

	def static void register() {
		val factory = new StructuredTextSupportFactory
		ILanguageSupportFactory.Registry.INSTANCE.registerFactory("forte_ng", STAlgorithm, factory)
		ILanguageSupportFactory.Registry.INSTANCE.registerFactory("forte_ng", ECTransition, factory)
		ILanguageSupportFactory.Registry.INSTANCE.registerFactory("forte_ng", STMethod, factory)
		ILanguageSupportFactory.Registry.INSTANCE.registerFactory("forte_ng", STFunctionSource, factory)
		ILanguageSupportFactory.Registry.INSTANCE.registerFactory("forte_ng", GlobalConstants, factory)
		ILanguageSupportFactory.Registry.INSTANCE.registerFactory("forte_ng", STGlobalConstsSource, factory)
		ILanguageSupportFactory.Registry.INSTANCE.registerFactory("forte_ng", VarDeclaration, factory)
		ILanguageSupportFactory.Registry.INSTANCE.registerFactory("forte_ng", STFunctionBody, factory)
	}
}
