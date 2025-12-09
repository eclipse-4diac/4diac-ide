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
 *   Dunja Životin & Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.edit.providers;

import org.eclipse.fordiac.ide.model.edit.helper.CommentHelper;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class CommentLabelProvider extends ColumnLabelProvider {
	@Override
	public String getText(final Object element) {
		if (element instanceof final INamedElement namedElement) {
			return CommentHelper.getInstanceComment(namedElement);
		}
		return super.getText(element);
	}

	@Override
	public Color getForeground(final Object element) {
		if (element instanceof final INamedElement namedElement && CommentHelper.hasComment(namedElement)) {
			return super.getForeground(element);
		}
		return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GRAY);
	}
}
