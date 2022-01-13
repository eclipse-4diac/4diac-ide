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

import org.eclipse.fordiac.ide.model.data.RealType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;

@SuppressWarnings("all")
public class RealValue implements AnyRealValue, Comparable<RealValue> {
  private final float value;
  
  public static final RealValue DEFAULT = new RealValue(0.0f);
  
  private RealValue(final float value) {
    this.value = value;
  }
  
  public static RealValue toRealValue(final float value) {
    return new RealValue(value);
  }
  
  public static RealValue toRealValue(final Number value) {
    float _floatValue = value.floatValue();
    return new RealValue(_floatValue);
  }
  
  public static RealValue toRealValue(final String value) {
    float _parseFloat = Float.parseFloat(value);
    return new RealValue(_parseFloat);
  }
  
  public static RealValue toRealValue(final AnyNumValue value) {
    return RealValue.toRealValue(value.floatValue());
  }
  
  @Override
  public RealType getType() {
    return IecTypes.ElementaryTypes.REAL;
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
    return this.value;
  }
  
  @Override
  public int compareTo(final RealValue o) {
    return Float.compare(this.value, o.value);
  }
  
  @Override
  public boolean equals(final Object obj) {
    boolean _xifexpression = false;
    if ((obj instanceof RealValue)) {
      _xifexpression = (this.value == ((RealValue)obj).value);
    } else {
      _xifexpression = false;
    }
    return _xifexpression;
  }
  
  @Override
  public int hashCode() {
    return Float.hashCode(this.value);
  }
  
  @Override
  public String toString() {
    return Float.toString(this.value);
  }
}
