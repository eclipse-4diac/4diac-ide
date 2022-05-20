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
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.edit.providers;

import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.jface.viewers.ColumnLabelProvider;

public class TypeLabelProvider extends ColumnLabelProvider {

	@Override
	public String getText(final Object element) {
		if (element instanceof VarDeclaration) {
			final int arraySize = ((VarDeclaration) element).getArraySize();
			final String typeName = ((VarDeclaration) element).getTypeName();
			if (arraySize == -1) {
				return typeName;
			}
			return typeName + " (" + arraySize + ")"; //$NON-NLS-1$ //$NON-NLS-2$
		}
		return super.getText(element);
	}

}
