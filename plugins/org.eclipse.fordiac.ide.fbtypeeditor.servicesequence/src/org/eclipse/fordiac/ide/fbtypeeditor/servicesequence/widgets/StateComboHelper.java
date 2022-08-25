/*******************************************************************************
 * Copyright (c) 2022 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Roithmayr - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.widgets;

import java.util.Arrays;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.swt.custom.CCombo;

public class StateComboHelper {
	public static void setup(final FBType fbtype, final ServiceSequence type, final CCombo combo) {
		if (fbtype instanceof BasicFBType) {
			final EList<ECState> states = ((BasicFBType) fbtype).getECC().getECState();
			final String[] statenames = Stream.concat(states.stream().map(ECState::getName), Stream.of("")) //$NON-NLS-1$
					.toArray(String[]::new);
			combo.setItems(statenames);
			int idx = type.getStartState() != null ? Arrays.asList(statenames).indexOf(type.getStartState())
					: -1;
			if (idx == -1) {
				idx = Arrays.asList(statenames).indexOf(""); //$NON-NLS-1$
			}
			combo.select(idx);
			combo.setEnabled(true);
		} else {
			combo.setEnabled(false);
		}
	}
}
