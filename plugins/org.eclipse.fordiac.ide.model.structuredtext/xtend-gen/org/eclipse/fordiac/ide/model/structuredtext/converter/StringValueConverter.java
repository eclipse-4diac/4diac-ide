/**
 * Copyright (c) 2015, 2017 fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst
 *       - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.model.structuredtext.converter;

import java.io.Reader;
import java.io.StringReader;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.conversion.impl.AbstractNullSafeConverter;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.eclipse.xtext.xbase.lib.IntegerRange;

@SuppressWarnings("all")
public abstract class StringValueConverter extends AbstractNullSafeConverter<String> {
  @Override
  protected String internalToString(final String value) {
    try {
      String _xblockexpression = null;
      {
        final StringReader reader = new StringReader(value);
        final StringBuilder result = new StringBuilder();
        result.append(this.getQuote());
        for (int i = new Function0<Integer>() {
          @Override
          public Integer apply() {
            try {
              return reader.read();
            } catch (Throwable _e) {
              throw Exceptions.sneakyThrow(_e);
            }
          }
        }.apply().intValue(); (i != (-1)); i = reader.read()) {
          final char c = ((char) i);
          boolean _matched = false;
          boolean _tripleEquals = this.operator_tripleEquals(" ", c);
          if (_tripleEquals) {
            _matched=true;
          }
          if (!_matched) {
            boolean _tripleEquals_1 = this.operator_tripleEquals("!", c);
            if (_tripleEquals_1) {
              _matched=true;
            }
          }
          if (!_matched) {
            boolean _tripleEquals_2 = this.operator_tripleEquals("#", c);
            if (_tripleEquals_2) {
              _matched=true;
            }
          }
          if (!_matched) {
            boolean _tripleEquals_3 = this.operator_tripleEquals("%", c);
            if (_tripleEquals_3) {
              _matched=true;
            }
          }
          if (!_matched) {
            boolean _tripleEquals_4 = this.operator_tripleEquals("&", c);
            if (_tripleEquals_4) {
              _matched=true;
            }
          }
          if (!_matched) {
            boolean _contains = this.operator_upTo("(", "/").contains(c);
            if (_contains) {
              _matched=true;
            }
          }
          if (!_matched) {
            boolean _contains_1 = this.operator_upTo("0", "9").contains(c);
            if (_contains_1) {
              _matched=true;
            }
          }
          if (!_matched) {
            boolean _contains_2 = this.operator_upTo(":", "@").contains(c);
            if (_contains_2) {
              _matched=true;
            }
          }
          if (!_matched) {
            boolean _contains_3 = this.operator_upTo("A", "Z").contains(c);
            if (_contains_3) {
              _matched=true;
            }
          }
          if (!_matched) {
            boolean _contains_4 = this.operator_upTo("[", "`").contains(c);
            if (_contains_4) {
              _matched=true;
            }
          }
          if (!_matched) {
            boolean _contains_5 = this.operator_upTo("a", "z").contains(c);
            if (_contains_5) {
              _matched=true;
            }
          }
          if (!_matched) {
            boolean _contains_6 = this.operator_upTo("{", "~").contains(c);
            if (_contains_6) {
              _matched=true;
            }
          }
          if (_matched) {
            result.append(c);
          }
          if (!_matched) {
            boolean _tripleEquals_5 = this.operator_tripleEquals("\'", c);
            if (_tripleEquals_5) {
              _matched=true;
            }
            if (!_matched) {
              boolean _tripleEquals_6 = this.operator_tripleEquals("\"", c);
              if (_tripleEquals_6) {
                _matched=true;
              }
            }
            if (_matched) {
              char _quote = this.getQuote();
              boolean _equals = (c == _quote);
              if (_equals) {
                result.append("$");
              }
              result.append(c);
            }
          }
          if (!_matched) {
            boolean _tripleEquals_7 = this.operator_tripleEquals("$", c);
            if (_tripleEquals_7) {
              _matched=true;
              result.append("$$");
            }
          }
          if (!_matched) {
            boolean _tripleEquals_8 = this.operator_tripleEquals("\n", c);
            if (_tripleEquals_8) {
              _matched=true;
              result.append("$N");
            }
          }
          if (!_matched) {
            boolean _tripleEquals_9 = this.operator_tripleEquals("\f", c);
            if (_tripleEquals_9) {
              _matched=true;
              result.append("$P");
            }
          }
          if (!_matched) {
            boolean _tripleEquals_10 = this.operator_tripleEquals("\r", c);
            if (_tripleEquals_10) {
              _matched=true;
              result.append("$R");
            }
          }
          if (!_matched) {
            boolean _tripleEquals_11 = this.operator_tripleEquals("\t", c);
            if (_tripleEquals_11) {
              _matched=true;
              result.append("$T");
            }
          }
          if (!_matched) {
            result.append(this.toHexLiteral(((char) c)));
          }
        }
        result.append(this.getQuote());
        _xblockexpression = result.toString();
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Override
  protected String internalToValue(final String string, final INode node) {
    try {
      String _xblockexpression = null;
      {
        if ((string.isEmpty() || (string.length() < 2))) {
          throw new ValueConverterException("Unclosed string literal", null, null);
        }
        if (((string.charAt(0) != this.getQuote()) || (string.charAt((string.length() - 1)) != this.getQuote()))) {
          throw new ValueConverterException("Invalid quotes for string literal", null, null);
        }
        int _length = string.length();
        int _minus = (_length - 1);
        final String value = string.substring(1, _minus);
        final StringReader reader = new StringReader(value);
        final StringBuilder result = new StringBuilder();
        for (int i = new Function0<Integer>() {
          @Override
          public Integer apply() {
            try {
              return reader.read();
            } catch (Throwable _e) {
              throw Exceptions.sneakyThrow(_e);
            }
          }
        }.apply().intValue(); (i != (-1)); i = reader.read()) {
          Object _switchResult = null;
          final char c = ((char) i);
          boolean _matched = false;
          boolean _tripleEquals = this.operator_tripleEquals(" ", c);
          if (_tripleEquals) {
            _matched=true;
          }
          if (!_matched) {
            boolean _tripleEquals_1 = this.operator_tripleEquals("!", c);
            if (_tripleEquals_1) {
              _matched=true;
            }
          }
          if (!_matched) {
            boolean _tripleEquals_2 = this.operator_tripleEquals("#", c);
            if (_tripleEquals_2) {
              _matched=true;
            }
          }
          if (!_matched) {
            boolean _tripleEquals_3 = this.operator_tripleEquals("%", c);
            if (_tripleEquals_3) {
              _matched=true;
            }
          }
          if (!_matched) {
            boolean _tripleEquals_4 = this.operator_tripleEquals("&", c);
            if (_tripleEquals_4) {
              _matched=true;
            }
          }
          if (!_matched) {
            boolean _contains = this.operator_upTo("(", "/").contains(c);
            if (_contains) {
              _matched=true;
            }
          }
          if (!_matched) {
            boolean _contains_1 = this.operator_upTo("0", "9").contains(c);
            if (_contains_1) {
              _matched=true;
            }
          }
          if (!_matched) {
            boolean _contains_2 = this.operator_upTo(":", "@").contains(c);
            if (_contains_2) {
              _matched=true;
            }
          }
          if (!_matched) {
            boolean _contains_3 = this.operator_upTo("A", "Z").contains(c);
            if (_contains_3) {
              _matched=true;
            }
          }
          if (!_matched) {
            boolean _contains_4 = this.operator_upTo("[", "`").contains(c);
            if (_contains_4) {
              _matched=true;
            }
          }
          if (!_matched) {
            boolean _contains_5 = this.operator_upTo("a", "z").contains(c);
            if (_contains_5) {
              _matched=true;
            }
          }
          if (!_matched) {
            boolean _contains_6 = this.operator_upTo("{", "~").contains(c);
            if (_contains_6) {
              _matched=true;
            }
          }
          if (_matched) {
            _switchResult = Character.valueOf(c);
          }
          if (!_matched) {
            boolean _tripleEquals_5 = this.operator_tripleEquals("\'", c);
            if (_tripleEquals_5) {
              _matched=true;
            }
            if (!_matched) {
              boolean _tripleEquals_6 = this.operator_tripleEquals("\"", c);
              if (_tripleEquals_6) {
                _matched=true;
              }
            }
            if (_matched) {
              char _xblockexpression_1 = (char) 0;
              {
                char _quote = this.getQuote();
                boolean _tripleEquals_7 = (c == _quote);
                if (_tripleEquals_7) {
                  throw new ValueConverterException(
                    "Couldn\'t convert value due to illegal quote character inside string", null, null);
                }
                _xblockexpression_1 = c;
              }
              _switchResult = Character.valueOf(_xblockexpression_1);
            }
          }
          if (!_matched) {
            boolean _tripleEquals_7 = this.operator_tripleEquals("$", c);
            if (_tripleEquals_7) {
              _matched=true;
              Object _xblockexpression_2 = null;
              {
                i = reader.read();
                if ((i == (-1))) {
                  throw new ValueConverterException("Couldn\'t convert value due to invalid escape sequence", null, null);
                }
                Object _switchResult_1 = null;
                final char d = ((char) i);
                boolean _matched_1 = false;
                boolean _tripleEquals_8 = this.operator_tripleEquals("$", d);
                if (_tripleEquals_8) {
                  _matched_1=true;
                  _switchResult_1 = "$";
                }
                if (!_matched_1) {
                  boolean _tripleEquals_9 = this.operator_tripleEquals("L", d);
                  if (_tripleEquals_9) {
                    _matched_1=true;
                  }
                  if (!_matched_1) {
                    boolean _tripleEquals_10 = this.operator_tripleEquals("l", d);
                    if (_tripleEquals_10) {
                      _matched_1=true;
                    }
                  }
                  if (_matched_1) {
                    _switchResult_1 = "\n";
                  }
                }
                if (!_matched_1) {
                  boolean _tripleEquals_11 = this.operator_tripleEquals("N", d);
                  if (_tripleEquals_11) {
                    _matched_1=true;
                  }
                  if (!_matched_1) {
                    boolean _tripleEquals_12 = this.operator_tripleEquals("n", d);
                    if (_tripleEquals_12) {
                      _matched_1=true;
                    }
                  }
                  if (_matched_1) {
                    _switchResult_1 = "\n";
                  }
                }
                if (!_matched_1) {
                  boolean _tripleEquals_13 = this.operator_tripleEquals("P", d);
                  if (_tripleEquals_13) {
                    _matched_1=true;
                  }
                  if (!_matched_1) {
                    boolean _tripleEquals_14 = this.operator_tripleEquals("p", d);
                    if (_tripleEquals_14) {
                      _matched_1=true;
                    }
                  }
                  if (_matched_1) {
                    _switchResult_1 = "\f";
                  }
                }
                if (!_matched_1) {
                  boolean _tripleEquals_15 = this.operator_tripleEquals("R", d);
                  if (_tripleEquals_15) {
                    _matched_1=true;
                  }
                  if (!_matched_1) {
                    boolean _tripleEquals_16 = this.operator_tripleEquals("r", d);
                    if (_tripleEquals_16) {
                      _matched_1=true;
                    }
                  }
                  if (_matched_1) {
                    _switchResult_1 = "\r";
                  }
                }
                if (!_matched_1) {
                  boolean _tripleEquals_17 = this.operator_tripleEquals("T", d);
                  if (_tripleEquals_17) {
                    _matched_1=true;
                  }
                  if (!_matched_1) {
                    boolean _tripleEquals_18 = this.operator_tripleEquals("t", d);
                    if (_tripleEquals_18) {
                      _matched_1=true;
                    }
                  }
                  if (_matched_1) {
                    _switchResult_1 = "\t";
                  }
                }
                if (!_matched_1) {
                  char _quote = this.getQuote();
                  boolean _tripleEquals_19 = (_quote == d);
                  if (_tripleEquals_19) {
                    _matched_1=true;
                    _switchResult_1 = Character.valueOf(this.getQuote());
                  }
                }
                if (!_matched_1) {
                  char _xblockexpression_3 = (char) 0;
                  {
                    reader.skip((-1));
                    _xblockexpression_3 = this.parseHexLiteral(reader);
                  }
                  _switchResult_1 = Character.valueOf(_xblockexpression_3);
                }
                _xblockexpression_2 = ((Object)_switchResult_1);
              }
              _switchResult = ((Object)_xblockexpression_2);
            }
          }
          if (!_matched) {
            throw new ValueConverterException("Couldn\'t convert value due to invalid character", null, null);
          }
          result.append(_switchResult);
        }
        _xblockexpression = result.toString();
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public boolean operator_tripleEquals(final String s, final char c) {
    boolean _xblockexpression = false;
    {
      int _length = s.length();
      boolean _notEquals = (_length != 1);
      if (_notEquals) {
        return false;
      }
      char _charAt = s.charAt(0);
      _xblockexpression = (_charAt == c);
    }
    return _xblockexpression;
  }
  
  public IntegerRange operator_upTo(final String string, final String string2) {
    IntegerRange _xblockexpression = null;
    {
      if (((string.length() != 1) || (string2.length() != 1))) {
        throw new UnsupportedOperationException("Strings must have length 1");
      }
      char _charAt = string.charAt(0);
      char _charAt_1 = string2.charAt(0);
      _xblockexpression = new IntegerRange(_charAt, _charAt_1);
    }
    return _xblockexpression;
  }
  
  public abstract char getQuote();
  
  public abstract char parseHexLiteral(final Reader reader);
  
  public abstract CharSequence toHexLiteral(final char c);
}
