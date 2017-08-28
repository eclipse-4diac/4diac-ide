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
import org.eclipse.xtext.conversion.impl.AbstractNullSafeConverter;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class Real_ValueValueConverter extends AbstractNullSafeConverter<Double> {
  @Override
  protected String internalToString(final Double value) {
    return value.toString();
  }
  
  @Override
  protected Double internalToValue(final String string, final INode node) throws ValueConverterException {
    Double _xblockexpression = null;
    {
      boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(string);
      if (_isNullOrEmpty) {
        throw new ValueConverterException("Couldn\'t convert empty string to a double value.", node, null);
      }
      final String value = string.replace("_", "");
      boolean _isNullOrEmpty_1 = StringExtensions.isNullOrEmpty(value);
      if (_isNullOrEmpty_1) {
        throw new ValueConverterException((("Couldn\'t convert input \'" + string) + "\' to a double value."), node, null);
      }
      Double _xtrycatchfinallyexpression = null;
      try {
        _xtrycatchfinallyexpression = Double.valueOf(value);
      } catch (final Throwable _t) {
        if (_t instanceof NumberFormatException) {
          final NumberFormatException e = (NumberFormatException)_t;
          throw new ValueConverterException((("Couldn\'t convert \'" + string) + "\' to a double value."), node, e);
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
      _xblockexpression = _xtrycatchfinallyexpression;
    }
    return _xblockexpression;
  }
}
