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
import java.util.concurrent.ConcurrentHashMap;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class EvaluatorThreadGroup extends ThreadGroup {
  @Accessors(AccessorType.PUBLIC_GETTER)
  private EvaluatorDebugger debugger = DefaultEvaluatorDebugger.INSTANCE;
  
  @Accessors
  private final Set<EvaluatorMonitor> monitorSet = ConcurrentHashMap.<EvaluatorMonitor>newKeySet();
  
  public EvaluatorThreadGroup(final String name) {
    super(name);
    this.setDaemon(true);
  }
  
  public synchronized void attachDebugger(final EvaluatorDebugger debugger) {
    if (((debugger != this.debugger) && (this.debugger != DefaultEvaluatorDebugger.INSTANCE))) {
      throw new IllegalStateException("Another debugger is currently attached");
    }
    this.debugger = debugger;
  }
  
  public synchronized void detachDebugger(final EvaluatorDebugger debugger) {
    if ((this.debugger != debugger)) {
      throw new IllegalStateException("Another debugger is currently attached");
    }
    this.debugger = DefaultEvaluatorDebugger.INSTANCE;
  }
  
  public void addMonitor(final EvaluatorMonitor monitor) {
    this.monitorSet.add(monitor);
  }
  
  public void removeMonitor(final EvaluatorMonitor monitor) {
    this.monitorSet.remove(monitor);
  }
  
  @Pure
  public EvaluatorDebugger getDebugger() {
    return this.debugger;
  }
  
  @Pure
  public Set<EvaluatorMonitor> getMonitorSet() {
    return this.monitorSet;
  }
}
