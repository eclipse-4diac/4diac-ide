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
 *   Virendra Ashiwal
 *     - initial implementation
 *     - extracted common code from ECStateToolTipFigure to ECCToolTip
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.figures;

import org.eclipse.fordiac.ide.model.libraryElement.ECState;

public class ECStateToolTipFigure extends ECCToolTip {

	public void setECState(ECState state) {
		setLabel(state.getComment());
	}

}
