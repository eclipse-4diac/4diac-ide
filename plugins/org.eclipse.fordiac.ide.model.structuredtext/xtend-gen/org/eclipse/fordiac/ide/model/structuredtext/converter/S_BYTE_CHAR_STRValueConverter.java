/**
 * Copyright (c) 2015, 2017 fortiss GmbH
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

import java.io.Reader;
import org.eclipse.fordiac.ide.model.structuredtext.converter.StringValueConverter;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class S_BYTE_CHAR_STRValueConverter extends StringValueConverter {
  @Override
  public char getQuote() {
    return '\'';
  }
  
  @Override
  public char parseHexLiteral(final Reader reader) {
    try {
      char _xblockexpression = (char) 0;
      {
        final char[] cbuf = new char[2];
        int _read = reader.read(cbuf);
        boolean _notEquals = (_read != 2);
        if (_notEquals) {
          throw new ValueConverterException("Couldn\'t convert value due to invalid escape sequence", null, null);
        }
        char _xtrycatchfinallyexpression = (char) 0;
        try {
          int _parseUnsignedInt = Integer.parseUnsignedInt(String.valueOf(cbuf), 16);
          _xtrycatchfinallyexpression = ((char) _parseUnsignedInt);
        } catch (final Throwable _t) {
          if (_t instanceof NumberFormatException) {
            throw new ValueConverterException("Couldn\'t convert value due to invalid escape sequence", null, null);
          } else {
            throw Exceptions.sneakyThrow(_t);
          }
        }
        _xblockexpression = _xtrycatchfinallyexpression;
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Override
  public CharSequence toHexLiteral(final char c) {
    String _xblockexpression = null;
    {
      if ((c > 255)) {
        throw new ValueConverterException("Couldn\'t convert value due to invalid character", null, null);
      }
      _xblockexpression = Integer.toUnsignedString(c, 16);
    }
    return _xblockexpression;
  }
}
