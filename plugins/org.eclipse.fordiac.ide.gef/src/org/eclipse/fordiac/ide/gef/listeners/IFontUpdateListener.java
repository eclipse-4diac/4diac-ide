/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.listeners;

/**
 * Call back interface to get called when a font preference may changed and
 * updates are needed.
 */
public interface IFontUpdateListener {

	/**
	 * Perform any updates needed
	 */
	void updateFonts();
}
