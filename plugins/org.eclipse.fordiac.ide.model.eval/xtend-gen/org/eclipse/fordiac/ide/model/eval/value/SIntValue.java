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

import org.eclipse.fordiac.ide.model.data.SintType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;

@SuppressWarnings("all")
public class SIntValue implements AnySignedValue, Comparable<SIntValue> {
  private final byte value;
  
  public static final SIntValue DEFAULT = new SIntValue(((byte) 0));
  
  private SIntValue(final byte value) {
    this.value = value;
  }
  
  public static SIntValue toSIntValue(final byte value) {
    return new SIntValue(value);
  }
  
  public static SIntValue toSIntValue(final Number value) {
    byte _byteValue = value.byteValue();
    return new SIntValue(_byteValue);
  }
  
  public static SIntValue toSIntValue(final String value) {
    byte _parseByte = Byte.parseByte(value);
    return new SIntValue(_parseByte);
  }
  
  public static SIntValue toSIntValue(final AnyNumValue value) {
    return SIntValue.toSIntValue(value.byteValue());
  }
  
  @Override
  public SintType getType() {
    return IecTypes.ElementaryTypes.SINT;
  }
  
  @Override
  public byte byteValue() {
    return this.value;
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
  public int compareTo(final SIntValue o) {
    return Byte.compare(this.value, o.value);
  }
  
  @Override
  public boolean equals(final Object obj) {
    boolean _xifexpression = false;
    if ((obj instanceof SIntValue)) {
      _xifexpression = (this.value == ((SIntValue)obj).value);
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
    return Byte.toString(this.value);
  }
}
