/*******************************************************************************
 * Copyright (c) 2012, 2014, 2018 Profactor GmbH, fortiss GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Monika Wenger
 *    - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.fbtester.editparts;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Point;

/**
 * The Class FBEditPart.
 */
public class TesterFBEditPart extends
		org.eclipse.fordiac.ide.application.editparts.FBEditPart {

	/** The control listener. */
	private ControlListener controlListener;

	
	public TesterFBEditPart(ZoomManager zoomManager) {
		super(zoomManager);
	}
	
	@Override
	protected EContentAdapter createContentAdapter() {
		//Provide an empty content adpater as we don't want to react in the tester to the classical FBN editing changes
		return new EContentAdapter();
	}

	
	@Override
	protected void refreshName() {
		// always display empty name in fb tester
		getNameLabel().setText(""); //$NON-NLS-1$
	}

	@Override
	public void activate() {
		super.activate();
		refreshName();
	}

	@Override
	public void deactivate() {
		super.deactivate();
		if (getParent() != null && getParent().getViewer() != null
				&& getParent().getViewer().getControl() != null && controlListener != null) {
			getParent().getViewer().getControl()
					.removeControlListener(controlListener);
			controlListener = null;
		}
	}
	
	@Override
	protected void createEditPolicies() {
		// should be readonly in fb tester
	}

	@Override
	public boolean understandsRequest(Request req) {
		// should be readonly in fb tester
		return false;
	}

	@Override
	public void performRequest(Request request) {
		// should be readonly in fb tester
	}

	@Override
	public void setSelected(int value) {
		// should be readonly in fb tester
	}

	/**
	 * Update.
	 * 
	 * @param bounds
	 *            the bounds
	 */
	protected void update(final Rectangle bounds) {
		((GraphicalEditPart) getParent()).setLayoutConstraint(this,
				getFigure(), bounds);

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

				}

			};
			if (getParent() != null && getParent().getViewer() != null
					&& getParent().getViewer().getControl() != null) {
				getParent().getViewer().getControl()
						.addControlListener(controlListener);
			}
		}
		updatePosition();

	}

	private void updatePosition() {
		if (getParent() != null && getParent().getViewer() != null
				&& getParent().getViewer().getControl() != null) {
			Point p = getParent().getViewer().getControl().getSize();
			Dimension dim = getFigure().getPreferredSize(-1, -1);

			Rectangle rect = new Rectangle(p.x / 2 - dim.width / 2, p.y / 2
					- dim.height / 2, -1, -1);
			update(rect);
		}
	}

}
