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

import org.eclipse.fordiac.ide.model.libraryElement.Import;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/** The Class CompilerLabelProvider. */
public class PackageLabelProvider extends LabelProvider implements ITableLabelProvider {

	/* (non-Javadoc)
	 *
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang. Object, int) */
	@Override
	public Image getColumnImage(final Object element, final int columnIndex) {
		return null;
	}

	/* (non-Javadoc)
	 *
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int) */
	@Override
	public String getColumnText(final Object element, final int columnIndex) {

		if (element instanceof final Import importer) {
			switch (columnIndex) {
			case 0:
				return ((Import) element).getImportedNamespace();
			default:
				break;
			}
		}
		return element.toString();
	}
}
