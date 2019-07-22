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

import org.eclipse.fordiac.ide.util.preferences.PreferenceConstants;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

public class DiagramFontChangeListener implements IPropertyChangeListener {

	final IFontUpdateListener updateListener;

	public DiagramFontChangeListener(IFontUpdateListener updateListner) {
		this.updateListener = updateListner;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getProperty().equals(PreferenceConstants.DIAGRAM_FONT)) {
			updateListener.updateFonts();
		}
	}

}
