/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Muddasir Shakil
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.simplefb.properties;

import org.eclipse.fordiac.ide.fbtypeeditor.editparts.FBTypeEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.impl.SimpleFBTypeImpl;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.jface.viewers.IFilter;

public class SimpleFBFilter implements IFilter {

	@Override
	public boolean select(final Object toTest) {
		if (toTest instanceof FBTypeEditPart) {
			final RootEditPart root = ((FBTypeEditPart) toTest).getRoot();
			if (((EditPart) toTest).getModel() instanceof SimpleFBTypeImpl &&  root instanceof ScalableRootEditPart) { // here we are in the algorithm editor
				return true;
			}
		}
		return false;
	}

}
