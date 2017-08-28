/**
 * Copyright (c) 2016 fortiss GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * Alois Zoitl - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.model.structuredtext.ui;

import com.google.inject.Injector;
import org.eclipse.fordiac.ide.model.structuredtext.ui.ExtendedStructuredTextActivator;
import org.eclipse.fordiac.ide.model.structuredtext.ui.internal.StructuredtextActivator;
import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;

/**
 * Factory for generating the injection needed for Expressions
 */
@SuppressWarnings("all")
public class ExpressionFactory extends AbstractGuiceAwareExecutableExtensionFactory {
  @Override
  protected Bundle getBundle() {
    StructuredtextActivator _instance = ExtendedStructuredTextActivator.getInstance();
    return _instance.getBundle();
  }
  
  @Override
  protected Injector getInjector() {
    StructuredtextActivator _instance = ExtendedStructuredTextActivator.getInstance();
    return _instance.getInjector(
      ExtendedStructuredTextActivator.ORG_FORDIAC_IDE_MODEL_STRUCTUREDTEXT_EXPRESSION);
  }
}
