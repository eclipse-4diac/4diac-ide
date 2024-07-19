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
package org.eclipse.fordiac.ide.export.forte_ng.algorithm

import java.util.Map
import org.eclipse.fordiac.ide.export.language.ILanguageSupportFactory
import org.eclipse.fordiac.ide.model.libraryElement.OtherAlgorithm

class OtherAlgorithmSupportFactory implements ILanguageSupportFactory {
	override createLanguageSupport(Object source, Map<?,?> options) {
		if (source instanceof OtherAlgorithm) {
			new OtherAlgorithmSupport(source)
		}
	}

	def static void register() {
		val factory = new OtherAlgorithmSupportFactory
		ILanguageSupportFactory.Registry.INSTANCE.registerFactory("forte_ng", OtherAlgorithm, factory)
	}
}
