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

import com.google.common.base.Objects;
import java.util.Arrays;
import org.eclipse.fordiac.ide.model.data.AnyNumType;
import org.eclipse.fordiac.ide.model.data.BoolType;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.DintType;
import org.eclipse.fordiac.ide.model.data.IntType;
import org.eclipse.fordiac.ide.model.data.LintType;
import org.eclipse.fordiac.ide.model.data.LrealType;
import org.eclipse.fordiac.ide.model.data.RealType;
import org.eclipse.fordiac.ide.model.data.SintType;
import org.eclipse.fordiac.ide.model.data.UdintType;
import org.eclipse.fordiac.ide.model.data.UintType;
import org.eclipse.fordiac.ide.model.data.UlintType;
import org.eclipse.fordiac.ide.model.data.UsintType;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public final class ValueOperations {
  private ValueOperations() {
  }
  
  public static Value operator_plus(final Value value) {
    return ValueOperations.abs(value);
  }
  
  public static Value operator_minus(final Value value) {
    return ValueOperations.negate(value);
  }
  
  public static Value operator_plus(final Value first, final Value second) {
    return ValueOperations.add(first, second);
  }
  
  public static Value operator_minus(final Value first, final Value second) {
    return ValueOperations.subtract(first, second);
  }
  
  public static Value operator_multiply(final Value first, final Value second) {
    return ValueOperations.multiply(first, second);
  }
  
  public static Value operator_divide(final Value first, final Value second) {
    return ValueOperations.divideBy(first, second);
  }
  
  public static Value operator_modulo(final Value first, final Value second) {
    return ValueOperations.remainderBy(first, second);
  }
  
  public static Value operator_power(final Value first, final Value second) {
    return ValueOperations.power(first, second);
  }
  
  public static boolean operator_equals(final Value first, final Value second) {
    return ValueOperations.equals(first, second);
  }
  
  public static boolean operator_notEquals(final Value first, final Value second) {
    boolean _equals = ValueOperations.equals(first, second);
    return (!_equals);
  }
  
  public static boolean operator_lessThan(final Value first, final Value second) {
    int _compareTo = ValueOperations.compareTo(first, second);
    return (_compareTo < 0);
  }
  
  public static boolean operator_lessEqualsThan(final Value first, final Value second) {
    int _compareTo = ValueOperations.compareTo(first, second);
    return (_compareTo <= 0);
  }
  
  public static boolean operator_greaterThan(final Value first, final Value second) {
    int _compareTo = ValueOperations.compareTo(first, second);
    return (_compareTo > 0);
  }
  
  public static boolean operator_greaterEqualsThan(final Value first, final Value second) {
    int _compareTo = ValueOperations.compareTo(first, second);
    return (_compareTo >= 0);
  }
  
  public static int operator_spaceship(final Value first, final Value second) {
    return ValueOperations.compareTo(first, second);
  }
  
  public static Value abs(final Value value) {
    Object _switchResult = null;
    boolean _matched = false;
    if (value instanceof LRealValue) {
      _matched=true;
      _switchResult = LRealValue.toLRealValue(Math.abs(((LRealValue)value).doubleValue()));
    }
    if (!_matched) {
      if (value instanceof RealValue) {
        _matched=true;
        _switchResult = RealValue.toRealValue(Math.abs(((RealValue)value).floatValue()));
      }
    }
    if (!_matched) {
      if (value instanceof LIntValue) {
        _matched=true;
        _switchResult = LIntValue.toLIntValue(Math.abs(((LIntValue)value).longValue()));
      }
    }
    if (!_matched) {
      if (value instanceof DIntValue) {
        _matched=true;
        _switchResult = DIntValue.toDIntValue(Math.abs(((DIntValue)value).intValue()));
      }
    }
    if (!_matched) {
      if (value instanceof IntValue) {
        _matched=true;
        int _abs = Math.abs(((IntValue)value).shortValue());
        _switchResult = IntValue.toIntValue(((short) _abs));
      }
    }
    if (!_matched) {
      if (value instanceof SIntValue) {
        _matched=true;
        int _abs = Math.abs(((SIntValue)value).byteValue());
        _switchResult = SIntValue.toSIntValue(((byte) _abs));
      }
    }
    if (!_matched) {
      if (value instanceof ULIntValue) {
        _matched=true;
      }
      if (!_matched) {
        if (value instanceof UDIntValue) {
          _matched=true;
        }
      }
      if (!_matched) {
        if (value instanceof UIntValue) {
          _matched=true;
        }
      }
      if (!_matched) {
        if (value instanceof USIntValue) {
          _matched=true;
        }
      }
      if (_matched) {
        _switchResult = value;
      }
    }
    if (!_matched) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("The absolute operation is not supported for type ");
      String _name = value.getType().getName();
      _builder.append(_name);
      throw new UnsupportedOperationException(_builder.toString());
    }
    return ((AnyNumValue)_switchResult);
  }
  
  public static Value negate(final Value value) {
    Object _switchResult = null;
    boolean _matched = false;
    if (value instanceof LRealValue) {
      _matched=true;
      double _doubleValue = ((LRealValue)value).doubleValue();
      double _minus = (-_doubleValue);
      _switchResult = LRealValue.toLRealValue(_minus);
    }
    if (!_matched) {
      if (value instanceof RealValue) {
        _matched=true;
        float _floatValue = ((RealValue)value).floatValue();
        float _minus = (-_floatValue);
        _switchResult = RealValue.toRealValue(_minus);
      }
    }
    if (!_matched) {
      if (value instanceof LIntValue) {
        _matched=true;
        long _longValue = ((LIntValue)value).longValue();
        long _minus = (-_longValue);
        _switchResult = LIntValue.toLIntValue(_minus);
      }
    }
    if (!_matched) {
      if (value instanceof DIntValue) {
        _matched=true;
        int _intValue = ((DIntValue)value).intValue();
        int _minus = (-_intValue);
        _switchResult = DIntValue.toDIntValue(_minus);
      }
    }
    if (!_matched) {
      if (value instanceof IntValue) {
        _matched=true;
        short _shortValue = ((IntValue)value).shortValue();
        int _minus = (-_shortValue);
        _switchResult = IntValue.toIntValue(((short) _minus));
      }
    }
    if (!_matched) {
      if (value instanceof SIntValue) {
        _matched=true;
        byte _byteValue = ((SIntValue)value).byteValue();
        int _minus = (-_byteValue);
        _switchResult = SIntValue.toSIntValue(((byte) _minus));
      }
    }
    if (!_matched) {
      if (value instanceof ULIntValue) {
        _matched=true;
        long _longValue = ((ULIntValue)value).longValue();
        long _minus = (-_longValue);
        _switchResult = ULIntValue.toULIntValue(_minus);
      }
    }
    if (!_matched) {
      if (value instanceof UDIntValue) {
        _matched=true;
        int _intValue = ((UDIntValue)value).intValue();
        int _minus = (-_intValue);
        _switchResult = UDIntValue.toUDIntValue(_minus);
      }
    }
    if (!_matched) {
      if (value instanceof UIntValue) {
        _matched=true;
        short _shortValue = ((UIntValue)value).shortValue();
        int _minus = (-_shortValue);
        _switchResult = UIntValue.toUIntValue(((short) _minus));
      }
    }
    if (!_matched) {
      if (value instanceof USIntValue) {
        _matched=true;
        byte _byteValue = ((USIntValue)value).byteValue();
        int _minus = (-_byteValue);
        _switchResult = USIntValue.toUSIntValue(((byte) _minus));
      }
    }
    if (!_matched) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("The negate operation is not supported for type ");
      String _name = value.getType().getName();
      _builder.append(_name);
      throw new UnsupportedOperationException(_builder.toString());
    }
    return ((AnyNumValue)_switchResult);
  }
  
  public static Value bitwiseNot(final Value value) {
    Object _switchResult = null;
    boolean _matched = false;
    if (value instanceof LIntValue) {
      _matched=true;
      _switchResult = LIntValue.toLIntValue((~((LIntValue)value).longValue()));
    }
    if (!_matched) {
      if (value instanceof DIntValue) {
        _matched=true;
        _switchResult = DIntValue.toDIntValue((~((DIntValue)value).intValue()));
      }
    }
    if (!_matched) {
      if (value instanceof IntValue) {
        _matched=true;
        int _bitwiseNot = (~((IntValue)value).intValue());
        _switchResult = IntValue.toIntValue(((short) _bitwiseNot));
      }
    }
    if (!_matched) {
      if (value instanceof SIntValue) {
        _matched=true;
        int _bitwiseNot = (~((SIntValue)value).intValue());
        _switchResult = SIntValue.toSIntValue(((byte) _bitwiseNot));
      }
    }
    if (!_matched) {
      if (value instanceof ULIntValue) {
        _matched=true;
        _switchResult = ULIntValue.toULIntValue((~((ULIntValue)value).longValue()));
      }
    }
    if (!_matched) {
      if (value instanceof UDIntValue) {
        _matched=true;
        _switchResult = UDIntValue.toUDIntValue((~((UDIntValue)value).intValue()));
      }
    }
    if (!_matched) {
      if (value instanceof UIntValue) {
        _matched=true;
        int _bitwiseNot = (~((UIntValue)value).intValue());
        _switchResult = UIntValue.toUIntValue(((short) _bitwiseNot));
      }
    }
    if (!_matched) {
      if (value instanceof USIntValue) {
        _matched=true;
        int _bitwiseNot = (~((USIntValue)value).intValue());
        _switchResult = USIntValue.toUSIntValue(((byte) _bitwiseNot));
      }
    }
    if (!_matched) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("The absolute operation is not supported for type ");
      String _name = value.getType().getName();
      _builder.append(_name);
      throw new UnsupportedOperationException(_builder.toString());
    }
    return ((AnyIntValue)_switchResult);
  }
  
  protected static Value _add(final Value first, final Value second) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("The add operation is not supported for types ");
    String _name = first.getType().getName();
    _builder.append(_name);
    _builder.append(" and ");
    String _name_1 = second.getType().getName();
    _builder.append(_name_1);
    throw new UnsupportedOperationException(_builder.toString());
  }
  
  protected static AnyNumValue _add(final AnyNumValue first, final AnyNumValue second) {
    Object _switchResult = null;
    DataType _resultType = ValueOperations.resultType(first.getType(), second.getType());
    boolean _matched = false;
    if (_resultType instanceof LrealType) {
      _matched=true;
      double _doubleValue = first.doubleValue();
      double _doubleValue_1 = second.doubleValue();
      double _plus = (_doubleValue + _doubleValue_1);
      _switchResult = LRealValue.toLRealValue(_plus);
    }
    if (!_matched) {
      if (_resultType instanceof RealType) {
        _matched=true;
        float _floatValue = first.floatValue();
        float _floatValue_1 = second.floatValue();
        float _plus = (_floatValue + _floatValue_1);
        _switchResult = RealValue.toRealValue(_plus);
      }
    }
    if (!_matched) {
      if (_resultType instanceof LintType) {
        _matched=true;
        long _longValue = first.longValue();
        long _longValue_1 = second.longValue();
        long _plus = (_longValue + _longValue_1);
        _switchResult = LIntValue.toLIntValue(_plus);
      }
    }
    if (!_matched) {
      if (_resultType instanceof DintType) {
        _matched=true;
        int _intValue = first.intValue();
        int _intValue_1 = second.intValue();
        int _plus = (_intValue + _intValue_1);
        _switchResult = DIntValue.toDIntValue(_plus);
      }
    }
    if (!_matched) {
      if (_resultType instanceof IntType) {
        _matched=true;
        short _shortValue = first.shortValue();
        short _shortValue_1 = second.shortValue();
        int _plus = (_shortValue + _shortValue_1);
        _switchResult = IntValue.toIntValue(((short) _plus));
      }
    }
    if (!_matched) {
      if (_resultType instanceof SintType) {
        _matched=true;
        byte _byteValue = first.byteValue();
        byte _byteValue_1 = second.byteValue();
        int _plus = (_byteValue + _byteValue_1);
        _switchResult = SIntValue.toSIntValue(((byte) _plus));
      }
    }
    if (!_matched) {
      if (_resultType instanceof UlintType) {
        _matched=true;
        long _longValue = first.longValue();
        long _longValue_1 = second.longValue();
        long _plus = (_longValue + _longValue_1);
        _switchResult = ULIntValue.toULIntValue(_plus);
      }
    }
    if (!_matched) {
      if (_resultType instanceof UdintType) {
        _matched=true;
        int _intValue = first.intValue();
        int _intValue_1 = second.intValue();
        int _plus = (_intValue + _intValue_1);
        _switchResult = UDIntValue.toUDIntValue(_plus);
      }
    }
    if (!_matched) {
      if (_resultType instanceof UintType) {
        _matched=true;
        short _shortValue = first.shortValue();
        short _shortValue_1 = second.shortValue();
        int _plus = (_shortValue + _shortValue_1);
        _switchResult = UIntValue.toUIntValue(((short) _plus));
      }
    }
    if (!_matched) {
      if (_resultType instanceof UsintType) {
        _matched=true;
        byte _byteValue = first.byteValue();
        byte _byteValue_1 = second.byteValue();
        int _plus = (_byteValue + _byteValue_1);
        _switchResult = USIntValue.toUSIntValue(((byte) _plus));
      }
    }
    if (!_matched) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("The add operation is not supported for types ");
      String _name = first.getType().getName();
      _builder.append(_name);
      _builder.append(" and ");
      String _name_1 = second.getType().getName();
      _builder.append(_name_1);
      throw new UnsupportedOperationException(_builder.toString());
    }
    return ((AnyNumValue)_switchResult);
  }
  
  protected static Value _subtract(final Value first, final Value second) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("The subtract operation is not supported for types ");
    String _name = first.getType().getName();
    _builder.append(_name);
    _builder.append(" and ");
    String _name_1 = second.getType().getName();
    _builder.append(_name_1);
    throw new UnsupportedOperationException(_builder.toString());
  }
  
  protected static AnyNumValue _subtract(final AnyNumValue first, final AnyNumValue second) {
    Object _switchResult = null;
    DataType _resultType = ValueOperations.resultType(first.getType(), second.getType());
    boolean _matched = false;
    if (_resultType instanceof LrealType) {
      _matched=true;
      double _doubleValue = first.doubleValue();
      double _doubleValue_1 = second.doubleValue();
      double _minus = (_doubleValue - _doubleValue_1);
      _switchResult = LRealValue.toLRealValue(_minus);
    }
    if (!_matched) {
      if (_resultType instanceof RealType) {
        _matched=true;
        float _floatValue = first.floatValue();
        float _floatValue_1 = second.floatValue();
        float _minus = (_floatValue - _floatValue_1);
        _switchResult = RealValue.toRealValue(_minus);
      }
    }
    if (!_matched) {
      if (_resultType instanceof LintType) {
        _matched=true;
        long _longValue = first.longValue();
        long _longValue_1 = second.longValue();
        long _minus = (_longValue - _longValue_1);
        _switchResult = LIntValue.toLIntValue(_minus);
      }
    }
    if (!_matched) {
      if (_resultType instanceof DintType) {
        _matched=true;
        int _intValue = first.intValue();
        int _intValue_1 = second.intValue();
        int _minus = (_intValue - _intValue_1);
        _switchResult = DIntValue.toDIntValue(_minus);
      }
    }
    if (!_matched) {
      if (_resultType instanceof IntType) {
        _matched=true;
        short _shortValue = first.shortValue();
        short _shortValue_1 = second.shortValue();
        int _minus = (_shortValue - _shortValue_1);
        _switchResult = IntValue.toIntValue(((short) _minus));
      }
    }
    if (!_matched) {
      if (_resultType instanceof SintType) {
        _matched=true;
        byte _byteValue = first.byteValue();
        byte _byteValue_1 = second.byteValue();
        int _minus = (_byteValue - _byteValue_1);
        _switchResult = SIntValue.toSIntValue(((byte) _minus));
      }
    }
    if (!_matched) {
      if (_resultType instanceof UlintType) {
        _matched=true;
        long _longValue = first.longValue();
        long _longValue_1 = second.longValue();
        long _minus = (_longValue - _longValue_1);
        _switchResult = ULIntValue.toULIntValue(_minus);
      }
    }
    if (!_matched) {
      if (_resultType instanceof UdintType) {
        _matched=true;
        int _intValue = first.intValue();
        int _intValue_1 = second.intValue();
        int _minus = (_intValue - _intValue_1);
        _switchResult = UDIntValue.toUDIntValue(_minus);
      }
    }
    if (!_matched) {
      if (_resultType instanceof UintType) {
        _matched=true;
        short _shortValue = first.shortValue();
        short _shortValue_1 = second.shortValue();
        int _minus = (_shortValue - _shortValue_1);
        _switchResult = UIntValue.toUIntValue(((short) _minus));
      }
    }
    if (!_matched) {
      if (_resultType instanceof UsintType) {
        _matched=true;
        byte _byteValue = first.byteValue();
        byte _byteValue_1 = second.byteValue();
        int _minus = (_byteValue - _byteValue_1);
        _switchResult = USIntValue.toUSIntValue(((byte) _minus));
      }
    }
    if (!_matched) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("The subtract operation is not supported for types ");
      String _name = first.getType().getName();
      _builder.append(_name);
      _builder.append(" and ");
      String _name_1 = second.getType().getName();
      _builder.append(_name_1);
      throw new UnsupportedOperationException(_builder.toString());
    }
    return ((AnyNumValue)_switchResult);
  }
  
  protected static Value _multiply(final Value first, final Value second) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("The multiply operation is not supported for types ");
    String _name = first.getType().getName();
    _builder.append(_name);
    _builder.append(" and ");
    String _name_1 = second.getType().getName();
    _builder.append(_name_1);
    throw new UnsupportedOperationException(_builder.toString());
  }
  
  protected static AnyNumValue _multiply(final AnyNumValue first, final AnyNumValue second) {
    Object _switchResult = null;
    DataType _resultType = ValueOperations.resultType(first.getType(), second.getType());
    boolean _matched = false;
    if (_resultType instanceof LrealType) {
      _matched=true;
      double _doubleValue = first.doubleValue();
      double _doubleValue_1 = second.doubleValue();
      double _multiply = (_doubleValue * _doubleValue_1);
      _switchResult = LRealValue.toLRealValue(_multiply);
    }
    if (!_matched) {
      if (_resultType instanceof RealType) {
        _matched=true;
        float _floatValue = first.floatValue();
        float _floatValue_1 = second.floatValue();
        float _multiply = (_floatValue * _floatValue_1);
        _switchResult = RealValue.toRealValue(_multiply);
      }
    }
    if (!_matched) {
      if (_resultType instanceof LintType) {
        _matched=true;
        long _longValue = first.longValue();
        long _longValue_1 = second.longValue();
        long _multiply = (_longValue * _longValue_1);
        _switchResult = LIntValue.toLIntValue(_multiply);
      }
    }
    if (!_matched) {
      if (_resultType instanceof DintType) {
        _matched=true;
        int _intValue = first.intValue();
        int _intValue_1 = second.intValue();
        int _multiply = (_intValue * _intValue_1);
        _switchResult = DIntValue.toDIntValue(_multiply);
      }
    }
    if (!_matched) {
      if (_resultType instanceof IntType) {
        _matched=true;
        short _shortValue = first.shortValue();
        short _shortValue_1 = second.shortValue();
        int _multiply = (_shortValue * _shortValue_1);
        _switchResult = IntValue.toIntValue(((short) _multiply));
      }
    }
    if (!_matched) {
      if (_resultType instanceof SintType) {
        _matched=true;
        byte _byteValue = first.byteValue();
        byte _byteValue_1 = second.byteValue();
        int _multiply = (_byteValue * _byteValue_1);
        _switchResult = SIntValue.toSIntValue(((byte) _multiply));
      }
    }
    if (!_matched) {
      if (_resultType instanceof UlintType) {
        _matched=true;
        long _longValue = first.longValue();
        long _longValue_1 = second.longValue();
        long _multiply = (_longValue * _longValue_1);
        _switchResult = ULIntValue.toULIntValue(_multiply);
      }
    }
    if (!_matched) {
      if (_resultType instanceof UdintType) {
        _matched=true;
        int _intValue = first.intValue();
        int _intValue_1 = second.intValue();
        int _multiply = (_intValue * _intValue_1);
        _switchResult = UDIntValue.toUDIntValue(_multiply);
      }
    }
    if (!_matched) {
      if (_resultType instanceof UintType) {
        _matched=true;
        short _shortValue = first.shortValue();
        short _shortValue_1 = second.shortValue();
        int _multiply = (_shortValue * _shortValue_1);
        _switchResult = UIntValue.toUIntValue(((short) _multiply));
      }
    }
    if (!_matched) {
      if (_resultType instanceof UsintType) {
        _matched=true;
        byte _byteValue = first.byteValue();
        byte _byteValue_1 = second.byteValue();
        int _multiply = (_byteValue * _byteValue_1);
        _switchResult = USIntValue.toUSIntValue(((byte) _multiply));
      }
    }
    if (!_matched) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("The multiply operation is not supported for types ");
      String _name = first.getType().getName();
      _builder.append(_name);
      _builder.append(" and ");
      String _name_1 = second.getType().getName();
      _builder.append(_name_1);
      throw new UnsupportedOperationException(_builder.toString());
    }
    return ((AnyNumValue)_switchResult);
  }
  
  protected static Value _divideBy(final Value first, final Value second) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("The divide operation is not supported for types ");
    String _name = first.getType().getName();
    _builder.append(_name);
    _builder.append(" and ");
    String _name_1 = second.getType().getName();
    _builder.append(_name_1);
    throw new UnsupportedOperationException(_builder.toString());
  }
  
  protected static AnyNumValue _divideBy(final AnyNumValue first, final AnyNumValue second) {
    Object _switchResult = null;
    DataType _resultType = ValueOperations.resultType(first.getType(), second.getType());
    boolean _matched = false;
    if (_resultType instanceof LrealType) {
      _matched=true;
      double _doubleValue = first.doubleValue();
      double _doubleValue_1 = second.doubleValue();
      double _divide = (_doubleValue / _doubleValue_1);
      _switchResult = LRealValue.toLRealValue(_divide);
    }
    if (!_matched) {
      if (_resultType instanceof RealType) {
        _matched=true;
        float _floatValue = first.floatValue();
        float _floatValue_1 = second.floatValue();
        float _divide = (_floatValue / _floatValue_1);
        _switchResult = RealValue.toRealValue(_divide);
      }
    }
    if (!_matched) {
      if (_resultType instanceof LintType) {
        _matched=true;
        long _longValue = first.longValue();
        long _longValue_1 = second.longValue();
        long _divide = (_longValue / _longValue_1);
        _switchResult = LIntValue.toLIntValue(_divide);
      }
    }
    if (!_matched) {
      if (_resultType instanceof DintType) {
        _matched=true;
        int _intValue = first.intValue();
        int _intValue_1 = second.intValue();
        int _divide = (_intValue / _intValue_1);
        _switchResult = DIntValue.toDIntValue(_divide);
      }
    }
    if (!_matched) {
      if (_resultType instanceof IntType) {
        _matched=true;
        short _shortValue = first.shortValue();
        short _shortValue_1 = second.shortValue();
        int _divide = (_shortValue / _shortValue_1);
        _switchResult = IntValue.toIntValue(((short) _divide));
      }
    }
    if (!_matched) {
      if (_resultType instanceof SintType) {
        _matched=true;
        byte _byteValue = first.byteValue();
        byte _byteValue_1 = second.byteValue();
        int _divide = (_byteValue / _byteValue_1);
        _switchResult = SIntValue.toSIntValue(((byte) _divide));
      }
    }
    if (!_matched) {
      if (_resultType instanceof UlintType) {
        _matched=true;
        _switchResult = ULIntValue.toULIntValue(Long.divideUnsigned(first.longValue(), second.longValue()));
      }
    }
    if (!_matched) {
      if (_resultType instanceof UdintType) {
        _matched=true;
        _switchResult = UDIntValue.toUDIntValue(Integer.divideUnsigned(first.intValue(), second.intValue()));
      }
    }
    if (!_matched) {
      if (_resultType instanceof UintType) {
        _matched=true;
        int _divideUnsigned = Integer.divideUnsigned(first.intValue(), second.intValue());
        _switchResult = UIntValue.toUIntValue(((short) _divideUnsigned));
      }
    }
    if (!_matched) {
      if (_resultType instanceof UsintType) {
        _matched=true;
        int _divideUnsigned = Integer.divideUnsigned(first.intValue(), second.intValue());
        _switchResult = USIntValue.toUSIntValue(((byte) _divideUnsigned));
      }
    }
    if (!_matched) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("The divide operation is not supported for types ");
      String _name = first.getType().getName();
      _builder.append(_name);
      _builder.append(" and ");
      String _name_1 = second.getType().getName();
      _builder.append(_name_1);
      throw new UnsupportedOperationException(_builder.toString());
    }
    return ((AnyNumValue)_switchResult);
  }
  
  protected static Value _remainderBy(final Value first, final Value second) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("The remainder operation is not supported for types ");
    String _name = first.getType().getName();
    _builder.append(_name);
    _builder.append(" and ");
    String _name_1 = second.getType().getName();
    _builder.append(_name_1);
    throw new UnsupportedOperationException(_builder.toString());
  }
  
  protected static AnyNumValue _remainderBy(final AnyNumValue first, final AnyNumValue second) {
    Object _switchResult = null;
    DataType _resultType = ValueOperations.resultType(first.getType(), second.getType());
    boolean _matched = false;
    if (_resultType instanceof LrealType) {
      _matched=true;
      double _doubleValue = first.doubleValue();
      double _doubleValue_1 = second.doubleValue();
      double _modulo = (_doubleValue % _doubleValue_1);
      _switchResult = LRealValue.toLRealValue(_modulo);
    }
    if (!_matched) {
      if (_resultType instanceof RealType) {
        _matched=true;
        float _floatValue = first.floatValue();
        float _floatValue_1 = second.floatValue();
        float _modulo = (_floatValue % _floatValue_1);
        _switchResult = RealValue.toRealValue(_modulo);
      }
    }
    if (!_matched) {
      if (_resultType instanceof LintType) {
        _matched=true;
        long _longValue = first.longValue();
        long _longValue_1 = second.longValue();
        long _modulo = (_longValue % _longValue_1);
        _switchResult = LIntValue.toLIntValue(_modulo);
      }
    }
    if (!_matched) {
      if (_resultType instanceof DintType) {
        _matched=true;
        int _intValue = first.intValue();
        int _intValue_1 = second.intValue();
        int _modulo = (_intValue % _intValue_1);
        _switchResult = DIntValue.toDIntValue(_modulo);
      }
    }
    if (!_matched) {
      if (_resultType instanceof IntType) {
        _matched=true;
        short _shortValue = first.shortValue();
        short _shortValue_1 = second.shortValue();
        int _modulo = (_shortValue % _shortValue_1);
        _switchResult = IntValue.toIntValue(((short) _modulo));
      }
    }
    if (!_matched) {
      if (_resultType instanceof SintType) {
        _matched=true;
        byte _byteValue = first.byteValue();
        byte _byteValue_1 = second.byteValue();
        int _modulo = (_byteValue % _byteValue_1);
        _switchResult = SIntValue.toSIntValue(((byte) _modulo));
      }
    }
    if (!_matched) {
      if (_resultType instanceof UlintType) {
        _matched=true;
        _switchResult = ULIntValue.toULIntValue(Long.remainderUnsigned(first.longValue(), second.longValue()));
      }
    }
    if (!_matched) {
      if (_resultType instanceof UdintType) {
        _matched=true;
        _switchResult = UDIntValue.toUDIntValue(Integer.remainderUnsigned(first.intValue(), second.intValue()));
      }
    }
    if (!_matched) {
      if (_resultType instanceof UintType) {
        _matched=true;
        int _remainderUnsigned = Integer.remainderUnsigned(first.intValue(), second.intValue());
        _switchResult = UIntValue.toUIntValue(((short) _remainderUnsigned));
      }
    }
    if (!_matched) {
      if (_resultType instanceof UsintType) {
        _matched=true;
        int _remainderUnsigned = Integer.remainderUnsigned(first.intValue(), second.intValue());
        _switchResult = USIntValue.toUSIntValue(((byte) _remainderUnsigned));
      }
    }
    if (!_matched) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("The remainder operation is not supported for types ");
      String _name = first.getType().getName();
      _builder.append(_name);
      _builder.append(" and ");
      String _name_1 = second.getType().getName();
      _builder.append(_name_1);
      throw new UnsupportedOperationException(_builder.toString());
    }
    return ((AnyNumValue)_switchResult);
  }
  
  protected static Value _power(final Value first, final Value second) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("The power operation is not supported for types ");
    String _name = first.getType().getName();
    _builder.append(_name);
    _builder.append(" and ");
    String _name_1 = second.getType().getName();
    _builder.append(_name_1);
    throw new UnsupportedOperationException(_builder.toString());
  }
  
  protected static AnyNumValue _power(final AnyNumValue first, final AnyNumValue second) {
    double _doubleValue = first.doubleValue();
    double _doubleValue_1 = second.doubleValue();
    double _power = Math.pow(_doubleValue, _doubleValue_1);
    return LRealValue.toLRealValue(_power);
  }
  
  protected static boolean _equals(final Value first, final Value second) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("The equals operation is not supported for types ");
    String _name = first.getType().getName();
    _builder.append(_name);
    _builder.append(" and ");
    String _name_1 = second.getType().getName();
    _builder.append(_name_1);
    throw new UnsupportedOperationException(_builder.toString());
  }
  
  protected static boolean _equals(final BoolValue first, final BoolValue second) {
    boolean _boolValue = first.boolValue();
    boolean _boolValue_1 = second.boolValue();
    return (_boolValue == _boolValue_1);
  }
  
  protected static boolean _equals(final AnyNumValue first, final AnyNumValue second) {
    boolean _switchResult = false;
    DataType _resultType = ValueOperations.resultType(first.getType(), second.getType());
    boolean _matched = false;
    if (_resultType instanceof LrealType) {
      _matched=true;
      double _doubleValue = first.doubleValue();
      double _doubleValue_1 = second.doubleValue();
      _switchResult = (_doubleValue == _doubleValue_1);
    }
    if (!_matched) {
      if (_resultType instanceof RealType) {
        _matched=true;
        float _floatValue = first.floatValue();
        float _floatValue_1 = second.floatValue();
        _switchResult = (_floatValue == _floatValue_1);
      }
    }
    if (!_matched) {
      if (_resultType instanceof LintType) {
        _matched=true;
      }
      if (!_matched) {
        if (_resultType instanceof UlintType) {
          _matched=true;
        }
      }
      if (_matched) {
        long _longValue = first.longValue();
        long _longValue_1 = second.longValue();
        _switchResult = (_longValue == _longValue_1);
      }
    }
    if (!_matched) {
      if (_resultType instanceof DintType) {
        _matched=true;
      }
      if (!_matched) {
        if (_resultType instanceof UdintType) {
          _matched=true;
        }
      }
      if (_matched) {
        int _intValue = first.intValue();
        int _intValue_1 = second.intValue();
        _switchResult = (_intValue == _intValue_1);
      }
    }
    if (!_matched) {
      if (_resultType instanceof IntType) {
        _matched=true;
      }
      if (!_matched) {
        if (_resultType instanceof UintType) {
          _matched=true;
        }
      }
      if (_matched) {
        short _shortValue = first.shortValue();
        short _shortValue_1 = second.shortValue();
        _switchResult = (_shortValue == _shortValue_1);
      }
    }
    if (!_matched) {
      if (_resultType instanceof SintType) {
        _matched=true;
      }
      if (!_matched) {
        if (_resultType instanceof UsintType) {
          _matched=true;
        }
      }
      if (_matched) {
        byte _byteValue = first.byteValue();
        byte _byteValue_1 = second.byteValue();
        _switchResult = (_byteValue == _byteValue_1);
      }
    }
    if (!_matched) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("The compare operation is not supported for types ");
      String _name = first.getType().getName();
      _builder.append(_name);
      _builder.append(" and ");
      String _name_1 = second.getType().getName();
      _builder.append(_name_1);
      throw new UnsupportedOperationException(_builder.toString());
    }
    return _switchResult;
  }
  
  protected static int _compareTo(final Value first, final Value second) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("The compare operation is not supported for types ");
    String _name = first.getType().getName();
    _builder.append(_name);
    _builder.append(" and ");
    String _name_1 = second.getType().getName();
    _builder.append(_name_1);
    throw new UnsupportedOperationException(_builder.toString());
  }
  
  protected static int _compareTo(final AnyNumValue first, final AnyNumValue second) {
    int _switchResult = (int) 0;
    DataType _resultType = ValueOperations.resultType(first.getType(), second.getType());
    boolean _matched = false;
    if (_resultType instanceof LrealType) {
      _matched=true;
      _switchResult = Double.compare(first.doubleValue(), second.doubleValue());
    }
    if (!_matched) {
      if (_resultType instanceof RealType) {
        _matched=true;
        _switchResult = Float.compare(first.floatValue(), second.floatValue());
      }
    }
    if (!_matched) {
      if (_resultType instanceof LintType) {
        _matched=true;
        _switchResult = Long.compare(first.longValue(), second.longValue());
      }
    }
    if (!_matched) {
      if (_resultType instanceof DintType) {
        _matched=true;
        _switchResult = Integer.compare(first.intValue(), second.intValue());
      }
    }
    if (!_matched) {
      if (_resultType instanceof IntType) {
        _matched=true;
        _switchResult = Short.compare(first.shortValue(), second.shortValue());
      }
    }
    if (!_matched) {
      if (_resultType instanceof SintType) {
        _matched=true;
        _switchResult = Byte.compare(first.byteValue(), second.byteValue());
      }
    }
    if (!_matched) {
      if (_resultType instanceof UlintType) {
        _matched=true;
        _switchResult = Long.compareUnsigned(first.longValue(), second.longValue());
      }
    }
    if (!_matched) {
      if (_resultType instanceof UdintType) {
        _matched=true;
        _switchResult = Integer.compareUnsigned(first.intValue(), second.intValue());
      }
    }
    if (!_matched) {
      if (_resultType instanceof UintType) {
        _matched=true;
        _switchResult = Integer.compareUnsigned(first.intValue(), second.intValue());
      }
    }
    if (!_matched) {
      if (_resultType instanceof UsintType) {
        _matched=true;
        _switchResult = Integer.compareUnsigned(first.intValue(), second.intValue());
      }
    }
    if (!_matched) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("The compare operation is not supported for types ");
      String _name = first.getType().getName();
      _builder.append(_name);
      _builder.append(" and ");
      String _name_1 = second.getType().getName();
      _builder.append(_name_1);
      throw new UnsupportedOperationException(_builder.toString());
    }
    return _switchResult;
  }
  
  public static Value defaultValue(final DataType type) {
    AnyElementaryValue _switchResult = null;
    boolean _matched = false;
    if (type instanceof LrealType) {
      _matched=true;
      _switchResult = LRealValue.DEFAULT;
    }
    if (!_matched) {
      if (type instanceof RealType) {
        _matched=true;
        _switchResult = RealValue.DEFAULT;
      }
    }
    if (!_matched) {
      if (type instanceof LintType) {
        _matched=true;
        _switchResult = LIntValue.DEFAULT;
      }
    }
    if (!_matched) {
      if (type instanceof DintType) {
        _matched=true;
        _switchResult = DIntValue.DEFAULT;
      }
    }
    if (!_matched) {
      if (type instanceof IntType) {
        _matched=true;
        _switchResult = IntValue.DEFAULT;
      }
    }
    if (!_matched) {
      if (type instanceof SintType) {
        _matched=true;
        _switchResult = SIntValue.DEFAULT;
      }
    }
    if (!_matched) {
      if (type instanceof UlintType) {
        _matched=true;
        _switchResult = ULIntValue.DEFAULT;
      }
    }
    if (!_matched) {
      if (type instanceof UdintType) {
        _matched=true;
        _switchResult = UDIntValue.DEFAULT;
      }
    }
    if (!_matched) {
      if (type instanceof UintType) {
        _matched=true;
        _switchResult = UIntValue.DEFAULT;
      }
    }
    if (!_matched) {
      if (type instanceof UsintType) {
        _matched=true;
        _switchResult = USIntValue.DEFAULT;
      }
    }
    if (!_matched) {
      if (type instanceof BoolType) {
        _matched=true;
        _switchResult = BoolValue.DEFAULT;
      }
    }
    if (!_matched) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("The type ");
      String _name = type.getName();
      _builder.append(_name);
      _builder.append(" does not have a default");
      throw new UnsupportedOperationException(_builder.toString());
    }
    return _switchResult;
  }
  
  protected static Value _castValue(final Value value, final DataType type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("The cast operation is not supported for types ");
    String _name = value.getType().getName();
    _builder.append(_name);
    throw new UnsupportedOperationException(_builder.toString());
  }
  
  protected static Value _castValue(final Void value, final DataType type) {
    return null;
  }
  
  protected static Value _castValue(final BoolValue value, final DataType type) {
    BoolValue _switchResult = null;
    boolean _matched = false;
    BoolType _type = value.getType();
    if (Objects.equal(type, _type)) {
      _matched=true;
      _switchResult = value;
    }
    if (!_matched) {
      if (type instanceof BoolType) {
        _matched=true;
        _switchResult = value;
      }
    }
    if (!_matched) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("The value ");
      _builder.append(value);
      _builder.append(" with type ");
      String _name = value.getType().getName();
      _builder.append(_name);
      _builder.append(" cannot be cast to ");
      String _name_1 = type.getName();
      _builder.append(_name_1);
      throw new ClassCastException(_builder.toString());
    }
    return _switchResult;
  }
  
  protected static Value _castValue(final AnyNumValue value, final DataType type) {
    AnyNumValue _switchResult = null;
    boolean _matched = false;
    AnyNumType _type = value.getType();
    if (Objects.equal(type, _type)) {
      _matched=true;
      _switchResult = value;
    }
    if (!_matched) {
      if (type instanceof LrealType) {
        _matched=true;
        _switchResult = LRealValue.toLRealValue(value.doubleValue());
      }
    }
    if (!_matched) {
      if (type instanceof RealType) {
        _matched=true;
        _switchResult = RealValue.toRealValue(value.floatValue());
      }
    }
    if (!_matched) {
      if (type instanceof LintType) {
        _matched=true;
        _switchResult = LIntValue.toLIntValue(value.longValue());
      }
    }
    if (!_matched) {
      if (type instanceof DintType) {
        _matched=true;
        _switchResult = DIntValue.toDIntValue(value.intValue());
      }
    }
    if (!_matched) {
      if (type instanceof IntType) {
        _matched=true;
        _switchResult = IntValue.toIntValue(value.shortValue());
      }
    }
    if (!_matched) {
      if (type instanceof SintType) {
        _matched=true;
        _switchResult = SIntValue.toSIntValue(value.byteValue());
      }
    }
    if (!_matched) {
      if (type instanceof UlintType) {
        _matched=true;
        _switchResult = ULIntValue.toULIntValue(value.longValue());
      }
    }
    if (!_matched) {
      if (type instanceof UdintType) {
        _matched=true;
        _switchResult = UDIntValue.toUDIntValue(value.intValue());
      }
    }
    if (!_matched) {
      if (type instanceof UintType) {
        _matched=true;
        _switchResult = UIntValue.toUIntValue(value.shortValue());
      }
    }
    if (!_matched) {
      if (type instanceof UsintType) {
        _matched=true;
        _switchResult = USIntValue.toUSIntValue(value.byteValue());
      }
    }
    if (!_matched) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("The value ");
      _builder.append(value);
      _builder.append(" with type ");
      String _name = value.getType().getName();
      _builder.append(_name);
      _builder.append(" cannot be cast to ");
      String _name_1 = type.getName();
      _builder.append(_name_1);
      throw new ClassCastException(_builder.toString());
    }
    return _switchResult;
  }
  
  public static Value wrapValue(final Object value, final DataType type) {
    Value _xifexpression = null;
    if ((value == null)) {
      _xifexpression = ValueOperations.defaultValue(type);
    } else {
      AnyElementaryValue _switchResult = null;
      boolean _matched = false;
      if (type instanceof LrealType) {
        _matched=true;
        _switchResult = LRealValue.toLRealValue(((Number) value));
      }
      if (!_matched) {
        if (type instanceof RealType) {
          _matched=true;
          _switchResult = RealValue.toRealValue(((Number) value));
        }
      }
      if (!_matched) {
        if (type instanceof LintType) {
          _matched=true;
          _switchResult = LIntValue.toLIntValue(((Number) value));
        }
      }
      if (!_matched) {
        if (type instanceof DintType) {
          _matched=true;
          _switchResult = DIntValue.toDIntValue(((Number) value));
        }
      }
      if (!_matched) {
        if (type instanceof IntType) {
          _matched=true;
          _switchResult = IntValue.toIntValue(((Number) value));
        }
      }
      if (!_matched) {
        if (type instanceof SintType) {
          _matched=true;
          _switchResult = SIntValue.toSIntValue(((Number) value));
        }
      }
      if (!_matched) {
        if (type instanceof UlintType) {
          _matched=true;
          _switchResult = ULIntValue.toULIntValue(((Number) value));
        }
      }
      if (!_matched) {
        if (type instanceof UdintType) {
          _matched=true;
          _switchResult = UDIntValue.toUDIntValue(((Number) value));
        }
      }
      if (!_matched) {
        if (type instanceof UintType) {
          _matched=true;
          _switchResult = UIntValue.toUIntValue(((Number) value));
        }
      }
      if (!_matched) {
        if (type instanceof UsintType) {
          _matched=true;
          _switchResult = USIntValue.toUSIntValue(((Number) value));
        }
      }
      if (!_matched) {
        if (type instanceof BoolType) {
          _matched=true;
          _switchResult = BoolValue.toBoolValue((((Boolean) value)).booleanValue());
        }
      }
      if (!_matched) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("The type ");
        String _name = type.getName();
        _builder.append(_name);
        _builder.append(" is not supported");
        throw new UnsupportedOperationException(_builder.toString());
      }
      _xifexpression = _switchResult;
    }
    return _xifexpression;
  }
  
  public static Value parseValue(final String value, final DataType type) {
    Value _xifexpression = null;
    boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(value);
    if (_isNullOrEmpty) {
      _xifexpression = ValueOperations.defaultValue(type);
    } else {
      AnyElementaryValue _switchResult = null;
      boolean _matched = false;
      if (type instanceof LrealType) {
        _matched=true;
        _switchResult = LRealValue.toLRealValue(value);
      }
      if (!_matched) {
        if (type instanceof RealType) {
          _matched=true;
          _switchResult = RealValue.toRealValue(value);
        }
      }
      if (!_matched) {
        if (type instanceof LintType) {
          _matched=true;
          _switchResult = LIntValue.toLIntValue(value);
        }
      }
      if (!_matched) {
        if (type instanceof DintType) {
          _matched=true;
          _switchResult = DIntValue.toDIntValue(value);
        }
      }
      if (!_matched) {
        if (type instanceof IntType) {
          _matched=true;
          _switchResult = IntValue.toIntValue(value);
        }
      }
      if (!_matched) {
        if (type instanceof SintType) {
          _matched=true;
          _switchResult = SIntValue.toSIntValue(value);
        }
      }
      if (!_matched) {
        if (type instanceof UlintType) {
          _matched=true;
          _switchResult = ULIntValue.toULIntValue(value);
        }
      }
      if (!_matched) {
        if (type instanceof UdintType) {
          _matched=true;
          _switchResult = UDIntValue.toUDIntValue(value);
        }
      }
      if (!_matched) {
        if (type instanceof UintType) {
          _matched=true;
          _switchResult = UIntValue.toUIntValue(value);
        }
      }
      if (!_matched) {
        if (type instanceof UsintType) {
          _matched=true;
          _switchResult = USIntValue.toUSIntValue(value);
        }
      }
      if (!_matched) {
        if (type instanceof BoolType) {
          _matched=true;
          _switchResult = BoolValue.toBoolValue(value);
        }
      }
      if (!_matched) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("The type ");
        String _name = type.getName();
        _builder.append(_name);
        _builder.append(" is not supported");
        throw new UnsupportedOperationException(_builder.toString());
      }
      _xifexpression = _switchResult;
    }
    return _xifexpression;
  }
  
  public static boolean asBoolean(final Value value) {
    return ((BoolValue) value).boolValue();
  }
  
  public static DataType resultType(final DataType first, final DataType second) {
    DataType _xifexpression = null;
    boolean _isCompatibleWith = first.isCompatibleWith(second);
    if (_isCompatibleWith) {
      _xifexpression = second;
    } else {
      DataType _xifexpression_1 = null;
      boolean _isCompatibleWith_1 = second.isCompatibleWith(first);
      if (_isCompatibleWith_1) {
        _xifexpression_1 = first;
      } else {
        _xifexpression_1 = null;
      }
      _xifexpression = _xifexpression_1;
    }
    return _xifexpression;
  }
  
  public static Value add(final Value first, final Value second) {
    if (first instanceof AnyNumValue
         && second instanceof AnyNumValue) {
      return _add((AnyNumValue)first, (AnyNumValue)second);
    } else if (first != null
         && second != null) {
      return _add(first, second);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(first, second).toString());
    }
  }
  
  public static Value subtract(final Value first, final Value second) {
    if (first instanceof AnyNumValue
         && second instanceof AnyNumValue) {
      return _subtract((AnyNumValue)first, (AnyNumValue)second);
    } else if (first != null
         && second != null) {
      return _subtract(first, second);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(first, second).toString());
    }
  }
  
  public static Value multiply(final Value first, final Value second) {
    if (first instanceof AnyNumValue
         && second instanceof AnyNumValue) {
      return _multiply((AnyNumValue)first, (AnyNumValue)second);
    } else if (first != null
         && second != null) {
      return _multiply(first, second);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(first, second).toString());
    }
  }
  
  public static Value divideBy(final Value first, final Value second) {
    if (first instanceof AnyNumValue
         && second instanceof AnyNumValue) {
      return _divideBy((AnyNumValue)first, (AnyNumValue)second);
    } else if (first != null
         && second != null) {
      return _divideBy(first, second);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(first, second).toString());
    }
  }
  
  public static Value remainderBy(final Value first, final Value second) {
    if (first instanceof AnyNumValue
         && second instanceof AnyNumValue) {
      return _remainderBy((AnyNumValue)first, (AnyNumValue)second);
    } else if (first != null
         && second != null) {
      return _remainderBy(first, second);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(first, second).toString());
    }
  }
  
  public static Value power(final Value first, final Value second) {
    if (first instanceof AnyNumValue
         && second instanceof AnyNumValue) {
      return _power((AnyNumValue)first, (AnyNumValue)second);
    } else if (first != null
         && second != null) {
      return _power(first, second);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(first, second).toString());
    }
  }
  
  public static boolean equals(final Value first, final Value second) {
    if (first instanceof AnyNumValue
         && second instanceof AnyNumValue) {
      return _equals((AnyNumValue)first, (AnyNumValue)second);
    } else if (first instanceof BoolValue
         && second instanceof BoolValue) {
      return _equals((BoolValue)first, (BoolValue)second);
    } else if (first != null
         && second != null) {
      return _equals(first, second);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(first, second).toString());
    }
  }
  
  public static int compareTo(final Value first, final Value second) {
    if (first instanceof AnyNumValue
         && second instanceof AnyNumValue) {
      return _compareTo((AnyNumValue)first, (AnyNumValue)second);
    } else if (first != null
         && second != null) {
      return _compareTo(first, second);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(first, second).toString());
    }
  }
  
  public static Value castValue(final Value value, final DataType type) {
    if (value instanceof AnyNumValue) {
      return _castValue((AnyNumValue)value, type);
    } else if (value instanceof BoolValue) {
      return _castValue((BoolValue)value, type);
    } else if (value == null) {
      return _castValue((Void)null, type);
    } else if (value != null) {
      return _castValue(value, type);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(value, type).toString());
    }
  }
}
