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

import org.eclipse.fordiac.ide.model.data.UdintType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;

@SuppressWarnings("all")
public class UDIntValue implements AnyUnsignedValue, Comparable<UDIntValue> {
  private final int value;
  
  public static final UDIntValue DEFAULT = new UDIntValue(0);
  
  private UDIntValue(final int value) {
    this.value = value;
  }
  
  public static UDIntValue toUDIntValue(final int value) {
    return new UDIntValue(value);
  }
  
  public static UDIntValue toUDIntValue(final Number value) {
    int _intValue = value.intValue();
    return new UDIntValue(_intValue);
  }
  
  public static UDIntValue toUDIntValue(final String value) {
    int _parseUnsignedInt = Integer.parseUnsignedInt(value);
    return new UDIntValue(_parseUnsignedInt);
  }
  
  public static UDIntValue toUDIntValue(final AnyNumValue value) {
    return UDIntValue.toUDIntValue(value.intValue());
  }
  
  @Override
  public UdintType getType() {
    return IecTypes.ElementaryTypes.UDINT;
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
    return this.value;
  }
  
  @Override
  public long longValue() {
    return Integer.toUnsignedLong(this.value);
  }
  
  @Override
  public double doubleValue() {
    return this.longValue();
  }
  
  @Override
  public float floatValue() {
    return this.longValue();
  }
  
  @Override
  public int compareTo(final UDIntValue o) {
    return Integer.compareUnsigned(this.value, o.value);
  }
  
  @Override
  public boolean equals(final Object obj) {
    boolean _xifexpression = false;
    if ((obj instanceof UDIntValue)) {
      _xifexpression = (this.value == ((UDIntValue)obj).value);
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
    return Integer.toUnsignedString(this.value);
  }
}
