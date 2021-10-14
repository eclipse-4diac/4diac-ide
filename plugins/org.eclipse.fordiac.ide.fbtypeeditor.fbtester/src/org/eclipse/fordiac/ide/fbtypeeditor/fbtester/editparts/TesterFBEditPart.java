/*******************************************************************************
 * Copyright (c) 2012, 2014, 2018 Profactor GmbH, fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Monika Wenger
 *    - initial implementation
 *   Alois Zoitl - separated FBNetworkElement from instance name for better
 *                 direct editing of instance names
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.fbtester.editparts;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Point;

/**
 * The Class FBEditPart.
 */
public class TesterFBEditPart extends org.eclipse.fordiac.ide.application.editparts.FBEditPart {

	/** The control listener. */
	private ControlListener controlListener;

	public TesterFBEditPart() {
		super();
	}

	@Override
	protected Adapter createContentAdapter() {
		// Provide an empty content adpater as we don't want to react in the tester to
		// the classical FBN editing changes
		return new AdapterImpl();
	}

	@Override
	public void activate() {
		super.activate();
		refreshName();
	}

	@Override
	public void deactivate() {
		super.deactivate();
		if (getParent() != null && getParent().getViewer() != null && getParent().getViewer().getControl() != null
				&& controlListener != null) {
			getParent().getViewer().getControl().removeControlListener(controlListener);
			controlListener = null;
		}
	}

	@Override
	protected void createEditPolicies() {
		// should be readonly in fb tester
	}

	@Override
	public boolean understandsRequest(final Request req) {
		// should be readonly in fb tester
		return false;
	}

	@Override
	public void performRequest(final Request request) {
		// should be readonly in fb tester
	}

	@Override
	public void setSelected(final int value) {
		// should be readonly in fb tester
	}

	/**
	 * Update.
	 *
	 * @param bounds the bounds
	 */
	protected void update(final Rectangle bounds) {
		((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), bounds);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		if (controlListener == null) {
			controlListener = new ControlListener() {

				@Override
				public void controlResized(final ControlEvent e) {
					updatePosition();
				}

				@Override
				public void controlMoved(final ControlEvent e) {
					// currently nothing to be done here
				}

			};
			if (getParent() != null && getParent().getViewer() != null
					&& getParent().getViewer().getControl() != null) {
				getParent().getViewer().getControl().addControlListener(controlListener);
			}
		}
		updatePosition();

	}

	private void updatePosition() {
		if (getParent() != null && getParent().getViewer() != null && getParent().getViewer().getControl() != null) {
			final Point p = getParent().getViewer().getControl().getSize();
			final Dimension dim = getFigure().getPreferredSize(-1, -1);

			final Rectangle rect = new Rectangle(p.x / 2 - dim.width / 2, p.y / 2 - dim.height / 2, -1, -1);
			update(rect);
		}
	}

}
