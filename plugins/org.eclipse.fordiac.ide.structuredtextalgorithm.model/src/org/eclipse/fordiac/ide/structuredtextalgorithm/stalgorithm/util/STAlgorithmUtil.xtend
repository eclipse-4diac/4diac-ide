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
package org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.util

import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSource

final class STAlgorithmUtil {

	private new() {
	}
	
	def static String getSourceName(STAlgorithm algorithm) {
		val source = algorithm.eContainer
		if(source instanceof STAlgorithmSource) source.sourceName else null
	}

	def static String getSourceName(STAlgorithmSource source) {
		source.eResource?.URI?.lastSegment?.replaceAll("\\.[^.]+$", "")
	}
}
