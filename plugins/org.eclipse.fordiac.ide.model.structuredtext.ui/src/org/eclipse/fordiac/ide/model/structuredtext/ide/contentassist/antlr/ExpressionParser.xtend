/** 
 * Copyright (c) 2016 fortiss GmbH.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * Contributors:
 * Martin Jobst - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.model.structuredtext.ide.contentassist.antlr

import org.antlr.runtime.RecognitionException
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser
import org.eclipse.fordiac.ide.model.structuredtext.ide.contentassist.antlr.internal.InternalStructuredTextParser

class ExpressionParser extends StructuredTextParser {
	override getFollowElements(AbstractInternalContentAssistParser parser) {
		try {
			var typedParser = (parser as InternalStructuredTextParser)
			typedParser.entryRuleExpression()
			return typedParser.getFollowElements()
		} catch (RecognitionException ex) {
			throw new RuntimeException(ex)
		}

	}
}
