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
 *     - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.export.forte_lua.filter;

import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.util.Strings;

@SuppressWarnings("all")
public class LuaUtils {
  public static CharSequence luaString(final String s) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\"");
    String _convertToJavaString = Strings.convertToJavaString(s);
    _builder.append(_convertToJavaString);
    _builder.append("\"");
    return _builder;
  }
  
  public static CharSequence luaStringList(final Iterable<String> list) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    {
      boolean _hasElements = false;
      for(final String value : list) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(", ", "");
        }
        CharSequence _luaString = LuaUtils.luaString(value);
        _builder.append(_luaString);
      }
    }
    _builder.append("}");
    return _builder;
  }
  
  public static CharSequence luaIntegerList(final Iterable<Integer> list) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    {
      boolean _hasElements = false;
      for(final Integer value : list) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(", ", "");
        }
        String _string = value.toString();
        _builder.append(_string);
      }
    }
    _builder.append("}");
    return _builder;
  }
  
  public static CharSequence luaValueList(final Iterable<?> list) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    {
      boolean _hasElements = false;
      for(final Object value : list) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(", ", "");
        }
        {
          if ((value instanceof String)) {
            CharSequence _luaString = LuaUtils.luaString(((String)value));
            _builder.append(_luaString);
          } else {
            String _string = value.toString();
            _builder.append(_string);
          }
        }
      }
    }
    _builder.append("}");
    return _builder;
  }
}
