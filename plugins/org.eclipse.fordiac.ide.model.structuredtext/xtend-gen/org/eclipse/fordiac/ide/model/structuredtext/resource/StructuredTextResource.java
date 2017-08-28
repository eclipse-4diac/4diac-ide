/**
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
 */
package org.eclipse.fordiac.ide.model.structuredtext.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Map;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.IParser;
import org.eclipse.xtext.resource.XtextResource;

@SuppressWarnings("all")
public class StructuredTextResource extends LazyLinkingResource {
  public final static String OPTION_PARSER_RULE = "PARSER_RULE";
  
  @Override
  protected void doLoad(final InputStream inputStream, final Map<?, ?> options) throws IOException {
    this.setEncodingFromOptions(options);
    ParserRule _xifexpression = null;
    if ((options != null)) {
      Object _get = options.get(StructuredTextResource.OPTION_PARSER_RULE);
      _xifexpression = ((ParserRule) _get);
    } else {
      _xifexpression = null;
    }
    ParserRule rule = _xifexpression;
    IParseResult result = null;
    if ((rule != null)) {
      IParser _parser = this.getParser();
      Reader _createReader = this.createReader(inputStream);
      IParseResult _parse = _parser.parse(rule, _createReader);
      result = _parse;
    } else {
      IParser _parser_1 = this.getParser();
      Reader _createReader_1 = this.createReader(inputStream);
      IParseResult _parse_1 = _parser_1.parse(_createReader_1);
      result = _parse_1;
    }
    IParseResult _parseResult = this.getParseResult();
    this.updateInternalState(_parseResult, result);
    if (((options != null) && Boolean.TRUE.equals(options.get(XtextResource.OPTION_RESOLVE_ALL)))) {
      EcoreUtil.resolveAll(this);
    }
  }
}
