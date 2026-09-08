/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
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

import org.eclipse.fordiac.ide.application.editparts.ErrorDataTypeEditPart;
import org.eclipse.fordiac.ide.application.editparts.InstanceComment;
import org.eclipse.fordiac.ide.application.editparts.InstanceName;
import org.eclipse.fordiac.ide.application.editparts.StructuredTypeEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IFilter;

public class ConfigFBInstanceSectionFilter implements IFilter {

	@Override
	public boolean select(final Object toTest) {
		return getConfigFbFromSelectedElement(toTest) != null;
	}

	static ConfigurableFB getConfigFbFromSelectedElement(final Object element) {
		Object candidate = element;
		if (element instanceof final EditPart editPart) {
			candidate = (editPart instanceof StructuredTypeEditPart || editPart instanceof ErrorDataTypeEditPart)
					? editPart.getParent().getModel()
					: editPart.getModel();
		}

		if (candidate instanceof final InstanceComment instanceComment) {
			candidate = instanceComment.getRefElement();
		}

		if (candidate instanceof final InstanceName instanceName) {
			candidate = instanceName.getRefElement();
		}

		if (candidate instanceof final ConfigurableFB configFB && !configFB.isContainedInTypedInstance()) {
			return configFB;
		}
		return null;
	}

}
