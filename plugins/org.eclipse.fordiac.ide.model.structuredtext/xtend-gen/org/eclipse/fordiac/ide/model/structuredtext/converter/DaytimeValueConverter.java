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

import java.text.SimpleDateFormat;
import org.eclipse.fordiac.ide.model.structuredtext.converter.DateValueConverter;

@SuppressWarnings("all")
public class DaytimeValueConverter extends DateValueConverter {
	public DaytimeValueConverter() {
		super(new SimpleDateFormat("HH:mm:ss"));
	}
}
