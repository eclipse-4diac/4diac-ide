/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.ui.internal

import org.eclipse.fordiac.ide.structuredtextcore.ui.internal.StructuredtextcoreActivator
import org.eclipse.fordiac.ide.structuredtextcore.DTPRuntimeModule
import org.eclipse.fordiac.ide.structuredtextcore.DTPLanguageConstants
import org.eclipse.fordiac.ide.structuredtextcore.ui.DTPUiModule

class StructuredtextcoreActivatorEx extends StructuredtextcoreActivator {
	
	override protected getRuntimeModule(String grammar) {
		if(grammar.isDTPLanguage) {
			return new DTPRuntimeModule
		}
		super.getRuntimeModule(grammar)
	}
	
	override protected getUiModule(String grammar) {
		if(grammar.isDTPLanguage) {
			return new DTPUiModule(this)
		}
		super.getUiModule(grammar)
	}
	
	private def isDTPLanguage(String grammar) {
		DTPLanguageConstants.ORG_ECLIPSE_FORDIAC_IDE_MODEL_DATATYPE_DTP.equals(grammar)
	}
	
}