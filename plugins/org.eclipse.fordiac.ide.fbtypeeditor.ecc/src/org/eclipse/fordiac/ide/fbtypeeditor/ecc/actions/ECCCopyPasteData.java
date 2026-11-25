/*******************************************************************************
 * Copyright (c) 2026 Vikash Kumar Sinha
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vikash Kumar Sinha
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.actions;

import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;

public record ECCCopyPasteData(List<ECState> states, BasicFBType sourceType) {

	public static final ECCCopyPasteData EMPTY = new ECCCopyPasteData(List.of(), null);

	public boolean isEmpty() {
		return states == null || states.isEmpty();
	}
}
