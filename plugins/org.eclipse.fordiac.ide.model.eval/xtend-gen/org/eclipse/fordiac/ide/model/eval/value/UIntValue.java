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

import org.eclipse.fordiac.ide.model.data.UintType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;

@SuppressWarnings("all")
public class UIntValue implements AnyUnsignedValue, Comparable<UIntValue> {
  private final short value;
  
  public static final UIntValue DEFAULT = new UIntValue(((short) 0));
  
  private UIntValue(final short value) {
    this.value = value;
  }
  
  public static UIntValue toUIntValue(final short value) {
    return new UIntValue(value);
  }
  
  public static UIntValue toUIntValue(final Number value) {
    short _shortValue = value.shortValue();
    return new UIntValue(_shortValue);
  }
  
  public static UIntValue toUIntValue(final String value) {
    int _parseUnsignedInt = Integer.parseUnsignedInt(value);
    return new UIntValue(((short) _parseUnsignedInt));
  }
  
  public static UIntValue toUIntValue(final AnyNumValue value) {
    return UIntValue.toUIntValue(value.shortValue());
  }
  
  @Override
  public UintType getType() {
    return IecTypes.ElementaryTypes.UINT;
  }
  
  @Override
  public byte byteValue() {
    return ((byte) this.value);
  }
  
  @Override
  public short shortValue() {
    return this.value;
  }
  
  @Override
  public int intValue() {
    return Short.toUnsignedInt(this.value);
  }
  
  @Override
  public long longValue() {
    return Short.toUnsignedLong(this.value);
  }
  
  @Override
  public double doubleValue() {
    return this.intValue();
  }
  
  @Override
  public float floatValue() {
    return this.intValue();
  }
  
  @Override
  public int compareTo(final UIntValue o) {
    return Short.compareUnsigned(this.value, o.value);
  }
  
  @Override
  public boolean equals(final Object obj) {
    boolean _xifexpression = false;
    if ((obj instanceof UIntValue)) {
      _xifexpression = (this.value == ((UIntValue)obj).value);
    } else {
      _xifexpression = false;
    }
    return _xifexpression;
  }
  
  @Override
  public int hashCode() {
    return this.value;
  }
  
  @Override
  public String toString() {
    return Integer.toUnsignedString(this.intValue());
  }
}
