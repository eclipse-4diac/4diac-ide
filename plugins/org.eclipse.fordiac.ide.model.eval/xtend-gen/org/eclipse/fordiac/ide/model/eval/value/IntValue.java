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

import org.eclipse.fordiac.ide.model.data.IntType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;

@SuppressWarnings("all")
public class IntValue implements AnySignedValue, Comparable<IntValue> {
  private final short value;
  
  public static final IntValue DEFAULT = new IntValue(((short) 0));
  
  private IntValue(final short value) {
    this.value = value;
  }
  
  public static IntValue toIntValue(final short value) {
    return new IntValue(value);
  }
  
  public static IntValue toIntValue(final Number value) {
    short _shortValue = value.shortValue();
    return new IntValue(_shortValue);
  }
  
  public static IntValue toIntValue(final String value) {
    short _parseShort = Short.parseShort(value);
    return new IntValue(_parseShort);
  }
  
  public static IntValue toIntValue(final AnyNumValue value) {
    return IntValue.toIntValue(value.shortValue());
  }
  
  @Override
  public IntType getType() {
    return IecTypes.ElementaryTypes.INT;
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
    return this.value;
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
  public int compareTo(final IntValue o) {
    return Short.compare(this.value, o.value);
  }
  
  @Override
  public boolean equals(final Object obj) {
    boolean _xifexpression = false;
    if ((obj instanceof IntValue)) {
      _xifexpression = (this.value == ((IntValue)obj).value);
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
    return Short.toString(this.value);
  }
}
