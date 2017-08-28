/*******************************************************************************
 * Copyright (c) 2009, 2017 Profactor GbmH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef;

import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;

/**
 * The Class AdvancedScrollingGraphicalViewer.
 */
public class AdvancedScrollingGraphicalViewer extends ScrollingGraphicalViewer {

	/**
	 * Extends the superclass implementation to scroll the native Canvas control
	 * after the super's implementation has completed.
	 * 
	 * @param part the part
	 * 
	 * @see org.eclipse.gef.EditPartViewer#reveal(org.eclipse.gef.EditPart)
	 */
	@Override
	public void reveal(EditPart part) {
		// do not correct viewport for connections
		// FIXME -> make it setable in e.g. preferences;
		if (!(part instanceof ConnectionEditPart)) {
			 super.reveal(part);
		}
	}

}
