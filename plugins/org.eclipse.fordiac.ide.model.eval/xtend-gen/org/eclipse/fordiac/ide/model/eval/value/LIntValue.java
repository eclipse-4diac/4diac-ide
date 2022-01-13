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

import org.eclipse.fordiac.ide.model.data.LintType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;

@SuppressWarnings("all")
public class LIntValue implements AnySignedValue, Comparable<LIntValue> {
  private final long value;
  
  public static final LIntValue DEFAULT = new LIntValue(0);
  
  private LIntValue(final long value) {
    this.value = value;
  }
  
  public static LIntValue toLIntValue(final long value) {
    return new LIntValue(value);
  }
  
  public static LIntValue toLIntValue(final Number value) {
    long _longValue = value.longValue();
    return new LIntValue(_longValue);
  }
  
  public static LIntValue toLIntValue(final String value) {
    long _parseLong = Long.parseLong(value);
    return new LIntValue(_parseLong);
  }
  
  public static LIntValue toLIntValue(final AnyNumValue value) {
    return LIntValue.toLIntValue(value.longValue());
  }
  
  @Override
  public LintType getType() {
    return IecTypes.ElementaryTypes.LINT;
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
  public int compareTo(final LIntValue o) {
    return Long.compare(this.value, o.value);
  }
  
  @Override
  public boolean equals(final Object obj) {
    boolean _xifexpression = false;
    if ((obj instanceof LIntValue)) {
      _xifexpression = (this.value == ((LIntValue)obj).value);
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
    return Long.toString(this.value);
  }
}
