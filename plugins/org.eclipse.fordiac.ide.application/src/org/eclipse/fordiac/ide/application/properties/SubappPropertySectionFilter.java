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
 *   Mario Kastner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.application.editparts.InstanceComment;
import org.eclipse.fordiac.ide.application.editparts.InstanceName;
import org.eclipse.fordiac.ide.application.editparts.TargetInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IFilter;

public class SubappPropertySectionFilter implements IFilter {

	@Override
	public boolean select(final Object toTest) {
		return getFBNetworkElementFromSelectedElement(toTest) != null;
	}

	static SubApp getFBNetworkElementFromSelectedElement(final Object element) {
		Object candidate = null;
		if (element instanceof final EditPart editPart) {
			candidate = editPart.getModel();
		}

		if (candidate instanceof final FBNetwork fbNetwork) {
			candidate = fbNetwork.eContainer();
		}

		if (candidate instanceof final InstanceComment instanceComment) {
			candidate = instanceComment.getRefElement();
		}

		if (candidate instanceof final InstanceName instanceName) {
			candidate = instanceName.getRefElement();
		}

		if (candidate instanceof final TargetInterfaceElement targetIE) {
			candidate = targetIE.getHost().eContainer().eContainer();
		}

		if (candidate instanceof final SubApp subapp && !subapp.isTyped()) {
			return subapp;
		}

		return null;
	}

}
