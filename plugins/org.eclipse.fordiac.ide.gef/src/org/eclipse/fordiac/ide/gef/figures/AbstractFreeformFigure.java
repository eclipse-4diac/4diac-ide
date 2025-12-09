/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.gef.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformFigure;
import org.eclipse.draw2d.FreeformListener;
import org.eclipse.draw2d.geometry.Rectangle;

public abstract class AbstractFreeformFigure extends Figure implements FreeformFigure, FreeformListener {

	private FreeformFigure contents;
	private Rectangle extent;

	protected AbstractFreeformFigure() {
	}

	@Override
	public Rectangle getFreeformExtent() {
		if (extent == null) {
			extent = calculateFreeformExtent();
			translateToParent(extent);
		}
		return extent;
	}

	protected abstract Rectangle calculateFreeformExtent();

	@Override
	public void setFreeformBounds(final Rectangle bounds) {
		setBounds(bounds);
		final Rectangle childBounds = bounds.getCopy();
		translateFromParent(childBounds);
		setChildBounds(childBounds);
	}

	protected void setChildBounds(final Rectangle childBounds) {
		contents.setFreeformBounds(childBounds); // we insist on our own size calculation
	}

	@Override
	public void addFreeformListener(final FreeformListener listener) {
		addListener(FreeformListener.class, listener);
	}

	@Override
	public void removeFreeformListener(final FreeformListener listener) {
		removeListener(FreeformListener.class, listener);
	}

	@Override
	public void fireExtentChanged() {
		getListeners(FreeformListener.class).forEachRemaining(FreeformListener::notifyFreeformExtentChanged);
	}

	@Override
	public void notifyFreeformExtentChanged() {
		revalidate();
	}

	public void setContents(final FreeformFigure contents) {
		this.contents = contents;
		add(contents);
		contents.addFreeformListener(this);
	}

	public FreeformFigure getContents() {
		return contents;
	}

	@Override
	public void invalidate() {
		extent = null;
		fireExtentChanged();
		super.invalidate();
	}

}