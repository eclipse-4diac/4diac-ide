/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2017 Profactor GbmH, fortiss GmbH 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * NonResizeableEditPolicy with a rounded EtchedBorder and moveHandles (move
 * cursor) on each side.
 */
public class ModifiedNonResizeableEditPolicy extends NonResizableEditPolicy {

	private int arc = 14;

	private Insets insets = new Insets(2);

	/**
	 * Constructor.
	 * 
	 * @param arc the arc
	 * @param insets the insets
	 */
	public ModifiedNonResizeableEditPolicy(int arc, Insets insets) {
		super();
		this.arc = arc;
		this.insets = insets;
	}

	/**
	 * Default constructor.
	 */
	public ModifiedNonResizeableEditPolicy() {
		super();
		IPreferenceStore pf = Activator.getDefault().getPreferenceStore();
		int cornerDim = pf.getInt(DiagramPreferences.CORNER_DIM);
		this.arc = cornerDim;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected List createSelectionHandles() {
		List list = new ArrayList(1);
		list.add(new ModifiedMoveHandle((GraphicalEditPart) getHost(), insets, arc));
		return list;
	}
}
