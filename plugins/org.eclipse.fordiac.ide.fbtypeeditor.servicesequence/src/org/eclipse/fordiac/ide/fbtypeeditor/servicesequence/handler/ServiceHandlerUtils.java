/*******************************************************************************
 * Copyright (c) 2021, 2024 Johannes Kepler University Linz and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Bianca Wiesmayr
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.handler;

import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.ServiceSequenceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IStructuredSelection;

public class ServiceHandlerUtils {

	static ServiceSequence getFirstSelectedSequence(final IStructuredSelection selection) {
		for (final Object o : selection) {
			if (o instanceof final ServiceSequenceEditPart selectedSSEP) {
				return selectedSSEP.getModel();
			}
			if (o instanceof final ServiceSequence selectedSS) {
				return selectedSS;
			}
		}
		return null;
	}

	static FBType getSelectedFbType(final ServiceSequence seq, final IStructuredSelection sel) {
		if (seq != null && seq.getService() != null && seq.getService().getFBType() != null) {
			return seq.getService().getFBType();
		}
		for (Object o : sel) {
			if (o instanceof final EditPart ep) {
				o = ep.getModel();
			}
			if (o instanceof final FBType type) {
				return type;
			}
		}
		return null;
	}

	private ServiceHandlerUtils() {
		throw new UnsupportedOperationException();
	}
}
