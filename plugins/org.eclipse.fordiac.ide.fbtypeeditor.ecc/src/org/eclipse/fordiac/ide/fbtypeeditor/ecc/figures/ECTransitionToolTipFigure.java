/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 * 				 2020 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr, Ernst Blecha
 *     - initial implementation
 *   Virendra Ashiwal
 *   	- extracted common code from ECTransitionToolTipFigure to ECCToolTip
 *   Alexander Lumplecer
 *     - adjusted Label
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.figures;

import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;

public class ECTransitionToolTipFigure extends ECCToolTip {

	public void setECTransition(ECTransition transition) {
		final String sourDes = transition.getSource().getName() + " - " + transition.getDestination().getName();

		setLabel(sourDes, transition.getComment());
	}

}
