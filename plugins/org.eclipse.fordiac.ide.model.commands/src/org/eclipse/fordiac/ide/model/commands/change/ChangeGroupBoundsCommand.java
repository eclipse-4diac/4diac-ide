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
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.requests.ChangeBoundsRequest;

public class ChangeGroupBoundsCommand extends SetPositionCommand {

	public ChangeGroupBoundsCommand(final Group group, final ChangeBoundsRequest req,
			final Rectangle newBounds) {
		super(group, req, newBounds);
	}

	@Override
	public Group getPositionableElement() {
		return (Group) super.getPositionableElement();
	}

	@Override
	protected boolean handlesRequestType(final Object type) {
		return super.handlesRequestType(type) || RequestConstants.REQ_RESIZE.equals(type)
				|| RequestConstants.REQ_RESIZE_CHILDREN.equals(type);
	}

	@Override
	protected void setPosition(final Rectangle bounds) {
		getPositionableElement().updatePosition(bounds.getTopLeft());
		getPositionableElement().setWidth(bounds.width);
		getPositionableElement().setHeight(bounds.height);
	}

	@Override
	protected Rectangle getOldBounds() {
		final Group group = getPositionableElement();
		return new Rectangle(group.getPosition().getX(), group.getPosition().getY(), group.getWidth(),
				group.getHeight());
	}
}
