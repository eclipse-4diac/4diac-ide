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
package org.eclipse.fordiac.ide.model.structuredtext.parser.antlr;

import org.eclipse.fordiac.ide.model.structuredtext.parser.antlr.StructuredTextParser;

@SuppressWarnings("all")
public class ExpressionParser extends StructuredTextParser {
  @Override
  protected String getDefaultRuleName() {
    return "Expression";
  }
}
