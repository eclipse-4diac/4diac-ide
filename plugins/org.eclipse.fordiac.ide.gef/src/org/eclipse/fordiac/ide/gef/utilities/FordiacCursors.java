/*******************************************************************************
 * Copyright (c) 2018 fortiss GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.utilities;

import org.eclipse.fordiac.ide.util.imageprovider.FordiacImage;
import org.eclipse.swt.graphics.Cursor;

/** Class for providing own connection creation cursors to fix an issue in 
 * Gef. 
 */
public final class FordiacCursors {
	
	/** Cursor for valid connection
	 */
	public static final Cursor CURSOR_PLUG;
	/** Cursor for invalid connection
	 */
	public static final Cursor CURSOR_PLUG_NOT;
	
	static {
		CURSOR_PLUG = createCursor(FordiacImage.CURSOR_PLUG, FordiacImage.CURSOR_PLUG_MASK); 
		CURSOR_PLUG_NOT = createCursor(FordiacImage.CURSOR_PLUG_NOT, FordiacImage.CURSOR_PLUG_NOT_MASK); 
	}

	private static Cursor createCursor(FordiacImage img, FordiacImage mask) {
		//TODO get get and us the correct scale factor
		return new Cursor(null, img.getImageDescriptor().getImageData(100), mask.getImageDescriptor().getImageData(100), 0, 0);
	}
}
