/*******************************************************************************
 * Copyright (c) 2023 Fabio Gandolfi
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.fb.interpreter.mm;

import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;

public class ECStateAnnotations {

	public static ECState getActiveState(final String activeState, final BasicFBType bFBType) {
		return bFBType.getECC().getECState().stream().filter(state -> state.getName().equals(activeState)).findAny()
				.orElse(null);
	}

	private ECStateAnnotations() {
		throw new UnsupportedOperationException("do not instantiate"); //$NON-NLS-1$
	}
}
