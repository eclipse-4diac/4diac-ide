/*******************************************************************************
 * Copyright (c) 2011 Profactor GmbH,
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.policies;

import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECStateEditPart;
import org.eclipse.gef.editpolicies.SelectionEditPolicy;

/**
 * The Class ECStateSelectionPolicy. Selection Edit Policy which forwards the
 * selection event on an ECState to its EditPart and calls the
 * highlightTransitions method.
 */
public class ECStateSelectionPolicy extends SelectionEditPolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editpolicies.SelectionEditPolicy#hideSelection()
	 */
	@Override
	protected void hideSelection() {
		((ECStateEditPart) getHost()).highlightTransitions(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editpolicies.SelectionEditPolicy#showSelection()
	 */
	@Override
	protected void showSelection() {
		((ECStateEditPart) getHost()).highlightTransitions(true);
	}

}
