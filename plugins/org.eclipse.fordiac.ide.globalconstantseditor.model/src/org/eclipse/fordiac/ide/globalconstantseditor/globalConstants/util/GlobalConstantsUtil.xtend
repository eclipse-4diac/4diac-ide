/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Melik Merkumians - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.util

import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STVarGlobalDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration

final class GlobalConstantsUtil {
	private new() {
	}

	def static String getSourceName(STVarDeclaration varDeclaration) {
		val source = varDeclaration.eContainer
		if(source instanceof STVarGlobalDeclarationBlock) source.sourceName else null
	}

	def static String getSourceName(STVarGlobalDeclarationBlock source) {
		source.eResource?.URI?.lastSegment?.replaceAll("\\.[^.]+$", "")
	}
}
