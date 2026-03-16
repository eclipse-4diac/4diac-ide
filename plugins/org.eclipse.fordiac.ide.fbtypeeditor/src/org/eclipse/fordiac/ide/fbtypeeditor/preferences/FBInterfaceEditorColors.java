/*******************************************************************************
 * Copyright (c) 2026 Johannes Kepler University Linz
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
package org.eclipse.fordiac.ide.fbtypeeditor.preferences;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Color;

public final class FBInterfaceEditorColors {

	private static final String P_WITH_LINE_COLOR = "org.eclipse.fordiac.ide.fbtypeeditor.WithLineColor"; //$NON-NLS-1$
	private static final String P_WITH_BOX_COLOR = "org.eclipse.fordiac.ide.fbtypeeditor.WithBoxColor"; //$NON-NLS-1$

	public static Color getWithLineColor() {
		return JFaceResources.getColorRegistry().get(P_WITH_LINE_COLOR);
	}

	public static Color getWithBoxColor() {
		return JFaceResources.getColorRegistry().get(P_WITH_BOX_COLOR);
	}

	private FBInterfaceEditorColors() {
		throw new UnsupportedOperationException("PreferenceConstants utility class should not be instantiated!"); //$NON-NLS-1$
	}

}
