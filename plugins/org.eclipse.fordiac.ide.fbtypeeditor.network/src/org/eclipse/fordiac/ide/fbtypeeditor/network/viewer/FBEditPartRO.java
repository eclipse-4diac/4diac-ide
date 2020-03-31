/*******************************************************************************
 * Copyright (c) 2018 fortiss GmbH
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
package org.eclipse.fordiac.ide.fbtypeeditor.network.viewer;

import org.eclipse.fordiac.ide.application.editparts.FBEditPart;
import org.eclipse.fordiac.ide.application.policies.FBNElementSelectionPolicy;
import org.eclipse.fordiac.ide.gef.policies.EmptyXYLayoutEditPolicy;
import org.eclipse.gef.EditPolicy;

public class FBEditPartRO extends FBEditPart {

	FBEditPartRO() {
		super();
	}

	@Override
	protected void createEditPolicies() {
		// Highlight In and Outconnections of the selected fb, allow alignment of FBs
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new FBNElementSelectionPolicy());
		// correctly layout the internals
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EmptyXYLayoutEditPolicy());
		// do not allow any further editing of the RO FB
	}

	@Override
	public void performDirectEdit() {
		// don't do anything here to avoid direct edit
	}

}