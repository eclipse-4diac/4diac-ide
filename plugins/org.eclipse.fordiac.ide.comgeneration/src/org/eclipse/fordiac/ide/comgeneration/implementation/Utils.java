/*******************************************************************************
 * Copyright (c) 2014 - 2017 Luka Lednicki, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Luka Lednicki, Gerd Kainz, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.comgeneration.implementation;

import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteGroup;
import org.eclipse.fordiac.ide.model.libraryElement.Application;

public class Utils {		
	public static Palette getPalette(Application application) {
		Object o = application.getFBNetwork().getNetworkElements().get(0).getPaletteEntry().eContainer();
		while (o != null && !(o instanceof Palette)) {
			if (o instanceof PaletteGroup) {
				o = ((PaletteGroup) o).eContainer();
			} else {
				o = null;
			}
		}
		if (o instanceof Palette) {
			return (Palette) o;
		} else {
			return null;
		}		
	}
}
