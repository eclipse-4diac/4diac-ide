/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Hesam Rezaee
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.application.editparts.FBNetworkEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.jface.viewers.IFilter;

public class VarConfigurationFilter implements IFilter {

	@Override
	public boolean select(Object toTest) {
		return getObject(toTest) != null;
	}

	private Object getObject(final Object element) {
		Object retval = element;
		if ((retval instanceof FB) || (retval instanceof SubApp) || (retval instanceof Application)
				|| (retval instanceof FBNetworkEditPart)) {
			return true;
		}
		return null;
	}

}