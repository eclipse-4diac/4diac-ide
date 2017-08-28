/** 
 * Copyright (c) 2016 fortiss GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
