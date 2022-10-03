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
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IFilter;

public class GroupSectionFilter implements IFilter {

	@Override
	public boolean select(final Object toTest) {
		return (groupFromSelectedObejct(toTest) != null);
	}

	static Group groupFromSelectedObejct(final Object selObj) {
		Object retval = selObj;
		if (retval instanceof EditPart) {
			retval = ((EditPart) retval).getModel();
		}

		if (retval instanceof InstanceComment) {
			retval = ((InstanceComment) retval).getRefElement();
		}

		if (retval instanceof InstanceName) {
			retval = ((InstanceName) retval).getRefElement();
		}

		if (retval instanceof Group) {
			return (Group) retval;
		}
		return null;
	}

}
