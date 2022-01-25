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
package org.eclipse.fordiac.ide.structuredtextcore.ui

import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory
import org.eclipse.fordiac.ide.structuredtextcore.ui.internal.StructuredtextcoreActivatorEx
import org.eclipse.fordiac.ide.structuredtextcore.DTPLanguageConstants

class DTPExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {
	
	override protected getBundle() {
		StructuredtextcoreActivatorEx.instance.bundle
	}
	
	override protected getInjector() {
		StructuredtextcoreActivatorEx.instance.getInjector(DTPLanguageConstants.ORG_ECLIPSE_FORDIAC_IDE_MODEL_DATATYPE_DTP)
	}
	
}