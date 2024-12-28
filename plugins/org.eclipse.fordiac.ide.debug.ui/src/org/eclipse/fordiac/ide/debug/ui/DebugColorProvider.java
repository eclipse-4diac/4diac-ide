/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *   Alois Zoitl  - extracted from org.eclipse.fordiac.ide.deployment.debug.ui.DeploymentDebugModelPresentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.ui;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Color;

public final class DebugColorProvider {

	public static final String WATCH_COLOR = "org.eclipse.fordiac.ide.deployment.debug.ui.watchColor"; //$NON-NLS-1$

	public static final String WATCH_TEXT_COLOR = "org.eclipse.fordiac.ide.deployment.debug.ui.watchTextColor"; //$NON-NLS-1$

	public static Color getWatchColor() {
		return JFaceResources.getColorRegistry().get(WATCH_COLOR);
	}

	public static Color getWatchTextColor() {
		return JFaceResources.getColorRegistry().get(WATCH_TEXT_COLOR);
	}

	private DebugColorProvider() {
		throw new UnsupportedOperationException("Utility class must not be inherited"); //$NON-NLS-1$
	}
}
