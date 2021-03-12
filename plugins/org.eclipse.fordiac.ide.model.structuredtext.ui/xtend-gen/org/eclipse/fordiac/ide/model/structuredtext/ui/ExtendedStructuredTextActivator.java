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
package org.eclipse.fordiac.ide.model.structuredtext.ui;

import org.eclipse.fordiac.ide.model.structuredtext.ExpressionRuntimeModule;
import org.eclipse.fordiac.ide.model.structuredtext.ui.ExpressionUiModule;
import org.eclipse.fordiac.ide.model.structuredtext.ui.internal.StructuredtextActivator;
import org.osgi.framework.BundleContext;

/**
 * An extended activator which extends the standard generated xtext activator for
 * expressions as sublanguage
 */
@SuppressWarnings("all")
public class ExtendedStructuredTextActivator extends StructuredtextActivator {
  public static final String ORG_FORDIAC_IDE_MODEL_STRUCTUREDTEXT_EXPRESSION = "org.eclipse.fordiac.ide.model.structuredtext.Expression";
  
  private static StructuredtextActivator INSTANCE;
  
  @Override
  public void start(final BundleContext context) throws Exception {
    super.start(context);
    ExtendedStructuredTextActivator.INSTANCE = this;
  }
  
  @Override
  public void stop(final BundleContext context) throws Exception {
    ExtendedStructuredTextActivator.INSTANCE = null;
    super.stop(context);
  }
  
  public static StructuredtextActivator getInstance() {
    return ExtendedStructuredTextActivator.INSTANCE;
  }
  
  @Override
  public com.google.inject.Module getRuntimeModule(final String grammar) {
    boolean _equals = ExtendedStructuredTextActivator.ORG_FORDIAC_IDE_MODEL_STRUCTUREDTEXT_EXPRESSION.equals(grammar);
    if (_equals) {
      return new ExpressionRuntimeModule();
    }
    return super.getRuntimeModule(grammar);
  }
  
  @Override
  public com.google.inject.Module getUiModule(final String grammar) {
    boolean _equals = ExtendedStructuredTextActivator.ORG_FORDIAC_IDE_MODEL_STRUCTUREDTEXT_EXPRESSION.equals(grammar);
    if (_equals) {
      return new ExpressionUiModule(this);
    }
    return super.getUiModule(grammar);
  }
}
