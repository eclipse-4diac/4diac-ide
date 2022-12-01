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
import org.eclipse.fordiac.ide.ui.UIPlugin;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceConstants;
import org.eclipse.jface.viewers.IStructuredSelection;

public class IsTransferableStructCommentTester extends PropertyTester {

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (property.equalsIgnoreCase("isTansferableStruct") && receiver instanceof IStructuredSelection) { //$NON-NLS-1$
			IStructuredSelection selectedFB = (IStructuredSelection) receiver;
			if (selectedFB.getFirstElement() instanceof DemultiplexerEditPart) {
				return !UIPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.P_DEACTIVATE_COMMENT_TRANSFERRING_DEMUX_TO_MUX);
			}
		}
		return true;
	}

}
