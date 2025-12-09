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
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.provider;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.jface.viewers.IStructuredContentProvider;

public class PackageContentProvider implements IStructuredContentProvider {
	@Override
	public Object[] getElements(final Object inputElement) {
		if (inputElement instanceof final LibraryElement libElement && null != libElement.getCompilerInfo()
				&& null != libElement.getCompilerInfo().getImports()) {
			return libElement.getCompilerInfo().getImports().toArray();

		}
		return new Object[] {};
	}
}
