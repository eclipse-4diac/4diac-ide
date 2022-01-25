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
package org.eclipse.fordiac.ide.model.eval.fb;

import com.google.common.collect.Iterables;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Function;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.eval.AbstractEvaluator;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.variable.ElementaryVariable;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.xtend.lib.annotations.Data;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@Data
@SuppressWarnings("all")
public abstract class FBEvaluator<T extends FBType> extends AbstractEvaluator {
  private final T type;
  
  private final Queue<Event> queue;
  
  private final Map<VarDeclaration, Variable> variables;
  
  public FBEvaluator(final T type, final Queue<Event> queue, final Iterable<Variable> variables, final Evaluator parent) {
    super(parent);
    this.type = type;
    this.queue = queue;
    Map<VarDeclaration, Variable> _elvis = null;
    Map<VarDeclaration, Variable> _map = null;
    if (variables!=null) {
      final Function1<Variable, VarDeclaration> _function = (Variable it) -> {
        return it.getDeclaration();
      };
      _map=IterableExtensions.<VarDeclaration, Variable>toMap(variables, _function);
    }
    if (_map != null) {
      _elvis = _map;
    } else {
      HashMap<VarDeclaration, Variable> _newHashMap = CollectionLiterals.<VarDeclaration, Variable>newHashMap();
      _elvis = _newHashMap;
    }
    this.variables = _elvis;
    EList<VarDeclaration> _inputVars = type.getInterfaceList().getInputVars();
    EList<VarDeclaration> _outputVars = type.getInterfaceList().getOutputVars();
    final Consumer<VarDeclaration> _function_1 = (VarDeclaration it) -> {
      final Function<VarDeclaration, Variable> _function_2 = (VarDeclaration it_1) -> {
        return new ElementaryVariable(it_1);
      };
      this.variables.computeIfAbsent(it, _function_2);
    };
    Iterables.<VarDeclaration>concat(_inputVars, _outputVars).forEach(_function_1);
  }
  
  @Override
  public Value evaluate() {
    Object _xblockexpression = null;
    {
      Event _poll = null;
      if (this.queue!=null) {
        _poll=this.queue.poll();
      }
      this.evaluate(_poll);
      _xblockexpression = null;
    }
    return ((Value)_xblockexpression);
  }
  
  public abstract void evaluate(final Event event);
  
  @Override
  public String getName() {
    return this.type.getName();
  }
  
  @Override
  public Object getSourceElement() {
    return this.type;
  }
  
  @Override
  public Collection<Variable> getVariables() {
    return Collections.<Variable>unmodifiableCollection(this.variables.values());
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.type== null) ? 0 : this.type.hashCode());
    result = prime * result + ((this.queue== null) ? 0 : this.queue.hashCode());
    return prime * result + ((this.variables== null) ? 0 : this.variables.hashCode());
  }
  
  @Override
  @Pure
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    FBEvaluator<?> other = (FBEvaluator<?>) obj;
    if (this.type == null) {
      if (other.type != null)
        return false;
    } else if (!this.type.equals(other.type))
      return false;
    if (this.queue == null) {
      if (other.queue != null)
        return false;
    } else if (!this.queue.equals(other.queue))
      return false;
    if (this.variables == null) {
      if (other.variables != null)
        return false;
    } else if (!this.variables.equals(other.variables))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public String toString() {
    return new ToStringBuilder(this)
    	.addAllFields()
    	.toString();
  }
  
  @Pure
  public T getType() {
    return this.type;
  }
  
  @Pure
  public Queue<Event> getQueue() {
    return this.queue;
  }
}
