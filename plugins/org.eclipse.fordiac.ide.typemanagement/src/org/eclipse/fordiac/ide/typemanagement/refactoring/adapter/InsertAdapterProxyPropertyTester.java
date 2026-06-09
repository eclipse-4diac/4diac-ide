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
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.adapter;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.jface.viewers.StructuredSelection;

public class InsertAdapterProxyPropertyTester extends PropertyTester {

	@Override
	public boolean test(final Object receiver, final String property, final Object[] args, final Object expectedValue) {
		if (receiver == null) {
			return false;
		}

		if (receiver instanceof final StructuredSelection sel) {
			final Object firstElement = sel.getFirstElement();

			if (firstElement instanceof final ConnectionEditPart conEditPart) {
				final Object model = conEditPart.getModel();
				if (model instanceof AdapterConnection) {
					return true;
				}
			}
			if (firstElement instanceof final IFile file) {
				return TypeLibraryTags.ADAPTER_TYPE_FILE_ENDING.equalsIgnoreCase(file.getFileExtension());
			}
		}

		return false;
	}

}
