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
 *   Melanie Winter - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.Label;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDirectEditManager;
import org.eclipse.fordiac.ide.model.libraryElement.Primitive;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editparts.ZoomManager;

public class ServicePrimitiveDirectEditManager extends AbstractDirectEditManager<Primitive> {

	public ServicePrimitiveDirectEditManager(final GraphicalEditPart source, final Primitive primitive,
			final Label label, final ZoomManager zoomManager, final FigureCanvas fc) {
		super(source, primitive, label, zoomManager, fc, ServicePrimitiveCellEditor.class);
	}
}
