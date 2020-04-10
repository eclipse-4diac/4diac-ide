/*******************************************************************************
 * Copyright (c) 2011, 2013 TU Wien ACIN, Profactor GmbH, fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts;

import org.eclipse.fordiac.ide.model.libraryElement.ECAction;

public class ECActionAlgorithm {

	private ECAction action;

	public ECAction getAction() {
		return action;
	}

	public ECActionAlgorithm(ECAction action) {
		this.action = action;
	}

	String getLabel() {
		return action.getAlgorithm().getName();
	}

}
