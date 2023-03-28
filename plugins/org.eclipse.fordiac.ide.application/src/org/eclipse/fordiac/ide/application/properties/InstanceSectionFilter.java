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
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.application.editparts.InstanceComment;
import org.eclipse.fordiac.ide.application.editparts.InstanceName;
import org.eclipse.fordiac.ide.model.libraryElement.Comment;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IFilter;

public class InstanceSectionFilter implements IFilter {

	@Override
	public boolean select(final Object toTest) {
		return getFBNetworkElementFromSelectedElement(toTest) != null;
	}

	static FBNetworkElement getFBNetworkElementFromSelectedElement(final Object element) {
		Object retval = element;
		if (retval instanceof final EditPart ep) {
			retval = ep.getModel();
		}

		if (retval instanceof final FBNetwork fbn) {
			retval = fbn.eContainer();
		}

		if (retval instanceof final InstanceComment ic) {
			retval = ic.getRefElement();
		}

		if (retval instanceof final InstanceName in) {
			retval = in.getRefElement();
		}

		return isEditableFBNetworkElement(retval) ? (FBNetworkElement) retval : null;
	}

	private static boolean isEditableFBNetworkElement(final Object retval) {
		if (retval instanceof final FBNetworkElement fnbEl) {
			if (retval instanceof Group || retval instanceof Comment || retval instanceof Demultiplexer) {
				// these three do not have instance information to show
				return false;
			}
			return !fnbEl.isContainedInTypedInstance();
		}
		return false;
	}

}
