/*******************************************************************************
 * Copyright (c) 2022 Paul Pavlicek
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Paul Pavlicek
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.utilities;

import org.eclipse.swt.layout.GridLayout;

public final class CellEditorLayoutFactory {

	public static GridLayout getNewGridZeroLayout(final int numColumns) {
		final GridLayout contLayout = new GridLayout(numColumns, false);
		contLayout.horizontalSpacing = 0;
		contLayout.marginTop = 0;
		contLayout.marginBottom = 0;
		contLayout.marginWidth = 0;
		contLayout.marginHeight = 0;
		contLayout.verticalSpacing = 0;
		contLayout.horizontalSpacing = 0;
		return contLayout;
	}

	private CellEditorLayoutFactory() {
		throw new UnsupportedOperationException("This Class should not be instantiated"); //$NON-NLS-1$
	}
}
