/*******************************************************************************
 * Copyright (c) 2008, 2013, 2015, 2017 Profactor GbmH, fortiss GmbH, 
 * 				 2018 Johannes Kepler University 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.policies;

import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.geometry.Transposer;
import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * An non abstract ConstrainedLayoutEditPolicy.
 */
public class EmptyXYLayoutEditPolicy extends ConstrainedLayoutEditPolicy {
	private static final Dimension DEFAULT_SIZE = new Dimension(-1, -1);


	@Override
	protected Command getCreateCommand(final CreateRequest request) {
		return null;
	}

	@Override
	protected Command getDeleteDependantCommand(final Request request) {
		return null;
	}

	@Override
	protected EditPolicy createChildEditPolicy(EditPart child) {
		IPreferenceStore pf = Activator.getDefault().getPreferenceStore();
		int cornerDim = pf.getInt(DiagramPreferences.CORNER_DIM);
		if (cornerDim > 1) {
			cornerDim = cornerDim / 2;
		}
		return new ModifiedNonResizeableEditPolicy(cornerDim, new Insets(1));
	}

	/**
	 * Returns a Rectangle at the given Point with width and height of -1.
	 * <code>XYLayout</code> uses width or height equal to '-1' to mean use the
	 * figure's preferred size.
	 * 
	 * @param p the input Point
	 * 
	 * @return a Rectangle
	 */
	@Override
	public Object getConstraintFor(final Point p) {
		return new Rectangle(p, DEFAULT_SIZE);
	}

	/**
	 * Returns a new Rectangle equivalent to the passed Rectangle.
	 * 
	 * @param r the input Rectangle
	 * 
	 * @return a copy of the input Rectangle
	 */
	@Override
	public Object getConstraintFor(final Rectangle r) {
		return new Rectangle(r);
	}

	protected EditPart getInsertionReference(Point mousePoint){
		@SuppressWarnings("unchecked")
		List<EditPart> children = getHost().getChildren();
		if(!children.isEmpty()){
			Transposer transposer = new Transposer();
			transposer.setEnabled(false);
			Point requestPoint = transposer.t(mousePoint);	
			Rectangle parentBoundCopy = ((AbstractGraphicalEditPart) getHost()).getFigure().getBounds().getCopy();
			((AbstractGraphicalEditPart)getHost()).getFigure().translateToAbsolute(parentBoundCopy);
			Rectangle parentBound = transposer.t(parentBoundCopy);
			for(EditPart child : children){
				Rectangle childBoundCopy = ((AbstractGraphicalEditPart)child).getFigure().getBounds().getCopy();
				((AbstractGraphicalEditPart)child).getFigure().translateToAbsolute(childBoundCopy);
				Rectangle childBound = transposer.t(childBoundCopy);
				if((requestPoint.y > parentBound.y) && (requestPoint.y <= childBound.bottom())){
						return child;
				}
			}
		}
		return null;
	}
}
