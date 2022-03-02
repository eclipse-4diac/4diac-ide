/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.application.policies;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.editparts.GroupContentEditPart;
import org.eclipse.fordiac.ide.gef.policies.ModifiedResizeablePolicy;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.ResizeTracker;

public class GroupResizePolicy extends ModifiedResizeablePolicy {

	@Override
	protected ResizeTracker getResizeTracker(final int direction) {
		return new GroupResizeTracker(getHost(), direction);
	}

	@Override
	public GraphicalEditPart getHost() {
		return (GraphicalEditPart) super.getHost();
	}

	public static GraphicalEditPart getGroupContent(final EditPart groupEP) {
		return (GraphicalEditPart) groupEP.getChildren().stream()
				.filter(GroupContentEditPart.class::isInstance).findAny().orElse(null);
	}

	public static Rectangle getGroupContentBounds(final EditPart groupContent) {
		Rectangle selectionExtend = null;
		for (final Object child : groupContent.getChildren()) {
			if (child instanceof GraphicalEditPart) {
				final Rectangle fbBounds = getBoundsForEditPart((GraphicalEditPart) child);
				if (selectionExtend == null) {
					selectionExtend = fbBounds.getCopy();
				} else {
					selectionExtend.union(fbBounds);
				}
			}
		}
		return (selectionExtend != null) ? selectionExtend : getDefaultGroupContentBounds();
	}

	private static Rectangle getBoundsForEditPart(final GraphicalEditPart ep) {
		return ep.getFigure().getBounds().getCopy();
	}

	static Rectangle getDefaultGroupContentBounds() {
		return new Rectangle(new Point(0, 0), IFigure.MIN_DIMENSION);
	}
}