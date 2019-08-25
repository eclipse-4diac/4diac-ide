/**
 * Copyright (c) 2017 fortiss GmbH
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

import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.conversion.impl.AbstractNullSafeConverter;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class Array_SizeValueConverter extends AbstractNullSafeConverter<Integer> {
  @Override
  protected String internalToString(final Integer value) {
    return value.toString();
  }
  
  @Override
  protected Integer internalToValue(final String string, final INode node) throws ValueConverterException {
    Integer _xblockexpression = null;
    {
      boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(string);
      if (_isNullOrEmpty) {
        throw new ValueConverterException("Couldn\'t convert empty string to an int value.", node, null);
      }
      final String value = string.replace("_", "");
      boolean _isNullOrEmpty_1 = StringExtensions.isNullOrEmpty(value);
      if (_isNullOrEmpty_1) {
        throw new ValueConverterException((("Couldn\'t convert input \'" + string) + "\' to an int value."), node, null);
      }
      Integer _xtrycatchfinallyexpression = null;
      try {
        _xtrycatchfinallyexpression = Integer.valueOf(value);
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
}
