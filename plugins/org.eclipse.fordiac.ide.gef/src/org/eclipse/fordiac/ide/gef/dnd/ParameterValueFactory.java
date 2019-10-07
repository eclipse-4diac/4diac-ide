/*******************************************************************************
 * Copyright (c) 2012 Profactor GbmH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.dnd;

import org.eclipse.gef.requests.CreationFactory;

public class ParameterValueFactory implements CreationFactory {

   private String text = ""; //$NON-NLS-1$

   @Override
public Object getNewObject() {
	   return text;
   }

   @Override
public Object getObjectType() {
      return String.class;
   }

   public void setText(String s) {
      text = s;
   }
}