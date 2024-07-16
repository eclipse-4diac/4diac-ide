/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
import org.eclipse.fordiac.ide.export.ExportException
import org.eclipse.fordiac.ide.model.libraryElement.STFunctionBody
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

import static extension org.eclipse.fordiac.ide.structuredtextfunctioneditor.util.STFunctionParseUtil.*
import org.eclipse.fordiac.ide.export.language.ILanguageSupport
import org.eclipse.xtend.lib.annotations.Accessors
import java.util.List

@FinalFieldsConstructor
class STFunctionBodySupport implements ILanguageSupport {
	final STFunctionBody body

	@Accessors final List<String> errors = newArrayList
	@Accessors final List<String> warnings = newArrayList
	@Accessors final List<String> infos = newArrayList

	STFunctionSource parseResult
	STFunctionSupport support

	override prepare() {
		if (parseResult === null && errors.empty) {
			parseResult = body.parse(errors, warnings, infos)
			if (parseResult !== null) {
				support = new STFunctionSupport(parseResult)
			}
		}
		return parseResult !== null
	}

	override generate(Map<?, ?> options) throws ExportException {
		prepare()
		support?.generate(options)
	}

	override getDependencies(Map<?, ?> options) {
		prepare()
		support?.getDependencies(options)
	}
}
