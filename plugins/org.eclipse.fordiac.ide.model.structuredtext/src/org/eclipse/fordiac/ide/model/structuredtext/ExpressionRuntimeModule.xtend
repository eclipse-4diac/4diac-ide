/** 
 * Copyright (c) 2016 fortiss GmbH.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * Contributors:
 * Alois Zoitl - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.model.structuredtext

import com.google.inject.Binder
import com.google.inject.name.Names
import org.eclipse.xtext.Constants
import org.eclipse.fordiac.ide.model.structuredtext.parser.antlr.ExpressionParser

class ExpressionRuntimeModule extends StructuredTextRuntimeModule {
	override configureFileExtensions(Binder binder) {
		if(properties === null || properties.getProperty(Constants.FILE_EXTENSIONS) === null) binder.bind(String).
			annotatedWith(Names.named(Constants.FILE_EXTENSIONS)).toInstance("expr")
	}

	override bindIParser() {
		return ExpressionParser
	}
}
