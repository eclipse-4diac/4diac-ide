/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - moved openEditor helper function to EditorUtils
 *               - added diagram font preference
 *   			 - separated FBNetworkElement from instance name for better
 *                 direct editing of instance names
 *               - extracted common FB shape for interface and fbn editors
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.figures;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.gef.figures.FBShape;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;

/**
 * The visualization of an FB. It Provides several containers for its interface.
 */
public class FBNetworkElementFigure extends FBShape {

	/** The model. */
	private FBNetworkElement model = null;

	protected FBNetworkElement getModel() {
		return model;
	}

	/**
	 * Instantiates a new fB figure.
	 *
	 * @param model the model
	 */
	public FBNetworkElementFigure(final FBNetworkElement model) {
		super(model.getType());
		this.model = model;
		refreshToolTips();
	}

	/** Refresh tool tips. */
	public final void refreshToolTips() {
		setToolTip(new FBNetworkElementTooltipFigure(model));
	}

	public Rectangle getFBBounds() {
		final int x = getTop().getBounds().x();
		final int y = getLabelBounds().y();
		final int width = getTop().getBounds().width;
		final int height = getTop().getBounds().height() + getMiddle().getBounds().height()
				+ getBottom().getBounds().height() + getLabelBounds().height();
		return new Rectangle(x, y, width, height);
	}

	public Rectangle getLabelBounds() {
		for (final Object figure : getChildren()) {
			if (figure instanceof final InstanceNameFigure nameFigure) {
				return nameFigure.getBounds();
			}
		}
		return new Rectangle();
	}

}
