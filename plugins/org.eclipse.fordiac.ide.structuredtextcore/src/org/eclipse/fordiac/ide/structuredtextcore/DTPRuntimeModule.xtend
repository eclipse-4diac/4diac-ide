/*******************************************************************************
 * Copyright (c) 2022, 2023 Primetals Technologies GmbH
 *                          Martin Erich Jobst
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
 *   Martin Erich Jobst
 *       - add qualified name bindings
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore

import org.eclipse.fordiac.ide.structuredtextcore.naming.STCoreQualifiedNameConverter
import org.eclipse.fordiac.ide.structuredtextcore.naming.STCoreQualifiedNameProvider
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.resource.generic.AbstractGenericResourceRuntimeModule

class DTPRuntimeModule extends AbstractGenericResourceRuntimeModule {
	
	override protected getFileExtensions() {
		'dtp'
	}
	
	override protected getLanguageName() {
		DTPLanguageConstants.ORG_ECLIPSE_FORDIAC_IDE_MODEL_DATATYPE_DTP
	}
	
	override bindIQualifiedNameProvider() {
		STCoreQualifiedNameProvider
	}

	def Class<? extends IQualifiedNameConverter> bindIQualifiedNameConverter() {
		return STCoreQualifiedNameConverter
	}
}