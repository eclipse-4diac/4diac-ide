/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
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
