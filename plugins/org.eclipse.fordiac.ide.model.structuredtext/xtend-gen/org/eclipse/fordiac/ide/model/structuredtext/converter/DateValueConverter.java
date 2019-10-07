/**
 * Copyright (c) 2015 fortiss GmbH
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

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.conversion.impl.AbstractNullSafeConverter;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class DateValueConverter extends AbstractNullSafeConverter<Date> {
  @Extension
  private final DateFormat format;
  
  public DateValueConverter(final DateFormat format) {
    this.format = format;
  }
  
  @Override
  protected String internalToString(final Date value) {
    return this.format.format(value);
  }
  
  @Override
  protected Date internalToValue(final String string, final INode node) throws ValueConverterException {
    Date _xblockexpression = null;
    {
      boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(string);
      if (_isNullOrEmpty) {
        throw new ValueConverterException("Couldn\'t convert empty string to a date value.", node, null);
      }
      Date _xtrycatchfinallyexpression = null;
      try {
        _xtrycatchfinallyexpression = this.format.parse(string);
      } catch (final Throwable _t) {
        if (_t instanceof ParseException) {
          final ParseException e = (ParseException)_t;
          throw new ValueConverterException((("Couldn\'t convert \'" + string) + "\' to a date value."), node, e);
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
      _xblockexpression = _xtrycatchfinallyexpression;
    }
    return _xblockexpression;
  }
}
