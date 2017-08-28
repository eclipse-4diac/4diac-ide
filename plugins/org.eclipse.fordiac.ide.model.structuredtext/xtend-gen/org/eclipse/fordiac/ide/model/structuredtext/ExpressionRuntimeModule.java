/**
 * Copyright (c) 2016 fortiss GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * Alois Zoitl - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.model.structuredtext;

import com.google.inject.Binder;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import org.eclipse.fordiac.ide.model.structuredtext.StructuredTextRuntimeModule;
import org.eclipse.fordiac.ide.model.structuredtext.parser.antlr.ExpressionParser;
import org.eclipse.xtext.Constants;
import org.eclipse.xtext.parser.IParser;

@SuppressWarnings("all")
public class ExpressionRuntimeModule extends StructuredTextRuntimeModule {
  @Override
  public void configureFileExtensions(final Binder binder) {
    if (((this.properties == null) || (this.properties.getProperty(Constants.FILE_EXTENSIONS) == null))) {
      AnnotatedBindingBuilder<String> _bind = binder.<String>bind(String.class);
      Named _named = Names.named(Constants.FILE_EXTENSIONS);
      LinkedBindingBuilder<String> _annotatedWith = _bind.annotatedWith(_named);
      _annotatedWith.toInstance("expr");
    }
  }
  
  @Override
  public Class<? extends IParser> bindIParser() {
    return ExpressionParser.class;
  }
}
