/*******************************************************************************
 * Copyright (c) 2015 - 2017 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.navigator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;

public class TypeLibRootElement implements IAdaptable {

	private final AutomationSystem system;

	TypeLibRootElement(final AutomationSystem system) {
		this.system = system;
	}

	public AutomationSystem getSystem() {
		return system;
	}

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		if ((adapter == IResource.class) || (adapter == IProject.class)) {
			return adapter.cast(getSystem().getTypeLibrary().getProject());
		}
		return null;
	}

}
