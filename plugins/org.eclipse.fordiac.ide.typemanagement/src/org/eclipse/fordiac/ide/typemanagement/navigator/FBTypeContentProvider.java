/*******************************************************************************
 * Copyright (c) 2011, 2024 TU Wien ACIN, fortiss GmbH
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
 *   Patrick Aigner
 *     - moved methods to LibraryElementContentProvider
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.navigator;

import org.eclipse.core.resources.IFile;

public class FBTypeContentProvider extends LibraryElementContentProvider {

	public FBTypeContentProvider() {
		super(FBTypeComposedAdapterFactory.getAdapterFactory());
	}

	@Override
	public Object getParent(final Object element) {
		if (element instanceof final IFile file) {
			return file.getParent();
		}
		return super.getParent(element);
	}

	@Override
	public boolean hasChildren(final Object element) {
		if (element instanceof IFile) {
			return true;
		}
		return super.hasChildren(element);
	}
}
