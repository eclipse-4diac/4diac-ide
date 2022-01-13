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

import org.eclipse.fordiac.ide.model.data.LrealType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;

@SuppressWarnings("all")
public class LRealValue implements AnyRealValue, Comparable<LRealValue> {
  private final double value;
  
  public static final LRealValue DEFAULT = new LRealValue(0.0);
  
  private LRealValue(final double value) {
    this.value = value;
  }
  
  public static LRealValue toLRealValue(final double value) {
    return new LRealValue(value);
  }
  
  public static LRealValue toLRealValue(final Number value) {
    double _doubleValue = value.doubleValue();
    return new LRealValue(_doubleValue);
  }
  
  public static LRealValue toLRealValue(final String value) {
    double _parseDouble = Double.parseDouble(value);
    return new LRealValue(_parseDouble);
  }
  
  public static LRealValue toLRealValue(final AnyNumValue value) {
    return LRealValue.toLRealValue(value.doubleValue());
  }
  
  @Override
  public LrealType getType() {
    return IecTypes.ElementaryTypes.LREAL;
  }
  
  @Override
  public byte byteValue() {
    return ((byte) this.value);
  }
  
  @Override
  public short shortValue() {
    return ((short) this.value);
  }
  
  @Override
  public int intValue() {
    return ((int) this.value);
  }
  
  @Override
  public long longValue() {
    return ((long) this.value);
  }
  
  @Override
  public double doubleValue() {
    return this.value;
  }
  
  @Override
  public float floatValue() {
    return ((float) this.value);
  }
  
  @Override
  public int compareTo(final LRealValue o) {
    return Double.compare(this.value, o.value);
  }
  
  @Override
  public boolean equals(final Object obj) {
    boolean _xifexpression = false;
    if ((obj instanceof LRealValue)) {
      _xifexpression = (this.value == ((LRealValue)obj).value);
    } else {
      _xifexpression = false;
    }
    return _xifexpression;
  }
  
  @Override
  public int hashCode() {
    return Double.hashCode(this.value);
  }
  
  @Override
  public String toString() {
    return Double.toString(this.value);
  }
}
