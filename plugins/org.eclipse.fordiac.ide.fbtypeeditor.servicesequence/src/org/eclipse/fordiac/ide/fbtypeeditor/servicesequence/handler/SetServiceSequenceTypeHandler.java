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
 *  Felix Roithmayr
 *     - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.ServiceSequenceEditPart;
import org.eclipse.fordiac.ide.model.ServiceSequenceTypes;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class SetServiceSequenceTypeHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final String id = event.getCommand().getId();
		final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);
		String type;
		switch (id) {
		case "org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.setPossible": //$NON-NLS-1$
			type = ServiceSequenceTypes.DEFAULT;
			break;
		case "org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.setAlways": //$NON-NLS-1$
			type = ServiceSequenceTypes.ALWAYS;
			break;
		case "org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.setForbidden": //$NON-NLS-1$
			type = ServiceSequenceTypes.FORBIDDEN;
			break;
		case "org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.setConditional": //$NON-NLS-1$
			type = ServiceSequenceTypes.CONDITIONAL;
			break;
		default:
			return Status.CANCEL_STATUS;
		}
		for (final Object selected : selection.toList()) {
			if (selected instanceof ServiceSequenceEditPart) {
				final ServiceSequence ss = ((ServiceSequenceEditPart) selected).getModel();
				ss.setServiceSequenceType(type);
			} else if (selected instanceof ServiceSequence) {
				final ServiceSequence ss = (ServiceSequence) selected;
				ss.setServiceSequenceType(type);
			}
		}
		return Status.OK_STATUS;
	}

}
