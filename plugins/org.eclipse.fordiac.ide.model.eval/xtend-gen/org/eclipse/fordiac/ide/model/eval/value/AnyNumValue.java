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

import org.eclipse.fordiac.ide.model.data.AnyNumType;

@SuppressWarnings("all")
public interface AnyNumValue extends AnyMagnitudeValue {
  @Override
  AnyNumType getType();
  
  byte byteValue();
  
  short shortValue();
  
  int intValue();
  
  long longValue();
  
  float floatValue();
  
  double doubleValue();
}
