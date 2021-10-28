/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Germany GmbH
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
package org.eclipse.fordiac.ide.systemmanagement.ui.filters;

import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class TypedSubappFilter extends ViewerFilter {


	@Override
	public boolean select(final Viewer viewer, final Object parentElement, final Object element) {
		return !(element instanceof SubApp && ((SubApp) element).getType() != null);
	}

}
