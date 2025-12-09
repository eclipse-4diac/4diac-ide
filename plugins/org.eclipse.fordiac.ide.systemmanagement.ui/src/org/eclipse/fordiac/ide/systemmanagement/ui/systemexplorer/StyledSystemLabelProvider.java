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
 *   Sebastian Hollersbacher - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer;

import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.StyledString;

public class StyledSystemLabelProvider extends SystemLabelProvider implements IStyledLabelProvider {
	@Override
	public StyledString getStyledText(final Object object) {
		final StyledString text = new StyledString(getText(object));
		if (object instanceof final INamedElement el) {
			final String comment = el.getComment();
			if (!comment.isEmpty()) {
				text.append(" [" + comment + "]", StyledString.DECORATIONS_STYLER); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
		return text;
	}
}
