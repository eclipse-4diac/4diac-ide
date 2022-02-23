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
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.util

import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource

final class STFunctionUtil {

	private new() {
	}
	
	def static String getSourceName(STFunction function) {
		val source = function.eContainer
		if(source instanceof STFunctionSource) source.sourceName else null
	}

	def static String getSourceName(STFunctionSource source) {
		source.eResource?.URI?.lastSegment?.replaceAll("\\.[^.]+$", "")
	}
}
