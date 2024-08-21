/*******************************************************************************
 * Copyright (c) 2021, 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - added outMapped varInOuts to the model children
 *******************************************************************************/
package org.eclipse.fordiac.ide.subapptypeeditor.editparts;

import java.util.List;

import org.eclipse.fordiac.ide.fbtypeeditor.network.viewer.CompositeNetworkViewerEditPart;
import org.eclipse.gef.EditPart;

public class SubAppInstanceViewerEditPart extends CompositeNetworkViewerEditPart {

	@Override
	protected List<?> getModelChildren() {
		@SuppressWarnings("unchecked")
		final List<Object> modelChildren = (List<Object>) super.getModelChildren();
		if (getModel() != null) {
			modelChildren.addAll(getInterfaceList().getOutMappedInOutVars());
		}
		return modelChildren;
	}

	@Override
	protected boolean isVarVisible(final EditPart childEditPart) {
		// for subapps all interface vars are visible
		return true;
	}

}
