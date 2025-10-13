/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.util.marker;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Color;

public record MarkerDescriptor(String ID, String name, boolean isUnique, Color color) {

	private static final String PREDECESSOR_MARKER_ID = "org.eclipse.fordiac.ide.model.predecessor"; //$NON-NLS-1$
	private static final String CONNECTION_SRC_MARKER_ID = "org.eclipse.fordiac.ide.model.connectionSource"; //$NON-NLS-1$

	private static final String COLOR_PREDECESSOR = "org.eclipse.fordiac.ide.ui.PredecessorMarkerColor"; //$NON-NLS-1$
	private static final String COLOR_CONNECTION_SRC = "org.eclipse.fordiac.ide.ui.ConnectionSourceMarkerColor"; //$NON-NLS-1$

	public static final MarkerDescriptor PREDECESSOR = new MarkerDescriptor(PREDECESSOR_MARKER_ID, "predecessor", true, //$NON-NLS-1$
			JFaceResources.getColorRegistry().get(COLOR_PREDECESSOR));

	public static final MarkerDescriptor CONNECTION_SOURCE = new MarkerDescriptor(CONNECTION_SRC_MARKER_ID,
			"connection source", true, JFaceResources.getColorRegistry().get(COLOR_CONNECTION_SRC)); //$NON-NLS-1$
}
