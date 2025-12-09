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

import org.eclipse.fordiac.ide.ui.preferences.UIPreferenceConstants;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

public class DiagramFontChangeListener implements IPropertyChangeListener {

	private final IFontUpdateListener updateListener;

	public DiagramFontChangeListener(IFontUpdateListener updateListner) {
		this.updateListener = updateListner;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getProperty().equals(UIPreferenceConstants.DIAGRAM_FONT)) {
			updateListener.updateFonts();
		}
	}

}
