/**
 * Copyright (c) 2016 fortiss GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * Martin Jobst - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.model.structuredtext.ide.contentassist.antlr;

import java.util.Collection;
import org.antlr.runtime.RecognitionException;
import org.eclipse.fordiac.ide.model.structuredtext.ide.contentassist.antlr.StructuredTextParser;
import org.eclipse.fordiac.ide.model.structuredtext.ide.contentassist.antlr.internal.InternalStructuredTextParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.FollowElement;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class ExpressionParser extends StructuredTextParser {
  @Override
  public Collection<FollowElement> getFollowElements(final AbstractInternalContentAssistParser parser) {
    try {
      InternalStructuredTextParser typedParser = ((InternalStructuredTextParser) parser);
      typedParser.entryRuleExpression();
      return typedParser.getFollowElements();
    } catch (final Throwable _t) {
      if (_t instanceof RecognitionException) {
        final RecognitionException ex = (RecognitionException)_t;
        throw new RuntimeException(ex);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
}
