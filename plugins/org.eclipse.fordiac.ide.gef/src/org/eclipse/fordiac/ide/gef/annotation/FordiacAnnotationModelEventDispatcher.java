/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.annotation;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.GraphicalViewer;

public class FordiacAnnotationModelEventDispatcher extends GraphicalViewerAnnotationModelEventDispatcher {

	public FordiacAnnotationModelEventDispatcher(final GraphicalViewer viewer) {
		super(viewer);
	}

	@Override
	protected AnnotableGraphicalEditPart findEditPart(final Object target) {
		final AnnotableGraphicalEditPart result = super.findEditPart(target);
		if (result == null && target instanceof final EObject object) {
			return findEditPart(object.eContainer()); // try edit part for parent object
		}
		return result;
	}
}
