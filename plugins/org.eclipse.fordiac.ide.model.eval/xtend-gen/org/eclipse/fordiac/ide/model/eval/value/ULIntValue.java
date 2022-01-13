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

import org.eclipse.fordiac.ide.model.data.UlintType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;

@SuppressWarnings("all")
public class ULIntValue implements AnyUnsignedValue, Comparable<ULIntValue> {
  private final long value;
  
  public static final ULIntValue DEFAULT = new ULIntValue(0);
  
  private ULIntValue(final long value) {
    this.value = value;
  }
  
  public static ULIntValue toULIntValue(final long value) {
    return new ULIntValue(value);
  }
  
  public static ULIntValue toULIntValue(final Number value) {
    long _longValue = value.longValue();
    return new ULIntValue(_longValue);
  }
  
  public static ULIntValue toULIntValue(final String value) {
    long _parseUnsignedLong = Long.parseUnsignedLong(value);
    return new ULIntValue(_parseUnsignedLong);
  }
  
  public static ULIntValue toULIntValue(final AnyNumValue value) {
    return ULIntValue.toULIntValue(value.longValue());
  }
  
  @Override
  public UlintType getType() {
    return IecTypes.ElementaryTypes.ULINT;
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
    return this.value;
  }
  
  @Override
  public double doubleValue() {
    return this.value;
  }
  
  @Override
  public float floatValue() {
    return this.value;
  }
  
  @Override
  public int compareTo(final ULIntValue o) {
    return Long.compareUnsigned(this.value, o.value);
  }
  
  @Override
  public boolean equals(final Object obj) {
    boolean _xifexpression = false;
    if ((obj instanceof ULIntValue)) {
      _xifexpression = (this.value == ((ULIntValue)obj).value);
    } else {
      _xifexpression = false;
    }
    return _xifexpression;
  }
  
  @Override
  public int hashCode() {
    return Long.hashCode(this.value);
  }
  
  @Override
  public String toString() {
    return Long.toUnsignedString(this.value);
  }
}
