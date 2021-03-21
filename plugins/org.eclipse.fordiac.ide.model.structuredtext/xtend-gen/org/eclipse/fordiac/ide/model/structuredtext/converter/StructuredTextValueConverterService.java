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

import com.google.inject.Inject;
import java.util.Date;
import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverter;
import org.eclipse.xtext.conversion.impl.AbstractDeclarativeValueConverterService;

@SuppressWarnings("all")
public class StructuredTextValueConverterService extends AbstractDeclarativeValueConverterService {
  @Inject
  private BINARY_INTValueConverter binaryIntValueConverter;
  
  @ValueConverter(rule = "BINARY_INT")
  public IValueConverter<Long> BINARY_INT() {
    return this.binaryIntValueConverter;
  }
  
  @Inject
  private OCTAL_INTValueConverter octalIntValueConverter;
  
  @ValueConverter(rule = "OCTAL_INT")
  public IValueConverter<Long> OCTAL_INT() {
    return this.octalIntValueConverter;
  }
  
  @Inject
  private HEX_INTValueConverter hexIntValueConverter;
  
  @ValueConverter(rule = "HEX_INT")
  public IValueConverter<Long> HEX_INT() {
    return this.hexIntValueConverter;
  }
  
  @Inject
  private UNSIGNED_INTValueConverter unsignedIntValueConverter;
  
  @ValueConverter(rule = "UNSIGNED_INT")
  public IValueConverter<Long> UNSIGNED_INT() {
    return this.unsignedIntValueConverter;
  }
  
  @Inject
  private S_BYTE_CHAR_STRValueConverter singleStringValueConverter;
  
  @ValueConverter(rule = "S_BYTE_CHAR_STR")
  public IValueConverter<String> S_BYTE_CHAR_STR() {
    return this.singleStringValueConverter;
  }
  
  @Inject
  private D_BYTE_CHAR_STRValueConverter doubleStringValueConverter;
  
  @ValueConverter(rule = "D_BYTE_CHAR_STR")
  public IValueConverter<String> D_BYTE_CHAR_STR() {
    return this.doubleStringValueConverter;
  }
  
  @Inject
  private Signed_IntValueConverter signedIntValueConverter;
  
  @ValueConverter(rule = "Signed_Int")
  public IValueConverter<Long> Signed_int() {
    return this.signedIntValueConverter;
  }
  
  @Inject
  private Array_SizeValueConverter arraySizeValueConverter;
  
  @ValueConverter(rule = "Array_Size")
  public IValueConverter<Integer> Array_Size() {
    return this.arraySizeValueConverter;
  }
  
  @Inject
  private Real_ValueValueConverter realValueConverter;
  
  @ValueConverter(rule = "Real_Value")
  public IValueConverter<Double> Real_Value() {
    return this.realValueConverter;
  }
  
  @Inject
  private Bool_ValueValueConverter boolValueConverter;
  
  @ValueConverter(rule = "Bool_Value")
  public IValueConverter<Boolean> Bool_Value() {
    return this.boolValueConverter;
  }
  
  @Inject
  private DaytimeValueConverter daytimeValueConverter;
  
  @ValueConverter(rule = "Daytime")
  public IValueConverter<Date> Daytime() {
    return this.daytimeValueConverter;
  }
  
  @Inject
  private Date_LiteralValueConverter dateLiteralValueConverter;
  
  @ValueConverter(rule = "Date_Literal")
  public IValueConverter<Date> Date_Literal() {
    return this.dateLiteralValueConverter;
  }
  
  @Inject
  private Date_And_Time_ValueValueConverter dateAndTimeValueConverter;
  
  @ValueConverter(rule = "Date_And_Time_Value")
  public IValueConverter<Date> Date_And_Time_Value() {
    return this.dateAndTimeValueConverter;
  }
}
