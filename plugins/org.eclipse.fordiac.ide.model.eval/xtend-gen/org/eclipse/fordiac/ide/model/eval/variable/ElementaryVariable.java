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
package org.eclipse.fordiac.ide.model.eval.variable;

import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.value.ValueOperations;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public final class ElementaryVariable extends AbstractVariable {
  @Accessors
  private Value value;
  
  public ElementaryVariable(final VarDeclaration declaration) {
    this(declaration, ((Value) null));
  }
  
  public ElementaryVariable(final VarDeclaration declaration, final String value) {
    this(declaration, ValueOperations.parseValue(value, declaration.getType()));
  }
  
  public ElementaryVariable(final VarDeclaration declaration, final Value value) {
    super(declaration);
    this.setValue(value);
  }
  
  @Override
  public void setValue(final Value value) {
    Value _elvis = null;
    Value _castValue = ValueOperations.castValue(value, this.getDeclaration().getType());
    if (_castValue != null) {
      _elvis = _castValue;
    } else {
      Value _defaultValue = ValueOperations.defaultValue(this.getDeclaration().getType());
      _elvis = _defaultValue;
    }
    this.value = _elvis;
  }
  
  @Override
  public void setValue(final String value) {
    this.setValue(ValueOperations.parseValue(value, this.getDeclaration().getType()));
  }
  
  @Override
  public boolean validateValue(final String value) {
    boolean _xtrycatchfinallyexpression = false;
    try {
      boolean _xblockexpression = false;
      {
        ValueOperations.parseValue(value, this.getDeclaration().getType());
        _xblockexpression = true;
      }
      _xtrycatchfinallyexpression = _xblockexpression;
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        _xtrycatchfinallyexpression = false;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return _xtrycatchfinallyexpression;
  }
  
  @Pure
  @Override
  public Value getValue() {
    return this.value;
  }
}
