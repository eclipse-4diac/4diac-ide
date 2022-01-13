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

import org.eclipse.fordiac.ide.model.data.UsintType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;

@SuppressWarnings("all")
public class USIntValue implements AnyUnsignedValue, Comparable<USIntValue> {
  private final byte value;
  
  public static final USIntValue DEFAULT = new USIntValue(((byte) 0));
  
  private USIntValue(final byte value) {
    this.value = value;
  }
  
  public static USIntValue toUSIntValue(final byte value) {
    return new USIntValue(value);
  }
  
  public static USIntValue toUSIntValue(final Number value) {
    byte _byteValue = value.byteValue();
    return new USIntValue(_byteValue);
  }
  
  public static USIntValue toUSIntValue(final String value) {
    int _parseUnsignedInt = Integer.parseUnsignedInt(value);
    return new USIntValue(((byte) _parseUnsignedInt));
  }
  
  public static USIntValue toUSIntValue(final AnyNumValue value) {
    return USIntValue.toUSIntValue(value.byteValue());
  }
  
  @Override
  public UsintType getType() {
    return IecTypes.ElementaryTypes.USINT;
  }
  
  @Override
  public byte byteValue() {
    return this.value;
  }
  
  @Override
  public short shortValue() {
    int _unsignedInt = Byte.toUnsignedInt(this.value);
    return ((short) _unsignedInt);
  }
  
  @Override
  public int intValue() {
    return Byte.toUnsignedInt(this.value);
  }
  
  @Override
  public long longValue() {
    return Byte.toUnsignedLong(this.value);
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
  public int compareTo(final USIntValue o) {
    return Byte.compareUnsigned(this.value, o.value);
  }
  
  @Override
  public boolean equals(final Object obj) {
    boolean _xifexpression = false;
    if ((obj instanceof USIntValue)) {
      _xifexpression = (this.value == ((USIntValue)obj).value);
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
