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
package org.eclipse.fordiac.ide.model.eval.value;

import org.eclipse.fordiac.ide.model.data.BoolType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;

@SuppressWarnings("all")
public class BoolValue implements AnyBitValue {
  private final boolean value;
  
  public static final BoolValue DEFAULT = new BoolValue(false);
  
  private BoolValue(final boolean value) {
    this.value = value;
  }
  
  public static BoolValue toBoolValue(final boolean value) {
    return new BoolValue(value);
  }
  
  public static BoolValue toBoolValue(final String value) {
    boolean _parseBoolean = Boolean.parseBoolean(value);
    return new BoolValue(_parseBoolean);
  }
  
  @Override
  public BoolType getType() {
    return IecTypes.ElementaryTypes.BOOL;
  }
  
  public boolean boolValue() {
    return this.value;
  }
  
  @Override
  public boolean equals(final Object obj) {
    boolean _xifexpression = false;
    if ((obj instanceof BoolValue)) {
      _xifexpression = (this.value == ((BoolValue)obj).value);
    } else {
      _xifexpression = false;
    }
    return _xifexpression;
  }
  
  @Override
  public int hashCode() {
    return Boolean.hashCode(this.value);
  }
  
  @Override
  public String toString() {
    return Boolean.toString(this.value);
  }
}
