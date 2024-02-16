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
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IFilter;

public class InstanceSectionFilter implements IFilter {

	@Override
	public boolean select(final Object toTest) {
		return getFBNetworkElementFromSelectedElement(toTest) != null;
	}

	static FBNetworkElement getFBNetworkElementFromSelectedElement(final Object element) {
		Object candidate = null;
		if (element instanceof final EditPart editPart) {
			candidate = editPart.getModel();
		}

		if (element instanceof final FBNetwork fbNetwork) {
			candidate = fbNetwork.eContainer();
		}

		if (element instanceof final InstanceComment instanceComment) {
			candidate = instanceComment.getRefElement();
		}

		if (element instanceof final InstanceName instanceName) {
			candidate = instanceName.getRefElement();
		}

		if (candidate instanceof final SubApp subapp) {
			return null;
		}

		if (candidate instanceof final FBNetworkElement fbNetworkElement
				&& isEditableFBNetworkElement(fbNetworkElement)) {
			return fbNetworkElement;
		}

		return null;
	}

	private static boolean isEditableFBNetworkElement(final FBNetworkElement fbNetworkElement) {
		if (fbNetworkElement instanceof Group || fbNetworkElement instanceof Comment
				|| fbNetworkElement instanceof Demultiplexer) {
			// these three do not have instance information to show
			return false;
		}
		return !fbNetworkElement.isContainedInTypedInstance();
	}

}
