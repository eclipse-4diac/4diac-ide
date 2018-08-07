/*******************************************************************************
 * Copyright (c) 2012 Profactor GbmH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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