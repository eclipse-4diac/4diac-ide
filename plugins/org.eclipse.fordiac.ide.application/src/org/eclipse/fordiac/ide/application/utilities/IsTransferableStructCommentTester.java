/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.utilities;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.fordiac.ide.application.editparts.DemultiplexerEditPart;
import org.eclipse.fordiac.ide.ui.preferences.UIPreferenceConstants;
import org.eclipse.jface.viewers.IStructuredSelection;

public class IsTransferableStructCommentTester extends PropertyTester {

	@Override
	public boolean test(final Object receiver, final String property, final Object[] args, final Object expectedValue) {
		if ((property.equalsIgnoreCase("isTansferableStruct") //$NON-NLS-1$
				&& receiver instanceof final IStructuredSelection selectedFB)
				&& selectedFB.getFirstElement() instanceof DemultiplexerEditPart) {
			return !UIPreferenceConstants.STORE
					.getBoolean(UIPreferenceConstants.P_DEACTIVATE_COMMENT_TRANSFERRING_DEMUX_TO_MUX);
		}
		return true;
	}

}
