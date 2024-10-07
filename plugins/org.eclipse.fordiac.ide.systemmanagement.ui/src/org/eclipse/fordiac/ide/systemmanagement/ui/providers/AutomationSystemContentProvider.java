/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.providers;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.typemanagement.navigator.LibraryElementContentProvider;

public class AutomationSystemContentProvider extends LibraryElementContentProvider {
	private static SystemElementItemProviderAdapterFactory itemAdapterFactory = new SystemElementItemProviderAdapterFactory();

	public AutomationSystemContentProvider() {
		super(itemAdapterFactory);
	}

	@Override
	public Object[] getChildren(final Object parentElement) {
		if (parentElement instanceof final IFile ifile && SystemManager.isSystemFile(ifile)) {
			// retrieve the children for the Automation system
			return super.getChildren(SystemManager.INSTANCE.getSystem(ifile));
		}

		return super.getChildren(parentElement);
	}

	@Override
	public boolean hasChildren(final Object element) {
		return SystemManager.isSystemFile(element) || super.hasChildren(element);
	}

	@Override
	public Object getParent(final Object object) {
		if (object instanceof final IResource ires) {
			return ires.getParent();
		}
		return super.getParent(object);
	}
}
