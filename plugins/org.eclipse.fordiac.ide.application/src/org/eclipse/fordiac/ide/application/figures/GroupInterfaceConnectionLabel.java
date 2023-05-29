/*******************************************************************************
 * Copyright (c) 2023  Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.application.figures;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

public class GroupInterfaceConnectionLabel extends FBNetworkConnectionLabel {

	private IFigure groupFigure;

	public GroupInterfaceConnectionLabel(final boolean srcLabel, final IFigure groupFigure) {
		super(srcLabel);
		this.groupFigure = groupFigure;
	}

	public IFigure getGroupFigure() {
		return groupFigure;
	}

	public void setGroupFigure(final IFigure groupFigure) {
		this.groupFigure = groupFigure;
	}

	@Override
	public void setLocation(final Point p) {
		if (groupFigure == null) {
			// there was a problem with the group figure, go back to the default behavior
			super.setLocation(p);
			return;
		}

		final Dimension preferredSize = super.getPreferredSize();
		final Rectangle groupBounds = groupFigure.getBounds();
		if (!isSrcLabel()) {
			p.x = Math.min(groupBounds.x, p.x - preferredSize.width);
		} else {
			p.x = Math.max(groupBounds.right() - preferredSize.width, p.x);
		}
		p.y -= preferredSize.height / 2;
		super.setBounds(new Rectangle(p, preferredSize));
	}

}
