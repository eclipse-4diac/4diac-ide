/**
 * Copyright (c) 2022 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.model.eval;

import java.util.Set;
import java.util.function.Consumer;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Pure;

@FinalFieldsConstructor
@SuppressWarnings("all")
public abstract class AbstractEvaluator implements Evaluator {
  @Accessors
  private final Evaluator parent;
  
  protected <T extends Object> T trap(final T context) {
    try {
      AbstractEvaluator.currentDebugger().trap(context, this);
      return context;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  protected void info(final String message) {
    final Consumer<EvaluatorMonitor> _function = (EvaluatorMonitor it) -> {
      it.info(message);
    };
    AbstractEvaluator.currentMonitors().forEach(_function);
  }
  
  protected void warn(final String message) {
    final Consumer<EvaluatorMonitor> _function = (EvaluatorMonitor it) -> {
      it.warn(message);
    };
    AbstractEvaluator.currentMonitors().forEach(_function);
  }
  
  protected void error(final String message) {
    final Consumer<EvaluatorMonitor> _function = (EvaluatorMonitor it) -> {
      it.error(message);
    };
    AbstractEvaluator.currentMonitors().forEach(_function);
  }
  
  protected void error(final String message, final Throwable t) {
    final Consumer<EvaluatorMonitor> _function = (EvaluatorMonitor it) -> {
      it.error(message, t);
    };
    AbstractEvaluator.currentMonitors().forEach(_function);
  }
  
  public static EvaluatorDebugger currentDebugger() {
    final ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
    if ((threadGroup instanceof EvaluatorThreadGroup)) {
      return ((EvaluatorThreadGroup)threadGroup).getDebugger();
    }
    return DefaultEvaluatorDebugger.INSTANCE;
  }
  
  public static Set<EvaluatorMonitor> currentMonitors() {
    final ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
    if ((threadGroup instanceof EvaluatorThreadGroup)) {
      return ((EvaluatorThreadGroup)threadGroup).getMonitorSet();
    }
    return CollectionLiterals.<EvaluatorMonitor>emptySet();
  }
  
  public AbstractEvaluator(final Evaluator parent) {
    super();
    this.parent = parent;
  }
  
  @Pure
  @Override
  public Evaluator getParent() {
    return this.parent;
  }
}
