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

import java.text.SimpleDateFormat;
import org.eclipse.fordiac.ide.model.structuredtext.converter.DateValueConverter;

@SuppressWarnings("all")
public class DaytimeValueConverter extends DateValueConverter {
  public DaytimeValueConverter() {
    super(new SimpleDateFormat("HH:mm:ss"));
  }
}
