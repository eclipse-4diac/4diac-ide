/*******************************************************************************
 * Copyright (c) 2011 Profactor GmbH,
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
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
