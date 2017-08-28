/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Martin Jobst 
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.structuredtext.resource

import java.io.IOException
import java.io.InputStream
import java.util.Map
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtext.ParserRule
import org.eclipse.xtext.linking.lazy.LazyLinkingResource
import org.eclipse.xtext.parser.IParseResult

class StructuredTextResource extends LazyLinkingResource {
	public static final String OPTION_PARSER_RULE = "PARSER_RULE"

	override protected void doLoad(InputStream inputStream, Map<?, ?> options) throws IOException {
		setEncodingFromOptions(options)
		var ParserRule rule = if(options !== null) (options.get(OPTION_PARSER_RULE) as ParserRule) else null
		var IParseResult result
		if (rule !== null) {
			result = getParser().parse(rule, createReader(inputStream))
		} else {
			result = getParser().parse(createReader(inputStream))
		}
		updateInternalState(this.getParseResult(), result)
		if (options !== null && Boolean.TRUE.equals(options.get(OPTION_RESOLVE_ALL))) {
			EcoreUtil.resolveAll(this)
		}
	}
}
