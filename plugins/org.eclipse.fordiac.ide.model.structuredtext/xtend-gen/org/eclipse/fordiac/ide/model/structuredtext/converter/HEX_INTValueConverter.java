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

import org.eclipse.fordiac.ide.model.structuredtext.converter.LongValueConverter;

@SuppressWarnings("all")
public class HEX_INTValueConverter extends LongValueConverter {
  @Override
  public int getRadix() {
    return 16;
  }
}
