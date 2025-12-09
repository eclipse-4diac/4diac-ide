/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
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
package org.eclipse.fordiac.ide.gef.handlers;

import org.eclipse.gef.editparts.ZoomManager;

public class Zoom100Handler extends AbstractZoomHandler {

	@Override
	protected void performZoom(final ZoomManager zoomManager) {
		zoomManager.setZoom(1.0);
	}


}
