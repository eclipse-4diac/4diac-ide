/*******************************************************************************
 * Copyright (c) 2015, 2017 fortiss GmbH 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.ruler;

import org.eclipse.gef.rulers.RulerProvider;

class FordiacRulerProvider extends RulerProvider {
	
	@Override
	public int getUnit() {
		return UNIT_PIXELS;
	}
	
	@Override
	public Object getRuler() {
		return this;
	}
}