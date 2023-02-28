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
		if (retval instanceof EditPart) {
			retval = ((EditPart) retval).getModel();
		}

		if (retval instanceof FBNetwork) {
			retval = ((FBNetwork) retval).eContainer();
		}

		if (retval instanceof InstanceComment) {
			retval = ((InstanceComment) retval).getRefElement();
		}

		if (retval instanceof InstanceName) {
			retval = ((InstanceName) retval).getRefElement();
		}

		return isEditableFBNetworkElement(retval) ? (FBNetworkElement) retval : null;
	}

	private static boolean isEditableFBNetworkElement(final Object retval) {
		return retval instanceof FBNetworkElement && !(retval instanceof Group)
				&& !((FBNetworkElement) retval).isContainedInTypedInstance() && !(retval instanceof Demultiplexer);
	}

}
