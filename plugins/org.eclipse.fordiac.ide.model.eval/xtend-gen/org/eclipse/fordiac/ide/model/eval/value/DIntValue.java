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

import org.eclipse.fordiac.ide.model.data.DintType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;

@SuppressWarnings("all")
public final class DIntValue implements AnySignedValue, Comparable<DIntValue> {
  private final int value;
  
  public static final DIntValue DEFAULT = new DIntValue(0);
  
  private DIntValue(final int value) {
    this.value = value;
  }
  
  public static DIntValue toDIntValue(final int value) {
    return new DIntValue(value);
  }
  
  public static DIntValue toDIntValue(final Number value) {
    int _intValue = value.intValue();
    return new DIntValue(_intValue);
  }
  
  public static DIntValue toDIntValue(final String value) {
    int _parseInt = Integer.parseInt(value);
    return new DIntValue(_parseInt);
  }
  
  public static DIntValue toDIntValue(final AnyNumValue value) {
    return DIntValue.toDIntValue(value.intValue());
  }
  
  @Override
  public DintType getType() {
    return IecTypes.ElementaryTypes.DINT;
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
  public int compareTo(final DIntValue o) {
    return Integer.compare(this.value, o.value);
  }
  
  @Override
  public boolean equals(final Object obj) {
    boolean _xifexpression = false;
    if ((obj instanceof DIntValue)) {
      _xifexpression = (this.value == ((DIntValue)obj).value);
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
    return Integer.toString(this.value);
  }
}
