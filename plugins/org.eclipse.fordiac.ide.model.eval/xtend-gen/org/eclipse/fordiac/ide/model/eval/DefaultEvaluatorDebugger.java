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

import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class DefaultEvaluatorDebugger implements EvaluatorDebugger {
  public static final DefaultEvaluatorDebugger INSTANCE = new DefaultEvaluatorDebugger();
  
  @Override
  public void trap(final Object context, final Evaluator eval) {
    try {
      boolean _interrupted = Thread.interrupted();
      if (_interrupted) {
        throw new InterruptedException();
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
