/**
 * Copyright (c) 2015 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Martin Jobst
 *       - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.model.structuredtext.converter;

import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.conversion.impl.AbstractLexerBasedConverter;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public abstract class LongValueConverter extends AbstractLexerBasedConverter<Long> {
  @Override
  protected String toEscapedString(final Long value) {
    return Long.toString((value).longValue(), this.getRadix());
  }
  
  @Override
  protected void assertValidValue(final Long value) {
    super.assertValidValue(value);
    if (((value).longValue() < 0)) {
      String _ruleName = this.getRuleName();
      String _plus = (_ruleName + "-value may not be negative (value: ");
      String _plus_1 = (_plus + value);
      String _plus_2 = (_plus_1 + ").");
      throw new ValueConverterException(_plus_2, 
        null, null);
    }
  }
  
  @Override
  public Long toValue(final String string, final INode node) {
    Long _xblockexpression = null;
    {
      boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(string);
      if (_isNullOrEmpty) {
        throw new ValueConverterException("Couldn\'t convert empty string to a long value.", node, null);
      }
      final String prefix = this.getPrefix();
      boolean _startsWith = string.startsWith(prefix);
      boolean _not = (!_startsWith);
      if (_not) {
        throw new ValueConverterException(
          (((("Couldn\'t convert input \'" + string) + "\' without radix prefix \'") + prefix) + "\' to a long value."), node, 
          null);
      }
      final String value = string.substring(prefix.length()).replace("_", "");
      boolean _isNullOrEmpty_1 = StringExtensions.isNullOrEmpty(value);
      if (_isNullOrEmpty_1) {
        throw new ValueConverterException((("Couldn\'t convert input \'" + string) + "\' to a long value."), node, null);
      }
      Long _xtrycatchfinallyexpression = null;
      try {
        Long _xblockexpression_1 = null;
        {
          final long longValue = Long.parseUnsignedLong(value, this.getRadix());
          _xblockexpression_1 = Long.valueOf(longValue);
        }
        _xtrycatchfinallyexpression = _xblockexpression_1;
      } catch (final Throwable _t) {
        if (_t instanceof NumberFormatException) {
          final NumberFormatException e = (NumberFormatException)_t;
          throw new ValueConverterException((("Couldn\'t convert \'" + string) + "\' to an int value."), node, e);
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
      _xblockexpression = _xtrycatchfinallyexpression;
    }
    return _xblockexpression;
  }
  
  protected String getPrefix() {
    String _string = Integer.toString(this.getRadix());
    return (_string + "#");
  }
  
  protected abstract int getRadix();
}
