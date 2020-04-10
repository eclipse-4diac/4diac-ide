/*******************************************************************************
 * Copyright (c) 2011 Profactor GbmH
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
package org.eclipse.fordiac.ide.gef.draw2d;

public interface ITransparencyFigure {

	/**
	 * Sets the transparency (alpha) to the given value. Values may range from 0 to
	 * 255. A value of 0 is completely transparent.
	 * 
	 * @param value
	 */
	void setTransparency(int value);

	/**
	 * Returns the current transparency value;
	 * 
	 * @return the transparency value
	 */
	int getTransparency();

}
